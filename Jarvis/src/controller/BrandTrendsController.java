package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.DataAccess;
import model.Daily;

public class BrandTrendsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BrandTrendsController() {
            super();
    }
    
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
	    	DataAccess d = new DataAccess();
	    	String accountId = request.getParameter("id").toString();
	    	String platform = request.getParameter("platform").toString();
	    	List<Daily> accTrends = new ArrayList<Daily>();
	    	if(platform.equals("mmDSP"))
	    		accTrends = d.getAccountTrends(accountId);
	    	else if(platform.equals("Mydas"))
	    		accTrends = d.getGreenAccountTrends(accountId);
    		String json = new Gson().toJson(accTrends);
    		System.out.println(json);
            response.setContentType("application/json");
            response.getWriter().write(json);
    }
}
