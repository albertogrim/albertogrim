import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.math.BigDecimal;

/**
 * Lists the products in the catalog
 */
public class HW3_Feda_execute_queries {
	
	/**
	 * The JDBC driver to be used
	 */
	private static final String DRIVER = "org.postgresql.Driver";
	
	/**
	 * The URL of the database to be accessed
	 */
	private static final String DATABASE = "jdbc:postgresql://localhost/feda_db";

	/**
	 * The username for accessing the database
	 */
	private static final String USER = "postgres";

	/**
	 * The password for accessing the database
	 */
	private static final String PASSWORD = "pwd";
	
	/**
	 * The SQL statements to be executed
	 */
	private static final String SQL1 = "SELECT O.customer, CAST(AVG(Tot_price) AS NUMERIC(8,2)) AS average_price " + 
		"FROM Tailor_feda.order_customer AS O " + "INNER JOIN Tailor_feda.customer AS C " + "ON C.email= O.customer " + 
  		"INNER JOIN Tailor_feda.review AS R " + "ON C.Email = R.customer " + "GROUP BY O.customer;";
	private static final String SQL2 = "SELECT email AS shop, people_working FROM Tailor_feda.tailor_shop AS sh INNER JOIN (SELECT shop, COUNT(*) AS people_working " +
		"FROM Tailor_feda.manage AS m " + "INNER JOIN Tailor_feda.work AS w " + "ON m.employee = w.employee " + "GROUP BY shop) AS counting ON sh.id = counting.shop;";

	public static void main(String[] args) {

		// the connection to the DBMS
		Connection con = null;

		// the statement to be executed
		Statement stmt = null;

		// the results of the statement execution
		ResultSet rs = null;
		
		// start time of a statement
		long start;

		// end time of a statement
		long end;


		// "data structures" for the data to be read from the database
		
		// First query
		double average_price = 0;
		String customer = null;
		
		// Second query
		String shop = null;
		int count = 0;
		
		try {
			// register the JDBC driver
			Class.forName(DRIVER);

			System.out.printf("Driver %s successfully registered.%n", DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.printf(
					"Driver %s not found: %s.%n", DRIVER, e.getMessage());

			// terminate with a generic error code
			System.exit(-1);
		}

		try {

			// connect to the database
			start = System.currentTimeMillis();			
			
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD);								
			
			end = System.currentTimeMillis();

			System.out.printf(
					"Connection to database %s successfully established in %,d milliseconds.%n",
					DATABASE, end-start);

			// create the statement to execute the query
			start = System.currentTimeMillis();

			stmt = con.createStatement();

			end = System.currentTimeMillis();

			System.out.printf(
					"Statement successfully created in %,d milliseconds.%n",
					end-start);

			// execute the first query
			start = System.currentTimeMillis();

			rs = stmt.executeQuery(SQL1);

			end = System.currentTimeMillis();

			System.out
					.printf("%nQuery %s successfully executed %,d milliseconds.%n",
							SQL1, end - start);

			System.out
					.printf("%nCompute the average cost of an order by customers who wrote at least one review%n");

			System.out
					.printf("Query results:%n");

			// cycle on the query results and print them
			while (rs.next()) {

				// read the customer
				customer = rs.getString("customer");

				// read the average price
				average_price = rs.getBigDecimal("average_price").doubleValue();

				System.out.printf("- %s, %.2f%n", 
						customer, average_price);

			}
			
			// execute the second query
			start = System.currentTimeMillis();

			rs = stmt.executeQuery(SQL2);

			end = System.currentTimeMillis();

			System.out
					.printf("%nQuery %s successfully executed %,d milliseconds.%n",
							SQL2, end - start);

			System.out
					.printf("%nCount the people that are working for each shop%n");

			System.out
					.printf("Query results:%n");

			// cycle on the query results and print them
			while (rs.next()) {

				// read the customer's name
				shop = rs.getString("shop");

				// read the customer's surname
				count = rs.getInt("people_working");

				System.out.printf("- %s, %d%n", 
						shop, count);
			}
		} catch (SQLException e) {
			System.out.printf("Database access error:%n");

			// cycle in the exception chain
			while (e != null) {
				System.out.printf("- Message: %s%n", e.getMessage());
				System.out.printf("- SQL status code: %s%n", e.getSQLState());
				System.out.printf("- SQL error code: %s%n", e.getErrorCode());
				System.out.printf("%n");
				e = e.getNextException();
			}
		} finally {
			try { 
				
				// close the used resources
				if (rs != null) {
					
					start = System.currentTimeMillis();
					
					rs.close();
					
					end = System.currentTimeMillis();
					
					System.out
					.printf("%nResult set successfully closed in %,d milliseconds.%n",
							end-start);
				}
				
				if (stmt != null) {
					
					start = System.currentTimeMillis();
					
					stmt.close();
					
					end = System.currentTimeMillis();
					
					System.out
					.printf("Statement successfully closed in %,d milliseconds.%n",
							end-start);
				}
				
				if (con != null) {
					
					start = System.currentTimeMillis();
					
					con.close();
					
					end = System.currentTimeMillis();
					
					System.out
					.printf("Connection successfully closed in %,d milliseconds.%n",
							end-start);
				}
				
				System.out.printf("Resources successfully released.%n");
				
			} catch (SQLException e) {
				System.out.printf("Error while releasing resources:%n");

				// cycle in the exception chain
				while (e != null) {
					System.out.printf("- Message: %s%n", e.getMessage());
					System.out.printf("- SQL status code: %s%n", e.getSQLState());
					System.out.printf("- SQL error code: %s%n", e.getErrorCode());
					System.out.printf("%n");
					e = e.getNextException();
				}

			} finally {

				// release resources to the garbage collector
				rs = null;
				stmt = null;
				con = null;

				System.out.printf("Resources released to the garbage collector.%n");
			}
		}
		
		System.out.printf("Program end.%n");
		
	}
}
