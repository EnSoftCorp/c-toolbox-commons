package com.ensoftcorp.open.c.commons.analysis;

import java.util.HashSet;
import java.util.Set;

import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasHashSet;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.open.commons.analysis.CallSiteAnalysis.LanguageSpecificCallSiteAnalysis;

public class CallSiteAnalysis extends LanguageSpecificCallSiteAnalysis {

	// constructor must be visible to contribute as language specific analysis extension point
	public CallSiteAnalysis(){}
	
	/**
	 * Given call sites, return the invoked functions
	 * @param callsite
	 * @return
	 */
	public static Q getInvokedFunction(Q callsites) {
		return Common.toQ(getInvokedFunction(callsites.nodes(XCSG.CallSite).eval().nodes()));
	}
	
	/**
	 * Given a callsite, return the invoked function
	 * @param callsite
	 * @return
	 */
	public static AtlasSet<Node> getInvokedFunction(AtlasSet<Node> callsites) {
		AtlasSet<Node> functions = new AtlasHashSet<Node>();
		for(Node callsite : callsites){
			functions.add(getInvokedFunction(callsites));
		}
		return functions;
	}
	
	/**
	 * Given call sites, returns the invoked function
	 * @param callsites
	 * @return
	 */
	public static AtlasSet<Node> getInvokedFunction(Node callsite) {
		return Common.universe().edgesTaggedWithAny(XCSG.InvokedFunction).successors(Common.toQ(callsite)).eval().nodes();
	}
	
	/**
	 * Given functions, returns function invocations
	 * @param callsite
	 * @return
	 */
	public static Q getFunctionInvocations(Q functions) {
		return Common.toQ(getFunctionInvocations(functions.nodes(XCSG.Function).eval().nodes()));
	}

	/**
	 * Given a function, return the function invocations
	 * @param callsite
	 * @return
	 */
	public static AtlasSet<Node> getFunctionInvocations(AtlasSet<Node> functions) {
		AtlasSet<Node> invocations = new AtlasHashSet<Node>();
		for(Node function : functions){
			invocations.add(getFunctionInvocations(functions));
		}
		return invocations;
	}

	/**
	 * Given a function, return the function invocations
	 * @param callsites
	 * @return
	 */
	public static AtlasSet<Node> getFunctionInvocations(Node function) {
		return Common.universe().edgesTaggedWithAny(XCSG.InvokedFunction).predecessors(Common.toQ(function)).eval().nodes();
	}

	@Override
	public AtlasSet<Node> getTargets(Node callSite) {
		return getInvokedFunction(callSite);
	}

	@Override
	public AtlasSet<Node> getCallSites(Node function) {
		return getFunctionInvocations(function);
	}

	@Override
	public String getName() {
		return "C Call Site Analysis";
	}

	@Override
	public String getDescription() {
		return "Resolves potential method targets for call sites and vice versa.";
	}

	@Override
	public Set<String> getSupportedLanguages() {
		HashSet<String> languages = new HashSet<String>();
		languages.add(XCSG.Language.C);
		return languages;
	}
}
