package info.ds003.ircbot.events;

import info.ds003.ircbot.Main;

public class ClientQuit extends Event {

    public ClientQuit(Main main) {
        super(main);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void handleEvent(String sender, String type, String receiver,
                            String content) {
        // TODO Auto-generated method stub

    }

}
