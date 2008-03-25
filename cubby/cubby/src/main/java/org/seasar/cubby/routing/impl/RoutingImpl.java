package org.seasar.cubby.routing.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.routing.Routing;
import org.seasar.framework.util.StringUtil;

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
	private final RequestMethod[] requestMethods;

	/** 自動登録されたかどうか */
	private final boolean auto;

	/** 優先順位 */
	private final int priority;

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
	 * @param requestMethods
	 *            リクエストメソッド
	 * @param auto
	 *            自動登録されたかどうか
	 * @param priority
	 *            優先順位。手動登録の場合は登録順の連番。自動登録の場合は{@link Integer#MAX_VALUE}が常にセットされます。
	 */
	RoutingImpl(final Class<? extends Action> actionClass, final Method method,
			final String actionPath, final List<String> uriParameterNames,
			final Pattern pattern, final RequestMethod[] requestMethods,
			final boolean auto, final int priority) {
		this.actionClass = actionClass;
		this.method = method;
		this.actionPath = actionPath;
		this.uriParameterNames = uriParameterNames;
		this.pattern = pattern;
		this.requestMethods = requestMethods;
		this.auto = auto;
		this.priority = priority;
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
	public RequestMethod[] getRequestMethods() {
		return requestMethods;
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
	public int getPriority() {
		return this.priority;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAcceptable(final String requestMethod) {
		for (final RequestMethod acceptableRequestMethod : requestMethods) {
			if (StringUtil.equalsIgnoreCase(acceptableRequestMethod.name(),
					requestMethod)) {
				return true;
			}
		}
		return false;
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
				.append(",requestMethods=").append(
						Arrays.deepToString(this.requestMethods)).append(
						",auto=").append(this.auto).append(",priority=")
				.append(this.priority).append("]").toString();
	}
}
