//Class for encapsulting report details

public class ReportDetails {
	
	//Encapsulated fields
	private String date, status;
	private int employeeID, reportType, reportID, assignedID;
	
	//Details constructor
	public ReportDetails(int rt, int rid, String d, int eid, String s, int aid)
	{
		reportType = rt;
		reportID = rid;
		date = d;
		employeeID = eid;
		status = s;
		assignedID = aid;
	}
	
	//Accessing report type
	public int getReportType()
	{
		return reportType;
	}
	
	//Printing details to string
	public String toString()
	{
		String temp = reportType+"@"+reportID+"@"+date+"@"+employeeID+"@"+status+"@"+assignedID;
		return temp;
	}
	
}
