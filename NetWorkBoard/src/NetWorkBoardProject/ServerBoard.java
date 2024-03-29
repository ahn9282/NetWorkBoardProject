package NetWorkBoardProject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ServerBoard {
	static List<String> list = new ArrayList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(9282);
			while (true) {
				socket = serverSocket.accept();
				System.out.println("이용자가 들어왔습니다." );

				ServerOfBoard server = new ServerOfBoard(socket);
				server.start();
			
		}
		} catch (Exception e) {
		}

	}

}

class ServerOfBoard extends Thread {
	DataInputStream input;
	DataOutputStream output;
	Socket socket;
	String read;
	String phasestr;
	String title;
	String writer;
	String content;
	String liststr;
	String[] sarr;
	String numstr;
	int selectnum;
	int phase = 1;

	ServerOfBoard(Socket socket) {
		this.socket = socket;
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void printList(int a, int b) throws Exception {
		for (int i = 0; i < ServerBoard.list.size(); i++) {
			liststr = ServerBoard.list.get(i);
			sarr = liststr.split("/%/");
			try {
				if (a == 3) {
					output.writeUTF((i + 1) + ". "+"\t" + sarr[0] + "\t" + sarr[1] + "\t" + sarr[2] + "\n");
				} else if (a == 4) {
					if (i == b) {
						output.writeUTF(
								(i + 1) + ". "+"\t" + sarr[0] + "\t" + sarr[3] + "\t" + sarr[1] + "\t" + sarr[2] + "\n");
					}
				} else if (a == 5) {
					System.out.println((i + 1) + ". "+"\t" + sarr[0] + "\t" + sarr[1] + "\t" + sarr[2] + "\t" + sarr[1]);

				}
			} catch (IOException e) {
			}
		}
	}

	String twoLine() {
		return "============================================================================" + "\n";
	}

	String oneLine() {
		return "----------------------------------------------------------------------------" + "\n";
	}

	void showstate() {
		System.out.println("!!!!!!!!!실시간 게시판 !!!!!!!!!!!");
		System.out.print(twoLine());
		System.out.print(" 번호                제목                     작성자                                          날짜                               내용" + "\n");
		System.out.print(oneLine());
		if (ServerBoard.list.size() > 0) {
			try {
				printList(5, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.print(oneLine());
	}
	int checknum(String str) {
		selectnum = Integer.parseInt(str);
		if (ServerBoard.list.size() < selectnum) {
			try {
				output.writeUTF("해당 번호의 게시글은 없습니다." + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return phase = 1;
		} else {
			return selectnum -= 1;
		}
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while (input != null || output != null) {
			try {
				switch (phase) {
				case 1:
					
					output.writeUTF(twoLine());
					output.writeUTF(" 번호                   제목                           작성자                                                날짜" + "\n");
					output.writeUTF(oneLine());
					if (ServerBoard.list.size() > 0) {
						try {
							printList(3, 0);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					output.writeUTF(oneLine());
					output.writeUTF(
							"|| 1. 목록" + "\t" + "2. 등록" + "\t" + "3. 내용" + "\t" + "4. 삭제" + "\t" + "0. 종료 ||  -->>");
					output.writeUTF("select");
					phasestr = input.readUTF();
					phase = Integer.parseInt(phasestr);
					break;
					
				case 2:
					
					output.writeUTF("제목 >>");
					title = input.readUTF();
					output.writeUTF("내용 >>");
					content = input.readUTF();
					output.writeUTF("작성자 >>");
					writer = input.readUTF();
					Date date = new Date();
					SimpleDateFormat time = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a", Locale.US);
					String now = time.format(date);
					liststr = title + "/%/" + writer + "/%/" + now + "/%/" + content;
					ServerBoard.list.add(liststr);
					output.writeUTF("[ 등록 : 번호 : " + ServerBoard.list.size() 
					+ " 제목 : " + title + " 내용 : " + content + " 작성자 : " + writer
					+ " 등록 시간 : " + now + " ]" + "\n");
					output.writeUTF("글이 정상적으로 추가 되었습니다!!" + "\n");
					phase = 1;
					showstate();
					break;

				case 3:
					
					output.writeUTF(twoLine());
					output.writeUTF(" 번호                          제목                              작성자                                       날짜" + "\n");
					output.writeUTF(oneLine());
					if (ServerBoard.list.size() > 0)
						try {
							printList(3, 0);
						} catch (Exception e) {
							e.printStackTrace();
						}
					output.writeUTF(oneLine());
					output.writeUTF("위 게시판 중 하나를 선택해 주세요.-->>");
					numstr = input.readUTF();
					checknum(numstr);
					if(phase == 1)break;
					output.writeUTF(twoLine());
					output.writeUTF(" 번호             제목                   내용                            작성자                                            날짜" + "\n");
					output.writeUTF(oneLine());
					if (ServerBoard.list.size() > 0)
						try {
							printList(4, selectnum);
						} catch (Exception e) {
							e.printStackTrace();
						}
					output.writeUTF(oneLine());
					output.writeUTF(
							"|| 1. 목록" + "\t" + "2. 등록" + "\t" + "3. 내용" + "\t" + "4. 삭제" + "\t" + "0. 종료 ||  -->>");
					output.writeUTF("select");
					phasestr = input.readUTF();
					phase = Integer.parseInt(phasestr);
					break;
					
				case 4:
					
					output.writeUTF(twoLine());
					output.writeUTF(" 번호                       제목                           작성자                                                          날짜" + "\n");
					output.writeUTF(oneLine());
					if (ServerBoard.list.size() > 0)
						try {
							printList(3, 0);
						} catch (Exception e) {
							e.printStackTrace();
						}
					output.writeUTF(oneLine());
					output.writeUTF("위 글에서 삭제하실 글을 선택해 주세요.-->>");
					numstr = input.readUTF();
					checknum(numstr);
					if(phase == 1)break;
					ServerBoard.list.remove(selectnum);
					phase = 1;
					showstate();
					break;

				default:
					
					output.writeUTF("제대로 입력하세용~" + "\n");
					phase = 1;
					break;
					
				}
			} catch (Exception e) {
				System.out.println("한명의 이용자가 나갔습니다.");
				break;
			}

		}
	}
}