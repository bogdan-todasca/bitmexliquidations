package widgets.ui;

import business.Product;
import business.Venue;
import business.manager.Subscription;
import com.google.gson.JsonObject;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import util.ProductUtilities;
import util.ScreenUtils;
import widgets.controller.CellController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Objects;

public class QuoteCell implements Saveable {
    private JComboBox<Venue> exchange;
    private JComboBox<Product> products;
    private OneSidedQuote bid, ask;
    private JPanel content;
    private final CellController quoteCellController;
    private final SettingsManager settingsManager;

    QuoteCell(final int x, final int y) {
        this.quoteCellController = new CellController(quote -> SwingUtilities.invokeLater(() -> {
            bid.setPrice(ProductUtilities.formatQuote(quote.getBid(), (Product) this.products.getSelectedItem()));
            ask.setPrice(ProductUtilities.formatQuote(quote.getAsk(), (Product) this.products.getSelectedItem()));
        }));
        this.settingsManager = new SettingsManager(String.format("Cell[%d][%d]", x, y));
        initLayout();
        initEvents();
        requestInstruments();
    }

    private void initLayout() {
        this.bid = new OneSidedQuote(OneSidedQuote.Type.BID);
        this.ask = new OneSidedQuote(OneSidedQuote.Type.ASK);
        this.exchange = createExchangeDropdown();
        this.products = createProductDropdown();
        this.content = new JPanel(new BorderLayout());
        final JPanel prices = new JPanel(new GridLayout(1, 2));

        prices.add(bid.getContent());
        prices.add(ask.getContent());

        final JPanel upperPanel = new JPanel(new GridLayout(1, 2));
        upperPanel.add(this.exchange);
        upperPanel.add(this.products);
        upperPanel.setBorder(null);

        this.content.add(upperPanel, BorderLayout.NORTH);
        this.content.add(prices, BorderLayout.CENTER);
        this.content.setBorder(new LineBorder(Color.GRAY, 5, false));
    }

    private void requestInstruments() {
        quoteCellController.requestInstruments((Venue) exchange.getSelectedItem(), QuoteCell.this::populateProducts);

    }

    private void initEvents() {
        this.exchange.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                requestInstruments();
            }
        });

        this.products.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                final Product product = (Product) e.getItem();
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    unsubscribe(product);
                } else if (e.getStateChange() == ItemEvent.SELECTED) {
                    subscribe(product);
                    updateProduct(product);
                }
            }
        });
    }

    private void updateProduct(final Product product) {
        this.bid.setCurrency(product.getQuoteCurrency());
        this.ask.setCurrency(product.getQuoteCurrency());
    }

    private void subscribe(final Product product) {
        quoteCellController.setSubscription(new Subscription((Venue) this.exchange.getSelectedItem(), product.getSymbol()));
    }

    private void unsubscribe(final Product product) {
        LogManager.getLogger().info("Unsubscribing from {}", product);
        quoteCellController.unsubscribe();
    }


    private void populateProducts(final java.util.List<Product> productList) {
        ScreenUtils.runOnSwing(() -> {
            products.removeAllItems();
            productList.forEach(products::addItem);

        });
    }

    JPanel getContent() {
        return content;
    }

    private JComboBox<Venue> createExchangeDropdown() {
        final JComboBox<Venue> venueJComboBox = new JComboBox<>();
        Arrays.stream(Venue.values()).forEach(venueJComboBox::addItem);
        return venueJComboBox;
    }

    private JComboBox<Product> createProductDropdown() {
        final JComboBox<Product> productJComboBox = new JComboBox<>();
        return productJComboBox;
    }


    @Override
    public JsonObject save() {
        final JsonObject settings = new JsonObject();
        settings.addProperty(Properties.VENUE.toString(), Objects.requireNonNull(exchange.getSelectedItem()).toString());
        settings.addProperty(Properties.PRODUCT.toString(), Objects.requireNonNull(products.getSelectedItem()).toString());
        return settings;
    }

    @Override
    public void restore(JsonObject source) {
        final String venue = source.get(Properties.VENUE.toString()).getAsString();
        final String product = source.get(Properties.PRODUCT.toString()).getAsString();
        this.exchange.setSelectedItem(Venue.valueOf(venue));
        boolean found = false;
        for(int i = 0; !found && i < this.products.getItemCount(); i++){
            if(product.equals(this.products.getItemAt(i).toString())){
                this.products.setSelectedItem(this.products.getItemAt(i));
                found = true;
            }
        }
    }

    @Override
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    enum Properties {
        VENUE, PRODUCT;
    }
}
