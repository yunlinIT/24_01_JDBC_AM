package com.KoreaIT.java.JDBCAM.dao;

import java.util.Map;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.dto.Member;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class MemberDao {

	public Member getMemberByLoginId(String loginId) { // 로그인아이디로 회원 찾는 함수 // 서비스한테 받은 아이디로... 
		SecSql sql = new SecSql(); // 아래 쿼리 날려 

		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?;", loginId); // 입력받은 아이디 쿼리에 넣어

		Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql); // 쿼리로 select해서 나온 정보 한줄가져와서 memberMap에 담아

		if (memberMap.isEmpty()) { // memberMap이 비어있으면 null 리턴 (회원정보가 데이터에 없다는 뜻)
			return null;
		}

		return new Member(memberMap); // 회원정보 한줄 서비스한테 돌려줘 
	}

	public boolean isLoginIdDup(String loginId) { // 서비스한테 넘겨받은 (회원가입시 입력받은)아이디를 쿼리에 있는 변수에 담아
		SecSql sql = new SecSql(); // 여기서 입력된 쿼리 dq로 날려
		sql.append("SELECT COUNT(*) > 0"); 
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?;", loginId); // <-여기에 아이디값 들어가는거

		return DBUtil.selectRowBooleanValue(Container.conn, sql); // DBUtil.selectRow어쩌구 함수로 DBUtil클래스에서 일을 막 하고 나온 값을 서비스한테 반환해줌
																	// 아마도 이 안에서 하는 일이, 회원정보 한줄씩 훑어보는거같고, 중복아이디 있으면 true 리턴받는 듯?
	}

	public int doJoin(String loginId, String loginPw, String name) { // 서비스한테 넘겨받은 아이디 비번 이름 매개변수로 받아서 아래 쿼리문에 넣어 
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("loginId = ?,", loginId); // 저 앞에서(컨트롤러) 입력받은거 담아
		sql.append("loginPw = ?,", loginPw); // "
		sql.append("`name` = ?;", name); // "

		return DBUtil.insert(Container.conn, sql); // DBUtil.insert 함수로 DB에 INSERT하고 리턴받은 id를 서비스로 돌려줘
	}

}