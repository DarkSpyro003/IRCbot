package info.ds003.ircbot;

public class Actions {

    private Connection network;

    public Actions(Main main) {
        network = main.getNetwork();
    }

    public void sendRawData(String data) {
        network.sendData(data);
    }

    public void sendMessage(String receiver, String content) {
        network.sendData("PRIVMSG " + Info.getNick(receiver) + " :" + content);
    }

    public void sendAction(String receiver, String content) {
        sendCtcp(receiver, "ACTION", content);
    }

    public void sendCtcp(String receiver, String ctcp, String content) {
        sendCtcp(receiver, ctcp, content, "PRIVMSG");
    }

    public void sendCtcp(String receiver, String ctcp, String content, String type) {
        String spacer = " ";
        if (content == null || content.length() == 0)
            spacer = "";

        network.sendData(type + " " + Info.getNick(receiver) + " :" + "\001" + ctcp + spacer + content + "\001");
    }

    public void joinChannel(String channel) {
        network.sendData("JOIN " + channel);
    }

    public void partChannel(String channel) {
        network.sendData("PART " + channel);
    }

    public void whois(String name) {
        network.sendData("WHOIS " + name);
    }

    public void quit() {
        quit("Leaving.");
    }

    public void quit(String message) {
        network.sendData("QUIT :" + message);
    }
}
