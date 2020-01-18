package Entity;

import java.util.ArrayList;

public class Employee extends User {

	private int id;
	private Belong belong;
	private String job;
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getSupportSystem() {
		return supportSystem; 
	}

	public void setSupportSystem(String supportSystem) {
		this.supportSystem = supportSystem;
	}

	public Belong getBelong() {
		return belong;
	}

	public void setJob(String job) {
		this.job = job;
	}

	private String fullname;
	private String supportSystem;
	private ArrayList<Role> roles = new ArrayList<Role>();
	
	
	public Employee(String fullname,int id,String job) {
		this.fullname=fullname;
		this.id=id;
		this.job=job;
	}
	
	public Employee(String fullname,int id,String job, String supportSystem) {
		this.fullname=fullname;
		this.id=id;
		this.job=job;
		this.supportSystem=supportSystem;
	}
	
	public Employee(String username,String firstName, String lastName, String job) {
		super(username,firstName,lastName);
		this.job=job;
	}
	public Employee(String username,String password,String firstName, String lastName, String job) {
		super(username,password,firstName,lastName);
		this.job=job;
	}
	

	public Employee(int id, Belong belong, ArrayList<Role> roles,String email, String password, String username, String firstName, String lastName,String job) {
		super( email, password, username, firstName, lastName);
		this.id = id;
		this.belong = belong;
		this.job=job;
	}

	public Employee(int id, Belong belong, ArrayList<Role> roles,String email, String password, String username, String firstName, String lastName,
			ArrayList<Request> myRequests) {
		super( email, password, username, firstName, lastName,myRequests);
		this.id = id;
		this.belong = belong;
		this.roles = roles;
	}

	public Employee(String username,String firstName,String lastName) {
		super(username,null,null);
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJob() {
		return this.job;
	}

	public void setBelong(Belong belong) {
		this.belong = belong;
	}
	public ArrayList<Role> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}
	public enum Belong{
		Lecturer,AdminEmployee, Manager;
	}

	public Boolean addRole(Role role) {
		if (roles.contains(role)) {
			System.out.println("this role is already existing ");
			return false;
		}
		roles.add(role);
		return true;
	}

	public Boolean removeRole(Role role) {
		if (!roles.contains(role))
			return false;
		roles.remove(role);
		return true;

	}

	public enum Role {
		Tester, CommitteeMember, Chairman, PerformanceLeader, Evaluator;
	}
}
