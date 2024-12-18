//Class handles communication between server and single client

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;
import java.io.FileWriter;

public class ServerThread extends Thread {

	private Socket myConnection;
	private ObjectOutputStream out;//Used to send messages to client
	private ObjectInputStream in;//Used to receive messages from client
	private String message;
	private String email, name, password, depName, role;
	private String date;
	private int employeeID, statusNum, option, reportID, assignedID, reportType;
	private RegistrationDetails shared;
	private Reports shared2;

	
	// Constructor to initialize the thread with a client socket
	public ServerThread(Socket s, RegistrationDetails reg, Reports rep)
	{
		myConnection = s;// Stores the client connection socket
		shared = reg;
		shared2 = rep;
	}
	
	public void run()
	{
		try 
		{	//Initializing input and output streams
			out = new ObjectOutputStream(myConnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(myConnection.getInputStream());
		
			//Communication Loop.
		do 
		{	
			do
			{
				sendMessage("1.Register\n2. Login");//Sending a message to the client
				message = (String)in.readObject();//Reads the client's response
			}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2"));
		
			//Registration
			if(message.equalsIgnoreCase("1"))
			{

				sendMessage("Please enter your name:");
				name = (String)in.readObject();
				
				sendMessage("Please enter your Employee ID:");
				message = (String)in.readObject();
				employeeID = Integer.parseInt(message);
				
				//If employee id already exists, must retry
				while (!shared.isEmployeeIDUnique(employeeID)) {
				    sendMessage("Error: Employee ID already exists. Please try again.");
				    sendMessage("Please enter your Employee ID:");
					message = (String)in.readObject();
					employeeID = Integer.parseInt(message);
				}
				
				sendMessage("Employee ID is not taken.");				
				sendMessage("Please enter your email:");
				email = (String)in.readObject();
				
				//If email already exists, must retry
				while (!shared.isEmailUnique(email)) {
				    sendMessage("Error: Email already exists. Please try again.");
				    sendMessage("Please enter your Email:");
					email = (String)in.readObject();
				}
				
				sendMessage("Email is not taken.");
				
				sendMessage("Please enter your password:");
				password = (String)in.readObject();
				
				sendMessage("Please enter your department name:");
				depName = (String)in.readObject();
				
				sendMessage("Please enter your role:");
				role = (String)in.readObject();
				
				shared.addDetails(name, employeeID, email, password, depName, role);
				
			}
			
			//Login
		else if (message.equalsIgnoreCase("2"))
		{
			boolean loginSuccessful = false;

			while(!loginSuccessful) {
			    sendMessage("Please enter your email:");
				email = (String)in.readObject();
				
				sendMessage("Please enter your password:");
				password = (String)in.readObject();
				
				//Searching for email and password
				String result = shared.searchDetails(email, password);
			
				if (result.equals("-1")) {
		            //Invalid login
		            sendMessage("Invalid email or password. Please try again.");
		        } else {
		        	   
		        	String[] userDetails = result.split("@");
		        	employeeID = Integer.parseInt(userDetails[1]); // Extract employeeID
		            loginSuccessful = true;
		        	System.out.println("Logged in as Employee ID: " + employeeID);
		            sendMessage("Welcome to Health and Safety Reporting");
		        } 
			}		         
		            do {//Menu options
		            	sendMessage("MENU\n1. Create Health and Safety Report\n2. Show all registered accident reports\n3. Assign Health and Safety Report\n4. Review all Health and Safety Reports assigned\n5. Close Report\n6. Update password\n7. Sign out");
						message = (String)in.readObject();
		            	option = Integer.parseInt(message);
						
		            	switch(option) {
		            	
		            	//New health and safety report
		            	case 1:
		            		//Health and safety report information
		            		sendMessage("Report type\n(Type 1 for Options Accident Report, Type 2 for New Health and Safety Risk Report): ");
		            		message = (String)in.readObject();
		    				reportType = Integer.parseInt(message);
		    				
		    				String reportName = "";
		    				
		    				if(reportType == 1) {
		    					reportName = "Accident Report";
		    				}				
		    				else if(reportType == 2) {
		    					reportName = "Health and Safety Risk Report";
		    				} else {
		    			        sendMessage("Invalid report type. Please enter 1 or 2.");
		    			        break;
		    			    }		
		    				
		    				sendMessage("Date: ");
		    				date = (String)in.readObject();
		    				
		    				sendMessage("Employee ID : ");
		    				message = (String)in.readObject();
		    				employeeID = Integer.parseInt(message);
		    				
		    				sendMessage("Status\nEnter 1 for Open");
		    				message = (String)in.readObject();
		    				statusNum = Integer.parseInt(message);
		    				
		    				String status = "";
		    				
		    				status = "Open";
		    				
		    				sendMessage("Assigned employee ID: null");
		    				assignedID = Integer.parseInt(message);
		  	
		    				shared2.addDetails(reportName, 0, date, employeeID, status, assignedID);
		            		break;
		            	
		            	//Retrieve all registered accident reports
		            	case 2:
		            		int length = shared2.getAccidentReportSize();
		    				
							sendMessage(""+length);
							
							for(int i=0;i<length;i++)
							{
								sendMessage(shared2.getAccidentReport(i));
							}
		            		break;
		            	
		            	//Assign health and safety report 
		            	case 3:
		            		sendMessage("Enter Report ID: ");
		            		message = (String)in.readObject();
		    				reportID = Integer.parseInt(message);
		    				
		    				//If report id not found, try again
		    				while (!shared2.doesReportExist(reportID)) {
		    				    sendMessage("Error: Report not found. Please try again");
		    				    sendMessage("Report ID: ");
		    				    message = (String)in.readObject();
			    				reportID = Integer.parseInt(message);
		    				}
		    				
		    				sendMessage("Update Status and Assign ID\n");
		    						    				
		    				//Employee id to be assigned to report
		    				sendMessage("Enter employee ID you would like to assign to report: ");
		    				message = (String)in.readObject();
		    				employeeID = Integer.parseInt(message);
		    				
		    				//If employee id doesnt exist, must retry
		    				while (!shared.doesEmployeeIDExist(employeeID)) {
		    				    sendMessage("Error: Employee ID doesnt not exist. Please try again.");
		    				    sendMessage("Enter employee ID you would like to assign to report: ");
		    					message = (String)in.readObject();
		    					employeeID = Integer.parseInt(message);
		    				}
		    				
		    				//Assigning report and get the result
		    			    String resultAssigned = shared2.assignReport(reportID, employeeID);
		    			    sendMessage(resultAssigned);
		    			    
		    			    shared2.updateReportFile();//updating report
		            		break;
		            	
		            	//View reports assigned to currently logged in user
		            	case 4:
		            		String assignedReports = shared2.assignedReports(employeeID);
		            		sendMessage(assignedReports);
		            		System.out.println("Assigned Reports: "+assignedReports);
		            		break;
		            		
		            	//Closing report
		            	case 5:
		            		sendMessage("Enter Report ID: ");
		            		message = (String)in.readObject();
		    				reportID = Integer.parseInt(message);
		    				
		    				//If report id not found, try again
		    				while (!shared2.doesReportExist(reportID)) {
		    				    sendMessage("Error: Report not found. Please try again");
		    				    sendMessage("Report ID: ");
		    				    message = (String)in.readObject();
			    				reportID = Integer.parseInt(message);
		    				}
		    				
		    				//Assigning report and get the result
		    			    String resultClosed = shared2.closeReport(reportID);
		    			    sendMessage(resultClosed);
		    			    
		    			    shared2.updateReportFile();//updating report
		            		break;
		            	
		            	//Update password
		            	case 6:
		            		sendMessage("Please enter your current password: ");
		            		String currentPassword = (String)in.readObject();
		            		
		            		sendMessage("Please enter new password: ");
		    				String newPassword = (String)in.readObject();
		   
		            		boolean isUpdated = shared.updatePassword(currentPassword, newPassword);
		            		
		            		if (isUpdated == false) {
		    		            //Invalid login
		    		            sendMessage("Incorrect password. Please try again");
		    		        } else {
		            		String message = "Password Updated";
							sendMessage(message);
		            		break;
		    				}
		            	}		            
		            } while(option != 7);		       
		        }		  
			}			
		while(true);//Loop until client exits			
			
		}

		catch (IOException | ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//Closing connection
			try
			{
				in.close();
				out.close();
				myConnection.close();
			}
			catch(IOException ioException)
			{
				ioException.printStackTrace();
			}
		}
		
	}
	
	// Sends a message to the client
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);//Sending the message
			out.flush();//Flushing the output stream
			System.out.println("server>" + msg);//Logging the message on the server
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
}

