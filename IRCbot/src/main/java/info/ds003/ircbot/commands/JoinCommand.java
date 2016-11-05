package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;

public class JoinCommand extends Command {

    public JoinCommand(Main main) {
        super("join", true, main);
    }

    public JoinCommand(String description, Main main) {
        super("join", description, true, main);
    }

    @Override
    protected void runCommand(String content) {
        actionCenter.joinChannel(content);
    }

}
