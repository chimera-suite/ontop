package it.unibz.inf.ontop.dbschema;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import it.unibz.inf.ontop.injection.OntopSQLCoreConfiguration;
import it.unibz.inf.ontop.utils.ImmutableCollectors;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BasicViewWithConstraintsProfWithDuplicatesTest {
    private static final String VIEW_FILE = "src/test/resources/prof/prof-basic-views-with-constraints-duplicateUCFD.json";
    private static final String DBMETADATA_FILE = "src/test/resources/prof/prof_with_constraints.db-extract.json";

    ImmutableSet<OntopViewDefinition> viewDefinitions = loadViewDefinitions(VIEW_FILE, DBMETADATA_FILE);

    public BasicViewWithConstraintsProfWithDuplicatesTest() throws Exception {
    }

    /**
     * Duplicate functional dependencies appear only once - check determinant
     */
    @Test
    public void testProfDuplicateFDDeterminants() throws Exception {
        List<String> otherFD = viewDefinitions.stream()
                .map(v -> v.getOtherFunctionalDependencies())
                .flatMap(Collection::stream)
                .map(d -> d.getDeterminants())
                .flatMap(Collection::stream)
                .map(d -> d.getID().getName())
                .collect(Collectors.toList());

        assertEquals(ImmutableList.of("first_name"), otherFD);
    }

    /**
     * Duplicate functional dependencies appear only once - check dependent
     */
    @Test
    public void testProfDuplicateFDDependents() throws Exception {
        List<String> otherFD = viewDefinitions.stream()
                .map(v -> v.getOtherFunctionalDependencies())
                .flatMap(Collection::stream)
                .map(d -> d.getDependents())
                .flatMap(Collection::stream)
                .map(d -> d.getID().getName())
                .collect(Collectors.toList());

        assertEquals(ImmutableList.of("last_name"), otherFD);
    }

    /**
     * Duplicate unique constraints appear only once - check determinant
     */
    @Test
    public void testProfDuplicateUCColumn() throws Exception {
        List<String> constraints = viewDefinitions.stream()
                .map(v -> v.getUniqueConstraints())
                .flatMap(Collection::stream)
                .map(d -> d.getAttributes())
                .flatMap(Collection::stream)
                .map(d -> d.getID().getName())
                .collect(Collectors.toList());

        assertEquals(ImmutableList.of("position", "a_id"), constraints);
    }

    protected ImmutableSet<OntopViewDefinition> loadViewDefinitions(String viewFilePath,
                                                                    String dbMetadataFilePath)
            throws Exception {

        OntopSQLCoreConfiguration configuration = OntopSQLCoreConfiguration.defaultBuilder()
                .jdbcUrl("jdbc:h2:mem:nowhere")
                .jdbcDriver("org.h2.Driver")
                .build();

        Injector injector = configuration.getInjector();
        SerializedMetadataProvider.Factory serializedMetadataProviderFactory = injector.getInstance(SerializedMetadataProvider.Factory.class);
        OntopViewMetadataProvider.Factory viewMetadataProviderFactory = injector.getInstance(OntopViewMetadataProvider.Factory.class);

        SerializedMetadataProvider dbMetadataProvider;
        try (Reader dbMetadataReader = new FileReader(dbMetadataFilePath)) {
            dbMetadataProvider = serializedMetadataProviderFactory.getMetadataProvider(dbMetadataReader);
        }

        OntopViewMetadataProvider viewMetadataProvider;
        try (Reader viewReader = new FileReader(viewFilePath)) {
            viewMetadataProvider = viewMetadataProviderFactory.getMetadataProvider(dbMetadataProvider, viewReader);
        }

        ImmutableMetadata metadata = ImmutableMetadata.extractImmutableMetadata(viewMetadataProvider);

        return metadata.getAllRelations().stream()
                .filter(r -> r instanceof OntopViewDefinition)
                .map(r -> (OntopViewDefinition) r)
                .collect(ImmutableCollectors.toSet());
    }
}
