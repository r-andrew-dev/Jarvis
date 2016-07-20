package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Viewability;
import utils.ExcelHelper;

public class ViewabilityController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ViewabilityController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path").toString();
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readViewabilityData();
		try {
			tags.addAll(readNewData(path));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String json = new Gson().toJson(tags);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private List<Viewability> readNewData(String path) throws IOException, ParseException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readNewViewabilityData();
		return tags;
	}

}
