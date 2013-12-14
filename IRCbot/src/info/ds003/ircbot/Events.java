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
		else
			recvPrivMsg(sender, content);
	}
	
}
