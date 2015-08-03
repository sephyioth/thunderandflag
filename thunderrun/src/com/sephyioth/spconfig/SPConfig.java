package com.sephyioth.spconfig;

import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 类说明：SP管理类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午11:36:56
 * 
 */
public class SPConfig {
	// ** 常量 **/
	private static final String TAG = "SPConfig ";
	public static final String RUNNING_TIME = "running time";
	public static final String ENGINER = "enginer";
	public static final String LEADER = "leader";
	public static final String THUNDER = "thunder";
	public static final String HIGH_SCORE = "high score temp";
	public static final String GAME_STATE = "game state";
	public static final String SCORE = "score";
	public static final String HAS_STATE = "hasState";
	// ** 变量 **/
	public static SPConfig mConfig;
	public static SharedPreferences mSettingPreferences;
	public static Editor mEditor;

	// ** 构造函数 **/
	public SPConfig(Context context) {
		if (context != null) {
			mSettingPreferences = PreferenceManager
					.getDefaultSharedPreferences(context);
			mEditor = mSettingPreferences.edit();
			mConfig = this;
		} else {
			Log.e(TAG, "init error");
		}
	}

	// ** 成员方法 **/
	public static SPConfig getConfig() {
		return mConfig;
	}

	public void saveStatus(boolean isHave) {
		if (mEditor != null) {
			mEditor.putBoolean(HAS_STATE, isHave);
			mEditor.commit();
		} else {
			Log.e(TAG, "save status error");
		}
	}

	public boolean getStatus() {
		if (mSettingPreferences == null) {
			Log.e(TAG, "get status error");
			return false;
		}
		return mSettingPreferences.getBoolean(HAS_STATE, false);
	}

	public void setInt(String key, int value) {
		if (mEditor == null) {
			Log.e(TAG, "get status error");
			return;
		}
		mEditor.putInt(key, value);
		mEditor.commit();
	}

	public void setFloat(String key, Float value) {
		if (mEditor == null) {
			Log.e(TAG, "get status error");
			return;
		}
		mEditor.putFloat(key, value);
		mEditor.commit();
	}

	public void setString(String key, String value) {
		if (mEditor == null) {
			Log.e(TAG, "get status error");
			return;
		}
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public void setLong(String key, Long value) {
		if (mEditor == null) {
			Log.e(TAG, "get status error");
			return;
		}
		mEditor.putLong(key, value);
		mEditor.commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setStringSet(String key, Set<String> value) {
		if (mEditor == null) {
			Log.e(TAG, "get status error");
			return;
		}
		mEditor.putStringSet(key, value);
		mEditor.commit();
	}

	public int getInt(String key) {
		if (mSettingPreferences == null) {
			Log.e(TAG, "get status error");
			return -1;
		}
		return mSettingPreferences.getInt(key, 0);
	}

	public float getFloat(String key) {
		if (mSettingPreferences == null) {
			Log.e(TAG, "get status error");
			return -1;
		}
		return mSettingPreferences.getFloat(key, 0);
	}

	public long getLong(String key) {
		if (mSettingPreferences == null) {
			Log.e(TAG, "get status error");
			return -1;
		}
		return mSettingPreferences.getLong(key, 0);
	}

	public String getString(String key) {
		if (mSettingPreferences == null) {
			Log.e(TAG, "get status error");
			return null;
		}
		return mSettingPreferences.getString(key, null);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Set<String> getStringSet(String key) {
		if (mSettingPreferences == null) {
			Log.e(TAG, "get status error");
			return null;
		}
		return mSettingPreferences.getStringSet(key, null);
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}

