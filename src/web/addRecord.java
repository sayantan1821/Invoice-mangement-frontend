package web;
import database.connectDB;
import database.dbCredentials;
import database.isExist;

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

@WebServlet("/api/addRecord")
public class addRecord extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		int sl_no = Integer.parseInt(req.getParameter("sl_no"));
		String doc_id = req.getParameter("doc_id");
		PrintWriter out = res.getWriter();
		try {
			if(!isExist.ifDataExists(sl_no, doc_id)) {
				
				MainPojo fp = new MainPojo();
				
				fp.setSl_no(Integer.parseInt(req.getParameter("sl_no")));
				fp.setBusiness_code(req.getParameter("business_code"));
				fp.setCust_number(req.getParameter("cust_number"));
				fp.setClear_date(req.getParameter("clear_date"));
				fp.setBusiness_year(req.getParameter("buisness_year"));
				fp.setDoc_id(req.getParameter("doc_id"));
				fp.setPosting_date(req.getParameter("posting_date"));
				fp.setDocument_create_date(req.getParameter("document_create_date"));
				fp.setDue_in_date(req.getParameter("due_in_date"));
				fp.setInvoice_currency(req.getParameter("invoice_currency"));
				fp.setDocument_type(req.getParameter("document_type"));
				fp.setPosting_id(Integer.parseInt(req.getParameter("posting_id")));
				fp.setTotal_open_amount(Double.parseDouble(req.getParameter("total_open_amount")));
				fp.setBaseline_create_date(req.getParameter("baseline_create_date"));
				fp.setCust_payment_terms(req.getParameter("cust_payment_terms"));
				fp.setInvoice_id(Integer.parseInt(req.getParameter("invoice_id")));
				
				Connection con = connectDB.getConnection();
				PreparedStatement pst = null;
				String query = "INSERT INTO "+dbCredentials.getTableName() 
							+" (sl_no, business_code, cust_number, clear_date, buisness_year,"
							+" doc_id, posting_date, document_create_date, due_in_date, invoice_currency,"
							+" document_type, posting_id, total_open_amount, baseline_create_date,"
							+" cust_payment_terms, invoice_id) VALUES"
							+" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pst = con.prepareStatement(query);

				pst.setInt(1, fp.getSl_no());
		        pst.setString(2, fp.getBusiness_code());
		        pst.setString(3, fp.getCust_number());
		        pst.setString(4, fp.getClear_date());
		        pst.setString(5, fp.getBusiness_year());
		        pst.setString(6, fp.getDoc_id());
		        pst.setString(7, fp.getPosting_date());
		        pst.setString(8, fp.getDocument_create_date());
		        pst.setString(9, fp.getDue_in_date());
		        pst.setString(10, fp.getInvoice_currency());
		        pst.setString(11, fp.getDocument_type());
		        pst.setInt(12, fp.getPosting_id());
		        pst.setDouble(13, fp.getTotal_open_amount());
		        pst.setString(14, fp.getBaseline_create_date());
		        pst.setString(15, fp.getCust_payment_terms());
		        pst.setInt(16, fp.getInvoice_id());

				pst.executeUpdate();
				RequestDispatcher rd = req.getRequestDispatcher("recordById");
				rd.forward(req,  res);
				
				con.close();
				pst.close();
			} else {
				out.println("data with same id and invoice_id is already in the database.");
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
	

}



//To do
//1. Advanced search 	--- done
//		Document Id-(doc_id) 
//		Customer No-(cust_number)
//		Invoice No-(invoice_id)
//		Business Year- (buisness_year)
//2. Edit --- done
//		Invoice Currency and Customer Payment Terms
//3. Error
//4. Total count api ---done
//5. update, add, delete api updated ---done

//Doubts :
//6. CORS
//7. Warnings in console