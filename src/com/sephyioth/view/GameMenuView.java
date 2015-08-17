package com.sephyioth.view;

import com.sephyioth.constant.Constant;
import com.sephyioth.thunderrun.R;
import com.sephyioth.tools.BitmapTools;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;

/** 类说明：
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-6 下午2:46:31 */
public class GameMenuView {
	// ** 常量 **/

	// ** 变量 **/
	private Resources mResources = null;
	private SurfaceView mParentView;
	private Bitmap mBackgroundBitmap = null;
	private AminationButton mButton1, mButton2, mButton3, mButton4;
	private int mScreenX;
	private int mScreenY;
	private Handler mHandler;

	// ** 构造函数 **/
	public GameMenuView(SurfaceView parentView) {
		mResources = parentView.getResources();
		this.mParentView = parentView;
		if (mParentView != null) {
			mScreenX = mParentView.getWidth();
			mScreenY = mParentView.getHeight();
		}
	}

	// ** 成员方法 **/
	@SuppressLint("WrongCall")
	public void onDraw(Canvas canvas, Paint paint) {
		canvas.drawColor(Color.WHITE);
		drawBitmapBackground(canvas, paint);
		if (mButton4 != null) {
			mButton4.onDraw(canvas, paint);
		}
		if (mButton2 != null) {
			mButton2.onDraw(canvas, paint);
		}
		if (mButton1 != null) {
			mButton1.onDraw(canvas, paint);
		}

		if (mButton3 != null) {
			mButton3.onDraw(canvas, paint);
		}

		// Bitmap logo = BitmapTools.RotateBitmap(mBongLogoBitmap, mDegrees);
		// canvas.drawBitmap(logo, mScreenX / 2 - logo.getWidth() / 2, mScreenY
		// / 2 - logo.getHeight() / 2, paint);

		// logo.recycle();
	}

	private void drawBitmapBackground(Canvas canvas, Paint paint) {
		if (canvas != null && paint != null) {
			canvas.drawBitmap(mBackgroundBitmap, 0, 0, paint);
		}
	}

	public void initGame() {
		int w = mScreenX / 2;
		int h = mScreenY / 2;
		mButton1 = new AminationButton(0, 0, w, h);
		mButton1.setModel(Constant.BUTTON_BOTTOM);
		mButton1.setPadding(Constant.BUTTON_PADDING);
		mButton1.setColor(mResources.getColor(R.color.original));
		mButton1.setText(mResources.getText(R.string.str_mode_normal)
				.toString());

		mButton3 = new AminationButton(w, h, w, h);
		mButton3.setPadding(Constant.BUTTON_PADDING);
		mButton3.setText(mResources.getText(R.string.str_mode_infinite).toString());
		mButton3.setModel(Constant.BUTTON_TOP);
		mButton3.setColor(mResources.getColor(R.color.yblue));

		mBackgroundBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_background);
		mBackgroundBitmap = BitmapTools.resizeBitmap(mBackgroundBitmap,
				(float) mScreenX / mBackgroundBitmap.getWidth(),
				(float) mScreenY / mBackgroundBitmap.getHeight());
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				int w = mScreenX / 2;
				int h = mScreenY / 2;
				switch (msg.what) {
				case Constant.MSG_AMIN_FINISH:
					mButton2 = new AminationButton(0, h, w, h);
					mButton2.setModel(Constant.BUTTON_TOP);
					mButton2.setHeight(-h);
					mButton2.setText(mResources.getText(
							R.string.str_mode_time).toString());
					mButton2.setPadding(Constant.BUTTON_PADDING);
					mButton2.setColor(mResources.getColor(R.color.ygreen));
					mButton4 = new AminationButton(w, 0, w, h);
					mButton4.setModel(Constant.BUTTON_BOTTOM);
					mButton4.setHeight(h);
					mButton4.setText(mResources.getText(R.string.str_mode_setting)
							.toString());
					mButton4.setPadding(Constant.BUTTON_PADDING);
					mButton4.setColor(mResources.getColor(R.color.pubble));
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}

		};
		mButton1.setHandler(mHandler);
	}
	// ** 静态方法 **/

	// ** 内部类接口 **/
}
