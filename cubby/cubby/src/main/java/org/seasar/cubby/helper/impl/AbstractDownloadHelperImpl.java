package org.seasar.cubby.helper.impl;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.helper.DownloadHelper;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.StringUtil;

abstract class AbstractDownloadHelperImpl implements DownloadHelper {

	private boolean autoDownload = false;

	private String contentType = null;

	private String filename = null;

	private Long lastModified = null;

	private String defaultCharset = "UTF-8";

	private String ieCharset = "SJIS";

	private static boolean isIE(final HttpServletRequest request) {
		final String userAgent = request.getHeader("User-Agent");
		if (userAgent == null) {
			return false;
		}
		return userAgent.indexOf("MSIE") != -1 && userAgent.indexOf("Opera") == -1;
	}

	private static boolean isSafari(final HttpServletRequest request) {
		final String userAgent = request.getHeader("User-Agent");
		if (userAgent == null) {
			return false;
		}
		return userAgent.indexOf("Safari") != -1;
	}

	public boolean isAutoDownload() {
		return autoDownload;
	}

	public void setAutoDownload(final boolean autoDownload) {
		this.autoDownload = autoDownload;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(final String fileName) {
		this.filename = fileName;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(final Long lastModified) {
		this.lastModified = lastModified;
	}

	public void setupHeader(final HttpServletRequest request,
			final HttpServletResponse response)
			throws UnsupportedEncodingException {

		final String charset = isIE(request) ? ieCharset
				: defaultCharset;

		setupContentType(request, response, charset);
		setupContentDisposition(request, response, charset);

		if (this.lastModified != null) {
			response.addDateHeader("Last-Modified", this.lastModified);
		}
	}

	private void setupContentType(final HttpServletRequest request,
			final HttpServletResponse response, final String charset) {
		ContentTypeHelper helper = new ContentTypeHelper(charset);
		helper.setContentType(contentType);
		helper.setupResponseHeader(request, response);
	}

	private void setupContentDisposition(final HttpServletRequest request,
			final HttpServletResponse response, final String charset) {
		final ContentDispositionHelper helper = new ContentDispositionHelper(
				charset);
		if (this.autoDownload) {
			helper.setDispositionTypeToInline();
		} else {
			helper.setDispositionTypeToAttachment();
		}
		helper.setFilename(this.filename);
		helper.setupResponseHeader(request, response);
	}

	class ContentTypeHelper {

		private final String charset;

		private String contentType = null;

		public ContentTypeHelper(final String charset) {
			this.charset = charset;
		}

		public void setContentType(final String contentType) {
			this.contentType  = contentType;
		}

		public void setupResponseHeader(final HttpServletRequest request,
				final HttpServletResponse response) {
			StringBuilder builder = new StringBuilder(50);
			final String contentType;
			if (StringUtil.isEmpty(this.contentType)) {
				contentType = "application/octet-stream";
			} else {
				contentType = this.contentType;
			}
			builder.append(contentType);

			if (contentType.indexOf("charset") == -1) {
				builder.append("; charset=\"");
				builder.append(charset);
				builder.append("\"");
			}

			response.setContentType(builder.toString());
		}
	}

	static class ContentDispositionHelper {

		private static final String ATTACHMENT = "attachment";

		private static final String INLINE = "inline";
	
		private final String charset;

		private String dispositionType = null;

		private String filename = null;

		public ContentDispositionHelper(final String charset) {
			this.charset = charset;
		}

		public void setDispositionTypeToAttachment() {
			this.dispositionType = ATTACHMENT;
		}

		public void setDispositionTypeToInline() {
			this.dispositionType = INLINE;
		}

		public void setFilename(final String filename) {
			this.filename = filename;
		}

		public void setupResponseHeader(final HttpServletRequest request,
				final HttpServletResponse response) {
			final StringBuilder builder = new StringBuilder(50);
			builder.append(this.dispositionType);

			if (filename != null && !isSafari(request)) {
				builder.append("; ");
				builder.append("filename=\"");
				builder.append(encodeFilename(this.filename, this.charset));
				builder.append("\"");
			}

			response.addHeader("Content-Disposition", builder.toString());
		}

		private String encodeFilename(final String name, final String charset) {
			try {
				return new String(name.getBytes(charset), "ISO-8859-1");
			} catch (final UnsupportedEncodingException e) {
				throw new IORuntimeException(e);
			}
		}

	}

}