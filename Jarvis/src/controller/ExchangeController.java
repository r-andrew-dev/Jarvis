package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.DataAccess;
import model.Exchange;

public class ExchangeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ExchangeController() {
            super();
    }
    
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
    		DataAccess d = new DataAccess();
    		List<Exchange> exchanges = d.getExchanges();
    		
    		String json = new Gson().toJson(exchanges);
            response.setContentType("application/json");
            response.getWriter().write(json);
    }
}
