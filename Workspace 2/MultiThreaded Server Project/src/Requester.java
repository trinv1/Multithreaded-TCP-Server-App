//This class acts as a client that connects to the server, sends messages, and receives responses.

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Requester{
	Socket requestSocket;
	ObjectOutputStream out;//Used to send messages to the server
 	ObjectInputStream in;//Used to recieve messages from the server
 	String message;
 	Scanner input;
	
 	Requester(){
		
		input = new Scanner(System.in);
	}
	void run()
	{
		try
		{
			//Creating a socket to connect to the server			
			requestSocket = new Socket("127.0.0.1", 2004);
			System.out.println("Connected to localhost in port 2004");

			//Initializing output stream to send messages to server
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			
			//Initializing input stream to receive messages from server
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//Communicating with the server
			do
			{
			
				do
				{
					message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
				}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2")&&!message.equalsIgnoreCase("3"));
			
				//Registration
			    if(message.equalsIgnoreCase("1"))
			    {
			    	//Name
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);		
					
					//Employee ID
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//Email
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
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
			    else if(message.equalsIgnoreCase("2"))
			    {
			    	//Name
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//Password
			    	message = (String)in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);
					
					//Result
					message = (String)in.readObject();
					System.out.println(message);
		

			    }
			    
			    
			    
			    
			    
			    message = (String)in.readObject();
			    System.out.println(message);
			    message = input.nextLine();
			    sendMessage(message);
		}while(message.equalsIgnoreCase("1"));
		
		}
		catch(UnknownHostException unknownHost)
		{
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		Requester client = new Requester();
		client.run();
	}
}
