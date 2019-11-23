package persistence;

import business.Product;
import business.Venue;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class RedisConnection {
    private final Jedis jedis = new Jedis("localhost");
    private final static RedisConnection instance = new RedisConnection();

    private RedisConnection() {
        System.out.println(this.jedis.keys("*5"));
    }

    public static RedisConnection getInstance() {
        return instance;
    }

    public void test() {
        System.out.println("Connection successful");

        //Checking server
        System.out.println("Getting response from the server: " + jedis.ping());
    }

    public void saveProducts(final Venue venue, final List<Product> products) {
        LogManager.getLogger().info("Pinging redis " + jedis.ping());
        products.forEach(p -> jedis.sadd(venue.toString(), toJSON(p)));
    }

    public List<Product> readProducts(final Venue venue) {
        LogManager.getLogger().info("Reading product for query {}", venue);
        final Set<String> result = jedis.smembers(venue.toString());
        LogManager.getLogger().info("Found {} entries in Redis", result.size());

        return result.stream().map(t -> new Gson().fromJson(t, venue.getProductClass())).collect(Collectors.toList());
    }

    private static String toJSON(final Object obj) {
        return new Gson().toJson(obj);
    }

}
