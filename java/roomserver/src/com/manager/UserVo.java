package com.manager;

import net.sf.json.JSONObject;

import org.java_websocket.WebSocket;

public class UserVo {
	
	private WebSocket conn ;
	/**
	 * 用户Id
	 */
	private String userId;
	
	/**
	 * 用户名称
	 */
	private String userName ;
	
	/** 进入房间时间*/
	private String userInDate ;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserInDate() {
		return userInDate;
	}

	public void setUserInDate(String userInDate) {
		this.userInDate = userInDate;
	}

	public WebSocket getConn() {
		return conn;
	}

	public void setConn(WebSocket conn) {
		this.conn = conn;
	}
	
	/**
	 * 给该用户发送数据
	 * @param jsonObj
	 */
	public void sendMsg(JSONObject jsonObj)
	{
		if(conn != null)
		{
			conn.send(jsonObj.toString());
		}
	}
	
	

}
