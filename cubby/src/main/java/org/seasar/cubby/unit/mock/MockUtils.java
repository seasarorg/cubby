package org.seasar.cubby.unit.mock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.cubby.internal.util.IteratorEnumeration;

public class MockUtils {

	private MockUtils() {
	}

	private static SimpleDateFormat[] DATE_FORMATTERS = {
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy/MM/dd") };

	public static <T> Enumeration<T> toEnumeration(Iterator<T> iterator) {
		return new IteratorEnumeration<T>(iterator);
	}

	public static <T> Enumeration<T> emptyEnumeration() {
		return toEnumeration(new EmptyIterator<T>());
	}

	public static String toDate(long value) {
		return DATE_FORMATTERS[0].format(new Date(value));
	}

	public static long toDateLong(String value) {
		if (value == null) {
			return -1L;
		}
		for (int i = 0; i < DATE_FORMATTERS.length; i++) {
			try {
				return DATE_FORMATTERS[i].parse(value).getTime();
			} catch (ParseException ignore) {
			}
		}
		throw new IllegalArgumentException(value);
	}

	public static int toInt(String value) {
		if (value == null) {
			return -1;
		}
		return Integer.parseInt(value);
	}

	public static PrintWriter getResponseWriter() {

		return new PrintWriter(new StringWriter()) {
			@Override
			public String toString() {
				return out.toString();
			}
		};
	}

	public static ServletOutputStream getServletOutputStream(
			final OutputStream outputStream) {
		return new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				outputStream.write(b);
			}
		};
	}

	public static RequestDispatcher getRequestDispatcher() {
		return new RequestDispatcher() {

			public void forward(ServletRequest request, ServletResponse response)
					throws ServletException, IOException {
			}

			public void include(ServletRequest request, ServletResponse response)
					throws ServletException, IOException {
			}
		};
	}

	public static File getResourceAsFile(String path) {

		URL url = getResource(path);
		if (url == null) {
			return null;
		}
		return toFile(url);
	}

	public static boolean isExist(String path) {
		return getResource(path) != null;
	}

	public static URL getResource(String path) {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (path == null || loader == null) {
			return null;
		}
		return loader.getResource(toResourcePath(path));
	}

	public static InputStream getResourceAsStream(String path) {
		URL url = getResource(path);
		try {
			URLConnection connection = url.openConnection();
			connection.setUseCaches(false);
			return connection.getInputStream();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toResourcePath(String path) {
		return path.replaceAll(".", "/");
	}

	private static File toFile(URL url) {
		File file = new File(toFilePath(url));
		if (file != null && file.exists()) {
			return file;
		}
		return null;
	}

	private static String toFilePath(URL url) {
		String s = url.getFile();
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] value) {
		if (value == null) {
			return "";
		}
		StringBuilder buf = new StringBuilder(value.length * 2);
		for (int i = 0; i < value.length; ++i) {
			appendHex(buf, value[i]);
		}
		return buf.toString();
	}

	private static String toHex(final int value) {
		StringBuilder buf = new StringBuilder(2);
		appendHex(buf, value);
		return buf.toString();
	}

	private static void appendHex(StringBuilder buf, final int i) {
		buf.append(Character.forDigit((i & 0xf0) >> 4, 16));
		buf.append(Character.forDigit((i & 0x0f), 16));
	}

	private static final byte[] DEFAULT_ADDRESS = new byte[] { (byte) 127,
			(byte) 0, (byte) 0, (byte) 1 };

	private static SecureRandom _random = new SecureRandom();

	private static String _base = toHex(getAddress())
			+ toHex(System.identityHashCode(_random));

	/**
	 * UUIDを作成します。
	 * 
	 * @return
	 */
	public static String uuid() {
		StringBuilder buf = new StringBuilder(_base.length() * 2);
		buf.append(_base);
		int lowTime = (int) System.currentTimeMillis() >> 32;
		appendHex(buf, lowTime);
		appendHex(buf, _random.nextInt());
		return buf.toString();
	}

	private static byte[] getAddress() {
		try {
			return InetAddress.getLocalHost().getAddress();
		} catch (UnknownHostException ignore) {
			return DEFAULT_ADDRESS;
		}
	}

	private static final class EmptyIterator<T> implements Iterator<T> {

		public boolean hasNext() {
			return false;
		}

		public T next() {
			throw new NoSuchElementException();
		}

		public void remove() {
			throw new IllegalStateException();
		}
	}

}
