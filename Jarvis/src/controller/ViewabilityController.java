package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
		String type = request.getParameter("type").toString();
		
		List<Viewability> tags = new ArrayList<Viewability>();
		
		if(type.equals("iase"))
			tags = getIasExchangeData(path);
		else if(type.equals("moate"))
			tags = getMoatExchangeData(path);
		else if(type.equals("iasn"))
			tags = getIasNetworkData(path);
		else if(type.equals("moatn"))
			tags = getMoatNetworkData(path);
		
		String json = new Gson().toJson(tags);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	private List<Viewability> getIasExchangeData(String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readViewabilityData("IASexchange");
		try {
			tags.addAll(readNewIasExchangeData(path));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	private List<Viewability> readNewIasExchangeData(String path) throws IOException, ParseException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readNewViewabilityData("IASexchange");
		return tags;
	}
	
	private List<Viewability> getMoatExchangeData(String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readViewabilityData("MOATexchange");
		try {
			tags.addAll(readNewMoatExchangeData(path));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	private List<Viewability> readNewMoatExchangeData(String path) throws IOException, ParseException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readNewViewabilityData("MOATexchange");
		return tags;
	}
	
	private List<Viewability> getIasNetworkData(String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readViewabilityData("IASnetwork");
		try {
			tags.addAll(readNewIasNetworkData(path));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	private List<Viewability> readNewIasNetworkData(String path) throws IOException, ParseException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readNewViewabilityData("IASnetwork");
		return tags;
	}
	
	private List<Viewability> getMoatNetworkData(String path) throws IOException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readViewabilityData("MOATnetwork");
		try {
			tags.addAll(readNewMoatNetworkData(path));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	private List<Viewability> readNewMoatNetworkData(String path) throws IOException, ParseException {
		ExcelHelper eh = new ExcelHelper(path);
		List<Viewability> tags = eh.readNewViewabilityData("MOATnetwork");
		return tags;
	}

}
