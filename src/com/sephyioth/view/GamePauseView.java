package com.sephyioth.view;

import android.content.res.Resources;
import android.view.SurfaceView;

/** 类说明：游戏暂停界面
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-5 下午4:08:04 */
public class GamePauseView {
	// ** 常量 **/

	// ** 变量 **/
	private Resources mResources = null;
	private SurfaceView mParentView;

	// ** 构造函数 **/
	public GamePauseView(SurfaceView parentView) {
		mResources = parentView.getResources();
		this.mParentView = parentView;
	}
	// ** 成员方法 **/

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
