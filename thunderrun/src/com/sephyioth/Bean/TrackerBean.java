package com.sephyioth.Bean;

/** ��˵����׷���ߵ�������
 * 
 * @author ���� E-mail:
 * @version ����ʱ�䣺2015-7-31 ����6:45:52 */
public class TrackerBean {
	// ** ���� **/

	// ** ���� **/
	private int x;
	private int y;
	private int count;
	private int mLocalX;
	private int mLocalY;
	private int mWidth;
	private int mHeight;

	// ** ���캯�� **/
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

	// ** ��Ա���� **/
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

	// ** ��̬���� **/

	// ** �ڲ���ӿ� **/
}
