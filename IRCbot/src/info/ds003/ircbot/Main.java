package info.ds003.ircbot;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {
	
	private Actions actionCenter;
	private Connection network;
	private Events eventHandler;
	private String admin = "Sparxyxxx!Sparxyxxx@2001:41d0:2:a98c::1";
	private char prefix = '!';

	public static void main(String args[]) 
	{
		new Main();
	}
	
	public String getAdmin()
	{
		return admin;
	}
	
	public Main()
	{
		System.out.println("Starting bot.");
		
		network = new Connection("irc.esper.net", 6667, "SparxBot", "Sparxy", "Sparxy");
		actionCenter = new Actions(network);
		eventHandler = new HandleEvents(this, actionCenter, prefix);
		network.setEventHandler(eventHandler);
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
		actionCenter.sendCtcp("Sparxyxxx", "VERSION", "");
	}
}
