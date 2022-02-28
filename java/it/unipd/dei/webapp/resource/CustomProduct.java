package it.unipd.dei.webapp.resource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class CustomProduct {

    private final UUID id;
    private String model;
    private Integer work_time;
    private String fabric;
    private String work_type;
    private String size;
    private String color;
    private String customer;


    public CustomProduct(UUID id, String model, Integer work_time, String fabric, String work_type, String size, String color, String customer){

        this.id = id;
        this.model = model;
        this. work_time = work_time;
        this.fabric = fabric;
        this.work_type = work_type;
        this.size = size;
        this.color = color;
        this.customer = customer;
    }

    public UUID getID() { return id; }
    public String getModel() { return model; }
    public Integer getWork_time() { return work_time; }
    public String getFabric() { return fabric; }
    public String getWork_type() {return work_type; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public String getCustomer() { return customer; }

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("id", this.id);
        jobj.put("model", this.model);
        jobj.put("work_time", this.work_time);
        jobj.put("fabric", this.fabric);
        jobj.put("work_type", this.work_type);
        jobj.put("size", this.size);
        jobj.put("color", this.color);
        jobj.put("customer", this.customer);
        return jobj;
    }


}
