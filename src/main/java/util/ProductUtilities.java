package util;

import business.Product;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ProductUtilities {
    private static final Map<Integer, DecimalFormat> formatters = new HashMap<>();

    private ProductUtilities() {
    }

    public static String formatQuote(final double value, final Product product) {
        final int decimals = product.getPrecision();
        if (!formatters.containsKey(decimals)) {
            final StringBuilder formatter = new StringBuilder("###,##0");
            if (decimals > 0) {
                formatter.append(".");
                IntStream.range(0, decimals).forEach(t -> formatter.append("0"));
            }
            formatters.put(decimals, new DecimalFormat(formatter.toString()));
        }
        return formatters.get(decimals).format(value);
    }
}
