package model;

import java.io.Serializable;

public class AdQuality implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bidder;
	private int verified;
	private int unverified;

	public String getBidder() {
		return bidder;
	}

	public void setBidder(String bidder) {
		this.bidder = bidder;
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

}
