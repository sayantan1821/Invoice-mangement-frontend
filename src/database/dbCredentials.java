package database;

public class dbCredentials {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	//Local Mysql
	private static final String DB_URL = "jdbc:mysql://localhost/grey_goose";
	private static final String USER = "root";
	private static final String PASS = "root";
	
	//Clever Cloud
//	private static final String DB_URL = "jdbc:mysql://bqypqxhupmr46p0aihii-mysql.services.clever-cloud.com/bqypqxhupmr46p0aihii";
//	private static final String USER = "ubjhq2q1ewxzzpvs";
//	private static final String PASS = "Cc7PnQYjmajLpCBj0UUn";
	
	private static final String TABLE_NAME = "winter_internship";
	private static final String CLIENT_URL = "/*";
	private static final String csvFilePath = "D:\\Workspace\\HRC\\final\\HRC61197WK_Sayantan_Kapat.csv";
	
	public static String getJdbcDriver() {
		return JDBC_DRIVER;
	}
	public static String getDbUrl() {
		return DB_URL;
	}
	public static String getUser() {
		return USER;
	}
	public static String getPass() {
		return PASS;
	}
	public static String getTableName() {
		return TABLE_NAME;
	}
	public static String getClientUrl() {
		return CLIENT_URL;
	}
	public static String getCsvfilepath() {
		return csvFilePath;
	}
}
