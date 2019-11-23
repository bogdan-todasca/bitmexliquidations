package widgets.ui;

import com.google.gson.JsonObject;

public class SettingsPersistence {
    private static final SettingsPersistence instance = new SettingsPersistence();

    public static SettingsPersistence getInstance() {
        return instance;
    }

    void saveSettings(final Saveable root) {
        saveSettings(root, new SettingsFilePersistable());
    }

    private void saveSettings(final Saveable root, final Persistable persistable) {
        final JsonObject result = new JsonObject();
        saveSettings(result, root);
        persistable.persist(result);
    }

    private void saveSettings(final JsonObject current, final Saveable target) {
        final JsonObject targetNode = target.save();
        current.add(target.getSettingsManager().getName(), targetNode);
        for (Saveable s : target.getSettingsManager().getChildren()) {
            final JsonObject result = s.save();
            targetNode.add(s.getSettingsManager().getName(), result);
            saveSettings(result, s);
        }
    }

    public void loadSettings(final Saveable root) {
        loadSettings(root, new SettingsFilePersistable());
    }

    private void loadSettings(final Saveable root, final Persistable persistable) {
        final JsonObject rootNode = persistable.loadPersistence();
        loadSettings(root, rootNode.getAsJsonObject(root.getSettingsManager().getName()));
    }

    private void loadSettings(final Saveable currentTarget, final JsonObject currentNode) {
        //loading for current node
        currentTarget.restore(currentNode);
        //loading for every child
        for (Saveable child : currentTarget.getSettingsManager().getChildren()) {
            loadSettings(child, currentNode.getAsJsonObject(child.getSettingsManager().getName()));
        }
    }


}
