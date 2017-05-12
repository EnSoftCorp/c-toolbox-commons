package com.ensoftcorp.open.c.commons.ui.smart;

import static com.ensoftcorp.atlas.core.script.Common.edges;

import java.awt.Color;

import com.ensoftcorp.atlas.core.markup.Markup;
import com.ensoftcorp.atlas.core.markup.MarkupProperty;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.StyledResult;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.atlas.ui.scripts.selections.FilteringAtlasSmartViewScript;
import com.ensoftcorp.atlas.ui.selection.event.IAtlasSelectionEvent;

/**
 * For a selected function, displays the control flow graph. The edge back to
 * the start of the loop is highlighted in blue.
 */
public class ControlFlowWithinFunctionSmartView extends FilteringAtlasSmartViewScript {
	@Override
	public String[] getSupportedNodeTags() {
		return new String[]{XCSG.Function};
	}

	@Override
	public String[] getSupportedEdgeTags() {
		return FilteringAtlasSmartViewScript.NOTHING;
	}

	@Override
	public StyledResult selectionChanged(IAtlasSelectionEvent input, Q filteredSelection) {
		Q functions = filteredSelection;
		
		Q body = functions.contained();

		Q result = body.nodesTaggedWithAny(XCSG.ControlFlow_Node).induce(edges(XCSG.ControlFlow_Edge));
		
		Markup m = new Markup();
		m.setEdge(Common.codemap().edges(XCSG.ControlFlowBackEdge), MarkupProperty.EDGE_COLOR, Color.BLUE);
		
		return new StyledResult(result, m);
	}

	@Override
	public String getTitle() {
		return "Control Flow (within a function)";
	}
}
