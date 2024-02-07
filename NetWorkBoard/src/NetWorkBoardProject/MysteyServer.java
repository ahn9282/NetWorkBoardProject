package NetWorkBoardProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MysteyServer {
	
	static boolean a= true;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(7899);
			while (true) {
				socket = serverSocket.accept();
				ReceiveThread rThread = new ReceiveThread(socket);
				SendThread sThread = new SendThread(socket);
				sThread.start();
				rThread.start();
				sThread.setPriority(10);
				rThread.setPriority(5);
				
			}
		} catch (Exception e) {
		}

	}

}

class ReceiveThread extends Thread {
	Socket socket;
	DataInputStream inServer;
	BufferedReader instr;
	String indata;

	ReceiveThread(Socket socket) throws Exception {
		this.socket = socket;
		inServer = new DataInputStream(socket.getInputStream());
		instr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	

	public static void saveTxt(String str) {
		try (BufferedWriter txtin = new BufferedWriter(
				new FileWriter("C:\\tools\\apache-tomcat-10.1.18\\webapps\\ROOT\\data.txt", true))) {
			txtin.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (inServer != null) {
			System.out.println("리시버~");
			try {
				indata = inServer.readUTF();
				if (indata.equals("Insert")) {
					indata = inServer.readUTF();
					indata = indata + "\n";
					saveTxt(indata);
					System.out.println("list================================");
					System.out.println("====================================");
				} else if (indata.equals("remove")) {
					int removenum;
					removenum = inServer.readInt();
					System.out.println(removenum + "번 글을 삭제");
					System.out.println("list================================");

					System.out.println("====================================");
				} else if (indata.equals("Check")) {
					
				}
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("익명의 이용자 로그아웃1");
				break;
			}
		}

	}
}

@SuppressWarnings("serial")
class SendThread extends Thread implements Serializable {
	Socket socket;
	List<String> list;
	ObjectOutputStream Send;
	String txtstr;
	String totaltxt;

	public SendThread(Socket socket) throws Exception {
		this.socket = socket;
		Send = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			System.out.println("전송~");
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i));
				}
			}
			Send.writeObject(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("익명의 이용자 로그아웃2");
		}
		while (Send != null) {
//			try {
//				list = BoardServer.mainlist;
//				System.out.println("전송~");
//				if (list != null) {
//					for (int i = 0; i < list.size(); i++) {
//						System.out.println(list.get(i));
//					}
//				}
//				Send.writeObject(list);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				System.out.println("익명의 이용자 로그아웃2");
//				continue;
//			}
		}
	}

}
