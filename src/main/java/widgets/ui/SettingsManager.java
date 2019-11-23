package widgets.ui;


import java.util.HashSet;
import java.util.Set;

public class SettingsManager {
    private final Set<Saveable> children;
    private final String name;
    SettingsManager(final String name) {
        this.name = name;
        this.children = new HashSet<>();
    }

    void registerChild(final Saveable child) {
        this.children.add(child);
    }

    public void unregisterChild(final Saveable child) {
        this.children.remove(child);
    }

    Set<Saveable> getChildren() {
        return children;
    }

    String getName() {
        return name;
    }
}
