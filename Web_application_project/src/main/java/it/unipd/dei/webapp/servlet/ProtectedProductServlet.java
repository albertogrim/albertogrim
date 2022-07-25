package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.ProductDAO;
import it.unipd.dei.webapp.resource.Product;
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

public class ProtectedProductServlet extends AbstractDatabaseServlet {

    final Logger logger = LogManager.getLogger(EmployeeServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {

            String op = req.getParameter("operation");

            if (op == null) {

                System.out.println("Errore nella definizione di operation!");

            } else if (op.equals("list")) {
                /*
                Devo restituire un oggetto JSON che mi contiene tutti i prodotti.
                Questi sono restituiti come una lista di oggetti Product
                 */

                List<Product> productList = ProductDAO.getAllProducts();

                System.out.println("Lista dei prodotti richiesta al DB: " + productList);

                JSONObject resJSON = new JSONObject();
                resJSON.put("product_list", productList);
                res.setContentType("application/json");
                res.getWriter().write(resJSON.toString());

            }else if (op.equals("id")) {

                /*
                Devo restituire un oggetto JSON che mi contiene tutti i prodotti.
                Questi sono restituiti come una lista di oggetti Product
                 */
                String idProduct = req.getParameter("id");
                System.out.println("ID prodotto: " + idProduct);

                //Product product = ProductDAO.getProduct(UUID.fromString(idProduct));
                JSONObject resJSON = new JSONObject();
                resJSON.put("product", ProductDAO.getProduct(UUID.fromString(idProduct)).toJSON());
                res.setContentType("application/json");
                res.getWriter().write(resJSON.toString());

                /*
                List<Product> productList = ProductDAO.getAllProducts();

                System.out.println("Lista dei prodotti richiesta al DB: " + productList);

                JSONObject resJSON = new JSONObject();
                resJSON.put("product_list", productList);
                res.setContentType("application/json");
                res.getWriter().write(resJSON.toString());
                */


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

    }

    public void deletionOperations(HttpServletRequest req, HttpServletResponse res) throws IOException {


    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
    //TODO: se non funziona così faccio un rimando ad una pagina jsp dove caricare il nuovo ordine

    public void updateOperations(HttpServletRequest req, HttpServletResponse res) throws IOException {

    }
}
