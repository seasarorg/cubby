package org.seasar.cubby.controller.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import junit.framework.TestCase;

public class ActionFactoryImplTest extends TestCase {

	public void testUriPattern() throws Exception {
		Pattern pattern = Pattern.compile("([{]([^}]+)[}])([^{]*)");
		String src = "/{name}/todo/{todoId}";
		Matcher m = pattern.matcher(src);
		while (m.find()) {
			System.out.println(m.group(2));
			src = StringUtils.replace(src, m.group(1), "[a-zA-Z0-9]+");
		}
		System.out.println(src);
	}
}
