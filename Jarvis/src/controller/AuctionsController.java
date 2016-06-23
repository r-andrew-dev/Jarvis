package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.DataAccess;
import model.Site;

public class AuctionsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuctionsController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type").toString();
		String dateRange = request.getParameter("dateRange").toString();
		if (type.equals("getSites"))
			getSites(request, response, dateRange);
		if (type.equals("getTags")) {
			String siteId = request.getParameter("siteId").toString();
			getTags(request, response, siteId, dateRange);
		}
	}

	private void getSites(HttpServletRequest request, HttpServletResponse response, String dateRange) throws IOException {
		DataAccess d = new DataAccess();
		List<Site> sites = d.getSitesWithMultipleAuctions(dateRange);
		String json = new Gson().toJson(sites);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getTags(HttpServletRequest request, HttpServletResponse response, String siteId, String dateRange) throws IOException {
		DataAccess d = new DataAccess();
		Site s = d.getTagsWithMultipleAuctions(siteId, dateRange);
		String json = new Gson().toJson(s);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
}
