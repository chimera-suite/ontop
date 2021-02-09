package it.unibz.inf.ontop.protege.utils;

import it.unibz.inf.ontop.protege.core.OBDADataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class ExecuteSQLQuerySwingWorker extends SwingWorkerWithCompletionPercentageMonitor<DefaultTableModel, Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteSQLQuerySwingWorker.class);

    private final Dialog dialog;
    private final String sqlQuery;
    private final int maxRows;
    private final OBDADataSource dataSource;
    private final Consumer<DefaultTableModel> tableModelConsumer;

    public ExecuteSQLQuerySwingWorker(Dialog dialog, OBDADataSource dataSource, String sqlQuery, int maxRows, Consumer<DefaultTableModel> tableModelConsumer) {
        super(dialog, "<html><h3>Executing SQL Query:</h3></html>");
        this.dialog = dialog;
        this.dataSource = dataSource;
        this.sqlQuery = sqlQuery;
        this.maxRows = maxRows;
        this.tableModelConsumer = tableModelConsumer;

        progressMonitor.setCancelAction(this::doCancel);
    }

    private Statement statement;

    private void doCancel() {
        JDBCConnectionManager.cancelQuietly(statement);
    }

    @Override
    protected DefaultTableModel doInBackground() throws Exception {
        start("initializing...");
        setMaxTicks(maxRows);
        try (Connection conn = dataSource.getConnection()) {
            statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            statement.setMaxRows(maxRows);
            try (ResultSet rs = statement.executeQuery(sqlQuery)) {
                ResultSetMetaData metadata = rs.getMetaData();
                int numcols = metadata.getColumnCount();
                String[] columns = new String[numcols];
                for (int i = 1; i <= numcols; i++)
                    columns[i - 1] = metadata.getColumnLabel(i);
                DefaultTableModel tableModel = DialogUtils.createNonEditableTableModel(columns);
                startLoop(this::getCompletionPercentage, () -> String.format("%d%% rows retrieved...", getCompletionPercentage()));
                while (rs.next()) {
                    String[] values = new String[numcols];
                    for (int i = 1; i <= numcols; i++)
                        values[i - 1] = rs.getString(i);
                    tableModel.addRow(values);
                    tick();
                }
                endLoop("generating table...");
                end();
                return tableModel;
            }
        }
        finally {
            JDBCConnectionManager.closeQuietly(statement);
        }
    }

    @Override
    public void done() {
        try {
            tableModelConsumer.accept(complete());
        }
        catch (CancellationException | InterruptedException ignore) {
        }
        catch (ExecutionException e) {
            DialogUtils.showErrorDialog(dialog, dialog.getTitle(), "Error executing SQL sqlQuery: " + sqlQuery, LOGGER, e, dataSource);
        }
    }
}
