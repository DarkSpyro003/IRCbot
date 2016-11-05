package info.ds003.ircbot.commands;

import info.ds003.ircbot.Actions;
import info.ds003.ircbot.Main;

public class CommandHandler {

    private Command[] commands;
    private Actions actionCenter;

    public CommandHandler(Main main) {
        actionCenter = main.getActionCenter();
        commands = main.getCommandRegistration();
    }

    public void handleCommand(boolean admin, String command, String sender) {
        try {
            String[] cmd = command.split(" ");
            String commandName = cmd[0];
            StringBuilder commandContent = new StringBuilder();
            for (int i = 1; i < cmd.length; ++i) {
                commandContent.append(cmd[i]);
                if (i + 1 < cmd.length)
                    commandContent.append(" ");
            }

            Command run = findCommand(commandName);
            if (run != null)
                run.execute(commandContent.toString(), admin);
            else
                actionCenter.sendMessage(sender, "Command not found!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            actionCenter.sendMessage(sender, "An error occured executing the command.");
        }
    }

    private Command findCommand(String name) {
        for (Command command : commands)
            if (command.getName().equals(name))
                return command;

        return null;
    }
}
