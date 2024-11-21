//Server-side application that listens for client connections and starts threads for each client.

import java.io.*;
import java.net.*;
public class Provider{
	
	
	
	public static void main(String args[])
	{	
		ServerSocket providerSocket;//Listens for incoming client connections on port 2004
		Socket connection = null;
		ServerThread handler;
		
		//Infinite loop, The server continues listening for new clients indefinitely
		try
		{
			//Listens for incoming connections on port 2004 with a backlog of 10
			providerSocket = new ServerSocket(2004, 10);
		
			while(true)
			{
			
				System.out.println("Waiting for connection");
				connection = providerSocket.accept();//Accepts a client connection
				System.out.println("Connection received from " + connection.getInetAddress().getHostName());
				
				handler = new ServerThread(connection);//Spawns a new thread (ServerThread) to handle connection
				handler.start();//Start the thread
			}
		}
		
		catch(Exception e)
		{
			
		}
	}
}
