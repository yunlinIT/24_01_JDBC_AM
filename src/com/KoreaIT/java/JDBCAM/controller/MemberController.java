package com.KoreaIT.java.JDBCAM.controller;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.dto.Member;
import com.KoreaIT.java.JDBCAM.service.MemberService;

public class MemberController {

	private MemberService memberService;

	public MemberController() { // 멤버컨트롤러 생성자. 객체 생성과 동시에 
		this.memberService = Container.memberService; // new 멤버서비스 가져와
	}

	public void showProfile() { // 회원 상세보기 함수
		if (Container.session.isLogined() == false) { // 만약에 로그인이 안된 상태면...
			System.out.println("로그인 후 이용해줘"); // 로그인하라고 출력하고
			return;
		}
		System.out.println(Container.session.loginedMember); // 로그인 된 상태면 컨테이너에 loginedMemberr로 member정보 한줄 다 가져와서 출력
	}

	public void logout() { // 로그아웃 함수
		if (Container.session.isLogined() == false) { // 만약에 로그인 안된상태면
			System.out.println("로그인 후 이용해줘"); // 출력
			return;
		}
		Container.session.logout(); // 로그인상태맞으면 세션에 로그아웃함수 실행
	}

	public void login() { // 로그인 함수
		if (Container.session.isLogined()) { // 로그인상태면 
			System.out.println("로그아웃 하고 써"); // 로그아웃하고 로그인하라고 해
			return; // 함수 종료시켜 // 자신을 호출한 메소드로 돌아감
		}

		String loginId = null; // while문 끝나고 변수 사라지지 않게 전역변수처럼 초기화
		String loginPw = null; // " 

		System.out.println("==로그인==");
		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = Container.sc.nextLine().trim(); // 앞뒤 공백제거하고 아이디 입력받아서 변수에 담아

			if (loginId.length() == 0 || loginId.contains(" ")) { // 만약에 입력받은 아이디의 길이가 0이거나 공백포함이면 
				System.out.println("아이디 똑바로 입력해");// <- 이거 출력
				continue; // 다시 올라가서 아이디 입력받아
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId); // 중복아이디 있는지 확인해주고 참인지 거짓인지 (중복인지 아닌지) 변수에 담아

			if (isLoginIdDup == false) { // 중복이 아니면 로그인을 못하니까 (로그인기능은 아이디가 데이터에 존재해야하잖아 -> 중복이면 좋은거)
				System.out.println(loginId + "는(은) 없는놈이야"); // 출력하고
				continue;  // 다시 올라가서 아이디 입력받아
			}

			break; // 중복이면 -> 아이디 있으면 빠져나가고 다음 로직으로 가
		}

		Member member = memberService.getMemberByLoginId(loginId); // 서비스랑 dao 통해서 입력받은 회원정보 한줄 member에 담아

		int tryMaxCount = 3; // while문 실행하고 끝나도 변수 사라지지 말라고 전역변수처럼 초기화 해놈
		int tryCount = 0; // "

