package com.fowler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ApplicationUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtils.class);

    public static void runApplication(String applicationFilePath) throws IOException {
        File application = new File(applicationFilePath);
        String applicationName = application.getName();
        if (!isProcessRunning(applicationName)) {
            Desktop.getDesktop().open(application);
        }
    }

    public static boolean isProcessRunning(String processName) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("tasklist.exe");
        Process process = builder.start();
        String tasksList = toString(process.getInputStream());
        return tasksList.contains(processName);
    }

    private static String toString(InputStream is) {
        Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
        String str = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return str;
    }

    public static void main(String[] args) {
        try {
            String path = "C:\\Program Files (x86)\\Microsoft\\Skype for Desktop\\Skype.exe";

            while (true) {
                if (!ApplicationUtils.isProcessRunning("Skype.exe")) {
                    LOGGER.info("Skype not running");
                    ApplicationUtils.runApplication(path);
                }
                else {
                    LOGGER.info("Skype running");
                }
                Thread.sleep(2000);
            }
        }
        catch (Exception ex) {
            LOGGER.error("Exception checking running status of process.", ex);
        }
    }
}
