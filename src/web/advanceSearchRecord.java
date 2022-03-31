package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.connectDB;
import database.dbCredentials;
import helper.totalCount;

@WebServlet("/api/advanceSearch")
public class advanceSearchRecord extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String doc_id = null, invoice_id = null, cust_number = null, buisness_year = null;
		String query = null;
		String query1 = null;
		int pageNo = 0, recordsPerPage = 10;
		PrintWriter out = res.getWriter();
		Connection con = null;
		PreparedStatement pst = null;

		if (req.getParameter("doc_id") != null && req.getParameter("doc_id").length() > 0)
			doc_id = req.getParameter("doc_id");
		if (req.getParameter("invoice_id") != null && req.getParameter("invoice_id").length() > 0)
			invoice_id = req.getParameter("invoice_id");
		if (req.getParameter("cust_number") != null && req.getParameter("cust_number").length() > 0)
			cust_number = req.getParameter("cust_number");
		if (req.getParameter("buisness_year") != null && req.getParameter("buisness_year").length() > 0)
			buisness_year = req.getParameter("buisness_year");

		if (req.getParameter("pageNo") != null && req.getParameter("pageNo").length() > 0)
			pageNo = Integer.parseInt(req.getParameter("pageNo"));
		if (req.getParameter("recordsPerPage") != null && req.getParameter("recordsPerPage").length() > 0)
			recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
		int startIndex = pageNo * recordsPerPage;

		try {
			con = connectDB.getConnection();
			query = "SELECT * FROM " + dbCredentials.getTableName() + " WHERE ( " + doc_id + " IS NULL OR doc_id LIKE '"
					+ doc_id + "%') AND ( " + cust_number + " IS NULL OR cust_number LIKE '" + cust_number
					+ "%') AND ( " + invoice_id + " IS NULL OR invoice_id LIKE '" + invoice_id + "%') AND ( "
					+ buisness_year + " IS NULL OR buisness_year LIKE '" + buisness_year
					+ "%') AND is_deleted = 0 LIMIT " + startIndex + "," + recordsPerPage;
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery(query);
			Map<String,Object> objectMap = new HashMap<>();
			ArrayList<MainPojo> pojoArray = new ArrayList<MainPojo>();
			while (rs.next()) {
				MainPojo pojo = new MainPojo();
				pojo.setSl_no(Integer.parseInt(rs.getString("sl_no")));
				pojo.setBusiness_code(rs.getString("business_code"));
				pojo.setCust_number(rs.getString("cust_number"));
				pojo.setClear_date(rs.getString("clear_date"));
				pojo.setBusiness_year(rs.getString("buisness_year"));
				pojo.setDoc_id(rs.getString("doc_id"));
				pojo.setPosting_date(rs.getString("posting_date"));
				pojo.setDocument_create_date(rs.getString("document_create_date"));
				pojo.setDocument_create_date1(rs.getString("document_create_date1"));
				pojo.setDue_in_date(rs.getString("due_in_date"));
				pojo.setInvoice_currency(rs.getString("invoice_currency"));
				pojo.setDocument_type(rs.getString("document_type"));
				pojo.setPosting_id(Integer.parseInt(rs.getString("posting_id")));
				pojo.setArea_business(rs.getString("area_business"));
				pojo.setTotal_open_amount(rs.getDouble("total_open_amount"));
				pojo.setBaseline_create_date(rs.getString("baseline_create_date"));
				pojo.setCust_payment_terms(rs.getString("cust_payment_terms"));
				pojo.setInvoice_id(rs.getInt("invoice_id"));
				pojo.setIsOpen(rs.getInt("isOpen"));
				pojo.setAging_Bucket(rs.getString("aging_Bucket"));
				pojo.setIs_deleted(rs.getString("is_deleted"));
				pojo.setPredicted(rs.getString("predicted"));

				pojoArray.add(pojo);
			}
			objectMap.put("advanceSearch", pojoArray);
			query1 = "SELECT COUNT(*) AS count FROM " + dbCredentials.getTableName() + " WHERE ( " + doc_id + " IS NULL OR doc_id LIKE '%"
					+ doc_id + "%') AND ( " + cust_number + " IS NULL OR cust_number LIKE '%" + cust_number
					+ "%') AND ( " + invoice_id + " IS NULL OR invoice_id LIKE '%" + invoice_id + "%') AND ( "
					+ buisness_year + " IS NULL OR buisness_year LIKE '%" + buisness_year
					+ "%') AND is_deleted = 0 ";
			
			pst = con.prepareStatement(query1);
			rs = pst.executeQuery(query1);
			rs.next();
			totalCount tc = new totalCount();
			tc.count = rs.getInt("count");
			objectMap.put("count", tc);
			
			Gson gson = new Gson(); 
			String jsonData = gson.toJson(objectMap);

			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");

			out.println(jsonData);
			rs.close();

			con.close();
			pst.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
