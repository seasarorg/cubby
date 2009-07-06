/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.fileupload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * Streaming API によってマルチパート要求を処理するための {@link FileItemFactory} の実装です。
 * <p>
 * ローカルファイルへの書き込みに制限がある環境で {@link DiskFileItemFactory} の代わりに使用してください。
 * </p>
 * 
 * @see <a
 *      href="http://commons.apache.org/fileupload/streaming.html">Streaming&nbsp;API&nbsp;(commons-fileupload)</a>
 * @author baba
 */
public class StreamFileItemFactory implements FileItemFactory {

	/** 初期バッファサイズのデフォルト。 */
	public static final int DEFAULT_INITIAL_BUFFER_SIZE = 8192;

	/** 初期バッファサイズ。 */
	private int initialBufferSize = DEFAULT_INITIAL_BUFFER_SIZE;

	/**
	 * 初期バッファサイズを設定します。
	 * 
	 * @param initialBufferSize
	 *            初期バッファサイズ
	 */
	public void setInitialBufferSize(final int initialBufferSize) {
		this.initialBufferSize = initialBufferSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public FileItem createItem(final String fieldName,
			final String contentType, final boolean isFormField,
			final String fileName) {
		return new ByteArrayFileItem(fieldName, contentType, isFormField,
				fileName, initialBufferSize);
	}

	/**
	 * バイト配列にデータを保持する {@link FileItem} の実装です。
	 * 
	 * @author baba
	 */
	private static class ByteArrayFileItem implements FileItem {

		private static final long serialVersionUID = 1L;

		/** フィールド名。 */
		private String fieldName;

		/** コンテントタイプ。 */
		private final String contentType;

		/** フォームフィールドか。 */
		private boolean isFormField;

		/** ファイル名。 */
		private final String fileName;

		/** 初期バッファサイズ。 */
		private final int initialBufferSize;

		/** 出力ストリーム。 */
		private transient ByteArrayOutputStream outputStream;

		/** データ。 */
		private byte[] data;

		/**
		 * インスタンス化します。
		 * 
		 * @param fieldName
		 *            フィールド名
		 * @param contentType
		 *            コンテントタイプ
		 * @param isFormField
		 *            フォームフィールドか
		 * @param fileName
		 *            ファイル名
		 * @param initialBufferSize
		 *            初期バッファサイズ
		 */
		public ByteArrayFileItem(final String fieldName,
				final String contentType, final boolean isFormField,
				final String fileName, final int initialBufferSize) {
			this.fieldName = fieldName;
			this.contentType = contentType;
			this.isFormField = isFormField;
			this.fileName = fileName;
			this.initialBufferSize = initialBufferSize;
		}

		/**
		 * {@inheritDoc}
		 */
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(get());
		}

		/**
		 * {@inheritDoc}
		 */
		public String getContentType() {
			return contentType;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getName() {
			return fileName;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isInMemory() {
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		public long getSize() {
			return get().length;
		}

		/**
		 * {@inheritDoc}
		 */
		public byte[] get() {
			if (data == null) {
				data = outputStream.toByteArray();
			}
			return data;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getString(final String encoding)
				throws UnsupportedEncodingException {
			return new String(get(), encoding);
		}

		/**
		 * {@inheritDoc}
		 */
		public String getString() {
			return new String(get());
		}

		/**
		 * {@inheritDoc}
		 */
		public void write(final File file) throws Exception {
			final FileOutputStream out = new FileOutputStream(file);
			try {
				out.write(get());
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void delete() {
			this.data = null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getFieldName() {
			return fieldName;
		}

		/**
		 * {@inheritDoc}
		 */
		public void setFieldName(final String name) {
			this.fieldName = name;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isFormField() {
			return isFormField;
		}

		/**
		 * {@inheritDoc}
		 */
		public void setFormField(final boolean state) {
			this.isFormField = state;
		}

		/**
		 * {@inheritDoc}
		 */
		public OutputStream getOutputStream() {
			if (outputStream == null) {
				outputStream = new ByteArrayOutputStream(initialBufferSize);
			}
			return outputStream;
		}

	}
}
