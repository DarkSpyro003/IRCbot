package info.ds003.ircbot.events;

import java.util.ArrayList;

import info.ds003.ircbot.Main;
import info.ds003.ircbot.Info;

public class EventHandler {
	
	protected int numNumericalEvents = 1000;
	protected Event [] numericalEvents;
	protected ArrayList<String> stringEventStrings;
	protected ArrayList<Event> stringEvents;
	protected Main main;

	public EventHandler(Main main)
	{
		this.main = main;
		numericalEvents = new Event[numNumericalEvents];
		Event unhandled = new UnhandledEvent(main);
		for( int i = 0; i < numNumericalEvents; ++i )
			numericalEvents[i] = unhandled;
		
		registerEvents();
	}
	
	public Event getEvent(String s)
	{
		if( s.length() == 3 && Info.isInteger(s) )
		{
			return fetchEvent(Integer.parseInt(s));
		}
		
		return fetchEvent(s);
	}
	
	private Event fetchEvent(int ev)
	{
		return numericalEvents[ev];
	}
	
	private Event fetchEvent(String s)
	{
		for( int i = 0; i < stringEvents.size(); ++i )
			if( stringEventStrings.get(i).equals(s) )
				return stringEvents.get(i);
		
		return null;
	}
	
	protected void registerEvents()
	{
		numericalEvents[4] = new FinishedLogin(main);
		WhoisHandler whois = new WhoisHandler(main);
		numericalEvents[311] = whois;
		numericalEvents[312] = whois;
		numericalEvents[313] = whois;
		numericalEvents[317] = whois;
		numericalEvents[318] = whois;
		numericalEvents[319] = whois;
		numericalEvents[671] = whois;
		numericalEvents[690] = whois;

		registerEvent("JOIN", new JoinedChannel(main));
		registerEvent("PART", new PartedChannel(main));
		registerEvent("INVITE", new InvitedToChannel(main));
		registerEvent("PRIVMSG", new PrivmsgHandler(main));
		registerEvent("QUIT", new ClientQuit(main));
	}
	
	protected void registerEvent(String type, Event ev)
	{
		stringEventStrings.add(type);
		stringEvents.add(ev);
	}
}
