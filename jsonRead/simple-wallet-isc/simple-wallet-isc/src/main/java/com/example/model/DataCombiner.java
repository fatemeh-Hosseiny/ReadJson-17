package com.example.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class DataCombiner {
    public List<String> combineData(Map<String, Account> validAccounts, Map<String, Customer> validCustomers) {
        return validCustomers.entrySet().stream()
                .filter(entry -> validAccounts.containsKey(entry.getKey()))
                .map(entry -> {
                    Customer customer = entry.getValue();
                    Account account = validAccounts.get(entry.getKey());
                    // Example: CSV format with headers
                    return String.format("%s,%s,%s,%s,%.2f",
                            customer.getName(),
                            customer.getAddress(),
                            account.getNumber(),
                            account.getType(),
                            account.getBalance());
                })
                .collect(Collectors.toList());
    }

}
