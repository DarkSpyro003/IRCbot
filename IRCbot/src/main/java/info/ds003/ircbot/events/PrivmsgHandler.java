package info.ds003.ircbot.events;

import info.ds003.ircbot.Main;
import info.ds003.ircbot.commands.CommandHandler;

public class PrivmsgHandler extends Event {

	private String prefix;
	private CommandHandler commandHandler;
	
	public PrivmsgHandler(Main main) {
		super(main);
		this.prefix = main.getPrefix();
		commandHandler = main.getCommandHandler();
	}
	
	private void recvCtcpMsg(String sender, String receiver, String content) {
		handleCtcp(sender, receiver, content);
	}

	private void recvPrivMsg(String sender, String content) {		
		if( content.startsWith(prefix) )
			commandHandler.handleCommand(main.getAdmin().equals(sender), content.substring(1), sender);
	}
	
	private void handleCtcp(String sender, String receiver, String content)
	{
		if( content.contains("VERSION") )
			actionCenter.sendCtcp(sender, "VERSION", "SparxBot:EarlyAlpha:" + System.getProperty("os.name"), "NOTICE");
	}
	
	private void recvChannelMsg(String sender, String channel, String content) {
		if( content.startsWith(prefix) )
			commandHandler.handleCommand(main.getAdmin().equals(sender), content.substring(1), channel);
	}

	@Override
	public void handleEvent(String sender, String type, String receiver, String content) 
	{
		if( receiver.startsWith("#") )
			recvChannelMsg(sender, receiver, content);
		else if( content.startsWith("\001") )
			recvCtcpMsg(sender, receiver, content);
		else
			recvPrivMsg(sender, content);
	}

}
