/************************************************************************
 * NAME: VYSALI PUGHAZHENDI
 * STUDENT ID: 
 * COURSE # : CSE 4344/5344
 * COURSE NAME: 
 * PROJECT 1: Building a Simple Web Client and a Multithreaded Web Server
 *************************************************************************/
import java.net.*;
import java.io.*;

public class WebClient 
{
	/**************************************************
	 * Main Class
	 *************************************************/
	public static void main(String[] args) 
	{
		try 
		{
			// Object Creation for Socket class
			// Mention the port #
			Socket socket = new Socket("localhost", 8080);
			System.out.println("Server Connection - Success");
			
			//Object Creation for PrintStream class.
			PrintStream printStream = new PrintStream(socket.getOutputStream());
			
			//Prints the below lines after the client is connected to the server.
			printStream.println("GET index.html HTTP/1.1\r\n\r\n");
			printStream.println("Hello World");
			
			//Flushes the stream.
			printStream.flush();

			//Closing the connection and socket.
			printStream.close();
			socket.close();
			System.out.println("Connection/Socket Closed");
		}
		// Exception Handling Block
		catch (Exception ex) 
		{
			System.out.println(ex);
		}
	}
}
