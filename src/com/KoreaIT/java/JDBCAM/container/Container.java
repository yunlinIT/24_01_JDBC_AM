package com.KoreaIT.java.JDBCAM.container;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.controller.ArticleController;
import com.KoreaIT.java.JDBCAM.controller.MemberController;
import com.KoreaIT.java.JDBCAM.dao.ArticleDao;
import com.KoreaIT.java.JDBCAM.dao.MemberDao;
import com.KoreaIT.java.JDBCAM.service.ArticleService;
import com.KoreaIT.java.JDBCAM.service.MemberService;
import com.KoreaIT.java.JDBCAM.session.Session;

public class Container { // 모든 클래스들에서 사용되는 객체들을 컨테이너에 자원처럼 모아 여기저기서 사용 할 수 있도록 함. 
						// 이렇게 해놓으면 다른 클래스에서 여기있는 자원 사용할대 Conainer.만 하면 됨. 대신 다 static이어야 다른 곳에서도 불러낼 수 있음.

	public static ArticleController articleController;
	public static MemberController memberController;

	public static ArticleService articleService;
	public static MemberService memberService;

	public static ArticleDao articleDao;
	public static MemberDao memberDao;

	public static Scanner sc;

	public static Connection conn;

	public static Session session;

	public static void init() { // 프로그램 실행하면 아래 객체들 만들어져있음
		sc = new Scanner(System.in);

		session = new Session();
		
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		
		articleService = new ArticleService();
		memberService = new MemberService();

		articleController = new ArticleController();
		memberController = new MemberController();

		

	}

}