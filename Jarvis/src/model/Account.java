package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Account implements Serializable {
	private static final long serialVersionUID = 5204556986353432263L;
	
	private String accountName = null;
	private Double budget = 0.00;
	private Double yestSpend = 0.00;
	private Double prevSpend = 0.00;
	private Double last7Spend = 0.00;
	private Double cpaTarget = 0.00;
	private Double yestCpa = 0.00;
	private Double prevCpa = 0.00;
	private Double last7Cpa = 0.00;
	private int accountId = 0;
	private int fundsLeft = 0;
	private Double latency = 0.00;
	private List<Daily> trends = new ArrayList<Daily>();
	private List<Hourly> hourlyTrends = new ArrayList<Hourly>();
	private List<Weekly> weeklyTrends = new ArrayList<Weekly>();
	private List<Monthly> monthlyTrends = new ArrayList<Monthly>();
	private String platform = null;
	
	public List<Monthly> getMonthlyTrends() {
		return monthlyTrends;
	}
	public void setMonthlyTrends(List<Monthly> monthlyTrends) {
		this.monthlyTrends = monthlyTrends;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Double getBudget() {
		return budget;
	}
	public void setBudget(Double budget) {
		this.budget = budget;
	}
	public Double getYestSpend() {
		return yestSpend;
	}
	public void setYestSpend(Double yestSpend) {
		this.yestSpend = yestSpend;
	}
	public Double getPrevSpend() {
		return prevSpend;
	}
	public void setPrevSpend(Double prevSpend) {
		this.prevSpend = prevSpend;
	}
	public Double getLast7Spend() {
		return last7Spend;
	}
	public void setLast7Spend(Double last7Spend) {
		this.last7Spend = last7Spend;
	}
	public Double getCpaTarget() {
		return cpaTarget;
	}
	public void setCpaTarget(Double cpaTarget) {
		this.cpaTarget = cpaTarget;
	}
	public Double getYestCpa() {
		return yestCpa;
	}
	public void setYestCpa(Double yestCpa) {
		this.yestCpa = yestCpa;
	}
	public Double getPrevCpa() {
		return prevCpa;
	}
	public void setPrevCpa(Double prevCpa) {
		this.prevCpa = prevCpa;
	}
	public Double getLast7Cpa() {
		return last7Cpa;
	}
	public void setLast7Cpa(Double last7Cpa) {
		this.last7Cpa = last7Cpa;
	}
	public int getFundsLeft() {
		return fundsLeft;
	}
	public void setFundsLeft(int fundsLeft) {
		this.fundsLeft = fundsLeft;
	}


	public List<Daily> getTrends() {
		return trends;
	}
	public void setTrends(List<Daily> trends) {
		this.trends = trends;
	}


	public List<Hourly> getHourlyTrends() {
		return hourlyTrends;
	}
	public void setHourlyTrends(List<Hourly> hourlyTrends) {
		this.hourlyTrends = hourlyTrends;
	}


	public List<Weekly> getWeeklyTrends() {
		return weeklyTrends;
	}
	public void setWeeklyTrends(List<Weekly> weeklyTrends) {
		this.weeklyTrends = weeklyTrends;
	}


	public Double getLatency() {
		return latency;
	}
	public void setLatency(Double latency) {
		this.latency = latency;
	}

	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public static Comparator<Account> COMPARE_BY_PLATFORM_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.platform.toUpperCase().compareTo(one.platform.toUpperCase());
        }
    };
    
	public static Comparator<Account> COMPARE_BY_PLATFORM_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.platform.toUpperCase().compareTo(other.platform.toUpperCase());
        }
    };

	public static Comparator<Account> COMPARE_BY_NAME_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.accountName.toUpperCase().compareTo(one.accountName.toUpperCase());
        }
    };
    
	public static Comparator<Account> COMPARE_BY_NAME_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.accountName.toUpperCase().compareTo(other.accountName.toUpperCase());
        }
    };
    
    public static Comparator<Account> COMPARE_BY_BUDGET_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.budget.compareTo(one.budget);
        }
    };
    
	public static Comparator<Account> COMPARE_BY_BUDGET_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.budget.compareTo(other.budget);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_YSPEND_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.yestSpend.compareTo(one.yestSpend);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_YSPEND_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.yestSpend.compareTo(other.yestSpend);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_PSPEND_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.prevSpend.compareTo(one.prevSpend);
        }
    };
    
	public static Comparator<Account> COMPARE_BY_PSPEND_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.prevSpend.compareTo(other.prevSpend);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_LSPEND_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.last7Spend.compareTo(one.last7Spend);
        }
    };
    
	public static Comparator<Account> COMPARE_BY_LSPEND_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.last7Spend.compareTo(other.last7Spend);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_TARGET_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.cpaTarget.compareTo(one.cpaTarget);
        }
    };
    
	public static Comparator<Account> COMPARE_BY_TARGET_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.cpaTarget.compareTo(other.cpaTarget);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_YCPA_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.yestCpa.compareTo(one.yestCpa);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_YCPA_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.yestCpa.compareTo(other.yestCpa);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_PCPA_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.prevCpa.compareTo(one.prevCpa);
        }
    };
    
	public static Comparator<Account> COMPARE_BY_PCPA_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.prevCpa.compareTo(other.prevCpa);
        }
    };
    
    public static Comparator<Account> COMPARE_BY_LCPA_DESC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return other.last7Cpa.compareTo(one.last7Cpa);
        }
    };
    
	public static Comparator<Account> COMPARE_BY_LCPA_ASC = new Comparator<Account>() {
        public int compare(Account one, Account other) {
            return one.last7Cpa.compareTo(other.last7Cpa);
        }
    };
	
}
