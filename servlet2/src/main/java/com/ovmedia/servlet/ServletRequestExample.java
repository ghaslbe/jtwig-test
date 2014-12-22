package main.java.com.ovmedia.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

public class ServletRequestExample extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doAll(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doAll(request, response);
	}


	protected void doAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
	

		String id = request.getParameter("id");

		try {
			JtwigConfiguration configuration = new JtwigConfiguration();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			Renderable compiled = new JtwigTemplate(
					new File(
							"/var/lib/tomcat6/webapps/servletexample/WEB-INF/classes/screen/html/template.twig"),
					configuration).compile();
		

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("test", "test");
			map.put("name", "guenther");
			map.put("id", id);

			// JDBC driver name and database URL
			String JDBC_DRIVER = "com.mysql.jdbc.Driver";
			String DB_URL = "jdbc:mysql://localhost/usr_web30_1";

			// Database credentials
			String USER = "root";
			String PASS = "123456";

			Connection conn = null;
			Statement stmt = null;
			try {
				// STEP 2: Register JDBC driver
				Class.forName(JDBC_DRIVER);

				// STEP 3: Open a connection
				conn = DriverManager.getConnection(DB_URL, USER, PASS);

				// STEP 4: Execute a query
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT username FROM user limit 0,10";
				ResultSet rs = stmt.executeQuery(sql);

				HashMap hm1 = new HashMap();
				ResultSetMetaData md = rs.getMetaData();
				int columns = md.getColumnCount();
				int x = 1;
				while (rs.next()) {
					x++;
					HashMap row = new HashMap();
					for (int i = 1; i <= columns; i++) {
						row.put(md.getColumnName(i), rs.getObject(i));
					}
					hm1.put("line" + x, row);
				}

				map.put("users", hm1);
				// map.put("users", resultSetToHashMap(rs));

				// STEP 5: Extract data from result set
				/*
				 * while(rs.next()){ //Retrieve by column name
				 * out.println("o:"+rs.getString("username")); }
				 */

				// STEP 6: Clean-up environment
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
			} catch (Exception e) {
				// Handle errors for Class.forName
				e.printStackTrace();
			} finally {
				// finally block used to close resources
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se2) {
				}// nothing we can do
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}// end finally try
			}// end try

			JtwigModelMap jtwigModelMap = new JtwigModelMap();
			jtwigModelMap.add(map);

			compiled.render(RenderContext.create(configuration.render(),
					jtwigModelMap, outputStream));
			out.println("twig output:" + outputStream.toString() + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("<br>exception: " + e.toString());
		}
/*
		out.println("<br>getAuthType()- " + request.getAuthType());
		out.println("<br>getDateHeader()- " + request.getDateHeader("temp"));
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			out.println("<br>" + headers.nextElement());
		}
		out.println("<br>getMethod()- " + request.getMethod());
		out.println("<br>getPathInfo()- " + request.getPathInfo());
		out.println("<br>getPathTranslated()- " + request.getPathTranslated());
		out.println("<br>getQueryString()- " + request.getQueryString());
		out.println("<br>getRemoteUser()- " + request.getRemoteUser());
		out.println("<br>getRequestedSessionId()"
				+ request.getRequestedSessionId());
		out.println("<br>getRequestURI()- " + request.getRequestURI());
		out.println("<br>getRequestURL()- " + request.getRequestURL());
		out.println("<br>getServletPath()" + request.getServletPath());
		out.println("<br>isRequestedSessionIdFromCookie()- "
				+ request.isRequestedSessionIdFromCookie());
		out.println("<br>isRequestedSessionIdFromUrl()- "
				+ request.isRequestedSessionIdFromUrl());
		out.println("<br>isRequestedSessionIdFromURL()- "
				+ request.isRequestedSessionIdFromURL());
		out.println("<br>isRequestedSessionIdValid()- "
				+ request.isRequestedSessionIdValid());
		*/

	}

}
