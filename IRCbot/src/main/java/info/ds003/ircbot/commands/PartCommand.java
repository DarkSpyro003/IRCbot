package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;

public class PartCommand extends Command {

    public PartCommand(Main main) {
        super("part", true, main);
    }

    public PartCommand(String description, Main main) {
        super("part", description, true, main);
    }

    @Override
    protected void runCommand(String content) {
        actionCenter.partChannel(content);
    }

}
