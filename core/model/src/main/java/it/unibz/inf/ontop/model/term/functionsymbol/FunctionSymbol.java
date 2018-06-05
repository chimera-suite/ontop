package it.unibz.inf.ontop.model.term.functionsymbol;

import com.google.common.collect.ImmutableList;
import it.unibz.inf.ontop.exception.FatalTypingException;
import it.unibz.inf.ontop.iq.node.VariableNullability;
import it.unibz.inf.ontop.model.term.ImmutableTerm;
import it.unibz.inf.ontop.model.term.NonFunctionalTerm;
import it.unibz.inf.ontop.model.term.Variable;
import it.unibz.inf.ontop.model.type.TypeInference;

import java.util.Optional;

/**
 * FunctionSymbols are the functors needed to build ImmutableFunctionalTerms
 */
public interface FunctionSymbol extends Predicate {

    FunctionalTermNullability evaluateNullability(ImmutableList<? extends NonFunctionalTerm> arguments,
                               VariableNullability childNullability);


    TypeInference inferType(ImmutableList<? extends ImmutableTerm> terms) throws FatalTypingException;


    interface FunctionalTermNullability {

        boolean isNullable();

        /**
         * When the nullability of a functional term is bound to the nullability
         * of a variable
         */
        Optional<Variable> getBoundVariable();
    }


}
