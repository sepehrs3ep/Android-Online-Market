package project.com.maktab.onlinemarket.model.customer;

import com.google.gson.annotations.SerializedName;

public class Customer {

    private long id;

    @SerializedName("first_name")
    private String name;
    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    private Billing billing;

    public Customer(long id, String name, String lastName, String email, Billing billing) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.billing = billing;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Billing getBilling() {
        return billing;
    }
}
