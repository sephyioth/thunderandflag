package com.sephyioth.view;

import com.sephyioth.constant.Constant;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/** 类说明： 动画按键类
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-6 下午4:05:45 */
public class AminationButton implements Runnable {
	// ** 常量 **/
	private static final String TAG = "amination error";

	// ** 变量 **/
	private int mLayoutX;
	private int mLayoutY;
	private int mButtonW;
	private int mButtonH;
	private int mButtonX;
	private int mButtonY;
	private int mAminationMode = Constant.BUTTON_NONE;
	private int mColor;
	private Handler mHandler;
	private String mTextString;

	// ** 构造函数 **/
	public AminationButton(int x, int y, int w, int h) {
		this.mLayoutX = x;
		this.mLayoutY = y;
		this.mButtonH = h;
		this.mButtonW = w;
		Thread thread = new Thread(this);
		thread.start();
		this.mColor = Constant.INT_0;
	}

	// ** 成员方法 **/
	public void onDraw(Canvas canvas, Paint paint) {
		if (canvas == null || paint == null) {
			Log.e(TAG, "canvas and paint error");
			return;
		}
		int left = mLayoutX + mButtonX;
		int right = mLayoutX + mButtonW + mButtonX;
		int top = mLayoutY + mButtonY;
		int bottom = mLayoutY + mButtonY + mButtonH;
		paint.setColor(mColor);
		RectF rect = new RectF(left, top, right, bottom);
		canvas.drawRoundRect(rect, Constant.BUTTON_RUNDER,
				Constant.BUTTON_RUNDER, paint);
		paint.setColor(Color.WHITE);
		int size = Math.abs(right - left) / 10;
		paint.setTextSize(size);

		canvas.drawText(mTextString, (right + left) / 2 - size * 2,
				(top + bottom) / 2, paint);
	}

	public String getText() {
		return mTextString;
	}

	public void setText(String string) {
		this.mTextString = string;
	}

	/** Handler */
	public void setHandler(Handler handler) {
		this.mHandler = handler;
	}

	public void setPadding(int padding) {
		mButtonH = mButtonH - padding * 2;
		mButtonW = mButtonW - padding * 2;
		mLayoutX = mLayoutX + padding;
		mLayoutY = mLayoutY + padding;
	}

	public void setModel(int model) {
		this.mAminationMode = model;
		switch (mAminationMode) {
		case Constant.BUTTON_BOTTOM:
			mButtonY += mLayoutY + mButtonH * 2;
			break;
		case Constant.BUTTON_LEFT:
			mButtonX -= (mLayoutX + mButtonW);
			break;
		case Constant.BUTTON_RIGHT:
			mButtonX += mLayoutX + mButtonW;
			break;
		case Constant.BUTTON_TOP:
			mButtonY -= mLayoutY + mButtonH;
			break;
		case Constant.BUTTON_NONE:
			mButtonY = Constant.INT_0;
			mButtonX = Constant.INT_0;
			break;
		}
	}

	public void setWidth(int width) {
		mButtonX = width;
	}

	public void setHeight(int height) {
		mButtonY = height;
	}

	public void logic() {

		switch (mAminationMode) {
		case Constant.BUTTON_BOTTOM:
			mButtonY -= Constant.BUTTON_SPEED;
			break;
		case Constant.BUTTON_LEFT:
			mButtonX += Constant.BUTTON_SPEED;
			break;
		case Constant.BUTTON_RIGHT:
			mButtonX -= Constant.BUTTON_SPEED;
			break;
		case Constant.BUTTON_TOP:
			mButtonY += Constant.BUTTON_SPEED;
			break;
		case Constant.BUTTON_NONE:
			mButtonY = Constant.INT_0;
			mButtonX = Constant.INT_0;
			break;
		}
	}

	@Override
	public void run() {
		boolean flag = true;
		while (flag) {
			logic();
			if (Math.abs(mButtonX) <= Constant.BUTTON_SPEED
					&& Math.abs(mButtonY) <= Constant.BUTTON_SPEED) {
				mButtonX = mButtonY = 0;
				if (mHandler != null) {
					Message msgMessage = new Message();
					msgMessage.what = Constant.MSG_AMIN_FINISH;
					mHandler.sendMessage(msgMessage);
				}
				flag = false;
				break;
			}
			try {
				Thread.sleep(Constant.GAME_FLASH_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// ** 静态方法 **/

	public void setColor(int color) {
		mColor = color;
	}

	// ** 内部类接口 **/
}
