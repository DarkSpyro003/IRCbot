package info.ds003.ircbot.events;

import info.ds003.ircbot.Main;
import info.ds003.ircbot.commands.CommandHandler;

public class InvitedToChannel extends Event {

    private CommandHandler commandHandler;

    public InvitedToChannel(Main main) {
        super(main);
        commandHandler = main.getCommandHandler();
    }

    @Override
    public void handleEvent(String sender, String type, String receiver, String content) {
        commandHandler.handleCommand(main.getAdmin().equals(sender), "join " + content, sender);
    }

}
