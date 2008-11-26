package org.seasar.cubby.plugins.guice.unit;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;

/**
 * {@link ServletModule} をエミュレートします
 * 
 * @author someda
 */
public class MockServletModule extends AbstractModule {

	static final ThreadLocal<Context> localContext = new ThreadLocal<Context>();

	public static final void setUpContext(HttpServletRequest request,
			HttpServletResponse response) {
		localContext.set(new Context(request, response));
	}

	@Override
	protected void configure() {
		// Scopes.
		bindScope(RequestScoped.class, MockServletScopes.REQUEST);
		bindScope(SessionScoped.class, MockServletScopes.SESSION);

		// Bind request.
		Provider<HttpServletRequest> requestProvider = new Provider<HttpServletRequest>() {
			public HttpServletRequest get() {
				return getRequest();
			}

			public String toString() {
				return "RequestProvider";
			}
		};
		bind(HttpServletRequest.class).toProvider(requestProvider);
		bind(ServletRequest.class).toProvider(requestProvider);

		// Bind response.
		Provider<HttpServletResponse> responseProvider = new Provider<HttpServletResponse>() {
			public HttpServletResponse get() {
				return getResponse();
			}

			public String toString() {
				return "ResponseProvider";
			}
		};
		bind(HttpServletResponse.class).toProvider(responseProvider);
		bind(ServletResponse.class).toProvider(responseProvider);

		// Bind session.
		bind(HttpSession.class).toProvider(new Provider<HttpSession>() {
			public HttpSession get() {
				return getRequest().getSession();
			}

			public String toString() {
				return "SessionProvider";
			}
		});

		// Bind request parameters.
		bind(new TypeLiteral<Map<String, String[]>>() {
		}).annotatedWith(RequestParameters.class).toProvider(
				new Provider<Map<String, String[]>>() {
					@SuppressWarnings( { "unchecked" })
					public Map<String, String[]> get() {
						return getRequest().getParameterMap();
					}

					public String toString() {
						return "RequestParametersProvider";
					}
				});
	}

	static HttpServletRequest getRequest() {
		return getContext().getRequest();
	}

	static HttpServletResponse getResponse() {
		return getContext().getResponse();
	}

	private static Context getContext() {
		Context context = localContext.get();
		if (context == null) {
			throw new RuntimeException(
					"context not found. invoke setUpContext first.");
		}
		return context;
	}

	static class Context {

		final HttpServletRequest request;
		final HttpServletResponse response;

		Context(HttpServletRequest request, HttpServletResponse response) {
			this.request = request;
			this.response = response;
		}

		HttpServletRequest getRequest() {
			return request;
		}

		HttpServletResponse getResponse() {
			return response;
		}
	}

	static class MockServletScopes {

		private MockServletScopes() {
		}

		/**
		 * HTTP servlet request scope.
		 */
		public static final Scope REQUEST = new Scope() {
			public <T> Provider<T> scope(Key<T> key, final Provider<T> creator) {
				final String name = key.toString();
				return new Provider<T>() {
					public T get() {
						HttpServletRequest request = getRequest();
						synchronized (request) {
							@SuppressWarnings("unchecked")
							T t = (T) request.getAttribute(name);
							if (t == null) {
								t = creator.get();
								request.setAttribute(name, t);
							}
							return t;
						}
					}
				};
			}

			public String toString() {
				return "ServletScopes.REQUEST";
			}
		};

		/**
		 * HTTP session scope.
		 */
		public static final Scope SESSION = new Scope() {
			public <T> Provider<T> scope(Key<T> key, final Provider<T> creator) {
				final String name = key.toString();
				return new Provider<T>() {
					public T get() {
						HttpSession session = getRequest().getSession();
						synchronized (session) {
							@SuppressWarnings("unchecked")
							T t = (T) session.getAttribute(name);
							if (t == null) {
								t = creator.get();
								session.setAttribute(name, t);
							}
							return t;
						}
					}
				};
			}

			public String toString() {
				return "ServletScopes.SESSION";
			}
		};
	}

}
