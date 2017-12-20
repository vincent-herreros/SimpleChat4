import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.EchoServer;

import common.ChatIF;


public class ServerConsole implements ChatIF {

	EchoServer server;
	
	ServerConsole(int port)
	{
		try 
		{
			server = new EchoServer(port, this);
			accept();
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
	
	@Override
	public void display(String message) {
		System.out.println(message);
	}

	public void accept() 
	{
		try
		{
			BufferedReader fromConsole = 
					new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) 
			{
				message = fromConsole.readLine();
				server.handleMessageFromServerUI(message);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println
			("Unexpected error while reading from console!");
		}
	}
	
	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int port = 0; //Port to listen on

		try
		{
			port = Integer.parseInt(args[0]); //Get port from command line
		}
		catch(Throwable t)
		{
			port = EchoServer.DEFAULT_PORT; //Set port to 5555
		}

		ServerConsole sv = new ServerConsole(port);
	}

}
