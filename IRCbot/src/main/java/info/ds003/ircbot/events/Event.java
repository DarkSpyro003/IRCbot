package info.ds003.ircbot.events;

import info.ds003.ircbot.*;

public abstract class Event {

	protected Main main;
	protected Actions actionCenter;
	
	public Event(Main main)
	{
		this.main = main;
		this.actionCenter = main.getActionCenter();
	}
	
	public abstract void handleEvent(String sender, String type, String receiver, String content);
}
