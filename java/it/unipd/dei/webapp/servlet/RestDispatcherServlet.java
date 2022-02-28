package it.unipd.dei.webapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unipd.dei.webapp.rest.AppointmentRestResource;
import it.unipd.dei.webapp.utils.ErrorCode;
import java.io.IOException;
import java.util.UUID;

public class RestDispatcherServlet extends AbstractDatabaseServlet {

    /*
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String op = req.getRequestURI();

        if (processAppointment(req, res)) {
            return;
        } else {
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
        }
    }

     */

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String operation = req.getRequestURI();
        System.out.println("Operation URI: " + operation);
        operation = operation.substring(operation.lastIndexOf("rest") + 5);
        String value = operation.substring(0,12);
        System.out.println("Operation URI: " + value);


        switch (value) {

            case "appointment/":
                System.out.println("Process appointment part");
                processAppointment(req, res);
                break;

            case "register/":
                System.out.println("Register part");
                //registerUser(req, res);
                break;

            default:
                writeError(res, ErrorCode.OPERATION_UNKNOWN);

        }
    }

    private boolean processAppointment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String op = req.getRequestURI();

        System.out.println("String op in process appointment(): " + op);

        String[] tokens = op.split("/");

        // the requested resource is not an appointment
        if (tokens.length < 4 || !(tokens[3].equals("appointment") )) {
            return false;
        }

        // request uri = TailorShopDEI-1.00/rest/appointment
        if (tokens.length == 4 && tokens[3].equals("appointment")) {
            switch (req.getMethod()) {
                case "GET":
                    new AppointmentRestResource(req, res).getAppointmentsList();
                    break;
                default:
                    writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
            }
        }
        // request uri = TailorShopDEI-1.00/rest/appointment/pending
        else if (tokens.length == 5 && tokens[3].equals("appointment") && tokens[4].equals("pending")) {
            switch (req.getMethod()) {
                case "GET":
                    new AppointmentRestResource(req, res).getPendingAppointment();
                    break;
                default:
                    writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
            }
        }
        // request uri = TailorShopDEI-1.00/rest/appointment/{id}
        else if (tokens.length == 5 && tokens[3].equals("appointment") && !tokens[4].equals("pending")) {

            try{
                UUID test = UUID.fromString(tokens[4]); // check that the parameter is actually an uuid
                System.out.println("UUID take from the URL: " + test.toString());

                switch (req.getMethod()) {
                    case "GET":
                        new AppointmentRestResource(req, res).getAppointment();
                        break;
                    case "PUT":
                        new AppointmentRestResource(req, res).updateAppointment();
                        break;
                    case "DELETE":
                        new AppointmentRestResource(req, res).deleteAppointment();
                        break;
                    case "POST":
                        System.out.println("Dovremmo entrare qua per creare un nuovo appuntamento");
                        new AppointmentRestResource(req, res).createAppointment();
                        break;
                    default:
                        writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
                }
            } catch (NumberFormatException e){
                writeError(res, ErrorCode.WRONG_REST_FORMAT);
            }
        } else {
            return  false;
        }
        return true;
    }

}