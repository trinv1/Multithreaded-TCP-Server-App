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
	private int employeeID, status, option, reportID, assignedID, reportType;
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
				
				sendMessage("Please enter your email:");
				email = (String)in.readObject();
				
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
		            //Successful login
		            loginSuccessful = true;
		            sendMessage("Welcome to Health and Safety Reporting");
		            
		            do {//Menu options
		            	sendMessage("MENU\n1. Create Health and Safety Report\n2. Show all registered accident reports\n3. Assign Health and Safety Report\n6. Review all Health and Safety Reports assigned\n7. Update password");
						message = (String)in.readObject();
		            	option = Integer.parseInt(message);
						
		            	switch(option) {
		            	
		            	//New health and safety report
		            	case 1:
		            		//Health and safety report information
		            		sendMessage("Report type\n(Type 1 for Options Accident Report, Type 2 for New Health and Safety Risk Report): ");
		            		message = (String)in.readObject();
		    				reportType = Integer.parseInt(message);
		    				
		    				sendMessage("Date: ");
		    				date = (String)in.readObject();
		    				
		    				sendMessage("Employee ID : ");
		    				message = (String)in.readObject();
		    				employeeID = Integer.parseInt(message);
		    				
		    				sendMessage("Status\n(Enter 1 for 1 for Open, 2 for Assigned or 3 for Closed: ");
		    				message = (String)in.readObject();
		    				status = Integer.parseInt(message);
		    				
		    				sendMessage("Assigned employee ID: ");
		    				message = (String)in.readObject();
		    				assignedID = Integer.parseInt(message);
		    				
		    				shared2.addDetails(reportType, reportID, date, employeeID, status, assignedID);
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
		            		sendMessage("3");
		            		break;
		            	
		            	//View reports assigned to user
		            	case 4:
		            		sendMessage("4");
		            		break;
		            	
		            	//Update password
		            	case 5:
		            		sendMessage("5");
		            		break;
		            		
		            	}
		            
		            
		            } while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2")&&!message.equalsIgnoreCase("3")&& !message.equalsIgnoreCase("4")&&!message.equalsIgnoreCase("5")&&!message.equalsIgnoreCase("6")&&!message.equalsIgnoreCase("7"));
		       
		            
		     
		        }
			}

			 //Prompting the client to continue or terminate
			sendMessage("Enter 1 to repeat");
			message = (String)in.readObject();
			
		}while(message.equalsIgnoreCase("1"));//Loop until client exits
		
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

