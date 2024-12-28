//Class to manage a collection of details

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
	private LinkedList<ReportDetails> healthAndSafetyRiskReport;
	
	//Constructor to initialize and populate from lists
	public Reports()
	{
		accidentReport = new LinkedList<>();
		healthAndSafetyRiskReport = new LinkedList<>();
		String fileContents;//To store a line read from file
		String[] results = new String[5];
		int maxID = 0;
		
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
				
				//Creating new reportDetails object from parsed data
				ReportDetails temp = new ReportDetails(resultPart[0], Integer.parseInt(resultPart[1]), resultPart[2], Integer.parseInt(resultPart[3]), resultPart[4], Integer.parseInt(resultPart[5]));

				 //Adding to appropriate list
				if (resultPart[0].equals("Accident Report")) {
				    accidentReport.add(temp);
				} else if (resultPart[0].equals("Health and Safety Risk Report")) {
				    healthAndSafetyRiskReport.add(temp);
				}
				
				//Update maxID
                int currentID = Integer.parseInt(resultPart[1]);
                if (currentID > maxID) {
                    maxID = currentID;
                }

			}
	        ReportDetails.setIdCounter(maxID + 1);//Updating by 1 

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
	public synchronized void addDetails(String reportName, int reportID,  String date, int employeeID, String status, int assignedID)
	{	int newReportID = ReportDetails.idCounter++;

		//Creating new report details objects from parameters
		ReportDetails temp = new ReportDetails(reportName, newReportID, date, employeeID, status, assignedID);
		
		if (reportName.equals("Accident Report")) {
	        accidentReport.add(temp);
	    } else if (reportName.equals("Health and Safety Risk Report")) {
	        healthAndSafetyRiskReport.add(temp);
	    } 
				
		//Updating the file storage with new list
		try 
		{
		FileWriter fw = new FileWriter(new File("ReportDetails.txt"));

		  //Writing accident reports to the file
        for (ReportDetails report : accidentReport) {
            fw.write(report.toString() + "\n");
        }

        //Writing health and safety reports to the file
        for (ReportDetails report : healthAndSafetyRiskReport) {
            fw.write(report.toString() + "\n");
        }
		fw.close();//Closing file writer
		} 
		
		catch (IOException e) 
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	
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
		 return false;
	}
	
	//Getting accident report size
	public synchronized int getAccidentReportSize()
	{
		return accidentReport.size();
	}
	
	//Getting h&s report size
	public synchronized int healthAndSafetyReportSize()
	{
		return healthAndSafetyRiskReport.size();
	}
	
	//Storing located details to string
	public synchronized String getAccidentReport(int location)
	{
		ReportDetails temp = accidentReport.get(location);
		
		return temp.toString();
	}
	
	//Retrieving all accident reports
	public synchronized String getAllAccidentReports() {
		if (accidentReport.isEmpty()) {
	        return "No accident reports available.";
	    }

	    StringBuilder allReports = new StringBuilder();
	    for (ReportDetails report : accidentReport) {
	        allReports.append(report.toString()).append("\n");//Appending report
	    }

	    return allReports.toString().trim(); 
	}
		
	//Assigned reports to currently logged in employee
		public synchronized String assignedReports(int employeeID) {
		    StringBuilder result = new StringBuilder();

		    //Loop through accident reports
		    for (ReportDetails report : accidentReport) {
		        if (report.getAssignedID() == employeeID) {
		            result.append(report.toString()).append("\n");
		        }
		    }

		    //Loop through health and safety risk reports
		    for (ReportDetails report : healthAndSafetyRiskReport) {
		        if (report.getAssignedID() == employeeID) {
		            result.append(report.toString()).append("\n");
		        }
		    }

		    if (result.length() > 0) {
		        result.setLength(result.length() - 1); 
		    } else {
		        return "No reports assigned to you.";
		    }

		    return result.toString().trim();
		}

	
	//Assigning report to employee and updating status
		public synchronized String assignReport(int reportID, int employeeID)
		{
			for (ReportDetails report : accidentReport) {
				if(report.getReportID() == reportID) {
					report.setStatus("Assigned");
					report.setAssignedID(employeeID);
		            return  "Report Assigned: " + report.toString().trim(); 
				}
			}
			
			for (ReportDetails report : healthAndSafetyRiskReport) {
				if(report.getReportID() == reportID) {
					report.setStatus("Assigned");
					report.setAssignedID(employeeID);
		            return "Report Assigned: " + report.toString().trim();
				}
			}
			return "Report ID not found";//Report not assigned
		}
		
		//Method updating report file
		public synchronized void updateReportFile() {
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter("ReportDetails.txt"))) {
		        boolean firstLine = true;

		        // Write all accident reports
		        for (ReportDetails report : accidentReport) {
		            if (!firstLine) bw.newLine(); // Add newline only between reports
		            bw.write(report.toString());
		            firstLine = false;
		        }

		        // Write all health and safety risk reports
		        for (ReportDetails report : healthAndSafetyRiskReport) {
		            if (!firstLine) bw.newLine(); // Add newline only between reports
		            bw.write(report.toString());
		            firstLine = false;
		        }
		        bw.close();
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
					if(report.getReportID() == reportID) {					
						report.setStatus("Closed");
						report.setAssignedID(0);
				        return "Closed Report: " + report.toString().trim();				
					}
				}
				for (ReportDetails report : healthAndSafetyRiskReport) {
					if(report.getReportID() == reportID) {					
						report.setStatus("Closed");
						report.setAssignedID(0);
				        return "Closed Report: " + report.toString().trim();				
					}
				}
				return "Report ID not found";
		}
		
		//Updating status to open
		public synchronized String openReport(int reportID)
		{
				for (ReportDetails report : accidentReport) {
						report.setStatus("Open");
						report.setAssignedID(0);
						return "Open Report: " + report.toString();				
				}
				for (ReportDetails report : healthAndSafetyRiskReport) {
						report.setStatus("Open");
						report.setAssignedID(0);
					    return "Open Report: " + report.toString(); 			
				}
						return "Report ID not found";
		}
 
		}




