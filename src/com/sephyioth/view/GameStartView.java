package com.sephyioth.view;

import com.sephyioth.Bean.BasicModelBean;
import com.sephyioth.Bean.EngineerBean;
import com.sephyioth.Bean.LeaderBean;
import com.sephyioth.Bean.ThunderBean;
import com.sephyioth.Bean.TrackerBean;
import com.sephyioth.constant.Constant;
import com.sephyioth.model.MainGame;
import com.sephyioth.thunderrun.R;
import com.sephyioth.thunderrun.R.color;
import com.sephyioth.thunderrun.R.string;
import com.sephyioth.tools.BitmapTools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceView;

/** 类说明：开始游戏
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-5 下午4:07:37 */
public class GameStartView {
	// ** 常量 **/
	private String TAG = "StartView error";
	// ** 变量 **/
	private static String[] mLeaderLevel;
	private static String[] mEnginneerStatus;
	private float mScale;
	private int mDefineWidth;
	private Canvas mCanvas;
	private Paint mPaint;
	private Resources mResources = null;
	// 兵线资源
	private Bitmap mLeaderBackgroundBitmap = null;
	private Bitmap mRoadScaleBitmap = null;
	private Bitmap mInvBitmap = null;
	private Bitmap mBackgroundBitmap = null;
	private Bitmap mSettingBitmap = null;
	public int mScreenX, mScreenY;
	// 工兵图片
	private static Bitmap mEngineerBitmap = null;
	// 路线资源
	private Bitmap mRoadLineBitmap = null;

	// ** 构造函数 **/
	public GameStartView(SurfaceView parentView) {
		if (parentView == null) {
			Log.e(TAG, "Program error");
		}
		mResources = parentView.getResources();
		mScreenX = parentView.getWidth();
		mScreenY = parentView.getHeight();
		initGame();
	}

	// ** 成员方法 **/
	public void onDraw(MainGame game, Canvas canvas, Paint paint) {
		this.mCanvas = canvas;
		this.mPaint = paint;
		drawBitmapBackground(canvas, paint);
		caleTheSize(game.mGameLevel);
		resizeRoadBitmap();
		for (int i = 0; i < game.getLeaderBeans().size(); i++) {
			LeaderBean bean = game.getLeaderBeans().get(i);
			if (bean.getLayoutX() < game.mGameLevel) {
				drawLeader(bean);
			}
		}

		for (int i = 0; i < game.getThunderBeans().size(); i++) {
			ThunderBean bean = game.getThunderBeans().get(i);
			drawThunder(bean);
		}
		EngineerBean bean = game.getEngineerBean();
		drawTracker(game.getTrackerBean());

		drawRunning(game, canvas, paint);
		if (bean.getEngineerStatus() != Constant.GAME_ENGINEERSTATUS_NORMAL) {
			drawEngineerStatusTime(bean.getInvTime(), canvas, paint);
		}
		drawSettingBmp(canvas, paint);
		game.setSettingWidth(mSettingBitmap.getWidth());
		game.setSettingHeight(mSettingBitmap.getHeight());
	}

	/** Running
	 * 
	 * @param game
	 * @param canvas
	 * @param paint
	 * @author Sephyioth */
	private void drawRunning(MainGame game, Canvas canvas, Paint paint) {
		if (game == null || canvas == null || paint == null) {
			return;
		}
		int localx = 0;
		int localy = 0;
		int width = mScreenX;
		int height = mScreenY / 7;

		EngineerBean bean = game.getEngineerBean();
		drawEngineer(bean);
		drawEngineer(game.getEngineerStatus());
		paint.setColor(mResources.getColor(R.color.ygreen));
		RectF rect = new RectF(localx, localy, localx + width, localy + height);
		canvas.drawRoundRect(rect, Constant.BUTTON_RUNDER,
				Constant.BUTTON_RUNDER, paint);
		drawScore(game.getScore(), canvas, paint);
		drawHeightScore(game.getHighScore(), canvas, paint);
		drawEngineerStatus(bean.getEngineerStatus(), canvas, paint);

	}

	/** 画设置的Bar
	 * 
	 * @param canvas
	 * @param paint
	 * @author Sephyioth */
	private void drawSettingBmp(Canvas canvas, Paint paint) {
		if (canvas != null && paint != null && mSettingBitmap != null) {
			int localx = mScreenX * 4 / 5;
			int localy = mScreenY / 12;
			int width = mSettingBitmap.getWidth();
			int height = mSettingBitmap.getHeight();
			paint.setColor(mResources.getColor(R.color.yblue));
			RectF rect = new RectF(localx, localy, localx + width, localy
					+ height);
			canvas.drawRoundRect(rect, Constant.BUTTON_RUNDER,
					Constant.BUTTON_RUNDER, paint);

			canvas.drawBitmap(mSettingBitmap, localx, localy, paint);
		}
	}

