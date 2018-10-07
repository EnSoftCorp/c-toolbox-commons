package com.ensoftcorp.open.c.commons.ui.ui.smart;

import java.awt.Color;

import com.ensoftcorp.atlas.core.markup.Markup;
import com.ensoftcorp.atlas.core.markup.MarkupProperty;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.StyledResult;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.atlas.ui.scripts.selections.FilteringAtlasSmartViewScript;
import com.ensoftcorp.atlas.ui.selection.event.IAtlasSelectionEvent;
import com.ensoftcorp.open.commons.analysis.CommonQueries;

/**
 * For a selected function, displays the data flow graph embedded in the control
 * flow graph. The control flow edge back to the start of the loop is
 * highlighted in blue.
 */
public class DataFlowWithinFunctionSmartView extends FilteringAtlasSmartViewScript {
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

		Q result = CommonQueries.dfg(functions).union(CommonQueries.cfg(functions));
		
		Markup m = new Markup();
		m.setEdge(Common.codemap().edges(XCSG.ControlFlowBackEdge), MarkupProperty.EDGE_COLOR, Color.BLUE);

		return new StyledResult(result, m);
	}

	@Override
	public String getTitle() {
		return "Data Flow (within a function)";
	}
}
