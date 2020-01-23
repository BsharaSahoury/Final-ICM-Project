package Entity;

import java.sql.Date;

public class Frozen {
	private Date startDate;
	private Date dueDate;
	private int id;
	public Frozen(int id,Date startDate, Date dueDate) {
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
