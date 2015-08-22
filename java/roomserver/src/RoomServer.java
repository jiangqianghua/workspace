import java.net.InetSocketAddress;
import java.util.Date;

import net.sf.json.JSONObject;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.manager.RoomManager;
import com.manager.UserVo;

/**
 *  ���������������
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
			
			// �����û����ͷ������б���Ϣ
			roomManager.sendMsgUserList(jsonObj.getString("userId"), jsonObj.getString("roomId"));
		}
		else if("sendMsg".equals(type))
		{
			// ����������Ϣ,���͸���ǰ����������
			roomManager.sendMsg(socket, jsonObj.getString("userId"), jsonObj.getString("msg"), jsonObj.getString("toUserId"));
		}
	}

	@Override
	public void onOpen(WebSocket socket, ClientHandshake handShake) {
		// �����û����룬�������userId,׼��һЩ��ʼ���ݸ��û�
		// �����µ�userId,�����û�
		long time = new Date().getTime() ;
		// ����json��ʽ
		JSONObject result = new JSONObject() ;
		result.element("type", "assignedUserId");
		result.element("userId", time+"");
		// ��ȡ�÷��������û�
		socket.send(result.toString());
		
		
		
	}

}
