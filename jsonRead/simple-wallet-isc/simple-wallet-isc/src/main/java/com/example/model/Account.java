package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity

public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 10, max = 20)
    private String number;

    @NotNull
    private String type;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private double balanceLimit;

    private LocalDate openDate;

    @Column
    @Digits(integer = 20, fraction = 2)
    private BigDecimal balance;

    // Constructors, Getters, Setters, toString() etc.

    public Account() {}

    public Account(String number, String type, double balanceLimit, BigDecimal balance) {
        this.number = number;
        this.type = type;
        //this.customer = customer;
        this.balanceLimit = balanceLimit;
        //this.openDate = openDate;
        this.balance = balance;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getBalanceLimit() {
        return balanceLimit;
    }

    public void setBalanceLimit(double balanceLimit) {
        this.balanceLimit = balanceLimit;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", customer=" + customer +
                ", balanceLimit=" + balanceLimit +
                ", openDate=" + openDate +
                ", balance=" + balance +
                '}';
    }
}
