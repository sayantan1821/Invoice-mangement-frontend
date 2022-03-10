package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
	private static final String JDBC_DRIVER = dbCredentials.getJdbcDriver();
	private static final String DB_URL = dbCredentials.getDbUrl();
	private static final String USER = dbCredentials.getUser();
	private static final String PASS = dbCredentials.getPass();
	
	public static Connection getConnection() {
		try {
			//Register JDBC driver
			Class.forName(JDBC_DRIVER); // this might throw classNotFoundException
			//Open a connection 
			return DriverManager.getConnection(DB_URL,USER,PASS);
			
		} catch (ClassNotFoundException ce) {//checked exception
			//generally JVM internally throw the exception .
			throw new RuntimeException("MySql Driver Not found"); //custom exception to manually throw it.
		} catch (SQLException ex) {
			throw new RuntimeException("Error connecting to the database", ex);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error!");
		}
		
	}
}
