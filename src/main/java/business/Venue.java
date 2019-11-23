package business;

import connectivity.bitmex.BitmexProduct;
import connectivity.bitmex.BitmexProductProvider;
import connectivity.bitmex.BitmexQuoteProvider;
import connectivity.quote.ExchangeProductProvider;
import connectivity.quote.ExchangeQuoteProvider;

public enum Venue {
    BITMEX(new BitmexQuoteProvider(), BitmexProductProvider.getInstance(), BitmexProduct.class),
    BINANCE(null, null, null),
    COINBASE(null, null, null);
    private ExchangeQuoteProvider listener;
    private ExchangeProductProvider productProvider;
    private Class<? extends Product> productClass;

    Venue(ExchangeQuoteProvider listener, ExchangeProductProvider productProvider, Class<? extends Product> productClass) {
        this.listener = listener;
        this.productProvider = productProvider;
        this.productClass = productClass;
    }

    public ExchangeQuoteProvider getListener() {
        return listener;
    }

    public ExchangeProductProvider getProductProvider() {
        return productProvider;
    }

    public Class<? extends Product> getProductClass() {
        return productClass;
    }
}