	/** 画背景
	 * 
	 * @param canvas
	 * @param paint
	 * @author Sephyioth */
	private void drawBitmapBackground(Canvas canvas, Paint paint) {
		if (canvas != null && paint != null) {
			canvas.drawBitmap(mBackgroundBitmap, 0, 0, paint);
		}
	}

	/** 显示目前得分
	 * 
	 * @param score
	 * @author Sephyioth */
	public void drawScore(long score, Canvas canvas, Paint paint) {
		paint.setColor(Color.WHITE);
		String scoreString = "" + score;
		int textlength = scoreString.length() > Constant.HEIGHT_SCORE ? scoreString
				.length() : Constant.HEIGHT_SCORE;
		int scoreSize = mScreenX / 2 / textlength;
		int localx = mScreenX / 20;
		int localy = mScreenY / 15;
		paint.setTextSize(scoreSize);
		canvas.drawText(mResources.getString(R.string.str_score) + scoreString,
				localx, localy, paint);
	}

	/** 显示最高得分
	 * 
	 * @param score
	 * @author Sephyioth */
	public void drawHeightScore(long score, Canvas canvas, Paint paint) {
		paint.setColor(Color.WHITE);
		String scoreString = "" + score;
		int textlength = scoreString.length() > Constant.HEIGHT_SCORE ? scoreString
				.length() : Constant.HEIGHT_SCORE;
		int scoreSize = mScreenX / 2 / textlength;
		int localx = mScreenX / 2;
		int localy = mScreenY / 15;
		paint.setTextSize(scoreSize);
		canvas.drawText(mResources.getString(R.string.str_height_score)
				+ scoreString, localx, localy, paint);
	}

	/** 小兵状态
	 * 
	 * @param status
	 * @author Sephyioth */
	public void drawEngineerStatus(int status, Canvas canvas, Paint paint) {
		if (mEnginneerStatus == null) {
			mEnginneerStatus = mResources.getStringArray(R.array.array_status);
		}
		paint.setColor(Color.WHITE);
		String string = mResources.getString(R.string.str_status)
				+ mEnginneerStatus[status % mEnginneerStatus.length];
		int textSize = mScreenX / 3 / string.length();
		int localx = mScreenX / 20;
		int localy = mScreenY / 8;
		paint.setTextSize(textSize);
		canvas.drawText(string, localx, localy, paint);
	}

	/** 画状态时间
	 * 
	 * @param time
	 * @param canvas
	 * @param paint
	 * @author Sephyioth */
	public void drawEngineerStatusTime(int time, Canvas canvas, Paint paint) {
		if (mEnginneerStatus == null) {
			mEnginneerStatus = mResources.getStringArray(R.array.array_status);
		}
		paint.setColor(Color.WHITE);
		String string = mResources.getString(R.string.str_status) + time;
		int textSize = mScreenX / 3 / string.length();
		int localx = mScreenX / 2;
		int localy = mScreenY / 8;
		paint.setTextSize(textSize);
		canvas.drawText(string, localx, localy, paint);

	}

	/** 画路
	 * 
	 * @param x
	 * @param y
	 * @author Sephyioth */
	private void drawRoad(int x, int y) {
		if (mRoadLineBitmap == null || mRoadScaleBitmap == null) {
			mRoadLineBitmap = BitmapFactory.decodeResource(mResources,
					R.drawable.bmp_road);
			mRoadScaleBitmap = BitmapTools.resizeBitmap(mRoadScaleBitmap,
					mScale, mScale);
		}
		mCanvas.drawBitmap(mRoadScaleBitmap, mDefineWidth + x
				* mRoadScaleBitmap.getWidth(),
				mScreenY - y * mRoadScaleBitmap.getHeight(), mPaint);
	}

	/** 画小兵
	 * 
	 * @param bean
	 * @author Sephyioth */
	private void drawEngineer(EngineerBean bean) {
		if (mLeaderLevel != null) {
			drawObject(
					bean,
					mEngineerBitmap,
					mLeaderLevel[bean.getEngineerStatus() % mLeaderLevel.length]);

			if (bean.getEngineerStatus() == Constant.GAME_ENGINEERSTATUS_INVINCIBLE) {
				drawPerson(
						mInvBitmap,
						bean.getLocalX()
								+ (bean.getWidth() - mInvBitmap.getWidth()) / 2,
						bean.getLocalY()
								+ (bean.getHeight() - mInvBitmap.getHeight())
								/ 2);
			}
		}

	}

