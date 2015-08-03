package com.sephyioth.Bean;

/** 类说明：拦截者等等的数据类
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午10:58:26 */
public class LeaderBean {
	// ** 常量 **/

	// ** 变量 **/
	private int mLeaderX;
	private int mLeaderY;
	private int mLeaderLevel;
	private int mLocalX;
	private int mLocalY;
	private int mWidth;
	private int mHeight;

	// ** 构造函数 **/
	public LeaderBean(int leaderX, int leaderY, int leaderLevel) {
		this.mLeaderX = leaderX;
		this.mLeaderY = leaderY;
		this.mLeaderLevel = leaderLevel;
	}

	// ** 成员方法 **/
	public int getLeaderLevel() {
		return mLeaderLevel;
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

	public void setLeaderLevel(int mLeaderLevel) {
		this.mLeaderLevel = mLeaderLevel;
	}

	public int getmLocalY() {
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

	public int getLeaderX() {
		return mLeaderX;
	}

	public void setLeaderX(int leaderX) {
		mLeaderX = leaderX;
	}

	public int getLeaderY() {
		return mLeaderY;
	}

	public void setLeaderY(int leaderY) {
		mLeaderY = leaderY;
	}

	// ** 静态方法 **/

	// ** 内部类接口 **/
}
