package widgets.ui;

import java.io.File;

public class SettingsFilePersistable extends FilePersistable {
    public SettingsFilePersistable() {
        super(System.getProperty("user.home") + File.separator + "crypto_dashboard.txt");
    }
}
