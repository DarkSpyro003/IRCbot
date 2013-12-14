package info.ds003.ircbot;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {

	public static void main(String args[]) 
	{
		System.out.println("Starting bot.");
		
		Connection network = new Connection("irc.esper.net", 6667, "SparxBot", "Sparxy", "Sparxy");
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
}
