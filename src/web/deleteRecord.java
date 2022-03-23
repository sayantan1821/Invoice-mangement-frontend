package web;
import database.isExist;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.connectDB;
import database.dbCredentials;

import com.google.gson.Gson;

@WebServlet("/api/deleteById")
public class deleteRecord extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String sl_nos = req.getParameter("sl_nos");
		String[] slArray = sl_nos.split("[,]", 0);
		PrintWriter out = res.getWriter();
		serverResponse sr = new serverResponse();
		Gson gson = new Gson();
		for(String sl_no : slArray) {
			try {
				if(isExist.ifDataExistsById(Integer.parseInt(sl_no))) {
					Connection con = connectDB.getConnection();
					String query = "DELETE FROM "+dbCredentials.getTableName()
									+" WHERE sl_no = ?";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, sl_no);
					pst.execute();
					sr.code ="200";
					sr.mssg =sl_no + " record has been deleted";
					String jsonData = gson.toJson(sr);
					res.setContentType("application/json");
					res.setCharacterEncoding("UTF-8");

					out.println(jsonData);
					
					con.close();
					pst.close();
					
				} else {
					sr.code ="400";
					sr.mssg = sl_no + " record can not be deleted";
					String jsonData = gson.toJson(sr);
					res.setContentType("application/json");
					res.setCharacterEncoding("UTF-8");

					out.println(jsonData);
				}
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		out.flush();
		out.close();
	}
}
