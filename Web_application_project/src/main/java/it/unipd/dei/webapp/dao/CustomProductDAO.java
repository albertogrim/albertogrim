package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.CustomProduct;
import it.unipd.dei.webapp.utils.DataSourceProvider;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomProductDAO extends AbstractDAO{

    public static int deleteCustomProduct(UUID id) throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement stmnt = null;
        String query = "DELETE FROM Tailor_feda.custom_product WHERE id=?::uuid";

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

    public static CustomProduct getCustomProduct(UUID id) throws SQLException, NamingException {

        CustomProduct customProduct = null;
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT id, model, work_time, fabric, work_type, size, color, customer" +
                    " FROM Tailor_feda.custom_product WHERE id=?::uuid";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(id));
            result = stmnt.executeQuery();

            if (result.next()) {
                customProduct = new CustomProduct((java.util.UUID) result.getObject(1),
                        result.getString(2),
                        result.getInt(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8));
            }

            return customProduct;

        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static int insertCustomProduct(CustomProduct customProduct) throws NamingException, SQLException {

        Connection conn = null;
        PreparedStatement stmnt = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "INSERT INTO Tailor_feda.custom_product VALUES (?::uuid, ?, ?, ?, ?::Tailor_feda.WORKTYPE, ?, ?::Tailor_feda.COLOR, ?)";

            stmnt = conn.prepareStatement(query);

            stmnt.setString(1, customProduct.getID().toString());
            stmnt.setString(2, customProduct.getModel());
            stmnt.setInt(3, customProduct.getWork_time());
            stmnt.setString(4, customProduct.getFabric());
            stmnt.setString(5, customProduct.getWork_type());
            stmnt.setString(6, customProduct.getSize());
            stmnt.setString(7, customProduct.getColor());
            stmnt.setString(8, customProduct.getCustomer());

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

    public static List<UUID> getOrderByIdUUID(boolean ordering_type) throws SQLException, NamingException {
        /*
        Return the whole list of custom product sorted by Id in ascending (ordering_type=1) or descending order (ordering_type=0)
        */

        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<UUID> customProduct_list = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            if (ordering_type) { // ascending order is default type of ordering
                String query = "SELECT * FROM Tailor_feda.custom_product ORDER BY id";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();

            } else { // descending order
                String query = "SELECT * FROM Tailor_feda.custom_product ORDER BY id DESC";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();
            }

            while (result.next()) {
                customProduct_list.add((UUID) result.getObject(1));
            }
            return customProduct_list;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }
}
