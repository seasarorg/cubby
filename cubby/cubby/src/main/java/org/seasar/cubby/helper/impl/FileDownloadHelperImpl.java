package org.seasar.cubby.helper.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.exception.DownloadRuntimeException;
import org.seasar.cubby.helper.DownloadHelper;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.StringUtil;

/**
 * 指定された {@link File} のダウンロード処理を行う {@link DownloadHelper}。
 * 
 * @author baba
 */
public class FileDownloadHelperImpl extends AbstractDownloadHelperImpl {

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(final File file) {
		this.file = file;
	}

	public void download(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		if (this.file == null) {
			throw new DownloadRuntimeException("ECUB0101",
					new Object[] { "file" });
		}

		if (StringUtil.isEmpty(this.getFilename())) {
			this.setFilename(this.file.getName());
		}

		this.setupHeader(request, response);

		InputStream input = null;
		try {
			input = new FileInputStream(this.file);
			final OutputStream output = response.getOutputStream();
			byte buf[] = new byte[8192];
			for (int n = 0; (n = input.read(buf, 0, buf.length)) != -1;) {
				output.write(buf, 0, n);
			}
		} finally {
			InputStreamUtil.close(input);
		}
	}

}
