package business.bitmex;

import business.Quote;

import java.util.Date;

public class BitmexData {
    private String table;
    private String action;

    private Data[] data;


    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    static class Data {

        private String symbol;
        private String bidSize;
        private String bidPrice;
        private String askPrice;
        private String askSize;
        private Date timestamp;
        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getBidSize() {
            return bidSize;
        }

        public void setBidSize(String bidSize) {
            this.bidSize = bidSize;
        }

        public String getBidPrice() {
            return bidPrice;
        }

        public void setBidPrice(String bidPrice) {
            this.bidPrice = bidPrice;
        }

        public String getAskPrice() {
            return askPrice;
        }

        public void setAskPrice(String askPrice) {
            this.askPrice = askPrice;
        }

        public String getAskSize() {
            return askSize;
        }

        public void setAskSize(String askSize) {
            this.askSize = askSize;
        }

        public static Quote createQuote(Data data) {
            return new Quote(data.symbol,
                    Double.valueOf(data.bidSize),
                    Double.valueOf(data.bidPrice),
                    Double.valueOf(data.askPrice),
                    Double.valueOf(data.askSize),
                    data.timestamp);
        }
    }

}
