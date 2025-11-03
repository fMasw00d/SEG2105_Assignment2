// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
    
    
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      
    	if(message.charAt(0)=='#') { //checks if entered message is a command
    		handleCommand(message);
    	} else {
    		sendToServer(message);
    	}
    	
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  //End of provided code ------------------------------------------------------------------------------------------
  
  //Implements corresponding hook method from AbstractClient
  @Override
  protected void connectionException(Exception exception) {
	  clientUI.display("Server has shut down.");
	  quit();
  }
  
  
  //Implements hook method from AbstractClient
  @Override
  protected void connectionClosed() {
	  clientUI.display("Connection closed.");
  }
  
  //handles commands (messages that start with '#')
  protected void handleCommand(String command) throws IOException {
	  if(command.equals("#quit")) {
		  
		  quit();
		  
	  } else if(command.equals("#logoff")) {
		  
		  closeConnection();
		  
	  } else if(command.contains("#sethost")) {
		  
		  if(!isConnected()) {
			  setHost(command.substring(command.indexOf(" ")+1));
		  } else {
			  System.out.println("Error: This method is only usable when logged off. You are currently logged in.");
		  }
		  
	  } else if(command.contains("#setport")) {
		  
		  if(!isConnected()) {
			  setPort(Integer.parseInt(command.substring(command.indexOf(" ")+1)));
		  } else {
			  System.out.println("Error: This method is only usable when logged off. You are currently logged in.");
		  }
		  
	  } else if(command.equals("#login")) {
		  
		  if(!isConnected()) {
			  openConnection();
		  } else {
			  System.out.println("Error: You are already logged in.");
		  }
		  
	  } else if(command.equals("#gethost")) {
		  
		  System.out.println(getHost());
		  
	  } else if(command.equals("#getport")) {
		  
		  System.out.println(getPort());
		  
	  } else {
		  
		  System.out.println("Command does not exist.");
	  }
  }
  
}
//End of ChatClient class
