package com.ensoftcorp.open.c.commons.ui.smart;

import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.StyledResult;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.atlas.ui.scripts.selections.FilteringAtlasSmartViewScript;
import com.ensoftcorp.atlas.ui.selection.event.IAtlasSelectionEvent;
import com.ensoftcorp.open.c.commons.Queries;

/**
 * Shows a call graph restricted to a set of leaf nodes for functions that read the selected variable
 */
public class MatchingPairSearchModelSmartView extends FilteringAtlasSmartViewScript {

	/**
	 * A set of leaf nodes to restrict the call graph to
	 */
	private static Q leaves = Common.empty();
	
	/**
	 * Get the leaf node restrictions
	 * @return
	 */
	public static Q getAllowedLeafNodes() {
		return leaves;
	}

	/**
	 * Set function 1 of the matching pair search model
	 * @param f1
	 */
	public static void setAllowedLeafNodes(Q leaves) {
		MatchingPairSearchModelSmartView.leaves = leaves.nodesTaggedWithAny(XCSG.Function);
	}
	
	@Override
	public String[] getSupportedNodeTags() {
		return new String[]{XCSG.DataFlow_Node, 
				XCSG.Variable,
				XCSG.Type, 
				XCSG.TypeAlias};
	}

	@Override
	public String[] getSupportedEdgeTags() {
		return FilteringAtlasSmartViewScript.NOTHING;
	}

	@Override
	public StyledResult selectionChanged(IAtlasSelectionEvent input, Q filteredSelection) {
		
		// attempt to initialize f1,f2 to xinu example defaults
		if(leaves.eval().nodes().isEmpty()){
			leaves = Queries.functions("getbuf").union(Queries.functions("freebuf"));
		}
		
		Q res = Queries.typeOf(filteredSelection);
		Q selectedType = res.leaves();
		Q refSelectedType = Queries.ref(selectedType);
		Q roots = refSelectedType.roots();
		Q searchModel = Queries.graph(roots, leaves);
		return new StyledResult(searchModel);
	}

	@Override
	public String getTitle() {
		return "Matching Pair Search Model";
	}
}