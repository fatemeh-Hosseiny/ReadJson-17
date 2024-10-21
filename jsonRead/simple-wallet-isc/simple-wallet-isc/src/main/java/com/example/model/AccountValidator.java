package com.example.model;

import com.example.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountValidator {

    private Map<String, String> fieldErrors = new HashMap<>();
    private boolean hasError = false;
    private Account validatedAccount;

    @Autowired
    private AccountRepo accountRepo;

    public boolean hasErrors() {
        return hasError;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public Account getValidatedAccount() {
        return validatedAccount;
    }

    // 1. Customer ID Validation
    public void customerIdValidation(String[] cols) {
        try {
            long customerId = Long.parseLong(cols[0]);
            if (customerId <= 0) {
                addError("customerId", "Customer ID must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            addError("customerId", "Invalid Customer ID format.");
        }
    }

    // 2. Account Number Validation
    public void numberValidation(String[] cols) {
        String accountNumber = cols[1].trim();
        if (accountNumber.isEmpty()) {
            addError("accountNumber", "Account number cannot be empty.");
        } else if (!accountNumber.matches("\\d{22}")) {  // Assuming decrypted account number is 22 digits
            addError("accountNumber", "Account number must be 22 digits.");
        }
    }

    // 3. Account Type Validation
    public void typeValidation(String[] cols) {
        String accountType = cols[2].trim();
        if (!accountType.equals("1") && !accountType.equals("2") && !accountType.equals("3")) {
            addError("accountType", "Invalid account type. Must be 1, 2, or 3.");
        }
    }

    // 4. Balance Limit Validation
    public void balanceLimitValidation(String[] cols) {
        try {
            double balanceLimit = Double.parseDouble(cols[3]);
            if (balanceLimit < 0) {
                addError("balanceLimit", "Balance limit must be greater than or equal to 0.");
            }
        } catch (NumberFormatException e) {
            addError("balanceLimit", "Invalid balance limit format.");
        }
    }

    // 5. Open Date Validation
    public void openDateValidation(String[] cols) {
        try {
            String[] dateParts = cols[4].split("-");
            if (dateParts.length != 3) {
                addError("openDate", "Invalid date format. Expected format is yyyy-mm-dd.");
                return;
            }

            LocalDate openDate = LocalDate.parse(cols[4]);
            if (openDate.getYear() < 1995) {
                addError("openDate", "Year must be 1995 or later.");
            } else if (openDate.isAfter(LocalDate.now())) {
                addError("openDate", "Open date cannot be in the future.");
            }
        } catch (DateTimeParseException e) {
            addError("openDate", "Invalid date format.");
        }
    }

    // 6. Balance Validation
    public void balanceValidation(String[] cols, double limit) {
        try {
            double balance = Double.parseDouble(cols[5].trim());
            if (balance < limit) {
                addError("balance", "Balance cannot be below the balance limit.");
            }
        } catch (NumberFormatException e) {
            addError("balance", "Invalid balance format.");
        }
    }

    // Error Management
    private void addError(String fieldName, String errorMessage) {
        fieldErrors.put(fieldName, errorMessage);
        hasError = true;
    }

    // 8. Account Creation
    public void createValidatedAccount(String[] cols) {
        if (!hasError) {
            String accountNumber = cols[1].trim();
            String accountType = cols[2].trim();
            double balanceLimit = Double.parseDouble(cols[3].trim());
            LocalDate openDate = LocalDate.parse(cols[4].trim());
            BigDecimal balance = new  BigDecimal(cols[5].trim());

            validatedAccount = new Account(accountNumber, accountType, balanceLimit, balance);
            validatedAccount.setOpenDate(openDate);
        }
    }

    // Full Validation Method
    public void validate(String[] cols) {
        customerIdValidation(cols);
        numberValidation(cols);
        typeValidation(cols);
        balanceLimitValidation(cols);
        openDateValidation(cols);
        balanceValidation(cols, Double.parseDouble(cols[3].trim()));

        if (!hasErrors()) {
            createValidatedAccount(cols);
        }
    }
}

