package org.seasar.cubby.examples.docs;

import java.io.File;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Url;

public class DocsAction extends Action {

	private HttpServletRequest request;
	public void setRequset(HttpServletRequest request) {
		this.request = request;
	}
	private ServletContext context;
	public void setContext(ServletContext context) {
		this.context = context;
	}
	@Url("cubby-docs.txt")
	public ActionResult download() {
		System.out.println("request=" + request);
		System.out.println("context=" + context);
		File docsDir = new File(context.getRealPath("/docs"));
		System.out.println("docsDir=" + docsDir);
		// localhost:8080/cubby-examples/docs/index.html以下をクロールして、
		// 結果をZIPデータとして固める
		byte[] data = new byte[] {65};
		return new Direct(data, "", new Date().getTime());
	}
}
