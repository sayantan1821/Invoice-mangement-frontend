package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.connectDB;
import database.dbCredentials;
 
@WebServlet("/api/count")
public class countRecords extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String query = null;
		PrintWriter out = res.getWriter();
		
		try {
			Connection con = connectDB.getConnection();
			query = "select count(*) from "+ dbCredentials.getTableName();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			rs.next();
			String count = rs.getString(1);
			
			out.println(count);
			
			con.close();
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
