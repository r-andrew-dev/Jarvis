package model;

public class GenericObject {
	private String code;
	private long z;
	private String[] values;
	
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	public String getAttribute() {
		return code;
	}
	public void setAttribute(String attribute) {
		this.code = attribute;
	}
	public long getRequests() {
		return z;
	}
	public void setRequests(long requests) {
		this.z = requests;
	}
	
}
