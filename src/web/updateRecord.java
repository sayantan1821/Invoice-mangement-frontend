package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.connectDB;
import database.dbCredentials;
import database.isExist;

@WebServlet("/api/updateRecord")
public class updateRecord extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		int sl_no = Integer.parseInt(req.getParameter("sl_no"));
		String invoice_currency = req.getParameter("invoice_currency");
		String cust_payment_terms = req.getParameter("cust_payment_terms");
		String query = null;
		
		PrintWriter out = res.getWriter();
		try {
			if(isExist.ifDataExistsById(sl_no)) {
				Connection con = connectDB.getConnection();
				query = "UPDATE "+dbCredentials.getTableName()
								+" SET `cust_payment_terms` = ?,"
								+" `invoice_currency`=? WHERE sl_no=?";
				PreparedStatement pst = con.prepareStatement(query);
				
				
				pst.setString(1, cust_payment_terms);
				pst.setString(2, invoice_currency);
				pst.setInt(3, sl_no);
				
				pst.executeUpdate();
				out.print("Record Updated");
				RequestDispatcher rd = req.getRequestDispatcher("recordById");
				rd.forward(req,  res);
				
				con.close();
				pst.close();
			} else {
				out.print("Record does not exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
