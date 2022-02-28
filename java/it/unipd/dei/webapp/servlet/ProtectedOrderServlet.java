package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.OrderCustomerDAO;
import it.unipd.dei.webapp.resource.OrderCustomer;
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

public class ProtectedOrderServlet extends AbstractDatabaseServlet {

    final Logger logger = LogManager.getLogger(EmployeeServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        try {

            String op = req.getParameter("operation");

            if (op == null) {

                String customer = req.getParameter("customer");

                System.out.println(customer);

                if (customer == null || customer.equals("")) {
                    ErrorCode ec = ErrorCode.EMAIL_MISSING;
                    res.setStatus(ec.getHTTPCode());
                    writeError(res, ec);
                } else {

                    //Costruisco la tabella con tutti gli ordini di un customer
                    List<OrderCustomer> orderCustomerList = OrderCustomerDAO.getCustomerOrderList(customer);
                    //System.out.println("Order customer List: " + orderCustomerList);
                    JSONObject resJSON = new JSONObject();
                    resJSON.put("data", orderCustomerList);
                    res.setContentType("application/json");
                    res.getWriter().write(resJSON.toString());
                }

            } else if (op.equals("list")) {


                // Questo fornisce la lista degli UUID degli ordini
                /*
                List<UUID> ListID = OrderCustomerDAO.getOrderByIdUUID(true);
                JSONObject resJSONID = new JSONObject();
                resJSONID.put("order_list_ID", ListID);
                res.setContentType("application/json");
                res.getWriter().write(resJSONID.toString());

                 */

                List<OrderCustomer> oList = OrderCustomerDAO.getOCnotCancelled();
                for (int i = 0; i < oList.size(); i++){
                    System.out.println("ProductID " + oList.get(i).getProductID());
                }

                JSONObject resJSON = new JSONObject();
                resJSON.put("order_list", oList);

                System.out.println("RESJSON: " + resJSON);

                res.setContentType("application/json");
                res.getWriter().write(resJSON.toString());

            }
            else {
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
            }

        } catch (NamingException | SQLException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace:", e);
        }
    }

    public  void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        deletionOperations(req, res);
    }

    public void deletionOperations(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {

            String idString = req.getParameter("id");

            if(idString==null){
                writeError(res, ErrorCode.EMAIL_MISSING);
            }
            else {
                int deletionResult = OrderCustomerDAO.deleteOrderContain(UUID.fromString(idString));
                int deletionResultOrderCustomer = OrderCustomerDAO.deleteOrderOrderCustomer(UUID.fromString(idString));

                if (deletionResult == 0 && deletionResultOrderCustomer == 0) {
                    res.setStatus(HttpServletResponse.SC_OK);
                    JSONObject resJSON = new JSONObject();
                    resJSON.put("success", "order removed");
                    res.setContentType("application/json");
                    res.getWriter().write(resJSON.toString());
                } else if (deletionResult == -1) {
                    writeError(res, ErrorCode.DELETED_USER_NOT_PRESENT);
                } else {
                    writeError(res, ErrorCode.INTERNAL_ERROR);
                    logger.error("problem with deleting order " + idString);
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
    //TODO: se non funziona così faccio un rimando ad una pagina jsp dove caricare il nuovo ordine

    public void updateOperations(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try{


            String status = req.getParameter("status");
            String idString = req.getParameter("id");
            String is_cancelled = req.getParameter("is_cancelled");

            OrderCustomer oc = new OrderCustomer(UUID.fromString(idString), status, Boolean.valueOf(is_cancelled));

            int updateResult = OrderCustomerDAO.updateOrder(oc, status, Boolean.valueOf(is_cancelled));

            if (updateResult==0){
                res.setStatus(HttpServletResponse.SC_OK);
            } else{
                writeError(res, ErrorCode.INTERNAL_ERROR);
            }

        } catch (SQLException | NamingException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            logger.error("stacktrace:", e);
        }
    }
}
