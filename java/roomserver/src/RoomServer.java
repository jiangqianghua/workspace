import java.net.InetSocketAddress;
import java.util.Date;

import net.sf.json.JSONObject;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.manager.RoomManager;
import com.manager.UserVo;

/**
 *  房间服务器管理类
 * @author jiangqianghua
 */
public class RoomServer extends WebSocketServer {

	RoomManager roomManager = new RoomManager() ;
	public RoomServer(int port) {
		super( new InetSocketAddress( port ) );
	}
	
	public RoomServer(InetSocketAddress address) {
		super(address);
	}

	
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		
		roomManager.deleteUserInfo(conn);
	}

	@Override
	public void onError(WebSocket socket, Exception exc) {
			System.out.println("onError  " + exc.toString());
	}

	@Override
	public void onMessage(WebSocket socket, String msg) {
		// {"type":"userIn" , "roomId":"123" , "userName":"jiang" , "userId":"1234567" , "userInDate":"2014-05-07 01:00:00"}
		// {"type":"sendMsg" , "userId":"1234567" ,"msg":"hello","toUserId":"11111"}
		
		JSONObject jsonObj = JSONObject.fromObject(msg);
		String type = jsonObj.getString("type");
		if("userIn".equals(type))
		{
			UserVo userVo = new UserVo() ;
			userVo.setUserId(jsonObj.getString("userId"));
			userVo.setUserInDate(jsonObj.getString("userInDate"));
			userVo.setUserName(jsonObj.getString("userName"));
			userVo.setConn(socket);
			roomManager.addUserInfo(socket, jsonObj.getString("roomId"), userVo);
			
			// 给该用户发送房间人列表信息
			roomManager.sendMsgUserList(jsonObj.getString("userId"), jsonObj.getString("roomId"));
		}
		else if("sendMsg".equals(type))
		{
			// 发送聊天消息,发送给当前教室所有人
			roomManager.sendMsg(socket, jsonObj.getString("userId"), jsonObj.getString("msg"), jsonObj.getString("toUserId"));
		}
	}

	@Override
	public void onOpen(WebSocket socket, ClientHandshake handShake) {
		// 有新用户进入，给其分配userId,准备一些初始数据给用户
		// 生成新的userId,发给用户
		long time = new Date().getTime() ;
		// 构造json格式
		JSONObject result = new JSONObject() ;
		result.element("type", "assignedUserId");
		result.element("userId", time+"");
		// 获取该房间所有用户
		socket.send(result.toString());
		
		
		
	}

}
