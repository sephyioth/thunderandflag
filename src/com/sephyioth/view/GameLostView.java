package com.sephyioth.view;

import com.sephyioth.thunderrun.R;
import com.sephyioth.tools.BitmapTools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

/** 类说明：游戏失败显示
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-5 下午4:08:17 */
public class GameLostView {
	// ** 常量 **/

	// ** 变量 **/
	private Bitmap mGameOverBitmap = null;
	private Resources mResources = null;
	private SurfaceView mParentView;

	// ** 构造函数 **/
	public GameLostView(SurfaceView parentView) {
		mResources = parentView.getResources();
		this.mParentView = parentView;
	}

	// ** 成员方法 **/
	/** 绘制游戏失败的页面
	 * 
	 * @author Sephyioth */
	public void drawGameLost(Canvas canvas, Paint paint) {
		canvas.drawColor(Color.WHITE);
		if (mGameOverBitmap != null) {
			canvas.drawBitmap(mGameOverBitmap, 0, 0, paint);
		}

	}

	public void initGame() {
		mGameOverBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.gamelost);
		mGameOverBitmap = BitmapTools.resizeBitmap(mGameOverBitmap,
				(float) mParentView.getWidth() / mGameOverBitmap.getWidth(),
				(float) mParentView.getHeight() / mGameOverBitmap.getHeight());
	}

	public void recycle() {
		if (mGameOverBitmap != null) {
			mGameOverBitmap.recycle();
			mGameOverBitmap = null;
		}
	}
	// ** 静态方法 **/

	// ** 内部类接口 **/
}
