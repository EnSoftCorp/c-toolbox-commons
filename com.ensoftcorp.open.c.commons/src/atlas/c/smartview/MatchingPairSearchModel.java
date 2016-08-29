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
public class MatchingPairSearchModel extends FilteringAtlasSmartViewScript {

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
		Q selectedType = res.leaves();
		Q a1 = Queries.functions("getbuf");
		Q a2 = Queries.functions("freebuf");
		Q leaves = a1.union(a2);
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