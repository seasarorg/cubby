package org.seasar.cubby.cubbitter.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.service.AccountService;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationPhase;
import org.seasar.framework.util.StringUtil;

public class AbstractAction extends Action {

	public AccountService accountService;

	public HttpServletRequest request;

	public HttpServletResponse response;

	public Account loginAccount;

	@RequestParameter
	public String r;

	@Override
	public void initialize() {
		this.loginAccount = accountService.getLoginAccount();
	}

	public void prerender() {
		String contextPath = (String) request
				.getAttribute("javax.servlet.forward.context_path");
		String requestURI = (String) request
				.getAttribute("javax.servlet.forward.request_uri");
		String queryString = (String) request
				.getAttribute("javax.servlet.forward.query_string");
		StringBuilder builder = new StringBuilder();
		builder.append(requestURI.substring(contextPath.length()));
		if (!StringUtil.isEmpty(queryString)) {
			builder.append('?');
			builder.append(queryString);
		}
		try {
			this.r = URLEncoder.encode(builder.toString(), request
					.getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	protected ActionResult restore() {
		if (StringUtil.isEmpty(r)) {
			throw new IllegalArgumentException(
					"Request parameter 'redirect' is empty");
		}
		try {
			String path = URLDecoder.decode(r, request.getCharacterEncoding());
			return new Redirect(path);
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	protected static abstract class AbstractValidationRules extends
			DefaultValidationRules {

		public static final ValidationPhase RESOURCE = new ValidationPhase();

		public AbstractValidationRules() {
			super();
		}

		public AbstractValidationRules(String resourceKeyPrefix) {
			super(resourceKeyPrefix);
		}

		private static final List<ValidationPhase> VALIDATION_PHASES = Arrays
				.asList(new ValidationPhase[] { RESOURCE, DATA_TYPE,
						DATA_CONSTRAINT });

		@Override
		public List<ValidationPhase> getValidationPhases() {
			return VALIDATION_PHASES;
		}

	}

	protected void notice(String message) {
		flash.put("notice", message);
	}

}