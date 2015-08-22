package com.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.java_websocket.WebSocket;

/**
 * ������Ϣ
 * @author jiangqianghua
 *
 */
public class RoomVo {

	/** ����ID */
	private String roomId ;
	
	/** ��������*/
	private String roomName ; 
	
	private Map<String , UserVo> userMap ;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Map<String, UserVo> getUserMap() {
		return userMap;
	}

	
	/**
	 * ����û�
	 * @param uservo
	 */
	public void addUserInfo(UserVo userVo)
	{
		if(userMap == null)
			userMap = new HashMap<String, UserVo>();
		
		userMap.put(userVo.getUserId(), userVo);
	}
	
	/**
	 * ��ȡ�û���Ϣ
	 * @param userId
	 * @return
	 */
	public UserVo getUserVo(String userId)
	{
		if(userMap != null)
			return userMap.get(userId);
		return null ;
	}
	
	/**
	 * ɾ���û���Ϣ
	 * @param userId
	 */
	public void deleUserVo(String userId)
	{
		if(userMap == null)
			return ;
		if(userMap.containsKey(userId))
			userMap.remove(userId);
	}
	
	public void sendMsg(String fromUserId , String msg , String toUserId )
	{
		if(userMap == null)
			return ;
		UserVo fromUserVo = userMap.get(fromUserId);
		if(fromUserVo == null)
			return ;
		
	     // ����json��ʽ����
	     JSONObject result = new JSONObject();  
	     result.element("type", "onMessage");  
	     result.element("userId", fromUserVo.getUserId());  
	     result.element("userName", fromUserVo.getUserName());  
	     // ����ʱ��
	     Date date = new Date() ;
	     String time = date.getHours() + ":"+date.getMinutes();
	     result.element("time", time);
	     result.element("msg", msg);

		if(toUserId == null || "".equals(toUserId))
		{
		     result.element("chatType", "0"); // ����ȫ��
		}
		else
		{
			UserVo toUserVo = userMap.get(toUserId);
			if(toUserVo == null)
				return ;
			
			result.element("chatType", "1"); // ˽��
		    result.element("toUserId", toUserVo.getUserId());
		    result.element("toUser", toUserVo.getUserName());
		}
		
		
		// to all
		for(Entry<String, UserVo> entry:userMap.entrySet()){   
		     UserVo userVo = entry.getValue() ;
		     if(userVo == null)
		    	 continue ;
		     userVo.sendMsg(result);
		}   
	}
	
	public void sendMsgForLeave(String fromUserId ,String userName, String msg )
	{
		if(userMap == null)
			return ;
		
	     // ����json��ʽ����
	     JSONObject result = new JSONObject();  
	     result.element("type", "user_leave");  
	     result.element("userId", fromUserId);  
	     result.element("userName", userName);  
	     // ����ʱ��
	     Date date = new Date() ;
	     String time = date.getHours() + ":"+date.getMinutes();
	     result.element("time", time);
	     result.element("msg", msg);
		
		
		// to all
		for(Entry<String, UserVo> entry:userMap.entrySet()){   
		     UserVo userVo = entry.getValue() ;
		     if(userVo == null)
		    	 continue ;
		     userVo.sendMsg(result);
		}   
	}
	
	public void sendMsgForUserIn(String fromUserId ,String userName, String msg )
	{
		if(userMap == null)
			return ;
		
	     // ����json��ʽ����
	     JSONObject result = new JSONObject();  
	     result.element("type", "user_join");  
	     result.element("userId", fromUserId);  
	     result.element("userName", userName);  
	     // ����ʱ��
	     Date date = new Date() ;
	     String time = date.getHours() + ":"+date.getMinutes();
	     result.element("time", time);
	     result.element("msg", msg);
		
		
		// to all
		for(Entry<String, UserVo> entry:userMap.entrySet()){   
		     UserVo userVo = entry.getValue() ;
		     if(userVo == null)
		    	 continue ;
		     userVo.sendMsg(result);
		}   
	}
	
	public void sendMsgUserList(String userId , String roomId)
	{
		
		if(userMap == null)
			return ;
		
		
	    UserVo userVo = userMap.get(userId);
	    
	    if(userVo == null)
	    	return ;
	    
		String userList="";
		// ��ȡ�����û��б�
		for(Entry<String, UserVo> entry:userMap.entrySet()){   
		     UserVo userVo1 = entry.getValue() ;
		     if(userVo1 == null)
		    	 continue ;
		     if(userId.equals(userVo1.getUserId()))
		    	 continue ;
		     if(userList.equals(""))
		     {
		    	 userList+= userVo1.getUserId() + "&"+userVo1.getUserName();
		     }
		     else
		     {
		    	 userList += ",";
		    	 userList+= userVo1.getUserId() + "&"+userVo1.getUserName();
		     }
		}   
		if(userList.equals(""))
			return ;
		// 123&jiang,345&hudan,890&huojianhua
	    // ����json��ʽ����
	    JSONObject result = new JSONObject();  
	    result.element("type", "get_online_user");  
	    result.element("userlist", userList);  
	    	
	    userVo.sendMsg(result);
	    
	     
		
	}
	
}
