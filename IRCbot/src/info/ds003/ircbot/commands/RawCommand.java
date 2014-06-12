package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;

public class RawCommand extends Command {

	public RawCommand(String name, boolean requiresAdmin, Main main) 
	{
		super(name, requiresAdmin, main);
	}
	
	public RawCommand(String name, String description, boolean requiresAdmin, Main main)
	{
		super(name, description, requiresAdmin, main);
	}

	@Override
	protected void runCommand(String content) 
	{
		actionCenter.sendRawData(content);
	}

}
