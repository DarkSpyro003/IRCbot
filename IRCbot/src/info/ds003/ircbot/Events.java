package info.ds003.ircbot;

public abstract class Events {
	
	protected Main main;
	protected Actions actionCenter;
	protected HandleEvents events;
	protected char prefix;
	
	public abstract void finishedLogin();
	public abstract void recvChannelMsg(String sender, String channel, String content);
	public abstract void recvPrivMsg(String sender, String content);
	public abstract void joinedChannel(String channel);
	public abstract void whoisResult(String [] result);
	public abstract void recvChannelInvite(String sender, String channel);
	public abstract void recvCtcpMsg(String sender, String receiver, String content);

	public Events(Main main, Actions actionCenter, char prefix)
	{
		this.main = main;
		this.actionCenter = actionCenter;
		this.prefix = prefix;
	}
	
	public void recvMsg(String sender, String receiver, String content)
	{
		if( receiver.startsWith("#") )
			recvChannelMsg(sender, receiver, content);
		else if( content.startsWith("\001") )
			recvCtcpMsg(sender, receiver, content);
		else
			recvPrivMsg(sender, content);
	}
	
}
