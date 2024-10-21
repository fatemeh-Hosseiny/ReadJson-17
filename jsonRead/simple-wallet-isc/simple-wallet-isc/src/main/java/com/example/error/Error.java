package com.example.error;

public class Error {
    private String fileName;
    private long recordNumber;
    private String errorCode;
    private String errorDescription;

    public Error(String fileName, long recordNumber, String errorCode, String errorDescription) {
        this.fileName = fileName;
        this.recordNumber = recordNumber;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(long recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
