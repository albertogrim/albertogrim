package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.OrderCustomer;
import it.unipd.dei.webapp.utils.DataSourceProvider;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderCustomerDAO extends AbstractDAO {

    private static final Object UUID = null;

    public static int deleteOrderContain(UUID order_customer) throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement stmnt = null;
        String query = "DELETE FROM Tailor_feda.contain WHERE order_customer=?";

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            stmnt = conn.prepareStatement(query);
            stmnt.setObject(1, order_customer);

            int resultDeletion = stmnt.executeUpdate();

            if (resultDeletion == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }
    }

    public static int deleteOrderOrderCustomer(UUID id) throws SQLException, NamingException {

        Connection conn = null;

        PreparedStatement stmnt = null;

        String query = "DELETE FROM Tailor_feda.order_customer WHERE id=?";

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            stmnt = conn.prepareStatement(query);
            stmnt.setObject(1, id);

            int resultDeletion = stmnt.executeUpdate();

            if (resultDeletion == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }
    }

    public static int updateOrder(OrderCustomer order, String status, Boolean is_cancelled) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stmnt = null;
        String query = "UPDATE Tailor_feda.order_customer SET status=?::Tailor_feda.orderstatus, is_cancelled=? WHERE id=?::uuid";


        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, status);
            stmnt.setBoolean(2, is_cancelled);
            stmnt.setObject(3, order.getId());

            int updateResult = stmnt.executeUpdate();

            if (updateResult == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }
    }

    public static OrderCustomer getOrder(UUID id) throws SQLException, NamingException {

        OrderCustomer order = null;
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT id, tot_price, address, p_method, delivery_mode, is_cancelled, invoice,customer,status" +
                    " FROM Tailor_feda.order_customer WHERE id=?::uuid";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(id));
            result = stmnt.executeQuery();

            if (result.next()) {
                order = new OrderCustomer((java.util.UUID) result.getObject(1),
                        result.getFloat(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getBoolean(6),
                        result.getString(7),
                        result.getString(8),
                        result.getString(9));
            }

            return order;

        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }


    public static int insertOrder(OrderCustomer order) throws NamingException, SQLException {

        Connection conn = null;
        PreparedStatement stmnt = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "INSERT INTO Tailor_feda.order_customer VALUES (?::uuid, ?, ?, ?::Tailor_feda.PAYMENTMETHOD, ?::Tailor_feda.DELIVERYMODE, ?, ?, ?::Tailor_feda.EMAILADDRESS, ?::Tailor_feda.ORDERSTATUS)";
            stmnt = conn.prepareStatement(query);

            stmnt.setString(1, order.getId().toString());
            stmnt.setFloat(2, order.getTot_price());
            stmnt.setString(3, order.getAddress());
            stmnt.setString(4, order.getP_method());
            stmnt.setString(5, order.getDelivery_mode());
            stmnt.setBoolean(6, order.isCancelled());
            stmnt.setString(7, order.getInvoice());
            stmnt.setString(8, order.getCustomer());
            stmnt.setString(9, order.getStatus());

            System.out.println("Query " + stmnt);

            int result = stmnt.executeUpdate();

            System.out.println("Risultato della query eseguita da OrderCustomerDAO: " + result);

            if (result == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }
    }

    public static List<OrderCustomer> getOrderById(boolean ordering_type) throws SQLException, NamingException {
        /*
            Return the whole list of OrderCustomer sorted by Id in ascending (ordering_type=1) or descending order (ordering_type=0)
        */

        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<OrderCustomer> order_list = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            if (ordering_type) { // ascending order is default type of ordering
                String query = "SELECT * FROM Tailor_feda.order_customer ORDER BY id";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();

            } else { // descending order
                String query = "SELECT * FROM Tailor_feda.order_customer ORDER BY id DESC";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();
            }

            while (result.next()) {
                order_list.add((OrderCustomer) result.getObject(1));
            }
            return order_list;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static List<UUID> getOrderByIdUUID(boolean ordering_type) throws SQLException, NamingException {
        /*
        Return the whole list of OrderCustomer sorted by Id in ascending (ordering_type=1) or descending order (ordering_type=0)
        */

        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<UUID> order_list = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            if (ordering_type) { // ascending order is default type of ordering
                String query = "SELECT * FROM Tailor_feda.order_customer ORDER BY id";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();

            } else { // descending order
                String query = "SELECT * FROM Tailor_feda.order_customer ORDER BY id DESC";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();
            }

            while (result.next()) {
                order_list.add((UUID) result.getObject(1));
            }
            return order_list;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    //TODO farmi restituire la lista di tutti gli ordini che hanno is_accepted = false
    public static List<OrderCustomer> getOCnotCancelled() throws SQLException, NamingException{
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;

        List<OrderCustomer> orderList = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            String query= "SELECT id, tot_price, address, p_method, delivery_mode, is_cancelled, invoice, customer, status, product " +
                    "FROM Tailor_feda.order_customer as O INNER JOIN Tailor_feda.contain as C " +
                    "ON O.id = C.order_customer";

            stmnt = conn.prepareStatement(query);
            result = stmnt.executeQuery();

            System.out.println("Statement query: " + stmnt);

            while (result.next()) {
                orderList.add(new OrderCustomer((java.util.UUID) result.getObject(1),
                        result.getFloat(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getBoolean(6),
                        result.getString(7),
                        result.getString(8),
                        result.getString(9),
                        (java.util.UUID)result.getObject(10)));
            }
            return orderList;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static List<OrderCustomer> getCustomerOrderList(String customer) throws SQLException, NamingException{
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;

        List<OrderCustomer> order_customer_list = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            String query= "SELECT * FROM Tailor_feda.order_customer AS O INNER JOIN Tailor_feda.customer AS C ON O.customer=C.email where o.customer=?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, customer);
            result = stmnt.executeQuery();

            while (result.next()) {
                order_customer_list.add(new OrderCustomer((java.util.UUID) result.getObject(1),
                        result.getFloat(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getBoolean(6),
                        result.getString(7),
                        result.getString(8),
                        result.getString(9)));
            }
            return order_customer_list;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static List<String> getOrderIDList() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        Connection conn = null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT id FROM Tailor_feda.order_customer";

            stmnt = conn.prepareStatement(query);
            result = stmnt.executeQuery();
            List<String> id = new ArrayList<String>();

            while (result.next()) {
                id.add(result.getString(1));
            }

            stmnt.close();
            result.close();
            conn.close();
            return id;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static int insertContain(UUID orderCustomerID, UUID p_code)throws NamingException, SQLException{

        Connection conn = null;
        PreparedStatement stmnt = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "INSERT INTO Tailor_feda.contain VALUES (?::uuid, ?::uuid)";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(orderCustomerID));
            stmnt.setString(2, String.valueOf(p_code));

            System.out.println("Query " + stmnt);

            int result = stmnt.executeUpdate();

            System.out.println("Risultato di insert contain query fatta da OrderCustomer DAO: " + result);

            if (result == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }
    }

}