package com.manager;

import net.sf.json.JSONObject;

import org.java_websocket.WebSocket;

public class UserVo {
	
	private WebSocket conn ;
	/**
	 * �û�Id
	 */
	private String userId;
	
	/**
	 * �û�����
	 */
	private String userName ;
	
	/** ���뷿��ʱ��*/
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
	 * �����û���������
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
