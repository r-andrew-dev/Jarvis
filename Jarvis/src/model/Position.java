package model;

import java.util.List;

public class Position {
	private String positionName;
	private List<Placement> tagsList;
	private boolean tierError = false;
	
	public boolean isTierError() {
		return tierError;
	}
	public void setTierError(boolean tierError) {
		this.tierError = tierError;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public List<Placement> getTagsList() {
		return tagsList;
	}
	public void setTagsList(List<Placement> tagsList) {
		this.tagsList = tagsList;
	}

}
