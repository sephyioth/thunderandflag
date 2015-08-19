package com.sephyioth.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceView;

import com.sephyioth.constant.Constant;
import com.sephyioth.model.MainGame;
import com.sephyioth.thunderrun.R;
import com.sephyioth.tools.BitmapTools;

/** 类说明：
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-19 下午5:26:28 */
public class GameWinView {
	// ** 常量 **/

	// ** 变量 **/
	private Bitmap mGameOverBitmap = null;
	private Resources mResources = null;
	private SurfaceView mParentView;

	// ** 构造函数 **/
	public GameWinView(SurfaceView parentView) {
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
		int top = mParentView.getHeight() * 2 / 3;
		int bottom = mParentView.getHeight() * 5 / 6;
		paint.setColor(mResources.getColor(R.color.yblue));
		RectF rect = new RectF(left, top, right, bottom);
		canvas.drawRoundRect(rect, Constant.BUTTON_RUNDER_BIG,
				Constant.BUTTON_RUNDER_BIG, paint);
		paint.setColor(Color.WHITE);
		int size = Math.abs(right - left) / 10;
		paint.setTextSize(size);
		String timeString = mResources.getString(R.string.str_time);
		canvas.drawText(
				mResources.getString(R.string.str_score) + game.getScore(),
				(right + left) / 2 - size * 4, (top + bottom) / 2 - size / 2,
				paint);
		canvas.drawText(String.format(timeString, game.getTime()),
				(right + left) / 2 - size * 4, (top + bottom) / 2 + size, paint);
		paint.setColor(Color.BLACK);
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
}
