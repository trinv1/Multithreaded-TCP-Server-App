//Class for encapsulting report details

public class ReportDetails {
	
	//Encapsulated fields
	private String date, reportName, status;
	private int employeeID, reportID;
	private int assignedID;
	static int idCounter =1;
	
	//Details constructor
	public ReportDetails(String rn, int rid, String d, int eid, String s, int aid)
	{
		reportName = rn;
		reportID = rid;
		date = d;
		employeeID = eid;
		status = s;
		assignedID = aid;
	}
	
	//Accessing report type
	public String getReportName()
	{
		return reportName;
	}
	
	//Setting Id counter
	public static void setIdCounter(int newIdCounter) {
	    idCounter = newIdCounter;
	}
	
	//Accessing report type
	public int getReportID()
	{
		return reportID;
	}
	
	//Accessing employee ID
	public int getEmployeeID()
	{
		return employeeID;
	}
	
	//Accessing status
	public String getStatus()
	{
		return status;
	}
	
	//Setting status
	public String setStatus(String status) {
		return this.status = status;
	}
	
	//Accessing assigned id
	public int getAssignedID()
	{
		return assignedID;
	}
	
	//Setting assigned id
	public int setAssignedID(int assignedID) {
		return this.assignedID = assignedID;
	}
	
	//Printing details to string
	public String toString()
	{
		String temp = reportName+"@"+reportID+"@"+date+"@"+employeeID+"@"+status+"@"+assignedID;
		return temp;
	}
	
}
