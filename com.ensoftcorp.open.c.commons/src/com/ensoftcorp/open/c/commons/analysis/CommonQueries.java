package com.ensoftcorp.open.c.commons.analysis;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IPath;

import com.ensoftcorp.atlas.core.db.graph.Edge;
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
import com.ensoftcorp.atlas.core.index.common.SourceCorrespondence;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.query.Query;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.CommonQueries.TraversalDirection;
import com.ensoftcorp.atlas.core.xcsg.XCSG;

/**
 * Common queries which are useful for writing larger Java analysis programs,
 * and for using on the shell.
 * 
 * @author Ben Holland, Tom Deering, Jon Mathews
 */
public final class CommonQueries {	
	
	// hide constructor
	private CommonQueries() {}
	
	// begin wrapper queries
	
	/**
	 * Produces a declarations (contains) graph. 
	 * 
	 * Equivalent to declarations(index(), origin).
	 * 
	 * @param origin
	 * @return the query expression
	 */
	public static Q declarations(Q origin){
		return com.ensoftcorp.atlas.core.script.CommonQueries.declarations(origin);
	}
	
	
	/**
	 * Produces a declarations (contains) graph. Uses only the given context for
	 * the traversal.
	 * 
	 * @param context
	 * @param origin
	 * @return the query expression
	 */
	public static Q declarations(Q context, Q origin){
		return com.ensoftcorp.atlas.core.script.CommonQueries.declarations(context, origin);
	}
	
	/**
	 * Returns direct edges of the given kinds which lay immediately between the
	 * first group and second group of nodes. In the case that the selected edge
	 * kinds have multiple levels of granularity, only the function level of
	 * granularity is used.
	 * 
	 * @param first
	 * @param second
	 * @param edgeTags
	 * @return the query expression
	 */
	public static Q interactions(Q first, Q second, String... edgeTags){
		return com.ensoftcorp.atlas.core.script.CommonQueries.interactions(first, second, edgeTags);
	}
	
	/**
	 * Returns direct edges of the given kinds which lay immediately between the
	 * first group and second group of nodes. In the case that the selected edge
	 * kinds have multiple levels of granularity, only the function level of
	 * granularity is used. Uses only the given context for the traversal.
	 * 
	 * @param context
	 * @param first
	 * @param second
	 * @param edgeTags
	 * @return the query expression
	 */
	public static Q interactions(Q context, Q first, Q second, String... edgeTags){
		return com.ensoftcorp.atlas.core.script.CommonQueries.interactions(context, first, second, edgeTags);
	}
	
	/**
	 * Returns those nodes which are declared by a library.
	 * 
	 * @return the query expression
	 */
	public static Q libraryDeclarations(){
		return com.ensoftcorp.atlas.core.script.CommonQueries.libraryDeclarations(); 
	}
	
	/**
	 * Returns those nodes which are declared by a library. Results are only
	 * returned if they are within the given context.
	 * 
	 * Equivalent to libraryDeclarations(index())
	 * 
	 * @param context
	 * @return the query expression
	 */
	public static Q libraryDeclarations(Q context){
		return com.ensoftcorp.atlas.core.script.CommonQueries.libraryDeclarations(context); 
	}
	
	/**
	 * Returns those nodes which are declared by a library with the given name.
	 * 
	 * @param name
	 * @return the query expression
	 */
	public static Q libraryDeclarations(String name){
		return com.ensoftcorp.atlas.core.script.CommonQueries.libraryDeclarations(name); 
	}
	
	/**
	 * Returns those nodes which are declared by a library with the given name.
	 * Results are only returned if they are within the given context.
	 * 
	 * Equivalent to libraryDeclarations(index(), name)
	 * 
	 * @param context
	 * @param name
	 * @return the query expression
	 */
	public static Q libraryDeclarations(Q context, String name){
		return com.ensoftcorp.atlas.core.script.CommonQueries.libraryDeclarations(context, name); 
	}
	
	/**
	 * Returns the parameters of the given functions. 
	 * 
	 * Equivalent to functionParameter(index(), functions)
	 * 
	 * @param functions
	 * @return the query expression
	 */
	public static Q functionParameter(Q functions){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionParameter(functions); 
	}
	
	/**
	 * Returns the parameters of the given functions at the given indices. 
	 * 
	 * Equivalent to functionParameter(index(), functions, index)
	 * 
	 * @param functions
	 * @param index
	 * @return the query expression
	 */
	public static Q functionParameter(Q functions, Integer... index){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionParameter(functions, index); 
	}
	
