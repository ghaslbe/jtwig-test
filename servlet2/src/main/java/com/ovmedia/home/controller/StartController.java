package main.java.com.ovmedia.home.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.ovmedia.libs.Controller;
import main.java.com.ovmedia.libs.Databaseconnector;
import main.java.com.ovmedia.libs.Helper;

@SuppressWarnings("serial")
public class StartController extends Controller {

	/**
	 * 
	 */

	public void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		super.doAll(request, response);

		Helper helper = new Helper();
		helper.newLogger();
		helper.log("hello");

		String id = request.getParameter("id");

		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);

			Databaseconnector dbc = new Databaseconnector();
			Map<String, Object> dbresult = dbc.doquery("SELECT username FROM user limit 0,10");
			params.put("users", dbresult);
			params.put("name", "guenther");
			params.put("pagetitle", "Hallo Welt");

			super.display("screen/html/template.twig", params);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

}
