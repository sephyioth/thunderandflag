package com.sephyioth.Bean;

/** 类说明：地雷数据解析类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午10:47:21 */
public class ThunderBean extends BasicModelBean {
	// ** 常量 **/

	// ** 变量 **/
	private int mThunderNum;

	// ** 构造函数 **/
	public ThunderBean(int x, int y) {
		this.mLayoutY = y;
		this.mLayoutX = x;
	}

	// ** 成员方法 **/

	public int getThunderNum() {
		return mThunderNum;
	}

	public int getThunderY() {
		return mLayoutY;
	}

	public void setThunderY(int thunderY) {
		mLayoutY = thunderY;
	}

	public int getThunderX() {
		return mLayoutX;
	}

	public void setThunderX(int mThunderX) {
		this.mLayoutX = mThunderX;
	}

	public void setThunderNum(int mThunderNum) {
		this.mThunderNum = mThunderNum;
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
