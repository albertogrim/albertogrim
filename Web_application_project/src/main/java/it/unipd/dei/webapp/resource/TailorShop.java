package it.unipd.dei.webapp.resource;

import org.json.JSONException;
import org.json.JSONObject;

public class TailorShop {

    private final int id;
    private String email;
    private String phone;
    private String address;

    public TailorShop(int id, String email, String phone, String address) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() { return id; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("id", this.id);
        jobj.put("email", this.email);
        jobj.put("phone", this.phone);
        jobj.put("address", this.address);
        return jobj;
    }

}
