package widgets.ui;

import util.ScreenUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OneSidedQuote {
    private final Type type;
    private JButton price;
    private JLabel side;
    private JLabel currency;
    private JPanel content;

    public OneSidedQuote(Type type) {
        this.type = type;
        initComponents();
    }

    public JPanel getContent() {
        return content;
    }

    private void initComponents() {
        this.price = createPriceComponent(this.type.foreground);

        this.currency = new JLabel();
        this.currency.setHorizontalAlignment(SwingConstants.CENTER);
        this.currency.setForeground(Color.WHITE);
        this.currency.setBorder(new EmptyBorder(5, 5, 5, 5));

        this.side = new JLabel(type.side);
        this.side.setHorizontalAlignment(SwingConstants.CENTER);
        this.side.setForeground(type.foreground);
        this.side.setBorder(new EmptyBorder(5,5,5,5));

        this.content = new JPanel(new BorderLayout());
        this.content.add(this.side, BorderLayout.NORTH);
        this.content.add(this.price, BorderLayout.CENTER);
        this.content.add(this.currency, BorderLayout.SOUTH);
        this.content.setBackground(Color.BLACK);
    }

    public void setPrice(final String value) {
        ScreenUtils.runOnSwing(() -> price.setText(value));
    }

    private JButton createPriceComponent(final Color foreground) {
        final JButton result = new JButton();
        result.setOpaque(true);
        result.setBackground(Color.BLACK);
        result.setForeground(foreground);
        result.setBorderPainted(false);
        result.setPreferredSize(new Dimension(50, 70));
        result.setFont(new Font("Arial", Font.PLAIN, 18));
        return result;
    }

    public enum Type {
        ASK(Color.BLUE, "BUY"), BID(Color.RED, "SELL");
        private final Color foreground;
        private final String side;

        Type(final Color color, final String side) {
            this.foreground = color;
            this.side = side;
        }
    }

    public void setCurrency(final String currency){
        this.currency.setText(currency);
    }
}
