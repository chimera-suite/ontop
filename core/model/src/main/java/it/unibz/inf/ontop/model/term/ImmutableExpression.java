package it.unibz.inf.ontop.model.term;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import it.unibz.inf.ontop.exception.IncompatibleTermException;
import it.unibz.inf.ontop.model.term.functionsymbol.OperationPredicate;
import it.unibz.inf.ontop.model.type.TermType;

import java.util.Optional;

public interface ImmutableExpression extends Expression, ImmutableFunctionalTerm {
    @Override
    ImmutableExpression clone();

    /**
     * Flattens AND expressions.
     */
    ImmutableSet<ImmutableExpression> flattenAND();

    /**
     * Flattens OR expressions.
     */
    ImmutableSet<ImmutableExpression> flattenOR();

    /**
     * Generalization of flattening (AND, OR, etc.).
     *
     * It is the responsibility of the caller to make sure such a flattening makes sense.
     */
    ImmutableSet<ImmutableExpression> flatten(OperationPredicate operator);

    boolean isVar2VarEquality();

    /**
     * TODO: generalize
     */
    default Optional<TermType> getOptionalTermType() throws IncompatibleTermException {
        try {
            OperationPredicate predicate = getFunctionSymbol();
            return predicate.getTermTypeInferenceRule().inferType(
                    getArguments(), predicate.getArgumentTypes());
        } catch (IncompatibleTermException e) {
            throw new IncompatibleTermException(this, e);
        }
    }

    default Optional<TermType> getOptionalTermType(ImmutableList<Optional<TermType>> actualArgumentTypes) {
        try {
            OperationPredicate predicate = getFunctionSymbol();
            return predicate.getTermTypeInferenceRule().inferTypeFromArgumentTypes(
                    actualArgumentTypes, predicate.getArgumentTypes());
        } catch (IncompatibleTermException e) {
            throw new IncompatibleTermException(this, e);
        }
    }
}