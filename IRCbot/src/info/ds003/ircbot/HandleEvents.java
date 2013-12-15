package info.ds003.ircbot;

public class HandleEvents extends Events {
	
	public void handleCtcp(String sender, String receiver, String content)
	{
		if( content.contains("VERSION") )
			actionCenter.sendCtcp(sender, "VERSION", "SparxBot:EarlyAlpha:" + System.getProperty("os.name"), "NOTICE");
	}
	
	public HandleEvents(Main main) {
		super(main);
	}

	@Override
	public void finishedLogin() {
		main.ready();
	}

	@Override
	public void recvPrivMsg(String sender, String content) {		
		if( content.startsWith(prefix) )
			handleCommand(main.getAdmin().equals(sender), content.substring(1), sender);
	}

	@Override
	public void joinedChannel(String channel) {
	}

	@Override
	public void recvChannelMsg(String sender, String channel, String content) {
		if( content.startsWith(prefix) )
			handleCommand(main.getAdmin().equals(sender), content.substring(1), channel);
	}

	@Override
	public void whoisResult(String[] result) {
		
	}

	@Override
	public void recvChannelInvite(String sender, String channel) {
		handleCommand(main.getAdmin().equals(sender), "join " + channel, sender);
	}

	@Override
	public void recvCtcpMsg(String sender, String receiver, String content) {
		handleCtcp(sender, receiver, content);
	}
}
