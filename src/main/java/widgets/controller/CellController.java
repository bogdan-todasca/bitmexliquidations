package widgets.controller;

import business.Product;
import business.Quote;
import business.Venue;
import business.bitmex.Callback;
import business.manager.ProductManager;
import business.manager.Subscription;
import business.manager.SubscriptionManager;
import io.reactivex.functions.Consumer;

import java.util.List;

public class CellController {
    private Subscription subscription;
    private final Consumer<Quote> quoteObserver;

    public CellController(final Consumer<Quote> quoteObserver) {
        this.quoteObserver = quoteObserver;
    }

    private void subscribe() {
        SubscriptionManager.getINSTANCE().addListener(this.subscription, this.quoteObserver);
    }

    public void unsubscribe() {
        SubscriptionManager.getINSTANCE().removeListener(this.subscription, this.quoteObserver);
    }

    public void requestInstruments(Venue venue, Callback<List<Product>> onDone) {
        ProductManager.getInstance().readInstruments(venue, onDone);
    }


    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
        subscribe();
    }
}
