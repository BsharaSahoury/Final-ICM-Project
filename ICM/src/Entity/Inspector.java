package Entity;


public class Inspector extends Employee {
	


	public Inspector(Employee e) {
		super(e.getJob(), e.getEmail(),e.getPassword(),e.getUsername());
		// TODO Auto-generated constructor stub
	}


	private static Inspector MyInspector ;
	

	public static Inspector getInstance(Employee e) {
		if (MyInspector == null) {
			MyInspector = new Inspector(e);
		}
		return MyInspector;
	}
}
