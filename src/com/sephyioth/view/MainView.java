package com.sephyioth.view;

import com.sephyioth.Bean.EngineerBean;
import com.sephyioth.Bean.LeaderBean;
import com.sephyioth.Bean.ThunderBean;
import com.sephyioth.Bean.TrackerBean;
import com.sephyioth.constant.Constant;
import com.sephyioth.model.MainGame;
import com.sephyioth.thunderrun.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
	private Resources mResources = this.getResources();
	private SurfaceHolder mHolder;
	private Paint mPaint;
	private Handler mHandler;
	private static String[] mLeaderLevel;
	private static String[] mEnginneerStatus;
	private float mScale;
	private int mDefineWidth;

	// 兵线资源
	private Bitmap mLeaderBackgroundBitmap = null;
	private Bitmap mRoadScaleBitmap = null;
	private Bitmap mTrackerBitmap = null;

	// 工兵图片
	private static Bitmap mEngineerBitmap = null;
	// 路线资源
	private Bitmap mRoadLineBitmap = null;

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
	/** 显示目前得分
	 * 
	 * @param score
	 * @author Sephyioth */
	public void drawScore(long score) {
		mPaint.setColor(Color.WHITE);
		String scoreString = "" + score;
		int textlength = scoreString.length() > Constant.HEIGHT_SCORE ? scoreString
				.length() : Constant.HEIGHT_SCORE;
		int scoreSize = mScreenX / 2 / textlength;
		int localx = mScreenX / 20;
		int localy = mScreenY / 15;
		mPaint.setTextSize(scoreSize);
		mCanvas.drawText(
				mResources.getString(R.string.str_score) + scoreString, localx,
				localy, mPaint);
	}

	/** 显示最高得分
	 * 
	 * @param score
	 * @author Sephyioth */
	public void drawHeightScore(long score) {
		mPaint.setColor(Color.WHITE);
		String scoreString = "" + 0;
		int textlength = scoreString.length() > Constant.HEIGHT_SCORE ? scoreString
				.length() : Constant.HEIGHT_SCORE;
		int scoreSize = mScreenX / 2 / textlength;
		int localx = mScreenX / 2;
		int localy = mScreenY / 15;
		mPaint.setTextSize(scoreSize);
		mCanvas.drawText(mResources.getString(R.string.str_height_score)
				+ scoreString, localx, localy, mPaint);
	}

	/** 小兵状态
	 * 
	 * @param status
	 * @author Sephyioth */
	public void drawEngineerStatus(int status) {
		if (mEnginneerStatus == null) {
			mEnginneerStatus = mResources.getStringArray(R.array.array_status);
		}
		mPaint.setColor(Color.WHITE);
		String string = mResources.getString(R.string.str_status)
				+ mEnginneerStatus[status % mEnginneerStatus.length];
		int textSize = mScreenX / 3 / string.length();
		int localx = mScreenX / 20;
		int localy = mScreenY / 8;
		mPaint.setTextSize(textSize);
		mCanvas.drawText(string, localx, localy, mPaint);
	}

	public void drawEngineerStatusTime(int time) {
		if (mEnginneerStatus == null) {
			mEnginneerStatus = mResources.getStringArray(R.array.array_status);
		}
		mPaint.setColor(Color.WHITE);
		String string = mResources.getString(R.string.str_status) + time;
		int textSize = mScreenX / 3 / string.length();
		int localx = mScreenX / 2;
		int localy = mScreenY / 15;
		mPaint.setTextSize(textSize);
		mCanvas.drawText(string, localx, localy, mPaint);

	}

	/** 画路
	 * 
	 * @param x
	 * @param y
	 * @author Sephyioth */
	public void drawRoad(int x, int y) {
		if (mRoadLineBitmap == null || mRoadScaleBitmap == null) {
			mRoadLineBitmap = BitmapFactory.decodeResource(mResources,
					R.drawable.bmp_road);
			mRoadScaleBitmap = resizeBitmap(mRoadScaleBitmap, mScale, mScale);
		}
		mCanvas.drawBitmap(mRoadScaleBitmap, mDefineWidth + x
				* mRoadScaleBitmap.getWidth(),
				mScreenY - y * mRoadScaleBitmap.getHeight(), mPaint);
	}

	/** 画小兵
	 * 
	 * @param bean
	 * @author Sephyioth */
	public void drawEngineer(EngineerBean bean) {
		if (bean == null) {
			return;
		}
		if (mEngineerBitmap != null) {
			int personw = mEngineerBitmap.getWidth();
			int personh = mEngineerBitmap.getHeight();
			int roadw = mRoadScaleBitmap.getWidth();
			int roadh = mRoadScaleBitmap.getHeight();
			int localx = mDefineWidth + bean.getEngineerX() * roadw
					+ (roadw - personw) / 2;
			int localy = mScreenY - bean.getLayoutY() * roadh
					+ (roadh - personh) / 2;
			int textx = (int) (localx + (personw / 2 - mPaint.getTextSize()));
			int texty = (int) (localy + (personh + mPaint.getTextSize()) / 2);
			drawPerson(mEngineerBitmap, localx, localy);
			mPaint.setColor(Color.WHITE);
			mCanvas.drawText(mLeaderLevel[bean.getEngineerStatus()], textx,
					texty, mPaint);
			bean.setLocalX(localx);
			bean.setLocalY(localy);
			bean.setHeight(personh);
			bean.setWidth(personw);
		}

	}

	/** 画地雷
	 * 
	 * @param bean
	 * @author Sephyioth */
	public void drawThunder(ThunderBean bean) {
		if (bean != null) {
			int personw = mLeaderBackgroundBitmap.getWidth();
			int personh = mLeaderBackgroundBitmap.getHeight();
			int roadw = mRoadScaleBitmap.getWidth();
			int roadh = mRoadScaleBitmap.getHeight();
			int localx = mDefineWidth + bean.getThunderX() * roadw
					+ (roadw - personw) / 2;
			int localy = mScreenY - bean.getThunderY() * roadh
					+ (roadh - personh) / 2;
			drawRoad(bean.getThunderX(), bean.getThunderY());
			int textx = (int) (localx + (personw / 2 - mPaint.getTextSize()));
			int texty = (int) (localy + (personh + mPaint.getTextSize()) / 2);
			if (bean.isVisable()) {
				drawPerson(mLeaderBackgroundBitmap, localx, localy);
				mPaint.setColor(Color.WHITE);
				mCanvas.drawText(mLeaderLevel[Constant.LEADER_LEVEL_MINE],
						textx, texty, mPaint);
			}
			bean.setLocalX(localx);
			bean.setLocalY(localy);
			bean.setHeight(personh);
			bean.setWidth(personw);
		}
	}

	/** 绘制 阻挡者
	 * 
	 * @param bean
	 * @author Sephyioth */
	public void drawLeader(LeaderBean bean) {
		if (bean != null && mLeaderBackgroundBitmap != null) {
			drawRoad(bean.getLeaderX(), bean.getLeaderY());

			int personw = mLeaderBackgroundBitmap.getWidth();
			int personh = mLeaderBackgroundBitmap.getHeight();
			int roadw = mRoadScaleBitmap.getWidth();
			int roadh = mRoadScaleBitmap.getHeight();
			int localx = mDefineWidth + bean.getLeaderX() * roadw
					+ (roadw - personw) / 2;
			int localy = mScreenY - bean.getLeaderY() * roadh
					+ (roadh - personh) / 2;
			int textx = (int) (localx + (personw / 2 - mPaint.getTextSize()));
			int texty = (int) (localy + (personh + mPaint.getTextSize()) / 2);
			if (bean.isVisable()) {
				drawPerson(mLeaderBackgroundBitmap, localx, localy);
				mPaint.setColor(Color.WHITE);
				mCanvas.drawText(mLeaderLevel[bean.getLeaderLevel()], textx,
						texty, mPaint);
			}
			bean.setLocalX(localx);
			bean.setLocalY(localy);
			bean.setHeight(personh);
			bean.setWidth(personw);
		}
	}

	/** 绘制追击者
	 * 
	 * @param tracker
	 * @author Sephyioth */
	public void drawTracker(TrackerBean tracker) {
		if (tracker != null && mTrackerBitmap != null) {
			int roadw = mRoadScaleBitmap.getWidth();
			int trackerw = mTrackerBitmap.getWidth();
			int trackerh = mTrackerBitmap.getHeight();
			int localx = mDefineWidth + roadw * tracker.getX()
					+ (roadw - trackerw) / 2;
			int localy = mScreenY - mTrackerBitmap.getHeight()
					+ tracker.getCount();
			mCanvas.drawBitmap(mTrackerBitmap, localx, localy, mPaint);
			tracker.setLocalX(localx);
			tracker.setLocalY(localy);
			tracker.setHeight(trackerw);
			tracker.setWidth(trackerh);
		}
	}

	/** 绘制人物
	 * 
	 * @param person
	 * @param x
	 * @param y
	 * @author Sephyioth */
	private void drawPerson(Bitmap person, int x, int y) {
		if (person != null) {
			mCanvas.drawBitmap(person, x, y, mPaint);
		}
	}

	/** 依据运行时数据绘制图形
	 * 
	 * @param game
	 * @author Sephyioth */
	public void onDraw(MainGame game) {
		if (game == null) {
			Log.e(TAG, "game is null");
			return;
		}
		caleTheSize(game.mGameLevel);
		resizeRoadBitmap();
		try {
			mCanvas = mHolder.lockCanvas();
			mCanvas.drawColor(Color.WHITE);

			for (int i = 0; i < game.getLeaderBeans().size(); i++) {
				LeaderBean bean = game.getLeaderBeans().get(i);
				drawLeader(bean);
			}

			for (int i = 0; i < game.getThunderBeans().size(); i++) {
				ThunderBean bean = game.getThunderBeans().get(i);
				drawThunder(bean);
			}
			EngineerBean bean = game.getEngineerBean();
			drawTracker(game.getTrackerBean());
			drawScore(game.getScore());
			drawHeightScore(game.getHighScore());
			drawEngineerStatus(bean.getEngineerStatus());
			drawEngineer(bean);
			drawEngineer(game.getEngineerStatus());
			if (bean.getEngineerStatus() != Constant.GAME_ENGINEERSTATUS_NORMAL) {
				drawEngineerStatusTime(bean.getInvTime());
			}
			mCanvas.save();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mCanvas != null)
				mHolder.unlockCanvasAndPost(mCanvas);
		}
	}

	/** resize 路面的背景
	 * 
	 * @author Sephyioth */
	private void resizeRoadBitmap() {
		mRoadScaleBitmap = resizeBitmap(mRoadLineBitmap, mScale, mScale);
	}

	/** 放大缩小图片
	 * 
	 * @param src
	 * @param sw
	 * @param sh
	 * @return
	 * @author Sephyioth */
	public Bitmap resizeBitmap(Bitmap src, float sw, float sh) {
		if (src != null) {
			Matrix matrix = new Matrix();
			matrix.setScale(sw, sh);
			return Bitmap.createBitmap(src, Constant.INT_0, Constant.INT_0,
					src.getWidth(), src.getHeight(), matrix, true);

		}
		return null;
	}

	/** 依据等级计算宽度和长度大小
	 * 
	 * @author Sephyioth */
	private void caleTheSize(int level) {
		if (mRoadLineBitmap != null) {
			int scalw = mScreenX * 9 / 10 / level;
			mScale = (float) scalw / mRoadLineBitmap.getWidth();
		}
	}

	public int getScreenWidth() {
		return this.mScreenX;
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
		mRoadLineBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_road);
		mLeaderBackgroundBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_leaderbackground);
		mEngineerBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_enginnerbackground);
		mTrackerBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.emcar03);
		mLeaderLevel = mResources.getStringArray(R.array.array_level);
		mEnginneerStatus = mResources.getStringArray(R.array.array_status);
		mDefineWidth = mScreenX / 20;
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
		if (mEngineerBitmap != null) {
			mEngineerBitmap.recycle();
			mEngineerBitmap = null;
		}
		if (mRoadLineBitmap != null) {
			mRoadLineBitmap.recycle();
			mRoadLineBitmap = null;
		}
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
