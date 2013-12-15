package info.ds003.ircbot;

import info.ds003.ircbot.commands.*;

public abstract class Events {
	
	protected Main main;
	protected Actions actionCenter;
	protected HandleEvents events;
	protected String prefix;
	protected Command [] commands;
	
	public abstract void finishedLogin();
	public abstract void recvChannelMsg(String sender, String channel, String content);
	public abstract void recvPrivMsg(String sender, String content);
	public abstract void joinedChannel(String channel);
	public abstract void whoisResult(String [] result);
	public abstract void recvChannelInvite(String sender, String channel);
	public abstract void recvCtcpMsg(String sender, String receiver, String content);

	public Events(Main main)
	{
		this.main = main;
		this.actionCenter = main.getActionCenter();
		this.prefix = main.getPrefix();
		commands = main.getCommandRegistration();
	}
	
	protected void handleCommand(boolean admin, String command, String sender)
	{
		if( command.startsWith(":") )
			command = command.substring(1);

		try
		{
			String [] cmd = command.split(" ");
			String commandName = cmd[0];
			String commandContent = "";
			for( int i = 1; i < cmd.length; ++i )
			{
				commandContent += cmd[i];
				if( i+1 < cmd.length )
					commandContent += " ";
			}
			
			Command run = findCommand(commandName);
			if( run != null )
				run.execute(commandContent, admin);
			else
				actionCenter.sendMessage(sender, "Command not found!");
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			actionCenter.sendMessage(sender, "An error occured executing the command.");
		}
	}
	
	protected Command findCommand(String name)
	{
		for(int i = 0; i < commands.length; ++i)
			if( commands[i].getName().equals(name) )
				return commands[i];
		
		return null;
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
