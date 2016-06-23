package model;

import java.util.Comparator;

public class PubChurn {
	private int year;
	private int month;
	private long oldRequests;
	private long newRequests;
	private long y1m1requests;
	private long y1m2requests;
	private long y1m3requests;
	private long y1m4requests;
	private long y1m5requests;
	private long y1m6requests;
	private long y1m7requests;
	private long y1m8requests;
	private long y1m9requests;
	private long y1m10requests;
	private long y1m11requests;
	private long y1m12requests;
	private long y2m1requests;
	private long y2m2requests;
	private long y2m3requests;
	private long y2m4requests;
	private long y2m5requests;
	private long y2m6requests;
	private long y2m7requests;
	private long y2m8requests;
	private long y2m9requests;
	private long y2m10requests;
	private long y2m11requests;
	private long y2m12requests;
	private float oldrevenue;
	private float y1m1revenue;
	private float y1m2revenue;
	private float y1m3revenue;
	private float y1m4revenue;
	private float y1m5revenue;
	private float y1m6revenue;
	private float y1m7revenue;
	private float y1m8revenue;
	private float y1m9revenue;
	private float y1m10revenue;
	private float y1m11revenue;
	private float y1m12revenue;
	private float y2m1revenue;
	private float y2m2revenue;
	private float y2m3revenue;
	private float y2m4revenue;
	private float y2m5revenue;
	private float y2m6revenue;
	private float y2m7revenue;
	private float y2m8revenue;
	private float y2m9revenue;
	private float y2m10revenue;
	private float y2m11revenue;
	private float y2m12revenue;
	private double risk = 0.0;
	
	
	public double getRisk() {
		return risk;
	}
	public void setRisk(double risk) {
		this.risk = risk;
	}
	public float getOldrevenue() {
		return oldrevenue;
	}
	public void setOldrevenue(float oldrevenue) {
		this.oldrevenue = oldrevenue;
	}
	public float getY1m1revenue() {
		return y1m1revenue;
	}
	public void setY1m1revenue(float y1m1revenue) {
		this.y1m1revenue = y1m1revenue;
	}
	public float getY1m2revenue() {
		return y1m2revenue;
	}
	public void setY1m2revenue(float y1m2revenue) {
		this.y1m2revenue = y1m2revenue;
	}
	public float getY1m3revenue() {
		return y1m3revenue;
	}
	public void setY1m3revenue(float y1m3revenue) {
		this.y1m3revenue = y1m3revenue;
	}
	public float getY1m4revenue() {
		return y1m4revenue;
	}
	public void setY1m4revenue(float y1m4revenue) {
		this.y1m4revenue = y1m4revenue;
	}
	public float getY1m5revenue() {
		return y1m5revenue;
	}
	public void setY1m5revenue(float y1m5revenue) {
		this.y1m5revenue = y1m5revenue;
	}
	public float getY1m6revenue() {
		return y1m6revenue;
	}
	public void setY1m6revenue(float y1m6revenue) {
		this.y1m6revenue = y1m6revenue;
	}
	public float getY1m7revenue() {
		return y1m7revenue;
	}
	public void setY1m7revenue(float y1m7revenue) {
		this.y1m7revenue = y1m7revenue;
	}
	public float getY1m8revenue() {
		return y1m8revenue;
	}
	public void setY1m8revenue(float y1m8revenue) {
		this.y1m8revenue = y1m8revenue;
	}
	public float getY1m9revenue() {
		return y1m9revenue;
	}
	public void setY1m9revenue(float y1m9revenue) {
		this.y1m9revenue = y1m9revenue;
	}
	public float getY1m10revenue() {
		return y1m10revenue;
	}
	public void setY1m10revenue(float y1m10revenue) {
		this.y1m10revenue = y1m10revenue;
	}
	public float getY1m11revenue() {
		return y1m11revenue;
	}
	public void setY1m11revenue(float y1m11revenue) {
		this.y1m11revenue = y1m11revenue;
	}
	public float getY1m12revenue() {
		return y1m12revenue;
	}
	public void setY1m12revenue(float y1m12revenue) {
		this.y1m12revenue = y1m12revenue;
	}
	public float getY2m1revenue() {
		return y2m1revenue;
	}
	public void setY2m1revenue(float y2m1revenue) {
		this.y2m1revenue = y2m1revenue;
	}
	public float getY2m2revenue() {
		return y2m2revenue;
	}
	public void setY2m2revenue(float y2m2revenue) {
		this.y2m2revenue = y2m2revenue;
	}
	public float getY2m3revenue() {
		return y2m3revenue;
	}
	public void setY2m3revenue(float y2m3revenue) {
		this.y2m3revenue = y2m3revenue;
	}
	public float getY2m4revenue() {
		return y2m4revenue;
	}
	public void setY2m4revenue(float y2m4revenue) {
		this.y2m4revenue = y2m4revenue;
	}
	public float getY2m5revenue() {
		return y2m5revenue;
	}
	public void setY2m5revenue(float y2m5revenue) {
		this.y2m5revenue = y2m5revenue;
	}
	public float getY2m6revenue() {
		return y2m6revenue;
	}
	public void setY2m6revenue(float y2m6revenue) {
		this.y2m6revenue = y2m6revenue;
	}
	public float getY2m7revenue() {
		return y2m7revenue;
	}
	public void setY2m7revenue(float y2m7revenue) {
		this.y2m7revenue = y2m7revenue;
	}
	public float getY2m8revenue() {
		return y2m8revenue;
	}
	public void setY2m8revenue(float y2m8revenue) {
		this.y2m8revenue = y2m8revenue;
	}
	public float getY2m9revenue() {
		return y2m9revenue;
	}
	public void setY2m9revenue(float y2m9revenue) {
		this.y2m9revenue = y2m9revenue;
	}
	public float getY2m10revenue() {
		return y2m10revenue;
	}
	public void setY2m10revenue(float y2m10revenue) {
		this.y2m10revenue = y2m10revenue;
	}
	public float getY2m11revenue() {
		return y2m11revenue;
	}
	public void setY2m11revenue(float y2m11revenue) {
		this.y2m11revenue = y2m11revenue;
	}
	public float getY2m12revenue() {
		return y2m12revenue;
	}
	public void setY2m12revenue(float y2m12revenue) {
		this.y2m12revenue = y2m12revenue;
	}
	public void setY2m12revenue(long y2m12revenue) {
		this.y2m12revenue = y2m12revenue;
	}
	public long getY1m1requests() {
		return y1m1requests;
	}
	public void setY1m1requests(long y1m1requests) {
		this.y1m1requests = y1m1requests;
	}
	public long getY1m2requests() {
		return y1m2requests;
	}
	public void setY1m2requests(long y1m2requests) {
		this.y1m2requests = y1m2requests;
	}
	public long getY1m3requests() {
		return y1m3requests;
	}
	public void setY1m3requests(long y1m3requests) {
		this.y1m3requests = y1m3requests;
	}
	public long getY1m4requests() {
		return y1m4requests;
	}
	public void setY1m4requests(long y1m4requests) {
		this.y1m4requests = y1m4requests;
	}
	public long getY1m5requests() {
		return y1m5requests;
	}
	public void setY1m5requests(long y1m5requests) {
		this.y1m5requests = y1m5requests;
	}
	public long getY1m6requests() {
		return y1m6requests;
	}
	public void setY1m6requests(long y1m6requests) {
		this.y1m6requests = y1m6requests;
	}
	public long getY1m7requests() {
		return y1m7requests;
	}
	public void setY1m7requests(long y1m7requests) {
		this.y1m7requests = y1m7requests;
	}
	public long getY1m8requests() {
		return y1m8requests;
	}
	public void setY1m8requests(long y1m8requests) {
		this.y1m8requests = y1m8requests;
	}
	public long getY1m9requests() {
		return y1m9requests;
	}
	public void setY1m9requests(long y1m9requests) {
		this.y1m9requests = y1m9requests;
	}
	public long getY1m10requests() {
		return y1m10requests;
	}
	public void setY1m10requests(long y1m10requests) {
		this.y1m10requests = y1m10requests;
	}
	public long getY1m11requests() {
		return y1m11requests;
	}
	public void setY1m11requests(long y1m11requests) {
		this.y1m11requests = y1m11requests;
	}
	public long getY1m12requests() {
		return y1m12requests;
	}
	public void setY1m12requests(long y1m12requests) {
		this.y1m12requests = y1m12requests;
	}
	public long getY2m1requests() {
		return y2m1requests;
	}
	public void setY2m1requests(long y2m1requests) {
		this.y2m1requests = y2m1requests;
	}
	public long getY2m2requests() {
		return y2m2requests;
	}
	public void setY2m2requests(long y2m2requests) {
		this.y2m2requests = y2m2requests;
	}
	public long getY2m3requests() {
		return y2m3requests;
	}
	public void setY2m3requests(long y2m3requests) {
		this.y2m3requests = y2m3requests;
	}
	public long getY2m4requests() {
		return y2m4requests;
	}
	public void setY2m4requests(long y2m4requests) {
		this.y2m4requests = y2m4requests;
	}
	public long getY2m5requests() {
		return y2m5requests;
	}
	public void setY2m5requests(long y2m5requests) {
		this.y2m5requests = y2m5requests;
	}
	public long getY2m6requests() {
		return y2m6requests;
	}
	public void setY2m6requests(long y2m6requests) {
		this.y2m6requests = y2m6requests;
	}
	public long getY2m7requests() {
		return y2m7requests;
	}
	public void setY2m7requests(long y2m7requests) {
		this.y2m7requests = y2m7requests;
	}
	public long getY2m8requests() {
		return y2m8requests;
	}
	public void setY2m8requests(long y2m8requests) {
		this.y2m8requests = y2m8requests;
	}
	public long getY2m9requests() {
		return y2m9requests;
	}
	public void setY2m9requests(long y2m9requests) {
		this.y2m9requests = y2m9requests;
	}
	public long getY2m10requests() {
		return y2m10requests;
	}
	public void setY2m10requests(long y2m10requests) {
		this.y2m10requests = y2m10requests;
	}
	public long getY2m11requests() {
		return y2m11requests;
	}
	public void setY2m11requests(long y2m11requests) {
		this.y2m11requests = y2m11requests;
	}
	public long getY2m12requests() {
		return y2m12requests;
	}
	public void setY2m12requests(long y2m12requests) {
		this.y2m12requests = y2m12requests;
	}
	public static Comparator<PubChurn> getCOMPARE_BY_FIRST_MONTH() {
		return COMPARE_BY_FIRST_MONTH;
	}
	public static void setCOMPARE_BY_FIRST_MONTH(Comparator<PubChurn> cOMPARE_BY_FIRST_MONTH) {
		COMPARE_BY_FIRST_MONTH = cOMPARE_BY_FIRST_MONTH;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public long getOldRequests() {
		return oldRequests;
	}
	public void setOldRequests(long oldRequests) {
		this.oldRequests = oldRequests;
	}
	public long getNewRequests() {
		return newRequests;
	}
	public void setNewRequests(long newRequests) {
		this.newRequests = newRequests;
	}
	
	public static Comparator<PubChurn> COMPARE_BY_FIRST_MONTH = new Comparator<PubChurn>() {
        public int compare(PubChurn one, PubChurn other) {
        	int oneYear = one.getYear();
        	int oneMonth = one.getMonth();
        	int otherYear = other.getYear();
        	int otherMonth = other.getMonth();
            if(oneYear < otherYear)
            	return -1;
            else if(oneYear > otherYear)
            	return 1;
            else if(oneYear == otherYear && oneMonth < otherMonth)
            	return -1;
            else if(oneYear == otherYear && oneMonth > otherMonth)
            	return 1;
            else
            	return 0;
        }
    };
	

}
