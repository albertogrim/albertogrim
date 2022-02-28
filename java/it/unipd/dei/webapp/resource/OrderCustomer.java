package it.unipd.dei.webapp.resource;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.UUID;

public class OrderCustomer {

    private final UUID id;
    private String p_method;
    private float tot_price;
    private String delivery_mode;
    private boolean is_cancelled;
    private String address;
    private String invoice;
    private String customer;
    private String status;
    private UUID productID;


    public OrderCustomer(UUID id, float tot_price, String address, String p_method, String delivery_mode, boolean is_cancelled,
                         String invoice, String customer, String status) {
        this.id = id;
        this.tot_price = tot_price;
        this.address = address;
        this.p_method = p_method;
        this.delivery_mode = delivery_mode;
        this.is_cancelled = is_cancelled;
        this.invoice = invoice;
        this.customer = customer;
        this.status = status;
    }

    public OrderCustomer(UUID id, float tot_price, String address, String p_method, String delivery_mode, boolean is_cancelled,
                         String invoice, String customer, String status, UUID productID) {
        this.id = id;
        this.tot_price = tot_price;
        this.address = address;
        this.p_method = p_method;
        this.delivery_mode = delivery_mode;
        this.is_cancelled = is_cancelled;
        this.invoice = invoice;
        this.customer = customer;
        this.status = status;
        this.productID = productID;
    }

    public OrderCustomer(UUID id, String status, Boolean is_cancelled){
        this.id = id;
        this.status = status;
        this.is_cancelled = is_cancelled;
    }

    public UUID getId() { return id; }

    public String getP_method() { return p_method; }

    public float getTot_price() { return tot_price; }

    public String getDelivery_mode() { return delivery_mode; }

    public boolean isCancelled() { return is_cancelled; }

    public String getAddress() { return address; }

    public String getInvoice() { return invoice; }

    public String getCustomer() { return customer; }

    public String getStatus() {return status;}

    public UUID getProductID() {return productID;}

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("id", this.id);
        jobj.put("tot_price", this.tot_price);
        jobj.put("address", this.address);
        jobj.put("p_method", this.p_method);
        jobj.put("delivery_mode", this.delivery_mode);
        jobj.put("is_cancelled", this.is_cancelled);
        jobj.put("invoice", this.invoice);
        jobj.put("customer", this.customer);
        jobj.put("status", this.status);
        return jobj;
    }

    public JSONObject toJSONID() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("id", this.id);
        jobj.put("tot_price", this.tot_price);
        jobj.put("address", this.address);
        jobj.put("p_method", this.p_method);
        jobj.put("delivery_mode", this.delivery_mode);
        jobj.put("is_cancelled", this.is_cancelled);
        jobj.put("invoice", this.invoice);
        jobj.put("customer", this.customer);
        jobj.put("status", this.status);
        jobj.put("productID", this.productID);
        return jobj;
    }
}
