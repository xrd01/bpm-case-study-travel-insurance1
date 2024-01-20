package de.viadee.bpm.camunda.travelinsuranceprocessapp.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.NO_CLASS_NAME_STYLE;

public class Address {
    private String street;
    private String number;
    private String postCode;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(final String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, NO_CLASS_NAME_STYLE, false, false, true, Object.class);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Address))
            return false;
        Address other = (Address) o;
        boolean addressEqual = Objects.equals(this.city, other.city) &&
                Objects.equals(this.country, other.country) &&
                Objects.equals(this.number, other.number) &&
                Objects.equals(this.postCode, other.postCode) &&
                Objects.equals(this.street, other.street);
        return addressEqual;
    }
}
