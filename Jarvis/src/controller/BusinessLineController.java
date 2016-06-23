package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Exchange;
import utils.ExcelHelper;

public class BusinessLineController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BusinessLineController() {
            super();
    }
    
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
    		String path = request.getParameter("path").toString();
    		ExcelHelper eh = new ExcelHelper(path);
			List<Exchange> e = eh.readBusinessHighLevel();		
    		String json = new Gson().toJson(e);
    		System.out.println(json);
            response.setContentType("application/json");
            response.getWriter().write(json);

    		
    }
}
