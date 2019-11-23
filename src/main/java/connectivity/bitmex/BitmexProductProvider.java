package connectivity.bitmex;

import business.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import connectivity.quote.ExchangeProductProvider;
import org.apache.logging.log4j.LogManager;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class BitmexProductProvider extends ExchangeProductProvider {
    private static final BitmexProductProvider instance = new BitmexProductProvider();
    private final Client client;
    private final String PRODUCT_ENDPOINT = "https://www.bitmex.com/api/v1/instrument";

    public static BitmexProductProvider getInstance() {
        return instance;
    }

    private BitmexProductProvider() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public List<Product> downloadProducts() {
        return downloadProducts(ProductFilter.OPEN);
    }

    private List<Product> downloadProducts(final ProductFilter filter) {

        LogManager.getLogger().info("Downloading products for filter {}", filter);
        final WebTarget webTarget
                = client.target(PRODUCT_ENDPOINT).
                queryParam("filter", filter.getFilter());
        final Invocation.Builder invocationBuilder
                = webTarget.request(MediaType.APPLICATION_JSON);
        final Response response
                = invocationBuilder.get();
        LogManager.getLogger().info("Received content" + response.getStatusInfo());
        final String content = response.readEntity(String.class);
        System.out.println(content);
        final BitmexProduct[] result = new Gson().fromJson(content, BitmexProduct[].class);
        LogManager.getLogger().info(content);
        LogManager.getLogger().info("Downloaded {} {}", result.length, " products");
        return Arrays.asList(result);
    }

    enum ProductFilter {
        OPEN("{\"state\":\"Open\"}");
        private final String filter;

        ProductFilter(String filter) {
            this.filter = filter;
        }

        public String getFilter() {
            return URLEncoder.encode(filter, StandardCharsets.UTF_8);
        }
    }
}
