package gui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static void createFile(File createFile, String fileData) throws IOException {
        if (createFile.createNewFile())
            System.out.println("File created: " + createFile.getName());
        else
            System.out.println("File already exists");
        FileOutputStream fos = new FileOutputStream(createFile);
        fos.write(fileData.getBytes());
        fos.flush();
        fos.close();
    }

    public static void folderTempFiles(String localDir) throws IOException {
        if (!Files.exists(Paths.get(localDir))) {               // does this directory exist?
            Files.createDirectories(Paths.get(localDir));       // create  folder for temporary files
        }
    }
}
