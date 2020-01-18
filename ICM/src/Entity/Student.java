package Entity;

import java.util.ArrayList;

public class Student extends User{
	
private int id;
private String department;
public Student(String username,String firstName,String lastName) {
	super(username,firstName,lastName);
}
public Student(String username,String password,String firstName,String lastName) {
	super(username,password,firstName,lastName);
}
public Student(int id, String firstName, String lastName, String email, String department, String password,String username) {
	super( email, password, username, firstName, lastName);
	this.id = id;
	this.department = department;
}

public Student(int id, String department,String email, String password, String username, String firstName, String lastName,
		ArrayList<Request> myRequests) {
	super( email, password, username, firstName, lastName,myRequests);
	this.id = id;
	this.department = department;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}


}
