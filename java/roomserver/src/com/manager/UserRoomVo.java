package com.manager;

import org.java_websocket.WebSocket;

/**
 * 用户房间对应的信息
 * @author jiangqianghua
 *
 */
public class UserRoomVo {

	private String roomId ; 
	
	private String userId ;

	private WebSocket conn ;
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public WebSocket getConn() {
		return conn;
	}

	public void setConn(WebSocket conn) {
		this.conn = conn;
	} 
	
}
