package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.CustomerDAO;
import it.unipd.dei.webapp.utils.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ProtectedCustomerServlet extends AbstractDatabaseServlet{

    final Logger logger = LogManager.getLogger(EmployeeServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        try {

            String op = req.getParameter("operation");

            if (op == null) {

                String email = req.getParameter("email");

                if (email == null || email.equals("")) {
                    ErrorCode ec = ErrorCode.EMAIL_MISSING;
                    res.setStatus(ec.getHTTPCode());
                    writeError(res, ec);
                } else {

                    //Con questo riempiamo la tabella con le info sul customer
                    res.setContentType("application/json");
                    JSONObject resJSON = new JSONObject();
                    resJSON.put("data", CustomerDAO.getCustomer(email).toJSON());
                    res.getWriter().write(resJSON.toString());
                }
            } else if (op.equals("list")) {
                List<String> oList = CustomerDAO.getCustomerEmailList();
                JSONObject resJSON = new JSONObject();
                resJSON.put("customer_list", oList);
                res.setContentType("application/json");
                res.getWriter().write(resJSON.toString());
            } else {
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
            }

        } catch (NamingException | SQLException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace:", e);
        }
    }
}
