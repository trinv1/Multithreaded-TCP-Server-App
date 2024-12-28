import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket myConnection;
    private ObjectOutputStream out; // Used to send messages to client
    private ObjectInputStream in;   // Used to receive messages from client
    private String message;
    private String email, name, password, depName, role;
    private String date, status;
    private int employeeID, employeeIDCreated, reportType, statusNum, option, reportID, assignedID, assignedIDNull;
    private Users shared;
    private Reports shared2;

	// Constructor to initialize the thread with a client socket
    public ServerThread(Socket s, Users reg, Reports rep) {
        myConnection = s;
        shared = reg;
        shared2 = rep;
    }

    public void run() {
        try {
            //Initialize streams
            out = new ObjectOutputStream(myConnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myConnection.getInputStream());
            
			//Communication Loop.
            do {
                //Menu for Register/Login
                do {
                    sendMessage("1.Register\n2.Login");
                    message = (String) in.readObject();
                } while (!message.equalsIgnoreCase("1") && !message.equalsIgnoreCase("2"));

                // Registration
                if (message.equalsIgnoreCase("1")) {
                    
                	//Name
                	sendMessage("Please enter your name:");
                    name = (String) in.readObject();

                    sendMessage("Please enter your Employee ID:");
                    message = (String) in.readObject();
                    employeeID = Integer.parseInt(message);
                    

    				//If employee id already exists, must retry
    				while (shared.doesEmployeeIDExist(employeeID)) {
    				    sendMessage("Error: Employee ID already exists. Please try again.");
    				    sendMessage("Please enter your Employee ID:");
    					message = (String)in.readObject();
    					employeeID = Integer.parseInt(message);
    				}
    				
    				sendMessage("Employee ID Available");				

    				sendMessage("Please enter your email:");
    				email = (String)in.readObject();
    				
    				//If email already exists, must retry
    				while (!shared.doesEmailExist(email)) {
    				    sendMessage("Error: Email already exists. Please try again.");
    				    sendMessage("Please enter your Email:");
    					email = (String)in.readObject();
    				}
    				
    				sendMessage("Email Available");

                    sendMessage("Please enter your password:");
                    password = (String) in.readObject();

                    sendMessage("Please enter your department name:");
                    depName = (String) in.readObject();

                    sendMessage("Please enter your role:");
                    role = (String) in.readObject();

                    shared.addDetails(name, employeeID, email, password, depName, role);
                }

                // Login
                else if (message.equalsIgnoreCase("2")) {
                    boolean loginSuccessful = false;

                    //Loop to see if login is successful
                    while (!loginSuccessful) {
                        sendMessage("Please enter your email:");
                        email = (String) in.readObject();

                        sendMessage("Please enter your password:");
                        password = (String) in.readObject();

                        //Validating email and password
                        String result = shared.searchDetails(email, password);                      
                        if (result.equals("-1")) {
                            sendMessage("Invalid email or password. Please try again.");
                        } 
                        
                        else {
                        	//Extracting employee id for getting reports assigned to user later on
                            String[] userDetails = result.split("@");
                            employeeID = Integer.parseInt(userDetails[1]);
                            loginSuccessful = true;
                            sendMessage("Welcome to Health and Safety Reporting");
                        }
                    }

                    // Menu Options
                    do {
                        sendMessage("MENU\n1. Create Health and Safety Report\n2. Show all registered accident reports\n3. Assign Health and Safety Report\n4. Update status\n5. Review all Health and Safety Reports assigned\n6. Update password\n7. Sign out");
                        message = (String) in.readObject();
                        option = Integer.parseInt(message);

                        //Create health and safety report
                        if (option == 1) {
                            sendMessage("Report type (Type 1 for Accident Report, Type 2 for Health and Safety Risk Report): ");
                            message = (String) in.readObject();
                            reportType = Integer.parseInt(message);

                            String reportName = "";
		    				
		    				if(reportType == 1) {
		    					reportName = "Accident Report";
		    				}				
		    				else if(reportType == 2) {
		    					reportName = "Health and Safety Risk Report";
		    				} else {
		    			        sendMessage("Invalid report type. Please enter 1 or 2.");
		    				}
                            sendMessage("Date: ");
                            date = (String)in.readObject();
                            
                            sendMessage("Employee ID: " + employeeID);
                            employeeIDCreated = Integer.parseInt(message);
                            
                            sendMessage("No Status Assigned");
		    				statusNum = Integer.parseInt(message);

                            sendMessage("No Employee Assigned");
                            assignedIDNull = Integer.parseInt(message);
                            
                            shared2.addDetails(reportName, reportID, date, employeeID, status, assignedID);

                        }
                        
                        //getting all accident reports
                        else if (option == 2) {
                        	sendMessage(shared2.getAllAccidentReports());
                        }

                        
                        //Assign report to employee
                        else if (option == 3) {
                        	boolean matchSuccessful = false;

                            while (!matchSuccessful) {
                                //Report ID
                                sendMessage("Enter Report ID: ");
                                message = (String) in.readObject();
                                reportID = Integer.parseInt(message);

                                //Employee ID
                                sendMessage("Please enter Employee ID:");
                                message = (String) in.readObject();
                                employeeID = Integer.parseInt(message);

                                //Validating Report ID and Employee ID
                                boolean reportExists = shared2.doesReportExist(reportID);
                                boolean employeeExists = shared.doesEmployeeIDExist(employeeID);
                                
                                //If either dont exist
                                if (!reportExists || !employeeExists) {
                                    sendMessage("Invalid Report or Employee ID. Please try again.");
                                } 
                                //If ids do exist
                                else {
                                    sendMessage(shared2.assignReport(reportID, employeeID));                 
                                    matchSuccessful = true; //Exit the loop
                    				shared2.updateReportFile();
                                }
                            }
                        }
                        
                        //Upddating status
                        else if(option == 4) {  
                    	    boolean matchSuccessful = false;

                            //Report ID
                            sendMessage("Enter Report ID: ");
                            message = (String) in.readObject();
                            reportID = Integer.parseInt(message);
                            System.out.println("Server Received: " + message);


                            //Status
                            sendMessage("Please enter 1 to Open\nPlease enter 2 to Close ID:");
                            message = (String) in.readObject();
                            statusNum = Integer.parseInt(message);

                            //Validating Report ID 
                            boolean reportExists = shared2.doesReportExist(reportID);
                            
                            //If report exists and number is valid
                            if (reportExists) {
                                if (statusNum == 1) {
                                    sendMessage(shared2.openReport(reportID));
                                    matchSuccessful = true;
                                    shared2.updateReportFile();
                                } else if (statusNum == 2) {
                                    sendMessage(shared2.closeReport(reportID));
                                    matchSuccessful = true;
                                    shared2.updateReportFile();
                                } else {
                                    sendMessage("Invalid ID or status. Please try again");
                                }
                            } else {
                                sendMessage("Invalid Report ID. Please try again.");
                            }   
                        }
                        
                        //Assigned reports to currently logged in user
                        else if (option == 5) {
                            sendMessage(shared2.assignedReports(employeeID)); // Send all reports in a single string

                        }
                        
                        //Update password
                        else if(option == 6) {

                        	    //current password
                        	    sendMessage("Enter current password: ");
                        	    String password = (String) in.readObject();

                        	    //new password
                        	    sendMessage("Please enter new password: ");
                        	    String newPassword = (String) in.readObject();
                        	    
                        	    //Update password
                        	    shared.updatePassword(password, newPassword);                        	    
                            
                        }

                   } while (option != 7);//Log out on option 7
                }

            } while (true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            
        	//Closing connection
        	try {
                in.close();
                out.close();
                myConnection.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    
    //Sends message to client
    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
