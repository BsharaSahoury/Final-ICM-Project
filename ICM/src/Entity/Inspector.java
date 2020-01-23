package Entity;
/**
 * Inspector class is a Singleton Class
 *  @param MyInspector,
 *  variables of User Class
 * @author Arkan Muhammad
 */
public class Inspector extends Employee {
/**
 * First Constructor:
 * @param e:Employee 
 */
	public Inspector(Employee e) {
		super(e.getJob(), e.getEmail(), e.getPassword(), e.getUsername());
		// TODO Auto-generated constructor stub
	}
	/**
	 * creating an object of Inspector
	 */
	private static Inspector MyInspector;
/**
 *             ** Static 'instance' method **
 * getInstance : method to get the only object available
 * @param e :Employee
 * @return Inspector
 */
	public static Inspector getInstance(Employee e) {
		if (MyInspector == null) {
			MyInspector = new Inspector(e);
		}
		return MyInspector;
	}
}
