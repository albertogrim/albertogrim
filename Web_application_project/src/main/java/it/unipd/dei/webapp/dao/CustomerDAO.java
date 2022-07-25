package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Customer;
import it.unipd.dei.webapp.utils.DataSourceProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class CustomerDAO extends AbstractDAO{

    public static int authenticateCustomer(Customer c) throws SQLException, NamingException {
        /*
        authenticateCustomer verify that, given a Customer c, exists a tuple in the
        feda_db.customer table with the email associated to c and the password coincides
        input:
           Customer c: customer to be authenticated
        output:
            int authenticationResult: 0 if the email exists and email and password coincide, else -1
         */

        //declare resources to connect to the database
        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            //use the data source provider to get the connection
            conn = DataSourceProvider.getDataSource().getConnection();

            //write the string that can be used by the "prepareStatement" method
            String query = "SELECT email, password FROM Tailor_feda.customer WHERE email=? AND password=md5(?);";

            //prepare the statement
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, c.getEmail());
            stmnt.setString(2, c.getPassword());

            //show hou the prepared statement look like
            System.out.println("Authentication customer query prepared statement: " + stmnt);

            //execute the query
            result = stmnt.executeQuery();

            //handle the results of the query
            int authenticationResult = -1;

            if (result.next()) {
                authenticationResult = 0;
            }
            return authenticationResult;

        } finally {
            //close all the possible resources
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static Customer getCustomer(String email) throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT email, name, surname, password, phone, addresses, newsletter, get_to_know, sizes, lifestyle" +
                           " FROM Tailor_feda.customer WHERE email=?;";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);

            result = stmnt.executeQuery();

            Customer c = null;
            if (result.next()) {
                c = new Customer(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5), result.getString(6),
                        result.getBoolean(7), result.getString(8), result.getString(9), result.getString(10));

            }

            return c;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static int registerCustomer(Customer c) throws NamingException, SQLException {

        Connection conn = null;
        PreparedStatement stmnt = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "INSERT INTO Tailor_feda.customer VALUES (?::Tailor_feda.EMAILADDRESS, ?, ?, md5(?), ?, ?, ?, ?, ?, ?);";

            stmnt = conn.prepareStatement(query);

            stmnt.setString(1, c.getEmail());
            stmnt.setString(2, c.getName());
            stmnt.setString(3, c.getSurname());
            stmnt.setString(4, c.getPassword());
            stmnt.setString(5, c.getPhone());
            stmnt.setString(6, c.getAddresses());
            stmnt.setBoolean(7, c.isNewsletter());
            stmnt.setString(8, c.getTo_know());
            stmnt.setString(9, c.getSizes());
            stmnt.setString(10, c.getLifestyle());

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

    public static int deleteCustomer(String email) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stmnt = null;

        try {
            Customer c = getCustomer(email);
            if (c == null) {
                return -1;
            }

            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "DELETE FROM Tailor_feda.customer WHERE email=?;";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);

            int result = stmnt.executeUpdate();

            if (result == 1) {
                return 0;
            } else {
                return -2;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }
    }

    public static List<String> getCustomerEmailList() throws SQLException, NamingException {
        PreparedStatement stmnt = null;
        ResultSet result = null;
        Connection conn= null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT email FROM Tailor_feda.customer;";

            stmnt = conn.prepareStatement(query);

            result = stmnt.executeQuery();

            List<String> emails = new ArrayList<>();

            while (result.next()) {
                emails.add(result.getString(1));
            }
            stmnt.close();
            result.close();
            conn.close();
            return emails;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static int updateCustomerNoPassword(String email, Customer c) throws SQLException, NamingException {

        /*
        * Allows you to modify the Customer without the need to change the password
        */

        PreparedStatement stmnt = null;
        Connection conn= null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "UPDATE Tailor_feda.customer SET email=?, name=?, surname=?, addresses=?, phone=?, " +
                           "newsletter=?, sizes=?, lifestyle=?, get_to_know=? WHERE email=?;";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, c.getEmail());
            stmnt.setString(2, c.getName());
            stmnt.setString(3, c.getSurname());
            stmnt.setString(4, c.getAddresses());
            stmnt.setString(5, c.getPhone());
            stmnt.setBoolean(7, c.isNewsletter());
            stmnt.setString(8, c.getSizes());
            stmnt.setString(9, c.getLifestyle());
            stmnt.setString(10, c.getTo_know());

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

}
