package business;

import com.google.gson.Gson;

import java.util.List;

public abstract class QuoteAdapter {
    public abstract List<Quote> getQuote(final Object gson);

    public abstract Class<?> getGsonClass();

    public List<Quote> parseQuote(final String raw) {
        final Object gson = new Gson().fromJson(raw, getGsonClass());
        return getQuote(gson);
    }
}
