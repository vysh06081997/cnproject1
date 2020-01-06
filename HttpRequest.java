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

public class HttpRequest implements Runnable {
	
	Socket socketPort;

	// Constructor
	public HttpRequest(Socket s) throws Exception 
	{
		System.out.println("socket #:" + s);
		this.socketPort = s;
	}

	public void run() 
	{
		try 
		{	//Calling Process Request Method
			processRequest();
		} 
		//Exception Handling block 
		catch (Exception e) 
		{
			//Logs the exceptions
			System.out.println(e);
		}
	}
	
	/**************************************************
	 * METHOD NAME: processRequest()
	 * Throws: Exception base class
	 * DESCRIPTION: This method gets the input request 
	 * and reads the input file. This method reads and 
	 * writes the content of the input request file.
	 *************************************************/
	public void processRequest() throws Exception 
	{
		//OBJECT CREATION
		//Object creation for InputStream class
		InputStream inputStream = socketPort.getInputStream();
		
		//Object Creation for DataOutputStream class
		DataOutputStream dataOutputStream = new DataOutputStream(socketPort.getOutputStream());

		//Object creation for BufferedReader class
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		//Reads the inputs Request.
		String httpReqMessage = bufferedReader.readLine();
		System.out.println();
		System.out.println("REQUEST Message :" + httpReqMessage);
		
		String fileRead = bufferedReader.readLine();		
		while (fileRead != null && fileRead.length() != 0) 
		{
			System.out.println(fileRead);
			fileRead = bufferedReader.readLine();
		}
		
		StringTokenizer tokens = new StringTokenizer(httpReqMessage);
		tokens.nextToken();
		String fileName = tokens.nextToken();

		fileName = "." + fileName;

		//Input File - Read & Write.
		FileInputStream fileInputStream = null;
		boolean IsFileFound = true;

		try 
		{
			fileInputStream = new FileInputStream(fileName);
		} 
		//Exception Handling block
		catch (Exception ex) 
		{
			IsFileFound = false;
			System.out.println(ex);
		}

		// Response Message
		String message = null;
		String messageContent = null;
		String messageBody = null;

		if (IsFileFound) 
		{
			message = "File is found - 200 Success";
			messageContent = "Content-type: " + messageContentType(fileName) + "\r\n";
		} 
		else 
		{
			message = "HTTP/1.1 404 Not Found";
			messageContent = "File Not Found or does not exists";
			messageBody = "<HTML>" + "<HEAD><TITLE>File Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
		}

		if (IsFileFound) 
		{
			//Calling File reading method
			fileRead(fileInputStream, dataOutputStream);
			fileInputStream.close();
		} 
		else 
		{
			dataOutputStream.writeBytes(messageBody);
		}
		dataOutputStream.writeBytes("\r\n");

		//Closing the connection and socket.
		dataOutputStream.close();
		bufferedReader.close();
		socketPort.close();
		System.out.println("Connection/Socket Closed");

	}
	
	/**************************************************
	 * METHOD NAME: messageContentType()
	 * Parameters: filename
	 * DESCRIPTION: This method writes message in the input file.
	 *************************************************/
	public String messageContentType(String fileName) 
	{
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) 
		{
			return "text/html";
		}

		return "application/octet-stream";
	}

	/**************************************************
	 * METHOD NAME: fileRead()
	 * Throws: Exception base class
	 * Parameters: InputFileStream, Output
	 * DESCRIPTION: This method reads the input file.
	 *************************************************/
	public void fileRead(FileInputStream fileInputStream, OutputStream output) 
			throws Exception 
	{
		
		int i = 0;
		//object creation.
		byte[] bytes = new byte[1024];
		
		while ((i = fileInputStream.read(bytes)) != -1) 
		{
			//writes the input file content.
			output.write(bytes, 0, i);
		}
	}



}
