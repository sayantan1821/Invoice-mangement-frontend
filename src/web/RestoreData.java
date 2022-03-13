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
		String columns = "winter_internship.sl_no, winter_internship.business_code, winter_internship.cust_number, winter_internship.clear_date, winter_internship.buisness_year, winter_internship.doc_id, winter_internship.posting_date, winter_internship.document_create_date, winter_internship.document_create_date1, winter_internship.due_in_date, winter_internship.invoice_currency, winter_internship.document_type, winter_internship.posting_id, winter_internship.area_business, winter_internship.total_open_amount, winter_internship.baseline_create_date, winter_internship.cust_payment_terms, winter_internship.invoice_id, winter_internship.isOpen, winter_internship.aging_Bucket, winter_internship.is_deleted";
		try {
			
				Connection con = connectDB.getConnection();
				con.setAutoCommit(false);
				ResultSet rs1 = null;
				Statement st = null;
				
				String query1 = "SELECT "+columns+" FROM `winter_internship` WHERE is_deleted = 1";
				String query2 = "UPDATE `grey_goose`.`winter_internship` SET `is_deleted` = '0' WHERE is_deleted = 1";
				st = con.createStatement();
				st.executeUpdate(query2);
				
//				while(rs1.next()) {
//					String sl_no = rs1.getString("sl_no");
//					System.out.println(sl_no);
//					Statement st2 = con.createStatement();
//					
//					
////					PreparedStatement pst = con.prepareStatement(query2);
////					pst.setString(1, "0");
////					pst.setString(2, sl_no);
////					int r = pst.executeUpdate();
//					st2.execute(query2);
//					out.println("Record restore sl_no : " + sl_no +" ");
//				}
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
