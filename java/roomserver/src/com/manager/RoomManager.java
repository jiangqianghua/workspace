package com.manager;

import java.util.HashMap;

import org.java_websocket.WebSocket;


/**
 * 房间管理器，房间信息需要从数据库初始化读取
 * @author jiangqianghua
 *
 */
public class RoomManager extends HashMap<String, RoomVo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private UserSocketManager userSocketManager = new UserSocketManager()  ;
	
	public void addUserInfo(WebSocket conn , String roomId ,UserVo userVo )
	{
		userSocketManager.addUserRoom(conn, roomId, userVo.getUserId());
		RoomVo roomVo = this.get(roomId) ;
		if(roomVo == null)
		{
			roomVo = new RoomVo() ;
			this.put(roomId, roomVo);
		}
		roomVo.addUserInfo(userVo);
		
		if(userVo != null)
			roomVo.sendMsgForUserIn(userVo.getUserId(), userVo.getUserName(), "进入教室");// 通知其其他用户该用户已经退出
 	}
	
	public void deleteUserInfo(WebSocket conn)
	{
		// 获取用户id
		UserRoomVo userRoom = userSocketManager.get(conn) ;
		if(userRoom == null)
			return ;
		
		String userId = userRoom.getUserId() ;
		String roomId = userRoom.getRoomId() ;
		

		if(userId == null || roomId == null)
			return ; // 该用户 或者该房间不存在
		RoomVo roomVo = this.get(roomId);
		if(roomVo == null)
			return ;
		
		// 获取用户
		UserVo user = roomVo.getUserVo(userId) ;
		
		// 删除用户
		roomVo.deleUserVo(userId) ;
		
		// 删除该用户其相关信息
		userSocketManager.deleUserRoom(conn);
		
		if(user != null)
			roomVo.sendMsgForLeave(userId, user.getUserName(), "离开教室");// 通知其其他用户该用户已经退出
	}
	
	/**
	 * 开始发送聊天消息
	 * @param fromUserId
	 * @param toUserId
	 */
	public void sendMsg(WebSocket conn ,String fromUserId , String msg ,String toUserId )
	{
		// 获取用户id
		UserRoomVo userRoom = userSocketManager.get(conn) ;
		if(userRoom == null)
			return ;
		RoomVo roomVo = this.get(userRoom.getRoomId());
		if(roomVo == null)
			return ;
		
		roomVo.sendMsg(fromUserId, msg, toUserId);
	}
	
	// 给该用户发送所有用户列表
	public void sendMsgUserList(String userId, String roomId)
	{
		RoomVo room = this.get(roomId);
		room.sendMsgUserList(userId, roomId);
	}
}
