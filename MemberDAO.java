//MemberDAO
package Train;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDAO {
	
	private Connection con = null;
	
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","LYW","1111");
		return con;
	}
	
	public MemberDAO() {
		try {
			con = getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB 연결 실패!");
		}
	}

	public int memberInput(MemberInfo memberinfo) {
		int insertResult = 0;
		String sql = "INSERT INTO MEMBER (MID,MPW,MNAME,MBIRTH,MEMAIL)"
				+ "VALUES (?,?,?,TO_DATE(?,'YYYY-MM-DD'),?)";
		
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberinfo.getMid());
			pstmt.setString(2, memberinfo.getMpw());
			pstmt.setString(3, memberinfo.getMname());
			pstmt.setString(4, memberinfo.getMbirth());
			pstmt.setString(5, memberinfo.getMemail());
			insertResult = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertResult;
	}

	public String memberLogin(String Id, String Pw) {
		String loginMem = null;
		String sql = "SELECT * FROM MEMBER WHERE MID = ? AND MPW =?";
		
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Id);
			pstmt.setString(2, Pw);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				loginMem = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return loginMem;
	}
	
	 public MemberInfo selectMemberInfo(String loginId) {
	      String sql ="SELECT MID,MPW,MNAME,TO_CHAR(MBIRTH,'YYYY-MM-DD'), MEMAIL FROM MEMBER WHERE MID =?";
	      MemberInfo memberList = null;
	      try {
	         Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, loginId);
	         ResultSet rs = pstmt.executeQuery();
	          while(rs.next()) {
	             memberList = new MemberInfo();
	             memberList.setMid(rs.getString(1));
	             memberList.setMpw(rs.getString(2));
	             memberList.setMname(rs.getString(3));
	             memberList.setMbirth(rs.getString(4));
	             memberList.setMemail(rs.getString(5));
	          }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return memberList;
	   }

	public String chdeckmidQuery(String mid) {
		ArrayList<MemberInfo> memlist = new ArrayList<MemberInfo>();
		String searchId = null;
		String sql = "SELECT MID FROM MEMBER";
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberInfo mem = new MemberInfo();
				mem.setMid(rs.getString("MID"));
				memlist.add(mem);
			}
			for(int i = 0; i < memlist.size(); i++) {
				if(memlist.get(i).getMid().equals(mid)) {
					searchId = mid;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return searchId;
	}

}