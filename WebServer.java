/************************************************************************
 * NAME: VYSALI PUGHAZHENDI
 * STUDENT ID: 
 * COURSE # : CSE 4344/5344
 * COURSE NAME: 
 * PROJECT 1: Building a Simple Web Client and a Multithreaded Web Server
 *************************************************************************/
import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer 
{

	static Socket clientSocketObj = null;
	static ServerSocket serverSocketObj = null;
	static int port = 80;

	/**************************************************
	 * Main Class
	 *************************************************/
	public static void main(String args[]) 
	{
		try 
		{
			serverSocketObj = new ServerSocket(port);
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}

		while (true) 
		{
			try 
			{
				// Server accepts the client connection.
				clientSocketObj = serverSocketObj.accept();
				System.out.println("Accepted Client Connection");

				while (true) 
				{
					Date date = new Date();
					String response = "Response Successful HTTP/1.1 200 OK \r\n\r\n" + date;
					clientSocketObj.getOutputStream().write(response.getBytes("UTF-8"));

					// Request Message
					// Object creation for HTTPRequest
					HttpRequest httpRequest = new HttpRequest(clientSocketObj);

					Thread thread = new Thread(httpRequest);
					thread.start();

					// Thread sleeps for 10000 millisecs. - to retain the connection active for a few more mins.
					thread.sleep(10000);
				}
			} 
			//Exception Handling
			catch (Exception ex) 
			{
				System.out.println(ex);
			}
		}

	}
}
