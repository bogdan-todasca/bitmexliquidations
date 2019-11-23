package business.manager;

import business.Quote;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;


public class SubscriptionManager {
    private static final SubscriptionManager INSTANCE = new SubscriptionManager();

    public static SubscriptionManager getINSTANCE() {
        return INSTANCE;
    }

    public void addListener(final Subscription subscription, Consumer<Quote> listener) {
        subscription.venue.getListener().addListener(subscription.code, listener);
    }

    public void removeListener(final Subscription subscription, final Consumer<Quote> listener) {
        subscription.venue.getListener().removeListener(subscription.code, listener);
    }


}
