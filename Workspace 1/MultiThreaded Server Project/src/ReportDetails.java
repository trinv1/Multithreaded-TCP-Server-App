//Class for encapsulting report details

public class ReportDetails {
	
	//Encapsulated fields
	private String date;
	private int employeeID, reportID, status, reportType, assignedID;
	private static int idCounter = 1;
	
	//Details constructor
	public ReportDetails(int rt, int rid, String d, int eid, int s, int aid)
	{
		reportType = rt;
		reportID = idCounter++;
		date = d;
		employeeID = eid;
		status = s;
		assignedID = 0;
	}
	
	//Accessing report type
	public int getReportType()
	{
		return reportType;
	}
	
	//Accessing report type
	public int getReportID()
	{
		return reportID;
	}
	
	//Accessing assigned employeeid
    public int getAssignedID() {
        return assignedID;
    }
    
    //Accessing date
    public String getDate() {
        return date;
    }
    
    //Accessing status
    public int getStatus() {
        return status;
    }
    
    //Setting status
    public int setStatus(int status) {
    	return this.status = status;
    }
    
    //Setting assigned employee id
    public void setAssignedID(int assignedID) {
        this.assignedID = assignedID;
    }
	
	//Printing details to string
	public String toString()
	{
		String temp = reportType+"@"+reportID+"@"+date+"@"+employeeID+"@"+status+"@"+assignedID;
		return temp;
	}
	
}
