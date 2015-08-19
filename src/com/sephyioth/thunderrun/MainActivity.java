package com.sephyioth.thunderrun;

import com.sephyioth.constant.Constant;
import com.sephyioth.model.MainGame;
import com.sephyioth.spconfig.SPConfig;
import com.sephyioth.tools.ApplicationManager;
import com.sephyioth.view.GameMenuView;
import com.sephyioth.view.MainView;

import android.R.menu;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MainActivity extends Activity implements OnTouchListener {

	protected final static String TAG = "MainActivity";
	private MainView mView;
	private MainGame mGame;
	public boolean isInterappurt = false;

	private final Handler mHandler = new Handler() {

		@SuppressLint("WrongCall")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.GAME_NORMAL_START:
				mView.onDraw(mGame);
				break;
			case Constant.GAME_PAUSE:

				break;
			case Constant.GAME_ENDLESS:
				mView.onDraw(mGame);
				break;
			case Constant.GAME_WIN:
				mView.onDraw(mGame);
				break;
			case Constant.GAME_LOST:
				mView.onDraw(mGame);
				break;
			case Constant.MSG_FLASH:
				mView.onDraw(mGame);
				break;
			case Constant.MSG_SETTING:
				jumpToSetting();
				finish();
				break;
			case Constant.MSG_NEWGAME:
				mGame.newGame();
				break;
			case Constant.MSG_COLLISION:
				if (msg.obj instanceof Integer) {
					int cmd = (Integer) msg.obj;
					Message coMessage = new Message();
					if (cmd == Constant.COLLISION_FLAG) {
						coMessage.what = Constant.GAME_WIN;
						mGame.mGameState = Constant.GAME_WIN;
					} else {
						coMessage.what = Constant.GAME_LOST;
						mGame.mGameState = Constant.GAME_LOST;
					}
					mHandler.sendMessage(coMessage);
				}
			case Constant.GAME_MENU:
				
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@SuppressLint("WrongCall")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mView = new MainView(getBaseContext(), mHandler);
		mGame = new MainGame(getBaseContext(), mHandler);
		mGame.start();
		SPConfig spConfig = new SPConfig(getApplication());
		mView.setOnTouchListener(this);

		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("hasState")) {
				load();
			}
		} else {
			SPConfig.getConfig();
		}
		setContentView(mView);

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBoolean("hasState", true);
		save();
		isInterappurt = true;
	}

	@Override
	protected void onStop() {
		save();
		super.onStop();
	}

	protected void onPause() {
		super.onPause();
		save();

		isInterappurt = true;

	}

	/** 跳转到设置页面
	 * 
	 * @author Sephyioth */
	private void jumpToSetting() {
		Intent intent = new Intent(MainActivity.this, SettingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void save() {

		SPConfig spConfig = SPConfig.getConfig();
		spConfig.saveStatus(true);
		spConfig.setLong(SPConfig.SCORE, mGame.getScore());
		spConfig.setInt(SPConfig.GAME_STATE, mGame.getGameState());
	}

	protected void onResume() {
		super.onResume();
		load();
	}

	private void load() {
		int time = SPConfig.getConfig().getInt(SPConfig.RUNNING_TIME);
		if (time > 0 && time % 20 == 0) {
			ApplicationManager.showFeedBackDialog(MainActivity.this);
		}
		SPConfig spConfig = SPConfig.getConfig();
		long hightScore = spConfig.getLong(SPConfig.HIGH_SCORE);
		mGame.setHighScore(hightScore);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mGame.dealTouch(event, mView.getWidth(), mView.getHeight());
		}
		return false;
	}

}
