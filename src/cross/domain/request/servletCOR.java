package cross.domain.request;

import javax.servlet.http.HttpServletResponse;

import database.dbCredentials;

public class servletCOR {
	public static void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "*");
	      resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
	}

}
