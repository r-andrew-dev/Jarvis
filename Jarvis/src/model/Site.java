package model;

import java.util.List;

public class Site {
	private String siteId;
	private String siteName;
	private String bundleId;
	private String adSourceType;
	private List<Daily> greenTrends;
	private List<Daily> nexTrends;
	private List<Placement> placementsList;
	private String os;
	private float ratio;
	private float cost;
	private float rev;
	private float margin;
	private int totalImpressions;
	private int totalRequests;
	private int totalCleared;
	private int totalClicked;
	private float avgEcpm;
	private List<GenericObject> countryReqs;
	private List<GenericObject> carrierReqs;
	private List<GenericObject> countryWins;
	private String date;
	private boolean tierErrorFlag = false;
	private Position position;
	private List<Position> positionsList;

	public List<Position> getPositionsList() {
		return positionsList;
	}

	public void setPositionsList(List<Position> positionsList) {
		this.positionsList = positionsList;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isTierErrorFlag() {
		return tierErrorFlag;
	}

	public void setTierErrorFlag(boolean tierErrorFlag) {
		this.tierErrorFlag = tierErrorFlag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAdSourceType() {
		return adSourceType;
	}

	public void setAdSourceType(String adSourceType) {
		this.adSourceType = adSourceType;
	}

	public int getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}

	public int getTotalCleared() {
		return totalCleared;
	}

	public void setTotalCleared(int totalCleared) {
		this.totalCleared = totalCleared;
	}

	public int getTotalClicked() {
		return totalClicked;
	}

	public void setTotalClicked(int totalClicked) {
		this.totalClicked = totalClicked;
	}

	public List<GenericObject> getCountryWins() {
		return countryWins;
	}

	public void setCountryWins(List<GenericObject> countryWins) {
		this.countryWins = countryWins;
	}

	public List<GenericObject> getCountryReqs() {
		return countryReqs;
	}

	public void setCountryReqs(List<GenericObject> countryReqs) {
		this.countryReqs = countryReqs;
	}

	public List<GenericObject> getCarrierReqs() {
		return carrierReqs;
	}

	public void setCarrierReqs(List<GenericObject> carrierReqs) {
		this.carrierReqs = carrierReqs;
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

	public float getMargin() {
		return margin;
	}

	public void setMargin(float margin) {
		this.margin = margin;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getRev() {
		return rev;
	}

	public void setRev(float rev) {
		this.rev = rev;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public List<Placement> getPlacementsList() {
		return placementsList;
	}

	public void setPlacementsList(List<Placement> placementsList) {
		this.placementsList = placementsList;
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

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}
