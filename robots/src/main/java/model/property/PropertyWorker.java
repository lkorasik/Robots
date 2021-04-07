package model.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyWorker {
    private static final String filename = ".session";

    private static final String logFramePositionXKey = "logFramePositionX";
    private static final String logFramePositionYKey = "logFramePositionY";
    private static final String logFrameWidth = "logFrameWidth";
    private static final String logFrameHeight = "logFrameHeight";

    private static final String gameFramePositionXKey = "gameFramePositionX";
    private static final String gameFramePositionYKey = "gameFramePositionY";
    private static final String gameFrameWidth = "gameFrameWidth";
    private static final String gameFrameHeight = "gameFrameHeight";

    static {
        var file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            var container = new PropertyContainer();

            container.logFramePositionX = 20;
            container.logFramePositionY = 10;
            container.logFrameWidth = 300;
            container.logFrameHeight = 500;

            container.gameFramePositionX = 340;
            container.gameFramePositionY = 20;
            container.gameFrameWidth = 500;
            container.gameFrameHeight = 800;

            save(container);
        }
    }

    public static void save(PropertyContainer container) {
        Properties properties = new Properties();

        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            properties.setProperty(logFramePositionXKey, String.valueOf(container.logFramePositionX));
            properties.setProperty(logFramePositionYKey, String.valueOf(container.logFramePositionY));
            properties.setProperty(logFrameWidth, String.valueOf(container.logFrameWidth));
            properties.setProperty(logFrameHeight, String.valueOf(container.logFrameHeight));
            properties.setProperty(gameFramePositionXKey, String.valueOf(container.gameFramePositionX));
            properties.setProperty(gameFramePositionYKey, String.valueOf(container.gameFramePositionY));
            properties.setProperty(gameFrameWidth, String.valueOf(container.gameFrameWidth));
            properties.setProperty(gameFrameHeight, String.valueOf(container.gameFrameHeight));

            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertyContainer load() {
        Properties properties = new Properties();

        PropertyContainer container = null;

        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            properties.load(fileInputStream);

            container = new PropertyContainer();
            container.logFramePositionX = Integer.parseInt(properties.getProperty(logFramePositionXKey));
            container.logFramePositionY = Integer.parseInt(properties.getProperty(logFramePositionYKey));
            container.logFrameWidth = Integer.parseInt(properties.getProperty(logFrameWidth));
            container.logFrameHeight = Integer.parseInt(properties.getProperty(logFrameHeight));
            container.gameFramePositionX = Integer.parseInt(properties.getProperty(gameFramePositionXKey));
            container.gameFramePositionY = Integer.parseInt(properties.getProperty(gameFramePositionYKey));
            container.gameFrameWidth = Integer.parseInt(properties.getProperty(gameFrameWidth));
            container.gameFrameHeight = Integer.parseInt(properties.getProperty(gameFrameHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return container;
    }
}
