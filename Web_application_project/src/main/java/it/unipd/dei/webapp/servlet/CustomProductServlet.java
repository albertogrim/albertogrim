package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.CustomProductDAO;
import it.unipd.dei.webapp.resource.CustomProduct;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.utils.ErrorCode;
import java.io.IOException;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomProductServlet extends AbstractDatabaseServlet{

    public Logger logger;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String operation = req.getParameter("operation");

        if(operation != null && operation.equals("logout")){

            // invalidate user session
            req.getSession().invalidate();
            res.sendRedirect(req.getContextPath() + "/jsp/homepage.jsp");

        }
        else{
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String operation = req.getRequestURI();
        operation = operation.substring(operation.lastIndexOf("customProduct") + 14);


        switch (operation) {

            case "insert/":
                insertCustomProduct(req, res);
                break;

            default:
                writeError(res, ErrorCode.OPERATION_UNKNOWN);

        }
    }

    public void insertCustomProduct(HttpServletRequest req, HttpServletResponse res) throws IOException{
        try{

            //Take the insert parameters
            UUID id = UUID.randomUUID();
            String model = req.getParameter("model");
            String work_time = req.getParameter("work_time");
            String fabric = req.getParameter("fabric");
            String work_type = req.getParameter("work_type");
            String size = req.getParameter("size");
            String color = req.getParameter("color");
            String customer = req.getParameter("customer");


            if(
                    model == null || model.equals("") ||
                    work_time == null || work_time.equals("")||
                    fabric == null || fabric.equals("")||
                    work_type == null || work_type.equals("") ||
                    size == null || size.equals("") ||
                    color == null || color.equals("") ||
                    customer == null || customer.equals("")){

                ErrorCode error = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(error.getHTTPCode());
                Message message = new Message(true, error.getErrorMessage());
                req.setAttribute("message_empty", message);
                req.getRequestDispatcher("/jsp/create-product.jsp").forward(req, res);

            }


            else{

                CustomProduct customProduct = CustomProductDAO.getCustomProduct(id);

                if(customProduct != null){

                    writeError(res, ErrorCode.INTERNAL_ERROR);
                }
                else {
                    customProduct = new CustomProduct(id, model, Integer.valueOf(work_time), fabric, work_type, size, color, customer);

                    int registrationResult = CustomProductDAO.insertCustomProduct(customProduct);

                    if (registrationResult == 0) {
                        Message m = new Message("Your order has been processed correctly!");
                        res.setStatus(HttpServletResponse.SC_OK);
                        req.setAttribute("message", m);
                        req.getRequestDispatcher("/jsp/message-page.jsp").forward(req, res);
                    } else {
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                    }
                }
            }
        } catch(SQLException | NamingException | ServletException ex){
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace: " , ex);
        }

    }


}
