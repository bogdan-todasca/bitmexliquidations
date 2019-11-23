package connectivity.bitmex;


import business.Quote;
import business.bitmex.BitmexQuoteAdapter;
import connectivity.quote.ExchangeQuoteProvider;

import javax.websocket.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@ClientEndpoint
public class BitmexQuoteProvider extends ExchangeQuoteProvider {

    private static final BitmexQuoteAdapter quoteParser = new BitmexQuoteAdapter();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected ... " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        final List<Quote> quotes = quoteParser.parseQuote(message);
        quotes.forEach(this::notify);
        return null;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }


    @Override
    public void subscribe(String code) throws DeploymentException, IOException, URISyntaxException {
        BitMexManager.singleton().subscribeQuote(code);
    }

    @Override
    public void unsubscribe(String code) {
        //TODO
    }

    @Override
    public String getCodeFromQuote(Quote quote) {
        return quote.getSymbol();
    }
}
