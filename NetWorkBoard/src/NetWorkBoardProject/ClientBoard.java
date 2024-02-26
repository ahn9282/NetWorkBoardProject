package NetWorkBoardProject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientBoard {
static boolean exit_0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		exit_0=false;
		try {
			socket = new Socket("127.0.0.1", 9282);
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
				if (insert.equals("0") && ClientBoard.exit_0 == true) {
					socket.close();
					sc.close();
					System.exit(0);
					break;
				}
				out.writeUTF(insert);
				ClientBoard.exit_0 = false;
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
				if(read.equals("select")) {
					ClientBoard.exit_0 = true;
				}
				else {
					System.out.print(read);
				}
			} catch (Exception e) {

			}
		}
	}

}
