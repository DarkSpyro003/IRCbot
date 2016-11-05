package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;

public class PartCommand extends Command {

    public PartCommand(String name, boolean requiresAdmin, Main main) {
        super(name, requiresAdmin, main);
    }

    public PartCommand(String name, String description, boolean requiresAdmin, Main main) {
        super(name, description, requiresAdmin, main);
    }

    @Override
    protected void runCommand(String content) {
        actionCenter.partChannel(content);
    }

}
