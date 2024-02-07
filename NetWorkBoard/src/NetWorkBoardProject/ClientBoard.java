package NetWorkBoardProject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientBoard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		try {
			socket = new Socket("localhost", 9282);
			SendBoard send = new SendBoard(socket);
			ReceiveBoard recv = new ReceiveBoard(socket);
			recv.start();
			send.start();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}

class SendBoard extends Thread {
	Socket socket;
	DataOutputStream out;
	String insert;

	public SendBoard(Socket socket) {
		try {
			this.socket = socket;
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while (out != null) {
			try {
				insert = sc.nextLine();
				if (insert.equals("0")) {
					socket.close();
					break;
					}
				out.writeUTF(insert);
			} catch (Exception e) {
			} 

		}

	}
}

class ReceiveBoard extends Thread {
	Socket socket;
	DataInputStream in;
	String read;

	ReceiveBoard(Socket socket) {
		this.socket = socket;
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (in != null) {
			try {
				read = in.readUTF();
				System.out.print(read);
			} catch (Exception e) {
				
			}
		}
	}

}
