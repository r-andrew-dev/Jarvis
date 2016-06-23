package model;

public class Exchange {
	private String exchangeId;
	private String exchangeName;
	private float costs;
	private long impressions;
	private long clicks;
	private long conversions;
	private float ecpm;
	private float ctr;
	private float cvr;
	private int costsRank = 1;
	private int impsRank = 1;
	private int clicksRank = 1;
	private int convsRank = 1;
	private int ecpmRank = 1;
	private int ctrRank = 1;
	private int cvrRank = 1;
	private String lineOfBusiness;
	private String type;
	
	public String getLineOfBusiness() {
		return lineOfBusiness;
	}
	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getCosts() {
		return costs;
	}
	public void setCosts(float costs) {
		this.costs = costs;
	}
	public long getImpressions() {
		return impressions;
	}
	public void setImpressions(long impressions) {
		this.impressions = impressions;
	}
	public long getClicks() {
		return clicks;
	}
	public void setClicks(long clicks) {
		this.clicks = clicks;
	}
	public long getConversions() {
		return conversions;
	}
	public void setConversions(long conversions) {
		this.conversions = conversions;
	}
	public float getEcpm() {
		return ecpm;
	}
	public void setEcpm(float ecpm) {
		this.ecpm = ecpm;
	}
	public float getCtr() {
		return ctr;
	}
	public void setCtr(float ctr) {
		this.ctr = ctr;
	}
	public float getCvr() {
		return cvr;
	}
	public void setCvr(float cvr) {
		this.cvr = cvr;
	}
	public int getCostsRank() {
		return costsRank;
	}
	public void setCostsRank(int costsRank) {
		this.costsRank = costsRank;
	}
	public int getImpsRank() {
		return impsRank;
	}
	public void setImpsRank(int impsRank) {
		this.impsRank = impsRank;
	}
	public int getClicksRank() {
		return clicksRank;
	}
	public void setClicksRank(int clicksRank) {
		this.clicksRank = clicksRank;
	}
	public int getConvsRank() {
		return convsRank;
	}
	public void setConvsRank(int convsRank) {
		this.convsRank = convsRank;
	}
	public int getEcpmRank() {
		return ecpmRank;
	}
	public void setEcpmRank(int ecpmRank) {
		this.ecpmRank = ecpmRank;
	}
	public int getCtrRank() {
		return ctrRank;
	}
	public void setCtrRank(int ctrRank) {
		this.ctrRank = ctrRank;
	}
	public int getCvrRank() {
		return cvrRank;
	}
	public void setCvrRank(int cvrRank) {
		this.cvrRank = cvrRank;
	}
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	public String getExchangeName() {
		return exchangeName;
	}
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

}
