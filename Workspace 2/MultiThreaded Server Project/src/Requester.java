import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    Scanner input;
    int result;

    Requester() {
        input = new Scanner(System.in);
    }

    void run() {
        try {
        	//Creating a socket to connect to server
            requestSocket = new Socket("127.0.0.1", 2004);
            
            //Initializing output stream to send messages to server
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            
            //Initialize input stream to reseive messages from server
            in = new ObjectInputStream(requestSocket.getInputStream());

            //Communicating with server
            do {
                message = (String) in.readObject();
                System.out.println(message);
                message = input.nextLine();
                sendMessage(message);

                //Registration
                if (message.equalsIgnoreCase("1")) {
                    
                	//Name
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);		
					
					//Employee ID
					do {
					    message = (String) in.readObject();
					    System.out.println(message);

					    message = input.nextLine();
					    sendMessage(message);

					    message = (String) in.readObject();
					    System.out.println(message);

					} while (message.contains("Error: Employee ID already exists. Please try again."));
					
					//Email
					do {
					    message = (String) in.readObject();
					    System.out.println(message);

					    message = input.nextLine();
					    sendMessage(message);

					    message = (String) in.readObject();
					    System.out.println(message);

					} while (message.contains("Error: Email already exists. Please try again."));
					
					//Password
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//Department name
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//Role
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);

                } 
                
                //Login
                else if (message.equalsIgnoreCase("2")) {
                    boolean loginSuccessful = false;

                    //Loop to see if login is successful
                    while (!loginSuccessful) {
                        for (int i = 0; i < 2; i++) {
                            
                        	//Email and Password
                        	message = (String) in.readObject();
                            System.out.println(message);
                            sendMessage(input.nextLine());
                        }

                        message = (String) in.readObject();
                        System.out.println(message);
                        loginSuccessful = message.contains("Welcome to Health and Safety Reporting");
                    }

                    //Declaring option outside the loop to avoid scope errors
                    String option = "";

                    do {
                    	//Menu options
                        message = (String) in.readObject();
                        System.out.println(message);
                        option = input.nextLine();
                        sendMessage(option);
                        
                        //Create health and safety report
                        if (option.equals("1")) {
                        	
                        	//Report Type 
                        	message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message);
                            
                            //Date
							message = (String)in.readObject();
							System.out.println(message);
							message = input.nextLine();
							sendMessage(message);
							
							//Employee ID
							message = (String)in.readObject();
							System.out.println(message);
							
							//Status
							message = (String)in.readObject();
							System.out.println(message);
							
							//Assigned ID
							message = (String)in.readObject();
							System.out.println(message);
                        }
                        //Getting all accident reports
                        else if (option.equals("2")) {
								
                        	//Accident reports
                        	    message = (String)in.readObject();
								System.out.println(message);
                        }

                        //Assigning id to report
                        else if (option.equals("3")) {
                        	boolean matchSuccessful = false;

                            while (!matchSuccessful) {
                                // Enter Report ID
                                message = (String) in.readObject();
                                System.out.println(message);
                                message = input.nextLine();
                                sendMessage(message);

                                // Enter Employee ID
                                message = (String) in.readObject();
                                System.out.println(message);
                                message = input.nextLine();
                                sendMessage(message);

                                // Response from server
                                message = (String) in.readObject();
                                System.out.println(message);

                                if (message.contains("Report Assigned")) {
                                    matchSuccessful = true;
                                }
                            }  
                        }
						   			
                        //Updating status
                        else if (option.equals("4")) {
                            boolean matchSuccessful = false;

                            while (!matchSuccessful) {
                                //Report ID
                                message = (String) in.readObject();
                                System.out.println(message);
                                message = input.nextLine();
                                sendMessage(message);

                                //Status
                                message = (String) in.readObject();
                                System.out.println(message);
                                message = input.nextLine();
                                sendMessage(message);

                                //Response
                                message = (String) in.readObject();
                                System.out.println(message);

                                //Exit loop on success
                                if (message.contains("Closed Report") || message.contains("Open Report")) {
                                    matchSuccessful = true;
                                }
                            }
                        }
   
                        //Assigned reports to currently logged in user
                        else if (option.equals("5")) {
                          message = (String) in.readObject();
                          System.out.println(message);
                            }
                        
                        //Update password
                        else if(option.equals(6)) {

                        	boolean matchSuccessful = false;

                        	while (!matchSuccessful) {

                                //Enter Current Password
                                message = (String) in.readObject();
                                System.out.println(message);
                                message = input.nextLine();
                                sendMessage(message);

                                //Enter new password
                                message = (String) in.readObject();
                                System.out.println(message);
                                message = input.nextLine();
                                sendMessage(message);

                        	
                                // Exit loop on success
                                if (message.contains("Password Updated Successfully")) {
                                	matchSuccessful = true;
                                }
                        	}                    
                        }

                    } while (!option.equals("7")); //Log out on option 7

                }
            } while (true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Requester client = new Requester();
        client.run();
    }
}
