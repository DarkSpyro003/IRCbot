package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;

public class RawCommand extends Command {

    public RawCommand(Main main) {
        super("raw", true, main);
    }

    public RawCommand(String description, Main main) {
        super("raw", description, true, main);
    }

    @Override
    protected void runCommand(String content) {
        actionCenter.sendRawData(content);
    }

}
