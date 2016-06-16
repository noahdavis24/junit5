/*
 * Copyright 2015-2016 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.meta;

import static de.schauderhaft.degraph.check.JCheck.classpath;
import static de.schauderhaft.degraph.check.JCheck.violationFree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import de.schauderhaft.degraph.configuration.NamedPattern;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

/**
 * {@code DependencyTests} check against dependency cycles at the package
 * and module levels.
 *
 * <p><em>Modules</em> are defined by the package name element immediately
 * following the {@code org.junit.gen5} base package. For example,
 * {@code org.junit.gen5.console.ConsoleRunner} belongs to the {@code console}
 * module.
 */
@RunWith(JUnit5.class)
public class DependencyTests {

	@Test
	void noCycles() {
		// we can't use noJar(), because with gradle the dependencies of other modules are
		// included as jar files in the path.
		//@formatter:off
		assertThat(
			classpath()
				.printTo("dependencies.graphml")
				.including("org.junit.gen5.**")
				.withSlicing("module",
					new NamedPattern("org.junit.gen5.engine.junit4.**", "junit4-engine"),
					new NamedPattern("org.junit.gen5.engine.junit5.**", "junit5-engine"),
					new NamedPattern("org.junit.gen5.engine.**", "engine-api"),
					"org.junit.gen5.(*).**"),
			is(violationFree()));
		//@formatter:on
	}

}
