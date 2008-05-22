package org.seasar.cubby.snippets;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.seasar.framework.util.StringUtil;

public class Main {
	public static void main(String[] args) throws Exception {
		String template = "insert into SNIPPET values (%d, 1, '%s', '%s', now(), now());\n";
		Pattern titlePatten = Pattern.compile("(?m).+snippet.+");
		StringBuilder sb = new StringBuilder();
		String path = "/Users/agata/Workspaces/cubby/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets";
		Collection<File> files = FileUtils.listFiles(new File(path), new String[]{"java"}, false);
		int id = 10000;
		for (File f : files) {
			String content = FileUtils.readFileToString(f);
			//Matcher m = titlePatten.matcher(content);
			String title = "[SWT]Snippet" + f.getName();
			sb.append(String.format(template, id, title, StringUtil.replace(content, "'", "")));
			id++;
		}
		System.out.println(sb);
		FileUtils.writeStringToFile(new File("/Users/agata/Workspaces/cubby/snippets/src/main/resources/insert_snippet.sql"), sb.toString());
	}
}
