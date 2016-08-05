package model;

import java.io.Serializable;

public class Bidder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private int served;
	private int viewed;
	private float revenue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getServed() {
		return served;
	}
	public void setServed(int served) {
		this.served = served;
	}
	public int getViewed() {
		return viewed;
	}
	public void setViewed(int viewed) {
		this.viewed = viewed;
	}
	public float getRevenue() {
		return revenue;
	}
	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}
	

}
