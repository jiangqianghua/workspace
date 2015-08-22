package com.manager;

import java.util.HashMap;

import org.java_websocket.WebSocket;

/**
 * 用户对应Socket类
 * @author jiangqianghua
 *
 */
public class UserSocketManager extends HashMap<WebSocket, UserRoomVo>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public void addUserRoom(WebSocket conn ,String roomId , String userId)
	{
		UserRoomVo userRoom = this.get(conn);
		if(userRoom == null)
		{
			userRoom = new UserRoomVo() ;
			this.put(conn, userRoom);
		}
		
		userRoom.setRoomId(roomId) ; 
		userRoom.setUserId(userId);
	}
	
	public void deleUserRoom(WebSocket conn)
	{
		UserRoomVo userRoom = this.get(conn);
		
		if(userRoom == null)
			return ;
		
		this.remove(conn) ;
	}
	

}
