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

public class OOController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public OOController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type").toString();
		
		if(type.equals("mtd"))
			getMTD(request, response);	
		if(type.equals("site")) {
			String siteId = request.getParameter("id").toString();
			getSiteDaily(request, response, siteId);
		}
		if(type.equals("tagmtd")) {
			String siteId = request.getParameter("id").toString();
			getTagMTD(request, response, siteId);	
		}
		if(type.equals("tagdaily")) {
			String tagId = request.getParameter("id").toString();
			getTagDaily(request, response, tagId);	
		}
	}
	
	private void getMTD(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataAccess d = new DataAccess();
		List<Site> sites = d.getOOSitesMTD();
		String json = new Gson().toJson(sites);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getTagMTD(HttpServletRequest request, HttpServletResponse response, String siteId) throws IOException {
		DataAccess d = new DataAccess();
		List<Site> tags = d.getTagsMTD(siteId);
		String json = new Gson().toJson(tags);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getSiteDaily(HttpServletRequest request, HttpServletResponse response, String id) throws IOException {
		DataAccess d = new DataAccess();
		List<Site> sites = d.getOOSiteDaily(id);
		String json = new Gson().toJson(sites);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getTagDaily(HttpServletRequest request, HttpServletResponse response, String id) throws IOException {
		DataAccess d = new DataAccess();
		List<Site> tags = d.getOOTagDaily(id);
		String json = new Gson().toJson(tags);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
