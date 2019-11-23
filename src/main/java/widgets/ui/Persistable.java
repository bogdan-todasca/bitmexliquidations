package widgets.ui;

import com.google.gson.JsonObject;

public interface Persistable {
    void persist(final JsonObject object);
    JsonObject loadPersistence();
}
