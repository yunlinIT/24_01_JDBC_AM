package com.KoreaIT.java.JDBCAM.session;

import com.KoreaIT.java.JDBCAM.dto.Member;

public class Session {

	public Member loginedMember;
	public int loginedMemberId;

	public Session() { // 생성자. 세션객체 생성시 로그인된회원이 없는 걸로 시작
		loginedMemberId = -1; // 로그인상태 아니라는 것을 -1이라고 표현
	}

	public void login(Member member) { // 로그인 함수. 로그인한 회원을 매개변수로 받아서 아래 변수들에 저장
		loginedMember = member; // 회원의 정보를(1줄을 통채로) 담아
		loginedMemberId = member.getId(); // 회원의 번호를 담아
	}

	public void logout() { // 로그아웃 함수 실행되면 로그인된 회원에 -1 설정하고, 로그인된 회원은 null이라고 하고 로그아웃됐다고 출력
		loginedMember = null;
		loginedMemberId = -1;
		System.out.println("로그아웃 됨");
	}

	public boolean isLogined() { //로그인된 상태인지 아닌지 (참/거짓) 알려주는 함수
		return loginedMemberId != -1; // 로그아웃상태면 -1인채로 이 함수로 들어올거고, "-1은 -1이 아니야"는 false니까 false를 리턴.
		                              // 로그인상태면 id번호가 이 함수로 들어올거고, "id번호는 -1이 아니야"는 true니까 ture를 리턴.
		                              // 결론, true면 로그인상태임! false면 로그아웃상태!
	}

}