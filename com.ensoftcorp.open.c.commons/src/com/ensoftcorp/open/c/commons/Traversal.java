package com.ensoftcorp.open.c.commons;

import java.util.Iterator;

import com.ensoftcorp.atlas.core.db.graph.Graph;
import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.graph.GraphElement.EdgeDirection;
import com.ensoftcorp.atlas.core.db.graph.GraphElement.NodeDirection;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.graph.UncheckedGraph;
import com.ensoftcorp.atlas.core.db.set.AtlasHashSet;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.db.set.EmptyAtlasSet;
import com.ensoftcorp.atlas.core.db.set.IntersectionSet;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.CommonQueries.TraversalDirection;
import com.ensoftcorp.atlas.core.xcsg.XCSG;

public class Traversal {
	
	public static Graph reverseDF(AtlasSet<Node> origin) {
		AtlasSet<Node> stop = EmptyAtlasSet.<Node>instance(Graph.U);
		return reverseDF(origin, stop);
	}
	
	public static Graph reverseDF(AtlasSet<Node> origin, AtlasSet<Node> stop) {
		Graph context = Common.universe().edgesTaggedWithAny(XCSG.DataFlow_Edge).eval();
		return traverse(context, TraversalDirection.REVERSE, origin, stop);
	}
	
	public static Graph forwardDF(AtlasSet<Node> origin) {
		AtlasSet<Node> stop = EmptyAtlasSet.<Node>instance(Graph.U);
		return forwardDF(origin, stop);
	}
	
	public static Graph forwardDF(AtlasSet<Node> origin, AtlasSet<Node> stop) {
		Graph context = Common.universe().edgesTaggedWithAny(XCSG.DataFlow_Edge).eval();
		return traverse(context, TraversalDirection.FORWARD, origin, stop);
	}

	/**
	 * Selects a subgraph of the given graph, by traversal in a given direction
	 * from the origin nodes.
	 * 
	 * @param graph
	 * @param direction FORWARD or REVERSE
	 * @param origin possible starting nodes
	 * @param stop nodes at which to stop traversal if encountered; such nodes are included in the result
	 * @return
	 */
	public static Graph traverse(Graph graph, TraversalDirection direction, AtlasSet<Node> origin, AtlasSet<Node> stop) {
		
		final NodeDirection nodeDirection = (direction == TraversalDirection.REVERSE) ? NodeDirection.IN : NodeDirection.OUT;
		final EdgeDirection edgeDirection = (direction == TraversalDirection.REVERSE) ? EdgeDirection.FROM : EdgeDirection.TO;
		
		AtlasHashSet<GraphElement> nodesInGraph = new AtlasHashSet<GraphElement>();
		AtlasHashSet<GraphElement> edgesInGraph = new AtlasHashSet<GraphElement>();

		AtlasHashSet<GraphElement> frontier = new AtlasHashSet<GraphElement>();
		frontier.addAll(new IntersectionSet<GraphElement>(graph.nodes(), origin));
		
		while (!frontier.isEmpty()) {
			Iterator<GraphElement> itr = frontier.iterator();
			GraphElement currentNode = itr.next();
			itr.remove();

			nodesInGraph.add(currentNode);
			
			if (stop.contains(currentNode)){
				continue;
			}
			
			AtlasSet<GraphElement> edges = graph.edges(currentNode, nodeDirection);

			for (GraphElement edge : edges) {
				edgesInGraph.add(edge);
				GraphElement nextNode = edge.getNode(edgeDirection);
				if (!nodesInGraph.contains(nextNode)) {
					frontier.add(nextNode);
				}
			}
		}
		
		return new UncheckedGraph(nodesInGraph, edgesInGraph);
	}
}
