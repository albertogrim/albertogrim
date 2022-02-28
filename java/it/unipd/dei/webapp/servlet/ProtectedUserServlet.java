package it.unipd.dei.webapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import it.unipd.dei.webapp.resource.Employee;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.dao.EmployeeDAO;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProtectedUserServlet extends AbstractDatabaseServlet{

    final Logger logger = LogManager.getLogger(EmployeeServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        try {

            String op = req.getParameter("operation");

            if (op == null){

                String email = req.getParameter("email");

                if (email == null || email.equals("")) {
                    ErrorCode ec = ErrorCode.EMAIL_MISSING;
                    res.setStatus(ec.getHTTPCode());
                    writeError(res, ec);
                } else {
                    res.setContentType("application/json");
                    JSONObject resJSON = new JSONObject();
                    resJSON.put("data", EmployeeDAO.getEmployee(email).toJSON());
                    res.getWriter().write(resJSON.toString());
                }
            } else if (op.equals("list")){

                List<String> uList= EmployeeDAO.getEmployeeEmailList();
                JSONObject resJSON = new JSONObject();
                resJSON.put("emails_list", uList);
                res.setContentType("application/json");
                res.getWriter().write(resJSON.toString());
            } else{
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
            }

        } catch (NamingException | SQLException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace:", e);
        }

    }

    @Override
    public  void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        System.out.println("Entriamo nel metodo doDelete");

        deletionOperations(req, res);
    }

    public void deletionOperations(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {

            System.out.println("Entriamo nel metodo deletionOperation");

            String email = req.getParameter("email");

            System.out.println("Email dell'employee da cancellare: " + email);

            if(email==null){
                writeError(res, ErrorCode.EMAIL_MISSING);
            }
            else {

                System.out.println("Email non vuota, procediamo con la richeista di cancellazione");

                int deletionResult = EmployeeDAO.DeleteEmployee(email);

                System.out.println("Risultato della query di cancellazione");

                if (deletionResult == 0) {

                    System.out.println("Risultato ok!");

                    res.setStatus(HttpServletResponse.SC_OK);
                    JSONObject resJSON = new JSONObject();
                    resJSON.put("success", "user removed");
                    res.setContentType("application/json");
                    res.getWriter().write(resJSON.toString());

                    System.out.println("Eliminato");

                } else if (deletionResult == -1) {
                    writeError(res, ErrorCode.DELETED_USER_NOT_PRESENT);
                } else {
                    writeError(res, ErrorCode.INTERNAL_ERROR);
                    logger.error("problem with deleting user " + email);
                }
            }
        } catch (IOException | NamingException | SQLException e){
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace:", e);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String op = req.getParameter("operation");
        switch (op){
            case "update":
                updateOperations(req, res);
                break;
            default:
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
                logger.warn("user requested operation "+op);
        }
    }
    //TODO: check if update operation is necessary and modify it

    public void updateOperations(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try{

            String email = req.getParameter("email");
            String first_name = req.getParameter("name");
            String last_name = req.getParameter("surname");
            String phone = req.getParameter("phone");

            if(email==null){
                writeError(res, ErrorCode.EMAIL_MISSING);
            }
            else if(first_name==null|| first_name.equals("")||
                    last_name==null|| last_name.equals("")){
                writeError(res, ErrorCode.EMPTY_INPUT_FIELDS);
            } else {

                    Employee new_e = new Employee(email, first_name, last_name, phone);
                    int updateResult = EmployeeDAO.UpdateEmployee(email, new_e);
                    if (updateResult==0){
                        res.setStatus(HttpServletResponse.SC_OK);
                    } else{
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                    }
                }

        } catch (SQLException | NamingException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace:", e);
        }
    }

}
