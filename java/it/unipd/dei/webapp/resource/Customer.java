package it.unipd.dei.webapp.resource;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {

    private final String email;
    private String name;
    private String surname;
    private String addresses;
    private String phone;
    private String password;
    private boolean newsletter;
    private String sizes;
    private String lifestyle;
    private String get_to_know;


    public Customer(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Customer(String email, String name, String surname, String password, String phone, String addresses,
                    boolean newsletter, String get_to_know, String sizes, String lifestyle) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.addresses = addresses;
        this.phone = phone;
        this.password = password;
        this.newsletter = newsletter;
        this.sizes = sizes;
        this.lifestyle = lifestyle;
        this.get_to_know = get_to_know;
    }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getAddresses() { return addresses; }

    public String getPhone() { return phone; }

    public String getPassword() { return password; }

    public boolean isNewsletter() { return newsletter; }

    public String getSizes() { return sizes; }

    public String getLifestyle() { return lifestyle; }

    public String getTo_know() { return get_to_know; }

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("email", this.email);
        jobj.put("name", this.name);
        jobj.put("surname", this.surname);
        jobj.put("addresses", this.addresses);
        jobj.put("phone", this.phone);
        jobj.put("password", this.password);
        jobj.put("newsletter", this.newsletter);
        jobj.put("sizes", this.sizes);
        jobj.put("lifestyle", this.lifestyle);
        jobj.put("to_know", this.get_to_know);
        return jobj;
    }

}
