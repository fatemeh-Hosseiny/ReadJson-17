package com.example.model;

import com.example.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
@Service
public class CustomerValidator {
    private Map<String, String> fieldErrors = new HashMap<>();
    private boolean hasError = false;
    private Customer validatedCustomer;

    @Autowired
    private CustomerRepo customerRepo;


    public boolean hasErrors() {
        return hasError;
    }

    public Customer getValidatedCustomer() {
        return validatedCustomer;
    }

    public Customer validate(String[] fields) {
        fieldErrors.clear();

        // Validate Customer ID (Index 0)
        Long customerId = validateCustomerId(fields[0]);

        // Validate Name (Index 1)
        String name = validateName(fields[1]);

        // Validate Surname (Index 2)
        String surname = validateSurname(fields[2]);

        // Validate Address (Index 3)
        String address = validateAddress(fields[3]);

        // Validate Zip Code (Index 4)
        String zipCode = validateZipCode(fields[4]);

        // Validate National ID (Index 5)
        String nationalId = validateNationalId(fields[5]);

        // Validate Birth Date (Index 6)
        LocalDate birthDate = validateBirthDate(fields[6]);

        // If no errors, return a valid Customer object
        if (fieldErrors.isEmpty()) {
            return new Customer(name, surname, address, zipCode, nationalId, birthDate);
        } else {
            // Return null if there are validation errors
            System.out.println("Validation errors: " + fieldErrors);
            return null;
        }
    }

    private Long validateCustomerId(String customerIdStr) {
        try {
            Long customerId = Long.parseLong(customerIdStr.trim());
            if (customerId < 1) {
                fieldErrors.put("Customer ID", "Customer ID must be greater than or equal to 1.");
            }
            return customerId;
        } catch (NumberFormatException e) {
            fieldErrors.put("Customer ID", "Customer ID must be a valid number.");
            return null;
        }
    }

    private String validateName(String name) {
        name = name.trim();
        if (name.isEmpty()) {
            fieldErrors.put("Name", "Name must not be empty.");
        } else if (name.length() < 1) {
            fieldErrors.put("Name", "Name must contain at least 1 character.");
        }
        return name;
    }

    private String validateSurname(String surname) {
        surname = surname.trim();
        if (surname.isEmpty()) {
            fieldErrors.put("Surname", "Surname must not be empty.");
        } else if (surname.length() < 1) {
            fieldErrors.put("Surname", "Surname must contain at least 1 character.");
        }
        return surname;
    }

    private String validateAddress(String address) {
        address = address.trim();
        if (address.isEmpty() || address.length() < 3) {
            fieldErrors.put("Address", "Address must contain at least 3 characters.");
        }
        return address;
    }

    private String validateZipCode(String zipCode) {
        zipCode = zipCode.trim();
        if (zipCode.isEmpty() || zipCode.length() < 3) {
            fieldErrors.put("Zip Code", "Zip Code must contain at least 3 characters.");
        }
        return zipCode;
    }

    private String validateNationalId(String nationalId) {
        nationalId = nationalId.trim();
        if (!nationalId.matches("\\d{10}")) {
            fieldErrors.put("National ID", "National ID must be exactly 10 digits.");
        }
        return nationalId;
    }

    private LocalDate validateBirthDate(String birthDateStr) {
        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr.trim());
            if (birthDate.isAfter(LocalDate.now())) {
                fieldErrors.put("Birth Date", "Birth Date must not be in the future.");
            } else if (birthDate.getYear() < 1995) {
                fieldErrors.put("Birth Date", "Year must be greater than or equal to 1995.");
            }
            return birthDate;
        } catch (DateTimeParseException e) {
            fieldErrors.put("Birth Date", "Birth Date must be in a valid format (yyyy-mm-dd).");
            return null;
        }
    }

    // Retrieves all field errors encountered during validation
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