	/**
	 * Returns the parameters of the given functions. Results are only returned if
	 * they are within the given context.
	 * 
	 * @param context
	 * @param functions
	 * @return the query expression
	 */
	public static Q functionParameter(Q context, Q functions){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionParameter(context, functions); 
	}
	
	/**
	 * Returns the parameters of the given functions at the given indices. Results
	 * are only returned if they are within the given context.
	 * 
	 * @param context
	 * @param functions
	 * @param index
	 * @return the query expression
	 */
	public static Q functionParameter(Q context, Q functions, Integer... index){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionParameter(context, functions, index); 
	}
	
	/**
	 * Returns the return nodes for the given functions.
	 * 
	 * Equivalent to functionReturn(index(), functions).
	 * 
	 * @param functions
	 * @return the query expression
	 */
	public static Q functionReturn(Q functions){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionReturn(functions); 
	}
	
	/**
	 * Returns the return nodes for the given functions.
	 * @param context
	 * @param functions
	 * @return the query expression
	 */
	public static Q functionReturn(Q context, Q functions){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionReturn(context, functions); 
	}
	
	/**
	 * Returns the functions declared by the given types. 
	 * 
	 * Equivalent to functionsOf(index(), types).
	 * 
	 * @param params
	 * @return the query expression
	 */
	public static Q functionsOf(Q types){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionsOf(types); 
	}
	
	/**
	 * Returns the functions declared by the given types.
	 * 
	 * @param context
	 * @param types
	 * @return the query expression
	 */
	public static Q functionsOf(Q context, Q types){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functionsOf(context, types); 
	}
	
	/**
	 * Returns the nodes whose names contain the given string.
	 * 
	 * Equivalent to nodesContaining(index(), substring).
	 * 
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesContaining(String substring){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesContaining(substring);
	}
	
	/**
	 * Returns the nodes whose names contain the given string within the given
	 * context.
	 * 
	 * @param context
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesContaining(Q context, String substring){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesContaining(context, substring);
	}
	
	/**
	 * Returns the nodes whose names end with the given string.
	 * 
	 * Equivalent to nodesEndingWith(index(), suffix).
	 * 
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesEndingWith(String suffix){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesEndingWith(suffix);
	}
	
	/**
	 * Returns the nodes whose names end with the given string within the given
	 * context.
	 * 
	 * @param context
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesEndingWith(Q context, String suffix){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesEndingWith(context, suffix);
	}
	
	/**
	 * Returns the nodes whose names match the given regular expression.
	 * 
	 * Equivalent to nodesMatchingRegex(index(), regex).
	 * 
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesMatchingRegex(String regex){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesMatchingRegex(regex);
	}
	
	/**
	 * Returns the nodes whose names match the given regular expression within
	 * the given context.
	 * 
	 * @param context
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesMatchingRegex(Q context, String regex){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesMatchingRegex(context, regex);
	}
	
	/**
	 * Returns the nodes whose names start with the given string.
	 * 
	 * Equivalent to nodesStartingWith(index(), prefix).
	 * 
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesStartingWith(String prefix){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesStartingWith(prefix);
	}
	
	/**
	 * Returns the nodes whose names start with the given string within the
	 * given context.
	 * 
	 * @param context
	 * @param substring
	 * @return the query expression
	 */
	public static Q nodesStartingWith(Q context, String prefix){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodesStartingWith(context, prefix);
	}
	
	/**
	 * Returns the number of edges contained.
	 * @param toCount
	 * @return
	 */
	public static long edgeSize(Q toCount){
		return com.ensoftcorp.atlas.core.script.CommonQueries.edgeSize(toCount);
	}
	
	/**
	 * Returns the number of nodes contained.
	 * @param toCount
	 * @return
	 */
	public static long nodeSize(Q toCount){
		return com.ensoftcorp.atlas.core.script.CommonQueries.nodeSize(toCount);
	}
	
	/**
	 * Returns whether the given Q is empty.
	 * 
	 * @param test
	 * @return
	 */
	public static boolean isEmpty(Q test){
		return com.ensoftcorp.atlas.core.script.CommonQueries.isEmpty(test);
	}
	
	// begin toolbox commons queries
	
	/**
	 * Selects the Atlas graph element given a serialized graph
	 * element address
	 * 
	 * Returns null if the address does not correspond to a graph element
	 * 
	 * @param address
	 * @return
	 */
	public static GraphElement getGraphElementByAddress(String address){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getGraphElementByAddress(address);
	}
	
