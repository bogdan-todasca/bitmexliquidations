package connectivity.quote;

import business.Quote;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import org.apache.logging.log4j.LogManager;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class ExchangeQuoteProvider {
    private final Map<String, PublishSubject<Quote>> subjects = new HashMap<>();
    private final Map<String, Integer> subjectCount = new HashMap<>();
    private final Map<String, Observable<Quote>> subjectWrapper = new HashMap<>();
    private final Map<Consumer<Quote>, Disposable> subscriptions = new HashMap<>();

    public void addListener(final String code, final Consumer<Quote> consumer) {
        boolean needsSubscribe = false;
        if (!subjects.containsKey(code)) {
            needsSubscribe = true;
            final PublishSubject<Quote> subject = PublishSubject.create();
            final Observable<Quote> o = subject.throttleLast(1000, TimeUnit.MILLISECONDS);
            subjects.put(code, subject);
            subjectCount.put(code, 0);
            subjectWrapper.put(code, o);
        }
        final Observable<Quote> o = subjectWrapper.get(code);
        subscriptions.put(consumer, o.subscribe(consumer));
        subjectCount.put(code, subjectCount.get(code) + 1);
        if (needsSubscribe) {
            try {
                subscribe(code);
            } catch (DeploymentException | IOException | URISyntaxException e) {
                LogManager.getLogger(ExchangeQuoteProvider.class.getName()).error("Exception while subscribing", e);
            }
        }
    }

    public void removeListener(final String code, final Consumer<Quote> observer) {
        subscriptions.remove(observer).dispose();
        subjectCount.put(code, subjectCount.get(code) - 1);
        LogManager.getLogger().info("Decreasing subscription count for {} to {}", code, subjectCount.get(code));
        boolean needsUnsubscribe = false;
        if(subjectCount.get(code) == 0){
            needsUnsubscribe = true;
            subjectCount.remove(code);
            subjectWrapper.remove(code);
            subjects.remove(code);
        }
        if(needsUnsubscribe){
            LogManager.getLogger().info("Requesting unsubscribe from the exchange {}", code);
            unsubscribe(code);
        }
    }

    protected void notify(final Quote quote) {
        if(!subjects.containsKey(getCodeFromQuote(quote))){
            return;
        }
        subjects.get(getCodeFromQuote(quote)).onNext(quote);
    }

    public abstract void subscribe(final String code) throws DeploymentException, IOException, URISyntaxException;

    public abstract void unsubscribe(final String code);

    public abstract String getCodeFromQuote(final Quote quote);
}
