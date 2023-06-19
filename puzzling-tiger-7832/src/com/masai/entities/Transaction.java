package com.masai.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    private LocalDateTime timestamp;
    private String traderUsername;
    private String stockSymbol;
    private TransactionType type;
    private int quantity;
    private double price;

    public Transaction(LocalDateTime timestamp, String traderUsername, String stockSymbol,
                       TransactionType type, int quantity, double price) {
        this.timestamp = timestamp;
        this.traderUsername = traderUsername;
        this.stockSymbol = stockSymbol;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTraderUsername() {
        return traderUsername;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public TransactionType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "timestamp=" + timestamp +
                ", traderUsername='" + traderUsername + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", type=" + type +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
