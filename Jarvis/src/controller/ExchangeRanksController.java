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

public class ExchangeRanksController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ExchangeRanksController() {
            super();
    }
    
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
    		String networkId = request.getParameter("id").toString();
    		String path = request.getParameter("path").toString();
    		ExcelHelper eh = new ExcelHelper(path);
    		if(!networkId.equals("-1")) {
    			List<Exchange> e = eh.readExchangeHighLevel();		
        		String json = new Gson().toJson(e);
        		System.out.println(json);
                response.setContentType("application/json");
                response.getWriter().write(json);
    		}
    		
    }
}
