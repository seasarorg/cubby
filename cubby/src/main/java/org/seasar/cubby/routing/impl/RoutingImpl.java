package org.seasar.cubby.routing.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.util.StringUtils;

/**
 * ルーティングの実装。
 * 
 * @author baba
 * @since 1.1.0
 */
class RoutingImpl implements Routing {

	/** アクションクラス。 */
	private final Class<? extends Action> actionClass;

	/** アクソンメソッド。 */
	private final Method method;

	/** アクションのパス。 */
	private final String actionPath;

	/** URI パラメータ名。 */
	private final List<String> uriParameterNames;

	/** 正規表現パターン。 */
	private final Pattern pattern;

	/** リクエストメソッド。 */
	private final RequestMethod requestMethod;

	/** このルーティングを使用することを判断するためのパラメータ名。 */
	private final String onSubmit;

	/** 優先順位。 */
	private final int priority;

	/** 自動登録されたかどうか。 */
	private final boolean auto;

	/**
	 * インスタンス化します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @param actionPath
	 *            アクションのパス
	 * @param uriParameterNames
	 *            URI パラメータ名
	 * @param pattern
	 *            正規表現パターン
	 * @param requestMethod
	 *            リクエストメソッド
	 * @param onSubmit
	 *            このルーティングを使用することを判断するためのパラメータ名
	 * @param priority
	 *            優先順位。手動登録の場合は登録順の連番。自動登録の場合は{@link Integer#MAX_VALUE}
	 *            が常にセットされます。
	 * @param auto
	 *            自動登録されたかどうか
	 */
	RoutingImpl(final Class<? extends Action> actionClass, final Method method,
			final String actionPath, final List<String> uriParameterNames,
			final Pattern pattern, final RequestMethod requestMethod,
			final String onSubmit, final int priority, final boolean auto) {
		this.actionClass = actionClass;
		this.method = method;
		this.actionPath = actionPath;
		this.uriParameterNames = uriParameterNames;
		this.pattern = pattern;
		this.requestMethod = requestMethod;
		this.onSubmit = onSubmit;
		this.priority = priority;
		this.auto = auto;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<? extends Action> getActionClass() {
		return actionClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getActionPath() {
		return actionPath;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getUriParameterNames() {
		return uriParameterNames;
	}

	/**
	 * {@inheritDoc}
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * {@inheritDoc}
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOnSubmit() {
		return onSubmit;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAuto() {
		return auto;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAcceptable(final String requestMethod) {
		return StringUtils.equalsIgnoreCase(this.requestMethod.name(),
				requestMethod);
	}

	/**
	 * このオブジェクトの文字列表現を返します。
	 * 
	 * @return このオブジェクトの正規表現
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("[regex=").append(this.pattern)
				.append(",method=").append(this.method).append(
						",uriParameterNames=").append(this.uriParameterNames)
				.append(",requestMethod=").append(this.requestMethod).append(
						",onSubmit=").append(onSubmit).append(",priority=")
				.append(this.priority).append(",auto=").append(this.auto)
				.append("]").toString();
	}
}
