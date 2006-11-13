package org.seasar.cubby.config.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class RegexTest extends TestCase{

	public void testConvertUri() throws Exception {
		Pattern pattern = Pattern.compile("([/]([a-z]+)[/]todo[/]([0-9]+))");
		Matcher m = pattern.matcher("/agata/todo/100");
		String result = m.replaceAll("/todo/show?id=$3&name=$2");
		assertEquals("/todo/show?id=100&name=agata", result);
	}

}