		while (true) { 
			if (tryCount >= tryMaxCount) { // 만약에 tryCount가 tryMaxCount(3)보다 크거나 같으면
				System.out.println("다시 확인하고 시도해라"); // 아이디 입력 다시 받으라고 출력
				break; //탈출해서 로그인함수 종료하고 명령어 처음부터 다시 입력받게 함
			} 
			System.out.print("비밀번호 : "); 
			loginPw = Container.sc.nextLine().trim(); // 비번입력받을건데 공백제거하고 받아

			if (loginPw.length() == 0 || loginPw.contains(" ")) { // 비번 길이 0이거나 공백 포함이면 
				tryCount++; // 변수 1증가시키고 
				System.out.println("비밀번호 똑바로 입력해"); // <- 출력
				continue; // 올라가서 다시 입력받게 해 
			}

			if (member.getLoginPw().equals(loginPw) == false) { // 입력받은 비번이랑, member(회원정보한줄)에서 찾은 비번이랑 일치하지 않으면
				tryCount++; // 변수 1증가키고
				System.out.println("일치하지 않아"); // <- 출력
				continue; // 올라가서 다시 입력받게해
			}

			Container.session.login(member); // 비번 일치하면 여기까지 내려오겠지, 그러면 세션에있는 로그인 함수 실행해서 로그인 시키면 도미

			System.out.println(member.getName() + "님 환영"); // 로그인 성공하면 출력하고
			break; // 탈출하고 명령어 아예 새로 입력받게하겠지

		}

	}

	public void doJoin() { // member join 명령어 입력 받으면 실행되는 함수

		if (Container.session.isLogined()) { // 만약에  isLogined함수가 true면 로그인상태니까, 아래 출력문 출력
			System.out.println("로그아웃 하고 써");
			return;
		}
		
		// 아래 변수들은, while문 끝나면 변수가 사라지니까 사라지지 않게 while문 밖에다가 전역변수처럼 세팅해줌
		String loginId = null; 
		String loginPw = null;
		String loginPwConfirm = null;
		String name = null;

		System.out.println("==회원가입==");
		while (true) { // while문 사용하는 이유는 break를 만나기전까지는 계속 입력 받게하고 종료돼서 처음부터 다시 입력하는 것을 방지하려고?
			System.out.print("로그인 아이디 : ");
			loginId = Container.sc.nextLine().trim(); // 아이디 입력받아서 loginId에 담고, 앞뒤 공백은 trim해

			if (loginId.length() == 0 || loginId.contains(" ")) { // 입력받은 아이디의 길이가 0이거나 아이디에 공백이 포함되면 
				System.out.println("아이디 똑바로 입력해"); // <- 이거 출력
				continue; // 그리고 다시 while문 실행해서 로그인아이디 다시 입력받게 함
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId); // 아이디가 중복인지 true / false 담아

			if (isLoginIdDup) { // 만약에 이 함수 실행해서 true면 중복이라라고 출력해
				System.out.println(loginId + "는(은) 이미 사용중");
				continue; // 중복이면 다시 올라가서 로그인 아이디 입력받고
			}

			break; // 중복아니면 이 while문 빠져나와서 그 다음 로직으로 가
		}
		while (true) {
			System.out.print("비밀번호 : "); 
			loginPw = Container.sc.nextLine().trim(); // 가입 비밀번호 입력 받아서 loginPw 변수에 담고, 앞뒤 공백은 trim 

			if (loginPw.length() == 0 || loginPw.contains(" ")) { // 만약에 입력받은 비번의 길이가 0이거나 공백을 포함하면 
				System.out.println("비밀번호 똑바로 입력해"); // 이거 출력하고 
				continue; // 다시 비밀번호 입력받으로 위로 올려보내 
			}

			boolean loginPwCheck = true; // 비밀번호 확인 boolean 변수 true로 일단 초기화

			while (true) {
				System.out.print("비밀번호 확인: "); 
				loginPwConfirm = Container.sc.nextLine().trim(); // 비밀번호 확인용으로 입력 받아서 loginPwConfirm 변수에 담고, 앞뒤 공백 trim

				if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) { // 입력 받은 비번 길이 0이거나 공백 포함이면 
					System.out.println("확인 똑바로 입력해");
					continue; // 위로 올려보내고 비번확인 입력 다시 받아
				}

				if (loginPw.equals(loginPwConfirm) == false) { // 만약에 입력받은 비번이랑 확인용으로 입력받은 비번이 일치하지 않으면
					System.out.println("일치하지 않아"); // <- 이거 출력
					loginPwCheck = false; // boolean 변수에 false 담아
				}
				break; // 
			}

			if (loginPwCheck) { // 만약에 이게 true면 break로 빠져나가서 다음 로직으로 가고, false(일치하지않다)으면 break할 수 없으니가 while문 맨 위로 올라감!
				break; 
			}
		}
		while (true) {
			System.out.print("이름  : ");
			name = Container.sc.nextLine().trim(); // 이름 입력 받고 trim해서 변수에 담아

			if (name.length() == 0 || name.contains(" ")) { // 만약에 입력받음 이름의 길이가 0이거나 공백이 있으면 
				System.out.println("이름 똑바로 입력해"); // <- 이거출력
				continue; // 그리고 다시 입력받게 올려보내
			}
			break; // 제대로 입력했으면 빠져나가고 다음 로직으로 가
		}

		int id = memberService.doJoin(loginId, loginPw, name); // 위에서 입력받은 아이디, 비번, 이름 서비스통해서 dao한테까지 보내고 리턴받은 id값을 id변수에 담아
																// int로 리턴받은 이유는, 저기 뒤에 DBUtil어쩌구에서 int로 리턴해서.
		System.out.println(name + "님, 가입 되었습니다"); // DB에 INSERT 되고나서 출력

	}

}