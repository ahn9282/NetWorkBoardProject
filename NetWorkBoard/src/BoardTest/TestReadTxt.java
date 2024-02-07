package BoardTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class TestReadTxt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(8000);
			while (true) {
				socket = serverSocket.accept();
				System.out.println("client 입장");
				SendThread sendThread = new SendThread(socket);
				sendThread.start();
				sendThread.getTxt();
			}
		} catch (Exception e) {
		}

	
	}

}

@SuppressWarnings("serial")
class SendThread extends Thread implements Serializable {
	Socket socket;
	List<String> list = new ArrayList<String>();
	PrintWriter Send;
	String txtstr;
	String totaltxt;
	BufferedReader gettxt;
	public SendThread(Socket socket) throws Exception {
		this.socket = socket;
		Send = new PrintWriter(socket.getOutputStream());
	}

	public void getTxt() {
		try (BufferedReader gettxt = new BufferedReader(new FileReader("test.txt"))) {
			while (true) {
				txtstr = gettxt.readLine();
				if (txtstr == null)
					break;
				totaltxt = totaltxt.concat(txtstr);
			}
			System.out.println(totaltxt);
		} catch (Exception e) {

		}
	}

	@Override
	public void run() {
		while (Send != null) {
			System.out.println("보내는거");
			totaltxt = "";
			try (BufferedReader gettxt = new BufferedReader(new FileReader("test.txt"))){
				while(true) {
					txtstr = gettxt.readLine();
					if(txtstr == null)break;
					totaltxt = totaltxt + txtstr + "\n";
				}
				System.out.println(totaltxt);
				Send.println(totaltxt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("익명의 이용자 로그아웃2");
				e.printStackTrace();
				break;
			}
		}
	}

}