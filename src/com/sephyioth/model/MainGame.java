package com.sephyioth.model;

import java.util.Vector;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.sephyioth.Bean.EngineerBean;
import com.sephyioth.Bean.LeaderBean;
import com.sephyioth.Bean.ThunderBean;
import com.sephyioth.Bean.TrackerBean;
import com.sephyioth.constant.Constant;
import com.sephyioth.spconfig.SPConfig;

/** 类说明： 主要游戏处理类
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-1 上午12:01:45 */
public class MainGame extends Thread {

	private static final String TAG = "mainGame";
	public int mGameLevel = Constant.DEFAULT_LEVEL;
	public int mGameState = Constant.GAME_MENU;

	private long mScore = Constant.INT_0;
	private long mHighScore = Constant.INT_0;
	private long mTime = Constant.INT_0;
	private long mLastTime;
	private int mLeaderNum = Constant.INT_1;
	private int mWidth = Constant.INT_0;
	private int mHeight = Constant.INT_0;

	private int mEndTimes = Constant.INT_0;
	private int mPassedTime = Constant.INT_NEG;

	private Vector<ThunderBean> mThunderBeans;
	private EngineerBean mEngineerBean;
	private TrackerBean mTrackerBean;
	private EngineerBean mEngineerStatus;
	private Vector<LeaderBean> mLeaderBeans;
	private Context mContext;
	private Handler mHandler;
	private boolean isFlag = true;

	/** 构造函数
	 * 
	 * @param context
	 * @param mHandler */
	public MainGame(Context context, Handler mHandler) {
		if (mHandler == null) {
			Log.e(TAG, "handler is null");
			Toast.makeText(mContext, "program is error", Toast.LENGTH_LONG)
					.show();
		}
		mContext = context;
		this.mHandler = mHandler;
		mThunderBeans = new Vector<ThunderBean>();
		mEngineerBean = new EngineerBean(mGameLevel / 2);
		mLeaderBeans = new Vector<LeaderBean>();
		mScore = Constant.INT_0;
		mGameState = Constant.GAME_MENU;
	}

	/** 新游戏
	 * 
	 * @author Sephyioth */
	public void newGame() {
		mThunderBeans.removeAllElements();
		mLeaderBeans.removeAllElements();
		mEngineerBean = new EngineerBean(mGameLevel / 2);
		mScore = Constant.INT_0;
		mTime = Constant.INT_0;
		mPassedTime = Constant.INT_0;
		mLastTime = System.currentTimeMillis();
		dealGame(Constant.ENGINEER_DEFAULT_Y + Constant.INT_1);
		resetTracker();
	}

	/** 重置Leader
	 * 
	 * @author Sephyioth */
	public void resetTracker() {
		if (mTrackerBean == null) {
			mTrackerBean = new TrackerBean(mEngineerBean.getEngineerX(), 0,
					Constant.LEADER_DEFAULT_TIME);
		} else {
			mTrackerBean.setCount(Constant.LEADER_DEFAULT_TIME);
			mTrackerBean.setX(mEngineerBean.getEngineerX());
		}
	}

	/** 游戏处理类
	 * 
	 * @author Sephyioth */
	private void dealGame(int line) {
		switch (mGameState) {
		case Constant.GAME_NORMAL_START:
			if (mEndTimes <= mPassedTime) {
				return;
			}
			break;
		case Constant.GAME_ENDLESS:

			break;
		default:
			break;
		}
		int level = (int) Math.log(mPassedTime);
		if (level > mGameLevel - Constant.INT_1) {
			mGameLevel = level;
		}
		for (int i = line; i < Constant.THUNDER_DEFAULT_COUNT; i++) {
			addThunder(i);
			addLeader(i);

		}

	}

	/** 增加拦截者
	 * 
	 * @author Sephyioth */
	private void addLeader(int line) {
		if (mLeaderBeans == null) {
			mLeaderBeans = new Vector<LeaderBean>();
		}
		boolean isLowLV = false;
		for (int j = Constant.INT_0; j < Constant.MAX_LEVEL; j++) {
			if (mThunderBeans.get(mThunderBeans.size() - 1).getThunderX() != j) {
				int level = getRandomLeaderLevel();
				if (isLowLV) {
					level = level % 5;
				}
				if (level > 5) {
					isLowLV = true;
				}

				mLeaderBeans.add(new LeaderBean(j, line, level));
			}
		}
	}

