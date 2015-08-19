package com.sephyioth.view;

import com.sephyioth.constant.Constant;
import com.sephyioth.model.MainGame;

import android.R.menu;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.Toast;

/** 类说明： 类的主要视图
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午2:28:42 */
@SuppressLint("WrongCall")
public class MainView extends SurfaceView implements Callback {

	// ** 常量 **/
	private static final String TAG = "MainView";

	// ** 变量 **/
	private Canvas mCanvas;
	public static int mScreenX, mScreenY;
	private SurfaceHolder mHolder;
	private Paint mPaint;
	private Handler mHandler;

	// 兵线资源
	private GameStartView mStartView = null;
	private GameLostView mGameLostView = null;
	private GamePauseView mPauseView = null;
	private GameMenuView mMenuView = null;
	private GameWinView mGameWinView = null;

	// ** 构造函数 **/
	public MainView(Context context, Handler mHandler) {
		super(context);
		if (mHandler == null) {
			Log.e(TAG, "handler is null");
			Toast.makeText(context, "program is error", Toast.LENGTH_LONG)
					.show();
		}

		this.mHandler = mHandler;
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);
		setFocusable(true);
	}

	// ** 成员方法 **/

	/** 依据运行时数据绘制图形
	 * 
	 * @param game
	 * @author Sephyioth */
	public void onDraw(MainGame game) {
		if (game == null) {
			Log.e(TAG, "game is null");
			return;
		}
		try {
			mCanvas = mHolder.lockCanvas();
			switch (game.mGameState) {
			case Constant.GAME_TIME:
				mStartView.drawTime(game.getTime(), mCanvas, mPaint);
			case Constant.GAME_NORMAL_START:
				mStartView.onDraw(game, mCanvas, mPaint);
			case Constant.GAME_ENDLESS:
				mStartView.onDraw(game, mCanvas, mPaint);
				break;
			case Constant.GAME_LOST:
				mGameLostView.drawGameLost(mCanvas, mPaint, game);
				break;
			case Constant.GAME_PAUSE:
				break;
			case Constant.GAME_MENU:
				mMenuView.onDraw(mCanvas, mPaint);
				break;
			case Constant.GAME_WIN:
				mGameWinView.drawGameLost(mCanvas, mPaint, game);
				break;
			default:
				break;
			}
			mCanvas.save();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mCanvas != null)
				mHolder.unlockCanvasAndPost(mCanvas);
		}
	}

	public int getScreenWidth() {
		return mScreenX;
	}

	@Override
	/**
	 * SurfaceView 初始化
	 * @param holder
	 * @author Sephyioth
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		mScreenX = this.getWidth();
		mScreenY = this.getHeight();
		initGame();
	}

	/** 初始化游戏数据
	 * 
	 * @author Sephyioth */
	public void initGame() {
		mStartView = new GameStartView(this);
		mStartView.initGame();
		mGameLostView = new GameLostView(this);
		mGameLostView.initGame();
		mPauseView = new GamePauseView(this);
		// mPauseView.initGame();
		mMenuView = new GameMenuView(this);
		mMenuView.initGame();
		mGameWinView = new GameWinView(this);
		mGameWinView.initGame();
	}

	public void setMenuView(GameMenuView menu) {
		mMenuView = menu;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/** 释放资源 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCanvas != null) {
			this.mCanvas = null;
		}
		if (mStartView != null) {
			mStartView.recycle();
			mStartView = null;
		}
		if (mGameLostView != null) {
			mGameLostView.recycle();
			mGameLostView = null;
		}
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
