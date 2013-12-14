package info.ds003.ircbot;

public class HandleEvents extends Events {
	
	public void handleCtcp(String sender, String receiver, String content)
	{
		if( content.contains("VERSION") )
			actionCenter.sendCtcp(sender, "VERSION", "SparxBot:EarlyAlpha:" + System.getProperty("os.name"), "NOTICE");
	}
	
	private void handleCommand(boolean admin, String command)
	{
		if( command.startsWith(":") )
			command = command.substring(1);
		
		if( admin )
			handleAdminCommand(command);
		else
			handleUserCommand(command);
	}

	private void handleAdminCommand(String command)
	{
		try
		{
			if( command.equals("quit") )
				actionCenter.quit("bubais");
			else if( command.startsWith("join "))
			{
				actionCenter.joinChannel(command.split(" ")[1]);
			}
			else if( command.startsWith("part " ))
			{
				actionCenter.partChannel(command.split(" ")[1]);
			}
			else if( command.startsWith("raw ") )
			{
				String toSend = "";
				String [] cmd = command.split(" ");
				for( int i = 1; i < cmd.length; ++i )
				{
					toSend += cmd[i];
					if( i+1 < cmd.length )
						toSend += " ";
				}
				actionCenter.sendRawData(toSend);
			}
		}
		catch(Exception e)
		{}
	}
	
	private void handleUserCommand(String command)
	{
		
	}

	public HandleEvents(Main main, Actions actionCenter, char prefix) {
		super(main, actionCenter, prefix);
	}

	@Override
	public void finishedLogin() {
		main.ready();
	}

	@Override
	public void recvPrivMsg(String sender, String content) {		
		if( content.startsWith("" + prefix) )
			handleCommand(main.getAdmin().equals(sender), content.substring(1));
	}

	@Override
	public void joinedChannel(String channel) {
	}

	@Override
	public void recvChannelMsg(String sender, String channel, String content) {
		if( content.startsWith("" + prefix) )
			handleCommand(main.getAdmin().equals(sender), content.substring(1));
	}

	@Override
	public void whoisResult(String[] result) {
		
	}

	@Override
	public void recvChannelInvite(String sender, String channel) {
		handleCommand(main.getAdmin().equals(sender), "join " + channel);
	}

	@Override
	public void recvCtcpMsg(String sender, String receiver, String content) {
		handleCtcp(sender, receiver, content);
	}
}
