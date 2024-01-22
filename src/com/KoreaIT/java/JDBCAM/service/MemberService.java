package com.KoreaIT.java.JDBCAM.service;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.dao.MemberDao;
import com.KoreaIT.java.JDBCAM.dto.Member;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		this.memberDao = Container.memberDao;
	}

	public boolean isLoginIdDup(String loginId) { // 컨트롤러에서 입력받은 아이디 여기서 매개변수로 받아서 
		return memberDao.isLoginIdDup(loginId); // dao한테 다시 넘겨주고나서 되돌려받은걸, 컨트롤러한테 다시 리턴
	}

	public int doJoin(String loginId, String loginPw, String name) { // 컨트롤러에서 입력받은 아이디, 비번, 이름을 매개변수로 받아서
		return memberDao.doJoin(loginId, loginPw, name); // dao한테 넘겨주고나서 되돌려받은 id, 컨트롤러한테 다시 리턴해
	}

	public Member getMemberByLoginId(String loginId) { // 컨트롤러한테 넘겨받은 아이디 매개변수로 받아서 
		return memberDao.getMemberByLoginId(loginId); // dao한테 넘겨주고 되돌려받은 회원정보 한줄, 컨트롤러한테 리턴해
	}

}