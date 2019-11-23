package widgets.ui;

import com.google.gson.JsonObject;
import netscape.javascript.JSObject;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame implements Saveable {

    private final int rows;
    private final int cols;
    private static final String TITLE = "Crypto Dashboard";
    private final SettingsManager settingsManager = new SettingsManager(TITLE);

    public Dashboard(final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
        initLayout();
        setPreferredSize(new Dimension(920, 550));
        setLocation(new Point(308, 70));
        setTitle(TITLE);
        initMenu();
        pack();
    }

    private void initMenu() {
        final JMenu menu = new JMenu("File");
        final JMenuItem save = new JMenuItem("Save Settings");
        menu.add(save);
        save.addActionListener(e -> saveSettings());
        final JMenuItem exit = new JMenuItem("Exit");
        menu.add(exit);
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

    }

    private void saveSettings() {
        SettingsPersistence.getInstance().saveSettings(this);
    }

    private void initLayout() {
        final JPanel content = new JPanel(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final QuoteCell qc = new QuoteCell(i, j);
                getSettingsManager().registerChild(qc);
                content.add(qc.getContent());
            }
        }
        setContentPane(content);
    }


    @Override
    public JsonObject save() {
        final JsonObject settings = new JsonObject();

        final JsonObject size = new JsonObject();
        size.addProperty(Properties.HEIGHT.toString(), this.getSize().height);
        size.addProperty(Properties.WIDTH.toString(), this.getSize().width);
        settings.add(Properties.SIZE.toString(), size);


        final JsonObject location = new JsonObject();
        location.addProperty(Properties.X.toString(), this.getLocation().x);
        location.addProperty(Properties.Y.toString(), this.getLocation().y);
        settings.add(Properties.LOCATION.toString(), location);

        return settings;

    }

    @Override
    public void restore(JsonObject source) {
        final JsonObject size = source.getAsJsonObject(Properties.SIZE.toString());
        if (size != null) {
            final double height = size.getAsJsonPrimitive(Properties.HEIGHT.toString()).getAsDouble();
            final double width = size.getAsJsonPrimitive(Properties.WIDTH.toString()).getAsDouble();
            System.out.println("Loaded from saved settings " + height + " " + width);
        }
        final JsonObject location = source.getAsJsonObject(Properties.LOCATION.toString());
        if (location != null) {
            final double x = location.getAsJsonPrimitive(Properties.X.toString()).getAsDouble();
            final double y = location.getAsJsonPrimitive(Properties.Y.toString()).getAsDouble();
        }


    }

    @Override
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    enum Properties {
        SIZE, WIDTH, HEIGHT, LOCATION, X, Y;
    }
}
