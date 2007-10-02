package org.seasar.cubby.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;

import org.apache.taglibs.standard.tag.rt.core.ImportTag;

/**
 * レイアウト継承を実現するテンプレートタグ
 * <p>
 * この機能は今後変更になる可能性があります。
 * </p>
 * 
 * @author agata
 */
public class TemplateTag extends DynamicAttributesTagSupport {

	/**
	 * 継承元のJSPパス
	 */
	private String extend;

	/**
	 * 継承元のJSPパスをセットします。
	 * 
	 * @param extend 継承元のJSPパス
	 */
	public void setExtend(final String extend) {
		this.extend = extend;
	}

	/**
	 * タグの処理。
	 * （Japserによるタグファイルのコンパイル結果から移植しています）
	 */
	@Override
	public void doTag() throws JspException, IOException {
		getJspContext().setAttribute("parts", getDynamicAttribute(),
				PageContext.REQUEST_SCOPE);
		@SuppressWarnings("unused")
		JspWriter out = getJspContext().getOut();
		final ImportTag importTag = new ImportTag();
		importTag.setPageContext(getPageContext());
		importTag.setParent(new javax.servlet.jsp.tagext.TagAdapter(
				(javax.servlet.jsp.tagext.SimpleTag) this));
		importTag.setUrl(extend);
		int[] _jspx_push_body_count_c_import_0 = new int[] { 0 };
		try {

			if (importTag.doStartTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE
					|| importTag.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
				throw new SkipPageException();
			}
		} catch (Throwable _jspx_exception) {
			while (_jspx_push_body_count_c_import_0[0]-- > 0)
				out = getJspContext().popBody();
			try {
				importTag.doCatch(_jspx_exception);
			} catch (Throwable e) {
				throw new JspException(e);
			}
		} finally {
			importTag.doFinally();
		}
	}
}
