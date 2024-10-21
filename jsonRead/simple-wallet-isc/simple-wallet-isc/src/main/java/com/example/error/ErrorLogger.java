package com.example.error;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ErrorLogger {
    private final String errorFilePath;

    public ErrorLogger(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public void logErrors(List<Error> errors) {
        try (FileWriter writer = new FileWriter(errorFilePath, true)) {
            for (Error error : errors) {
                writer.write(error.getFileName() + "," + error.getRecordNumber() + "," +
                        error.getErrorCode() + "," + error.getErrorDescription() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
