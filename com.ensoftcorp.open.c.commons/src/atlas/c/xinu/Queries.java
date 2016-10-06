package atlas.c.xinu;

import static com.ensoftcorp.atlas.core.script.Common.codemap;
import static com.ensoftcorp.atlas.core.script.Common.edges;
import static com.ensoftcorp.atlas.core.script.Common.universe;

import com.ensoftcorp.atlas.core.db.graph.Graph;
import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.graph.GraphElement.EdgeDirection;
import com.ensoftcorp.atlas.core.db.graph.GraphElement.NodeDirection;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasHashSet;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.xcsg.XCSG;

public class Queries {
	
	/**
	 * Filter ignoring all edges other than XCSG.Call edges.
	 * @return
	 */
	public static Q callEdges() {
		return codemap().edgesTaggedWithAny(XCSG.Call);
	}
	
	/**
	 * Returns a filter over the edges which describe the type
	 * of a Variable or DataFlow_Node.
	 * @return
	 */
	public static Q typeEdges() {
		Q typeEdges = Common.edges(XCSG.TypeOf, // from Variable or DataFlow_Node to a Type
				XCSG.ArrayElementType, // arrays have an ArrayElementType
				XCSG.ReferencedType, // pointers have a ReferencedType
				XCSG.AliasedType,  // typedefs have an AliasedType
				XCSG.C.CompletedBy // OpaqueTypes are connected to the corresponding Type 
				);
		return typeEdges;
	}
	
	/**
	 * Returns the set of functions where their names match the given (functionName). A (*) in (functionName) represents a wildcard that matches any string.
	 * @param functionName: The function name as a String
	 * @return A set of functions
	 */
	public static Q function(String functionName) {
		return findByName(functionName, XCSG.Function); 
	}

	/**
	 * Returns the set of functions where their names matches the any of the names given (functionNames) list. A (*) in (functionNames) represents a wildcard that matches any string.
	 * @param functionNames: A list of function names as Strings
	 * @return A set of functions
	 */
	public static Q functions(String... functionNames){
		Q functions = Common.empty();
		for(String n : functionNames){
			functions = functions.union(function(n));
		}
		return functions;
	}

	/**
	 * Returns the nodes representing the global variables given by the parameter (name). A (*) in (name) represents a wildcard that matches any string.
	 * @param name: The global variable name as a String
	 * @return A set of global variable nodes
	 */
	public static Q global(String name){
		return findByName(name, XCSG.GlobalVariable);
	}
	
	/**
	 * Returns the nodes representing the globa variable(s) given by the parameter list (names). A (*) in any string in the list (names) represents a wildcard that matches any string.
	 * @param names: A list of global variable names as Strings
	 * @return A set of global variable nodes
	 */
	public static Q globals(String... names){
		Q ts = Common.empty();
		for(String n : names){
			ts = ts.union(global(n));
		}
		return ts;
	}
	
	/**
	 * Returns the nodes representing the Struct given by the parameter (name). A (*) in (name) represents a wildcard that matches any string.
	 * 
	 * @param name the struct name, without the prefix "struct "
	 * @return XCSG.C.Struct nodes
	 */
	public static Q Type(String name){
		return findByName("struct " + name, XCSG.C.Struct);
	}
	
	/**
	 * Returns the nodes representing the Structs having the given names. A (*) in any string in the list (names) represents a wildcard that matches any string.
	 * 
	 * @see #type(String)
	 * @param names list of Struct names
	 * @return XCSG.C.Struct nodes
	 */
	public static Q Types(String... names){
		Q ts = Common.empty();
		for(String n : names){
			ts = ts.union(Type(n));
		}
		return ts;
	}
	
	/**
	 * Returns the induced call graph of functions referencing (read/write) the given global variables and/or types (structures) given in parameter (object).
	 * @param object: the set of global variables and/or types
	 * @return A set of type/structure/global variable nodes
	 */
	public static Q ref(Q object){
		Q refV = refVariable(object.nodesTaggedWithAny(XCSG.GlobalVariable));
		Q refT = refType(object.nodesTaggedWithAny(XCSG.C.Struct));
		return refV.union(refT);
	}
	
	/**
	 * Returns the control-flow graph (CFG) of the given function name in (funcName)
	 * @param funcName: function name as a String
	 * @return the CFG of the given function
	 */
	public static Q cfg(String funcName){
		return cfg(function(funcName));
	}
	
