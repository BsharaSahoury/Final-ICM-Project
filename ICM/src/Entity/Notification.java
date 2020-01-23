package Entity;

import java.io.Serializable;
import java.sql.Date;

import javafx.beans.property.SimpleStringProperty;
/**
  *  Notification: represents a notification in the database.
  *  Notification Class Extends from Serializable Class
  *  Notification describes the message that the ICM-User received
  *  The User has private variables:
  * @param content
  * @param date
  * @param dateStr
  * @param type
  * @param id
  * @author Arkan Muhammad
  *
  */
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;
	private String content;
	private Date date;
	private String dateStr;
	private String type;
	private int id;
/**
 * First Constructor:
 * @param content
 * @param date
 * @param type
 */
	public Notification(String content, Date date, String type) {
		this.content = content;
		this.date = date;
		this.dateStr = date.toString();
		this.type = type;
	}
	/**
	 * getContent() method doesn't received any parameters
	 * @return content : the content of the Notification
	 */
	public String getContent() {
		return content;
	}
	/**
	 * setContent(String content) method doesn't received any parameters
	 * @return content : the content of the RequestPhase
	 * @param content : the method received a parameter with String type that
	 * describes the content of the Notification
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * getDate() method doesn't received any parameters
	 * @return getDate : the Date of the Notification
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * setDate(Date date) method doesn't received any parameters
	 * @return date : the date of the RequestPhase
	 * @param date : the method received a parameter with String type that
	 * describes the date of the Notification
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * getDateStr() method doesn't received any parameters
	 * @return dateStr : the dateStr of the Notification
	 */
	public String getDateStr() {
		return dateStr;
	}
	/**
	 * getId() method doesn't received any parameters
	 * @return id : the id of the Notification
	 */
	public int getId() {
		return id;
	}
	/**
	 * setId(int id) method doesn't received any parameters
	 * @return id : the content of the RequestPhase
	 * @param id : the method received a parameter with int type that
	 * describes the id of the Notification
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * setDateStr(String dateStr) method doesn't received any parameters
	 * @return dateStr : the dateStr of the RequestPhase
	 * @param dateStr : the method received a parameter with String type that
	 * describes the dateStr of the Notification
	 */
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	/**
	 * getType() method doesn't received any parameters
	 * @return type : the type of the Notification
	 */
	public String getType() {
		return type;
	}
	/**
	 * setType(String type) method doesn't received any parameters
	 * @return type : the type of the RequestPhase
	 * @param type : the method received a parameter with String type that
	 * describes the type of the Notification
	 */
	public void setType(String type) {
		this.type = type;
	}

}