	/**
	 * Selects the Atlas node graph element given a serialized graph
	 * element address
	 * 
	 * Returns null if the address does not correspond to a node
	 * 
	 * @param address
	 * @return
	 */
	public static Node getNodeByAddress(String address){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getNodeByAddress(address);
	}
	
	/**
	 * Selects the Atlas edge graph element given a serialized graph
	 * element address
	 * 
	 * Returns null if the address does not correspond to a edge
	 * 
	 * @param address
	 * @return
	 */
	public static Edge getEdgeByAddress(String address){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getEdgeByAddress(address);
	}
	
	/**
	 * All nodes declared under the given functions, but NOT declared under
	 * additional functions or types. Retrieves declarations of only this function.
	 * Results are only returned if they are within the given context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q localDeclarations(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.localDeclarations(origin);
	}

	/**
	 * All nodes declared under the given functions, but NOT declared under
	 * additional functions or types. Retrieves declarations of only this function.
	 * Results are only returned if they are within the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q localDeclarations(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.localDeclarations(context, origin);
	}

	/**
	 * Returns the direct callers of the given functions.
	 * 
	 * Operates in the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q callers(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.callers(origin);
	}

	/**
	 * Returns the direct callers of the given functions.
	 * 
	 * Operates in the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q callers(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.callers(context, origin);
	}

	/**
	 * Returns the subset of the given functions which are called.
	 * 
	 * Operates in the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q called(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.called(origin);
	}

	/**
	 * Returns the subset of the given functions which are called. Results are
	 * only returned if they are within the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q called(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.called(context, origin);
	}

	/**
	 * Returns the given functions which were called by the given callers.
	 * 
	 * Operates in the index context.
	 * 
	 * @param callers
	 * @param called
	 * @return
	 */
	public static Q calledBy(Q callers, Q called) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.calledBy(callers, called);
	}

	/**
	 * Returns the given functions which were called by the given callers. Results
	 * are only returned if they are within the given context.
	 * 
	 * @param context
	 * @param callers
	 * @param called
	 * @return
	 */
	public static Q calledBy(Q context, Q callers, Q called) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.calledBy(context, callers, called);
	}
	
	/**
	 * 
	 * @param functions
	 * @return the data flow graph under the function
	 */
	public static Q dfg(Q functions) {
		return localDeclarations(functions).nodes(XCSG.DataFlow_Node).induce(Common.edges(XCSG.DataFlow_Edge));
	}
	
	/**
	 * 
	 * @param function
	 * @return the data flow graph under the function
	 */
	public static Q dfg(Node function) {
		return dfg(Common.toQ(function));
	}
	
	/**
	 * 
	 * @param functions
	 * @return the control flow graph under the function
	 */
	public static Q cfg(Q functions) {
		return com.ensoftcorp.atlas.core.script.CommonQueries.cfg(functions);
	}
	
	/**
	 * 
	 * @param function
	 * @return the control flow graph under the function
	 */
	public static Q cfg(Node function) {
		return cfg(Common.toQ(function));
	}
	
	/**
	 * 
	 * @param functions
	 * @return the control flow graph (including exceptional control flow) under the function
	 */
	public static Q excfg(Q functions) {
		return com.ensoftcorp.atlas.core.script.CommonQueries.excfg(functions);
	}
	
	/**
	 * 
	 * @param function
	 * @return the control flow graph (including exceptional control flow) under the function
	 */
	public static Q excfg(Node function) {
		return excfg(Common.toQ(function));
	}

	/**
	 * Returns the first declaring node of the given Q which is tagged with one
	 * of the given types.
	 * 
	 * Operates in the index context.
	 * 
	 * @param declared
	 * @param declaratorTypes
	 * @return
	 */
	public static Q firstDeclarator(Q declared, String... declaratorTypes) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.firstDeclarator(declared, declaratorTypes);
	}

	/**
	 * Returns the first declaring node of the given Q which is tagged with one
	 * of the given types. Results are only returned if they are within the
	 * given context.
	 * 
	 * @param context
	 * @param declared
	 * @param declaratorTypes
	 * @return
	 */
	public static Q firstDeclarator(Q context, Q declared, String... declaratorTypes) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.firstDeclarator(context, declared, declaratorTypes);
	}

	/**
	 * Given two query expressions, intersects the given node and edge kinds to
	 * produce a new expression.
	 * 
	 * @param first
	 * @param second
	 * @param nodeTags
	 * @param edgeTags
	 * @return
	 */
	public static Q advancedIntersection(Q first, Q second, String[] nodeTags, String[] edgeTags) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.advancedIntersection(first, second, nodeTags, edgeTags);
	}

	/**
	 * Returns the nodes which directly read from nodes in origin.
	 * 
	 * Operates in the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q readersOf(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.readersOf(origin);
	}

	/**
	 * Returns the nodes which directly read from nodes in origin.
	 * 
	 * Operates in the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q readersOf(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.readersOf(context, origin);
	}

	/**
	 * Returns the nodes which directly write to nodes in origin.
	 * 
	 * Operates in the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q writersOf(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.writersOf(origin);
	}

	/**
	 * Returns the nodes which directly write to nodes in origin.
	 * 
	 * Operates in the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q writersOf(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.writersOf(context, origin);
	}

	/**
	 * Returns the nodes from which nodes in the origin read.
	 * 
	 * Operates in the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q readBy(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.readBy(origin);
	}

	/**
	 * Returns the nodes from which nodes in the origin read.
	 * 
	 * Operates in the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q readBy(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.readBy(context, origin);
	}

	/**
	 * Returns the nodes to which nodes in origin write.
	 * 
	 * Operates in the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q writtenBy(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.writtenBy(origin);
	}

	/**
	 * Returns the nodes to which nodes in origin write.
	 * 
	 * Operates in the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q writtenBy(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.writtenBy(context, origin);
	}
	
	/**
	 * Returns the least common ancestor of both child1 and child2 within the given graph
	 * 
	 * @param child1
	 * @param child2
	 * @param graph
	 * @return
	 */
	public static Node leastCommonAncestor(Node child1, Node child2, Graph graph){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.leastCommonAncestor(child1, child2, graph);
	}
	
	/**
	 * Returns the least common ancestor of both child1 and child2 within the given graph
	 * 
	 * @param child1
	 * @param child2
	 * @param graph
	 * @return
	 */
	public static Node leastCommonAncestor(Node child1, Node child2, Q graph){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.leastCommonAncestor(child1, child2, graph);
	}

	/**
	 * Returns the containing function of a given Q or empty if one is not found
	 * @param nodes
	 * @return
	 */
	public static Q getContainingFunctions(Q nodes) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getContainingFunctions(nodes);
	}
	
	/**
	 * Returns the nearest parent that is a control flow node
	 * @param node
	 * @return
	 */
	public static Node getContainingControlFlowNode(Node node) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getContainingControlFlowNode(node);
	}

	/**
	 * Returns the containing function of a given graph element or null if one is not found
	 * @param node
	 * @return
	 */
	public static Node getContainingFunction(Node node) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getContainingFunction(node);
	}
	
	/**
	 * Find the next immediate containing node with the given tag.
	 * 
	 * @param node 
	 * @param containingTag
	 * @return the next immediate containing node, or null if none exists; never returns the given node
	 */
	public static Node getContainingNode(Node node, String containingTag) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getContainingNode(node, containingTag);
	}

	/**
	 * Returns the control flow graph between conditional nodes and the given
	 * origin.
	 * 
	 * Operates within the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q conditionsAbove(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.conditionsAbove(origin);
	}

	/**
	 * Returns the control flow graph between conditional nodes and the given
	 * origin.
	 * 
	 * Operates within the given context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q conditionsAbove(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.conditionsAbove(context, origin);
	}

	/**
	 * Given a Q containing functions or data flow nodes, returns a Q of things
	 * which write to or call things in the Q.
	 * 
	 * Operates within the index context.
	 * 
	 * @param origin
	 * @return
	 */
	public static Q mutators(Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.mutators(origin);
	}

	/**
	 * Returns those nodes in the context which have self edges.
	 * 
	 * @param context
	 * @return
	 */
	public static Q nodesWithSelfEdges(Q context) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.nodesWithSelfEdges(context);
	}

	/**
	 * Given a Q containing functions or data flow nodes, returns a Q of things
	 * which write to or call things in the Q.
	 * 
	 * Operates within the index context.
	 * 
	 * @param context
	 * @param origin
	 * @return
	 */
	public static Q mutators(Q context, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.mutators(context, origin);
	}

	/**
	 * Returns those elements in the origin which were called by or written by
	 * elements in the mutators set.
	 * 
	 * Operates within the index context.
	 * 
	 * @param mutators
	 * @param origin
	 * @return
	 */
	public static Q mutatedBy(Q mutators, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.mutatedBy(mutators, origin);
	}

	/**
	 * Returns those elements in the origin which were called by or written by
	 * elements in the mutators set.
	 * 
	 * Operates within the given context.
	 * 
	 * @param context
	 * @param mutators
	 * @param origin
	 * @return
	 */
	public static Q mutatedBy(Q context, Q mutators, Q origin) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.mutatedBy(context, mutators, origin);
	}
	
	/**
	 * Helper function to get the stringified qualified name of the class
	 * @param function
	 * @return
	 */
	public static String getQualifiedTypeName(Node type) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getQualifiedTypeName(type);
	}
	
	/**
	 * Helper function to get the stringified qualified name of the function
	 * @param function
	 * @return
	 */
	public static String getQualifiedFunctionName(Node function) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getQualifiedFunctionName(function);
	}
	
	/**
	 * Helper function to get the stringified qualified name of the function
	 * @param function
	 * @return
	 */
	public static String getQualifiedName(Node node) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getQualifiedName(node);
	}
	
	/**
	 * Helper function to get the stringified qualified name of the class
	 * Stop after tags specify parent containers to stop qualifying at (example packages or jars)
	 * @param function
	 * @return
	 */
	public static String getQualifiedName(Node node, String...stopAfterTags) {
		return com.ensoftcorp.open.commons.analysis.CommonQueries.getQualifiedName(node, stopAfterTags);
	}
	
	/**
	 * Returns a filter over the edges which describe the type
	 * of a Variable or DataFlow_Node.
	 * @return
	 */
	public static Q typeEdges() {
		Q typeEdges = edges(XCSG.TypeOf, // from Variable or DataFlow_Node to a Type
				XCSG.ArrayElementType, // arrays have an ArrayElementType
				XCSG.ReferencedType, // pointers have a ReferencedType
				XCSG.AliasedType,  // typedefs have an AliasedType
				XCSG.C.CompletedBy // OpaqueTypes are connected to the corresponding Type 
				);
		return typeEdges;
	}

	/**
	 * Returns the set of functions where their names matches the any of the
	 * names given (functionNames) list. A (*) in (functionNames) represents a
	 * wildcard that matches any string.
	 * 
	 * @param functionNames:
	 *            A list of function names as Strings
	 * @return A set of functions
	 */
	public static Q functions(String... functionNames){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.functions(functionNames);
	}

	/**
	 * Returns the nodes representing the global variable(s) given by the
	 * parameter list (names). A (*) in any string in the list (names)
	 * represents a wildcard that matches any string.
	 * 
	 * @param names:
	 *            A list of global variable names as Strings
	 * @return A set of global variable nodes
	 */
	public static Q globals(String... names){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.globals(names);
	}

	/**
	 * Returns the nodes representing the types given by the names. 
	 * A (*) is a wildcard that matches any string.
	 * @param names A list of type names; structs must be prefixed with "struct "
	 * @return A set of global variable nodes
	 */
	public static Q types(String... names){
		return com.ensoftcorp.open.commons.analysis.CommonQueries.types(names);
	}
	
	/**
	 * Returns the nodes representing the structs having the given names. 
	 * <p>
	 * The name is automatically prefixed with "struct ".
	 * A (*) in name represents a wildcard that matches any string.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li> structs("iblk")
	 * <li> structs("*bl*k*")
	 * <li> structs("pentry","bpool")
	 * </ul>
	 * 
	 * @param names list of struct names
	 * @return XCSG.C.Struct nodes
	 */
	public static Q structs(String... names){
		Q ts = Common.empty();
		for(String n : names){
			Q s = findByName("struct " + n, XCSG.C.Struct);
			ts = ts.union(s);
		}
		return ts;
	}
	
	/**
	 * Returns the induced call graph of functions referencing (read/write) the
	 * given global variables and/or types (structures) given in parameter
	 * (object).
	 * 
	 * @param object:
	 *            the set of global variables and/or types
	 * @return A set of type/structure/global variable nodes
	 */
	public static Q ref(Q object){
		Q refV = refVariable(object.nodes(XCSG.GlobalVariable));
		Q refT = refType(object.nodes(XCSG.C.Struct));
		return refV.union(refT);
	}
	
	/**
	 * Returns the control-flow graph (CFG) of the given function name in (funcName)
	 * @param funcName: function name as a String
	 * @return the CFG of the given function
	 */
	public static Q cfg(String funcName){
		return cfg(functions(funcName));
	}
	
	/**
	 * Returns the call graph of the given function(s) in (funcs)
	 * @param funcs: function node(s)
	 * @return The call graph of given function(s)
	 */
	public static Q cg(Q funcs){
		return edges(XCSG.Call).forward(funcs);
	}
	
	/**
	 * Returns the reverse-call graph of the given function(s) in (funcs)
	 * @param funcs: function node(s)
	 * @return The reverse call graph of given function(s)
	 */
	public static Q rcg(Q funcs){
		return edges(XCSG.Call).reverse(funcs);
	}
	
	/**
	 * Returns the set of function directly calling a given function(s)
	 * @param functs: function node(s)
	 * @return direct callers of given function(s)
	 */
	public static Q call(Q funcs){
		return edges(XCSG.Call).predecessors(funcs);
	}
	
	/**
	 * Returns the set of functions that are directly called by given function(s)
	 * @param functs: function node(s)
	 * @return direct callees by given function(s)
	 */
	public static Q calledBy(Q funcs){
		return edges(XCSG.Call).successors(funcs);
	}
	
	/**
	 * Returns the call graph between the functions in (roots) and functions in (leaves)
	 * @param roots: function node(s)
	 * @param leaves: function node(s)
	 * @return a call graph
	 */
	public static Q graph(Q roots, Q leaves){
		 return edges(XCSG.Call).between(roots, leaves);
	}
	
	/**
	 * Induces a call edges between the given set of function(s) in (funcs)
	 * @param functs: function node(s)
	 */
	public static Q induceCG(Q functs){
		return functs.induce(edges(XCSG.Call));
	}
	
	/**
	 * Induces the control-flow edges between the given control-flow blocks in (nodes)
	 * @param nodes: control-flow node(s)
	 */
	public static Q induceCFG(Q nodes){
		return nodes.induce(edges(XCSG.ControlFlow_Edge));
	}
	
	/**
	 * Returns the Matching Pair Graph for given object and event functions
	 * @param e1Functions: L function node(s)
	 * @param e2Functions: U function node(s)
	 * @param object: type node
	 * @return the matching pair graph for object (object)
	 */
	public static Q mpg(Q e1Functions, Q e2Functions, Q object){
		Q callL = call(e1Functions);
		Q callU = call(e2Functions);
		if(object.eval().nodes().one().taggedWith(XCSG.GlobalVariable)){
			callL = callL.intersection(refVariable(object));
			callU = callU.intersection(refVariable(object));
		}else if(object.eval().nodes().one().taggedWith(XCSG.C.Struct)){
			callL = callL.intersection(refType(object));
			callU = callU.intersection(refType(object));
		}
		Q rcg_lock = edges(XCSG.Call).reverse(callL);
		Q rcg_unlock = edges(XCSG.Call).reverse(callU);
		Q rcg_both = rcg_lock.intersection(rcg_unlock);
		Q rcg_c = rcg_lock.union(rcg_unlock);
		Q rcg_lock_only = rcg_lock.difference(rcg_both);
		Q rcg_unlock_only = rcg_unlock.difference(rcg_both);
		Q call_lock_only = callL.union(edges(XCSG.Call).reverseStep(rcg_lock_only));
		Q call_unlock_only = callU.union(edges(XCSG.Call).reverseStep(rcg_unlock_only));
		Q call_c_only = call_lock_only.union(call_unlock_only);
		Q balanced = call_c_only.intersection(rcg_both);
		Q ubc = balanced.union(rcg_lock_only, rcg_unlock_only);
		Q mpg = rcg_c.intersection(edges(XCSG.Call).forward(ubc));
		mpg = mpg.union(e1Functions, e2Functions);
		mpg = mpg.induce(edges(XCSG.Call));
		return mpg;
	}
	
	/**
	 * Returns the portion of data-flow graph within the given Function
	 * 
	 * @param dfg
	 *            A previously computed data-flow graph
	 * @param func
	 *            Function node(s)
	 * @return intra-procedural data-flow graph
	 */
	public static Q projectDFG(Q dfg, Q func){
		Q functionBody = func.contained().induce(Query.universe());
		return functionBody.intersection(dfg);
	}
	
	/**
	 * Returns the intra-procedural forward data-flow graph starting at the
	 * given node within the projection
	 * 
	 * @param projection
	 *            data-flow graph
	 * @param node
	 *            where the flow starts
	 * @return a forward data-flow graph from the selected node
	 */
	public static Q forwardProjection(Q projection, Q node){
		return projection.forward(node);
	}
	
	/**
	 * Returns the forward data-flow graph starting at the return value of
	 * Function "functionSource" in the context of Function "functionContext".
	 * The flow continues until it reaches a parameter of Function
	 * "functionSink".
	 * <p>
	 * In Xinu, try: <code>
	 * dfg("dsread", "getbuf", "freebuf")
	 * </code>
	 * 
	 * @param functionContext
	 *            caller of functionSource
	 * @param functionSource
	 *            return value to use as origin in data-flow graph
	 * @param functionSink
	 *            stopping point for data-flow graph
	 */
	public static Q dfg(String functionContext, String functionSource, String functionSink) {
		// find CallSites to "functionSource" within "functionContext"
		Q qFunctionSource = functions(functionSource);
		Q callSites = edges(XCSG.InvokedFunction).predecessors(qFunctionSource);
		Q callSitesInFunction = functions(functionContext).contained().intersection(callSites);

		// take the contents of "functionSink" as the stopping point for flows
		Q functionSinkParameters = functions(functionSink).contained();
		
		AtlasSet<Node> origin = callSitesInFunction.eval().nodes();
		AtlasSet<Node> stop = functionSinkParameters.eval().nodes();
		
		Graph forwardDF = Traversal.forwardDF(origin, stop);
		
		return Common.toQ(forwardDF);
	}
	
	/**
	 * Returns Types, GlobalVariables, Functions, and Macros
	 * with source correspondence matching the given path.
	 * 
	 * Wildcards are allowed.
	 * Paths are always normalized to use / as a directory separator.
	 * Paths are always assumed to start with a wildcard (to avoid needing to 
	 * include the full project path).
	 * <p>
	 * Examples:
	 * <ul>
	 * <li> file("open.c")  -- any file ending with open.c
	 * <li> file("/open.c")  -- exactly files named open.c
	 * <li> file("/dg*.c")  -- any file starting with dg and ending with .c
	 * </ul>
	 * 
	 * @param path 
	 * @return
	 */
	public static Q file(String path) {
		
		Q entities = Common.codemap().nodes(
				XCSG.Type,
				XCSG.TypeAlias,
				XCSG.GlobalVariable,
				XCSG.Function,
				XCSG.C.Provisional.Macro);
		
		String regex = ".*" + path.replace("*", ".*");
		Pattern pattern = Pattern.compile(regex);
		
		AtlasSet<Node> matches = new AtlasHashSet<>();
		for (Node entity : entities.eval().nodes()) {
			if (scMatches(entity,pattern)) matches.add(entity);
		}
		
		return Common.toQ(matches);
	}

	/******************************************************************/
	/******************************************************************/
	/*                   Utility Functions Below                      */ 
	/******************************************************************/
	/******************************************************************/

	private static Q findByName(String functionName, String tag) {
		if(functionName.indexOf("*") >= 0){
			Q nodes = Query.universe().nodes(tag);
			Q result = getMatches(functionName, nodes);
			return result;
		}
		// Atlas has an index over literal attribute values, so it's faster to query directly
		return Query.universe().nodes(tag).selectNode(XCSG.name, functionName);
	}

	/**
	 * tests source correspondence path
	 * @param n
	 * @param pattern
	 * @return true if sourceCorrespondence file path matches
	 */
	private static boolean scMatches(Node n, Pattern pattern) {
		SourceCorrespondence sc = (SourceCorrespondence) n.getAttr(XCSG.sourceCorrespondence);
		if (sc == null) return false;
		
		IPath fullPath = sc.sourceFile.getFullPath();
		String path = fullPath.toOSString();
	
		Matcher matcher = pattern.matcher(path);
		return matcher.matches();
	}

	/**
	 * The induced call graph on the Functions which contain Variable or DataFlow_Nodes 
	 * having the given type.
	 * 
	 *  
	 * @param type
	 * @return
	 */
	private static Q refType(Q type){
		Q ref = typeEdges().reverse(type);
		ref = Common.extend(ref, XCSG.Contains);
		return ref.nodes(XCSG.Function).induce(edges(XCSG.Call));
	}
	
	/**
	 * The induced call graph on the functions which read or write the given variables.
	 * 
	 * @param variable
	 * @return the induced Call Graph
	 */
	private static Q refVariable(Q variable){
		Q read =  edges(XCSG.Reads).reverseStep(variable);
		Q write = edges(XCSG.Writes).reverseStep(variable);
		Q all = read.union(write);
		all = Common.extend(all, XCSG.Contains);
		return all.nodes(XCSG.Function).induce(edges(XCSG.Call));
	}
	
	/**
	 * Returns the Functions containing the given nodes (if any).
	 * Not all nodes are nested under a Function.
	 * 
	 * @param nodes
	 * @return Functions containing the given nodes
	 */
	public static Q getContainingFunction(Q nodes) {
		AtlasSet<Node> ns = nodes.eval().nodes();
		AtlasSet<Node> containingFunctions = new AtlasHashSet<>();
		for (Node node : ns) {
			Node function = CommonQueries.getContainingFunction(node);
			if (function != null)
				containingFunctions.add(function);
		}
		Q functions = Common.toQ(Common.toGraph(containingFunctions));
		
		return functions;
	}

	/**
	 * Returns all nodes with a name matching the wildcard expression
	 * 
	 * @param name
	 * @param nodes
	 * @return
	 */
	private static Q getMatches(String name, Q nodes){
		name = name.replace("*", ".*");
		AtlasSet<Node> allNodes = nodes.eval().nodes();
		AtlasSet<GraphElement> result = new AtlasHashSet<GraphElement>();
		
		for(GraphElement node : allNodes){
			String thisName = (String) node.getAttr(XCSG.name);
			if(thisName.matches(name)){
				result.add(node);
			}
		}
		return Common.toQ(Common.toGraph(result));
	}
	
	/**
	 * Extends the given graph through the edges describing the type of the nodes.
	 * @see CommonQueries#typeEdges()
	 * @param q
	 * @return The given graph extended along the type edges.
	 */
	public static Q typeOf(Q q) {
		return q.forwardOn(typeEdges());
	}
	
	/**
	 * Selects all functions, and one step of data flow for
	 * the capture of the address of a function.
	 * 
	 * @return
	 */
	public static Q functionPtr(){
		Q dataFlowEdges = edges(XCSG.DataFlow_Edge);
		Q functions = Common.codemap().nodes(XCSG.Function);
		return dataFlowEdges.forwardStep(functions);
	}
	
	/**
	 * Filter for edges in the code map
	 * @param tags
	 * @return
	 */
	private static Q edges(String ... tags) {
		return Common.codemap().edges(tags);
	}
	
	@SuppressWarnings("unused")
	private static class Traversal {
		
		public static Graph reverseDF(AtlasSet<Node> origin) {
			AtlasSet<Node> stop = EmptyAtlasSet.<Node>instance(Graph.U);
			return reverseDF(origin, stop);
		}
		
		public static Graph reverseDF(AtlasSet<Node> origin, AtlasSet<Node> stop) {
			Graph context = Query.universe().edges(XCSG.DataFlow_Edge).eval();
			return traverse(context, TraversalDirection.REVERSE, origin, stop);
		}
		
		public static Graph forwardDF(AtlasSet<Node> origin) {
			AtlasSet<Node> stop = EmptyAtlasSet.<Node>instance(Graph.U);
			return forwardDF(origin, stop);
		}
		
		public static Graph forwardDF(AtlasSet<Node> origin, AtlasSet<Node> stop) {
			Graph context = Query.universe().edges(XCSG.DataFlow_Edge).eval();
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
			
			AtlasHashSet<Node> nodesInGraph = new AtlasHashSet<>();
			AtlasHashSet<Edge> edgesInGraph = new AtlasHashSet<>();

			AtlasHashSet<Node> frontier = new AtlasHashSet<>();
			frontier.addAll(new IntersectionSet<Node>(graph.nodes(), origin));
			
			while (!frontier.isEmpty()) {
				Iterator<Node> itr = frontier.iterator();
				Node currentNode = itr.next();
				itr.remove();

				nodesInGraph.add(currentNode);
				
				if (stop.contains(currentNode)){
					continue;
				}
				
				AtlasSet<Edge> edges = graph.edges(currentNode, nodeDirection);

				for (Edge edge : edges) {
					edgesInGraph.add(edge);
					Node nextNode = edge.getNode(edgeDirection);
					if (!nodesInGraph.contains(nextNode)) {
						frontier.add(nextNode);
					}
				}
				
			}
			
			return new UncheckedGraph(nodesInGraph, edgesInGraph);
		}
	}
	
}