	/**
	 * Returns the control-flow graph (CFG) of the given function node in (funcs)
	 * @param funcs: function node(s)
	 * @return the CFG of the given function(s)
	 */
	public static Q cfg(Q funcs){
		return funcs.contained().nodesTaggedWithAny(XCSG.ControlFlow_Node).induce(edges(XCSG.ControlFlow_Edge));
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
		if(object.eval().nodes().getFirst().tags().contains(XCSG.GlobalVariable)){
			callL = callL.intersection(refVariable(object));
			callU = callU.intersection(refVariable(object));
		}else if(object.eval().nodes().getFirst().tags().contains(XCSG.C.Struct)){
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
		return func.contained().induce(universe()).intersection(dfg);
	}
	
	/**
	 * Returns the intra-procedural forward data-flow graph starting at the given node
	 * @param node where the forward slice starts
	 * @return a forward data-flow graph from the selected node
	 */
	public static Q forwardSlice(Q node){
		return edges(XCSG.LocalDataFlow).forward(node);
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
		Q qFunctionSource = function(functionSource);
		Q callSites = edges(XCSG.InvokedFunction).predecessors(qFunctionSource);
		Q callSitesInFunction = function(functionContext).contained().intersection(callSites);

		// take the contents of "functionSink" as the stopping point for flows
		Q functionSinkParameters = function(functionSink).contained();
		
		AtlasSet<Node> origin = callSitesInFunction.eval().nodes();
		AtlasSet<Node> stop = functionSinkParameters.eval().nodes();
		
		Graph forwardDF = Traversal.forwardDF(origin, stop);
		
		return Common.toQ(forwardDF);
	}
	

	/******************************************************************/
	/******************************************************************/
	/*                   Utility Functions Below                      */ 
	/******************************************************************/
	/******************************************************************/
	
	private static Q findByName(String functionName, String tag) {
		if(functionName.indexOf("*") >= 0){
			Q nodes = universe().nodesTaggedWithAll(tag);
			Q result = getMatches(functionName, nodes);
			return result;
		}
		// Atlas has an index over literal attribute values, so it's faster to query directly
		return universe().nodesTaggedWithAll(tag).selectNode(XCSG.name, functionName);
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
		return ref.nodesTaggedWithAll(XCSG.Function).induce(callEdges());
	}
	
	/**
	 * The induced call graph on the functions which read or write the given variables.
	 * 
	 * @param variable
	 * @return the induced Call Graph
	 */
	private static Q refVariable(Q variable){
		Q read =  universe().edgesTaggedWithAny(XCSG.Reads).reverseStep(variable);
		Q write = universe().edgesTaggedWithAll(XCSG.Writes).reverseStep(variable);
		Q all = read.union(write);
		all = Common.extend(all, XCSG.Contains);
		return all.nodesTaggedWithAll(XCSG.Function).induce(callEdges());
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
		AtlasSet<GraphElement> containingFunctions = new AtlasHashSet<GraphElement>();
		for (GraphElement node : ns) {
			GraphElement function = getContainingFunction(node);
			if (function != null)
				containingFunctions.add(function);
		}
		Q functions = Common.toQ(Common.toGraph(containingFunctions));
		
		return functions;
	}
	
	/**
	 * Returns the containing function of a given graph element or null if one is not found
	 * @param ge
	 * @return
	 */
	public static GraphElement getContainingFunction(GraphElement ge) {
		// NOTE: the enclosing function may be two steps or more above
		Graph contains = Common.universe().edgesTaggedWithAll(XCSG.Contains).eval();
		while (ge != null) {
			AtlasSet<GraphElement> containsEdges = contains.edges(ge, NodeDirection.IN);
			if (!containsEdges.isEmpty()) {
				GraphElement parent = containsEdges.getFirst().getNode(EdgeDirection.FROM);
				if (parent.taggedWith(XCSG.Function))
					return parent;
				ge = parent;
			} else {
				ge = null;
			}
		}
		return null;
	}

	private static Q getMatches(String name, Q nodes){
		name = name.replace("*", ".*");
		AtlasSet<Node> allFunctions = nodes.eval().nodes();
		AtlasSet<GraphElement> result = new AtlasHashSet<GraphElement>();
		
		for(GraphElement fun : allFunctions){
			String n = (String) fun.getAttr(XCSG.name);
			if(n.matches(name)){
				result.add(fun);
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
	
	public static Q functionPtr(){
		Q dataFlowEdges = universe().edgesTaggedWithAll(XCSG.DataFlow_Edge);
		Q functions = functions("*");
		return dataFlowEdges.forwardStep(functions);
	}
	
	/**
	 * Returns ReturnValue nodes for the given functions
	 * @param functions
	 * @return The call site nodes
	 */
	public static Q functionReturn(Q functions) {
		return edges(XCSG.Contains).forwardStep(functions).nodesTaggedWithAll(XCSG.ReturnValue);
	}
}