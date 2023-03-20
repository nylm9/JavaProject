package Train;

public class StationInfo {
	private int etnum;
	private int tnum;
	private String tdepartsta;
	private String tarrivalsta;
	private int tamount;
	private String tstart;
	private String tend;
	private String etime;
	
	
	public int getEtnum() {
		return etnum;
	}
	public void setEtnum(int etnum) {
		this.etnum = etnum;
	}
	public int getTnum() {
		return tnum;
	}
	public void setTnum(int tnum) {
		this.tnum = tnum;
	}
	public String getTdepartsta() {
		return tdepartsta;
	}
	public void setTdepartsta(String tdepartsta) {
		this.tdepartsta = tdepartsta;
	}
	public String getTarrivalsta() {
		return tarrivalsta;
	}
	public void setTarrivalsta(String tarrivalsta) {
		this.tarrivalsta = tarrivalsta;
	}
	public int getTamount() {
		return tamount;
	}
	public void setTamount(int tamount) {
		this.tamount = tamount;
	}
	public String getTstart() {
		return tstart;
	}
	public void setTstart(String tstart) {
		this.tstart = tstart;
	}
	public String getTend() {
		return tend;
	}
	public void setTend(String tend) {
		this.tend = tend;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	
	
	@Override
	public String toString() {
		return  etnum + "\t" + tnum + "\t" + tdepartsta + "\t"
				+ tarrivalsta + "\t" + tamount + "\t" + tstart + " ~ " + tend +"\t\t"+ etime;
	}
	
	
	
}