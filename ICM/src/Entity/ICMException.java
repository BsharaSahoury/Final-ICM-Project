package Entity;

import java.util.Date;

public class ICMException {
	private String masg;
	private Date date;
	private RequestPhase r;

	public ICMException(String masg, Date date, RequestPhase r) {
		super();
		this.masg = masg;
		this.date = date;
		this.r = r;
	}

	public String getMasg() {
		return masg;
	}

	public void setMasg(String masg) {
		this.masg = masg;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public RequestPhase getR() {
		return r;
	}

	public void setR(RequestPhase r) {
		this.r = r;
	}

}
