package util;

import javax.swing.*;

public class ScreenUtils {
    private ScreenUtils() {
    }

    public static void runOnSwing(final Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }
}
