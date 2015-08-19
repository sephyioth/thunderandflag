package com.sephyioth.view;

import com.sephyioth.constant.Constant;
import com.sephyioth.model.MainGame;
import com.sephyioth.thunderrun.R;
import com.sephyioth.tools.BitmapTools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
	public void drawGameLost(Canvas canvas, Paint paint, MainGame game) {
		if (mGameOverBitmap != null) {
			canvas.drawBitmap(mGameOverBitmap, 0, 0, paint);
		}

		int left = mParentView.getWidth() / 5;
		int right = mParentView.getWidth() * 4 / 5;
		int top = mParentView.getHeight() / 3;
		int bottom = mParentView.getHeight() * 2 / 3;
		paint.setColor(mResources.getColor(R.color.yblue));
		RectF rect = new RectF(left, top, right, bottom);
		canvas.drawRoundRect(rect, Constant.BUTTON_RUNDER,
				Constant.BUTTON_RUNDER, paint);
		paint.setColor(Color.WHITE);
		int size = Math.abs(right - left) / 10;
		paint.setTextSize(size);

		canvas.drawText(
				mResources.getString(R.string.str_score) + game.getScore(),
				(right + left) / 2 - size * 4, (top + bottom) / 2 - size * 2,
				paint);
		canvas.drawText(
				mResources.getString(R.string.str_time) + game.getScore(),
				(right + left) / 2 - size * 4, (top + bottom) / 2, paint);

		canvas.drawText("Touch to try again", (right + left) / 2 - size * 4,
				(top + bottom) / 2 + size * 3, paint);

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
