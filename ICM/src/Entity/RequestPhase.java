
package Entity;

import java.io.Serializable;
import java.sql.Date;

public class RequestPhase  extends Request implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
private Date startDate;
private Date dueDate;
private Request r;
private Phase phase;
private State phasestate;
private int repetion;
private String employee;
public RequestPhase(Date startDate, Date dueDate,Request r,Phase phase,State phasestate) {
	super(r.getId(),r.getInitiatorName(),r.getStatus(),r.getPrivilegedInfoSys(),r.getDate(),r.getCurrentPhase());
	this.phase=phase;
	this.r=r;
	this.startDate = startDate;
	this.dueDate = dueDate;
	this.phasestate=phasestate;
}
public RequestPhase(int id,Phase phase,int repetion ) {
	super(id);
	this.phase=phase;
	this.repetion=repetion;
	
}
public RequestPhase(int id,Date startDate, Date dueDate,Phase phase,State phasestate,String employee) {
	super(id);
	this.phase=phase;
	this.startDate = startDate;
	this.dueDate = dueDate;
	this.phasestate=phasestate;
	this.employee=employee;
}

public State getPhasestate() {
	return phasestate;
}
public void setPhasestate(State phasestate) {
	this.phasestate = phasestate;
}
public String getEmployee() {
	return employee;
}
public void setEmployee(String employee) {
	this.employee = employee;
}
public int getRepetion() {
	return repetion;
}
public void setRepetion(int repetion) {
	this.repetion = repetion;
}

public RequestPhase(Date startDate, Date dueDate,Phase phase,State phasestate) {
	super();
	this.phase=phase;
	this.startDate = startDate;
	this.dueDate = dueDate;
	this.phasestate=phasestate;
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
public Request getR() {
	return r;
}
public void setR(Request r) {
	this.r = r;
}
public Phase getPhase() {
	return phase;
}
public void setPhase(Phase phase) {
	this.phase = phase;
}
public State getState() {
	return phasestate;
}
public boolean Extension(Date newDueDate)
{
	if(newDueDate.compareTo(dueDate)<0) {
		System.out.println("cann't Extend the due date ");
		return false;
	}
	setDueDate(newDueDate);
	return true;
}
@Override
public String toString() {
	return "phase"+phase.toString()+"state"+phasestate;
}
}

