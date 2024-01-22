package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.controller.ArticleController;
import com.KoreaIT.java.JDBCAM.controller.MemberController;

public class App {

	private Scanner sc;

	public App() { // 앱 생성자. 
		Container.init(); // 컨테이너에 있는 자원을 프로그램 시작과 동시에 실행한다는 함수
		this.sc = Container.sc; // 프로그램 시작과 동시에 스캐너 입력 받을 수 있게 자원 세팅
	}

	public void run() { // 프로그램 시작하면 명령어 입력받을 수 있게 하고, conn으로 DB와 연결
		System.out.println("==프로그램 시작==");

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");

				Container.conn = conn;

				int actionResult = action(cmd);

				if (actionResult == -1) {
					System.out.println("==프로그램 종료==");
					sc.close();
					break;
				}

			} catch (SQLException e) {
				System.out.println("에러 1 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int action(String cmd) { // 기능들 실행 할 수 있는 명령어 버튼 모음. int를 리턴 받을 수 있어. 왜? (이유는 아래에)

		if (cmd.equals("exit")) { // 해당 명령어 입력 하면 -1을 리턴하고 run 메서드에서 프로그램 종료
			return -1;
		}

		MemberController memberController = Container.memberController; // 컨테이너에 있는 new 멤버컨트롤러 객체 가져와
		ArticleController articleController = Container.articleController; // 컨테이너에 있는 new 아티클컨트롤러 객체 가져와 

		if (cmd.equals("member logout")) { // 명령어 입력 받으면 멤버컨트롤러에서 리턴 받은거 실행
			memberController.logout();
		} else if (cmd.equals("member profile")) {
			memberController.showProfile();
		} else if (cmd.equals("member login")) {
			memberController.login();
		} else if (cmd.equals("member join")) { // 
			memberController.doJoin();
		} else if (cmd.equals("article write")) {
			articleController.doWrite();
		} else if (cmd.equals("article list")) {
			articleController.showList();
		} else if (cmd.startsWith("article modify")) {
			articleController.doModify(cmd);
		} else if (cmd.startsWith("article detail")) {
			articleController.showDetail(cmd);
		} else if (cmd.startsWith("article delete")) {
			articleController.doDelete(cmd);
		} else {
			System.out.println("처리할 수 없는 명령어");
		}

		return 0; // 종료 명령어 외에 다른 명령어 실행 시 리턴하는 정수. 리턴을 뭔가 해줘야하니까 0으로 해놓은듯
	}
}