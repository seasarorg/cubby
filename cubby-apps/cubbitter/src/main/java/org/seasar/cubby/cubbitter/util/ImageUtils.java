package org.seasar.cubby.cubbitter.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static byte[] getResizedImageBytes(BufferedImage image, int size)
			throws IOException {
		int width = image.getWidth();
		int height = image.getHeight();

		int resizeWidth, resizeHeight, offsetX = 0, offsetY = 0;

		// 縦長の場合
		if (width < height) {
			// 横幅を基準に縮小
			resizeWidth = size;
			resizeHeight = -1;

			// 縦方向オフセット
			offsetY = (int) ((height - width) * size / (double) width / 2.0);

		}
		// 横長の場合
		else {
			// 高さを基準に縮小
			resizeWidth = -1;
			resizeHeight = size;

			// 横方向オフセット
			offsetX = (int) ((width - height) * size / (double) height / 2.0);
		}

		// 縮小
		Image resizedImage = image.getScaledInstance(resizeWidth, resizeHeight,
				Image.SCALE_AREA_AVERAGING);

		// リサイズ画像作成
		BufferedImage resizedCutImage = new BufferedImage(size, size,
				BufferedImage.TYPE_INT_RGB);
		resizedCutImage.getGraphics().drawImage(resizedImage, 0, 0, size, size,
				offsetX, offsetY, size + offsetX, size + offsetY, null);

		// JPEGにしてバイト配列に変換
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(resizedCutImage, "JPG", os);

		return os.toByteArray();
	}

}
