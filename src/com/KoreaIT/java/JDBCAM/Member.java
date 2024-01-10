package com.KoreaIT.java.JDBCAM;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {

	private int id;
	private LocalDateTime regDate;
	private String loginId;
	private String loginPw;	
	private String name;	
	
	
	public Member(int id, String loginId, String loginPw, String name) {
		this.id = id;
		this.loginId = loginId;
		this.loginPw = loginPw;
		
	}
	
	
	public Member(int id, LocalDateTime regDate, String loginId, String loginPw, String name) {

		this.id = id;
		this.regDate = regDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		
		
	}
	
	
	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.regDate = (LocalDateTime) memberMap.get("regDate");
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("name");
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public LocalDateTime getRegDate() {
		return regDate;
	}


	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public String getLoginPw() {
		return loginPw;
	}


	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