	/** 增加地雷
	 * 
	 * @author Sephyioth */
	private void addThunder(int line) {
		if (mThunderBeans == null) {
			mThunderBeans = new Vector<ThunderBean>();
		}
		if (mThunderBeans.size() < Constant.THUNDER_DEFAULT_COUNT) {
			mThunderBeans.add(new ThunderBean(getRandomX(), line));
		}
		mPassedTime++;

	}

	/** 随机产生追击者等级
	 * 
	 * @return
	 * @author Sephyioth */
	private int getRandomLeaderLevel() {
		return (int) (Math.random() * Constant.LEADER_LEVEL);
	}

	/** 随机产生坐标
	 * 
	 * @return
	 * @author Sephyioth */
	private int getRandomX() {
		return (int) (Math.random() * mGameLevel);
	}

	private int getRandomY() {
		return Constant.DEFAULT_LEVEL + (int) (Math.random() * 10);
	}

	/** 取得最高分
	 * 
	 * @return
	 * @author Sephyioth */
	public long getHighScore() {
		return mHighScore;
	}

	public long getTime() {
		return mTime;
	}

	/** 游戏胜利与否
	 * 
	 * @return
	 * @author Sephyioth */
	public boolean gameWon() {
		if (mLeaderNum > 10000) {
			mGameState = Constant.GAME_WIN;
			gameWon();
		}
		return false;
	}

	/** 游戏是否失败
	 * 
	 * @return
	 * @author Sephyioth */
	public boolean gameLost() {
		return (mGameState == Constant.GAME_LOST);
	}

	/** 游戏是否活动
	 * 
	 * @return
	 * @author Sephyioth */
	public boolean isActive() {
		return !(gameWon() || gameLost());
	}

	/** 结束游戏
	 * 
	 * @author Sephyioth */
	private void endGame() {
		mGameState = Constant.GAME_LOST;
		if (mScore >= mHighScore) {
			mHighScore = mScore;
			recordHighScore();
		}
	}

	/** 记录最大分数
	 * 
	 * @author Sephyioth */
	private void recordHighScore() {
		SPConfig.getConfig().setLong(SPConfig.HIGH_SCORE, mHighScore);
	}

	/** 是否继续游戏
	 * 
	 * @return
	 * @author Sephyioth */
	public boolean canContinue() {
		return !(mGameState == Constant.GAME_ENDLESS || mGameState == Constant.GAME_ENDLESS_WON);
	}

