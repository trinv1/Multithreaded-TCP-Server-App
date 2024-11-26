//Class for encapsulting user details

public class Details {
	
	//Encapsulated fields
	private String name, email, password, depName, role;
	private int employeeID;
	
	//Details constructor
	public Details(String n, int ei, String e, String p, String dn, String r)
	{
		name = n;
		employeeID = ei;
		email = e;
		password = p;
		depName = dn;
		role = r;
	}
	
	//Accessing users name
	public String getName()
	{
		return name;
	}
	
	//Printing details to string
	public String toString()
	{
		String temp = name+"@"+employeeID+"@"+email+"@"+password+"@"+depName+"@"+role;
		return temp;
	}
	
}
