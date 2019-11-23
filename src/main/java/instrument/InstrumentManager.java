package instrument;

import business.Product;
import business.Venue;
import org.apache.logging.log4j.LogManager;
import persistence.RedisConnection;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InstrumentManager {
    private static final InstrumentManager instance = new InstrumentManager();
    private final Executor executor;

    private InstrumentManager() {
        this.executor = Executors.newFixedThreadPool(Venue.values().length);
    }

    public static InstrumentManager getInstance() {
        return instance;
    }

    public void downloadProducts() {
        LogManager.getLogger().info("Downloading Products");
        Arrays.stream(Venue.values()).forEach(this::downloadFromVenue);
    }

    private void downloadFromVenue(final Venue venue) {
        if (venue.getProductProvider() == null) {
            return;
        }
        LogManager.getLogger().info("Downloading products from venue " + venue);
        final List<Product> products = venue.getProductProvider().downloadProducts();
        RedisConnection.getInstance().saveProducts(venue, products);
    }
}
