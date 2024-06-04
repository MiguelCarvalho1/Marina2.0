package com.miguel.marina2;

import java.util.Date;

public class Vessel {
    private String registration;
    private String capitanName;
    private int numPassenger;
    private Client clientId;
    private Country countryId;
    private Date entryDate;
    private Date exitDate;
    private Anchorages pierType;

    // Getters e setters para todos os campos

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

    public int getNumPassenger() {
        return numPassenger;
    }

    public void setNumPassenger(int numPassenger) {
        this.numPassenger = numPassenger;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public Anchorages getPierType() {
        return pierType;
    }

    public void setPierType(Anchorages pierType) {
        this.pierType = pierType;
    }

    public Object getNumberDaysStay() {
        return null;
    }
}
