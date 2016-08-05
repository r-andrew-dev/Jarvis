package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Bidder;
import utils.ExcelHelper;

public class NativeDemandController  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public NativeDemandController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path").toString();
		ExcelHelper eh = new ExcelHelper(path);
		List<Bidder> data = eh.readNativeBidders();
		String json = new Gson().toJson(data);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
