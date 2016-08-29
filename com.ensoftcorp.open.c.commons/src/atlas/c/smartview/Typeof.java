package atlas.c.smartview;

import java.awt.Color;

import atlas.c.xinu.Queries;

import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.atlas.core.highlight.Highlighter;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.StyledResult;
import com.ensoftcorp.atlas.ui.scripts.selections.FilteringAtlasSmartViewScript;
import com.ensoftcorp.atlas.ui.selection.event.IAtlasSelectionEvent;
/**
 * For a selected node, displays the immediate type and the basis of that type.
 *
 */
public class Typeof extends FilteringAtlasSmartViewScript {

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
		
		Q res = Queries.typeOf(filteredSelection);
		
		Highlighter h = new Highlighter();
		h.highlightEdges(Queries.typeEdges(), Color.BLUE);
		
		return new StyledResult(res, h);
	}

	@Override
	public String getTitle() {
		return "TypeOf";
	}
}