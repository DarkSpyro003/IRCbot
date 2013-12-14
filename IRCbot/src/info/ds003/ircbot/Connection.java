package info.ds003.ircbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

// Provided by http://www.hawkee.com/snippet/5656/
public class Connection implements Runnable {
	
	private String server;
	private int port;
	private String nick, user, name;
	private boolean isActive;
	private Events eventHandler;
	private String dataQueue;
	private boolean waitingWhois = false;
	private List<String> whoisBuffer;

	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
	public void setEventHandler(Events eventHandler)
	{
		this.eventHandler = eventHandler;
	}

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
		dataQueue = "";
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
		}
	}
	
	public void sendData(String data)
	{
		dataQueue += data + "\r\n";
	}
	
	private void handleReceivedData(String buffer)
	{
		System.out.println("> " + buffer);
		if (buffer.startsWith("PING")) 
		{
			sendData("PONG " + buffer.substring(5));
		}
		if( buffer.startsWith(":") )
		{
			buffer = buffer.substring(1);
			String [] c = buffer.split(" ");
			String sender = "", type = "", receiver = "", content = "";
			sender = c[0];
			type = c[1];
			receiver = c[2];
			for( int i = 3; i < c.length; ++i )
			{
				content += c[i];
				if( i+1 != c.length )
					content += " ";
			}
			if( content.length() > 1 )
				content = content.substring(1);
			//System.out.printf("%s: %s; %s: %s; %s: %s; %s: %s", "sender", sender, "type", type, "receiver", receiver, "content", content);
		
			if( type != null && !buffer.startsWith("PING") )
			{
				if( type.equals("QUIT") && Info.getNick(sender).equals(nick) )
					isActive = false;
				else if( type.equals("PRIVMSG") )
				{
					if( sender != null && content != null )
						eventHandler.recvMsg(sender, receiver, content);
				}
				else if( type.equals("004") )
				{
					eventHandler.finishedLogin();
				}
				else if( type.equals("JOIN"))
				{
					if( receiver != null )
						eventHandler.joinedChannel(receiver);
				}
				else if( type.equals("311") ) // Start WHOIS
				{
					if( waitingWhois )
						whoisBuffer = new ArrayList<String>();
				}
				else if( type.equals("318") )
				{
					if( waitingWhois )
					{
						waitingWhois = false;
						whoisBuffer.add(buffer);
						int n = whoisBuffer.size();
						String [] resultBuffer = new String[n];
						for( int i = 0; i < n; ++i )
							resultBuffer[i] = whoisBuffer.get(i);
					}
				}
				else if( type.equals("INVITE") )
				{
					
				}
				
				if( waitingWhois )
					whoisBuffer.add(buffer);
			}
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
					handleReceivedData(buffer);
					if(dataQueue.length() > 0)
					{
						out.write(dataQueue);
						out.flush();
						System.out.print("< " + dataQueue);
						dataQueue = "";
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