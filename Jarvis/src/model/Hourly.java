package model;

public class Hourly {
	
	private int hour;
	private int yestSpend;
	private int todaySpend;
	private int clicks;
	private int conversions;
	
	public int getClicks() {
		return clicks;
	}
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	public int getConversions() {
		return conversions;
	}
	public void setConversions(int conversions) {
		this.conversions = conversions;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getYestSpend() {
		return yestSpend;
	}
	public void setYestSpend(int yestSpend) {
		this.yestSpend = yestSpend;
	}
	public int getTodaySpend() {
		return todaySpend;
	}
	public void setTodaySpend(int todaySpend) {
		this.todaySpend = todaySpend;
	}
}
