package com.ensoftcorp.open.c.commons.analysis;

import com.ensoftcorp.atlas.core.query.Q;

/**
 * Common set definitions which are useful for program analysis
 * 
 * @author Ben Holland
 */
public final class SetDefinitions {

	// hide constructor
	private SetDefinitions() {}

	/**
	 * Types which represent arrays of other types
	 * 
	 * NOTE: These nodes are NOT declared by anything. They are outside of any
	 * project.
	 */
	public static Q arrayTypes() {
		return com.ensoftcorp.open.commons.analysis.SetDefinitions.arrayTypes();
	}

	/**
	 * Types which represent language primitive types
	 * 
	 * NOTE: These nodes are NOT declared by anything. They are outside of any
	 * project.
	 */
	public static Q primitiveTypes() {
		return com.ensoftcorp.open.commons.analysis.SetDefinitions.primitiveTypes();
	}

	/**
	 * Everything declared under any of the known API projects, if they are in
	 * the index.
	 */
	public static Q libraries() {
		return com.ensoftcorp.open.commons.analysis.SetDefinitions.libraries();
	}
	
	/**
	 * Everything in the universe which is part of the app (not part of the
	 * libraries, or any "floating" nodes).
	 */
	public static Q app() {
		return com.ensoftcorp.open.commons.analysis.SetDefinitions.app();
	}

}