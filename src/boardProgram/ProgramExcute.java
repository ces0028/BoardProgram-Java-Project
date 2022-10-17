package boardProgram;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProgramExcute {
	
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, IOException {		
		List<Board> list = new ArrayList<Board>();

		boolean loopFlag = false;
		while (!loopFlag) {
			int selectNumber = displayMenu();
			switch (selectNumber) {
			case 1:
				inputContent(list);
				break;
			case 2:
				inputPastContent(list);
				break;
			case 3:
				outputContent(list);
				break;
			case 4:
				modifyContent(list);
				break;
			case 5:
				deleteContent(list);
				break;
			case 6:
				saveContent(list);
				break;
			case 7:
				loopFlag = true;
				break;
			default:
				System.out.println("보기를 확인 후 다시 입력해주세요.");
				continue;
			}
		}
		System.out.print("프로그램이 종료되었습니다.");
	}

	// Save post in file
	public static void saveContent(List<Board> list) throws IOException {
		if (list.isEmpty()) {
			System.out.println("현재 게시판에 작성된 글이 없습니다.");
			return;
		}
		
		OutputStream os = new FileOutputStream("C:/temp/BoardExample.txt");
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(list);
		oos.flush();
		oos.close();
		System.out.println("게시물이 저장되었습니다.");
	}

	// Delete post
	public static void deleteContent(List<Board> list) {
		if (list.isEmpty()) {
			System.out.println("현재 게시판에 작성된 글이 없습니다.");
			return;
		}
		
		scanner.nextLine();
		System.out.print("삭제할 게시물 번호를 입력해주세요 : ");
		int number = scanner.nextInt();
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNumber() == number) {
				list.remove(i);
				index = i;
				System.out.println("게시물이 정상적으로 삭제되었습니다.");
			}
		}
		
		if (index == -1) {
			System.out.println("해당 게시물 번호를 찾을 수 없습니다. 확인 후 다시 입력해주세요.");
			return;
		}
	}

	// Update post & Check password
	public static void modifyContent(List<Board> list) {
		if (list.isEmpty()) {
			System.out.println("현재 게시판에 작성된 글이 없습니다.");
			return;
		}
		
		scanner.nextLine();
		System.out.print("수정할 게시물 번호를 입력해주세요 : ");
		int number = scanner.nextInt();
		Board board = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNumber() == (number)) {
				board = list.get(i);
				scanner.nextLine();
				System.out.print("비밀번호를 입력하세요 : ");
				String password = scanner.nextLine();
				if (list.get(i).getPassword().equals(password)) {
					System.out.print("제목을 입력하세요.\n제목 > ");
					String title = scanner.nextLine();
					System.out.print("내용을 입력하세요.\n내용 > ");
					String content = scanner.nextLine();
					board.setTitle(title);
					board.setContent(content);
					System.out.println("\n" + board);
					System.out.println("게시물이 정상적으로 수정되었습니다.");
					break;
				}
				System.out.println("비밀번호가 맞지 않습니다.");
				return;
			}

		}
		if (board == null) {
			System.out.println("해당 게시물 번호를 찾을 수 없습니다. 확인 후 다시 입력해주세요.");
			return;
		}
	}

	// List view & View details
	public static void outputContent(List<Board> list) {
		if (list.isEmpty()) {
			System.out.println("현재 게시판에 작성된 글이 없습니다.");
			return;
		}
		
		scanner.nextLine();
		System.out.println("번호\t제목\t작성자\t작성일자");
		Collections.sort(list);
		for (Board board : list) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setNumber(i + 1);
				list.get(i).MakePassword();
			}
			board.viewDetails();
		}

		System.out.print("\n상세내역을 확인하시겠습니까? (Y/N) : ");
		String selectYesNo = scanner.nextLine();
		switch (selectYesNo) {
		case "Y":
		case "y":
			outputDetailContent(list);
			break;
		case "N":
		case "n":
			break;
		default:
			System.out.println("보기를 확인 후 다시 입력해주세요.");
			return;
		}
	}

	// View details
	public static void outputDetailContent(List<Board> list) {
		if (list.isEmpty()) {
			System.out.println("현재 게시판에 작성된 글이 없습니다.");
		}

		System.out.print("게시물 번호를 입력하세요 : ");
		int number = scanner.nextInt();
		int successOrFailure = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNumber() == (number)) {
				System.out.println(list.get(number - 1));
				System.out.println();
				successOrFailure = 1;
			}
		}
		
		if(successOrFailure == 0) {
			System.out.println("해당 게시물 번호를 찾지 못했습니다. 확인 후 다시 입력해주세요.");
			return;
		}
	}
	
	// Read data from file
	public static void inputPastContent(List<Board> list) throws ClassNotFoundException, IOException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("C:/temp/BoardExample.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("불러올 게시물이 없습니다. 먼저 게시물을 작성해주세요.");
			return;
		} catch (IOException e) {
			System.out.println("예외가 발생했습니다.");
		}
		
		Object object = ois.readObject();
		if (object instanceof List<?>) {
			List<Board> temporaryList = (List<Board>) object;
			for (Board board : temporaryList) {
				list.add(board);
			}
			System.out.println("게시물 데이터를 불러왔습니다.");
			ois.close();
			return;
		}
	}
	
	// Create new post
	public static void inputContent(List<Board> list) {
		int number = 1 + list.size();
		String title = "제목" + number;
		String content = "내용" + number;
		String writer = createName();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
		String date = sdf.format(now);

		list.add(new Board(number, title, content, writer, date));
		for (Board board : list) {
			board.MakePassword();
		}
		System.out.println("데이터가 정상적으로 입력되었습니다.");
	}

	// Create random number
	public static int createNumber(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	// Create writer name
	public static String createName() {
		List<String> lastName = new ArrayList<String>(Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임",
				"한", "오", "서", "신", "권", "황", "안", "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남",
				"심", "노", "정", "하", "곽", "성", "차", "주", "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원",
				"천", "방", "공", "강", "현", "함", "변", "염", "양", "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길",
				"주", "연", "방", "위", "표", "명", "기", "반", "왕", "금", "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여",
				"진", "어", "은", "편", "구", "용"));

		List<String> firstName = new ArrayList<String>(Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근",
				"기", "길", "나", "남", "노", "누", "다", "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마",
				"만", "명", "무", "문", "미", "민", "바", "박", "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선",
				"설", "섭", "성", "세", "소", "솔", "수", "숙", "순", "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영",
				"예", "오", "옥", "완", "요", "용", "우", "원", "월", "위", "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎",
				"자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준", "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘",
				"충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형", "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘",
				"희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비", "솜", "공", "면", "탁", "온", "디", "항", "후",
				"려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼", "열", "웅", "분", "변", "양", "출", "타",
				"흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠", "흔", "악", "람", "뜸", "권", "복",
				"심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련"));

		String name1 = lastName.get((int) (Math.random() * (lastName.size())));
		String name2 = firstName.get((int) (Math.random() * (lastName.size())));
		String name3 = firstName.get((int) (Math.random() * (lastName.size())));

		return name1 + name2 + name3;
	}

	// Display program menu
	public static int displayMenu() {
		System.out.println("+----------------------------------------------------------------------------+");
		System.out.println("| 1. 신규작성 | 2. 불러오기 | 3. 목록보기 | 4. 수정하기 | 5. 삭제하기 | 6. 파일저장 | 7. 종료하기 |");
		System.out.println("+----------------------------------------------------------------------------+");
		System.out.print("선택 > ");
		int selectNumber = scanner.nextInt();
		return selectNumber;
	}
}