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

import cross.domain.request.servletCOR;
import database.connectDB;
import database.dbCredentials;
@WebServlet("/api/deleteById")
public class deleteRecord extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		servletCOR.setAccessControlHeaders(res);
		String sl_nos = req.getParameter("sl_nos");
		String[] slArray = sl_nos.split("[,]", 0);
		PrintWriter out = res.getWriter();
		for(String sl_no : slArray) {
			try {
				if(isExist.ifDataExistsById(Integer.parseInt(sl_no))) {
					Connection con = connectDB.getConnection();
					String query = "DELETE FROM "+dbCredentials.getTableName()
									+" WHERE sl_no = ?";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, sl_no);
					pst.execute();
					out.println(sl_no + " record has been deleted");
					
					con.close();
					pst.close();
					
				} else {
					out.println(sl_no + " record can not be deleted");
				}
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		out.flush();
		out.close();
	}
}
