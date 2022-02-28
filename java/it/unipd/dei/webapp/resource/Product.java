package it.unipd.dei.webapp.resource;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.UUID;

public class Product {

    private final UUID p_code;
    private float price;
    private String fabric;
    private String pictures;
    private String size;
    private String color;
    private String description;
    private String model;
    private Boolean out_of_stock;

    public Product( UUID p_code, float price, String fabric, String pictures, String size, String color, String description,
                    String model, Boolean out_of_stock){
        this.p_code = p_code;
        this.price = price;
        this.fabric = fabric;
        this.pictures = pictures;
        this.size = size;
        this.color = color;
        this.description = description;
        this.model = model;
        this.out_of_stock = out_of_stock;
    }

    public UUID getP_code() { return p_code; }
    public float getPrice() { return price; }
    public String getFabric() { return fabric; }
    public String getPictures() { return pictures; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public String getDescription() { return description; }
    public String getModel() { return model; }
    public Boolean getOut_of_stock() { return out_of_stock; }

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("p_code", this.p_code);
        jobj.put("price", this.price);
        jobj.put("fabric", this.fabric);
        jobj.put("pictures", this.pictures);
        jobj.put("size", this.size);
        jobj.put("color", this.color);
        jobj.put("description", this.description);
        jobj.put("model", this.model);
        jobj.put("out_of_stock", this.out_of_stock);
        return jobj;
    }

}
