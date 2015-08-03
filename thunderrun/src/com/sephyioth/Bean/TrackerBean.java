package com.sephyioth.Bean;

/** 类说明：追踪者的数据类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-31 下午6:45:52 */
public class TrackerBean {
	// ** 常量 **/

	// ** 变量 **/
	private int x;
	private int y;
	private int count;
	private int mLocalX;
	private int mLocalY;
	private int mWidth;
	private int mHeight;

	// ** 构造函数 **/
	public TrackerBean(int x, int y, int count) {
		this.x = x;
		this.y = y;
		this.count = count;
	}

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

	// ** 成员方法 **/
	public int getX() {
		return x;
	}

	public int getLocalY() {
		return mLocalY;
	}

	public void setLocalY(int mLocalY) {
		this.mLocalY = mLocalY;
	}

	public int getLocalX() {
		return mLocalX;
	}

	public void setLocalX(int mLocalX) {
		this.mLocalX = mLocalX;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
