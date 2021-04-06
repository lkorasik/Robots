package model.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyWorker {
    public static final String filename = ".session";

    public static final String logFramePositionXKey = "logFramePositionX";
    public static final String logFramePositionYKey = "logFramePositionY";
    public static final String logFrameWidth = "logFrameWidth";
    public static final String logFrameHeight = "logFrameHeight";

    public static final String gameFramePositionXKey = "gameFramePositionX";
    public static final String gameFramePositionYKey = "gameFramePositionY";
    public static final String gameFrameWidth = "gameFrameWidth";
    public static final String gameFrameHeight = "gameFrameHeight";

    // TODO: Bufferize it!

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

    public static class PropertyContainer {
        public int logFramePositionX;
        public int logFramePositionY;
        public int logFrameWidth;
        public int logFrameHeight;

        public int gameFramePositionX;
        public int gameFramePositionY;
        public int gameFrameWidth;
        public int gameFrameHeight;
    }
}
