package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;

public class JoinCommand extends Command {

    public JoinCommand(String name, boolean requiresAdmin, Main main) {
        super(name, requiresAdmin, main);
    }

    public JoinCommand(String name, String description, boolean requiresAdmin, Main main) {
        super(name, description, requiresAdmin, main);
    }

    @Override
    protected void runCommand(String content) {
        actionCenter.joinChannel(content);
    }

}
