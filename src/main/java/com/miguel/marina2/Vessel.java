package com.miguel.marina2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "vessels")
public class Vessel {
    @Id
    private String registration;
    private String capitanName;
    private Integer numPassenger;
    private LocalDate entryDate;
    private String pierType;
    private LocalDate exitDate;
    private Integer numberDaysStay;
    private Double amountPaid;
    private Double amountPayable;

    private String clientId;



    public Vessel() {
    }

    public Vessel(String registration, String capitanName, Integer numPassenger, LocalDate entryDate, String pierType, LocalDate exitDate, Integer numberDaysStay, Double amountPaid, Double amountPayable, String clientId) {
        this.registration = registration;
        this.capitanName = capitanName;
        this.numPassenger = numPassenger;
        this.entryDate = entryDate;
        this.pierType = pierType;
        this.exitDate = exitDate;
        this.numberDaysStay = numberDaysStay;
        this.amountPaid = amountPaid;
        this.amountPayable = amountPayable;
        this.clientId = clientId;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getCapitanName() {
        return capitanName;
    }

    public void setCapitanName(String capitanName) {
        this.capitanName = capitanName;
    }

    public Integer getNumPassenger() {
        return numPassenger;
    }

    public void setNumPassenger(Integer numPassenger) {
        this.numPassenger = numPassenger;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getPierType() {
        return pierType;
    }

    public void setPierType(String pierType) {
        this.pierType = pierType;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public Integer getNumberDaysStay() {
        return numberDaysStay;
    }

    public void setNumberDaysStay(Integer numberDaysStay) {
        this.numberDaysStay = numberDaysStay;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(Double amountPayable) {
        this.amountPayable = amountPayable;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vessel vessel = (Vessel) o;
        return Objects.equals(registration, vessel.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registration);
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "registration='" + registration + '\'' +
                ", numPassenger=" + numPassenger +
                '}';
    }
}
