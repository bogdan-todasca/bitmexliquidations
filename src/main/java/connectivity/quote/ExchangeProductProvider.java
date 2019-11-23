package connectivity.quote;

import business.Product;

import java.util.List;

public abstract class ExchangeProductProvider {
    public abstract List<Product> downloadProducts();
}
