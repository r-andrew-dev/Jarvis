package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.SDK;
import utils.ExcelHelper;

public class SDKController  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public SDKController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");
		ExcelHelper eh = new ExcelHelper(path);
		List<SDK> data = eh.readSdkData();
		String json = new Gson().toJson(data);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
