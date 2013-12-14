package info.ds003.ircbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

// Provided by http://www.hawkee.com/snippet/5656/
public class Connection implements Runnable {
	
	private String server;
	private int port;
	private String nick, user, name;
	private boolean isActive;

	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;

	protected void server(String server) 
	{
		this.server = server;
	}

	protected String server() 
	{
		return this.server;
	}

	protected void port(int port) 
	{
		this.port = port;
	}

	protected int port() 
	{
		return this.port;
	}
	
	protected void nick(String nick) 
	{
		this.nick = nick;
	}

	protected String nick() 
	{
		return this.nick;
	}

	protected void user(String user) 
	{
		this.user = user;
	}

	protected String user() 
	{
		return this.user;
	}

	protected void name(String name) 
	{
		this.name = name;
	}

	protected String name() 
	{
		return this.name;
	}

	protected void isActive(boolean bool) 
	{
		this.isActive = bool;
	}

	protected boolean isActive() 
	{
		return this.isActive;
	}

	public Connection(String server, int port, String nick, String user, String realname) 
	{
		System.out.println("Initializing.");
		this.server = server;
		this.port = port;
		this.nick = nick;
		this.user = user;
		this.name = realname;
	}

	protected void start() throws java.io.IOException 
	{
		this.socket = new Socket(this.server(), this.port());
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		if (socket.isConnected()) 
		{
			out.write("NICK " + this.nick() + "\r\n");
			out.write("USER " + this.user() + " \"\" \"\" :" + this.name() + "\r\n");
			out.flush();
			this.isActive(true);
			System.out.println("Starting thread.");
			new Thread(this).start();
		}
	}

	public void run() 
	{
		String buffer;
		while (this.isActive()) 
		{
			try 
			{
				while ((buffer = in.readLine()) != null) 
				{
					if (buffer.startsWith("PING")) 
					{
						out.write("PONG " + buffer.substring(5) + "\r\n");
						out.flush();
					}
				}
			} 
			catch (java.io.IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}