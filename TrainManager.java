//TrainManager
package Train;

import java.util.ArrayList;
import java.util.Scanner;

public class TrainManager {
	private Scanner scan = new Scanner(System.in);
	MemberDAO mdao = new MemberDAO();
	TicketingDAO tdao = new TicketingDAO();
	private String loginId = null;
	
	public String getLoginId() {
		return loginId;
	}
	
	// 초기화면(로그인전/후)
	public int showMenu() {
		if (loginId == null) {
			System.out.println("\n===========================");
			System.out.println("[1]회원가입 | [2]로그인 | [0]종료");
			System.out.println("===========================");
		} else {
			System.out.println("\n==================================================================");
			System.out.println("[1]회원정보 | [2]예매하기 | [3]예매정보조회 | [4]예매취소 | [5]로그아웃 |[0]종료");
			System.out.println("==================================================================");
		}
		System.out.print("메뉴선택>>");
		return scan.nextInt();
	}
	
	ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
	private int memberCount = 0;
	
	// 회원가입
	public void MemberJoin() {
		if(loginId == null) {
			System.out.println("[1]회원가입");
			//아이디 입력 중복 체크
			String mid = checkoverlapId();
			System.out.println(mid);
			System.out.print("비밀번호 입력>>");
			String mpw = scan.next();
			System.out.print("이름입력>>");
			String mname = scan.next();
			System.out.print("생년월일(YYYY-MM-DD)>>");
			String mbirth = scan.next();
			System.out.print("이메일입력>>");
			String memail = scan.next();
	
			MemberInfo memberinfo = new MemberInfo();
			memberinfo.setMid(mid);
			memberinfo.setMpw(mpw);
			memberinfo.setMname(mname);
			memberinfo.setMbirth(mbirth);
			memberinfo.setMemail(memail);
			memberCount++;
			int inputResult = mdao.memberInput(memberinfo);
			if(inputResult > 0) {
				System.out.println("회원가입에 성공하였습니다");
			} else {
				System.out.println("회원가입에 실패했습니다");
			}
		}
	}
	
	
	// 로그인
	public void login() {
		System.out.println("[2]로그인 ");
		System.out.print("아이디 입력>>");
		String connectId = scan.next();
		System.out.print("패스워드 입력>>");
		String connectPw = scan.next();
		String loginCheck = mdao.memberLogin(connectId, connectPw);
		if (loginCheck != null) {
			System.out.println("로그인 되었습니다.");
			loginId = loginCheck;
		} else {
			System.out.println("아이디/비밀번호가 일치하지 않습니다.");
		}
	}
	
	// 로그아웃 메소드
	public void logout() {
		System.out.println("로그아웃 되었습니다.");
		loginId = null;
	}
	
	// 회원정보
	public void MemberInfo() {
		 System.out.println("[1]회원정보 ");
	      MemberInfo memberList = mdao.selectMemberInfo(loginId);
	   
	      System.out.println();
	      System.out.println("[이름] " + memberList.getMname());
	      System.out.println("[아이디] " + memberList.getMid());
	      System.out.println("[이메일] " + memberList.getMemail());
	      System.out.println("[생년월일] " + memberList.getMbirth());

	}
	
	
	
