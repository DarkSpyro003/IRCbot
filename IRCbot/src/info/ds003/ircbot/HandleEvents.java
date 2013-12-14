package info.ds003.ircbot;

public class HandleEvents extends Events {
	
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
		if( command.equals("quit") )
			actionCenter.quit("bubais");
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
		actionCenter.sendMessage(channel, "Hello everyone! I succesfully joined " + channel);
	}

	@Override
	public void recvChannelMsg(String sender, String channel, String content) {
		if( content.startsWith("" + prefix) )
			handleCommand(main.getAdmin().equals(sender), content.substring(1));
	}
}
