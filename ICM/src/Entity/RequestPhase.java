
package Entity;

import java.io.Serializable;
import java.sql.Date;
/**
 * RequestPhase: represents a Phase of Request in the database.
 *  User is an ICM-User in DataBase that his Role is Student
 *  Class RequestPhase Extends from User Class
 *  The User has private variables:
 * @param startDate
 * @param dueDate
 * @param r
 * @param phase
 * @param phasestate
 * @param repetion
 * @param employee
 * @author Arkan Muhammad
 */
public class RequestPhase extends Request implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date startDate;
	private Date dueDate;
	private Request r;
	private Phase phase;
	private State phasestate;
	private int repetion;
	private String employee;
	/**
	 * First Constructor:
	 * @param startDate
	 * @param dueDate
	 * @param r
	 * @param phase
	 * @param phasestate
	 */
	public RequestPhase(Date startDate, Date dueDate, Request r, Phase phase, State phasestate) {
		super(r.getId(), r.getInitiatorName(), r.getStatus(), r.getPrivilegedInfoSys(), r.getDate(),
				r.getCurrentPhase());
		this.phase = phase;
		this.r = r;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.phasestate = phasestate;
	}
	/**
	 * Second Constructor that received:
	 * @param id
	 * @param phase
	 * @param repetion
	 */
	public RequestPhase(int id, Phase phase, int repetion) {
		super(id);
		this.phase = phase;
		this.repetion = repetion;

	}
	/**
	 * Third Constructor that received:
	 * @param id
	 * @param startDate
	 * @param dueDate
	 * @param phase
	 * @param phasestate
	 * @param employee
	 */
	public RequestPhase(int id, Date startDate, Date dueDate, Phase phase, State phasestate, String employee) {
		super(id);
		this.phase = phase;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.phasestate = phasestate;
		this.employee = employee;
	}
	
	/**
	 * Fourth Constructor that received:
	 * @param startDate
	 * @param dueDate
	 * @param phase
	 * @param phasestate
	 */
	public RequestPhase(Date startDate, Date dueDate, Phase phase, State phasestate) {
		super();
		this.phase = phase;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.phasestate = phasestate;
	}
	
	/**
	 * getPhasestate() method doesn't received any parameters
	 * @return phasestate : the phasestate of the RequestPhase
	 */
	public State getPhasestate() {
		return phasestate;
	}
	/**
	 * setPhasestate(State phasestate) method to Set phasestate of RequestPhase
	 * @param phasestate : the method received a parameter with State type that
	 * describes the phasestate of the Request in phase (RequestPhase)
	 */
	public void setPhasestate(State phasestate) {
		this.phasestate = phasestate;
	}
	/**
	 * getEmployee() method doesn't received any parameters
	 * @return employee : the employee of the RequestPhase
	 */
	public String getEmployee() {
		return employee;
	}
	/**
	 * setEmployee(String employee) method to Set employee of RequestPhase
	 * @param employee : the method received a parameter with State type that
	 * describes the employee of the Request in phase (RequestPhase)
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	/**
	 * getRepetion() method doesn't received any parameters
	 * @return repetion : the repetion of the RequestPhase
	 */
	public int getRepetion() {
		return repetion;
	}
	/**
	 * setRepetion(int repetion) method to Set repetion of RequestPhase
	 * @param repetion : the method received a parameter with int type that
	 * describes the repetion of the Request in phase (RequestPhase)
	 */
	public void setRepetion(int repetion) {
		this.repetion = repetion;
	}

	/**
	 * getStartDate() method doesn't received any parameters
	 * @return startDate : the startDate of the RequestPhase
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * setStartDate(Date startDate) method to Set startDate of RequestPhase
	 * @param startDate : the method received a parameter with Date type that
	 * describes the startDate of the Request in phase (RequestPhase)
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * getDueDate() method doesn't received any parameters
	 * @return dueDate : the dueDate of the RequestPhase
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * setDueDate(Date dueDate) method to Set dueDate of RequestPhase
	 * @param dueDate : the method received a parameter with Date type that
	 * describes the dueDate of the Request in phase (RequestPhase)
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * getR() method doesn't received any parameters
	 * @return r : the r (request) of the RequestPhase
	 */
	public Request getR() {
		return r;
	}
	/**
	 * setR(Request r) method to Set r(request) of RequestPhase
	 * @param r : the method received a parameter with Request type that
	 * describes the r(request) of the Request in phase (RequestPhase)
	 */
	public void setR(Request r) {
		this.r = r;
	}
	/**
	 * getPhase() method doesn't received any parameters
	 * @return phase : the phase of the RequestPhase
	 */
	public Phase getPhase() {
		return phase;
	}
	/**
	 * setPhase(Phase phase) method to Set phase of RequestPhase
	 * @param phase : the method received a parameter with Phase type that
	 * describes the phase of the Request in phase (RequestPhase)
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	/**
	 * getState() method doesn't received any parameters
	 * @return phasestate : the phasestate of the RequestPhase
	 */
	public State getState() {
		return phasestate;
	}
   /**
    * Extension(Date newDueDate) : The method Checks the legality of the newDueDate
    * @param newDueDate : to extend the oldDueDate
    * @return boolean variable :
    * if the newDueDate is legal the method will return true
    * else,(illegal newDueDate), it will return false
    */
	public boolean Extension(Date newDueDate) {
		if (newDueDate.compareTo(dueDate) < 0) {
			System.out.println("cann't Extend the due date ");
			return false;
		}
		setDueDate(newDueDate);
		return true;
	}
	/**
	 * Returns a string representation of the object.*/
	

/**
 * toString():method returns a string representation of the RequestPhase Class.
 */
	@Override
	public String toString() {
		return "phase" + phase.toString() + "state" + phasestate;
	}
}
