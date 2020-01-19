package Entity;

import java.util.ArrayList;
/**
 * The Employee: represents a Student that he is a employee in the database.
 *  Employee is an ICM-User in DataBase with a specific job
 *  Class Employee Extends from User Class
 *  The Employee has private variables: 
 * @param id
 * @param belong 
 * @param job
 * @param fullname
 * @param supportSystem 
 * @param roles
 * and variables of User Class
 * @author Arkan Muhammad
 */
public class Employee extends User {

	private int id;
	private Belong belong;
	private String job;
	private String fullname;
	private String supportSystem;
	private ArrayList<Role> roles = new ArrayList<Role>();
	 /**
	 * First Constructor that received:
	 * @param fullname
	 * @param id
	 * @param job
	 */
	public Employee(String fullname, int id, String job) {
		this.fullname = fullname;
		this.id = id;
		this.job = job;
	}
	 /**
	 * Second Constructor that received:
	 * @param fullname
	 * @param id
	 * @param job
	 * @param supportSystem
	 */
	public Employee(String fullname, int id, String job, String supportSystem) {
		this.fullname = fullname;
		this.id = id;
		this.job = job;
		this.supportSystem = supportSystem;
	}
/**
 * Third Constructor that received:
 * @param username
 * @param firstName
 * @param lastName
 * @param job
 */
	public Employee(String username, String firstName, String lastName, String job) {
		super(username, firstName, lastName);
		this.job = job;
	}
/**
 * Third Constructor that received:
 * @param username
 * @param password
 * @param firstName
 * @param lastName
 * @param job
 */
	public Employee(String username, String password, String firstName, String lastName, String job) {
		super(username, password, firstName, lastName);
		this.job = job;
	}
/**
 * Fourth Constructor that received:
 * @param id
 * @param belong
 * @param roles
 * @param email
 * @param password
 * @param username
 * @param firstName
 * @param lastName
 * @param job
 */
	public Employee(int id, Belong belong, ArrayList<Role> roles, String email, String password, String username,
			String firstName, String lastName, String job) {
		super(email, password, username, firstName, lastName);
		this.id = id;
		this.belong = belong;
		this.job = job;
	}
/**
 * Fifth Constructor that received:
 * @param id
 * @param belong
 * @param roles
 * @param email
 * @param password
 * @param username
 * @param firstName
 * @param lastName
 * @param myRequests
 */
	public Employee(int id, Belong belong, ArrayList<Role> roles, String email, String password, String username,
			String firstName, String lastName, ArrayList<Request> myRequests) {
		super(email, password, username, firstName, lastName, myRequests);
		this.id = id;
		this.belong = belong;
		this.roles = roles;
	}
/**
 * sixth Constructor that received:
 * @param username
 * @param firstName
 * @param lastName
 */
	public Employee(String username, String firstName, String lastName) {
		super(username, null, null);
	}
	/**
	 * getFullname() method doesn't received any parameters
	 * @return fullname : the fullname of the Employee
	 */	
	public String getFullname() {
		return fullname;
	}
	/**
	 * setFullname(String fullname) method to Set fullname of Employee
	 * @param fullname : the method received a parameter with String type that
	 * it describe the fullname of the Employee
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * getSupportSystem() method doesn't received any parameters
	 * @return supportSystem : the supportSystem of the Employee
	 */
	public String getSupportSystem() {
		return supportSystem;
	}
	/**
	 * setSupportSystem(String supportSystem) method to Set supportSystem of Employee
	 * @param supportSystem : the method received a parameter with String type that
	 * it describe the supportSystem of the Employee
	 */
	public void setSupportSystem(String supportSystem) {
		this.supportSystem = supportSystem;
	}
	/**
	 * getBelong() method doesn't received any parameters
	 * @return belong : the belong of the Employee
	 */
	public Belong getBelong() {
		return belong;
	}
	/**
	 * setJob(String job) method to Set job of Employee
	 * @param job : the method received a parameter with String type that
	 * it describe the job of the Employee
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * getId() method doesn't received any parameters
	 * @return id : the id of the Employee
	 */
	public int getId() {
		return id;
	}
	/**
	 * setId(int id) method to Set id of Employee
	 * @param id : the method received a parameter with String type that
	 * it describe the id of the Employee
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * getJob() method doesn't received any parameters
	 * @return job : the job of the Employee
	 */
	public String getJob() {
		return this.job;
	}
	/**
	 * setBelong(Belong belong) method to Set belong of Employee
	 * @param belong : the method received a parameter with Belong type that
	 * it describe the belong of the Employee
	 */
	public void setBelong(Belong belong) {
		this.belong = belong;
	}
	/**
	 * getRoles() method doesn't received any parameters
	 * @return roles : the roles of the Employee
	 */
	public ArrayList<Role> getRoles() {
		return roles;
	}
	/**
	 * setRoles(ArrayList<Role> roles) method to Set roles of Employee
	 * @param roles : the method received a parameter with ArrayList<Role> type that
	 * it describe the roles of the Employee
	 */
	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}
	/**
	  * enum Belong that describes the Belong of a specific Employee 
	 * @author Arkan Muhammad
	 *
	 */
	public enum Belong {
		Lecturer, AdminEmployee, Manager;
	}
	/**
	 * removeRole : method the received an Role and added the role to ArrayList<Role> Roles
	 * if the role exists in the Roles => return "false";
	 * else adding the role to Roles and printing a specific Message and return "true"
	 * @param role 
	 * @return boolean
	 */
	public Boolean addRole(Role role) {
		if (roles.contains(role)) {
			System.out.println("this role is already existing ");
			return false;
		}
		roles.add(role);
		return true;
	}
/**
 * removeRole : method the received an Role and Remove the role from ArrayList<Role> Roles
 * if the role doesn't exists in the Roles => return "false";
 * else removing the role from Roles and return "true"
 * @param role 
 * @return boolean
 */
	public Boolean removeRole(Role role) {
		if (!roles.contains(role))
			return false;
		roles.remove(role);
		return true;

	}
/**
  * enum Role that describes the Role of a specific Employee 
 * @author Arkan Muhammad
 *
 */
	public enum Role {
		Tester, CommitteeMember, Chairman, PerformanceLeader, Evaluator;
	}
}
