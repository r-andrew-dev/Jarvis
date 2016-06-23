package model;

public class Monthly {
	
	private int day;
	private int currMonthSpend;
	private int prevMonthSpend;
	private int prevYearSpend;
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getCurrMonthSpend() {
		return currMonthSpend;
	}
	public void setCurrMonthSpend(int currMonthSpend) {
		this.currMonthSpend = currMonthSpend;
	}
	public int getPrevMonthSpend() {
		return prevMonthSpend;
	}
	public void setPrevMonthSpend(int prevMonthSpend) {
		this.prevMonthSpend = prevMonthSpend;
	}
	public int getPrevYearSpend() {
		return prevYearSpend;
	}
	public void setPrevYearSpend(int prevYearSpend) {
		this.prevYearSpend = prevYearSpend;
	}

}
