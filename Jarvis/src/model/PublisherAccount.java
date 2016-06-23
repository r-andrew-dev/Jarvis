package model;

import java.util.Comparator;
import java.util.List;

public class PublisherAccount {
	private String publisherId = new String();
	private String publisherName = new String();
	private List<Site> sitesList;
	private int firstYear;
	private int firstMonth;
	private long requests;
	private float revenue;

	public long getRequests() {
		return requests;
	}

	public void setRequests(long requests) {
		this.requests = requests;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public int getFirstYear() {
		return firstYear;
	}

	public void setFirstYear(int firstYear) {
		this.firstYear = firstYear;
	}

	public int getFirstMonth() {
		return firstMonth;
	}

	public void setFirstMonth(int firstMonth) {
		this.firstMonth = firstMonth;
	}

	public List<Site> getSitesList() {
		return sitesList;
	}

	public void setSitesList(List<Site> sitesList) {
		this.sitesList = sitesList;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	public static Comparator<PublisherAccount> COMPARE_BY_REQS_DESC = new Comparator<PublisherAccount>() {
        public int compare(PublisherAccount one, PublisherAccount other) {
        	return new Long(other.requests).compareTo(new Long(one.requests));
        }
    };
    
    public static Comparator<PublisherAccount> COMPARE_BY_REV_DESC = new Comparator<PublisherAccount>() {
        public int compare(PublisherAccount one, PublisherAccount other) {
        	return new Float(other.revenue).compareTo(new Float(one.revenue));
        }
    };

}
