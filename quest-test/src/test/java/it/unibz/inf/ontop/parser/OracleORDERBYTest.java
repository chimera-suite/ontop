package it.unibz.inf.ontop.parser;

/*
 * #%L
 * ontop-test
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.inf.ontop.model.OBDAException;
import it.unibz.inf.ontop.owlrefplatform.core.SQLExecutableQuery;
import it.unibz.inf.ontop.owlrefplatform.owlapi.QuestOWLStatement;
import it.unibz.inf.ontop.quest.AbstractVirtualModeTest;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/***
 * Tests that the SPARQL ORDER BY statement is correctly translated to ORDER BY in SQL.
 */
public class OracleORDERBYTest extends AbstractVirtualModeTest {

    private static final Logger log = LoggerFactory.getLogger(OracleORDERBYTest.class);
    static final String owlFile = "resources/orderby/orderBy.owl";
    static final String obdaFile = "resources/orderby/orderBy.obda";

    public OracleORDERBYTest() {
        super(owlFile, obdaFile);
    }

    private void runQueryAndCheckSQL(String query) throws OBDAException, OWLException{

        QuestOWLStatement st = conn.createStatement();
        String sql = ((SQLExecutableQuery)st.getExecutableQuery(query)).getSQL();
        //boolean m = sql.matches("(?ms)(.*)ORDER BY country_name (.*)");
        boolean m = sql.matches("(?ms)(.*)ORDER BY (.*)");
        log.debug(sql);
        assertTrue(m);
    }


    @Test
    public void testOrderBy() throws Exception {

        String query = "PREFIX : <http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#> " +
                "SELECT ?x ?name " +
                "WHERE { ?x a :Country; :name ?name . } "
                + "ORDER BY ?name"
                ;

        runQueryAndCheckSQL(query);

        List<String> expectedUris = new ArrayList<>();
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Argentina");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Australia");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Belgium");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Brazil");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Canada");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-China");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Denmark");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Egypt");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-France");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Germany");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-India");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Israel");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Italy");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Japan");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Kuwait");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Malaysia");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Mexico");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Netherlands");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Nigeria");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Singapore");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Switzerland");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-United%20Kingdom");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-United%20States%20of%20America");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Zambia");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Zimbabwe");

        checkReturnedUris(query, expectedUris);
    }


    @Test
    public void testOrderByAndLimit() throws Exception {
        String query = "PREFIX : <http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#> " +
                "SELECT ?x ?name " +
                "WHERE { ?x a :Country; :name ?name . } "
                + "ORDER BY ?name "
                + "LIMIT 2 " ;

        runQueryAndCheckSQL(query);
        List<String> expectedUris = new ArrayList<>();
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Argentina");
        expectedUris.add("http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-Australia");
        checkReturnedUris(query, expectedUris);
    }


}
