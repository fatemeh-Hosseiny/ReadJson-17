package com.example.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CsvProcessor {

    private ReadCsv readCsv;
    private CustomerValidator customerValidator;
    private AccountValidator accountValidator;

    // Constructor
    public CsvProcessor() {
        readCsv = new ReadCsv();
        customerValidator = new CustomerValidator();
        accountValidator = new AccountValidator();
    }


    // Method to process Customer CSV file using multithreading
    public void processCustomerCsv(String customerFilePath) {
        List<String[]> customerData = readCsv.readCsv(customerFilePath);

        // Create a thread pool with fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Iterate through each row of the CSV and validate in a separate thread
        for (String[] row : customerData) {
            executorService.submit(() -> {
                customerValidator.validate(row);

                if (customerValidator.hasErrors()) {
                    System.out.println("Errors for Customer ID: " + row[0]);
                    customerValidator.getFieldErrors().forEach((field, error) ->
                            System.out.println("Field: " + field + " - Error: " + error));
                } else {
                    Customer validatedCustomer = customerValidator.getValidatedCustomer();
                    System.out.println("Valid Customer: " + validatedCustomer);
                }
            });
        }

        // Shutdown executor and wait for all tasks to finish
        shutdownExecutorService(executorService);
    }

    // Method to process Account CSV file using multithreading
    public void processAccountCsv(String accountFilePath) {
        List<String[]> accountData = readCsv.readCsv(accountFilePath);

        // Create a thread pool with fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Iterate through each row of the CSV and validate in a separate thread
        for (String[] row : accountData) {
            executorService.submit(() -> {
                accountValidator.validate(row);

                if (accountValidator.hasErrors()) {
                    System.out.println("Errors for Account Number: " + row[1]);
                    accountValidator.getFieldErrors().forEach((field, error) ->
                            System.out.println("Field: " + field + " - Error: " + error));
                } else {
                    Account validatedAccount = accountValidator.getValidatedAccount();
                    System.out.println("Valid Account: " + validatedAccount);
                }
            });
        }

        // Shutdown executor and wait for all tasks to finish
        shutdownExecutorService(executorService);
    }

    // Utility method to shutdown executor service gracefully
    private void shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor service did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
