package model;

import java.util.List;

public class PublisherAccountManager {
	private String accountManagerId;
	private String accountManagerName;
	private List<PublisherAccount> accountsList;

	public String getAccountManagerId() {
		return accountManagerId;
	}

	public void setAccountManagerId(String accountManagerId) {
		this.accountManagerId = accountManagerId;
	}

	public String getAccountManagerName() {
		return accountManagerName;
	}

	public void setAccountManagerName(String accountManagerName) {
		this.accountManagerName = accountManagerName;
	}

	public List<PublisherAccount> getAccountsList() {
		return accountsList;
	}

	public void setAccountsList(List<PublisherAccount> accountsList) {
		this.accountsList = accountsList;
	}

}
