package org.seasar.cubby.helper.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.exception.DownloadRuntimeException;
import org.seasar.cubby.helper.DownloadHelper;
import org.seasar.framework.util.OutputStreamUtil;

/**
 * 指定されたバイト配列のダウンロード処理を行う {@link DownloadHelper}。
 * 
 * @author baba
 */
public class ByteArrayDownloadHelperImpl extends AbstractDownloadHelperImpl {

	private byte[] data = null;

	public byte[] getData() {
		return data;
	}

	public void setData(final byte[] data) {
		this.data = data;
	}

	public void download(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		if (this.data == null) {
			throw new DownloadRuntimeException("ECUB0101",
					new Object[] { data });
		}

		this.setupHeader(request, response);

		response.setContentLength(this.data.length);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(this.data);
		} finally {
			OutputStreamUtil.flush(out);
			OutputStreamUtil.close(out);
		}
	}

}
