package com.oasis.atm.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single ATM transaction (withdraw/deposit/transfer).
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT
    }

    private Type type;
    private double amount;
    private LocalDateTime dateTime;
    private String note;

    public Transaction(Type type, double amount, String note) {
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.note = note;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        String formatted = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.format("[%s] %-14s | Amount: Rs.%8.2f | %s", formatted, type, amount, note);
    }
}
