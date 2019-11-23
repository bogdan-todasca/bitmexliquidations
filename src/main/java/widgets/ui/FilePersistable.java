package widgets.ui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilePersistable implements Persistable {
    private final String path;

    public FilePersistable(String path) {
        this.path = path;
    }

    @Override
    public void persist(JsonObject object) {
        LogManager.getLogger().info("Attempting to persist settings ");
        LogManager.getLogger().info(object);
        try (PrintWriter bw = new PrintWriter(new FileWriter(new File(path)))) {
            bw.println(object.toString());
        } catch (Exception ex) {
            LogManager.getLogger().error("Could not persist settings", ex);
        }
    }

    @Override
    public JsonObject loadPersistence() {
        LogManager.getLogger().info("Attempting to read settings ");
        try {
            if (!Files.exists(Paths.get(path))) {
                return null;
            }
            return new JsonParser().parse((Files.readString(Paths.get(path)))).getAsJsonObject();
        } catch (IOException ex) {
            LogManager.getLogger().error("Could not read settings", ex);
        }
        return null;
    }
}
