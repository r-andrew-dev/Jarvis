package model;

import java.io.Serializable;

public class SDK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String month;
	private float ios6;
	private float android6;
	private float ios5;
	private float android5;
	private float below4_6;
	private float js;
	private float win_s2s;
	private float unknown;
	
	public float getUnknown() {
		return unknown;
	}
	public void setUnknown(float unknown) {
		this.unknown = unknown;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public float getIos6() {
		return ios6;
	}
	public void setIos6(float ios6) {
		this.ios6 = ios6;
	}
	public float getAndroid6() {
		return android6;
	}
	public void setAndroid6(float android6) {
		this.android6 = android6;
	}
	public float getIos5() {
		return ios5;
	}
	public void setIos5(float ios5) {
		this.ios5 = ios5;
	}
	public float getAndroid5() {
		return android5;
	}
	public void setAndroid5(float android5) {
		this.android5 = android5;
	}
	public float getBelow4_6() {
		return below4_6;
	}
	public void setBelow4_6(float below4_6) {
		this.below4_6 = below4_6;
	}
	public float getJs() {
		return js;
	}
	public void setJs(float js) {
		this.js = js;
	}
	public float getWin_s2s() {
		return win_s2s;
	}
	public void setWin_s2s(float win_s2s) {
		this.win_s2s = win_s2s;
	}

}
