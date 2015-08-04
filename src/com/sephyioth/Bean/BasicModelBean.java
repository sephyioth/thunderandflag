package com.sephyioth.Bean;

/** 类说明：
 * 
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-8-3 下午12:48:19 */
public class BasicModelBean {
	// ** 常量 **/

	// ** 变量 **/
	protected int mLocalX;
	protected int mLocalY;
	protected int mLayoutX;
	protected int mLayoutY;
	protected int mWidth;
	protected int mHeight;
	protected boolean isVisable = true;

	// ** 构造函数 **/

	// ** 成员方法 **/
	public void setVisable(boolean isVisable) {
		this.isVisable = isVisable;
	}

	public boolean isVisable() {
		return isVisable;
	}

	public int getLayoutX() {
		return mLayoutX;
	}

	public void setLayoutX(int mLayoutX) {
		this.mLayoutX = mLayoutX;
	}

	public int getLayoutY() {
		return mLayoutY;
	}

	public void setLayoutY(int mLayoutY) {
		this.mLayoutY = mLayoutY;
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

	public void logic() {

	}
	// ** 静态方法 **/

	// ** 内部类接口 **/
}
