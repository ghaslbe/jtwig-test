package main.java.de.ovmedia.libs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class Controller extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletResponse _response;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doAll(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doAll(request, response);
	}

	protected void display(String templatename, Map<String, Object> map2) {

		try {
			PrintWriter out = _response.getWriter();
			_response.setContentType("text/html");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("_serverid", "1234");

			map.putAll(map2);

			JtwigConfiguration configuration = new JtwigConfiguration();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String defaultpath = "";
			
			ServletContext context = getServletContext();
			String fullPath = context.getRealPath("");
			System.out.println(fullPath);
			
			String[] array = new String[]{
					"/WEB-INF/templates/src/view/",
					"/WEB-INF/templates/global/view/"
					};
			 
			// ForEach 
			for( String path: array )
			{
				path = fullPath+path;
				System.out.println("check: "+path+ templatename);
				
				File file = new File(path + templatename);
				if( file.exists() ){
					defaultpath = path;	
					System.out.println("found in: "+path+ templatename);
				}
			}

			Renderable compiled = new JtwigTemplate(new File(defaultpath + templatename), configuration).compile();

			JtwigModelMap jtwigModelMap = new JtwigModelMap();
			jtwigModelMap.add(map);

			compiled.render(RenderContext.create(configuration.render(), jtwigModelMap, outputStream));
			out.print(outputStream.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this._response = response;
		/*
		 * out.println("<br>getAuthType()- " + request.getAuthType());
		 * out.println("<br>getDateHeader()- " + request.getDateHeader("temp"));
		 * Enumeration<String> headers = request.getHeaderNames(); while
		 * (headers.hasMoreElements()) { out.println("<br>" +
		 * headers.nextElement()); } out.println("<br>getMethod()- " +
		 * request.getMethod()); out.println("<br>getPathInfo()- " +
		 * request.getPathInfo()); out.println("<br>getPathTranslated()- " +
		 * request.getPathTranslated()); out.println("<br>getQueryString()- " +
		 * request.getQueryString()); out.println("<br>getRemoteUser()- " +
		 * request.getRemoteUser()); out.println("<br>getRequestedSessionId()" +
		 * request.getRequestedSessionId()); out.println("<br>getRequestURI()- "
		 * + request.getRequestURI()); out.println("<br>getRequestURL()- " +
		 * request.getRequestURL()); out.println("<br>getServletPath()" +
		 * request.getServletPath());
		 * out.println("<br>isRequestedSessionIdFromCookie()- " +
		 * request.isRequestedSessionIdFromCookie());
		 * out.println("<br>isRequestedSessionIdFromUrl()- " +
		 * request.isRequestedSessionIdFromUrl());
		 * out.println("<br>isRequestedSessionIdFromURL()- " +
		 * request.isRequestedSessionIdFromURL());
		 * out.println("<br>isRequestedSessionIdValid()- " +
		 * request.isRequestedSessionIdValid());
		 */

	}

}
