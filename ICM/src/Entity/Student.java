package Entity;

import java.util.ArrayList;
 /**
  * The Student: represents a Student that he is a student in the database.
  *  Student is an ICM-User in DataBase that his Role is Student
  *  Class Student Extends from User Class
  *  The Student has private variables: 
  * @param id
  * @param department
  * and variables of User Class
  * @author Arkan Muhammad
  */
public class Student extends User {

	private int id;
	private String department;
	/**
	 * First Constructor:
	 * @param username
	 * @param firstName
	 * @param lastName
	 */
	public Student(String username, String firstName, String lastName) {
		super(username, firstName, lastName);
	}
	/**
	 * Second Constructor that received:
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 */
	public Student(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
	}
	/**
	 * Third Constructor that received:
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param department
	 * @param password
	 * @param username
	 */
	public Student(int id, String firstName, String lastName, String email, String department, String password,
			String username) {
		super(email, password, username, firstName, lastName);
		this.id = id;
		this.department = department;
	}
	/**
	 * Fourth Constructor that received:
	 * @param id
	 * @param department
	 * @param email
	 * @param password
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param myRequests
	 */
	public Student(int id, String department, String email, String password, String username, String firstName,
			String lastName, ArrayList<Request> myRequests) {
		super(email, password, username, firstName, lastName, myRequests);
		this.id = id;
		this.department = department;
	}
	/**
	 * getId() method doesn't received any parameters
	 * @return id : the id of the Student
	 */
	public int getId() {
		return id;
	}
	/**
	 * setId(int id) method to Set id of Student
	 * @param id : the method received a parameter with int type that
	 * it describe the id of the Student
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * getDepartment() method doesn't received any parameters
	 * @return department : the department of the Student
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * setDepartment(String department) method to Set department of User
	 * @param department : the method received a parameter with String type that
	 * describes the department of the Student
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

}
