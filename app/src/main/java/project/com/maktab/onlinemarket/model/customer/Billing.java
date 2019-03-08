package project.com.maktab.onlinemarket.model.customer;

import com.google.gson.annotations.SerializedName;

public class Billing {

    @SerializedName("first_name")
    private String name;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("address_1")
    private String firstAddress;
    @SerializedName("address_2")
    private String secondAddress;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("postcode")
    private String postCode;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostCode() {
        return postCode;
    }

    public Billing(String name, String lastName, String firstAddress, String secondAddress, String city, String country, String email, String phone, String postCode) {
        this.name = name;
        this.lastName = lastName;
        this.firstAddress = firstAddress;
        this.secondAddress = secondAddress;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phone = phone;
        this.postCode = postCode;
    }
}
