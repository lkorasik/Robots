package model.property;

import lombok.Getter;
import model.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyWorker {
    private final String filename = ".session";

    private final String logFramePositionXKey = "logFramePositionX";
    private final String logFramePositionYKey = "logFramePositionY";
    private final String logFrameWidth = "logFrameWidth";
    private static final String logFrameHeight = "logFrameHeight";

    private static final String gameFramePositionXKey = "gameFramePositionX";
    private static final String gameFramePositionYKey = "gameFramePositionY";
    private static final String gameFrameWidth = "gameFrameWidth";
    private static final String gameFrameHeight = "gameFrameHeight";

    public static final PropertyWorker instance = new PropertyWorker();
    @Getter
    private static boolean isFileCreated;

    private PropertyWorker() {
        isFileCreated = false;

        var file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                isFileCreated = true;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void save(PropertyContainer container) {
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

    public PropertyContainer load() {
        Properties properties = new Properties();

        PropertyContainer container = null;

        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            properties.load(fileInputStream);

            container = new PropertyContainer();
            container.logFramePositionX = tryParse(properties.getProperty(logFramePositionXKey), Constants.PropertyDefaultValues.LOG_FRAME_POSITION_X);
            container.logFramePositionY = tryParse(properties.getProperty(logFramePositionYKey), Constants.PropertyDefaultValues.LOG_FRAME_POSITION_Y);
            container.logFrameWidth = tryParse(properties.getProperty(logFrameWidth), Constants.PropertyDefaultValues.LOG_FRAME_WIDTH);
            container.logFrameHeight = tryParse(properties.getProperty(logFrameHeight), Constants.PropertyDefaultValues.LOG_FRAME_HEIGHT);
            container.gameFramePositionX = tryParse(properties.getProperty(gameFramePositionXKey), Constants.PropertyDefaultValues.GAME_FRAME_POSITION_X);
            container.gameFramePositionY = tryParse(properties.getProperty(gameFramePositionYKey), Constants.PropertyDefaultValues.GAME_FRAME_POSITION_Y);
            container.gameFrameWidth = tryParse(properties.getProperty(gameFrameWidth), Constants.PropertyDefaultValues.GAME_FRAME_WIDTH);
            container.gameFrameHeight = tryParse(properties.getProperty(gameFrameHeight), Constants.PropertyDefaultValues.GAME_FRAME_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return container;
    }

    private int tryParse(String property, int defaultValue) {
        int value = 0;

        try {
            value = Integer.parseInt(property);
        } catch (NumberFormatException exception) {
            value = defaultValue;
        }

        return value;
    }
}
