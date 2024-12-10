//Class for encapsulting user details

public class UserDetails {
	
	//Encapsulated fields
	private String name, email, password, depName, role;
	private int employeeID;
	
	//Details constructor
	public UserDetails(String n, int ei, String e, String p, String dn, String r)
	{
		name = n;
		employeeID = ei;
		email = e;
		password = p;
		depName = dn;
		role = r;
	}
	
	//Accessing users email
	public String getEmail()
	{
		return email;
	}
	
	//Accessing users email
	public String getPassword()
	{
		return password;
	}
	
	//Printing details to string
	public String toString()
	{
		String temp = name+"@"+employeeID+"@"+email+"@"+password+"@"+depName+"@"+role;
		return temp;
	}
	
}
