package org.seasar.cubby.examples.other.web.docs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Url;

public class DocsAction extends Action {

	private static final String[] EXCLUDES = { "template.html", "sidebar.html" };

	private static final String[] ENTRIES = { "/", "/css/", "/images/" };

	public ServletContext context;

	/**
	 * docs以下をZIPデータとして固める
	 */
	@Url("cubby-docs.zip")
	public ActionResult download() throws Exception {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(data);
		for (String path : ENTRIES) {
			addEntry(out, path);
		}
		out.flush();
		out.close();
		return new Direct(data.toByteArray(), "application/zip", new Date()
				.getTime());
	}

	private void addEntry(ZipOutputStream out, String path) throws IOException {
		File docsDir = new File(context.getRealPath("/docs" + path));
		for (File f : docsDir.listFiles()) {
			if (f.isFile() && !contains(EXCLUDES, f.getName())) {
				InputStream input = new FileInputStream(f);
				ZipEntry entry = new ZipEntry(f.getName());
				out.putNextEntry(entry);
				IOUtils.toByteArray(input);
				out.closeEntry();
			}
		}
	}

	private boolean contains(Object[] array, Object valueToFind) {
		for (Object o : array) {
			if (o == valueToFind) {
				return true;
			}
			if (o.equals(valueToFind)) {
				return true;
			}
		}
		return false;
	}

}
