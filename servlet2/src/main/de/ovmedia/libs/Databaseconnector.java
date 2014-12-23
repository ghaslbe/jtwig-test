package main.de.ovmedia.libs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Databaseconnector {

	Connection _conn = null;

	public Databaseconnector() {
		// JDBC driver name and database URL
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String DB_URL = "jdbc:mysql://localhost/usr_web30_1";

		// Database credentials
		String USER = "root";
		String PASS = "123456";

		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			_conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
		}
	}

	public Map<String, Object> doquery(String sql) {
		Statement stmt = null;

		try {

			stmt = this._conn.createStatement();

			// STEP 4: Execute a query

			ResultSet rs = stmt.executeQuery(sql);

			Map<String, Object> hm1 = new HashMap();
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			int x = 1;
			System.out.print("queryme >");
			while (rs.next()) {
				x++;
				HashMap row = new HashMap();
				for (int i = 1; i <= columns; i++) {
					row.put(md.getColumnName(i), rs.getObject(i));
					System.out.print("o");
				}
				hm1.put("line" + x, row);
			}
			System.out.println("<");
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			return hm1;

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}// nothing we can do

		}

		return null;

	}

	public void disconnect() {
		try {

			_conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources

			try {
				if (_conn != null)
					_conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

	}
}
