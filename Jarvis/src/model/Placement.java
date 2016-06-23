package model;

import java.util.List;

public class Placement {

	private String placementId;
	private String placementName;
	private String placementDimension;
	private List<Daily> greenTrends;
	private List<Daily> nexTrends;
	private double cost;
	private double rev;
	private double margin;
	private int totalImpressions;
	private float avgEcpm;
	private String tier;
	private String position;

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getTotalImpressions() {
		return totalImpressions;
	}

	public void setTotalImpressions(int totalImpressions) {
		this.totalImpressions = totalImpressions;
	}

	public float getAvgEcpm() {
		return avgEcpm;
	}

	public void setAvgEcpm(float avgEcpm) {
		this.avgEcpm = avgEcpm;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getRev() {
		return rev;
	}

	public void setRev(double rev) {
		this.rev = rev;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public String getPlacementDimension() {
		return placementDimension;
	}

	public void setPlacementDimension(String placementDimension) {
		this.placementDimension = placementDimension;
	}

	public String getPlacementId() {
		return placementId;
	}

	public void setPlacementId(String placementId) {
		this.placementId = placementId;
	}

	public String getPlacementName() {
		return placementName;
	}

	public void setPlacementName(String placementName) {
		this.placementName = placementName;
	}

	public List<Daily> getNexTrends() {
		return nexTrends;
	}

	public void setNexTrends(List<Daily> nexTrends) {
		this.nexTrends = nexTrends;
	}

	public List<Daily> getGreenTrends() {
		return greenTrends;
	}

	public void setGreenTrends(List<Daily> greenTrends) {
		this.greenTrends = greenTrends;
	}

}
