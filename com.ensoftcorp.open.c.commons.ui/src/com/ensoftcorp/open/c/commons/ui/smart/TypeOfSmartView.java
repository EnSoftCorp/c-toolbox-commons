package com.ensoftcorp.open.c.commons.ui.smart;

import java.awt.Color;

import com.ensoftcorp.atlas.core.markup.Markup;
import com.ensoftcorp.atlas.core.markup.MarkupProperty;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.StyledResult;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.atlas.ui.scripts.selections.FilteringAtlasSmartViewScript;
import com.ensoftcorp.atlas.ui.selection.event.IAtlasSelectionEvent;
import com.ensoftcorp.open.c.commons.analysis.CommonQueries;

/**
 * For a selected node, displays the immediate type and the basis of that type.
 */
public class TypeOfSmartView extends FilteringAtlasSmartViewScript {

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
		Q res = CommonQueries.typeOf(filteredSelection);
		
		Markup m = new Markup();
		m.setEdge(CommonQueries.typeEdges(), MarkupProperty.EDGE_COLOR, Color.BLUE);
		
		return new StyledResult(res, m);
	}

	@Override
	public String getTitle() {
		return "TypeOf";
	}
}