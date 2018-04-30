package it.unibz.inf.ontop.model.atom;


import com.google.common.collect.ImmutableList;
import it.unibz.inf.ontop.model.term.ImmutableTerm;
import it.unibz.inf.ontop.model.term.Variable;
import org.apache.commons.rdf.api.IRI;

import java.util.Optional;

/**
 * Abstraction for triples, quads and so on.
 */
public interface RDFAtomPredicate extends AtomPredicate {

    Optional<IRI> getClassIRI(ImmutableList<? extends ImmutableTerm> atomArguments);
    Optional<IRI> getPropertyIRI(ImmutableList<? extends ImmutableTerm> atomArguments);

    <T extends ImmutableTerm> T getSubject(ImmutableList<T> atomArguments);
    <T extends ImmutableTerm> T getProperty(ImmutableList<T> atomArguments);
    <T extends ImmutableTerm> T getObject(ImmutableList<T> atomArguments);

    <T extends ImmutableTerm> ImmutableList<T> updateSPO(ImmutableList<T> originalArguments, T newSubject,
                                                         T newProperty, T newObject);

    default <T extends ImmutableTerm> ImmutableList<T> updateSO(ImmutableList<T> originalArguments,
                                                                T newSubject, T newObject) {
        return updateSPO(originalArguments, newSubject, getProperty(originalArguments), newObject);
    }
}
