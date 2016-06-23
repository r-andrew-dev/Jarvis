package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
	
	public String getCountryName(String code) {
		
		Properties prop = new Properties();
		String propFileName = "performance.properties";
		InputStream input = null;
		String name = new String();
		
		try {
			 
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			prop.load(input);
			name = prop.getProperty(code);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return name;
	}
	
	public boolean isCpaAccount(String id) {
		
		Properties prop = new Properties();
		String propFileName = "performance.properties";
		InputStream input = null;
		String accounts = new String();
		
		try {
			 
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			prop.load(input);
			accounts = prop.getProperty("CPA_ACCOUNTS");
			
			if(accounts.contains(id))
				return true;
			else
				return false;
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		return false;
		
	}
	
	public String getAdSize(String name) {
		
		Properties prop = new Properties();
		String propFileName = "performance.properties";
		InputStream input = null;
		String adSize = new String();
		
		try {
			 
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			prop.load(input);
			adSize = prop.getProperty(name);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return adSize;
	}
	
	public String getIABCat(String name) {
		
		Properties prop = new Properties();
		String propFileName = "performance.properties";
		InputStream input = null;
		String category = new String();
		
		try {
			 
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			prop.load(input);
			category = prop.getProperty(name);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return category;
	}

}
