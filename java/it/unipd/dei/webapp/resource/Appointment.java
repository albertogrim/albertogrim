package it.unipd.dei.webapp.resource;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.UUID;

public class Appointment {

    private UUID id;
    private final Timestamp schedule;
    private final String description;
    private final boolean accepted;
    private String customer;

    public Appointment(UUID id, String description, boolean accepted, Timestamp schedule,  String customer) {
        this.id = id;
        this.schedule = schedule;
        this.description = description;
        this.accepted = accepted;
        this.customer = customer;
    }

    public Timestamp getSchedule() { return schedule; }

    public boolean getAccepted() { return accepted; }

    public String getDescription() { return description; }

    public UUID getId() { return id; }

    public String getCustomer() { return customer; }

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj  = new JSONObject();
        jobj.put("id", this.id);
        jobj.put("schedule", this.schedule);
        jobj.put("description", this.description);
        jobj.put("accepted", this.accepted);
        jobj.put("customer", this.customer);
        return jobj;
    }

    public static Appointment fromJSON(InputStream inputStream) throws IOException, JSONException {
        String dataString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        JSONObject jobj = new JSONObject(dataString);
        String description = jobj.getString("description");
        boolean accepted = jobj.getBoolean("accepted");
        Timestamp schedule = Timestamp.valueOf(jobj.getString("schedule"));
        UUID id = null;
        String customer = jobj.getString("customer");
        if (jobj.has("id")){
            id = UUID.fromString(jobj.getString("id"));
        }
        return new Appointment(id,  description, accepted,schedule,  customer);
    }

}
