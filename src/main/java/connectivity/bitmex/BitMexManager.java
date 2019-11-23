package connectivity.bitmex;

import business.Venue;
import org.apache.logging.log4j.LogManager;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class BitMexManager {
    private final ClientManager client = ClientManager.createClient();
    private static final BitMexManager instance = new BitMexManager();
    private final Executor executor;
    private BitMexManager() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    static BitMexManager singleton() {
        return instance;
    }


    void subscribeQuote(final String code) {
        LogManager.getLogger(BitMexManager.class.getName()).info(String.format("Subscribing for quote for %s", code));
        this.executor.execute(() -> {
            try {
                client.connectToServer(Venue.BITMEX.getListener(),
                        new URI(String.format("wss://www.bitmex.com/realtime?subscribe=quote:%s",
                                code)));
            } catch (DeploymentException | IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });


    }


}
