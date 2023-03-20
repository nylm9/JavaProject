//TicketingDAO
package Train;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TicketingDAO {

	private Connection con = null;
	
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","LYW","1111");
		return con;
	}
	
	public TicketingDAO() {
		try {
			con = getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB 연결 실패!");
		}
	}
	// station 정보 출력
	public ArrayList<StationInfo> searchTk(String loginId) {
		ArrayList<StationInfo> ticketList = new ArrayList<StationInfo>();
		String sql = "SELECT E.ETNUM, T.TNUM, T.TDEPARTSTA, T.TARRIVALSTA, E.EAMOUNT, T.TSTART, T.TEND, E.ETIME"
				+ " FROM TRAIN T, TICKETING E"
				+ " WHERE T.TNUM = E.ETRNUM AND E.EMID = ?";
		try {
		Connection con = getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, loginId);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
		StationInfo sta = new StationInfo();
		sta.setEtnum(rs.getInt(1));
		sta.setTnum(rs.getInt(2));
		sta.setTdepartsta(rs.getString(3));
		sta.setTarrivalsta(rs.getString(4));
		sta.setTamount(rs.getInt(5));
		sta.setTstart(rs.getString(6));
		sta.setTend(rs.getString(7));
		sta.setEtime(rs.getString(8));
		ticketList.add(sta);
		}
		}catch (Exception e) {
		System.out.println("오류발생");
		e.printStackTrace();
		}

		return ticketList;

		}

	public int insertticket(String loginId, int selectNum, int selectAmount) {
		int insertResult = 0;
		String sql = "INSERT INTO TICKETING (ETNUM, EMID, ETRNUM, EAMOUNT, ETIME)"
				+ "VALUES (?, ?, ?, ?, SYSDATE)";
		try {
			int maxNum = maxNumcount();
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, maxNum);
			pstmt.setString(2, loginId);
			pstmt.setInt(3, selectNum);
			pstmt.setInt(4, selectAmount);
			insertResult = pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertResult;
	}

	private int maxNumcount() {
		String sql = "SELECT NVL(MAX(ETNUM),0) FROM TICKETING";
		int maxNum = 0;
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				maxNum = rs.getInt(1);
			}
			maxNum += 1;
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxNum;
	}
	
	public int updateTicket(int amount, int tnum) {
		int updateResult = 0;
		String sql = "UPDATE TRAIN SET TAMOUNT = TAMOUNT - ? WHERE TNUM = ?";
		
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, amount);
			pstmt.setInt(2, tnum);
			updateResult = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return updateResult;
	}

	
	
	// 예매하기
	public ArrayList<TrainInfo> trainlistCreate(String selectdesta, String selectarsta) {
		ArrayList<TrainInfo> trlist = new ArrayList<TrainInfo>();
		String sql = "SELECT * FROM TRAIN WHERE TDEPARTSTA = ? AND TARRIVALSTA = ?";
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, selectdesta);
			pstmt.setString(2, selectarsta);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				TrainInfo newtrain = new TrainInfo();
				newtrain.setTnum(rs.getInt(1));
				newtrain.setTstart(rs.getString(2));
				newtrain.setTend(rs.getString(3));
				newtrain.setTprice(rs.getInt(4));
				newtrain.setTamount(rs.getInt(5));
				newtrain.setTdpartsta(rs.getString(6));
				newtrain.setTarrivalsta(rs.getString(7));
				trlist.add(newtrain);
			}
		} catch (Exception e) {
			System.out.println("열차정보를 가져오는데 실패하였습니다.");
		}
		return trlist;
	}
	
	public ArrayList<TrainInfo> ticketing(int selecttnum) {
		ArrayList<TrainInfo> trlist = new ArrayList<TrainInfo>();
		String sql = "SELECT * FROM TRAIN WHERE TNUM = ?";
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, selecttnum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				TrainInfo newtrain = new TrainInfo();
				newtrain.setTnum(rs.getInt(1));
				newtrain.setTstart(rs.getString(2));
				newtrain.setTend(rs.getString(3));
				newtrain.setTprice(rs.getInt(4));
				newtrain.setTamount(rs.getInt(5));
				newtrain.setTdpartsta(rs.getString(6));
				newtrain.setTarrivalsta(rs.getString(7));
				trlist.add(newtrain);
			}
		} catch (Exception e) {
			System.out.println("열차정보를 가져오는데 실패하였습니다.");
		}
		return trlist;
	}


	public int[] trainDate(int inputCancelNum) {
		int[] trainRewrite = new int[2];
		String sql = "SELECT ETRNUM, EAMOUNT FROM TICKETING WHERE ETNUM = ?";
		
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, inputCancelNum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				trainRewrite[0] = rs.getInt(1); //열차 번호
				trainRewrite[1] = rs.getInt(2); //예매 좌석수
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return trainRewrite;
	}

	public int cancelTK(int inputCancelNum) {
		int deleteResult = 0;
		String sql = "DELETE TICKETING WHERE ETNUM = ?";
		
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, inputCancelNum );
			deleteResult = pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return deleteResult;
	}

	public int trainDataPlus(int etrainNum, int etrainAmount) {
		int resultTrainData = 0;
		String sql = "UPDATE TRAIN SET TAMOUNT = TAMOUNT + ? WHERE TNUM = ?";
		
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, etrainAmount);
			pstmt.setInt(2, etrainNum);
			resultTrainData = pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultTrainData;
	}
	
}