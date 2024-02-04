package BoardTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class BoardtestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestText tt = new TestText();
		tt.run();
	}

}

class TestText {
	int linenum = 0;
	String str;
	int number = 1;
	List<String> list;
	Map<Integer, String> contentMap;
	int page = -1;
	int removeNum;
	int contnum;
	int countNum;
	String liststr;
	String[] sar;


	void UpdateToUpperCase() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("system.ini", true))) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("제목 >>");
			String title = scanner.nextLine();

			System.out.print("내용 >>");
			String content = scanner.nextLine();

			System.out.print("작성자 >>");
			String writer = scanner.nextLine();
			Date tumeInDate = new Date();
			SimpleDateFormat time = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a", Locale.US);

			bw.write(title + "\t");
			bw.write(writer + "\t");
			bw.write(" " + time.format(tumeInDate) + "\t");
			bw.write(content);
			bw.newLine();
			System.out.println("글이 정상적으로 등록되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		while (run) {

			try {
				try (BufferedReader br = new BufferedReader(new FileReader("system.ini"))) {
					list = new LinkedList<String>();
					System.out.println("==========================================");
					System.out.println("번호 제목 작성자   작성일");
					System.out.println("------------------------------------------");
					linenum =0;
					while (true) {
						liststr = br.readLine();
						if (liststr == null)
							break;
						linenum += 1;
						list.add(liststr);
						sar = liststr.split("	");
						System.out.println(linenum + ". " + sar[0] + sar[1] + sar[2]);
						System.out.println();
					}

				} catch (Exception e) {
					// TODO Auto-generated method stub
				}
				System.out.println("-----------------------------------------");
				System.out.print("1. 목록  2. 등록  3. 내용  4. 삭제  0. 종료 >>");
				page = sc.nextInt();
				switch (page) {
				case 1:
					continue;
				case 2:
					UpdateToUpperCase();
					continue;
				case 3:
					System.out.print("위 게시판 번호 중 하나를 입력해주세요. >>");
					contnum = sc.nextInt();
					System.out.println("===========================================");
					System.out.println("번호 제목 내용 작성자   작성일");
					System.out.println("--------------------------------------------");
					String contstr = list.get(contnum - 1);
					sar = contstr.split("	");
					System.out.println(sar[0] + " " + sar[1] + " " + sar[3] + " " + sar[2]);
					System.out.println();
					break;
				case 4:
					System.out.print("몇번 글을 삭제하시겠습니까? >>");
					removeNum = sc.nextInt();
					list.remove(removeNum - 1);
					int removCount = 0;
					try (BufferedWriter bw = new BufferedWriter(new FileWriter("system.ini", false))) {
						for (String removerstr : list) {
							removCount += 1;
							if (removeNum == removCount)
								System.out.println(removCount + "번이 삭제되었습니다.");
							bw.write(removerstr);
							bw.newLine();
						}
					} catch (Exception e) {

					}
					continue;
				case 0:
					run = false;
					break;
				case 5:

					System.out.println();
					for (int i = 0; i < list.size(); i++) {
						System.out.print(list.get(i) + "\t");
					}
					System.out.println();
				}
			}

			catch (Exception e) {

			}
		}

	}
}
