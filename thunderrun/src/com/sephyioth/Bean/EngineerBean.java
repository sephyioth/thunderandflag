package com.sephyioth.Bean;

/** 类说明：工兵数据类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午10:35:16 */
public class EngineerBean {
	// ** 常量 **/

	// ** 变量 **/
	private int mEngineerX;
	private int mThunderTime;
	private int mEngineerStatus;
	private int mLocalX;
	private int mLocalY;
	private int mWidth;
	private int mHeight;

	// ** 构造函数 **/
	public EngineerBean(int engineerX, int thunderNum) {
		this.mEngineerX = engineerX;
		this.mThunderTime = thunderNum;
	}

	// ** 成员方法 **/
	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public void setHeight(int mHeight) {
		this.mHeight = mHeight;
	}

	public int getEngineerX() {
		return mEngineerX;
	}

	public int getThunderTime() {
		return mThunderTime;
	}

	public void setEngineerX(int x) {
		this.mEngineerX = x;
	}

	public int getLocalX() {
		return mLocalX;
	}

	public void setLocalX(int mLocalX) {
		this.mLocalX = mLocalX;
	}

	public int getLocalY() {
		return mLocalY;
	}

	public void setLocalY(int mLocalY) {
		this.mLocalY = mLocalY;
	}

	public void setThunderT(int num) {
		this.mThunderTime = num;
	}

	public int getEngineerStatus() {
		return mEngineerStatus;
	}

	public void setEngineerStatus(int mEngineerStatus) {
		this.mEngineerStatus = mEngineerStatus;
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
