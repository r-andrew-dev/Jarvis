package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.PubChurn;
import model.PublisherAccount;
import utils.ExcelHelper;

public class PubChurnController  extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PubChurnController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type").toString();
		String path = request.getParameter("path").toString();
		
		if(type.equals("mydas"))
			getGreenPubChurn(request, response, path);
		if(type.equals("nex"))	
			getNexPubChurn(request, response, path);
		if(type.equals("mydasrev"))
			getGreenPubChurnRev(request, response, path);
		if(type.equals("nexrev"))	
			getNexPubChurnRev(request, response, path);
		if(type.equals("nexdetailrev")) {
			String category = request.getParameter("category").toString();
			String month = request.getParameter("month").toString();
			getNexDetailChurnRev(request, response, path, category, month);
		}
		if(type.equals("nexdetailreqs")) {
			String category = request.getParameter("category").toString();
			String month = request.getParameter("month").toString();
			getNexDetailChurnReqs(request, response, path, category, month);
		}
		if(type.equals("mydasdetailrev")) {
			String category = request.getParameter("category").toString();
			String month = request.getParameter("month").toString();
			getMydasDetailChurnRev(request, response, path, category, month);
		}
		if(type.equals("mydasdetailreqs")) {
			String category = request.getParameter("category").toString();
			String month = request.getParameter("month").toString();
			getMydasDetailChurnReqs(request, response, path, category, month);
		}
	}
	
	private void getGreenPubChurn(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PubChurn> sites = eh.readGreenPubChurn();
		String json = new Gson().toJson(sites);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getNexPubChurnRev(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PubChurn> sites = eh.readNexPubChurnRev();
		String json = new Gson().toJson(sites);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getGreenPubChurnRev(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PubChurn> sites = eh.readGreenPubChurnRev();
		String json = new Gson().toJson(sites);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getNexPubChurn(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PubChurn> sites = eh.readNexPubChurn();
		String json = new Gson().toJson(sites);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getNexDetailChurnRev(HttpServletRequest request, HttpServletResponse response, String path, String category, String month) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PublisherAccount> pubs = eh.readNexDetailChurnRev(category, month);
		String json = new Gson().toJson(pubs);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getNexDetailChurnReqs(HttpServletRequest request, HttpServletResponse response, String path, String category, String month) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PublisherAccount> pubs = eh.readNexDetailChurnReqs(category, month);
		String json = new Gson().toJson(pubs);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getMydasDetailChurnRev(HttpServletRequest request, HttpServletResponse response, String path, String category, String month) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PublisherAccount> pubs = eh.readMydasDetailChurnRev(category, month);
		String json = new Gson().toJson(pubs);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getMydasDetailChurnReqs(HttpServletRequest request, HttpServletResponse response, String path, String category, String month) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<PublisherAccount> pubs = eh.readMydasDetailChurnReqs(category, month);
		String json = new Gson().toJson(pubs);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
