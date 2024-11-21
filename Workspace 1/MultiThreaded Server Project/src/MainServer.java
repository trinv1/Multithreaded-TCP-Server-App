//Server-side application that listens for client connections and starts threads for each client.

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

	public static void main(String args[])
	{
		ServerSocket provider;
		Socket connection;
		ServerThread handler;
		
		try 
		{
			provider = new ServerSocket(2004,10);
			
			while(true)
			{
				connection = provider.accept();
				handler = new ServerThread(connection);
				handler.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
