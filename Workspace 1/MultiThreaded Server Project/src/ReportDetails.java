//Class for encapsulting report details

public class ReportDetails {
	
	//Encapsulated fields
	private String date, reportName;
	private int employeeID, reportID, status, assignedID;
	private static int idCounter = 1;
	String str;
	
	//Details constructor
	public ReportDetails(String rn, int rid, String d, int eid, int s, int aid)
	{
		reportName = rn;
		reportID = idCounter++;
		date = d;
		employeeID = eid;
		status = s;
		assignedID = 0;
	}
	
	//Accessing report type
	public String getReportName()
	{
		return reportName;
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
	
	public String toString()
	{
		String temp = reportName+"@"+reportID+"@"+date+"@"+employeeID+"@"+status+"@"+assignedID;
		return temp;
	}

	
}
