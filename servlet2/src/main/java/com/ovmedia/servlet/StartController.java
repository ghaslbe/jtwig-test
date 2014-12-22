package main.java.com.ovmedia.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StartController extends Controller {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		super.doAll(request, response);

		
		Helper helper = new Helper();
		helper.newLogger();
		helper.log("hello");
		
		String id = request.getParameter("id");

		try {
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			
			Databaseconnector dbc = new Databaseconnector();
			HashMap dbresult = dbc.doquery("SELECT username FROM user limit 0,10");
			map.put("users", dbresult);
			map.put("name", "guenther");

			super.display("screen/html/template.twig",map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}


	}

}