	public boolean isFlag() {
		return isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	/** 取得游戏等级
	 * 
	 * @return
	 * @author Sephyioth */
	public int getGameLevel() {
		return mGameLevel;
	}

	public void setGameLevel(int mGameLevel) {
		this.mGameLevel = mGameLevel;
	}

	public int getGameState() {
		return mGameState;
	}

	public void setGameState(int mGameState) {
		this.mGameState = mGameState;
	}

	public long getScore() {
		return mScore;
	}

	public void setScore(long mScore) {
		this.mScore = mScore;
	}

	public void setHighScore(long mHighScore) {
		this.mHighScore = mHighScore;
	}

	public Vector<ThunderBean> getThunderBeans() {
		return mThunderBeans;
	}

	public void setThunderBeans(Vector<ThunderBean> mThunderBeans) {
		this.mThunderBeans = mThunderBeans;
	}

	public EngineerBean getEngineerBean() {
		return mEngineerBean;
	}

	public void setEngineerBean(EngineerBean mEngineerBean) {
		this.mEngineerBean = mEngineerBean;
	}

	public Vector<LeaderBean> getLeaderBeans() {
		return mLeaderBeans;
	}

	public void setLeaderBeans(Vector<LeaderBean> mLeaderBeans) {
		this.mLeaderBeans = mLeaderBeans;
	}

	public TrackerBean getTrackerBean() {
		return mTrackerBean;
	}

	public void setTrackerBean(TrackerBean mTrackerBean) {
		this.mTrackerBean = mTrackerBean;
	}

	@Override
	/**
	 * 游戏进行线程
	 */
	public void run() {

		while (isFlag) {
			long start = System.currentTimeMillis();
			logic();
			if (mHandler != null) {
				Message msg = new Message();
				msg.what = Constant.MSG_FLASH;
				mHandler.sendMessage(msg);
			}
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(25);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/** 判断是否碰撞
	 * 
	 * @return
	 * @author Sephyioth */
	public int checkCollision() {
		if (mEngineerBean == null || mTrackerBean == null) {
			return Constant.COLLISION_NONE;
		}
		int eny = mEngineerBean.getLocalY();
		int enh = mEngineerBean.getHeight();
		int tray = mTrackerBean.getLocalY();
		int trah = mTrackerBean.getHeight();

		if (enh == Constant.INT_0 || eny == Constant.INT_0) {
			return Constant.COLLISION_NONE;
		}
		if (mEngineerBean.getEngineerStatus() != Constant.GAME_ENGINEERSTATUS_INVINCIBLE) {
			if (eny >= tray && eny <= tray + trah) {
				return Constant.COLLISION_ENGINEER;
			} else if (eny <= tray && eny + enh >= tray) {
				return Constant.COLLISION_ENGINEER;
			}
		}

		return Constant.COLLISION_NONE;
	}

	/** 处理游戏逻辑
	 * 
	 * @author Sephyioth */
	private void logic() {
		switch (mGameState) {
		case Constant.GAME_NORMAL_START:
		case Constant.GAME_ENDLESS:
		case Constant.GAME_TIME:
			if (mTrackerBean != null
					&& mEngineerBean.getEngineerStatus() != Constant.GAME_ENGINEERSTATUS_INVINCIBLE) {
				// mTrackerBean.logic();
			}
			if (mEngineerStatus == null) {
				mEngineerStatus = new EngineerBean(getRandomX());
				mEngineerStatus.setLayoutY(getRandomY());
				mEngineerStatus
						.setEngineerStatus(getRandomLeaderLevel() % 5 + 6);
			}
			if (mEngineerBean != null) {
				mEngineerBean.logic();
			}
			Integer collision = checkCollision();
			if (collision != Constant.COLLISION_NONE) {
				if (mHandler != null) {
					Message msg = new Message();
					msg.what = Constant.MSG_COLLISION;
					msg.obj = collision;
					mHandler.sendMessage(msg);

				}
			}
			long start = System.currentTimeMillis();
			mTime = (start - mLastTime) / 1000;
			break;
		case Constant.GAME_PAUSE:

			break;
		case Constant.GAME_WIN:

			break;
		case Constant.GAME_LOST:
			if (mHandler != null) {
				Message msg = new Message();
				msg.what = Constant.GAME_LOST;
				mHandler.sendMessage(msg);
			}
			break;
		default:
			break;
		}
	}

	public EngineerBean getEngineerStatus() {
		return mEngineerStatus;
	}

	public void setEngineerStatus(EngineerBean mEngineerStatus) {
		this.mEngineerStatus = mEngineerStatus;
	}

	/** 依据游戏者找到下一排雷区
	 * 
	 * @param engineery
	 * @return
	 * @author Sephyioth */
	private ThunderBean findThunderBean(int engineery) {
		int line = engineery;
		for (int i = 0; i < mThunderBeans.size(); i++) {
			if (mThunderBeans.get(i).getThunderY() == line) {
				return mThunderBeans.get(i);
			}
		}
		return null;
	}

	public Vector<LeaderBean> findLeaderByY(int y) {
		Vector<LeaderBean> vector = new Vector<LeaderBean>();
		for (int i = 0; i < mLeaderBeans.size(); i++) {
			if (y == mLeaderBeans.get(i).getLeaderY()) {
				vector.add(mLeaderBeans.get(i));
			}
		}
		return vector;
	}

	private boolean isAliveWithLeader(int x, int y) {

		Vector<LeaderBean> vector = findLeaderByY(y);

		for (int i = 0; i < vector.size(); i++) {
			LeaderBean bean = vector.get(i);
			if (x == bean.getLeaderX()) {
				if (mEngineerBean.getEngineerStatus() > bean.getLeaderLevel()) {
					return true;
				} else {
					return false;
				}
			}
		}

		return true;
	}

	public void forword() {
		forwordThunder();
		forwordLeader();
		forwordEngineerStatus();
	}

	/** 移除拦截者
	 * 
	 * @param bean
	 * @author Sephyioth */
	private void removeLeaderbyEngineer(EngineerBean bean) {
		for (int i = 0; i < mLeaderBeans.size(); i++) {
			LeaderBean leaderBean = mLeaderBeans.get(i);
			if (leaderBean.getLeaderX() == bean.getEngineerX()
					&& leaderBean.getLeaderY() == Constant.ENGINEER_DEFAULT_Y) {
				mLeaderBeans.get(i).setVisable(false);
				return;
			}
		}
	}

	/** 移除地雷
	 * 
	 * @param bean
	 * @author Sephyioth */
	private void removeThunderbyEngineer(EngineerBean bean) {
		for (int i = 0; i < mThunderBeans.size(); i++) {
			ThunderBean thunderBean = mThunderBeans.get(i);
			if (thunderBean.getThunderX() == bean.getEngineerX()
					&& thunderBean.getThunderY() == Constant.ENGINEER_DEFAULT_Y) {
				mThunderBeans.get(i).setVisable(false);
				return;
			}
		}
	}

	/** 向前移动拦截者
	 * 
	 * @author Sephyioth */
	private void forwordLeader() {
		for (int i = 0; i < mLeaderBeans.size(); i++) {
			LeaderBean bean = mLeaderBeans.get(i);
			int y = bean.getLeaderY() - 1;
			mLeaderBeans.get(i).setLeaderY(y);
		}
	}

	/** 清除失效的拦截者
	 * 
	 * @author Sephyioth */
	private void clearLeader() {
		for (int i = 0; i < mLeaderBeans.size(); i++) {
			if (mLeaderBeans.get(i).getLeaderY() < Constant.INT_0) {
				mLeaderBeans.remove(i);
			}
		}
	}

	/** 清除失效的雷区
	 * 
	 * @author Sephyioth */
	private void clearThunder() {
		for (int i = 0; i < mThunderBeans.size(); i++) {
			if (mThunderBeans.get(i).getThunderY() < 0) {
				mThunderBeans.remove(i);
			}
		}
	}

	/** 清除失效的数据
	 * 
	 * @author Sephyioth */
	public void clearUnusedBeans() {
		clearLeader();
		clearThunder();

	}

	/** 移除失效的leader和thunder
	 * 
	 * @param bean
	 * @author Sephyioth */
	public void remove(EngineerBean bean) {
		removeLeaderbyEngineer(bean);
		removeThunderbyEngineer(bean);
	}

	public void forwordEngineerStatus() {
		if (mEngineerStatus != null) {
			mEngineerStatus.forwordLayoutY();
			if (mEngineerStatus.getLayoutY() < Constant.INT_0) {
				mEngineerStatus = null;
			}
		}

	}

	/** 向前一格雷区
	 * 
	 * @author Sephyioth */
	private void forwordThunder() {
		for (int i = 0; i < mThunderBeans.size(); i++) {
			ThunderBean thunderBean = mThunderBeans.get(i);
			int y = thunderBean.getThunderY() - 1;
			mThunderBeans.get(i).setThunderY(y);
		}
	}

	/** 处理菜单的事件
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @author Sephyioth */
	private void dealMenuTouch(int x, int y, int width, int height) {

		int mode = (int) ((float) x / (width / 2))
				+ (int) ((float) y / (height / 2)) * 2;
		switch (mode) {
		case Constant.GAME_MODE_NORMAL:
			mGameState = Constant.GAME_NORMAL_START;
			mEndTimes = Constant.LEVEL_STACK;
			newGame();
			break;
		case Constant.GAME_MODE_ENDLESS:
			mGameState = Constant.GAME_ENDLESS;
			mEndTimes = Constant.INT_NEG;
			newGame();
			break;
		case Constant.GAME_MODE_TIME:
			mGameState = Constant.GAME_TIME;
			mEndTimes = Constant.LEVEL_STACK;
			newGame();
			break;
		case Constant.GAME_MODE_SETTING:
			Message msg = new Message();
			msg.what = Constant.MSG_SETTING;
			mHandler.sendMessage(msg);
			isFlag = false;
			break;
		default:
			break;
		}

	}

	/** 触发事件处理
	 * 
	 * @param x
	 * @author Sephyioth */
	private void deaStartlTouch(float x, int screenW) {
		ThunderBean thunderBean = findThunderBean(Constant.ENGINEER_STATUE_Y);

		if (thunderBean == null) {
			mGameState = Constant.GAME_WIN;
			return;
		}
		int thunderx = thunderBean.getLocalX();
		int thunderw = screenW / mGameLevel;

		// 正常模式下处理
		if (mEngineerStatus != null) {
			int ex = mEngineerStatus.getLocalX();
			// 得到加持状态
			if (x > ex
					&& x <= ex + thunderw
					&& mEngineerStatus.getLayoutY() == Constant.ENGINEER_STATUE_Y) {

				mEngineerBean.setEngineerStatus(mEngineerStatus
						.getEngineerStatus());
				mEngineerBean.setEngineerX(mEngineerStatus.getLayoutX());
				mEngineerStatus = null;
				forword();
				remove(mEngineerBean);
				clearUnusedBeans();
				dealGame(Constant.THUNDER_DEFAULT_COUNT - Constant.INT_1);
				resetTracker();
				return;
			}
		}
		// 通常模式下小兵触地雷
		if (mEngineerBean.getEngineerStatus() == Constant.GAME_ENGINEERSTATUS_NORMAL) {
			// 是否碰地雷
			if (x >= thunderx && x <= thunderx + thunderw) {
				mEngineerBean.setEngineerX(thunderBean.getThunderX());
				forword();
				clearUnusedBeans();
				remove(mEngineerBean);
				dealGame(Constant.THUNDER_DEFAULT_COUNT - Constant.INT_1);
				resetTracker();
				mScore++;
			} else {
				endGame();
			}
		} else {
			// 处理异常状态和无敌状态下的小兵
			int layoutx = (int) (x / (screenW / mGameLevel));
			if (isAliveWithLeader(layoutx, Constant.ENGINEER_STATUE_Y)
					&& isAliveWithThunder(layoutx, Constant.ENGINEER_STATUE_Y)) {
				mEngineerBean.setEngineerX(layoutx);
				forword();
				clearUnusedBeans();
				remove(mEngineerBean);
				dealGame(Constant.THUNDER_DEFAULT_COUNT - Constant.INT_1);
				resetTracker();
				mScore++;
			} else {
				endGame();
			}

		}

	}

	/** 判断是否撞雷
	 * 
	 * @param layoutx
	 * @param engineerStatueY
	 * @return
	 * @author Sephyioth */
	private boolean isAliveWithThunder(int layoutx, int engineerStatueY) {
		ThunderBean bean = findThunderBean(engineerStatueY);

		if (bean != null) {
			if (layoutx == bean.getLayoutX()) {
				if (mEngineerBean.getEngineerStatus() == Constant.GAME_ENGINEERSTATUS_INVINCIBLE
						|| mEngineerBean.getEngineerStatus() == Constant.GAME_ENGINEERSTATUS_NORMAL) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	/** 处理事件
	 * 
	 * @param event
	 * @param width
	 * @param height
	 * @author Sephyioth */
	public void dealTouch(MotionEvent event, int width, int height) {
		int eventX = (int) event.getX();
		int eventY = (int) event.getY();
		switch (mGameState) {
		case Constant.GAME_NORMAL_START:
		case Constant.GAME_ENDLESS:
		case Constant.GAME_TIME:
			if (!dealRefleash(eventX, eventY, width, height)) {
				if (eventY > height / 12) {
					deaStartlTouch(eventX, width);
				}
			}
			break;
		case Constant.GAME_PAUSE:

			break;
		case Constant.GAME_WIN:

			break;
		case Constant.GAME_LOST:
			mGameState = Constant.GAME_MENU;
			Message msg = new Message();
			msg.what = Constant.GAME_MENU;
			mHandler.sendMessage(msg);
			break;
		case Constant.GAME_MENU:
			dealMenuTouch(eventX, eventY, width, height);
			break;
		}
	}

	public void setSettingWidth(int width) {
		this.mWidth = width;
	}

	public void setSettingHeight(int height) {
		this.mHeight = height;
	}

	private boolean dealRefleash(int eventX, int eventY, int width, int height) {
		int localx = width * 4 / 5;
		int localy = height / 12;
		if (eventX > localx && eventX < localx + mWidth && eventY > localy
				&& eventY < localy + mHeight) {
			newGame();
			return true;
		}
		return false;
	}
}
