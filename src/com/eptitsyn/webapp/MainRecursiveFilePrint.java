package com.eptitsyn.webapp;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

public class MainRecursiveFilePrint {

    static void printDirectory(File dir, int level) {
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                System.out.println(
                    String.join("", Collections.nCopies(level, " ")) + file.getName());
                printDirectory(file, level + 1);
            }
        }
    }

    public static void main(String[] args) {
        printDirectory(new File(System.getProperty("user.dir")), 0);
    }
}
