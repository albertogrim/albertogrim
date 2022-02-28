package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Appointment;
import it.unipd.dei.webapp.utils.DataSourceProvider;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import java.util.UUID;

public class AppointmentDAO extends AbstractDAO {

    public static Appointment getAppointment(UUID id) throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        Appointment c = null;

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "SELECT id, description, accepted, schedule,  customer FROM Tailor_feda.appointment WHERE id=?::uuid;";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(id));
            result = stmnt.executeQuery();

            if (result.next()) {
                c = new Appointment(UUID.fromString(result.getString(1)),
                        result.getString(2),
                        result.getBoolean(3),
                        result.getTimestamp(4),
                        result.getString(5));
            }
            return c;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }

    }

    public static int createAppointment(Appointment c) throws NamingException, SQLException {

        Connection conn = null;
        PreparedStatement stmnt = null;

        if (getAppointment(c.getId()) != null) {
            return -2;
        }
        if (c.getSchedule() == null) {
            return -3;
        }
        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "INSERT INTO Tailor_feda.appointment VALUES (?::uuid, ?, ?, ?, ?);";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(c.getId()));
            stmnt.setString(2, c.getDescription());
            stmnt.setBoolean(3, c.getAccepted());
            stmnt.setTimestamp(4, c.getSchedule());
            stmnt.setString(5, c.getCustomer());

            System.out.println("Statement query: " + stmnt);

            int result = stmnt.executeUpdate();

            System.out.println("Query result: " + result);

            if (result == 1) {
                return 0;
            } else {
                return -1;
            }
        } finally {
            cleaningOperations(stmnt, conn);
        }

    }

    public static int deleteAppointment(UUID id) throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement stmnt = null;

        if (getAppointment(id) == null) {
            return -2;
        }
        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "DELETE FROM Tailor_feda.appointment WHERE id=?::uuid;";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(id));
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

    public static List getAppointmentNotAccepted() throws SQLException, NamingException {

        PreparedStatement stmnt = null;
        ResultSet result = null;
        Connection conn= null;
        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "SELECT id, description, accepted, schedule,  customer FROM Tailor_feda.appointment " +
                    "WHERE accepted=FALSE;";
            stmnt = conn.prepareStatement(query);

            result = stmnt.executeQuery();
            List<JSONObject> naa = new ArrayList<>();

            while (result.next()) {
                naa.add(new Appointment(UUID.fromString(result.getString(1)),
                        result.getString(2),
                        result.getBoolean(3),
                        result.getTimestamp(4),
                        result.getString(5)).toJSON());
            }
            stmnt.close();
            result.close();
            conn.close();
            return naa;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }

    }

    public static int updateAppointment(UUID id, Appointment c) throws SQLException, NamingException {

        PreparedStatement stmnt = null;
        Connection conn= null;

        if (getAppointment(id) == null) {
            return -2;
        }
        if (c.getSchedule() == null) {
            return -3;
        }
        try {
            conn = DataSourceProvider.getDataSource().getConnection();

            String query = "UPDATE Tailor_feda.appointment SET id=?::uuid, schedule=?, description=?, accepted=?, customer=? " +
                    "WHERE id=?::uuid;";

            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, String.valueOf(c.getId()));

            stmnt.setString(2, c.getDescription());
            stmnt.setBoolean(3, c.getAccepted());
            stmnt.setTimestamp(4, c.getSchedule());
            stmnt.setString(5, c.getCustomer());
            stmnt.setString(6, String.valueOf(id));

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

    public static List getAppointmentsList() throws SQLException, NamingException {

        PreparedStatement stmnt = null;
        ResultSet result = null;
        Connection conn = null;
        List<JSONObject> appList = new ArrayList<>();

        try {
            conn = DataSourceProvider.getDataSource().getConnection();
            String query = "SELECT appointment, description, accepted, schedule,  A.customer " +
                    "FROM Tailor_feda.book AS B INNER JOIN Tailor_feda.appointment AS A " +
                    "ON B.appointment=A.id";

            stmnt = conn.prepareStatement(query);
            result = stmnt.executeQuery();

            while (result.next()) {
                appList.add(new Appointment(UUID.fromString(result.getString(1)),
                        result.getString(2),
                        result.getBoolean(3),
                        result.getTimestamp(4),
                        result.getString(5)).toJSON());
            }
            return appList;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

    public static List<UUID> getAppointmentByIdUUID(boolean ordering_type) throws SQLException, NamingException {
        /*
        Return the whole list of appointment sorted by Id in ascending (ordering_type=1) or descending order (ordering_type=0)
        */

        Connection conn = null;
        PreparedStatement stmnt = null;
        ResultSet result = null;
        List<UUID> appointment_list = new ArrayList<>();

        try {

            conn = DataSourceProvider.getDataSource().getConnection();

            if (ordering_type) { // ascending order is default type of ordering
                String query = "SELECT * FROM Tailor_feda.appointment ORDER BY id";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();

            } else { // descending order
                String query = "SELECT * FROM Tailor_feda.appointment ORDER BY id DESC";
                stmnt = conn.prepareStatement(query);
                result = stmnt.executeQuery();
            }

            while (result.next()) {
                appointment_list.add((UUID) result.getObject(1));
            }
            return appointment_list;
        } finally {
            cleaningOperations(stmnt, result, conn);
        }
    }

}