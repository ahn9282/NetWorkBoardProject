package NetWorkBoardProject;

import java.net.Socket;

public class BoardClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
			try {
				socket = new Socket("localhost",7899);
				ReceiveThread NetWorkBoard = new ReceiveThread(socket);
				NetWorkBoard.start();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
