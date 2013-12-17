package info.ds003.ircbot;

import info.ds003.ircbot.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {
	
	private Actions actionCenter;
	private Connection network;
	private Events eventHandler;
	private String admin = "Sparxyxxx!Sparxyxxx@2001:41d0:2:a98c::1";
	private String prefix = "!";
	private List<Command> commands = new ArrayList<Command>();
	private CommandHandler commandHandler;
	
	public Command [] getCommandRegistration()
	{
		int n = commands.size();
		Command [] command = new Command[n];
		for( int i = 0; i < n; ++i )
			command[i] = commands.get(i);
		
		return command;
	}
	
	public void registerCommands()
	{
		registerCommand(new JoinCommand("join", true, this));
		registerCommand(new PartCommand("part", true, this));
		registerCommand(new QuitCommand("quit", true, this));
		registerCommand(new RawCommand("raw", true, this));
	}
	
	public void registerCommand(Command command)
	{
		commands.add(command);
	}

	public static void main(String args[]) 
	{
		new Main("irc.esper.net", 6667, "SparxBot", "Sparxy", "Sparxy");
	}
	
	public CommandHandler getCommandHandler()
	{
		return commandHandler;
	}
	
	public String getPrefix()
	{
		return prefix;
	}
	
	public String getAdmin()
	{
		return admin;
	}
	
	public Connection getNetwork()
	{
		return network;
	}
	
	public Events getEventHandler()
	{
		return eventHandler;
	}
	
	public Actions getActionCenter()
	{
		return actionCenter;
	}
	
	public Main(String server, int port, String nick, String user, String realname)
	{
		System.out.println("Starting bot.");
		network = new Connection(server, port, nick, user, realname);
		actionCenter = new Actions(this);
		registerCommands();
		eventHandler = new HandleEvents(this);
		network.setEventHandler(eventHandler);
		commandHandler = new CommandHandler(this);
		ExecutorService threadExec = Executors.newCachedThreadPool();
		
		try
		{
			network.start();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		threadExec.execute(network);
		threadExec.shutdown();
	}
	
	public void ready()
	{
		actionCenter.joinChannel("#Sparxy");
	}
}