	/** 画出对应的对象
	 * 
	 * @param bean
	 * @param bitmap
	 * @param text
	 * @author Sephyioth */
	private void drawObject(BasicModelBean bean, Bitmap bitmap, String text) {
		if (bean == null) {
			return;
		}
		if (bitmap != null) {
			int personw = bitmap.getWidth();
			int personh = bitmap.getHeight();
			int roadw = mRoadScaleBitmap.getWidth();
			int roadh = mRoadScaleBitmap.getHeight();
			int localx = mDefineWidth + bean.getLayoutX() * roadw
					+ (roadw - personw) / 2;
			int localy = mScreenY - bean.getLayoutY() * roadh
					+ (roadh - personh) / 2;
			int textSize = personw / 3;
			if (bean instanceof TrackerBean) {
				TrackerBean trackerBean = (TrackerBean) bean;
				localy = mScreenY - mLeaderBackgroundBitmap.getHeight()
						+ trackerBean.getCount();
			}
			mPaint.setTextSize(textSize);
			int textx = (int) (localx + (personw / 2 - mPaint.getTextSize()));
			int texty = (int) (localy + (personh + mPaint.getTextSize()) / 2);

			if (bean.isVisable()) {
				drawPerson(bitmap, localx, localy);
				mPaint.setColor(Color.WHITE);
				mCanvas.drawText(text, textx, texty, mPaint);
			}

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
	private void drawThunder(ThunderBean bean) {
		if (bean != null && mLeaderBackgroundBitmap != null) {
			drawRoad(bean.getLayoutX(), bean.getLayoutY());
			drawObject(bean, mLeaderBackgroundBitmap,
					mLeaderLevel[Constant.LEADER_LEVEL_MINE]);
		}
	}

	/** 绘制 阻挡者
	 * 
	 * @param bean
	 * @author Sephyioth */
	private void drawLeader(LeaderBean bean) {
		if (bean != null && mLeaderBackgroundBitmap != null) {
			drawRoad(bean.getLayoutX(), bean.getLayoutY());
			drawObject(bean, mLeaderBackgroundBitmap,
					mLeaderLevel[bean.getLeaderLevel()]);
		}
	}

	/** 绘制追击者
	 * 
	 * @param tracker
	 * @author Sephyioth */
	private void drawTracker(TrackerBean tracker) {
		if (tracker != null && mLeaderBackgroundBitmap != null) {
			drawObject(tracker, mLeaderBackgroundBitmap,
					mLeaderLevel[Constant.LEADER_LEVEL_COMMANDER]);
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

	/** resize 路面的背景
	 * 
	 * @author Sephyioth */
	private void resizeRoadBitmap() {
		mRoadScaleBitmap = BitmapTools.resizeBitmap(mRoadLineBitmap, mScale,
				mScale);
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

	/** 初始化游戏数据
	 * 
	 * @author Sephyioth */
	public void initGame() {
		mRoadLineBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_road);
		mInvBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_inv);
		mLeaderBackgroundBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_leaderbackground);
		mEngineerBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_enginnerbackground);

		mBackgroundBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.bmp_background);
		mSettingBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.ic_action_refresh);
		mBackgroundBitmap = BitmapTools.resizeBitmap(mBackgroundBitmap,
				(float) mScreenX / mBackgroundBitmap.getWidth(),
				(float) mScreenY / mBackgroundBitmap.getHeight());
		mLeaderLevel = mResources.getStringArray(R.array.array_level);
		mEnginneerStatus = mResources.getStringArray(R.array.array_status);
		mDefineWidth = mScreenX / 20;
	}

	/** 回收资源 */
	public void recycle() {
		if (mRoadLineBitmap != null) {
			mRoadLineBitmap.recycle();
			mRoadLineBitmap = null;
		}
		if (mInvBitmap != null) {
			mInvBitmap.recycle();
			mInvBitmap = null;
		}
		if (mLeaderBackgroundBitmap != null) {
			mLeaderBackgroundBitmap.recycle();
			mLeaderBackgroundBitmap = null;
		}
		if (mEngineerBitmap != null) {
			mEngineerBitmap.recycle();
			mEngineerBitmap = null;
		}
	}

	public void drawTime(long time, Canvas canvas, Paint paint) {
		int localx = Constant.INT_0;
		int localy = mScreenY * 11 / 12;
		int width = mScreenX;
		int height = mScreenY / 12;
		paint.setColor(mResources.getColor(R.color.yblue));
		RectF rect = new RectF(localx, localy, localx + width, localy + height);
		canvas.drawRoundRect(rect, Constant.BUTTON_RUNDER,
				Constant.BUTTON_RUNDER, paint);
		paint.setColor(Color.WHITE);
		String timeString = mResources.getString(R.string.str_time);
		canvas.drawText(String.format(timeString, time),
				width / 2 - paint.getTextSize() * 2, localy + mScreenY / 20,
				paint);
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
