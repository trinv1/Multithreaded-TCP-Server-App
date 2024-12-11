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
	public synchronized void addDetails(int reportType, int reportID, String date, int employeeID, int status, int assignedID)
	{
		//Creating new report details objects from parameters
		ReportDetails temp = new ReportDetails(reportType, reportID, date, employeeID, status, assignedID);
		
		if(reportType == 1) {
			accidentReport.add(temp);
		}
		
		if(reportType == 2) {
			healthAndSafetyRiskReport.add(temp);
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

        //Writing health and safety risk  reports to the file
        for (ReportDetails report : healthAndSafetyRiskReport) {
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
	
	//Storing located details to string
	public synchronized String getAccidentReport(int location)
	{
		ReportDetails temp = accidentReport.get(location);
		
		return temp.toString();
	}
	
	//Searching for report details
	public synchronized String searchDetails(int reportType)
	{
		String result="-1";
		Iterator<ReportDetails>i;

		 //Iterate over accident reports 
		if (reportType == 1) {
	    	i = accidentReport.iterator();
	    }
		
		else {
	        return "Invalid report type."; //Handle invalid reportType
	    }
	    			
		//Iterating through the list to find a matching report
	    while (i.hasNext()) {
	        ReportDetails temp = i.next();
	        if (temp.getReportType() == reportType) {
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
				report.setStatus(2);
	            return "Assigned Report: " + report.toString(); // Include details of the assigned report
			}
		}
		
		for (ReportDetails report : healthAndSafetyRiskReport) {
			if(report.getReportID() == reportID) {
				report.setAssignedID(employeeID);
				report.setStatus(2);
	            return "Assigned Report: " + report.toString();
			}
		}
		return "Report ID not found";//Report not assigned
	}
	
	//Method to get report details
	public synchronized String getReportDetails(int reportID) {
		for (ReportDetails report : accidentReport) {
			if(report.getReportID() == reportID) {
				return "Report Type: " + report.getReportType() +
						", Report ID: " + report.getReportID() +
		                   ", Assigned Employee ID: " + report.getAssignedID() +
		                   ", Date: " + report.getDate() +
		                   ", Status: " + report.getStatus();
			}
		}
		
		for (ReportDetails report : healthAndSafetyRiskReport) {
	        if (report.getReportID() == reportID) {
	            return "Report Type: " + report.getReportType() +
	                   ", Report ID: " + report.getReportID() +
	                   ", Assigned Employee ID: " + report.getAssignedID() +
	                   ", Date: " + report.getDate() +
	                   ", Status: " + report.getStatus();
	        }
	 }
		
		return "Report not found";
	}
	
	/*//Method to get assigned reports for employee
	public synchronized String assignedReports(int assignedID){		
		StringBuilder result = new StringBuilder();

		///Populating RegDetails
				try 
				{
					//Opening file for reading
					FileReader fr = new FileReader(new File("ReportDetails.txt"));
					BufferedReader br = new BufferedReader(fr);
					
					String fileContents;

					//Reading file line by line
					while((fileContents = br.readLine())!=null)
					{
						System.out.println(fileContents);
					}
					
					for(ReportDetails report : accidentReport) {
						if(report.getAssignedID() == assignedID) {
							result.append(report.toString()+"\n");
						}
					}
						
					for(ReportDetails report : healthAndSafetyRiskReport) {
						if(report.getAssignedID() == assignedID) {
							result.append(report.toString()+"\n");
						}
					}
					
					br.close();
					
				} catch (FileNotFoundException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			return result.toString();
		
	}*/

	// Method to get assigned reports for an employee
	public synchronized String assignedReports(int assignedID) {
	    StringBuilder result = new StringBuilder();

	    try {
	        // Open the file for reading
	        FileReader fr = new FileReader(new File("ReportDetails.txt"));
	        BufferedReader br = new BufferedReader(fr);

	        String fileContents;

	        while ((fileContents = br.readLine()) != null) {
	        	
	            String[] resultPart = fileContents.split("@");

	            // Parsing employee id field
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




	
	//Method updating report file
	public synchronized boolean updateReport(int reportID, int employeeID) {
	    boolean isUpdated = false;

	    //Iterating over accident reports
	    for (ReportDetails report : accidentReport) {
	        if (report.getReportID() == reportID) {
	            report.setAssignedID(employeeID);
	            isUpdated = true;
	            break;
	        }
	    }

	    //Iterating over health and safety risk reports if not found in accident reports
	    if (!isUpdated) {
	        for (ReportDetails report : healthAndSafetyRiskReport) {
	            if (report.getReportID() == reportID) {
	                report.setAssignedID(employeeID);
	                isUpdated = true;
	                break;
	            }
	        }
	    }

	    //Rewriting the updated data back to the file
	    if (isUpdated) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ReportDetails.txt"))) {
	            for (ReportDetails report : accidentReport) {
	                bw.write(report.toString());
	                bw.newLine();
	            }
	            for (ReportDetails report : healthAndSafetyRiskReport) {
	                bw.write(report.toString());
	                bw.newLine();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return isUpdated;
	}


}

