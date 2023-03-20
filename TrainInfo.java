// TrainInfo
package Train;

public class TrainInfo {
	private int tnum;
	private String tstart;
	private String tend;
	private int tprice;
	private int tamount;
	private String tdepartsta;
	private String tarrivalsta;
	
	public int getTnum() {
		return tnum;
	}

	public void setTnum(int tnum) {
		this.tnum = tnum;
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

	public int getTprice() {
		return tprice;
	}

	public void setTprice(int tprice) {
		this.tprice = tprice;
	}

	public int getTamount() {
		return tamount;
	}

	public void setTamount(int tamount) {
		this.tamount = tamount;
	}

	public String getTdepartsta() {
		return tdepartsta;
	}

	public void setTdpartsta(String tdepartsta) {
		this.tdepartsta = tdepartsta;
	}

	public String getTarrivalsta() {
		return tarrivalsta;
	}

	public void setTarrivalsta(String tarrivalsta) {
		this.tarrivalsta = tarrivalsta;
	}

	@Override
	public String toString() {
		return tnum +"\t"+ tstart +"\t"+ tend +"\t"+ tprice +"\t"+ tamount +"\t"+ tdepartsta +"\t"+ tarrivalsta;
	}
	
	

}