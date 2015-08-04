package com.sephyioth.Bean;

/** 类说明：拦截者等等的数据类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午10:58:26 */
public class LeaderBean extends BasicModelBean {
	// ** 常量 **/

	// ** 变量 **/
	private int mLeaderLevel;

	// ** 构造函数 **/
	public LeaderBean(int leaderX, int leaderY, int leaderLevel) {
		this.mLayoutX = leaderX;
		this.mLayoutY = leaderY;
		this.mLeaderLevel = leaderLevel;
	}

	// ** 成员方法 **/
	public int getLeaderLevel() {
		return mLeaderLevel;
	}

	public void setLeaderLevel(int mLeaderLevel) {
		this.mLeaderLevel = mLeaderLevel;
	}

	public void setLeaderX(int leaderX) {
		mLayoutX = leaderX;
	}

	public int getLeaderX() {
		return mLayoutX;
	}

	public int getLeaderY() {
		return mLayoutY;
	}

	public void setLeaderY(int leaderY) {
		mLayoutY = leaderY;
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
