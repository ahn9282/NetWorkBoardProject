package NetWorkBoardProject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MysteyClient {
	static List<String> list;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		List<String> testlist = new ArrayList<String>();
		DataOutputStream outClient;
		String lineString;
		String str2;
		String str1;
		String[] sar;
		int page = -1;
		boolean run = true;
		BufferedReader inList;
		int linenum;
		

		try {
			socket = new Socket("localhost", 8);
			outClient = new DataOutputStream(socket.getOutputStream());
			 inList = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (run) {
				try {
					outClient.writeUTF("Check");
					System.out.println("-----------------------------------------");
					System.out.print("1. 목록  2. 등록  3. 내용  4. 삭제  0. 종료 >>");
					page = sc.nextInt();
					switch (page) {
					case 1:
						try {
						System.out.println("==========================================");
						System.out.println("번호   작성자            작성일");
						System.out.println("------------------------------------------");
						linenum =0;
						while(true) {
							str1 = inList.readLine();
							if (str1 == null)
								break;
							linenum +=1;
							sar = str1.split("/%/");
							System.out.println(linenum + ". " + sar[0] + sar[1] + sar[2]);
							System.out.println();
							
						}
						
						} catch (Exception e) {
							System.out.println("서버 1번 캐치");
						}
						break;
				case 2:
						Scanner scanner = new Scanner(System.in);
						outClient.writeUTF("Insert");
						System.out.print("제목 >>");
						String title = scanner.nextLine();
						System.out.print("내용 >>");
						String content = scanner.nextLine();
						System.out.print("작성자 >>");
						String writer = scanner.nextLine();
						Date date = new Date();
						SimpleDateFormat time = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a", Locale.US);
						String now = time.format(date);
						lineString = title + "/%/" + writer + "/%/" + now + "/%/" + content;
						outClient.writeUTF(lineString);
						testlist.add(lineString);
						for (int i = 0; i < testlist.size(); i++) {
							System.out.println(testlist.get(i));
						}
						System.out.println("글이 정상적으로 등록되었습니다.");
						page = 1;
						break;
					case 3:
						try {
							
						System.out.println("==========================================");
						System.out.println(" 번호    제목    작성자         작성일");
						System.out.println("------------------------------------------");
						linenum =0;
						for(int i=0;i<list.size();i++) {
							if (list == null)
								break;
							if(linenum <= list.size()) { str1 = list.get(linenum);
							linenum +=1;
							sar = str1.split("/%/");
							System.out.println(linenum + ". " + sar[0] + sar[3]+ sar[1] + sar[2]);
							System.out.println();
							}
						}
						} catch (Exception e) {
							// TODO: handle exception
						}
						page = 1;
						break;
					case 4:
						outClient.writeUTF("remove");
						System.out.print("몇번 글을 삭제하시겠습니까? >>");
						int readcontent = sc.nextInt() - 1;
						outClient.writeInt(readcontent);
						System.out.println("글이 정상적으로 삭제되었습니다.");

						page = 1;
						break;
					case 0:
						run = false;
						outClient.close();
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
