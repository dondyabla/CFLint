package com.cflint;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cflint.config.CFLintConfig;

import cfml.parsing.reporting.ParseException;

public class TestComplexBooleanExpressionChecker {

	private CFLint cfBugs;

	@Before
	public void setUp() throws Exception{
		final com.cflint.config.CFLintConfiguration conf = CFLintConfig.createDefaultLimited("ComplexBooleanExpressionChecker");
		cfBugs = new CFLint(conf);
	}

	@Test
	public void testIfBooleanExpressionInScript() throws ParseException, IOException {
		final String scriptSrc = "<cfscript>\r\n"
			+ "if (a && b || c && d || e && f) {\r\n"
			+ "	c = 1;\r\n"
			+ "}\r\n"
			+ "else if (a or not b and not a or b and not c) {\r\n"
			+ "	c = 2;\r\n"
			+ "}\r\n"
			+ "</cfscript>";
			
		cfBugs.process(scriptSrc, "test");
		final List<BugInfo> result = cfBugs.getBugs().getBugList().values().iterator().next();
		assertEquals(2, result.size());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(0).getMessageCode());
		assertEquals(2, result.get(0).getLine());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(1).getMessageCode());
		assertEquals(5, result.get(1).getLine());
	}

	@Test
	public void testIfBooleanExpressionInTag() throws ParseException, IOException {
		final String scriptSrc = "<cfif a and b or c and d or e and f>\r\n"
			+ "<cfset c = 1>\r\n"
			+ "<cfelseif a or not b and not a or b and not c>\r\n"
			+ "<cfset c = 2>\r\n"
			+ "</cfif>\r\n";
			
		cfBugs.process(scriptSrc, "test");
		final List<BugInfo> result = cfBugs.getBugs().getBugList().values().iterator().next();
		assertEquals(2, result.size());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(0).getMessageCode());
		assertEquals(1, result.get(0).getLine());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(1).getMessageCode());
		assertEquals(3, result.get(1).getLine());
	}

	@Test
	public void testSetBooleanExpressionInTag() throws ParseException, IOException {
		final String tagSrc = "<cfset a = 23>\r\n"
			+ "<cfset a = a and not b or c and d or not e and f>";
			
		cfBugs.process(tagSrc, "test");
		final List<BugInfo> result = cfBugs.getBugs().getBugList().values().iterator().next();
		assertEquals(1, result.size());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(0).getMessageCode());
		assertEquals(2, result.get(0).getLine());
	}

	@Test
	public void testSetBooleanExpressionInScript() throws ParseException, IOException {
		final String scriptSrc = "<cfscript>\r\n"
			+ "a = 23;\r\n"
			+ "a = a && !b || c && d || !e && f;\r\n"
			+ "</cfscript>";
			
		cfBugs.process(scriptSrc, "test");
		final List<BugInfo> result = cfBugs.getBugs().getBugList().values().iterator().next();
		assertEquals(1, result.size());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(0).getMessageCode());
		assertEquals(3, result.get(0).getLine());
	}

	@Test
	public void testReurnBooleanExpressionInTag() throws ParseException, IOException {
		final String tagSrc = "<cffunction name=\"test\">\r\n"
			+ "<cfreturn a and not b or c and d or not e and f>\r\n"
			+ "</cffunction>";

		cfBugs.process(tagSrc, "test");
		final List<BugInfo> result = cfBugs.getBugs().getBugList().values().iterator().next();
		assertEquals(1, result.size());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(0).getMessageCode());
		assertEquals(2, result.get(0).getLine());
	}

	@Test
	public void testReturnBooleanExpressionInScript() throws ParseException, IOException {
		final String scriptSrc = "component {\r\n"
			+ "public function test() {\r\n"
			+ "return a && !b || c && d || !e && f;\r\n"
			+ "}\r\n"
			+ "}\r\n";
			
		cfBugs.process(scriptSrc, "test");
		final List<BugInfo> result = cfBugs.getBugs().getBugList().values().iterator().next();
		assertEquals(1, result.size());
		assertEquals("COMPLEX_BOOLEAN_CHECK", result.get(0).getMessageCode());
		assertEquals(3, result.get(0).getLine());
	}

}
