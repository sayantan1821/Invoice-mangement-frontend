package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import database.isExist;
@WebServlet("/api/removeFromView")
public class removeFromView extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String sl_nos = req.getParameter("sl_nos");
		String[] slArray = sl_nos.split("[,]", 0);
		PrintWriter out = res.getWriter();
		
		ArrayList<serverResponse> resArray = new ArrayList<>();
		for(String sl_no : slArray) {
			serverResponse sr = new serverResponse();
			try {
				if(isExist.ifDataExistsById(Integer.parseInt(sl_no))) {
					Connection con = connectDB.getConnection();
					String query = "UPDATE "+dbCredentials.getTableName()
									+" SET winter_internship.is_deleted = '1' WHERE `sl_no` = ? ORDER BY sl_no";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, sl_no); 
					pst.execute();
					sr.code ="200";
					sr.mssg ="Sl No. " + sl_no + " has been removed";
					
					
					con.close();
					pst.close();
					
				} else {
					sr.code ="400";
					sr.mssg ="Sl No. " + sl_no + " can not be deleted";
				}
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace(); 
			}
			resArray.add(sr);
		}
		GsonBuilder gb = new GsonBuilder();
		Gson gs = gb.create();
		String jsonData = gs.toJson(resArray);

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");

		out.println(jsonData);
		
		out.flush();
		out.close();
	}
}
