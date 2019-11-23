package business;


import java.util.Date;

public class Quote {
    private final String symbol;
    private final Double ask;
    private final Double bid;
    private final Double askSize;
    private final Double bidSize;
    private final Date date;

    public Quote(String symbol, Double bidSize, Double bid, Double ask, Double askSize, Date date) {
        this.symbol = symbol;
        this.ask = ask;
        this.bid = bid;
        this.askSize = askSize;
        this.bidSize = bidSize;
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getAsk() {
        return ask;
    }

    public Double getBid() {
        return bid;
    }

    public Double getAskSize() {
        return askSize;
    }

    public Double getBidSize() {
        return bidSize;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "symbol='" + symbol + '\'' +
                ", ask=" + ask +
                ", bid=" + bid +
                ", askSize=" + askSize +
                ", bidSize=" + bidSize +
                ", date=" + date +
                '}';
    }
}
