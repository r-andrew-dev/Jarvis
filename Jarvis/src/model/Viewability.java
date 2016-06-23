package model;

import java.io.Serializable;

public class Viewability implements Serializable {

	private static final long serialVersionUID = 3508889690199331832L;

	private String acctMgr;
	private String nexPubName;
	private String nexSiteName;
	private String nexAlias;
	private String dspSiteId;
	private String creativeType;
	private String creativeSize;
	private String os;
	private String invType;
	private int impsAnalyzed;
	private int impsDelivered;
	private String impsDiscrepancy;
	private String measuredRate;
	private String viewScore;
	private int dailyAvails;
	private String dateLastTested;
	private String partner;
	private String platform;
	private String pubId;
	private String placementName;
	private String apid;

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getPlacementName() {
		return placementName;
	}

	public void setPlacementName(String placementName) {
		this.placementName = placementName;
	}

	public String getApid() {
		return apid;
	}

	public void setApid(String apid) {
		this.apid = apid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getAcctMgr() {
		return acctMgr;
	}

	public void setAcctMgr(String acctMgr) {
		this.acctMgr = acctMgr;
	}

	public String getNexPubName() {
		return nexPubName;
	}

	public void setNexPubName(String nexPubName) {
		this.nexPubName = nexPubName;
	}

	public String getNexSiteName() {
		return nexSiteName;
	}

	public void setNexSiteName(String nexSiteName) {
		this.nexSiteName = nexSiteName;
	}

	public String getNexAlias() {
		return nexAlias;
	}

	public void setNexAlias(String nexAlias) {
		this.nexAlias = nexAlias;
	}

	public String getDspSiteId() {
		return dspSiteId;
	}

	public void setDspSiteId(String dspSiteId) {
		this.dspSiteId = dspSiteId;
	}

	public String getCreativeType() {
		return creativeType;
	}

	public void setCreativeType(String creativeType) {
		this.creativeType = creativeType;
	}

	public String getCreativeSize() {
		return creativeSize;
	}

	public void setCreativeSize(String creativeSize) {
		this.creativeSize = creativeSize;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
	}

	public int getImpsAnalyzed() {
		return impsAnalyzed;
	}

	public void setImpsAnalyzed(int impsAnalyzed) {
		this.impsAnalyzed = impsAnalyzed;
	}

	public int getImpsDelivered() {
		return impsDelivered;
	}

	public void setImpsDelivered(int impsDelivered) {
		this.impsDelivered = impsDelivered;
	}

	public String getImpsDiscrepancy() {
		return impsDiscrepancy;
	}

	public void setImpsDiscrepancy(String impsDiscrepancy) {
		this.impsDiscrepancy = impsDiscrepancy;
	}

	public String getMeasuredRate() {
		return measuredRate;
	}

	public void setMeasuredRate(String measuredRate) {
		this.measuredRate = measuredRate;
	}

	public String getViewScore() {
		return viewScore;
	}

	public void setViewScore(String viewScore) {
		this.viewScore = viewScore;
	}

	public int getDailyAvails() {
		return dailyAvails;
	}

	public void setDailyAvails(int dailyAvails) {
		this.dailyAvails = dailyAvails;
	}

	public String getDateLastTested() {
		return dateLastTested;
	}

	public void setDateLastTested(String dateLastTested) {
		this.dateLastTested = dateLastTested;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