	// 예매하기
	public void Ticketing() {
		System.out.println("[2]예매하기 ");
		int checkAmount = 0; 
		System.out.println("예매가능한 역 : 서울, 부산, 동대구, 광주송정, 대전");
		System.out.print("출발역을 입력하세요 : ");
		String selectdesta = scan.next();   //출발역 선택
		System.out.print("도착역을 입력하세요 : ");
		String selectarsta = scan.next();	//도착역 선택
		System.out.println("\n검색된 열차목록");
		ArrayList<TrainInfo> trainList = new ArrayList<TrainInfo>();
		trainList = tdao.trainlistCreate(selectdesta, selectarsta);
		System.out.println("열차번호\t출발시간\t도착시간\t티켓가격\t좌석수\t출발역\t도착역");
		System.out.println("-------------------------------------------------");
		for(TrainInfo t : trainList) {
			System.out.println(t);
		}
		
		System.out.print("구매할 열차번호를 입력하세요 : ");
		int selecttnum = scan.nextInt();
		ArrayList<TrainInfo> ticketing = new ArrayList<TrainInfo>();
		ticketing = tdao.ticketing(selecttnum);
		System.out.println("\n열차번호\t출발시간\t도착시간\t티켓가격\t좌석수\t출발역\t도착역");
		System.out.println("-------------------------------------------------");
		for(TrainInfo t : ticketing) {
			System.out.println(t);
			checkAmount = t.getTamount();
		}
		System.out.print("구매할 열차표 수를 입력하세요 : ");
		int selectticket = scan.nextInt();	
		if((checkAmount - selectticket) >= 0) {
			System.out.println(selectticket + "장 구매하였습니다.");
			int insertResult = tdao.insertticket(loginId, selecttnum, selectticket);
			if(insertResult != 0) {
				System.out.println("정산중..");
				int updateResult = tdao.updateTicket(selectticket, selecttnum);
				if(updateResult != 0) {
					System.out.println("예매가 성공적으로 처리되었습니다.");
				} else {
					System.out.println("예매가 실패하였습니다.");
				}
			} else {
				System.out.println("예매가 실패하였습니다.");
			}
		} else {
			System.out.println("예매수량보다 현재 좌석이 적습니다.");
		}
		
	}
	

	// 예매정보조회
		public void TicketInfo() {
			System.out.println("[3]예매정보조회 ");
			ArrayList<StationInfo> ticketList = new ArrayList<StationInfo>();
			ticketList = tdao.searchTk(loginId);
			// 출발지 도착지 추가해야됨
			System.out.println("예매번호\t열차번호\t출발지\t도착지\t예매장수\t출/도착시간\t\t\t\t\t\t\t예약시간");
			System.out.println("---------------------------------------------------------------------------------------------------------------------");
			for(StationInfo i : ticketList) {
				System.out.println(i);
			}
		}
	
	//아이디 중복 체크
	public String checkoverlapId() {
		boolean run = true;
		String id = null;
		while(run) {
			String checkId = null;
			id = null;
			System.out.print("아이디 입력>>");
			id = scan.next();
			checkId = mdao.chdeckmidQuery(id);
			if (checkId != null) {
				System.out.println("아이디가 중복되었습니다.");
			} else {
				System.out.println("사용가능한 아이디입니다");
				run = false;
				break;
			}
		}
		return id;
		
	}
	
	//예매취소
	public void cancelTK() {
		System.out.println("[4]예매취소");
		ArrayList<StationInfo> ticketList = new ArrayList<StationInfo>();
		ticketList = tdao.searchTk(loginId);
		// 출발지 도착지 추가해야됨
		System.out.println("예매번호\t열차번호\t출발지\t도착지\t예매장수\t출/도착시간\t\t예약시간");
		System.out.println("---------------------------------------------------------------------------------");
		for(StationInfo i : ticketList) {
			System.out.println(i);
		}
		System.out.print("예매 취소할 번호를 입력하세요>>");
		int inputCancelNum = scan.nextInt();
		//취소한 번호를 입력 >> 예매취소 delete됨 
		int[] trainRewrite = new int[2];
		trainRewrite = tdao.trainDate(inputCancelNum); // 기차번호, 좌석수 저장
		//기차번호&좌석 각각 저장
		int EtrainNum = trainRewrite[0]; // 예매 열차번호
		int EtrainAmount = trainRewrite[1]; // 예매 열차 좌석수
		
		int resultCancel = tdao.cancelTK(inputCancelNum);
		if(resultCancel == 1 ) {
			//예매 열차번호 & 좌석수를 통해 TRAIN 데이터를 수정한다.
			int resultTrianData = tdao.trainDataPlus(EtrainNum, EtrainAmount);
			if(resultTrianData == 1) {
				System.out.println("예매취소가 완료되었습니다.");
			} else {
				System.out.println("기차정보 수정 실패[2] : tdao.trainDataPlus(EtrainNum, EtrainAmount)");
			}
		} else {
			System.out.println("예매취소 실패[1] : tdao.cancelTK(inputCancelNum) 오류");
		}
		
	}
	
	
}