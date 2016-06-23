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

public class AdQualityController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AdQualityController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataAccess d = new DataAccess();
		List<AdQuality> tags = d.getAdQualityData();
		String json = new Gson().toJson(tags);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
