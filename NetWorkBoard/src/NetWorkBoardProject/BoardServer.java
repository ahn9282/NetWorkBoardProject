package NetWorkBoardProject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class BoardServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(1234);
			while(true) {
				socket = serverSocket.accept();
				ReceiveThread rThread = new ReceiveThread(socket);
				rThread.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

}
class ReceiveThread extends Thread{
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	String str = null;
	int num =0;
	public ReceiveThread(Socket socket) {
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(new FileOutputStream("data.dat"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		Scanner sc =new Scanner(System.in);
		boardSelectList();
		while(true) {
			switch(num) {
			case 1:
				boardSelectList();
			case 2:

			case 3:
				
			case 4:
			}
			
		}
	}
	

void boardSelectList(){
	
		System.out.println("============================================");
		System.out.println("번호  제목  작성자    작성일");
		//list 나열하기
	System.out.println("---------------------------------------------");
	System.out.print("1. 목록  2. 등록  3. 내용  4. 삭제  0. 종료 >>");
	
}

}
