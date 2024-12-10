//Class to manage a collection of details

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.IOException;

public class Reports {
	
	//Linked lists storing reports
	private LinkedList<ReportDetails> accidentReport;
	private LinkedList<ReportDetails> healthAndSafetyReport;
	
	//Constructor to initialize and populate from lists
	public Reports()
	{
		accidentReport = new LinkedList<>();
		healthAndSafetyReport = new LinkedList<>();
		String fileContents;//To store a line read from file
		String[] results = new String[5];
		
		///Populating RegDetails
		try 
		{
			//Opening file for reading
			FileReader fr = new FileReader(new File("ReportDetails.txt"));
			BufferedReader br = new BufferedReader(fr);
			
			//Reading file line by line
			while((fileContents = br.readLine())!=null)
			{
				System.out.println(fileContents);
				//Splitting each line into parts
				String[] resultPart = fileContents.split("@");
				
				
                int reportType = Integer.parseInt(resultPart[0]);

				//Creating new reportDetails object from parsed data
				ReportDetails temp = new ReportDetails(reportType, Integer.parseInt(resultPart[1]), resultPart[2], Integer.parseInt(resultPart[3]), Integer.parseInt(resultPart[4]), Integer.parseInt(resultPart[5]));

				 //Adding to appropriate list
                if (reportType == 1) {
                    accidentReport.add(temp);
                } else if (reportType == 2) {
                    healthAndSafetyReport.add(temp);
                }
			}
		} 
			catch (FileNotFoundException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//Method adding new reportDetails object to list and update file
	public synchronized void addDetails(int reportType, int reportID, String date, int employeeID, int status, int assignedID)
	{
		//Creating new report details objects from parameters
		ReportDetails temp = new ReportDetails(reportType, reportID, date, employeeID, status, assignedID);
		
		if(reportType == 1) {
			accidentReport.add(temp);
		}
		
		if(reportType == 2) {
			healthAndSafetyReport.add(temp);
		}
		
		//Updating the file storage with new list
		try 
		{
		FileWriter fw = new FileWriter(new File("ReportDetails.txt"));
		
		  //Writing accident reports to the file
        for (ReportDetails report : accidentReport) {
            fw.write(report.toString() + "\n");
            System.out.println("Writing to file: " + report.toString());
        }

        //Writing health and safety reports to the file
        for (ReportDetails report : healthAndSafetyReport) {
            fw.write(report.toString() + "\n");
            System.out.println("Writing to file: " + report.toString());
        }
		fw.close();//Closing file writer
		} 
		
		catch (IOException e) 
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	
	}
	
	//Getting accident report size
	public synchronized int getAccidentReportSize()
	{
		return accidentReport.size();
	}
	
	//Getting h&s report size
	public synchronized int healthAndSafetyReportSize()
	{
		return healthAndSafetyReport.size();
	}
	
	//Storing located details to string
	public synchronized String getAccidentReport(int location)
	{
		ReportDetails temp = accidentReport.get(location);
		
		return temp.toString();
	}
	
	//Storing located details to string
	public synchronized String getHealthAndSafetyReport(int location)
	{
		ReportDetails temp = accidentReport.get(location);
			
		return temp.toString();
	}
	
	//Searching for report details
	/*public synchronized String searchDetails(String reportType)
	{
		String result="-1";
		Iterator i = accidentReport.iterator();
		ReportDetails temp;
		
		while(i.hasNext())
		{
			temp = (ReportDetails)i.next();
			
			if(temp.getReportType())
			{
				result = temp.toString();
				break;
			}
		}
		
		return result;
		

	}*/
}

