//Class to manage a collection of details

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.IOException;

public class Users {
	
	private LinkedList<UserDetails> list;//Linked list storing objects
	
	//Constructor to initialize and populate from list
	public Users()
	{
		list = new LinkedList<UserDetails>();//Initializing linked list
		String fileContents;//To store a line read from file
		String[] results = new String[6];
		UserDetails temp;
		
		//Populating RegDetails
		try 
		{
			//Opening file for reading
			FileReader fr = new FileReader(new File("Details.txt"));
			BufferedReader br = new BufferedReader(fr);
			
			//Reading file line by line
			while((fileContents = br.readLine())!=null)
			{
				//Splitting each line into parts
				String[] resultPart = fileContents.split("@");
				
				//Creating new Details object from parsed data
				temp = new UserDetails(resultPart[0], Integer.parseInt(resultPart[1]), resultPart[2], resultPart[3], resultPart[4], resultPart[5]);
				list.add(temp);//Adding to list
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
	
	//Checking if employee id exists
	public boolean doesEmployeeIDExist(int employeeID) {
		for (UserDetails user : list) {
		   if (user.getEmployeeID() == employeeID) {
		         return true; 
		   }
		}
		    return false; //Employee id doesnt exist
	}
	
	//Is email unique
	public boolean doesEmailExist(String email) {
	for (UserDetails user : list) {
			if (user.getEmail().equalsIgnoreCase(email)) { 
				     return false; 
			}
		}
				 return true;//Email is unique
	}
	
	//Adding new Details object to list and update file
	public synchronized void addDetails(String name, int employeeID, String email, String password, String depName, String role)
	{
		//Creating new Details object from parameters
		UserDetails temp = new UserDetails(name, employeeID, email, password, depName, role);
		
		list.add(temp);//Add new details to list
		
		//Updating the file storage with new list
		try 
		{
		FileWriter fw = new FileWriter(new File("Details.txt"));
		
		Iterator i = list.iterator();//Iterating over list
		
		
		while(i.hasNext())
		{
			temp = (UserDetails)i.next();
			fw.write(temp.toString()+"\n");//Writing each detail to file
			
			System.out.println("Writing "+temp.toString());
		}
		
		fw.close();//Closing file writer
		} 
		
		catch (IOException e) 
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	
	}
	
	//Getting list size
	public synchronized int getLength()
	{
		return list.size();
	}
	
	//Storing located details to string
	public synchronized String getItem(int location)
	{
		UserDetails temp = list.get(location);
		
		return temp.toString();
	}
	
	//Searching for user details
	public synchronized String searchDetails(String email, String password)
	{
		String result="-1";
		Iterator i = list.iterator();
		UserDetails temp;
		
		while(i.hasNext())
		{
			temp = (UserDetails)i.next();
			
			if(temp.getEmail().equalsIgnoreCase(email) && temp.getPassword().equalsIgnoreCase(password))
			{
				result = temp.toString();
				break;
			}
		}
		
		return result;
	
	}
	// Update Password
	public synchronized String updatePassword(String password, String newPassword) {
	    boolean passwordUpdated = false; //Flag to check if password was updated

	    //Iterating over users
	    for (UserDetails user : list) {
	        if (user.getPassword().equals(password)) { 
	            user.setPassword(newPassword);    
	            passwordUpdated = true;
	            break; 
	        }
	    }

	    if (passwordUpdated) {
	        //Updating the file with the new password for all users
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Details.txt"))) {
	            for (UserDetails u : list) {
	                writer.write(u.toString().trim());
	                writer.newLine();
	            }
	            return "Password Updated Successfully";
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Error updating the password.";
	        }
	    } else {
	        return "Incorrect Password";
	    }
	}

	}

