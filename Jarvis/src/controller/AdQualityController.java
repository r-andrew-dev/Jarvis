package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.DataAccess;
import model.AdQuality;
import model.Daily;

public class AdQualityController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AdQualityController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		if(type.equals("bidders"))
			getBiddersData(request, response);
		else if(type.equals("daily"))
			getDailyData(request, response);
		else if(type.equals("bidder")) {
			String bidder = request.getParameter("bidder");
			getBidderDailyData(request, response, bidder);
		}
	}
	
	private void getBiddersData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataAccess d = new DataAccess();
		List<AdQuality> tags = d.getAdQualityData();
		String json = new Gson().toJson(tags);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getDailyData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataAccess d = new DataAccess();
		List<Daily> tags = d.getAdQualityDailyData();
		String json = new Gson().toJson(tags);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private void getBidderDailyData(HttpServletRequest request, HttpServletResponse response, String bidder) throws IOException {
		DataAccess d = new DataAccess();
		List<AdQuality> data = d.getBidderDailyData(bidder);
		String json = new Gson().toJson(data);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
