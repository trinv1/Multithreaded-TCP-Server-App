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
