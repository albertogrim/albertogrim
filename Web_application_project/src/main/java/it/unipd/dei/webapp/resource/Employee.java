package it.unipd.dei.webapp.resource;

import org.json.JSONException;
import org.json.JSONObject;

public class Employee {

    private String email;
    private String name;
    private String surname;
    private String password;
    private String phone;
    private String role;

    public Employee(String email, String name, String surname, String password, String phone, String role) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public Employee(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Employee(String email, String name, String surname, String phone){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }


    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getPassword() { return password; }

    public String getPhone() { return phone; }

    public String getRole() { return role; }

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("email", this.email);
        jobj.put("name", this.name);
        jobj.put("surname", this.surname);
        jobj.put("password", this.password);
        jobj.put("phone", this.phone);
        jobj.put("role", this.role);
        return jobj;
    }

}
