package com.ensoftcorp.open.c.commons;

import static com.ensoftcorp.atlas.core.script.Common.codemap;
import static com.ensoftcorp.atlas.core.script.Common.universe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IPath;

import com.ensoftcorp.atlas.core.db.graph.Graph;
import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasHashSet;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.index.common.SourceCorrespondence;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.open.commons.analysis.StandardQueries;
import com.ensoftcorp.atlas.core.script.Common;

public class Queries {
	
	/**
	 * Filter for edges in the code map
	 * @param tags
	 * @return
	 */
	private static Q edges(String ... tags) {
		return Common.codemap().edges(tags);
	}
	
	/**
	 * Filter ignoring all edges other than XCSG.Call edges.
	 * @return
	 */
	public static Q callEdges() {
		return edges(XCSG.Call);
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
	 * Returns the set of functions where their names matches the any of the names given (functionNames) list. A (*) in (functionNames) represents a wildcard that matches any string.
	 * @param functionNames: A list of function names as Strings
	 * @return A set of functions
	 */
	public static Q functions(String... functionNames){
		return find(XCSG.Function, functionNames);
	}

	/**
	 * Returns the nodes representing the global variable(s) given by the parameter list (names). A (*) in any string in the list (names) represents a wildcard that matches any string.
	 * @param names: A list of global variable names as Strings
	 * @return A set of global variable nodes
	 */
	public static Q globals(String... names){
		return find(XCSG.GlobalVariable, names);
	}

	/**
	 * Returns the nodes representing the types given by the names. 
	 * A (*) is a wildcard that matches any string.
	 * @param names A list of type names; structs must be prefixed with "struct "
	 * @return A set of global variable nodes
	 */
	public static Q types(String... names){
		return find(XCSG.Type, names);
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
	 * Returns the induced call graph of functions referencing (read/write) the given global variables and/or types (structures) given in parameter (object).
	 * @param object: the set of global variables and/or types
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
	 * Returns the control-flow graph (CFG) of the given function node in (funcs)
	 * @param funcs: function node(s)
	 * @return the CFG of the given function(s)
	 */
	public static Q cfg(Q funcs){
		return funcs.contained().nodes(XCSG.ControlFlow_Node).induce(edges(XCSG.ControlFlow_Edge));
	}
	
	/**
	 * Returns the call graph of the given function(s) in (funcs)
	 * @param funcs: function node(s)
	 * @return The call graph of given function(s)
	 */
	public static Q cg(Q funcs){
		return callEdges().forward(funcs);
	}
	
	/**
	 * Returns the reverse-call graph of the given function(s) in (funcs)
	 * @param funcs: function node(s)
	 * @return The reverse call graph of given function(s)
	 */
	public static Q rcg(Q funcs){
		return callEdges().reverse(funcs);
	}
	
	/**
	 * Returns the set of function directly calling a given function(s)
	 * @param functs: function node(s)
	 * @return direct callers of given function(s)
	 */
	public static Q call(Q funcs){
		return callEdges().predecessors(funcs);
	}
	
	/**
	 * Returns the set of functions that are directly called by given function(s)
	 * @param functs: function node(s)
	 * @return direct callees by given function(s)
	 */
	public static Q calledby(Q funcs){
		return callEdges().successors(funcs);
	}
	
	/**
	 * Returns the call graph between the functions in (roots) and functions in (leaves)
	 * @param roots: function node(s)
	 * @param leaves: function node(s)
	 * @return a call graph
	 */
	public static Q graph(Q roots, Q leaves){
		 return callEdges().between(roots, leaves);
	}
	
	/**
	 * Induces a call edges between the given set of function(s) in (funcs)
	 * @param functs: function node(s)
	 */
	public static Q induce(Q functs){
		return functs.induce(callEdges());
	}
	
	/**
	 * Induces the control-flow edges between the given control-flow blocks in (nodes)
	 * @param nodes: control-flow node(s)
	 */
	public static Q inducecfg(Q nodes){
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
		Q rcg_lock = callEdges().reverse(callL);
		Q rcg_unlock = callEdges().reverse(callU);
		Q rcg_both = rcg_lock.intersection(rcg_unlock);
		Q rcg_c = rcg_lock.union(rcg_unlock);
		Q rcg_lock_only = rcg_lock.difference(rcg_both);
		Q rcg_unlock_only = rcg_unlock.difference(rcg_both);
		Q call_lock_only = callL.union(callEdges().reverseStep(rcg_lock_only));
		Q call_unlock_only = callU.union(callEdges().reverseStep(rcg_unlock_only));
		Q call_c_only = call_lock_only.union(call_unlock_only);
		Q balanced = call_c_only.intersection(rcg_both);
		Q ubc = balanced.union(rcg_lock_only, rcg_unlock_only);
		Q mpg = rcg_c.intersection(callEdges().forward(ubc));
		mpg = mpg.union(e1Functions, e2Functions);
		mpg = mpg.induce(callEdges());
		return mpg;
	}
	
	/**
	 * Returns the portion of data-flow graph within the given Function
	 * @param dfg A previously computed data-flow graph
	 * @param func Function node(s)
	 * @return intra-procedural data-flow graph
	 */
	public static Q projectdfg(Q dfg, Q func){
		Q functionBody = func.contained().induce(universe());
		return functionBody.intersection(dfg);
	}
	
	/**
	 * Returns the intra-procedural forward data-flow graph starting at the given node
	 * within the projection
	 * @param projection data-flow graph
	 * @param node where the flow starts
	 * @return a forward data-flow graph from the selected node
	 */
	public static Q forwardProjection(Q projection, Q node){
		return projection.forward(node);
	}
	
	/**
	 * Returns the forward data-flow graph starting at the 
	 * return value of Function "functionSource" in the context of 
	 * Function "functionContext".  The flow continues until it reaches a parameter
	 * of Function "functionSink".
	 * <p>
	 * In Xinu, try:
	 * <code>
	 * dfg("dsread", "getbuf", "freebuf")
	 * </code>
	 * 
	 * @param functionContext caller of functionSource
	 * @param functionSource return value to use as origin in data-flow graph
	 * @param functionSink stopping point for data-flow graph
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
	
	private static Q find(String tag, String... names) {
		Q ts = Common.empty();
		for(String n : names){
			ts = ts.union(findByName(n, tag));
		}
		return ts;
	}

	private static Q findByName(String functionName, String tag) {
		if(functionName.indexOf("*") >= 0){
			Q nodes = codemap().nodes(tag);
			Q result = getMatches(functionName, nodes);
			return result;
		}
		// Atlas has an index over literal attribute values, so it's faster to query directly
		return codemap().nodes(tag).selectNode(XCSG.name, functionName);
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
		return ref.nodes(XCSG.Function).induce(callEdges());
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
		return all.nodes(XCSG.Function).induce(callEdges());
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
			Node function = StandardQueries.getContainingFunction(node);
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
	 * @see Queries#typeEdges()
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
	 * Returns ReturnValue nodes for the given functions
	 * @param functions
	 * @return The call site nodes
	 */
	public static Q functionReturn(Q functions) {
		return functions.children().nodes(XCSG.ReturnValue);
	}
}