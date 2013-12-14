package info.ds003.ircbot;

public class Actions {
	
	private Connection network;

	public Actions(Connection network)
	{
		this.network = network;
	}
	
	public void sendMessage(String receiver, String content)
	{
		network.sendData("PRIVMSG " + Info.getNick(receiver) + " :" + content);
	}
	
	public void joinChannel(String channel)
	{
		System.out.println("Joining " + channel);
		network.sendData("JOIN " + channel);
	}
	
	public void quit()
	{
		quit("Leaving.");
	}
	
	public void quit(String message)
	{
		network.sendData("QUIT :" + message);
	}
}
