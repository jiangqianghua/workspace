
public class RunServer {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RoomServer roomServer  = new RoomServer(8887);
		roomServer.start() ;
		System.out.println("run server  port 8887");
	}

}
