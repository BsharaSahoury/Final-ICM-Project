package Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The <code>Member</code> entity, represents a Member in the database.
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private String username;
	private String firstName;
	private String lastName;
	private ArrayList<Request> myRequests = new ArrayList<Request>(); 
	public User() {

	}
	public User(String username,String firstName, String lastName) {
		this.username=username;
		this.firstName=firstName;
		this.lastName=lastName;
	}
	public User(String username,String password,String firstName, String lastName) {
		this.username=username;
		this.password=password;
		this.firstName=firstName;
		this.lastName=lastName;
	}	

	public User(String email, String password, String username, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(String email, String password, String username, String firstName, String lastName,
			ArrayList<Request> myRequests) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.myRequests = myRequests;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ArrayList<Request> getMyRequests() {
		return myRequests;
	}

	public void setMyRequests(ArrayList<Request> myRequests) {
		this.myRequests = myRequests;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}