package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.EmployeeDAO;
import it.unipd.dei.webapp.dao.OrderCustomerDAO;
import it.unipd.dei.webapp.dao.ProductDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.OrderCustomer;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OrderServlet extends AbstractDatabaseServlet {

    final Logger logger = LogManager.getLogger(EmployeeServlet.class);
    String p_code = null;
    float price = 0;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {

            String op = req.getParameter("operation");

            if (op == null) {

                p_code = req.getParameter("p_code");

                if (p_code == null || p_code.equals("")) {
                    ErrorCode ec = ErrorCode.INTERNAL_ERROR;
                    res.setStatus(ec.getHTTPCode());
                    writeError(res, ec);
                } else {
                    res.setContentType("application/json");
                    JSONObject resJSON = new JSONObject();
                    resJSON.put("data", ProductDAO.getProduct(UUID.fromString(p_code)).toJSON());
                    res.getWriter().write(resJSON.toString());
                }
            } else if (op.equals("logout")) {
                req.getSession().invalidate();
                res.sendRedirect(req.getContextPath() + "/jsp/homepage.jsp");

            }else if (op.equals("list")) {

                List<UUID> oList = ProductDAO.getOrderByIdUUID(true);

                JSONObject resJSON = new JSONObject();
                resJSON.put("product_list", oList);
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String op = req.getRequestURI();
        op = op.substring(op.lastIndexOf("order") + 6);

        switch (op) {

            case "insert/":
                System.out.println("Switch = insert");
                insertOrder(req, res);
                break;
            case "update/":
                //updateOrder(req, res);
                break;
            default:
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
                //logger.warn("requested op " + op);
        }

    }

    public String randomString(int x){
        Random rand=new Random();
        String res="";
        for (int i=0;i<x;i++){
            res+=String.valueOf(rand.nextInt(5));
        }
        return res;
    }

    public void insertOrder(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        try {

            String p_code_id = req.getParameter("p_code_product");
            System.out.println("Prezzo ottenuto dalla pagina web: " + p_code_id);

            //float tot_price = ProductDAO.getPrice(UUID.fromString(p_code_id));


            String customer = req.getParameter("customer");
            System.out.println("Prezzo ottenuto dalla pagina web: " + customer);

            //float price = ProductDAO.getPrice(UUID.fromString(p_code));

            UUID id = UUID.randomUUID();

            String tot_price_string = req.getParameter("total_price");

            System.out.println("Prezzo ottenuto dalla pagina web: " + tot_price_string);

            float tot_price = Float.parseFloat(tot_price_string);

            //System.out.println("Tot price: " + tot_price);

            String address = req.getParameter("address");
            System.out.println("Address: " + address);

            String p_method = req.getParameter("p_method");
            System.out.println("Payment method: " + p_method);

            String delivery_mode = req.getParameter("delivery_mode");
            System.out.println("Modalità di spedizione: " + delivery_mode);

            boolean is_cancelled = false;
            System.out.println("Is cancelled: " + is_cancelled);

            String invoice = "AZ"+randomString(5);
            System.out.println("Invoice: " + invoice);

            String status = "Pending";

            OrderCustomer o = new OrderCustomer(id, tot_price, address, p_method, delivery_mode, is_cancelled,
                    invoice, customer, status);

            System.out.println("Ordine che deve essere creato: " + o.getId() );


            if (tot_price < 0  ||
                    address == null || address.equals("") ||
                    p_method == null || p_method.equals("") ||
                    delivery_mode == null || delivery_mode.equals("") ||
                    customer == null || customer.equals("")) {

                System.out.println("Qualche parametro è errato!");

                ErrorCode error = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(error.getHTTPCode());
                Message message = new Message(true, error.getErrorMessage());
                req.setAttribute("message_empty", message);
                req.getRequestDispatcher("/jsp/shop.jsp").forward(req, res);

            } else
            {
                System.out.println("Tutti i parametri sono stati inseriti correttamente nell'ordine \n" +
                        "procediamo con l'update nel DB");

                int updateResult = OrderCustomerDAO.insertOrder(o);
                int insertContainResult = OrderCustomerDAO.insertContain(id, UUID.fromString(p_code_id));

                System.out.println("Inserimento dell'ordine tramite OrderCustomerDAO: " + updateResult);
                System.out.println("Inserimento in contain: " + insertContainResult);


                if (updateResult == 0 && insertContainResult == 0) {

                    System.out.println("Entrambi gli inserimenti sono stati processati correttametne!");

                    Message m = new Message("Your order has been processed correctly!");
                    res.setStatus(HttpServletResponse.SC_OK);
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/jsp/message-page.jsp").forward(req, res);
                }
                else {

                    System.out.println("OPS, qualcosa è andato storto");

                    ErrorCode ec = ErrorCode.EMAIL_MISSING;
                    Message m = new Message(ec.getErrorMessage());
                    req.setAttribute("message", m);
                    req.setAttribute("id", id);
                    res.setStatus(ec.getHTTPCode());
                    req.getRequestDispatcher("/jsp/shop.jsp").forward(req, res);
                }

            }

        } catch (NamingException | SQLException e) {
            writeError(res, ErrorCode.INTERNAL_ERROR);
            //logger.error("stacktrace:", e);
        }
    }


}

