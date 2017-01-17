package com.ensoftcorp.open.c.commons.ui.smart;

import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.StyledResult;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.atlas.ui.scripts.selections.FilteringAtlasSmartViewScript;
import com.ensoftcorp.atlas.ui.selection.event.IAtlasSelectionEvent;
import com.ensoftcorp.open.c.commons.Queries;

/**
 * For a selected node, displays the immediate type and the basis of that type.
 *
 */
public class MatchingPairSearchModelSmartView extends FilteringAtlasSmartViewScript {

	/**
	 * Function 1 of the matching pair search model
	 */
	private static Node f1 = null;

	/**
	 * Function 2 of the matching pair search model
	 */
	private static Node f2 = null;
	
	/**
	 * Get function 1 of the matching pair search model
	 * @return
	 */
	public static Q getF1() {
		if(f1 == null){
			return Common.empty();
		} else {
			return Common.toQ(f1);
		}
	}

	/**
	 * Set function 1 of the matching pair search model
	 * @param f1
	 */
	public static void setF1(Q f1) {
		if(f2 != null){
			Q other = getF2() != null ? getF2() : Common.empty();
			AtlasSet<Node> functions = f1.nodesTaggedWithAny(XCSG.Function).difference(other).eval().nodes();
			if(functions.size() != 1){
				throw new IllegalArgumentException("Expected input is a single function that is not the same as function 2");
			} else {
				MatchingPairSearchModelSmartView.f1 = functions.one();
			}
		}
	}

	/**
	 * Get function 2 of the matching pair search model
	 * @return
	 */
	public static Q getF2() {
		if(f2 == null){
			return Common.empty();
		} else {
			return Common.toQ(f2);
		}
	}

	/**
	 * Set function 2 of the matching pair search model
	 * @param f2
	 */
	public static void setF2(Q f2) {
		if(f2 != null){
			Q other = getF1() != null ? getF1() : Common.empty();
			AtlasSet<Node> functions = f2.nodesTaggedWithAny(XCSG.Function).difference(other).eval().nodes();
			if(functions.size() != 1){
				throw new IllegalArgumentException("Expected input is a single function that is not the same as function 1");
			} else {
				MatchingPairSearchModelSmartView.f2 = functions.one();
			}
		}
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
		if(f1 == null){
			f1 = Queries.functions("getbuf").eval().nodes().one();
		}
		if(f2 == null){
			f2 = Queries.functions("freebuf").eval().nodes().one();
		}
		
		Q res = Queries.typeOf(filteredSelection);
		Q selectedType = res.leaves();
		Q leaves = getF1().union(getF2());
		Q refSelectedType = Queries.ref(selectedType);
		Q roots = refSelectedType.roots();
		Q searchModel = Queries.graph(roots,  leaves);
		return new StyledResult(searchModel);
	}

	@Override
	public String getTitle() {
		return "Matching Pair Search Model";
	}
}