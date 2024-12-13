//Class to manage a collection of details

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;

public class Reports {
	
	//Linked lists storing reports
	private LinkedList<ReportDetails> accidentReport;
	private LinkedList<ReportDetails> healthAndSafetyRiskReport;
	
	//Constructor to initialize and populate from lists
	public Reports()
	{
		accidentReport = new LinkedList<>();
		healthAndSafetyRiskReport = new LinkedList<>();
		String fileContents;//To store a line read from file
		String[] results = new String[5];
		
		//Populating RegDetails
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
				
				//Creating new reportDetails object from parsed data
				ReportDetails temp = new ReportDetails(resultPart[0], Integer.parseInt(resultPart[1]), resultPart[2], Integer.parseInt(resultPart[3]), resultPart[4], Integer.parseInt(resultPart[5]));

				if (resultPart[0].equals("Accident Report")) {
				    accidentReport.add(temp);
				} else if (resultPart[0].equals("Health and Safety Risk Report")) {
				    healthAndSafetyRiskReport.add(temp);
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
	public synchronized void addDetails(String reportName, int reportID, String date, int employeeID, String status, int assignedID)
	{
		//Creating new report details objects from parameters
		ReportDetails temp = new ReportDetails(reportName, reportID, date, employeeID, status, assignedID);
		
		  if (reportName.equals("Accident Report")) {
		        accidentReport.add(temp);
		    } else if (reportName.equals("Health and Safety Risk Report")) {
		        healthAndSafetyRiskReport.add(temp);
		    } 
		
		  // Updating the file storage with new entry
		    try (FileWriter fw = new FileWriter(new File("ReportDetails.txt"), true); // Enable append mode
		         BufferedWriter bw = new BufferedWriter(fw)) {
		        bw.write(temp.toString());
		        bw.newLine(); // Add a new line for each entry
		        System.out.println("Report added to file: " + temp.toString());
		    } catch (IOException e) {
		        System.err.println("Error writing report to file.");
		        e.printStackTrace();
		    }
	}
	
	//Getting accident report size
	public synchronized int getAccidentReportSize()
	{
		return accidentReport.size();
	}
	
	//Storing located details to string
	public synchronized String getAccidentReport(int location)
	{
		ReportDetails temp = accidentReport.get(location);
		
		return temp.toString();
	}
	
	//Searching for report details
	public synchronized String searchDetails(String reportName)
	{
		String result="-1";
		Iterator<ReportDetails>i;

		 //Iterate over accident reports 
		if (reportName.equals("Accident Report")) {
	        i = accidentReport.iterator();
	    }
		
		else {
	        return "Invalid report type."; //Handle invalid reportType
	    }
	    			
		//Iterating through the list to find a matching report
	    while (i.hasNext()) {
	        ReportDetails temp = i.next();
	        if (temp.getReportName().equals(reportName)) {
	            result = temp.toString();
	            break;
	        }
	    }
			
		return result;			
	}

	
	//Method to see if report exists
	public boolean doesReportExist(int reportID) {
	for (ReportDetails report : accidentReport) {
			 if (report.getReportID() == reportID) { 
			       return true; 
			 }
		}
	
	for (ReportDetails report : healthAndSafetyRiskReport) {
		 if (report.getReportID() == reportID) { 
		       return true; 
		 }
	}
		 return false;//Report exists
	}
	
	//Assigning report to employee and updating status
	public synchronized String assignReport(int reportID, int employeeID)
	{
		for (ReportDetails report : accidentReport) {
			if(report.getReportID() == reportID) {
				report.setAssignedID(employeeID);
				report.setStatus("Assigned");
	            return "Assigned Report: " + report.toString(); // Include details of the assigned report
			}
		}
		
		for (ReportDetails report : healthAndSafetyRiskReport) {
			if(report.getReportID() == reportID) {
				report.setAssignedID(employeeID);
				report.setStatus("Assigned");
	            return "Assigned Report: " + report.toString();
			}
		}
		return "Report ID not found";//Report not assigned
	}
	
	//Method to get report details
	public synchronized String getReportDetails(int reportID) {
		for (ReportDetails report : accidentReport) {
			if(report.getReportID() == reportID) {
				return "Report Type: " + report.getReportName() +
						", Report ID: " + report.getReportID() +
		                   ", Assigned Employee ID: " + report.getAssignedID() +
		                   ", Date: " + report.getDate() +
		                   ", Status: " + report.getStatus();
			}
		}
		
		for (ReportDetails report : healthAndSafetyRiskReport) {
	        if (report.getReportID() == reportID) {
	            return "Report Type: " + report.getReportName() +
	                   ", Report ID: " + report.getReportID() +
	                   ", Assigned Employee ID: " + report.getAssignedID() +
	                   ", Date: " + report.getDate() +
	                   ", Status: " + report.getStatus();
	        }
	 }
		
		return "Report not found";
	}

	// Method to get assigned reports for an employee
	public synchronized String assignedReports(int assignedID) {
	    StringBuilder result = new StringBuilder();

	    try {
	        //Opening the file for reading
	        FileReader fr = new FileReader(new File("ReportDetails.txt"));
	        BufferedReader br = new BufferedReader(fr);

	        String fileContents;

	        while ((fileContents = br.readLine()) != null) {
	        	
	            String[] resultPart = fileContents.split("@");

	            //Parsing employee id field
	            int assignedEmployeeID = Integer.parseInt(resultPart[5]);
	            
	            if (assignedEmployeeID == assignedID) {
	            	result.append(fileContents).append("\n");
	            }
	        }

	        br.close(); // Close the file reader
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return "Error: Report file not found.";
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "Error: Unable to read the report file.";
	    }

	    if(result.length() > 0) {
	    	return result.toString();
	    }
	    else {
	    	return "No reports assigned to you";
	    }
	}
	
	// Method updating report file
	public synchronized void updateReportFile() {
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter("ReportDetails.txt"))) {
	        // Write all accident reports
	        for (ReportDetails report : accidentReport) {
	            bw.write(report.toString());
	            bw.newLine();
	        }
	        // Write all health and safety risk reports
	        for (ReportDetails report : healthAndSafetyRiskReport) {
	            bw.write(report.toString());
	            bw.newLine();
	        }
	        System.out.println("Report file updated successfully.");
	    } catch (IOException e) {
	        System.err.println("Error updating report file.");
	        e.printStackTrace();
	    }
	}
	
	
	//Updating status to closed
		public synchronized String closeReport(int reportID)
		{
			for (ReportDetails report : accidentReport) {
					report.setStatus("Closed");
		            return "Closed Report: " + report.toString(); // Include details of the assigned report				
			}
			for (ReportDetails report : healthAndSafetyRiskReport) {
				report.setStatus("Closed");
	            return "Closed Report: " + report.toString(); // Include details of the assigned report				
		}
			return "Report ID not found";//Report not assigned
		}


}

