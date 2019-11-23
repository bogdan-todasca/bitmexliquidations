package widgets.ui;

import com.google.gson.JsonObject;


public interface Saveable {
    JsonObject save();

    void restore(final JsonObject source);

    SettingsManager getSettingsManager();
}
