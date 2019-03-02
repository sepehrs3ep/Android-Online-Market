package project.com.maktab.onlinemarket.network.webservices.add_customer;

public class Customer {

    private long id;

    private String username;

    private String password;

    private String email;

    public Customer(long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
