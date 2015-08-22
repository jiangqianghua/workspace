package com.manager;

import java.util.HashMap;

import org.java_websocket.WebSocket;


/**
 * �����������������Ϣ��Ҫ�����ݿ��ʼ����ȡ
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
			roomVo.sendMsgForUserIn(userVo.getUserId(), userVo.getUserName(), "�������");// ֪ͨ�������û����û��Ѿ��˳�
 	}
	
	public void deleteUserInfo(WebSocket conn)
	{
		// ��ȡ�û�id
		UserRoomVo userRoom = userSocketManager.get(conn) ;
		if(userRoom == null)
			return ;
		
		String userId = userRoom.getUserId() ;
		String roomId = userRoom.getRoomId() ;
		

		if(userId == null || roomId == null)
			return ; // ���û� ���߸÷��䲻����
		RoomVo roomVo = this.get(roomId);
		if(roomVo == null)
			return ;
		
		// ��ȡ�û�
		UserVo user = roomVo.getUserVo(userId) ;
		
		// ɾ���û�
		roomVo.deleUserVo(userId) ;
		
		// ɾ�����û��������Ϣ
		userSocketManager.deleUserRoom(conn);
		
		if(user != null)
			roomVo.sendMsgForLeave(userId, user.getUserName(), "�뿪����");// ֪ͨ�������û����û��Ѿ��˳�
	}
	
	/**
	 * ��ʼ����������Ϣ
	 * @param fromUserId
	 * @param toUserId
	 */
	public void sendMsg(WebSocket conn ,String fromUserId , String msg ,String toUserId )
	{
		// ��ȡ�û�id
		UserRoomVo userRoom = userSocketManager.get(conn) ;
		if(userRoom == null)
			return ;
		RoomVo roomVo = this.get(userRoom.getRoomId());
		if(roomVo == null)
			return ;
		
		roomVo.sendMsg(fromUserId, msg, toUserId);
	}
	
	// �����û����������û��б�
	public void sendMsgUserList(String userId, String roomId)
	{
		RoomVo room = this.get(roomId);
		room.sendMsgUserList(userId, roomId);
	}
}
