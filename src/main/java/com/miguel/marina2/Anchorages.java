package com.miguel.marina2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Document(collection = "anchorages")
public class Anchorages implements Serializable {

    @Id
    private Integer id;
    private Character pierType;
    private Double length;
    private Double price;
    private Integer places;
    private List<Vessel> vessels;

    public Anchorages() {
    }

    public Anchorages(Integer id, Character pierType, Double length, Double price, Integer places) {
        this.id = id;
        this.pierType = pierType;
        this.length = length;
        this.price = price;
        this.places = places;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getPierType() {
        return pierType;
    }

    public void setPierType(Character pierType) {
        this.pierType = pierType;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anchorages that = (Anchorages) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Anchorages: " + pierType + '-' + + length + "m" ;
    }
}
