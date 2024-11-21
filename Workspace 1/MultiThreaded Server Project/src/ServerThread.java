//Class handles communication between server and single client

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket myConnection;
	private ObjectOutputStream out;//Used to send messages to client
	private ObjectInputStream in;//Used to receive messages from client
	private String message;
	
	// Constructor to initialize the thread with a client socket
	public ServerThread(Socket s)
	{
		myConnection = s;// Stores the client connection socket
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
				sendMessage("MENU\n1.Register\n2. Login");//Sending a message to the client
				message = (String)in.readObject();//Reads the client's response
			}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2")&&!message.equalsIgnoreCase("3"));
		
			if(message.equalsIgnoreCase("1"))
			{
				sendMessage("Please enter your name:");
				message = (String)in.readObject();
				
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

