//TrainMain
package Train;

import Train.TrainManager;

public class TrainMain {
	public static void main(String[] args) {
		TrainManager manager = new TrainManager();
		boolean run = true;
		while (run) {
			// 메뉴 출력 및 선택 메소드 호출
			String loginId = manager.getLoginId();
			int selectMenu = manager.showMenu(); 	// showMenu 메소드에 있는 selectMenu 선언
			System.out.println("선택한 메뉴 : " + selectMenu);
			
			
			if(loginId == null) {
			switch (selectMenu) {
				case 1: 
					manager.MemberJoin();
					break;
				case 2: 
					manager.login();
					break;
				case 0: // 종료
					run = false;
					System.out.println("종료되었습니다.");
					break;
				default :
					System.out.println("잘못된 입력입니다.");
			}
			} else {
				switch (selectMenu) {
				case 1: 
					manager.MemberInfo();
					break;
				case 2: 
					manager.Ticketing();
					break;
				case 3: // 예매정보조회
					manager.TicketInfo();
					break;
				case 4: // 로그아웃
					manager.cancelTK();
					break;
				case 5 :
					manager.logout();
					break;
				case 0: // 종료
					run = false;
					System.out.println("종료되었습니다.");
					break;
				default :
					System.out.println("잘못된 입력입니다.");
			}
		}
		}
	}
}