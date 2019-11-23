package application;

import business.Quote;
import business.Venue;
import business.manager.QuoteObserver;
import business.manager.Subscription;
import business.manager.SubscriptionManager;
import instrument.InstrumentManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.apache.logging.log4j.LogManager;
import persistence.RedisConnection;
import widgets.ui.Dashboard;
import widgets.ui.SettingsFilePersistable;
import widgets.ui.SettingsManager;
import widgets.ui.SettingsPersistence;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws DeploymentException, IOException, URISyntaxException {
        System.setProperty("log4j.configurationFile", "src/main/java/log4jconfig.xml");
        System.setProperty("redis.port", "6379");

        RedisConnection.getInstance().test();
        InstrumentManager.getInstance().downloadProducts();
        final Dashboard d = new Dashboard(4, 4);
        SettingsPersistence.getInstance().loadSettings(d);
        d.setVisible(true);


    }
}
