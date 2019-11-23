package business.manager;

import business.Quote;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@FunctionalInterface
public interface QuoteObserver extends Observer<Quote> {
    @Override
    default void onSubscribe(Disposable d) {
    }

    @Override
    void onNext(Quote quote);

    @Override
    default void onError(Throwable e) {
    }

    @Override
    default void onComplete() {
    }
}
