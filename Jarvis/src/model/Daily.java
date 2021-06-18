// Take Down request

package model;

public class Daily {
	private String date = "";
	private float spend = 0;
	private int convs = 0;
	private int impressions = 0;
	private int clicks = 0;
	private int requests = 0;
	private int auctions = 0;
	private float costs = 0;
	private int verified;
	private int unverified;
	private int verifiedSeen;
	private int unverifiedSeen;

	public int getVerifiedSeen() {
		return verifiedSeen;
	}

	public void setVerifiedSeen(int verifiedSeen) {
		this.verifiedSeen = verifiedSeen;
	}

	public int getUnverifiedSeen() {
		return unverifiedSeen;
	}

	public void setUnverifiedSeen(int unverifiedSeen) {
		this.unverifiedSeen = unverifiedSeen;
	}

	public int getVerified() {
		return verified;
	}

	public void setVerified(int verified) {
		this.verified = verified;
	}

	public int getUnverified() {
		return unverified;
	}

	public void setUnverified(int unverified) {
		this.unverified = unverified;
	}

	public int getAuctions() {
		return auctions;
	}

	public void setAuctions(int auctions) {
		this.auctions = auctions;
	}

	public float getCosts() {
		return costs;
	}

	public void setCosts(float costs) {
		this.costs = costs;
	}

	public int getRequests() {
		return requests;
	}

	public void setRequests(int requests) {
		this.requests = requests;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public int getImpressions() {
		return impressions;
	}

	public void setImpressions(int impressions) {
		this.impressions = impressions;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getSpend() {
		return spend;
	}

	public void setSpend(float spend) {
		this.spend = spend;
	}

	public int getConvs() {
		return convs;
	}

	public void setConvs(int convs) {
		this.convs = convs;
	}

}
