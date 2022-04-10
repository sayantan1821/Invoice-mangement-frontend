package web;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cross.domain.request.servletCOR;
import database.connectDB;
import database.dbCredentials;

@WebServlet("/api/removePrediction")
public class removePrediction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		servletCOR.setAccessControlHeaders(res);
		String doc_id = req.getParameter("doc_id");
		String aging_bucket = req.getParameter("aging_bucket");
		String query = null;

		PrintWriter out = res.getWriter();
		try {
			Connection con = connectDB.getConnection();
			query = "UPDATE " + dbCredentials.getTableName() + " SET `aging_bucket` = NULL ";
			PreparedStatement pst = con.prepareStatement(query);


			pst.executeUpdate();
			out.print("Record Updated ");
			con.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doPost(req, res);
	}

}
