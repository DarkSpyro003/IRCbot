package info.ds003.ircbot.commands;

import info.ds003.ircbot.Main;

public class QuitCommand extends Command {

    public QuitCommand(Main main) {
        super("quit", true, main);
    }

    public QuitCommand(String description, Main main) {
        super("quit", description, true, main);
    }

    @Override
    protected void runCommand(String content) {
        actionCenter.quit("bubais");
    }

}
