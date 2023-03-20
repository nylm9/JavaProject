//TicketingInfo
package Train;

public class TicketingInfo {
	private int etnum;
	private String emid;
	private int etrnum;
	private int eamount;
	private String etime;
	
	public int getEtnum() {
		return etnum;
	}
	public void setEtnum(int etnum) {
		this.etnum = etnum;
	}
	public String getEmid() {
		return emid;
	}
	public void setEmid(String emid) {
		this.emid = emid;
	}
	public int getEtrnum() {
		return etrnum;
	}
	public void setEtrnum(int etrnum) {
		this.etrnum = etrnum;
	}
	public int getEamount() {
		return eamount;
	}
	public void setEamount(int eamount) {
		this.eamount = eamount;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	@Override
	public String toString() {
		return etnum +"\t"+emid +"\t"+etrnum +"\t"+eamount +"\t"+etime;
	}
	
	
}