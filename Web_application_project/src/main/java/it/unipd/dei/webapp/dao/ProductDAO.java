package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.OrderCustomer;
import it.unipd.dei.webapp.resource.Product;
import it.unipd.dei.webapp.utils.DataSourceProvider;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductDAO extends AbstractDAO {

    public static int deleteProduct(UUID p_code) throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement stmnt = null;
        String query = "DELETE FROM Tailor_feda.product WHERE p_code=?::uuid";

        try {

            conn = DataSourceProvider.getDataSource().getConnection();
            stmnt = conn.prepareStatement(query);
            stmnt.setObject(1, p_code);

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

    public static Product getProduct(UUID p_code) throws SQLException, NamingException {

        Product product = null;
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT p_code, price, fabric, pictures, size, color, description, model, out_of_stock" +
                    " FROM Tailor_feda.product WHERE p_code=?::uuid";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(p_code));
            result = stmnt.executeQuery();

            if (result.next()) {
                product = new Product((java.util.UUID) result.getObject(1),
                        result.getFloat(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8),
                        result.getBoolean(9));
            }

            return product;

        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static int insertProduct(Product product) throws NamingException, SQLException {

        Connection conn = null;
        PreparedStatement stmnt = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "INSERT INTO Tailor_feda.product VALUES (?::uuid, ?, ?, ?, ?, ?::Tailor_feda.COLOR, ?, ?, ?)";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, product.getP_code().toString());
            stmnt.setFloat(2, product.getPrice());
            stmnt.setString(3, product.getFabric());
            stmnt.setString(4, product.getPictures());
            stmnt.setString(5, product.getSize());
            stmnt.setString(6, product.getColor());
            stmnt.setString(7, product.getDescription());
            stmnt.setString(8, product.getModel());
            stmnt.setBoolean(9, product.getOut_of_stock());

            int result = stmnt.executeUpdate();

            if (result == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }
    }

    public static float getPrice(UUID p_code) throws SQLException, NamingException {

        float price = 0;
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT price" +
                    " FROM Tailor_feda.product WHERE p_code=?::uuid";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(p_code));

            result = stmnt.executeQuery();

            if (result.next()) {
                price = result.getFloat(1);
            }

            return price;

        } finally {
            cleaningOperations(stmnt, result, conn);
        }

    }

    public static List<UUID> getOrderByIdUUID(boolean ordering_type) throws SQLException, NamingException {
        /*
        Return the whole list of product sorted by p_code in ascending (ordering_type=1) or descending order (ordering_type=0)
        */

        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<UUID> product_list = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            if (ordering_type) { // ascending order is default type of ordering
                String query = "SELECT p_code, price FROM Tailor_feda.product ORDER BY p_code";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();

            } else { // descending order
                String query = "SELECT p_code, price FROM Tailor_feda.product ORDER BY p_code DESC";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();
            }

            while (result.next()) product_list.add((UUID) result.getObject(1));

            return product_list;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }


    public static List<Product> getAllProducts() throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;

        List<Product> productList = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT * FROM Tailor_feda.product";
            stmnt = conn.prepareStatement(query);
            result = stmnt.executeQuery();

            while (result.next()) {
                productList.add(new Product((java.util.UUID) result.getObject(1),
                        result.getFloat(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8),
                        result.getBoolean(9)));
            }
            return productList;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }
}
