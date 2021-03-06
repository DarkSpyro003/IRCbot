package info.ds003.ircbot;

import info.ds003.ircbot.events.Event;
import info.ds003.ircbot.events.EventHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// Provided by http://www.hawkee.com/snippet/5656/
public class Connection implements Runnable {

    private String server;
    private int port;
    private String nick, user, name;
    private boolean isActive;
    private EventHandler eventHandler;
    private String dataQueue;

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    protected void setServer(String server) {
        this.server = server;
    }

    protected String getServer() {
        return this.server;
    }

    protected void setPort(int port) {
        this.port = port;
    }

    protected int getPort() {
        return this.port;
    }

    protected void setNick(String nick) {
        this.nick = nick;
    }

    protected String getNick() {
        return this.nick;
    }

    protected void setUser(String user) {
        this.user = user;
    }

    protected String getUser() {
        return this.user;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getName() {
        return this.name;
    }

    protected void setActive(boolean bool) {
        this.isActive = bool;
    }

    protected boolean isActive() {
        return this.isActive;
    }

    public Connection(String server, int port, String nick, String user, String realname) {
        System.out.println("Initializing.");
        this.server = server;
        this.port = port;
        this.nick = nick;
        this.user = user;
        this.name = realname;
        dataQueue = "";
    }

    protected void start() throws java.io.IOException {
        this.socket = new Socket(this.getServer(), this.getPort());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        if (socket.isConnected()) {
            out.write("NICK " + this.nick + "\r\n");
            out.write("USER " + this.user + " \"\" \"\" :" + this.name + "\r\n");
            out.flush();
            this.setActive(true);
        }
    }

    public void sendData(String data) {
        if (!data.contains("\001")) // trim non-ctcp data only
            data = data.trim();

        dataQueue += data + "\r\n";
    }

    private void handleReceivedData(String buffer) {
        System.out.println("> " + buffer);
        if (buffer.startsWith("PING")) {
            sendData("PONG " + buffer.substring(5));
        }
        if (buffer.startsWith(":")) {
            buffer = buffer.substring(1);
            String[] c = buffer.split(" ");
            String sender, type, receiver;
            StringBuilder contentBuilder = new StringBuilder();
            sender = c[0];
            type = c[1];
            receiver = c[2];
            for (int i = 3; i < c.length; ++i) {
                contentBuilder.append(c[i]);
                if (i + 1 != c.length)
                    contentBuilder.append(" ");
            }
            String content = contentBuilder.toString();
            if (content.length() > 1)
                content = content.substring(1);
            //System.out.printf("%s: %s; %s: %s; %s: %s; %s: %s", "sender", sender, "type", type, "receiver", receiver, "content", content);

            if (type != null && !buffer.startsWith("PING")) {
                Event event = eventHandler.getEvent(type);
                if (event != null)
                    event.handleEvent(sender, type, receiver, content);

                // Special case
                if (type.equals("QUIT") && Info.getNick(sender).equals(nick)) {
                    isActive = false;
                }
            }
        }
    }

    public void run() {
        String buffer;
        while (this.isActive()) {
            try {
                while ((buffer = in.readLine()) != null) {
                    handleReceivedData(buffer);
                    if (dataQueue.length() > 0) {
                        out.write(dataQueue);
                        out.flush();
                        System.out.print("< " + dataQueue);
                        dataQueue = "";
                    }
                }
            } catch (java.io.IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}