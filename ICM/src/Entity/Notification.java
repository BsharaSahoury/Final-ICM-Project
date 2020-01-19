package Entity;

import java.io.Serializable;
import java.sql.Date;

import javafx.beans.property.SimpleStringProperty;

public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private Date date;
	private String dateStr;
	private String type;
	private int id;

	public Notification(String content, Date date, String type) {
		this.content = content;
		this.date = date;
		this.dateStr = date.toString();
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateStr() {
		return dateStr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
