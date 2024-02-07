
package BoardTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestBoardAct {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		List<String> list;
		List<String> testlist = new ArrayList<String>();
		DataOutputStream outClient;
		String lineString;
		String str2;
		String str1;
		String[] sar;
		int page = -1;
		boolean run = true;

		try {
			socket = new Socket("localhost", 8000);
			System.out.println("연결client");
			while (run) {
				try  {

					System.out.println("-----------------------------------------");
					System.out.print("1. 목록  2. 등록  3. 내용  4. 삭제  0. 종료 >>");
					page = sc.nextInt();
					switch (page) {
					case 1:
						try (DataInputStream inList = new DataInputStream(socket.getInputStream())){
							System.out.println("==========================================");
							System.out.println("번호   작성자            작성일");
							System.out.println("------------------------------------------");
							str1= inList.readUTF();
							System.out.println(str1);
							
						} catch (Exception e) {
							System.out.println("서버 1번 캐치");
							e.printStackTrace();
						}
						break;
					case 2:
						System.out.println("2번");
						break;
					case 3:
						System.out.println("3번");
						break;
					case 4:
						System.out.println("4번");

						break;
					case 0:
						run = false;
						System.exit(0);
						break;
					default:
						System.out.println("제대로 입력");
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
