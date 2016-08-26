package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.DataAccess;
import model.GenericObject;

public class OsVerController  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public OsVerController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataAccess d = new DataAccess();
		List<GenericObject> data = d.getReqsByOsVersions();
		String json = new Gson().toJson(data);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
