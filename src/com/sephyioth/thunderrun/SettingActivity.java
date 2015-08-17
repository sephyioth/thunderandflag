package com.sephyioth.thunderrun;

import com.sephyioth.tools.ApplicationManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** 类说明：设置模式
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-17 下午5:44:00 */
public class SettingActivity extends Activity implements OnClickListener {

	// ** 常量 **/

	// ** 变量 **/
	private TextView mTextVersion;
	private RelativeLayout mLayoutFeedBack, mLayoutAbout;

	// ** 构造函数 **/

	// ** 成员方法 **/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		mTextVersion = (TextView) findViewById(R.id.text_version);
		mLayoutAbout = (RelativeLayout) findViewById(R.id.layout_about);
		mLayoutFeedBack = (RelativeLayout) findViewById(R.id.layout_feedback);
		mTextVersion.append(ApplicationManager
				.getApplicationVersion(getApplicationContext()));
		mLayoutAbout.setOnClickListener(this);
		mLayoutFeedBack.setOnClickListener(this);
	}

	// ** 静态方法 **/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_about:
			break;
		case R.id.layout_feedback:
			ApplicationManager.showFeedBackDialog(SettingActivity.this);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {

			if (keyCode == KeyEvent.KEYCODE_BACK) {

				Intent intent = new Intent(SettingActivity.this,
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(intent);
				finish();
			}
		}
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	// ** 内部类接口 **/
}
