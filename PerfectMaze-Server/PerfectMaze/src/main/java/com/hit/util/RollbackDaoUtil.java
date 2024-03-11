package com.hit.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
public class RollbackDaoUtil {

    private static File backUpFile;
    private static String originalFilePath;

    public static void startTaskWithCaution(ICallback callback, String onRollbackError) throws ServiceRequestFailedException {
        try
        {
            callback.execute();
        }
        catch (ServiceRequestFailedException e) {
            try {
                tryCommittingRollback();
            } catch (IOException ex) {
                throw new ServiceRequestFailedException(onRollbackError);
            }
        }
        finally {
            resetRollbackProperties();
        }
    }


    public static void createBackupFile(String originalFilePath) throws IOException {
        String backUpFilePath = changeFileExtension(originalFilePath, ".bak");
        backUpFile = new File(backUpFilePath);
        Files.copy(Paths.get(originalFilePath), backUpFile.toPath(), REPLACE_EXISTING);
    }

    public static void resetRollbackProperties()
    {
        backUpFile.delete();
        backUpFile = null;
        originalFilePath = null;
    }

    private static void tryCommittingRollback() throws IOException {
        Files.copy(backUpFile.toPath(), Paths.get(originalFilePath), REPLACE_EXISTING);
    }

    private static String changeFileExtension(String filePath, String newExtension) {
        int lastDotIndex = filePath.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return filePath.substring(0, lastDotIndex) + newExtension;
        }
        return filePath + newExtension;
    }

}
