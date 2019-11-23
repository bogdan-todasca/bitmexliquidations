package connectivity.bitmex;

import business.Product;
import business.Venue;

import java.util.Objects;

public class BitmexProduct implements Product {
    String symbol;
    String positionCurrency;
    String quoteCurrency;
    String expiry;
    String tickSize;


    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitmexProduct that = (BitmexProduct) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPositionCurrency() {
        return positionCurrency;
    }

    public void setPositionCurrency(String positionCurrency) {
        this.positionCurrency = positionCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    @Override
    public int getPrecision() {
        try {
            return tickSize.startsWith("0.") ?
                    tickSize.length() - 2 :
                    tickSize.startsWith("1e-") ?
                            Integer.parseInt(tickSize.substring(3)) :
                            0;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    @Override
    public String getKey() {
        return Venue.BITMEX + symbol;
    }


}
