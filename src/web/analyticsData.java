package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.connectDB;
import database.dbCredentials;

@WebServlet("/api/analyticsData")
public class analyticsData extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String clear_date_start = null, clear_date_end = null, due_in_date_start = null, due_in_date_end = null,
				baseline_create_date_start = null, baseline_create_date_end = null, invoice_currency = null;
		String query = null;
		PrintWriter out = res.getWriter();
		Connection con = null;
		PreparedStatement pst = null;

		if (req.getParameter("clear_date_start") != null && req.getParameter("clear_date_start").length() > 0)
			clear_date_start = req.getParameter("clear_date_start");
		if (req.getParameter("clear_date_end") != null && req.getParameter("clear_date_end").length() > 0)
			clear_date_end = req.getParameter("clear_date_end");
		if (req.getParameter("due_in_date_start") != null && req.getParameter("due_in_date_start").length() > 0)
			due_in_date_start = req.getParameter("due_in_date_start");
		if (req.getParameter("due_in_date_end") != null && req.getParameter("due_in_date_end").length() > 0)
			due_in_date_end = req.getParameter("due_in_date_end");
		if (req.getParameter("baseline_create_date_start") != null
				&& req.getParameter("baseline_create_date_start").length() > 0)
			baseline_create_date_start = req.getParameter("baseline_create_date_start");
		if (req.getParameter("baseline_create_date_end") != null
				&& req.getParameter("baseline_create_date_end").length() > 0)
			baseline_create_date_end = req.getParameter("baseline_create_date_end");
		if (req.getParameter("invoice_currency") != null && req.getParameter("invoice_currency").length() > 0)
			invoice_currency = req.getParameter("invoice_currency");
		try {
			con = connectDB.getConnection();
			System.out.println(invoice_currency);
//			if (invoice_currency != null)
//				query = "SELECT `business_code`, COUNT(DISTINCT `cust_number`) AS no_of_cust, SUM(total_open_amount) AS sum_of_open_amount FROM "
//						+ dbCredentials.getTableName() + " WHERE ( " + clear_date_start + " IS NULL OR clear_date >=  "
//						+ clear_date_start + "  ) AND ( " + clear_date_end + " IS NULL OR clear_date <= \""
//						+ clear_date_end + "\" ) AND ( " + due_in_date_start + " IS NULL OR due_in_date >= '"
//						+ due_in_date_start + "' ) AND ( " + due_in_date_end + " IS NULL OR due_in_date <= '"
//						+ due_in_date_end + "' ) AND ( " + baseline_create_date_start
//						+ " IS NULL OR baseline_create_date >= " + baseline_create_date_start + ") AND ( "
//						+ baseline_create_date_end + " IS NULL OR baseline_create_date <= " + baseline_create_date_end
//						+ ") AND (invoice_currency = \"" + invoice_currency
//						+ "\" ) AND is_deleted = 0 GROUP BY `business_code`";
//
//			else
//				query = "SELECT `business_code`, COUNT(DISTINCT `cust_number`) AS no_of_cust, SUM(total_open_amount) AS sum_of_open_amount FROM "
//						+ dbCredentials.getTableName() + " WHERE ( " + clear_date_start + " IS NULL OR clear_date >= \""
//						+ clear_date_start + "\") AND ( " + clear_date_end + " IS NULL OR clear_date <= \""
//						+ clear_date_end + "\" ) AND ( " + due_in_date_start + " IS NULL OR due_in_date >= \""
//						+ due_in_date_start + "\") AND ( " + due_in_date_end + " IS NULL OR due_in_date <= \""
//						+ due_in_date_end + "\") AND ( " + baseline_create_date_start
//						+ " IS NULL OR baseline_create_date >= \"" + baseline_create_date_start + "\") AND ( "
//						+ baseline_create_date_end + " IS NULL OR baseline_create_date <= \"" + baseline_create_date_end
//						+ "\") AND is_deleted = 0 GROUP BY `business_code`";

//			query = "SELECT `business_code`, COUNT(DISTINCT `cust_number`) AS no_of_cust, SUM(total_open_amount) AS sum_of_open_amount FROM "
//					+ dbCredentials.getTableName() + " WHERE ( " + clear_date_start + " IS NULL OR clear_date >= \""
//					+ clear_date_start + "\") AND ( " + clear_date_end + " IS NULL OR clear_date <= \"" + clear_date_end
//					+ "\" ) AND ( " + due_in_date_start + " IS NULL OR due_in_date >= \"" + due_in_date_start
//					+ "\") AND ( " + due_in_date_end + " IS NULL OR due_in_date <= \"" + due_in_date_end + "\") AND ( "
//					+ baseline_create_date_start + " IS NULL OR baseline_create_date >= \"" + baseline_create_date_start
//					+ "\") AND ( " + baseline_create_date_end + " IS NULL OR baseline_create_date <= \""
//					+ baseline_create_date_end + "\") AND ( " + invoice_currency
//					+ " IS NOT NULL AND invoice_currency = \"" + invoice_currency
//					+ "\" ) AND is_deleted = 0 GROUP BY `business_code`";

			query = "SELECT `business_code`, COUNT(DISTINCT `cust_number`) AS no_of_cust, SUM(total_open_amount) AS sum_of_open_amount FROM `winter_internship` WHERE ( "
					+ clear_date_start + " IS NULL OR clear_date >= ? ) AND ( " + clear_date_end
					+ " IS NULL OR clear_date <= ? ) AND ( " + due_in_date_start
					+ " IS NULL OR due_in_date >= ? ) AND ( " + due_in_date_end
					+ " IS NULL OR due_in_date <= ? ) AND ( " + baseline_create_date_start
					+ " IS NULL OR baseline_create_date >= ? ) AND ( " + baseline_create_date_end
					+ " IS NULL OR baseline_create_date <= ? ) AND ( " + invoice_currency
					+ " IS NULL OR invoice_currency = ? ) AND is_deleted = 0 GROUP BY `business_code`";
			
			pst = con.prepareStatement(query);
			
			pst.setString(1, clear_date_start);
			pst.setString(2, clear_date_end);
			pst.setString(3, due_in_date_start); 
			pst.setString(4, due_in_date_end);
			pst.setString(5, baseline_create_date_start);
			pst.setString(6, baseline_create_date_end);
			pst.setString(7, invoice_currency);

			ResultSet rs = pst.executeQuery(query);

			ArrayList<AnalyticsPojo> pojoArray = new ArrayList<AnalyticsPojo>();
			while (rs.next()) {
				AnalyticsPojo ap = new AnalyticsPojo();

				ap.setBusiness_code(rs.getString("business_code"));
				ap.setNo_of_cust(rs.getInt("no_of_cust"));
				ap.setSum_of_open_amount(rs.getDouble("sum_of_open_amount"));

				pojoArray.add(ap);
			}
			GsonBuilder gb = new GsonBuilder();
			Gson gs = gb.create();
			String jsonData = gs.toJson(pojoArray);

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
	String s = "SELECT `business_code`, COUNT(DISTINCT `cust_number`) AS no_of_cust, SUM(total_open_amount) AS sum_of_open_amount FROM `winter_internship` WHERE clear_date >= 2019-01-22 AND clear_date <= '2019-05-06' AND due_in_date >= 2019-01-22 AND due_in_date <= '2019-05-06' AND baseline_create_date >= '2019-01-22' AND baseline_create_date <= '2019-05-06' AND invoice_currency = 'USD' AND is_deleted = 0 GROUP BY `business_code`";
}

