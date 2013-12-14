package info.ds003.ircbot;

public class Actions {
	
	private Connection network;

	public Actions(Connection network)
	{
		this.network = network;
	}
	
	public void sendRawData(String data)
	{
		network.sendData(data);
	}
	
	public void sendMessage(String receiver, String content)
	{
		network.sendData("PRIVMSG " + Info.getNick(receiver) + " :" + content);
	}
	
	public void sendAction(String receiver, String content)
	{
		network.sendData("PRIVMSG " + Info.getNick(receiver) + " :" + "\001" + "ACTION " + content + "\001");
	}
	
	public void joinChannel(String channel)
	{
		network.sendData("JOIN " + channel);
	}
	
	public void partChannel(String channel)
	{
		network.sendData("PART " + channel);
	}
	
	public void whois(String name)
	{
		network.sendData("WHOIS " + name);
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
