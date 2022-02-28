package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.dao.AppointmentDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.Appointment;
import it.unipd.dei.webapp.utils.ErrorCode;
import org.json.JSONException;
import org.json.JSONObject;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AppointmentRestResource extends RestResource {

    public AppointmentRestResource(HttpServletRequest req, HttpServletResponse res) {
        super(req, res);
    }

    public void getAppointmentsList() throws IOException {

        try {
            List<Object> appList = AppointmentDAO.getAppointmentsList();
            if (appList == null) {
                ErrorCode ec = ErrorCode.APPOINTMENT_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                JSONObject resJSON = new JSONObject();
                resJSON.put("appointments-list", appList);
                res.setStatus(HttpServletResponse.SC_OK);
                res.setContentType("application/json");
                res.getWriter().write((new JSONObject()).put("data", resJSON).toString());
            }
        } catch (Throwable e){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }

    }

    public void getAppointment() throws IOException {

        try {
            String op = req.getRequestURI();
            String[] tokens = op.split("/");
            UUID app_id = UUID.fromString(tokens[4]);

            Appointment app = AppointmentDAO.getAppointment(app_id);
            if (app == null) {
                ErrorCode ec = ErrorCode.APPOINTMENT_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                JSONObject appJSON = app.toJSON();
                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().write(appJSON.toString());
            }
        } catch (NamingException | SQLException e){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }

    }


    public void createAppointment() throws IOException {

        try {
            System.out.println("Se tutto è andato bene ci troviamo ora dentro AppointmentRestResource. ");

            final Appointment app = Appointment.fromJSON(req.getInputStream());

            System.out.println(app);

            int insertResult = AppointmentDAO.createAppointment(app);

            System.out.println("il nostro appuntamento è stato creato correttamente? " + insertResult);

            if (insertResult == -3) {
                ErrorCode ec = ErrorCode.DATE_NOT_VALID;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else if (insertResult == -2) {
                ErrorCode ec = ErrorCode.APPOINTMENT_ALREADY_EXIST;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else if (insertResult == -1) {
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {

                System.out.println("Teoricamente a questo punto ci siamo");
                JSONObject resJSON = new JSONObject();
                JSONObject newAppJSON = new JSONObject();

                /*
                Ma è giusto inserire insertResult che è un intero?
                Non bisognerebbe mettere tutto l'appuntamento?
                 */
                newAppJSON.put("appointment-id", insertResult);
                resJSON.put("data", newAppJSON);
                res.setContentType("application/json");
                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().write(resJSON.toString());
            }
        } catch (JSONException e){
            ErrorCode ec = ErrorCode.BADLY_FORMATTED_JSON;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        } catch (SQLException | NamingException e) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }

    }

    public void updateAppointment() throws IOException {

        String op = req.getRequestURI();
        String[] tokens = op.split("/");
        UUID app_id = UUID.fromString(tokens[4]);

        try{
            final Appointment app = Appointment.fromJSON(req.getInputStream());
            int updateResult = AppointmentDAO.updateAppointment(app_id, app);

            if (updateResult == -3) {
                ErrorCode ec = ErrorCode.DATE_NOT_VALID;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else if (updateResult == -2) {
                ErrorCode ec = ErrorCode.APPOINTMENT_DOES_NOT_EXIST;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else if (updateResult == -1) {
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                Message m = new Message("Appointment updated correctly");
                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().write(m.toJSON().toString());
            }
        } catch (JSONException e){
            ErrorCode ec = ErrorCode.BADLY_FORMATTED_JSON;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        } catch (SQLException | NamingException e){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }

    }

    public void deleteAppointment() throws IOException {

        try {
            String op = req.getRequestURI();
            String[] tokens = op.split("/");
            UUID app_id = UUID.fromString(tokens[4]);

            int deletionResult = AppointmentDAO.deleteAppointment(app_id);
            if (deletionResult == -2) {
                ErrorCode ec = ErrorCode.APPOINTMENT_DOES_NOT_EXIST;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else if (deletionResult == -1) {
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                Message m = new Message("Appointment deleted correctly");
                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().write(m.toJSON().toString());
            }
        } catch (NamingException | SQLException e){
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }

    }

    public void getPendingAppointment() throws IOException {

        try {
            List<String> appList = AppointmentDAO.getAppointmentNotAccepted();
            if (appList == null) {
                ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                JSONObject resJSON = new JSONObject();
                resJSON.put("pending-appointments", appList);
                res.setStatus(HttpServletResponse.SC_OK);
                res.setContentType("application/json");
                res.getWriter().write((new JSONObject()).put("data", resJSON).toString());
            }
        } catch (Throwable e) {
            ErrorCode ec = ErrorCode.INTERNAL_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }

    }

}