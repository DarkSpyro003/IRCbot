package info.ds003.ircbot.commands;

import info.ds003.ircbot.Actions;
import info.ds003.ircbot.Main;

public abstract class Command {

	protected String name;
	protected String description;
	protected boolean requiresAdmin;
	protected Actions actionCenter;
	
	public Command(String name, boolean requiresAdmin, Main main)
	{
		this(name, "", requiresAdmin, main);
	}
	
	public Command(String name, String description, boolean requiresAdmin, Main main)
	{
		this.name = name;
		this.description = description;
		this.requiresAdmin = requiresAdmin;
		actionCenter = main.getActionCenter();
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void execute(String content, boolean isAdmin)
	{
		if( requiresAdmin )
		{
			if( isAdmin )
				runCommand(content);
		}
		else
			runCommand(content);
	}
	
	protected abstract void runCommand(String content);
}
