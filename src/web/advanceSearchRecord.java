package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.connectDB;
import database.dbCredentials;

@WebServlet("/api/advanceSearch")
public class advanceSearchRecord extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String doc_id = req.getParameter("doc_id");
		String cust_number = req.getParameter("cust_number");
		String buisness_year = req.getParameter("buisness_year");
		String invoice_id = req.getParameter("invoice_id");
		int pageNo = 0, recordsPerPage = 10;
		if (req.getParameter("pageNo") != null)
			pageNo = Integer.parseInt(req.getParameter("pageNo"));
		if (req.getParameter("recordsPerPage") != null)
			recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
		int startIndex = pageNo * recordsPerPage;

		String query = null;
		String columns = "winter_internship.sl_no, winter_internship.business_code,"
				+ " business.business_name, winter_internship.cust_number, customer.name_customer,"
				+ " winter_internship.clear_date, winter_internship.buisness_year,"
				+ " winter_internship.doc_id, winter_internship.posting_date,"
				+ " winter_internship.document_create_date, winter_internship.document_create_date1,"
				+ " winter_internship.due_in_date, winter_internship.invoice_currency,"
				+ " winter_internship.document_type, winter_internship.posting_id,"
				+ " winter_internship.area_business, winter_internship.total_open_amount,"
				+ " winter_internship.baseline_create_date, winter_internship.cust_payment_terms,"
				+ " winter_internship.invoice_id, winter_internship.isOpen,"
				+ " winter_internship.aging_Bucket, winter_internship.is_deleted," + " winter_internship.predicted";

		PrintWriter out = res.getWriter();
		try {
			Connection con = connectDB.getConnection();
			Statement st = con.createStatement();

			st = con.createStatement();
			query = "SELECT " + columns + " FROM " + dbCredentials.getTableName()
					+ " LEFT JOIN `business` ON `winter_internship`.`business_code` ="
					+ " `business`.`business_code` LEFT JOIN `customer` ON"
					+ " `winter_internship`.`cust_number` = `customer`.`cust_number` WHERE (" + doc_id
					+ " IS NULL OR doc_id LIKE '" + doc_id + "%') AND (" + cust_number
					+ " IS NULL OR winter_internship.cust_number LIKE '" + cust_number + "%') AND (" + invoice_id
					+ " IS NULL OR winter_internship.invoice_id LIKE '" + invoice_id + "%') AND (" + buisness_year
					+ " IS NULL OR buisness_year LIKE '" + buisness_year + "%') AND winter_internship.is_deleted = 0 LIMIT "
					+ startIndex + ", " + recordsPerPage;

			ResultSet rs2 = st.executeQuery(query);

			ArrayList<MainPojo> pojoArray = new ArrayList<MainPojo>();
			while (rs2.next()) {
				MainPojo pojo = new MainPojo();

				pojo.setSl_no(Integer.parseInt(rs2.getString("sl_no")));
				pojo.setBusiness_code(rs2.getString("business_code"));
				pojo.setBusiness_name(rs2.getString("business_name"));
				pojo.setCust_number(rs2.getString("cust_number"));
				pojo.setName_customer(rs2.getString("name_customer"));
				pojo.setClear_date(rs2.getString("clear_date"));
				pojo.setBusiness_year(rs2.getString("buisness_year"));
				pojo.setDoc_id(rs2.getString("doc_id"));
				pojo.setPosting_date(rs2.getString("posting_date"));
				pojo.setDocument_create_date(rs2.getString("document_create_date"));
				pojo.setDocument_create_date1(rs2.getString("document_create_date1"));
				pojo.setDue_in_date(rs2.getString("due_in_date"));
				pojo.setInvoice_currency(rs2.getString("invoice_currency"));
				pojo.setDocument_type(rs2.getString("document_type"));
				pojo.setPosting_id(Integer.parseInt(rs2.getString("posting_id")));
				pojo.setArea_business(rs2.getString("area_business"));
				pojo.setTotal_open_amount(rs2.getDouble("total_open_amount"));
				pojo.setBaseline_create_date(rs2.getString("baseline_create_date"));
				pojo.setCust_payment_terms(rs2.getString("cust_payment_terms"));
				pojo.setInvoice_id(rs2.getInt("invoice_id"));
				pojo.setIsOpen(rs2.getInt("isOpen"));
				pojo.setAging_Bucket(rs2.getString("aging_Bucket"));
				pojo.setIs_deleted(rs2.getString("is_deleted"));
				pojo.setPredicted(rs2.getString("predicted"));
				pojoArray.add(pojo);
			}
			GsonBuilder gb = new GsonBuilder();
			Gson gs = gb.create();
			String jsonData = gs.toJson(pojoArray);

			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");

			out.println(jsonData);
			rs2.close();

			con.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
