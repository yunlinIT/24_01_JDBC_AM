package com.KoreaIT.java.JDBCAM;

import com.KoreaIT.java.JDBCAM.exception.SQLErrorException;

public class Main { 
	public static void main(String[] args) {
		try {
			new App().run(); // 프로그램 시작시 앱클래스에 있는 run 함수 실행
		} catch (SQLErrorException e) {
			System.err.println(e.getMessage());
			e.getOrigin().printStackTrace();
		}
	}
}