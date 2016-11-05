package info.ds003.ircbot.events;

import info.ds003.ircbot.Main;

public class UnhandledEvent extends Event {

	public UnhandledEvent(Main main) {
		super(main);
	}

	@Override
	public void handleEvent(String sender, String type, String receiver, String content) {
		
	}

}
