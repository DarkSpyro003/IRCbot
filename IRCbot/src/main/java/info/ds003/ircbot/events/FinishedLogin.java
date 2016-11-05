package info.ds003.ircbot.events;

import info.ds003.ircbot.Main;

public class FinishedLogin extends Event {

	public FinishedLogin(Main main) {
		super(main);
	}

	@Override
	public void handleEvent(String sender, String type, String receiver, String content) {
		main.ready();
	}
}
