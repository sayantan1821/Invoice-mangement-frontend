package web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cross.domain.request.servletCOR;
import database.connectDB;
import database.dbCredentials;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
@WebServlet("/api/restoreData")
public class RestoreData extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String columns = "winter_internship.sl_no";
		try {
			
				Connection con = connectDB.getConnection();
				con.setAutoCommit(false);
				ResultSet rs1 = null;
				Statement st = null;
				
				String query1 = "SELECT "+columns+" FROM `winter_internship` WHERE is_deleted = 1";
				
				st = con.createStatement();
				rs1 = st.executeQuery(query1);
				
				while(rs1.next()) {
					String sl_no = rs1.getString("sl_no");
					System.out.println(sl_no);
					Statement st2 = con.createStatement();
					
					String query2 = "UPDATE "+dbCredentials.getTableName()+" SET winter_internship.is_deleted = 0 WHERE winter_internship.sl_no = " + sl_no + "";
					PreparedStatement pst = con.prepareStatement(query2);
					pst.executeUpdate();
					out.println("Record restore sl_no : " + sl_no);
				}
//				if(endIndex <= pojoArray.size()) {
//					List<MainPojo> subPojoArray = pojoArray.subList(startIndex, endIndex);
//					jsonData = gs.toJson(subPojoArray);
//				} else jsonData = gs.toJson(pojoArray);
				con.close();
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		servletCOR.setAccessControlHeaders(res);
		try {
			doGet(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	@Override
//	  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
//	          throws ServletException, IOException {
//	      setAccessControlHeaders(resp);
//	      resp.setStatus(HttpServletResponse.SC_OK);
//	  }
	
}
