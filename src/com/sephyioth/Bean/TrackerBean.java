package com.sephyioth.Bean;

import com.sephyioth.constant.Constant;

/** 类说明：追踪者的数据类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-31 下午6:45:52 */
public class TrackerBean extends BasicModelBean {
	// ** 常量 **/

	// ** 变量 **/
	private int count;

	// ** 构造函数 **/
	public TrackerBean(int x, int y, int count) {
		this.mLayoutX = x;
		this.mLayoutY = y;
		this.count = count;
	}

	// ** 成员方法 **/
	public int getX() {
		return mLayoutX;
	}

	public void setX(int x) {
		this.mLayoutX = x;
	}

	public int getY() {
		return mLayoutY;
	}

	public void setY(int y) {
		this.mLayoutY = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void logic() {
		super.logic();
		count -= Constant.DEFAULT_SPEED;
	}
	// ** 静态方法 **/

	// ** 内部类接口 **/
}
