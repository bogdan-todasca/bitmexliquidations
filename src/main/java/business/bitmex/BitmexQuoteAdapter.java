package business.bitmex;

import business.Quote;
import business.QuoteAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BitmexQuoteAdapter extends QuoteAdapter {

    @Override
    public List<Quote> getQuote(Object gson) {
        final BitmexData data = (BitmexData) gson;
        if (data.getData() == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(data.getData()).map(BitmexData.Data::createQuote).collect(Collectors.toList());
    }

    @Override
    public Class<?> getGsonClass() {
        return BitmexData.class;
    }
}
