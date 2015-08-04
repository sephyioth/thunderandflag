package com.sephyioth.model;

import java.util.Vector;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
	public int mGameState = Constant.GAME_START;

	private long mScore = Constant.INT_0;
	private long mHighScore = Constant.INT_0;
	private int mLeaderNum = Constant.INT_1;
	private int mInvincibleTime = Constant.GAME_ENGINEERSTATUS_INVINCIBLE;

	private Vector<ThunderBean> mThunderBeans;
	private EngineerBean mEngineerBean;
	private TrackerBean mTrackerBean;
	private EngineerBean mEngineerStatus;
	private Vector<LeaderBean> mLeaderBeans;
	private Context mContext;
	private Handler mHandler;

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
		mGameState = Constant.GAME_START;
	}

	/** 新游戏
	 * 
	 * @author Sephyioth */
	public void newGame() {
		mGameState = Constant.GAME_START;
		mThunderBeans.removeAllElements();
		mEngineerBean = new EngineerBean(mGameLevel / 2);
		mScore = Constant.INT_0;
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

		for (int j = Constant.INT_0; j < mGameLevel; j++) {
			if (mThunderBeans.get(mThunderBeans.size() - 1).getThunderX() != j) {
				mLeaderBeans
						.add(new LeaderBean(j, line, getRandomLeaderLevel()));
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

		while (true) {
			long start = System.currentTimeMillis();
			if (mHandler != null) {
				Message msg = new Message();
				msg.what = Constant.MSG_FLASH;
				mHandler.sendMessage(msg);
			}
			logic();
			Integer collision = checkCollision();
			if (collision != Constant.COLLISION_NONE) {
				if (mHandler != null) {
					Message msg = new Message();
					msg.what = Constant.MSG_COLLISION;
					msg.obj = collision;
					mHandler.sendMessage(msg);

				}
			}
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(100);
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
		case Constant.GAME_START:
			if (mTrackerBean != null) {
				mTrackerBean.logic();
			}
			if (mEngineerStatus == null) {
				mEngineerStatus = new EngineerBean(getRandomX());
				mEngineerStatus.setLayoutY(getRandomY());
				mEngineerStatus.setEngineerStatus(getRandomLeaderLevel());
			}
			if (mEngineerBean != null) {
				mEngineerBean.logic();
			}
			break;
		case Constant.GAME_PAUSE:

			break;
		case Constant.GAME_STOP:

			break;
		case Constant.GAME_WIN:

			break;
		case Constant.GAME_LOST:

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
		int line = engineery + Constant.INT_1;
		for (int i = 0; i < mThunderBeans.size(); i++) {
			if (mThunderBeans.get(i).getThunderY() == line) {
				return mThunderBeans.get(i);
			}
		}
		return null;
	}

	/** 触发事件处理
	 * 
	 * @param x
	 * @author Sephyioth */
	public void dealTouch(float x, int screenW) {
		ThunderBean thunderBean = findThunderBean(Constant.ENGINEER_DEFAULT_Y);

		if (thunderBean == null) {
			mGameState = Constant.GAME_WIN;
			return;
		}
		int thunderx = thunderBean.getLocalX();
		int thunderw = screenW / mGameLevel;
		// 正常模式下处理
		if (mEngineerBean.getEngineerStatus() == Constant.GAME_ENGINEERSTATUS_NORMAL) {
			// 是否碰地雷
			if (mEngineerStatus != null) {
				int ex = mEngineerStatus.getLocalX();
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

				} else if (x >= thunderx && x <= thunderx + thunderw) {
					mEngineerBean.setEngineerX(thunderBean.getThunderX());
					forword();
					clearUnusedBeans();
					remove(mEngineerBean);
					dealGame(Constant.THUNDER_DEFAULT_COUNT - Constant.INT_1);
					resetTracker();
					mScore++;

				}
			}
		} else {
			int layoutx = (int) (x / (screenW / mGameLevel));
			if (mGameState == Constant.GAME_ENGINEERSTATUS_INVINCIBLE) {
				mEngineerBean.setEngineerX(layoutx);
				forword();
				clearUnusedBeans();
				remove(mEngineerBean);
				dealGame(Constant.THUNDER_DEFAULT_COUNT - Constant.INT_1);
				resetTracker();
				mScore++;
			} else {
				if (x >= thunderx && x <= thunderx + thunderw) {
					mGameState = Constant.GAME_LOST;
				} else if (isAliveWithLeader(layoutx,
						Constant.ENGINEER_STATUE_Y)) {
					mEngineerBean.setEngineerX(layoutx);
					forword();
					clearUnusedBeans();
					remove(mEngineerBean);
					dealGame(Constant.THUNDER_DEFAULT_COUNT - Constant.INT_1);
					resetTracker();
					mScore++;
				} else {
					mGameState = Constant.GAME_LOST;
				}

			}

		}

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

		return false;
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
}
