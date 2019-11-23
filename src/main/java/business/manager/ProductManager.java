package business.manager;

import business.Product;
import business.Venue;
import business.bitmex.Callback;
import persistence.RedisConnection;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductManager {
    private static final ProductManager instance = new ProductManager();
    private final Executor worker;

    private ProductManager() {
        this.worker = Executors.newSingleThreadExecutor();
    }

    public static ProductManager getInstance() {
        return instance;
    }

    public void readInstruments(final Venue venue, final Callback<List<Product>> callback) {
        this.worker.execute(() ->
                callback.onDone(RedisConnection.getInstance().readProducts(venue))
        );
    }


}
