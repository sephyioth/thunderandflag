package com.sephyioth.Bean;

/** 类说明：地雷数据解析类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午10:47:21 */
public class ThunderBean {
	// ** 常量 **/

	// ** 变量 **/
	private int mThunderX;
	private int mThunderNum;
	private int mThunderY;
	private int mLocalX;
	private int mLocalY;
	private int mWidth;
	private int mHeight;

	// ** 构造函数 **/
	public ThunderBean(int x, int y) {
		this.mThunderY = y;
		this.mThunderX = x;
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

	public int getThunderNum() {
		return mThunderNum;
	}

	public int getmLocalY() {
		return mLocalY;
	}

	public void setLocalY(int mLocalY) {
		this.mLocalY = mLocalY;
	}

	public int getThunderY() {
		return mThunderY;
	}

	public void setThunderY(int thunderY) {
		mThunderY = thunderY;
	}

	public int getLocalX() {
		return mLocalX;
	}

	public void setLocalX(int mLocalX) {
		this.mLocalX = mLocalX;
	}

	public int getThunderX() {
		return mThunderX;
	}

	public void setThunderX(int mThunderX) {
		this.mThunderX = mThunderX;
	}

	public void setThunderNum(int mThunderNum) {
		this.mThunderNum = mThunderNum;
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
