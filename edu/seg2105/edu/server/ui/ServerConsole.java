package edu.seg2105.edu.server.ui;

import java.util.Scanner;

import edu.seg2105.client.common.ChatIF;
import edu.seg2105.edu.server.backend.EchoServer;

public class ServerConsole implements ChatIF {
	
	//CLASS VARIABLES
	final public static int DEFAULT_PORT = 5555;
	
	EchoServer server;
	
	Scanner fromConsole;
	
	public ServerConsole(int port) {
		server = new EchoServer(port,this);
		fromConsole = new Scanner(System.in);
	}
	

	@Override
	public void display(String message) {
		System.out.println(message);
	}
	
	//waits for input in the server console and sends it to EchoServer.
	public void accept() {
		try {

	      String message;

	      while (true) {
	        message = fromConsole.nextLine();
	        server.handleMessageFromServerUI(message);
	      }
	    } catch (Exception ex) {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	}
	
	//main method
	public static void main(String[] args) {
		int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
		
	    ServerConsole sv = new ServerConsole(port);
	    
	    try 
	    {
	      sv.server.listen(); //Start listening for connections
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	    
	    sv.accept();

	}

}
