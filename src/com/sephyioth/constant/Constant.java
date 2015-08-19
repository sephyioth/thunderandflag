package com.sephyioth.constant;

/** 类说明：常量
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-7-28 下午11:09:20 */
public class Constant {
	// ** 常量 **/
	// 游戏运行状态
	public static final int GAME_NORMAL_START = 0;
	public static final int GAME_PAUSE = 1;
	public static final int GAME_STOP = 2;
	public static final int GAME_WIN = 3;
	public static final int GAME_LOST = -4;
	public static final int GAME_NORMAL_WON = 1;
	public static final int GAME_ENDLESS = 2;
	public static final int GAME_TIME = 5;
	public static final int GAME_ENDLESS_WON = 3;
	public static final int GAME_MENU = 4;
	

	public static final int GAME_MODE_NORMAL = 0;
	public static final int GAME_MODE_ENDLESS = 1;
	public static final int GAME_MODE_TIME = 2;
	public static final int GAME_MODE_SETTING = 3;

	public static final int DEFAULT_SCORE = 100;
	// 小兵的状态
	/** 无敌时间 */
	public static final int INVINCIBLE_TIME = 50;
	/** 排长 */
	public static final int GAME_ENGINEERSTATUS_PLATOON = 1;
	/** 连长 */
	public static final int GAME_ENGINEERSTATUS_COMPANY = 2;
	/** 营长 */
	public static final int GAME_ENGINEERSTATUS_BATTALION = 3;
	/** 团长 */
	public static final int GAME_ENGINEERSTATUS_HEAD = 4;
	/** 旅长 */
	public static final int GAME_ENGINEERSTATUS_BRIGADIER = 5;
	/** 师长 */
	public static final int GAME_ENGINEERSTATUS_MR = 6;
	/** 军长 */
	public static final int GAME_ENGINEERSTATUS_CAPTAINS = 7;
	/** 司令 */
	public static final int GAME_ENGINEERSTATUS_COMMANDER = 8;
	/** 无敌 */
	public static final int GAME_ENGINEERSTATUS_INVINCIBLE = 10;
	/** 工兵 */
	public static final int GAME_ENGINEERSTATUS_NORMAL = 0;
	// 刷新的时刻
	public static final int GAME_FLASH_TIME = 25;
	public static final int THUNDER_DEFAULT_COUNT = 10;
	public static final int LEADER_LEVEL = 8;
	// 默认追赶的时间
	public static final int LEADER_DEFAULT_TIME = 100;
	// 默认的等级
	public static final int DEFAULT_LEVEL = 3;
	// 通用变量
	public static final int INT_0 = 0;
	public static final int INT_NEG = -1;
	public static final int INT_1 = 1;
	// ** 变量 **/
	public static final int MSG_FLASH = 100;
	public static final int MSG_COLLISION = 101;
	public static final int MSG_AMIN_FINISH = 102;
	public static final int MSG_SETTING = 103;
	public static final int MSG_NEWGAME = 104;
	public static final int HEIGHT_SCORE = 7;
	// 小兵的等级
	public static final int LEADER_LEVEL_ENGINNER = 0; // 工兵
	public static final int LEADER_LEVEL_PLATOON = 1; // 排长
	public static final int LEADER_LEVEL_COMPANY = 2; // 连长
	public static final int LEADER_LEVEL_BATTALION = 3; // 营长
	public static final int LEADER_LEVEL_HEAD = 4; // 团长
	public static final int LEADER_LEVEL_BRIGADIER = 5; // 旅长
	public static final int LEADER_LEVEL_MR = 6; // 师长
	public static final int LEADER_LEVEL_CAPTAINS = 7; // 军长
	public static final int LEADER_LEVEL_COMMANDER = 8; // 司令
	public static final int LEADER_LEVEL_MINE = 9; // 无敌

	// 小兵初始位置
	public static final int ENGINEER_DEFAULT_Y = 2;
	public static final int ENGINEER_STATUE_Y = 3;
	// 追击者初始位置
	public static final int TRACKER_DEFAULT_Y = 1;
	// 碰撞事件
	public static final int COLLISION_NONE = 0;
	public static final int COLLISION_FLAG = 1;
	public static final int COLLISION_ENGINEER = 2;
	public static final int DEFAULT_EXSTATUS_TIME = 50;
	// 系统参数
	public static final int DEFAULT_SPEED = 1;
	public static final int DEFAULT_TIME = 1000;
	// 状态消失速度
	public static final int DEST_SPEED = 1;
	// 按键效果
	public static final int BUTTON_NONE = 0;
	public static final int BUTTON_TOP = 1;
	public static final int BUTTON_BOTTOM = 2;
	public static final int BUTTON_LEFT = 3;
	public static final int BUTTON_RIGHT = 4;
	public static final int BUTTON_RUNDER = 10;
	public static final int BUTTON_SPEED = 18;
	public static final int BUTTON_PADDING = 30;
	public static final int BUTTON_RUNDER_BIG = 30;
	// 模式
	public static final int LEVEL_STACK = 100;
	public static final int LEVEL_COUNT = 100;
	public static final int LEVEL = 30;
	public static final int MAX_LEVEL = 10;

	// ** 构造函数 **/

	// ** 成员方法 **/

	// ** 静态方法 **/

	// ** 内部类接口 **/
}