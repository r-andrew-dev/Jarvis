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

public class MobDealController  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public MobDealController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type").toString();
		if (type.equals("reqs"))
			getRequests(request, response);
		else if(type.equals("ecpm"))
			getEcpm(request, response);
	}
	
	private void getRequests(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataAccess d = new DataAccess();
		List<GenericObject> data = d.getRequests();
		String json = new Gson().toJson(data);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getEcpm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataAccess d = new DataAccess();
		List<GenericObject> data = d.getEcpm();
		String json = new Gson().toJson(data);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
