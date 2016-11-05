package info.ds003.ircbot;

import info.ds003.ircbot.commands.*;
import info.ds003.ircbot.events.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private Actions actionCenter;
    private Connection network;
    private EventHandler eventHandler;
    private String admin = "Sparxy!Sparxy@ds003.info";
    private String prefix = "!";
    private List<Command> commands = new ArrayList<Command>();
    private CommandHandler commandHandler;

    public Command[] getCommandRegistration() {
        int n = commands.size();
        Command[] command = new Command[n];
        for (int i = 0; i < n; ++i)
            command[i] = commands.get(i);

        return command;
    }

    public void registerCommands() {
        registerCommand(new JoinCommand(this));
        registerCommand(new PartCommand(this));
        registerCommand(new QuitCommand(this));
        registerCommand(new RawCommand(this));
    }

    public void registerCommand(Command command) {
        commands.add(command);
    }

    public static void main(String args[]) {
        new Main("irc.esper.net", 6667, "SparxBot", "Sparxy", "Sparxy");
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getAdmin() {
        return admin;
    }

    public Connection getNetwork() {
        return network;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public Actions getActionCenter() {
        return actionCenter;
    }

    public Main(String server, int port, String nick, String user, String realname) {
        System.out.println("Starting bot.");
        network = new Connection(server, port, nick, user, realname);
        actionCenter = new Actions(this);
        registerCommands();
        commandHandler = new CommandHandler(this);
        eventHandler = new EventHandler(this);
        network.setEventHandler(eventHandler);
        ExecutorService threadExec = Executors.newCachedThreadPool();

        try {
            network.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadExec.execute(network);
        threadExec.shutdown();
    }

    public void ready() {
        actionCenter.joinChannel("#Sparxy");
    }
}
