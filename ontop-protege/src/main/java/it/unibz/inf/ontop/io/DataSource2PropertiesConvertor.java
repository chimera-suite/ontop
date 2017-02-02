package it.unibz.inf.ontop.io;

import it.unibz.inf.ontop.injection.OntopSQLCoreSettings;
import it.unibz.inf.ontop.model.OBDADataSource;
import it.unibz.inf.ontop.model.impl.RDBMSourceParameterConstants;

import java.util.Properties;

/**
 * TODO: explain
 */
public class DataSource2PropertiesConvertor {

    /**
     * These properties are compatible with OBDAProperties' keys.
     */
    public static Properties convert(OBDADataSource source) {

        String id = source.getSourceID().toString();
        String url = source.getParameter(RDBMSourceParameterConstants.DATABASE_URL);
        String username = source.getParameter(RDBMSourceParameterConstants.DATABASE_USERNAME);
        String password = source.getParameter(RDBMSourceParameterConstants.DATABASE_PASSWORD);
        String driver =  source.getParameter(RDBMSourceParameterConstants.DATABASE_DRIVER);

        Properties p = new Properties();
        p.put(OntopSQLCoreSettings.JDBC_NAME, id);
        p.put(OntopSQLCoreSettings.JDBC_URL, url);
        p.put(OntopSQLCoreSettings.JDBC_USER, username);
        p.put(OntopSQLCoreSettings.JDBC_PASSWORD, password);
        p.put(OntopSQLCoreSettings.JDBC_DRIVER, driver);

        return p;
    }
}
