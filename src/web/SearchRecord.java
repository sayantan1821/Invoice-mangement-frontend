package web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cross.domain.request.servletCOR;
import database.connectDB;
import database.dbCredentials;
import database.isExist;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
@WebServlet("/api/recordById")
public class SearchRecord extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

//		int sl_no = Integer.parseInt(req.getParameter("sl_no")); 
		String sl_par = req.getParameter("sl_no");
		int sl_no = -1;
		if(sl_par != null) sl_no = Integer.parseInt(sl_par);
		int pageNo = 0, recordsPerPage = 10;
		if(req.getParameter("pageNo") != null) pageNo = Integer.parseInt(req.getParameter("pageNo"));
        if(req.getParameter("recordsPerPage") != null) recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
		int startIndex = pageNo * recordsPerPage;
		String search_by_cust_number = req.getParameter("search_by_cust_number");
		String cust_number = req.getParameter("cust_number"); 
		PrintWriter out = res.getWriter();
		String query = null;
		String columns = "winter_internship.sl_no, winter_internship.business_code, business.business_name, winter_internship.cust_number, customer.name_customer, winter_internship.clear_date, winter_internship.buisness_year, winter_internship.doc_id, winter_internship.posting_date, winter_internship.document_create_date, winter_internship.document_create_date1, winter_internship.due_in_date, winter_internship.invoice_currency, winter_internship.document_type, winter_internship.posting_id, winter_internship.area_business, winter_internship.total_open_amount, winter_internship.baseline_create_date, winter_internship.cust_payment_terms, winter_internship.invoice_id, winter_internship.isOpen, winter_internship.aging_Bucket, winter_internship.is_deleted";
		try {
			if((cust_number != null) || (sl_par != null && isExist.ifDataExistsById(sl_no))) {

				Connection con = connectDB.getConnection();
				con.setAutoCommit(false);
				ResultSet rs = null;
				Statement st = null;
				
				if(search_by_cust_number != null) {
					query = "select "+columns+" from "+dbCredentials.getTableName()
								+" LEFT JOIN `business` ON `winter_internship`.`business_code` = `business`.`business_code` LEFT JOIN `customer` ON `winter_internship`.`cust_number` = `customer`.`cust_number` where winter_internship.cust_number = " + cust_number + " LIMIT " + startIndex + ", " + recordsPerPage;
				} else {
					query = "select "+columns+" from "+dbCredentials.getTableName()
					+" LEFT JOIN `business` ON `winter_internship`.`business_code` = `business`.`business_code` LEFT JOIN `customer` ON `winter_internship`.`cust_number` = `customer`.`cust_number` where sl_no = " + sl_no;
				}
				
				
				st = con.createStatement();
				rs = st.executeQuery(query);
				
				ArrayList<MainPojo> pojoArray = new ArrayList<MainPojo>();
				
				while(rs.next()) {
					MainPojo pojo = new MainPojo();
					
					pojo.setSl_no(Integer.parseInt(rs.getString("sl_no")));
					pojo.setBusiness_code(rs.getString("business_code"));
					pojo.setBusiness_name(rs.getString("business_name"));
					pojo.setCust_number(rs.getString("cust_number"));
					pojo.setName_customer(rs.getString("name_customer"));
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
			        
			        pojoArray.add(pojo);
				}
				GsonBuilder gb = new GsonBuilder();
		        Gson gs = gb.create();
		        String jsonData = gs.toJson(pojoArray);
//				if(endIndex <= pojoArray.size()) {
//					List<MainPojo> subPojoArray = pojoArray.subList(startIndex, endIndex);
//					jsonData = gs.toJson(subPojoArray);
//				} else jsonData = gs.toJson(pojoArray);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				
				out.println(jsonData);
				con.releaseSavepoint(null);
				con.close();
				st.close();
				rs.close();
			}
			else {
				out.println("No record found");
			}
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
