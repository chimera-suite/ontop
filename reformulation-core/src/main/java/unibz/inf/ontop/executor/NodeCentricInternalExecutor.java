package unibz.inf.ontop.executor;

import unibz.inf.ontop.pivotalrepr.QueryNode;
import unibz.inf.ontop.pivotalrepr.proposal.NodeCentricOptimizationProposal;
import unibz.inf.ontop.pivotalrepr.proposal.NodeCentricOptimizationResults;

/**
 * TODO: explain
 */
public interface NodeCentricInternalExecutor<N extends QueryNode, P extends NodeCentricOptimizationProposal<N>>
        extends InternalProposalExecutor<P, NodeCentricOptimizationResults<N>> {

}
