package model;

import java.io.Serializable;
import java.util.Comparator;

public class AdQuality implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bidder;
	private int verified;
	private int unverified;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

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
	
	public static Comparator<AdQuality> COMPARE_BY_DATE_ASC = new Comparator<AdQuality>() {
        public int compare(AdQuality one, AdQuality other) {
            return one.date.compareTo(other.date);
        }
    };

}
