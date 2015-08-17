package com.sephyioth.tools;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

/** 类说明： application manager tools
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-17 下午8:45:11 */
public class ApplicationManager {

	// ~ 常量区块
	// =========================================================================

	// ~ 成员变量区块
	// =========================================================================

	// ~ 构造函数区块
	// =========================================================================

	// ~ 方法区块
	// =========================================================================

	// ~ 静态方法区块
	// =========================================================================

	/** 取得版本号
	 * 
	 * @param context
	 * @return
	 * @author Sephyioth */
	public static String getApplicationVersion(Context context) {
		String version = "";
		if (context == null) {
			return "";
		}
		try {
			PackageManager pManager = context.getPackageManager();
			PackageInfo info = pManager.getPackageInfo(
					context.getPackageName(), 0);

			if (info.versionCode < 0 || info.versionName == null) {
				return "";
			}

			version = info.versionName + "  " + info.versionCode;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return version;
	}

	/** 取得包名
	 * 
	 * @param context
	 * @return
	 * @author Sephyioth */
	public static String getApplicationPackageName(Context context) {
		if (context == null) {
			return "";
		}
		return context.getPackageName();
	}

	/** 判断Intent是否有效
	 * 
	 * @param context
	 * @param intent
	 * @return
	 * @author Sephyioth */
	public static boolean isIntentAvailable(Context context, Intent intent) {
		if (context == null) {
			return false;
		}
		PackageManager packageManager = context.getPackageManager();

		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);

		return list.size() > 0;
	}

	/** 取得SD卡目录
	 * 
	 * @return
	 * @author Sephyioth */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().toString();
	}

	/** 反馈到App Store
	 * 
	 * @param context
	 * @author Sephyioth */
	public static void feedback2market(Context context) {
		Uri uri = Uri.parse("market://details?id="
				+ ApplicationManager.getApplicationPackageName(context));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (ApplicationManager.isIntentAvailable(context, intent)) {

			context.startActivity(intent);

		} else {

			Toast.makeText(context, "no market", Toast.LENGTH_LONG).show();
		}
	}

	/** 展示反馈dialog框
	 * 
	 * @param context
	 * @author Sephyioth */
	public static void showFeedBackDialog(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle("提示")
				.setMessage("您的反馈是我们更新的动力。")
				.setPositiveButton("goTo App Store",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ApplicationManager.feedback2market(context);
							}
						}).show();
	}
	// ~ 内部接口（类）区块
	// =========================================================================
}
