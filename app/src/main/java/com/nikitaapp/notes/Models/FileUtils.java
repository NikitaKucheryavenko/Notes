package com.nikitaapp.notes.Models;

import java.io.File;

public class FileUtils {
    public static void deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
