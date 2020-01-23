package Entity;

import java.util.Date;
/**
 * ICM Exception Class that represents an ICM Exception that can throw while the Program is running
 * @param masg
 * @param date
 * @param r
 * @author Arkan Muhammad
 *
 */
public class ICMException {
	private String masg;
	private Date date;
	private RequestPhase r;
/**
 * First Constructor
 * @param masg
 * @param date
 * @param r
 */
	public ICMException(String masg, Date date, RequestPhase r) {
		super();
		this.masg = masg;
		this.date = date;
		this.r = r;
	}
	/**
	 * getMasg() method doesn't received any parameters
	 * @return masg : the masg of the ICMException
	 */
	public String getMasg() {
		return masg;
	}
	/**
	 * setMasg :  method to Set masg of ICMException
	 * @param masg : the method received a parameter with String type that
	 * describes the masg of the ICMException
	 */
	public void setMasg(String masg) {
		this.masg = masg;
	}
	/**
	 * getDate() method doesn't received any parameters
	 * @return date : the Date of the ICMException
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * setDate :  method to Set date of ICMException
	 * @param date : the method received a parameter with Date type that
	 * describes the date of the ICMException
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * getR() method doesn't received any parameters
	 * @return r : the r of the ICMException
	 */
	public RequestPhase getR() {
		return r;
	}
	/**
	 * setMasg :  method to Set masg of ICMException
	 * @param r : the method received a parameter with RequestPhase type that
	 * describes the requestPhase that the ICMException threw on
	 */
	public void setR(RequestPhase r) {
		this.r = r;
	}

}
