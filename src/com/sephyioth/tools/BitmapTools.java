package com.sephyioth.tools;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.sephyioth.constant.Constant;

/** 类说明：图片处理工具类
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-6 下午1:16:25 */
public class BitmapTools {
	// ** 常量 **/

	// ** 变量 **/

	// ** 构造函数 **/

	// ** 成员方法 **/

	// ** 静态方法 **/
	/** 放大缩小图片
	 * 
	 * @param src
	 * @param sw
	 * @param sh
	 * @return
	 * @author Sephyioth */
	public static Bitmap resizeBitmap(Bitmap src, float sw, float sh) {
		if (src != null && sw > 0 && sh > 0) {
			Matrix matrix = new Matrix();
			matrix.setScale(sw, sh);
			return Bitmap.createBitmap(src, Constant.INT_0, Constant.INT_0,
					src.getWidth(), src.getHeight(), matrix, true);

		}
		return null;
	}

	/** 旋转缩小图片
	 * 
	 * @param src
	 * @param sw
	 * @param sh
	 * @return
	 * @author Sephyioth */
	public static Bitmap rotateBitmap(Bitmap src, int degrees) {
		if (src != null) {
			Matrix matrix = new Matrix();
			matrix.setRotate(degrees);
			return Bitmap.createBitmap(src, Constant.INT_0, Constant.INT_0,
					src.getWidth(), src.getHeight(), matrix, true);

		}
		return null;
	}
	// ** 内部类接口 **/
}
