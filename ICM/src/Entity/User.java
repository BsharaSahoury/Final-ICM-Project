package Entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
  * The <code>Member</code> entity, represents a Member in the database.
  *  User is an ICM-User in DataBase 
  *  The User has private variables: username, password, firstname, lastname, email ,serialVersionID
  *  and an ArrayList myRequests with 'Request' type that describes the requests the the user Submitted
 * @author Arkan Muhammad
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;

	private ArrayList<Request> myRequests = new ArrayList<Request>();

	/**
	 * First Constructor:
	 * Default constructor that doesn't received any parameters
	 */
	public User() {

	}
	/**
	 * Second Constructor that received:
	 * @param username
	 * @param firstName
	 * @param lastName
	 */
	public User(String username, String firstName, String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	/**
	 * Third Constructor that received:
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 */
	public User(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	/**
	 * Fourth Constructor that received:
	 * @param email
	 * @param password
	 * @param username
	 * @param firstName
	 * @param lastName
	 */
	public User(String email, String password, String username, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	/**
	 * Fifth Constructor that received:
	 * @param email
	 * @param password
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param myRequests
	 */
	public User(String email, String password, String username, String firstName, String lastName,
			ArrayList<Request> myRequests) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.myRequests = myRequests;
	}
	/**
	 * getFirstName() method doesn't received any parameters
	 * @return firstName : the firstName of the User
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * setFirstName(String firstName) method to Set firstName of User
	 * @param firstName : the method received a parameter with String type that
	 * describes the firstName of the User
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * getLastName() method doesn't received any parameters
	 * @return lastName : the lastName of the User
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * setLastName(String lastName) method to Set lastName of User
	 * @param lastName : the method received a parameter with String type that
	 * describes the lastName of the User
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * getLastName() method doesn't received any parameters
	 * @return myRequests : the ArrayList of the Requests that the User Submitted
	 */
	public ArrayList<Request> getMyRequests() {
		return myRequests;
	}
	/**
	 * setMyRequests(ArrayList<Request> myRequests) method to Set myRequests of User
	 * @param myRequests : the method received a parameter with Request type that
	 * describes the myRequests of the User(requests of User)
	 */
	public void setMyRequests(ArrayList<Request> myRequests) {
		this.myRequests = myRequests;
	}
	/**
	 * getLastName() method doesn't received any parameters
	 * @return email : the email of the User
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * setEmail(String email) method to Set email of User
	 * @param email : the method received a parameter with String type that
	 * describes the email of the User
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * getLastName() method doesn't received any parameters
	 * @return password : the password of the User
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * setPassword(String password) method to Set password of User
	 * @param password : the method received a parameter with String type that
	 * describes the password of the User
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * getLastName() method doesn't received any parameters
	 * @return username : the username of the User
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * setFirstName(String username) method to Set username of User
	 * @param username : the method received a parameter with String type that
	 * describes the username of the User
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}