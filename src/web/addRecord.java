package web;

import database.connectDB;
import database.dbCredentials;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/api/addRecord")
public class addRecord extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		PrintWriter out = res.getWriter();
		String business_code = req.getParameter("business_code");
		String cust_number = req.getParameter("cust_number");
		Connection con = connectDB.getConnection();

		PreparedStatement pst0 = null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;

		String query0 = null;
		String query1 = null;
		String query2 = null;
		String query3 = null;
		
		serverResponse sr = new serverResponse();
		Gson gson = new Gson();
		
		try {

			MainPojo fp = new MainPojo();

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

			query0 = "SELECT EXISTS(SELECT * FROM `business` WHERE `business`.`business_code` = '" + business_code
					+ "')";
			pst0 = con.prepareStatement(query0);
			ResultSet rs0 = pst0.executeQuery();
			rs0.next();
			int businessCodeExist = rs0.getInt(1);

			if (businessCodeExist != 0) {
				query1 = "SELECT EXISTS(SELECT * FROM `customer` WHERE `customer`.`cust_number` = '" + cust_number
						+ "')";
				pst1 = con.prepareStatement(query1);
				ResultSet rs1 = pst1.executeQuery();
				rs1.next();
				int custNumberExist = rs1.getInt(1);

				if (custNumberExist != 0) {
					query2 = "select count(*) from " + dbCredentials.getTableName();
					pst2 = con.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					rs2.next();
					int sl_no = rs2.getInt(1);
					
					fp.setSl_no(sl_no + 1);

					query3 = "INSERT INTO " + dbCredentials.getTableName()
							+ " (sl_no, business_code, cust_number, clear_date, buisness_year,"
							+ " doc_id, posting_date, document_create_date, due_in_date, invoice_currency,"
							+ " document_type, posting_id, total_open_amount, baseline_create_date,"
							+ " cust_payment_terms, invoice_id) VALUES"
							+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					pst3 = con.prepareStatement(query3);

					pst3.setInt(1, fp.getSl_no());
					pst3.setString(2, fp.getBusiness_code());
					pst3.setString(3, fp.getCust_number());
					pst3.setString(4, fp.getClear_date());
					pst3.setString(5, fp.getBusiness_year());
					pst3.setString(6, fp.getDoc_id());
					pst3.setString(7, fp.getPosting_date());
					pst3.setString(8, fp.getDocument_create_date());
					pst3.setString(9, fp.getDue_in_date());
					pst3.setString(10, fp.getInvoice_currency());
					pst3.setString(11, fp.getDocument_type());
					pst3.setInt(12, fp.getPosting_id());
					pst3.setDouble(13, fp.getTotal_open_amount());
					pst3.setString(14, fp.getBaseline_create_date());
					pst3.setString(15, fp.getCust_payment_terms());
					pst3.setInt(16, fp.getInvoice_id());

					pst3.executeUpdate();
					sr.code ="200";
					sr.mssg = "Record Inserted Successfully Sl No. " + fp.getSl_no();
					
					String jsonData = gson.toJson(sr);
					res.setContentType("application/json");
					res.setCharacterEncoding("UTF-8");

					out.println(jsonData);
					pst3.close();
					pst2.close();
					rs2.close();
				} else {
					sr.code ="404";
					sr.mssg = "Customer Number not found";
					
					String jsonData = gson.toJson(sr);
					res.setContentType("application/json");
					res.setCharacterEncoding("UTF-8");
					out.println(jsonData);
					
					pst1.close();
					rs1.close();
				}
			} else {
				sr.code ="404";
				sr.mssg = "Business Code not found";
				
				String jsonData = gson.toJson(sr);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.println(jsonData);
				
				pst0.close();
				rs0.close();
				con.close();
			}

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doPost(req, res);
	}
	
}


// To do
// 1. Advanced search --- done
// Document Id-(doc_id)
// Customer No-(cust_number)
// Invoice No-(invoice_id)
// Business Year- (buisness_year)
// 2. Edit --- done
// Invoice Currency and Customer Payment Terms
// 3. Error
// 4. Total count api ---done
// 5. update, add, delete api updated ---done

// Doubts :
// 6. CORS
// 7. Warnings in console