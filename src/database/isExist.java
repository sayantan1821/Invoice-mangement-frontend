package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class isExist {
	public static boolean ifDataExistsById(int sl_no) throws SQLException {
		Connection con = connectDB.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String query = "SELECT EXISTS(SELECT * FROM "
						+dbCredentials.getTableName()
						+" WHERE sl_no = "+sl_no+")";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String r = null;
		while(rs.next()) {
			r = rs.getString(1);
		}
		return(r.equals("1"));
	}
	public static boolean ifDataExists(int sl_no, String doc_id) throws SQLException {
		Connection con = connectDB.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String query = "SELECT EXISTS(SELECT * FROM "
						+dbCredentials.getTableName()
						+" WHERE sl_no="+sl_no+" AND doc_id= "
						+doc_id+")";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String r = null;
		while(rs.next()) {
			r = rs.getString(1);
		}
		return(r.equals("1"));
	}
	public static boolean ifDataExistsByCustId(String cust_number) throws SQLException {
		Connection con = connectDB.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String query = "SELECT EXISTS(SELECT * FROM "
						+dbCredentials.getTableName()
						+" WHERE cust_number="+cust_number+")";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String r = null;
		while(rs.next()) {
			r = rs.getString(1);
		}
		return(r.equals("1"));
	}
	public static void main(String[] args) throws SQLException {
		boolean b = ifDataExistsById(48580);
		System.out.println(b);
	}
}
