package model;

public class Weekly {
	
	private String day;
	private int lastWeekSpend;
	private int prevWeekSpend;
	private int lastYearSpend;
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getLastWeekSpend() {
		return lastWeekSpend;
	}
	public void setLastWeekSpend(int lastWeekSpend) {
		this.lastWeekSpend = lastWeekSpend;
	}
	public int getPrevWeekSpend() {
		return prevWeekSpend;
	}
	public void setPrevWeekSpend(int prevWeekSpend) {
		this.prevWeekSpend = prevWeekSpend;
	}
	public int getLastYearSpend() {
		return lastYearSpend;
	}
	public void setLastYearSpend(int lastYearSpend) {
		this.lastYearSpend = lastYearSpend;
	}
	
}
