package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.EmployeeDAO;
import it.unipd.dei.webapp.dao.CustomProductDAO;
import it.unipd.dei.webapp.resource.CustomProduct;
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
import java.util.UUID;

public class ProtectedCustomProductServlet extends AbstractDatabaseServlet {

    final Logger logger = LogManager.getLogger(EmployeeServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        try {

            String op = req.getParameter("operation");
            if (op == null) {

                String idString = req.getParameter("id");

                System.out.println("Selected ID: " + idString);
                if (idString == null || idString.equals("")) {
                    ErrorCode ec = ErrorCode.EMAIL_MISSING;
                    res.setStatus(ec.getHTTPCode());
                    writeError(res, ec);
                } else {
                    res.setContentType("application/json");
                    JSONObject resJSON = new JSONObject();
                    resJSON.put("data", CustomProductDAO.getCustomProduct(UUID.fromString(idString)).toJSON());
                    res.getWriter().write(resJSON.toString());
                }
            } else if (op.equals("list")) {


                List<UUID> oList = CustomProductDAO.getOrderByIdUUID(true);

                System.out.println("Enter the elseif in orderServlet: " + oList);

                JSONObject resJSON = new JSONObject();
                resJSON.put("customProduct_list", oList);
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
