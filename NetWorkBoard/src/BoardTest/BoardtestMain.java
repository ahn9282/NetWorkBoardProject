package BoardTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class BoardtestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestText tt = new TestText();
		tt.UpdateToUpperCase();
		tt.ReadText();
	}

}
class TestText {
	int num = 0;
	String str;
	int number =1;
	List<String> list = new ArrayList<>();
	Map<Integer, String> contentMap = new HashMap<>();

	void ReadText() {
		int index = 0;
		String liststr;
		String[] sar;
		try (BufferedReader br = new BufferedReader(new FileReader("system.ini"))) {
			System.out.println("번호 제목 작성자   작성일");
			System.out.println("------------------------------------------");
			while(true) {
				liststr = br.readLine();
				if(liststr == null)break;
				sar = liststr.split("	");
				System.out.println(liststr);
				System.out.println(sar[0] + sar[1] + sar[2]);
				System.out.println();
			}
			

		} catch (Exception e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
	}

	void UpdateToUpperCase() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("system.ini",true))) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("제목 >>");
			String title = scanner.nextLine();
			
			System.out.print("내용 >>");
			String content = scanner.nextLine();
			
			System.out.print("작성자 >>");
			String writer = scanner.nextLine();
			Date tumeInDate = new Date();
			SimpleDateFormat time = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a", Locale.US);

			num +=1;
			bw.write(String.valueOf(num)+ "\t");
			bw.write(title + "\t");
			bw.write(writer+ "\t");
			bw.write(" "+time.format(tumeInDate) + "\t");
			bw.write(content);
			bw.newLine();
			System.out.println("글이 정상적으로 등록되었습니다.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}