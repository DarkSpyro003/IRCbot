package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;
import info.ds003.ircbot.Actions;

public class CommandHandler {

	private Command [] commands;
	private Actions actionCenter;
	
	public CommandHandler(Main main)
	{
		actionCenter = main.getActionCenter();
	}

	public void handleCommand(boolean admin, String command, String sender)
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
	
	private Command findCommand(String name)
	{
		for(int i = 0; i < commands.length; ++i)
			if( commands[i].getName().equals(name) )
				return commands[i];
		
		return null;
	}
}
