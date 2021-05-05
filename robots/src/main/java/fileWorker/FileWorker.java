package fileWorker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWorker {
    public static boolean existsFile(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static void createFile(String fileName) {
        try {
            Files.createFile(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
