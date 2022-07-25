package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Employee;
import it.unipd.dei.webapp.utils.DataSourceProvider;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class EmployeeDAO  extends AbstractDAO {

    public static int authenticateEmployee(Employee e) throws SQLException, NamingException {
        /*
        authenticateEmployee verify that, given a Employee e, exists a tuple in the
        Tailor_feda.employee table with the email associated to c and the password coincides
        input:
           Employee e: employee to be authenticated
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
            String query = "SELECT email, password FROM Tailor_feda.employee WHERE email=? AND password=md5(?);";


            //prepare the statement
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, e.getEmail());
            stmnt.setString(2, e.getPassword());

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

    public static int DeleteEmployee(String email) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stmnt = null;
        String query = "DELETE FROM Tailor_feda.employee WHERE email=?";
        try {

            conn = DataSourceProvider.getDataSource().getConnection();
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);

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

    public static int UpdateEmployee(String email, Employee employee) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stmnt = null;
        String query = "UPDATE Tailor_feda.employee SET email=?::Tailor_feda.emailaddress, name=?, surname=?, phone=? WHERE email=?::Tailor_feda.emailaddress";

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, employee.getEmail());
            stmnt.setString(2, employee.getName());
            stmnt.setString(3, employee.getSurname());
            stmnt.setString(4, employee.getPhone());
            stmnt.setString(5, email);

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

    public static Employee getEmployee(String email) throws SQLException, NamingException {


        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT email, name, surname, password, phone, role"+
                            " FROM Tailor_feda.employee WHERE email=?";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            result = stmnt.executeQuery();

            Employee employee = null;
            if (result.next()) {
                employee = new Employee(result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6));
            }
            return employee;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static int registerEmployee(Employee employee) throws NamingException, SQLException {

        Connection conn = null;
        PreparedStatement stmnt = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "INSERT INTO Tailor_feda.employee VALUES (?, ?, ?, md5(?), ?, ?)";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, employee.getEmail());
            stmnt.setString(2, employee.getName());
            stmnt.setString(3, employee.getSurname());
            stmnt.setString(4, employee.getPassword());
            stmnt.setString(5, employee.getPhone());
            stmnt.setString(6, employee.getRole());

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

    public static List<String> getEmployeeEmailList() throws SQLException, NamingException {

        PreparedStatement stmnt = null;
        ResultSet result = null;
        Connection conn = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "SELECT email FROM Tailor_feda.employee";
            stmnt = conn.prepareStatement(query);
            result = stmnt.executeQuery();

            List<String> emails = new ArrayList<String>();

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
}

