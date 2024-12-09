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
	private int employeeID, option;
	private RegistrationDetails shared;
	
	// Constructor to initialize the thread with a client socket
	public ServerThread(Socket s, RegistrationDetails reg)
	{
		myConnection = s;// Stores the client connection socket
		shared = reg;
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
		            
		            do {
		            	sendMessage("MENU\n1. Create Health and Safety Report\n2. Show all registered accident reports\n3. Assign Health and Safety Report\n6. Review all Health and Safety Reports assigned\n7. Update password");
						message = (String)in.readObject();
		            	option = Integer.parseInt(message);
						
						if(message.equalsIgnoreCase("1")) {
							sendMessage("Hello");
						}
		            
		            
		            } while(!message.equals("-1"));
		     
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

