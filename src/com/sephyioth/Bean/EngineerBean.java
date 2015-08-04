package com.sephyioth.Bean;

import com.sephyioth.constant.Constant;

/** 类说明：工兵数据类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午10:35:16 */
public class EngineerBean extends BasicModelBean {
	// ** 常量 **/

	// ** 变量 **/
	private int mInvTime;
	private int mEngineerStatus;

	// ** 构造函数 **/
	public EngineerBean(int engineerX) {
		this.mLayoutX = engineerX;
		this.mLayoutY = Constant.ENGINEER_DEFAULT_Y;
	}

	// ** 成员方法 **/
	public int getEngineerX() {
		return mLayoutX;
	}

	public int getInvTime() {
		return mInvTime;
	}

	public void setEngineerX(int x) {
		this.mLayoutX = x;
	}

	public void setInvTime(int num) {
		this.mInvTime = num;
	}

	public int getEngineerStatus() {
		return mEngineerStatus;
	}

	public void setEngineerStatus(int mEngineerStatus) {
		this.mEngineerStatus = mEngineerStatus;
		this.mInvTime = Constant.DEFAULT_TIME;
	}

	@Override
	public void logic() {
		super.logic();
		mInvTime -= Constant.DEST_SPEED;
		if (mInvTime < Constant.INT_0) {
			mEngineerStatus = Constant.GAME_ENGINEERSTATUS_NORMAL;
		}
	}

	public void forwordLayoutY() {
		mLayoutY--;
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
