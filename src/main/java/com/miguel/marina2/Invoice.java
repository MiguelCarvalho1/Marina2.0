package com.miguel.marina2;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Invoice {
    private String clientName;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private double anchorageCostPerHour;

    public Invoice(String clientName, Date entryDate, Date exitDate, double anchorageCostPerHour) {
        this.clientName = clientName;
        this.entryDate = convertToLocalDateTime(entryDate);
        this.exitDate = convertToLocalDateTime(exitDate);
        this.anchorageCostPerHour = anchorageCostPerHour;
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public double calculateTotalCost() {
        long durationInHours = DateUtil.calculateDurationInHours(entryDate, exitDate);
        return durationInHours * anchorageCostPerHour;
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("Invoice for: ").append(clientName).append("\n");
        invoice.append("Entry Date: ").append(DateUtil.formatDateTime(entryDate, "yyyy-MM-dd HH:mm:ss")).append("\n");
        invoice.append("Exit Date: ").append(DateUtil.formatDateTime(exitDate, "yyyy-MM-dd HH:mm:ss")).append("\n");
        invoice.append("Total Anchorage Cost: $").append(calculateTotalCost());
        return invoice.toString();
    }
}
