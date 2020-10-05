package it.unibz.inf.ontop.rdf4j.predefined.impl;

import com.google.common.collect.ImmutableMap;
import it.unibz.inf.ontop.answering.reformulation.input.RDF4JInputQuery;
import it.unibz.inf.ontop.rdf4j.predefined.InvalidBindingSetException;
import it.unibz.inf.ontop.rdf4j.predefined.PredefinedQuery;
import it.unibz.inf.ontop.rdf4j.predefined.parsing.PredefinedQueryConfigEntry;
import org.eclipse.rdf4j.query.BindingSet;

import java.util.Optional;

public class AbstractPredefinedQuery<Q extends RDF4JInputQuery> implements PredefinedQuery<Q> {

    private final String id;
    private final PredefinedQueryConfigEntry queryConfig;
    private final Q inputQuery;

    public AbstractPredefinedQuery(String id, Q inputQuery, PredefinedQueryConfigEntry queryConfig) {
        this.id = id;
        this.queryConfig = queryConfig;
        this.inputQuery = inputQuery;
    }

    @Override
    public Q getInputQuery() {
        return inputQuery;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Optional<String> getName() {
        return queryConfig.getName();
    }

    @Override
    public Optional<String> getDescription() {
        return queryConfig.getDescription();
    }

    @Override
    public BindingSet validateAndConvertBindings(ImmutableMap<String, String> bindings) throws InvalidBindingSetException {
        throw new RuntimeException("TODO: implement binding validation and conversion");
    }
}
