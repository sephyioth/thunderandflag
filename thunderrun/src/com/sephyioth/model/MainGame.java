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
	private Vector<LeaderBean> mLeaderBeans;
	private Context mContext;
	private Handler mHandler;

	/** ��Ϸ��ݴ�����
	 * 
	 * @param context
	 * @param view */
	public MainGame(Context context, Handler mHandler) {
		if (mHandler == null) {
			Log.e(TAG, "handler is null");
			Toast.makeText(context, "program is error", Toast.LENGTH_LONG)
					.show();
		}
		mContext = context;
		this.mHandler = mHandler;
		mThunderBeans = new Vector<ThunderBean>();
		mEngineerBean = new EngineerBean(mGameLevel / 2, Constant.INT_0);
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
		mEngineerBean = new EngineerBean(mGameLevel / 2, Constant.INT_0);
		mScore = Constant.INT_0;
		dealGame(Constant.ENGINEER_DEFAULT_Y + Constant.INT_1);
		resetLeader();
	}

	/** 重置Leader
	 * 
	 * @author Sephyioth */
	public void resetLeader() {
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
		addThunder(line);
		addLeader(line);
	}

	/** 增加拦截者
	 * 
	 * @author Sephyioth */
	private void addLeader(int line) {
		if (mLeaderBeans != null) {
			mLeaderBeans = new Vector<LeaderBean>();
		}
		for (int i = Constant.INT_0; i < mThunderBeans.size(); i++) {
			for (int j = 0; j < mGameLevel; j++) {
				if (mThunderBeans.get(i).getThunderY() != j) {
					mLeaderBeans.add(new LeaderBean(j, mThunderBeans.get(i)
							.getThunderY(), getRandomLeaderLevel()));
				}
			}
		}
	}

	/** 增加地雷
	 * 
	 * @author Sephyioth */
	private void addThunder(int line) {
		if (mThunderBeans != null) {
			mThunderBeans = new Vector<ThunderBean>();
		}
		if (mThunderBeans.size() < Constant.THUNDER_DEFAULT_COUNT) {
			for (int i = line; i < Constant.THUNDER_DEFAULT_COUNT; i++) {
				mThunderBeans.add(new ThunderBean(getRandomX(), i));
			}
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

	/** ��Ϸ�Ƿ�ʧ��
	 * 
	 * @return
	 * @author Sephyioth */
	public boolean gameLost() {
		return (mGameState == Constant.GAME_LOST);
	}

	/** �Ƿ�
	 * 
	 * @return
	 * @author Sephyioth */
	public boolean isActive() {
		return !(gameWon() || gameLost());
	}

	/** ��Ϸ����
	 * 
	 * @author Sephyioth */
	private void endGame() {
		if (mScore >= mHighScore) {
			mHighScore = mScore;
			recordHighScore();
		}
	}

	/** ��¼��߷���
	 * 
	 * @author Sephyioth */
	private void recordHighScore() {
		SPConfig.getConfig().setLong(SPConfig.HIGH_SCORE, mHighScore);
	}

	/** �Ƿ���Լ�����Ϸ
	 * 
	 * @return
	 * @author Sephyioth */
	public boolean canContinue() {
		return !(mGameState == Constant.GAME_ENDLESS || mGameState == Constant.GAME_ENDLESS_WON);
	}

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

	private void logic() {
		switch (mGameState) {
		case Constant.GAME_START:
			if (mTrackerBean != null) {
				int count = mTrackerBean.getCount() - Constant.DEFAULT_SPEED;
				// mTrackerBean.setCount(count);
			}
			mScore++;
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

	private ThunderBean findThunderBean(int engineery) {
		for (int i = 0; i < mThunderBeans.size(); i++) {
			if (mThunderBeans.get(i).getThunderY() == engineery
					+ Constant.INT_1) {
				return mThunderBeans.get(i);
			}
		}
		return null;
	}

	public void isTouch(float x) {
		ThunderBean thunderBean = findThunderBean(Constant.ENGINEER_DEFAULT_Y);

		if (thunderBean == null) {
			mGameState = Constant.GAME_WIN;
			return;
		}
		int thunderx = thunderBean.getLocalX();
		int thunderw = thunderBean.getWidth();
		if (mEngineerBean.getEngineerStatus() == Constant.GAME_ENGINEERSTATUS_NORMAL) {
			if (x >= thunderx && x <= thunderx + thunderw) {
				mEngineerBean.setEngineerX(thunderBean.getThunderX());
				forwordThunder();
				forwordLeader();
				clearBeans();
			}
		} else {

		}

	}

	private void forwordLeader() {
		for (int i = 0; i < mLeaderBeans.size(); i++) {
			LeaderBean bean = mLeaderBeans.get(i);
			int y = bean.getLeaderY() - 1;
			mLeaderBeans.get(i).setLeaderY(y);
		}
	}

	private void clearLeader() {
		for (int i = 0; i < mLeaderBeans.size(); i++) {
			if (mLeaderBeans.get(i).getLeaderY() < Constant.INT_0) {
				mLeaderBeans.remove(i);
			}
		}
	}

	private void clearThunder() {
		for (int i = 0; i < mThunderBeans.size(); i++) {
			if (mThunderBeans.get(i).getThunderY() < 0) {
				mThunderBeans.remove(i);
			}
		}
	}

	private void clearBeans() {
		clearLeader();
		clearThunder();
	}

	private void forwordThunder() {
		for (int i = 0; i < mThunderBeans.size(); i++) {
			ThunderBean thunderBean = mThunderBeans.get(i);
			int y = thunderBean.getThunderY() - 1;
			mThunderBeans.get(i).setThunderY(y);
		}
	}
}
