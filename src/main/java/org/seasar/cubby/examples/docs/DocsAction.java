package org.seasar.cubby.examples.docs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.ArrayUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Url;

public class DocsAction extends Action {
	
	private static final String[] EXCLUDES = {"template.html", "sidebar.html"};
	private static final String[] ENTRIES = {"/", "/css/", "/images/"};
	
	private HttpServletRequest request;
	public void setRequset(HttpServletRequest request) {
		this.request = request;
	}

	private ServletContext context;
	public void setContext(ServletContext context) {
		this.context = context;
	}
	
	/**
	 * localhost:8080/cubby-examples/docs/index.html以下をクロールして、
	 * 結果をZIPデータとして固める
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
		return new Direct(data.toByteArray(), "application/zip", new Date().getTime());
	}

	private void addEntry(ZipOutputStream out, String path) throws IOException, HttpException {
	    HttpClient client = new HttpClient();
		File docsDir = new File(context.getRealPath("/docs" + path));
		String baseUri = "http://localhost:8080" + request.getContextPath() + "/docs" + path;
		for (File f : docsDir.listFiles()) {
			if (f.isFile() && !ArrayUtils.contains(EXCLUDES, f.getName())) {
				String uri = baseUri + f.getName();
			    GetMethod method = new GetMethod(uri);
			    method.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
			    client.executeMethod(method);
				ZipEntry entry = new ZipEntry(path + f.getName());
				out.putNextEntry(entry);
				out.write(method.getResponseBody());
				out.closeEntry();
			}
		}
	}
}
