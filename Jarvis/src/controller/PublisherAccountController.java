package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.DataAccess;
import model.Placement;
import model.PublisherAccount;
import model.PublisherAccountManager;
import model.Site;

public class PublisherAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublisherAccountController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type").toString();
		if (type.equals("ams"))
			getAms(request, response);
		else if(type.equals("accounts"))
			getAccounts(request, response);
		else if(type.equals("siteData"))
			getSiteData(request, response);
		else if(type.equals("placementData"))
			getPlacementData(request, response);

	}

	private void getAms(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataAccess d = new DataAccess();
		List<PublisherAccountManager> ams = d.getPubAccountManagers();
		String json = new Gson().toJson(ams);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String amId = request.getParameter("amId").toString();
		DataAccess d = new DataAccess();
		List<PublisherAccount> accs = d.getPubAccounts(amId);
		String json = new Gson().toJson(accs);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getSiteData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String siteId = request.getParameter("siteId").toString();
		DataAccess d = new DataAccess();
		Site s = d.getSiteData(siteId);
		String json = new Gson().toJson(s);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getPlacementData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String placementId = request.getParameter("placementId").toString();
		String dimension = request.getParameter("dim").toString();
		String bundleId = request.getParameter("bundleId").toString();
		DataAccess d = new DataAccess();
		Placement p = d.getPlacementData(placementId, dimension, bundleId);
		String json = new Gson().toJson(p);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
}
