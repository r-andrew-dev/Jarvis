package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Account;
import model.AdQuality;
import model.Daily;
import model.Exchange;
import model.GenericObject;
import model.Placement;
import model.Position;
import model.PublisherAccount;
import model.PublisherAccountManager;
import model.Site;
import model.Viewability;
import utils.ExcelHelper;
import utils.PropertiesHelper;

public class DataAccess {
	private static String CONNECTION_STRING = "jdbc:mysql://kyle-db-slave2.jumptap.com/tapmatch31?"
			+ "user=kbasu&password=5U4qJvYHdWqX3";

	private static String MMEDIA1_CONNECTION_STRING = "jdbc:mysql://mmedia.db.corp.millennialmedia.com:3306/mmedia?"
			+ "user=kbasu&password=mMKb46^feB";
	
	private static String MMEDIA_CONNECTION_STRING = "jdbc:mysql://db07.prod.mia.millennialmedia.com:3306/mmedia?"
			+ "user=kbasu&password=mMKb46^feB";

	private static String NEX1_CONNECTION_STRING = "jdbc:mysql://n4d201s.nexage.com:3306/core?"
			+ "user=kbasu&password=*rkqNBAgsu4x^jA@";

	
	private static String NEX1A_CONNECTION_STRING = "jdbc:mysql://192.168.100.158:3306/core?"
			+ "user=core_reader&password=read2011";
	 

	private static String NEX2_CONNECTION_STRING = "jdbc:mysql://n4d201s.nexage.com:5029/datawarehouse?"
			+ "user=kbasu&password=*rkqNBAgsu4x^jA@";

	
	private static String NEX2A_CONNECTION_STRING = "jdbc:mysql://192.168.100.116:5030/datawarehouse?" +
	 "user=dw_reader&password=read2011";
	 
	 private static String JARVIS_CONNECTION_STRING = "jdbc:mysql://10.172.98.67:3306/Jarvis?" +
			 "user=kbasu&password=password123";
	 

	private String salesReps = "'jparatore','ogillis','ddroddy','abennett','tyannopoulos','cserio','jschuerholz','hulloa'";
	private String mmediaReps = "'ddroddy@millennialmedia.com','jparatore@millennialmedia.com','ogillis@millennialmedia.com','abennett@millennialmedia.com','tyannopoulos@millennialmedia.com','jschuerholz@millennialmedia.com','hulloa@millennialmedia.com'";

	public List<Account> accounts = new ArrayList<Account>();
	
	private Connection getConnection(String type) {
		Connection conn = null;
		if(type.equals("nex_core")) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(NEX1_CONNECTION_STRING);
			} catch (Exception e) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					conn = DriverManager.getConnection(NEX1A_CONNECTION_STRING);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				
			} 
			
		} else if(type.equals("nex_dw")) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(NEX2_CONNECTION_STRING);
			} catch (Exception e) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					conn = DriverManager.getConnection(NEX2A_CONNECTION_STRING);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				
			} 
			
		} else if(type.equals("mmedia")) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(MMEDIA_CONNECTION_STRING);
			} catch (Exception e) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					conn = DriverManager.getConnection(MMEDIA1_CONNECTION_STRING);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				
			} 
			
		}
		
		return conn;
	}

	public void getPerformance() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PropertiesHelper ph = new PropertiesHelper();

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(CONNECTION_STRING);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select a.companyName, b.dailyBudget, round(sum(ad.costs)/1000000, 2),   "
					+ "DayBefore.spend, Last7.spend,  " + "case  " + "when Target.cpaTarget < 0.01 then ''  "
					+ "else Target.cpaTarget  " + "end cpaTarget,  "
					+ "ifnull(round((sum(ad.costs)/1000000)/sum(ad.conversions), 2),''), ifnull(DayBefore.cpa,''),  "
					+ "ifnull(Last7.cpa,''), a.id  "
					+ "from tapmatch31.DM_AccountDashboard ad, tapmatch31.Billing b, tapmatch31.TC_USER u,  "
					+ "tapmatch31.Account a left outer join "
					+ "(select a.id, a.companyName Account, round(sum(ad.costs)/1000000, 2) spend,  "
					+ "round((sum(ad.costs)/1000000)/sum(ad.conversions), 2) cpa  "
					+ "from tapmatch31.Account a, tapmatch31.DM_AccountDashboard ad, tapmatch31.TC_USER u "
					+ "where a.lineOfBusiness = 'PERFORMANCE'  " + "and u.id = a.salesExecutiveUserId "
					+ "and u.name in (" + salesReps + ") " + "and ad.accountId = a.id  "
					+ "and ad.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 2 day)  "
					+ "group by 1) DayBefore on a.id = DayBefore.id " + "left outer join "
					+ "(select a.id, a.companyName Account, round(sum(ad.costs)/1000000/7, 2) spend,  "
					+ "round((sum(ad.costs)/1000000)/sum(ad.conversions), 2) cpa  "
					+ "from tapmatch31.Account a, tapmatch31.DM_AccountDashboard ad, tapmatch31.TC_USER u "
					+ "where a.lineOfBusiness = 'PERFORMANCE'  " + "and u.id = a.salesExecutiveUserId "
					+ "and u.name in (" + salesReps + ") " + "and ad.accountId = a.id  "
					+ "and ad.time >= date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 7 day) and ad.time < date(convert_tz(now(), 'UTC', 'US/Eastern'))  "
					+ "group by 1) Last7 on a.id = Last7.id " + "left outer join "
					+ "(select temp.id, temp.companyName, ifnull(round(sum(temp.product)/sum(temp.convs), 2),'') cpaTarget  "
					+ "from  "
					+ "(select a.id, a.companyName, c.name, ifnull(c.cpaTarget,''), round(sum(cd.costs)/1000000, 2) spend, sum(cd.conversions) convs,  "
					+ "ifnull(c.cpaTarget*sum(cd.conversions),'') product  "
					+ "from tapmatch31.Account a, tapmatch31.Campaign c, tapmatch31.DM_CampaignDashboard cd, tapmatch31.TC_USER u "
					+ "where a.lineOfBusiness = 'PERFORMANCE'  " + "and u.id = a.salesExecutiveUserId "
					+ "and u.name in (" + salesReps + ") " + "and c.accountId = a.id  " + "and cd.campaignId = c.id  "
					+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 1 day)  "
					+ "group by a.companyName, c.name having spend > 0) temp  "
					+ "group by temp.id) Target on a.id = Target.id  " + "where a.lineOfBusiness = 'PERFORMANCE'  "
					+ "and u.id = a.salesExecutiveUserId " + "and u.name in (" + salesReps + ") "
					+ "and ad.accountId = a.id  "
					+ "and ad.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 1 day)  "
					+ "and b.id = a.billingId  " + "group by 10  " + "order by 3 desc");

			while (rs.next()) {
				Account a = new Account();
				a.setAccountName(rs.getString(1));
				a.setBudget(rs.getDouble(2));
				a.setYestSpend(rs.getDouble(3));
				a.setPrevSpend(rs.getDouble(4));
				a.setLast7Spend(rs.getDouble(5));
				a.setCpaTarget(rs.getDouble(6));
				a.setYestCpa(rs.getDouble(7));
				a.setPrevCpa(rs.getDouble(8));
				a.setLast7Cpa(rs.getDouble(9));
				a.setAccountId(rs.getInt(10));
				a.setPlatform("mmDSP");
				if (ph.isCpaAccount(Integer.toString(a.getAccountId())))
					updateCpaCampaigns(Integer.toString(a.getAccountId()), a);
				accounts.add(a);
			}
			getMmediaPerformance();
		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

	}

	public void getBrand() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PropertiesHelper ph = new PropertiesHelper();

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(CONNECTION_STRING);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select a.companyName, b.dailyBudget, round(sum(ad.costs)/1000000, 2),   "
					+ "DayBefore.spend, Last7.spend,  " + "case  " + "when Target.cpaTarget < 0.01 then ''  "
					+ "else Target.cpaTarget  " + "end cpaTarget,  "
					+ "ifnull(round((sum(ad.costs)/1000000)/sum(ad.conversions), 2),''), ifnull(DayBefore.cpa,''),  "
					+ "ifnull(Last7.cpa,''), a.id  "
					+ "from tapmatch31.DM_AccountDashboard ad, tapmatch31.Billing b, tapmatch31.TC_USER u,  "
					+ "tapmatch31.Account a left outer join "
					+ "(select a.id, a.companyName Account, round(sum(ad.costs)/1000000, 2) spend,  "
					+ "round((sum(ad.costs)/1000000)/sum(ad.conversions), 2) cpa  "
					+ "from tapmatch31.Account a, tapmatch31.DM_AccountDashboard ad, tapmatch31.TC_USER u "
					+ "where u.id = a.salesExecutiveUserId " + "and u.name not in (" + salesReps + ") "
					+ "and ad.accountId = a.id  "
					+ "and ad.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 2 day)  "
					+ "group by 1) DayBefore on a.id = DayBefore.id " + "left outer join "
					+ "(select a.id, a.companyName Account, round(sum(ad.costs)/1000000/7, 2) spend,  "
					+ "round((sum(ad.costs)/1000000)/sum(ad.conversions), 2) cpa  "
					+ "from tapmatch31.Account a, tapmatch31.DM_AccountDashboard ad, tapmatch31.TC_USER u "
					+ "where u.id = a.salesExecutiveUserId " + "and u.name not in (" + salesReps + ") "
					+ "and ad.accountId = a.id  "
					+ "and ad.time >= date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 7 day) and ad.time < date(convert_tz(now(), 'UTC', 'US/Eastern'))  "
					+ "group by 1) Last7 on a.id = Last7.id " + "left outer join "
					+ "(select temp.id, temp.companyName, ifnull(round(sum(temp.product)/sum(temp.convs), 2),'') cpaTarget  "
					+ "from  "
					+ "(select a.id, a.companyName, c.name, ifnull(c.cpaTarget,''), round(sum(cd.costs)/1000000, 2) spend, sum(cd.conversions) convs,  "
					+ "ifnull(c.cpaTarget*sum(cd.conversions),'') product  "
					+ "from tapmatch31.Account a, tapmatch31.Campaign c, tapmatch31.DM_CampaignDashboard cd, tapmatch31.TC_USER u "
					+ "where u.id = a.salesExecutiveUserId " + "and u.name not in (" + salesReps + ") "
					+ "and c.accountId = a.id  " + "and cd.campaignId = c.id  "
					+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 1 day)  "
					+ "group by a.companyName, c.name having spend > 0) temp  "
					+ "group by temp.id) Target on a.id = Target.id  " + "where u.id = a.salesExecutiveUserId "
					+ "and u.name not in (" + salesReps + ") " + "and ad.accountId = a.id  "
					+ "and ad.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 1 day)  "
					+ "and b.id = a.billingId  " + "group by 10  " + "order by 3 desc");

			while (rs.next()) {
				Account a = new Account();
				a.setAccountName(rs.getString(1));
				a.setBudget(rs.getDouble(2));
				a.setYestSpend(rs.getDouble(3));
				a.setPrevSpend(rs.getDouble(4));
				a.setLast7Spend(rs.getDouble(5));
				a.setCpaTarget(rs.getDouble(6));
				a.setYestCpa(rs.getDouble(7));
				a.setPrevCpa(rs.getDouble(8));
				a.setLast7Cpa(rs.getDouble(9));
				a.setAccountId(rs.getInt(10));
				a.setPlatform("mmDSP");
				if (ph.isCpaAccount(Integer.toString(a.getAccountId())))
					updateCpaCampaigns(Integer.toString(a.getAccountId()), a);
				accounts.add(a);
			}
			getMmediaBrand();
		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

	}

	public void getMmediaPerformance() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {


			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"select YestSpend.name, YestSpend.spend, DayBeforeSpend.spend, round(Last7Spend.spend/7, 2), 0, round(YestSpend.spend/YestConvs.convs, 2),  "
							+ "round(DayBeforeSpend.spend/DayBeforeConvs.convs, 2), round(Last7Spend.spend/Last7Convs.convs, 2), YestSpend.id "
							+ "from " + "(select a.id, a.name, round(sum(da.costs), 2) spend "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_costs da "
							+ "where u.username in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-1 "
							+ "group by 1 having spend > 0) YestSpend " + "left outer join "
							+ "(select a.id, a.name, round(sum(da.costs), 2) spend "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_costs da "
							+ "where u.username in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-2 "
							+ "group by 1 having spend > 0) DayBeforeSpend on DayBeforeSpend.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, round(sum(da.costs), 2) spend "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_costs da "
							+ "where u.username in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
							+ "group by 1 having spend > 0) Last7Spend on Last7Spend.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_conversions da "
							+ "where u.username in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-1 "
							+ "group by 1 having convs > 0) YestConvs on YestConvs.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_conversions da "
							+ "where u.username in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-2 "
							+ "group by 1 having convs > 0) DayBeforeConvs on DayBeforeConvs.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_conversions da "
							+ "where u.username in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
							+ "group by 1 having convs > 0) Last7Convs on Last7Convs.id = YestSpend.id");

			while (rs.next()) {
				Account a = new Account();
				a.setAccountName(rs.getString(1));
				a.setBudget(0.00);
				a.setYestSpend(rs.getDouble(2));
				a.setPrevSpend(rs.getDouble(3));
				a.setLast7Spend(rs.getDouble(4));
				a.setCpaTarget(rs.getDouble(5));
				a.setYestCpa(rs.getDouble(6));
				a.setPrevCpa(rs.getDouble(7));
				a.setLast7Cpa(rs.getDouble(8));
				a.setAccountId(rs.getInt(9));
				a.setPlatform("Mydas");
				if (a.getAccountId() == 4158)
					updatePlayphoneMmediaCampaigns(Integer.toString(a.getAccountId()), a);
				accounts.add(a);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

	}

	public void getMmediaBrand() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"select YestSpend.name, YestSpend.spend, DayBeforeSpend.spend, round(Last7Spend.spend/7, 2), 0, round(YestSpend.spend/YestConvs.convs, 2),  "
							+ "round(DayBeforeSpend.spend/DayBeforeConvs.convs, 2), round(Last7Spend.spend/Last7Convs.convs, 2), YestSpend.id "
							+ "from " + "(select a.id, a.name, round(sum(da.costs), 2) spend "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_costs da "
							+ "where u.username not in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-1 "
							+ "group by 1 having spend > 0) YestSpend " + "left outer join "
							+ "(select a.id, a.name, round(sum(da.costs), 2) spend "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_costs da "
							+ "where u.username not in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-2 "
							+ "group by 1 having spend > 0) DayBeforeSpend on DayBeforeSpend.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, round(sum(da.costs), 2) spend "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_costs da "
							+ "where u.username not in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
							+ "group by 1 having spend > 0) Last7Spend on Last7Spend.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_conversions da "
							+ "where u.username not in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-1 "
							+ "group by 1 having convs > 0) YestConvs on YestConvs.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_conversions da "
							+ "where u.username not in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-2 "
							+ "group by 1 having convs > 0) DayBeforeConvs on DayBeforeConvs.id = YestSpend.id "
							+ "left outer join " + "(select a.id, a.name, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.users u, mmedia.rollup_campaign_day_conversions da "
							+ "where u.username not in (" + mmediaReps + ") " + "and c.sales_user_id = u.id "
							+ "and a.id = c.advertiserid " + "and da.campaign_id = c.id "
							+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
							+ "group by 1 having convs > 0) Last7Convs on Last7Convs.id = YestSpend.id");

			while (rs.next()) {
				Account a = new Account();
				a.setAccountName(rs.getString(1));
				a.setBudget(0.00);
				a.setYestSpend(rs.getDouble(2));
				a.setPrevSpend(rs.getDouble(3));
				a.setLast7Spend(rs.getDouble(4));
				a.setCpaTarget(rs.getDouble(5));
				a.setYestCpa(rs.getDouble(6));
				a.setPrevCpa(rs.getDouble(7));
				a.setLast7Cpa(rs.getDouble(8));
				a.setAccountId(rs.getInt(9));
				a.setPlatform("Mydas");
				if (!a.getAccountName().equals("Nexage"))
					accounts.add(a);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

	}

	public void updateCpaCampaigns(String id, Account a) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		if (id.equals("15216") || id.equals("47374") || id.equals("44353") || id.equals("44966")) {

			try {

				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(CONNECTION_STRING);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from " + "(select sum(spend), sum(spend)/ sum(convs) " + "from "
						+ "(select sum(c.cpaTarget*cd.conversions) spend, sum(cd.conversions) convs "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and c.name regexp 'CPA' " + "and cd.campaignId = c.id "
						+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 1 day) "
						+ "union " + "select round(sum(cd.costs)/1000000, 2) spend, sum(cd.conversions) convs "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and c.name not regexp 'CPA' " + "and cd.campaignId = c.id "
						+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 1 day)) temp) yest, "
						+ "(select sum(spend), sum(spend)/ sum(convs) " + "from "
						+ "(select sum(c.cpaTarget*cd.conversions) spend, sum(cd.conversions) convs "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and c.name regexp 'CPA' " + "and cd.campaignId = c.id "
						+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 2 day) "
						+ "union " + "select round(sum(cd.costs)/1000000, 2) spend, sum(cd.conversions) convs "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and c.name not regexp 'CPA' " + "and cd.campaignId = c.id "
						+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 2 day)) temp) daybefore, "
						+ "(select sum(spend)/7, sum(spend)/ sum(convs) " + "from "
						+ "(select sum(c.cpaTarget*cd.conversions) spend, sum(cd.conversions) convs "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and c.name regexp 'CPA' " + "and cd.campaignId = c.id "
						+ "and cd.time >= date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 7 day) and cd.time < curdate() "
						+ "union " + "select round(sum(cd.costs)/1000000, 2) spend, sum(cd.conversions) convs "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and c.name not regexp 'CPA' " + "and cd.campaignId = c.id "
						+ "and cd.time >= date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 7 day) and cd.time < curdate()) temp) last7");

				while (rs.next()) {
					a.setYestSpend(rs.getDouble(1));
					a.setYestCpa(rs.getDouble(2));
					a.setPrevSpend(rs.getDouble(3));
					a.setPrevCpa(rs.getDouble(4));
					a.setLast7Spend(rs.getDouble(5));
					a.setLast7Cpa(rs.getDouble(6));
				}

			} catch (Exception ex) {

				ex.printStackTrace();

			} finally {

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) {
					}

					rs = null;
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					}

					stmt = null;
				}

			}

		}

		else if (id.equals("20344")) {

			try {

				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(CONNECTION_STRING);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from " + "(select sum(spend), avg(cpa) from " + "(select  "
						+ "case when c.id = 124621 then sum(6.5*cd.conversions) "
						+ "else sum(c.cpaTarget*cd.conversions)  " + "end as spend,  "
						+ "case when c.id = 124621 then 6.5 " + "else c.cpaTarget " + "end as cpa "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and cd.campaignId = c.id "
						+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 1 day) "
						+ "group by cpa) yt) yest, " + "(select sum(spend), avg(cpa) from " + "(select  "
						+ "case when c.id = 124621 then sum(6.5*cd.conversions) "
						+ "else sum(c.cpaTarget*cd.conversions)  " + "end as spend,  "
						+ "case when c.id = 124621 then 6.5 " + "else c.cpaTarget " + "end as cpa "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and cd.campaignId = c.id "
						+ "and cd.time = date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 2 day) "
						+ "group by cpa) dt) daybefore, " + "(select sum(spend)/7, avg(cpa) from " + "(select  "
						+ "case when c.id = 124621 then sum(6.5*cd.conversions) "
						+ "else sum(c.cpaTarget*cd.conversions)  " + "end as spend,  "
						+ "case when c.id = 124621 then 6.5 " + "else c.cpaTarget " + "end as cpa "
						+ "from tapmatch31.DM_CampaignDashboard cd, tapmatch31.Campaign c " + "where c.accountId = "
						+ Integer.parseInt(id) + " " + "and cd.campaignId = c.id "
						+ "and cd.time >= date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 7 day) and cd.time < curdate() "
						+ "group by cpa) lt) last7");

				while (rs.next()) {
					a.setYestSpend(rs.getDouble(1));
					a.setYestCpa(rs.getDouble(2));
					a.setPrevSpend(rs.getDouble(3));
					a.setPrevCpa(rs.getDouble(4));
					a.setLast7Spend(rs.getDouble(5));
					a.setLast7Cpa(rs.getDouble(6));
				}

			} catch (Exception ex) {

				ex.printStackTrace();

			} finally {

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) {
					}

					rs = null;
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					}

					stmt = null;
				}

			}

		}

	}

	public void updatePlayphoneMmediaCampaigns(String accountId, Account a) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"select sum(YestSpend.spend), sum(DayBeforeSpend.spend), round(sum(Last7Spend.spend)/7, 2), 0,  "
							+ "round(sum(YestSpend.spend)/sum(YestConvs.convs), 2), round(sum(DayBeforeSpend.spend)/sum(DayBeforeConvs.convs), 2), "
							+ "round(sum(Last7Spend.spend)/sum(Last7Convs.convs), 2) " + "from " + "(select c.id,  "
							+ "case " + "when c.id = 182627 then round(sum(dc.clicks)*0.3, 2) "
							+ "else round(sum(da.costs), 2) " + "end spend "
							+ "from mmedia.campaigns c, mmedia.rollup_campaign_day_costs da, mmedia.rollup_campaign_day_activity dc "
							+ "where c.advertiserid = 4158 " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-1 " + "and dc.campaign_id = c.id "
							+ "and dc.dayperiod = datediff(curdate(), '1970-01-01')-1 "
							+ "and dc.campaign_id = da.campaign_id " + "and dc.dayperiod = da.dayperiod "
							+ "group by 1 having spend > 0) YestSpend " + "left outer join " + "(select c.id,  "
							+ "case " + "when c.id = 182627 then round(sum(dc.clicks)*0.3, 2) "
							+ "else round(sum(da.costs), 2) " + "end spend "
							+ "from mmedia.campaigns c, mmedia.rollup_campaign_day_costs da, mmedia.rollup_campaign_day_activity dc "
							+ "where c.advertiserid = 4158 " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-2 " + "and dc.campaign_id = c.id "
							+ "and dc.dayperiod = datediff(curdate(), '1970-01-01')-2 "
							+ "and dc.campaign_id = da.campaign_id " + "and dc.dayperiod = da.dayperiod "
							+ "group by 1 having spend > 0) DayBeforeSpend on DayBeforeSpend.id = YestSpend.id "
							+ "left outer join " + "(select c.id,  " + "case "
							+ "when c.id = 182627 then round(sum(dc.clicks)*0.3, 2) " + "else round(sum(da.costs), 2) "
							+ "end spend "
							+ "from mmedia.campaigns c, mmedia.rollup_campaign_day_costs da, mmedia.rollup_campaign_day_activity dc "
							+ "where c.id = 182627 " + "and da.campaign_id = c.id "
							+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
							+ "and dc.campaign_id = c.id "
							+ "and dc.dayperiod >= datediff(curdate(), '1970-01-01')-7 and dc.dayperiod < datediff(curdate(), '1970-01-01') "
							+ "and dc.campaign_id = da.campaign_id " + "and dc.dayperiod = da.dayperiod "
							+ "group by 1 having spend > 0) Last7Spend on Last7Spend.id = YestSpend.id "
							+ "left outer join " + "(select c.id, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.rollup_campaign_day_conversions da "
							+ "where c.advertiserid = 4158 " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-1 "
							+ "group by 1 having convs > 0) YestConvs on YestConvs.id = YestSpend.id "
							+ "left outer join " + "(select c.id, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.rollup_campaign_day_conversions da "
							+ "where c.advertiserid = 4158 " + "and da.campaign_id = c.id "
							+ "and da.dayperiod = datediff(curdate(), '1970-01-01')-2 "
							+ "group by 1 having convs > 0) DayBeforeConvs on DayBeforeConvs.id = YestSpend.id "
							+ "left outer join " + "(select c.id, sum(da.conversions) convs "
							+ "from mmedia.campaigns c, mmedia.rollup_campaign_day_conversions da "
							+ "where c.advertiserid = 4158 " + "and da.campaign_id = c.id "
							+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
							+ "group by 1 having convs > 0) Last7Convs on Last7Convs.id = YestSpend.id");

			while (rs.next()) {
				a.setYestSpend(rs.getDouble(1));
				a.setPrevSpend(rs.getDouble(2));
				a.setLast7Spend(rs.getDouble(3));
				a.setCpaTarget(rs.getDouble(4));
				a.setYestCpa(rs.getDouble(5));
				a.setPrevCpa(rs.getDouble(6));
				a.setLast7Cpa(rs.getDouble(7));
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

	}

	public List<Daily> getAccountTrends(String id) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Daily> trends = new ArrayList<Daily>();

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(CONNECTION_STRING);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"select ad.time, round(sum(ad.costs)/1000000, 2), sum(ad.conversions), sum(ad.impressions), sum(ad.clicks) "
							+ "from tapmatch31.DM_AccountDashboard ad " + "where ad.accountId = " + Integer.parseInt(id)
							+ " "
							+ "and ad.time >= date_sub(date(convert_tz(now(), 'UTC', 'US/Eastern')), interval 7 day) and ad.time < date(convert_tz(now(), 'UTC', 'US/Eastern')) "
							+ "group by 1 order by 1");

			while (rs.next()) {
				Daily d = new Daily();
				d.setDate(rs.getString(1));
				d.setSpend(rs.getInt(2));
				d.setConvs(rs.getInt(3));
				d.setImpressions(rs.getInt(4));
				d.setClicks(rs.getInt(5));
				trends.add(d);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return trends;
	}

	public List<Daily> getGreenAccountTrends(String id) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Daily> trends = new ArrayList<Daily>();

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select costs.date, costs.spend, conversions.convs, activity.views, activity.clicks "
					+ "from "
					+ "(select adddate('1970-01-01', interval da.dayperiod day) date, round(sum(da.costs), 2) spend "
					+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.rollup_campaign_day_costs da "
					+ "where a.id = " + Integer.parseInt(id) + " " + "and c.advertiserid = a.id "
					+ "and da.campaign_id = c.id "
					+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
					+ "group by 1 order by 1) costs " + "left outer join "
					+ "(select adddate('1970-01-01', interval da.dayperiod day) date, sum(da.conversions) convs "
					+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.rollup_campaign_day_conversions da "
					+ "where a.id = " + Integer.parseInt(id) + " " + "and c.advertiserid = a.id "
					+ "and da.campaign_id = c.id "
					+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
					+ "group by 1 order by 1) conversions on costs.date = conversions.date " + "left outer join "
					+ "(select adddate('1970-01-01', interval da.dayperiod day) date, sum(da.views) views, sum(da.clicks) clicks "
					+ "from mmedia.campaigns c, mmedia.advertisers a, mmedia.rollup_campaign_day_activity da "
					+ "where a.id = " + Integer.parseInt(id) + " " + "and c.advertiserid = a.id "
					+ "and da.campaign_id = c.id "
					+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
					+ "group by 1 order by 1) activity on costs.date = activity.date");

			while (rs.next()) {
				Daily d = new Daily();
				d.setDate(rs.getString(1));
				d.setSpend(rs.getInt(2));
				d.setConvs(rs.getInt(3));
				d.setImpressions(rs.getInt(4));
				d.setClicks(rs.getInt(5));
				trends.add(d);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return trends;
	}

	public List<Exchange> getExchanges() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Exchange> data = new ArrayList<Exchange>();
		String query = "select id, name from tapmatch31.PublisherNetwork where id not in (1,3,5,9)";

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(CONNECTION_STRING);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Exchange e = new Exchange();
				e.setExchangeId(rs.getString(1));
				e.setExchangeName(rs.getString(2));
				data.add(e);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return data;

	}

	public List<Exchange> getExchangeRanks() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Exchange> data = new ArrayList<Exchange>();

		String startDate = "";
		String endDate = "";
		String year = "";

		int prevQuarter = (Calendar.getInstance().get(Calendar.MONTH) / 3);
		switch (prevQuarter) {
		case 3:
			year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			startDate = "07-01";
			endDate = "09-30";
		case 2:
			year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			startDate = "04-01";
			endDate = "06-30";
		case 1:
			year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			startDate = "01-01";
			endDate = "03-31";
		case 0:
		default:
			year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1);
			startDate = "10-01";
			endDate = "12-31";
		}

		startDate = year + "-" + startDate;
		endDate = year + "-" + endDate;

		String query = "select temp.*, n.name " + "from tapmatch31.PublisherNetwork n, "
				+ "(select od.publisherNetworkId, sum(od.networkChargesUSD)/1000000 costs, sum(od.impressions) imps, sum(od.clicks) clicks, sum(od.conversions) convs "
				+ "from tapmatch31.DM_OptimizationDashboard od " + "where od.time between '" + startDate + "' and '"
				+ endDate + "' " + "and od.publisherNetworkId not in (1,3,5,9) " + "group by 1) temp "
				+ "where n.id = temp.publisherNetworkId ";

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(CONNECTION_STRING);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Exchange e = new Exchange();
				e.setExchangeId(rs.getString(1));
				e.setCosts(rs.getFloat(2));
				e.setImpressions(rs.getLong(3));
				e.setClicks(rs.getLong(4));
				e.setConversions(rs.getLong(5));
				e.setExchangeName(rs.getString(6));
				e.setEcpm(e.getCosts() / e.getImpressions() * 1000);
				e.setCtr((float) e.getClicks() / (float) e.getImpressions());
				e.setCvr((float) e.getConversions() / (float) e.getClicks());
				data.add(e);
			}

			for (int i = 0; i < data.size(); i++) {
				Exchange currExchange = data.get(i);
				for (int j = 0; j < data.size(); j++) {
					Exchange nextExchange = data.get(j);
					if (nextExchange.getCosts() > currExchange.getCosts()) {
						int currRank = currExchange.getCostsRank();
						currExchange.setCostsRank(currRank + 1);
					}
					if (nextExchange.getImpressions() > currExchange.getImpressions()) {
						int currRank = currExchange.getImpsRank();
						currExchange.setImpsRank(currRank + 1);
					}
					if (nextExchange.getClicks() > currExchange.getClicks()) {
						int currRank = currExchange.getClicksRank();
						currExchange.setClicksRank(currRank + 1);
					}
					if (nextExchange.getConversions() > currExchange.getConversions()) {
						int currRank = currExchange.getConvsRank();
						currExchange.setConvsRank(currRank + 1);
					}
					if (nextExchange.getEcpm() > currExchange.getEcpm()) {
						int currRank = currExchange.getEcpmRank();
						currExchange.setEcpmRank(currRank + 1);
					}
					if (nextExchange.getCtr() > currExchange.getCtr()) {
						int currRank = currExchange.getCtrRank();
						currExchange.setCtrRank(currRank + 1);
					}
					if (nextExchange.getCvr() > currExchange.getCvr()) {
						int currRank = currExchange.getCvrRank();
						currExchange.setCvrRank(currRank + 1);
					}
				}
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return data;

	}

	public List<PublisherAccountManager> getPubAccountManagers() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<PublisherAccountManager> amsList = new ArrayList<PublisherAccountManager>();

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select distinct amid, amname " + "from "
					+ "(select am.id amid, am.name amname, p.id pid, p.name pname "
					+ "from mmedia.publishers p, mmedia.account_managers am " + "where p.account_manager_id = am.id "
					+ "and am.name <> 'None' and p.active = 1) temp order by amname");

			while (rs.next()) {
				PublisherAccountManager am = new PublisherAccountManager();
				am.setAccountManagerId(rs.getString(1));
				am.setAccountManagerName(rs.getString(2));
				amsList.add(am);

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}
		return amsList;
	}

	public List<PublisherAccount> getPubAccounts(String amId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, PublisherAccount> accountsMap = new HashMap<String, PublisherAccount>();

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select p.id, p.name, s.id, s.name, sum(da.views) views "
					+ "from mmedia.publishers p, mmedia.sites s, mmedia.rollup_site_country_carrier_day_activity da "
					+ "where p.account_manager_id = " + amId + " " + "and s.publisherid = p.id  " + "and s.active = 1  "
					+ "and p.active = 1 " + "and da.site_id = s.id "
					+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
					+ "group by 3 having views > 99");

			while (rs.next()) {
				String publisherId = rs.getString(1);
				String publisherName = rs.getString(2);
				String siteId = rs.getString(3);
				String siteName = rs.getString(4);
				if (!accountsMap.containsKey(publisherId)) {
					PublisherAccount acc = new PublisherAccount();
					acc.setPublisherId(publisherId);
					acc.setPublisherName(publisherName);
					List<Site> sitesList = new ArrayList<Site>();
					Site site = new Site();
					site.setSiteId(siteId);
					site.setSiteName(siteName);
					sitesList.add(site);
					acc.setSitesList(sitesList);
					accountsMap.put(publisherId, acc);
				} else {
					PublisherAccount acc = accountsMap.get(publisherId);
					List<Site> sitesList = acc.getSitesList();
					Site site = new Site();
					site.setSiteId(siteId);
					site.setSiteName(siteName);
					sitesList.add(site);
					acc.setSitesList(sitesList);
					accountsMap.put(publisherId, acc);
				}

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}
		Set<String> accKeys = accountsMap.keySet();
		List<PublisherAccount> accountsList = new ArrayList<PublisherAccount>();
		for (Iterator<String> key = accKeys.iterator(); key.hasNext();) {
			PublisherAccount acc = accountsMap.get(key.next());
			accountsList.add(acc);
		}
		return accountsList;
	}

	public Site getSiteData(String siteId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Site s = new Site();
		List<Placement> placementsList = new ArrayList<Placement>();

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select s.id, s.name,  " + "case  "
					+ "when s.website like '%itunes.apple.com%' then substring_index(substring(s.website, locate('/id', s.website)+3), '?', 1)  "
					+ "when s.website like '%play.google.com%' then substring_index(substring(s.website, locate('id=', s.website)+3),'&',1)  "
					+ "when s.website like '%market.android.com%' then substring_index(substring(s.website, locate('id=', s.website)+3),'&',1)  "
					+ "else s.website  "
					+ "end appBundle, p.id, p.name, concat(dim.width,'x',dim.height), sum(da.views) views, " + "case  "
					+ "when s.website like '%itunes.apple.com%' then 'iOS'  "
					+ "when s.website like '%play.google.com%' then 'Android'  "
					+ "when s.website like '%market.android.com%' then 'Android'  " + "else 'Mobile Web'  " + "end os "
					+ "from mmedia.sites s, mmedia.placements p, mmedia.ad_dimensions_placements adp, mmedia.ad_dimensions dim, mmedia.rollup_placement_campaign_day_activity da "
					+ "where s.id = " + siteId + " " + "and p.siteid = s.id " + "and p.active = 1 "
					+ "and adp.placement_id = p.id " + "and dim.id = adp.ad_dimension_id "
					+ "and da.placement_id = p.id "
					+ "and da.dayperiod >= datediff(curdate(), '1970-01-01')-7 and da.dayperiod < datediff(curdate(), '1970-01-01') "
					+ "group by 4 having views > 9");

			while (rs.next()) {
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				s.setBundleId(rs.getString(3));
				s.setOs(rs.getString(8));
				Placement p = new Placement();
				p.setPlacementId(rs.getString(4));
				p.setPlacementName(rs.getString(5));
				String dim = rs.getString(6);
				if (dim.equals("-1x-1"))
					p.setPlacementDimension("Interstitial");
				else
					p.setPlacementDimension(dim);
				placementsList.add(p);
			}

			s.setPlacementsList(placementsList);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return s;
	}

	public Placement getPlacementData(String placementId, String dimension, String bundleId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Placement p = new Placement();
		p.setPlacementId(placementId);
		p.setPlacementDimension(dimension);

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select p.name from mmedia.placements p where p.id = " + placementId + " ");

			while (rs.next()) {
				p.setPlacementName(rs.getString(1));
			}

			p.setGreenTrends(getGreenSiteVolume(p.getPlacementId()));
			p.setNexTrends(getNexSiteVolume(bundleId, dimension));

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return p;
	}

	public List<Daily> getGreenSiteVolume(String placementId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Daily> sites = new ArrayList<Daily>();
		String query = "select requests.date, requests.reqs, round(costs.spend, 0), impressions.imps " + "from "
				+ "(select p.id, p.name, adddate('1970-01-01', interval r.dayperiod day) date, sum(r.views) reqs  "
				+ "from mmedia.rollup_placement_campaign_day_activity r, mmedia.placements p  "
				+ "where r.placement_id = p.id " + "and p.id = " + placementId + " "
				+ "and r.dayperiod >= datediff(curdate(), '1970-01-01')-7 and r.dayperiod < datediff(curdate(), '1970-01-01') "
				+ "group by 3) requests  " + "left outer join "
				+ "(select p.id, p.name, adddate('1970-01-01', interval r.dayperiod day) date, sum(r.costs) spend  "
				+ "from mmedia.rollup_placement_campaign_day_costs r, mmedia.placements p  "
				+ "where r.placement_id = p.id " + "and p.id = " + placementId + " "
				+ "and r.dayperiod >= datediff(curdate(), '1970-01-01')-7 and r.dayperiod < datediff(curdate(), '1970-01-01') "
				+ "group by 3) costs on requests.date = costs.date  " + "left outer join "
				+ "(select p.id, p.name, adddate('1970-01-01', interval r.dayperiod day) date, sum(r.views) imps  "
				+ "from mmedia.rollup_placement_campaign_day_activity r, mmedia.campaigns c, mmedia.placements p  "
				+ "where r.placement_id = p.id " + "and p.id = " + placementId + " "
				+ "and r.dayperiod >= datediff(curdate(), '1970-01-01')-7 and r.dayperiod < datediff(curdate(), '1970-01-01') "
				+ "and c.id = r.campaign_id and c.campaign_category_id in (1, 2, 4) "
				+ "and c.origin_type_id = 1 group by 3) impressions on requests.date = impressions.date";

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Daily s = new Daily();
				s.setDate(rs.getString(1));
				s.setRequests(rs.getInt(2));
				s.setSpend(rs.getInt(3));
				s.setImpressions(rs.getInt(4));
				sites.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sites;

	}

	public List<Daily> getNexSiteVolume(String bundleId, String dimension) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Daily> sites = new ArrayList<Daily>();

		String siteIdList = "";
		List<String> siteIds = getNexSitesFromBundle(bundleId);
		for (Iterator<String> i = siteIds.iterator(); i.hasNext();) {
			siteIdList = siteIdList + i.next() + ",";
		}

		if (siteIdList.length() > 0)
			siteIdList = siteIdList.substring(0, siteIdList.length() - 1);
		else
			return sites;

		String tagIdList = "";
		List<String> tagIds = getNexTagsFromSite(siteIdList, dimension);

		for (Iterator<String> i = tagIds.iterator(); i.hasNext();) {
			tagIdList = tagIdList + i.next() + ",";
		}

		if (tagIdList.length() > 0)
			tagIdList = tagIdList.substring(0, tagIdList.length() - 1);
		else
			return sites;

		String query = "select requests.date, requests.reqs, round(costs.rev, 0), requests.imps " + "from "
				+ "(select date(f.start) date, sum(f.ads_requested) reqs, sum(f.ads_served) imps "
				+ "from datawarehouse.fact_traffic_site f "
				+ "where f.start >= date_sub(curdate(), interval 7 day) and f.start < curdate() " + "and f.site_id in ("
				+ siteIdList + ") " + "group by 1) requests " + "left outer join "
				+ "(select date(f.start) date, sum(f.revenue) rev " + "from datawarehouse.fact_exchange_wins f "
				+ "where f.start >= date_sub(curdate(), interval 7 day) and f.start < curdate() " + "and f.site_id in ("
				+ siteIdList + ") " + "group by 1) costs on requests.date = costs.date";

		try {

			conn = getConnection("nex_dw");   
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Daily s = new Daily();
				s.setDate(rs.getString(1));
				s.setRequests(rs.getInt(2));
				s.setSpend(rs.getInt(3));
				s.setImpressions(rs.getInt(4));
				sites.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sites;

	}

	public Map<String, PublisherAccount> getGreenPubFirstRequest() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, PublisherAccount> pubsMap = new HashMap<String, PublisherAccount>();
		String query = "select r.publisher_id, " + "year(adddate('1970-01-01', interval r.monthperiod month)), "
				+ "month(adddate('1970-01-01', interval r.monthperiod month)) "
				+ "from mmedia.rollup_publisher_country_carrier_month_activity r " + "group by 1";

		try {

			conn = getConnection("mmedia");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				PublisherAccount p = new PublisherAccount();
				p.setPublisherId(rs.getString(1));
				p.setFirstYear(rs.getInt(2));
				p.setFirstMonth(rs.getInt(3));
				pubsMap.put(p.getPublisherId(), p);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return pubsMap;

	}
	
	public float getGreenPubChurnRevenue(String pubId, int year, int month) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		float rev = 0;
		String query = "select sum(r.costs) costs "
						+"from mmedia.rollup_publisher_carrier_campaign_month_costs r "
						+"where r.publisher_id = ? "
						+"and year(adddate('1970-01-01', interval r.monthperiod month)) = ? "
						+"and month(adddate('1970-01-01', interval r.monthperiod month)) = ?";

		try {

			conn = getConnection("mmedia");
			stmt = conn.prepareStatement(query);
			stmt.setString(1, pubId);
			stmt.setInt(2, year);
			stmt.setInt(3, month);
			rs = stmt.executeQuery();

			while (rs.next()) {
				rev = rs.getFloat(1);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return rev;

	}

	
	public Map<String, PublisherAccount> getNexPubFirstRequest() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, PublisherAccount> pubsMap = new HashMap<String, PublisherAccount>();
		String query = "select * "
						+"from "
						+"(select c.pid, year(r.REVTSTMP), month(r.REVTSTMP), c.name "
						+"from core.company_aud c, core.revinfo r "
						+"where c.type = 'SELLER' "
						+"and r.REV = c.REV "
						+"order by 2,3) temp group by 1";

		try {

			
			conn = getConnection("nex_core");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				PublisherAccount p = new PublisherAccount();
				p.setPublisherId(rs.getString(1));
				p.setFirstYear(rs.getInt(2));
				p.setFirstMonth(rs.getInt(3));
				p.setPublisherName(rs.getString(4));
				pubsMap.put(p.getPublisherId(), p);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return pubsMap;

	}

	public List<String> getNexSitesFromBundle(String appBundle) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> siteIds = new ArrayList<String>();
		String query = "select pid from core.site " + "where app_bundle = '" + appBundle + "' ";

		try {

			
			conn = getConnection("nex_core");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				siteIds.add(rs.getString(1));
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return siteIds;

	}

	public List<String> getNexTagsFromSite(String siteIds, String dimension) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> tagIds = new ArrayList<String>();
		String query = "select * from (select p.site_id, t.id tag_id, concat(convert(p.width,char), 'x', convert(p.height, char)) dim "
				+ "from datawarehouse.dim_position p, datawarehouse.dim_tag t " + "where t.site_id in (" + siteIds
				+ ") " + "and p.id = t.position_id) temp ";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				tagIds.add(rs.getString(2));
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return tagIds;

	}

	public Site getTagsWithMultipleAuctions(String siteId, String dateRange) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Site s = new Site();
		List<Placement> tags = new ArrayList<Placement>();
		String query = "select s.id, s.name, auctions.id, auctions.name, auctions.aucs, round(auctions.aucs*0.003/1000,2) cost, "
				+ "case when s.name like 'MM-%' then round(rev.grossRevenue,2) else round(rev.netRevenue,2) end rev, rev.imps, round(rev.grossRevenue/rev.imps*1000, 2) ecpm "
				+ "from " + "(select a.site_id, t.id, t.name, sum(a.auctions) aucs "
				+ "from datawarehouse.fact_exchange_auctions a, datawarehouse.dim_tag t "
				+ "where a.start > date_sub(curdate(), interval " + dateRange + " day) and a.start < curdate() "
				+ "and a.site_id = " + siteId + " " + "and t.rtb_profile_id = a.tag_id " + "group by 2) auctions,  "
				+ "(select a.site_id, a.tag_id id, sum(a.revenue_net) netRevenue, sum(a.revenue) grossRevenue, sum(a.ads_delivered) imps "
				+ "from datawarehouse.fact_exchange_wins a " + "where a.start > date_sub(curdate(), interval "
				+ dateRange + " day) and a.start < curdate() " + "and a.site_id = " + siteId + " "
				+ "group by 2) rev, datawarehouse.dim_site s " + "where auctions.id = rev.id "
				+ "and s.id = auctions.site_id " + "group by 3  ";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			boolean getTrends = true;

			Map<String, String[]> tagTiers = getTagTiers();

			while (rs.next()) {
				Placement tag = new Placement();
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				if (getTrends) {
					getSiteMultipleAuctionsTrends(s, dateRange);
					getTrends = false;
				}
				tag.setPlacementId(rs.getString(3));
				tag.setPlacementName(rs.getString(4));
				tag.setCost(0.002361 * rs.getLong(5) / 1000);
				tag.setRev(rs.getDouble(7));
				tag.setTotalImpressions(rs.getInt(8));
				tag.setAvgEcpm(rs.getFloat(9));
				tag.setMargin(tag.getRev() - tag.getCost());
				if (tagTiers.keySet().contains(tag.getPlacementId())) {
					tag.setPosition(tagTiers.get(tag.getPlacementId())[0]);
					tag.setTier(tagTiers.get(tag.getPlacementId())[1]);
				} else {
					tag.setPosition("");
					tag.setTier("");
				}
				tags.add(tag);
			}

			s.setPlacementsList(tags);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return s;

	}

	public void getSiteMultipleAuctionsTrends(Site s, String dateRange) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Daily> trends = new ArrayList<Daily>();
		String query = "select requests.d, requests.h, requests.m, requests.reqs, auctions.aucs, auctions.costs, round(revenue.rev, 2), revenue.served, revenue.delivered "
				+ "from " + "(select date(a.start) d, hour(a.start) h, minute(a.start) m,  sum(a.ads_requested) reqs  "
				+ "from datawarehouse.fact_traffic_site a  " + "where a.start > date_sub(curdate(), interval "
				+ dateRange + " day) and a.start < curdate()  " + "and a.site_id = " + s.getSiteId() + " "
				+ "group by 1,2,3 " + "order by 1,2,3) requests, "
				+ "(select date(a.start) d, hour(a.start) h, minute(a.start) m, sum(a.auctions) aucs, round(sum(a.auctions)/1000*0.003, 2) costs "
				+ "from datawarehouse.fact_exchange_auctions a  " + "where a.start > date_sub(curdate(), interval "
				+ dateRange + " day) and a.start < curdate()  " + "and a.site_id = " + s.getSiteId() + " "
				+ "group by 1,2,3 " + "order by 1,2,3) auctions, "
				+ "(select date(a.start) d, hour(a.start) h, minute(a.start) m,  " + "case  "
				+ "when s.name like 'MM-%' then sum(a.revenue) " + "else sum(a.revenue_net) "
				+ "end rev, sum(a.ads_served) served, sum(a.ads_delivered) delivered "
				+ "from datawarehouse.fact_exchange_wins a, datawarehouse.dim_site s "
				+ "where a.start > date_sub(curdate(), interval " + dateRange + " day) and a.start < curdate() "
				+ "and a.site_id = " + s.getSiteId() + " " + "and s.id = a.site_id " + "group by 1,2,3 "
				+ "order by 1,2,3) revenue " + "where requests.d = auctions.d " + "and requests.h = auctions.h "
				+ "and requests.m = auctions.m " + "and requests.d = revenue.d " + "and requests.h = revenue.h "
				+ "and requests.m = revenue.m";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Daily d = new Daily();
				d.setDate(rs.getString(1) + "-" + rs.getString(2) + ":" + rs.getString(3));
				d.setRequests(rs.getInt(4));
				d.setAuctions(rs.getInt(5));
				d.setCosts(rs.getInt(6));
				d.setSpend(rs.getInt(7));
				d.setImpressions(rs.getInt(8));
				trends.add(d);
			}

			s.setNexTrends(trends);
			s.setCountryReqs(getTargetedReqs(s.getSiteId(), "country", dateRange));
			s.setCarrierReqs(getTargetedReqs(s.getSiteId(), "carrier", dateRange));
			s.setCountryWins(getCountryWins(s.getSiteId(), dateRange));

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}
	}

	public List<GenericObject> getCountryWins(String siteId, String dateRange) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<GenericObject> targetWins = new ArrayList<GenericObject>();
		String query = "select  " + "case " + "when s.country = '???' then 'XXX' " + "else s.country "
				+ "end target, sum(s.ads_served) wins " + "from datawarehouse.fact_exchange_wins s "
				+ "where s.start > date_sub(curdate(), interval " + dateRange + " day) and s.start < curdate() "
				+ "and s.site_id = " + siteId + " " + "group by 1 " + "order by wins desc ";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				GenericObject obj = new GenericObject();
				obj.setAttribute(rs.getString(1));
				obj.setRequests(rs.getLong(2));
				targetWins.add(obj);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return targetWins;
	}

	public List<GenericObject> getTargetedReqs(String siteId, String target, String dateRange) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<GenericObject> targetReqs = new ArrayList<GenericObject>();
		String query = "select  " + "case " + "when s." + target + " = '???' then 'XXX' " + "else s." + target + " "
				+ "end target, sum(s.ads_requested) reqs " + "from datawarehouse.fact_traffic_targeted_site s "
				+ "where s.start > date_sub(curdate(), interval " + dateRange + " day) and s.start < curdate() "
				+ "and s.site_id = " + siteId + " " + "group by 1 " + "order by reqs desc ";

		if (!target.equals("country"))
			query = query + "limit 10";
		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				GenericObject obj = new GenericObject();
				obj.setAttribute(rs.getString(1));
				obj.setRequests(rs.getLong(2));
				targetReqs.add(obj);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return targetReqs;
	}

	public Map<String, String[]> getTagTiers() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String[]> tiers = new HashMap<String, String[]>();
		String query = "select t.pid, p.name, tr.level+1, tt.tag_order "
				+"from core.tag t, core.tier_tag tt, core.tier tr, core.position p "
				+"where t.pid = tt.tag_pid "
				+"and tr.pid = tt.tier_pid and p.pid = t.position_pid ";

		try {

			
			conn = getConnection("nex_core");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				String[] values = new String[2];
				values[0] = rs.getString(2);
				values[1] = rs.getString(3);
				tiers.put(rs.getString(1), values);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return tiers;
	}
	
	public List<Site> getTierOptData() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, List<Placement>> sites = new HashMap<String, List<Placement>>();
		List<Site> masterSitesList = new ArrayList<Site>();
		Map<String, String[]> tagTiers = getTagTiers();
		
		String query = "select t.id, t.name, s.name, sum(f.revenue) rev, "
						+"round(sum(f.revenue)/sum(f.ads_delivered)*1000, 2) ecpm, sum(f.ads_delivered) "
						+"from datawarehouse.dim_tag t, datawarehouse.dim_site s, datawarehouse.fact_revenue_adnet f "
						+"where s.id = t.site_id "
						+"and f.tag_id = t.id "
						+"and date(f.start) > date_sub(curdate(), interval 7 day) "
						+"group by 1 having rev > 9.99 order by s.name";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String site = rs.getString(3);
				Placement t = new Placement();
				t.setPlacementId(rs.getString(1));
				t.setPlacementName(rs.getString(2));
				t.setRev(rs.getDouble(4));
				t.setAvgEcpm(rs.getFloat(5));
				t.setTotalImpressions(rs.getInt(6));
				if(tagTiers.containsKey(t.getPlacementId()))
					site = site + "&&" + tagTiers.get(t.getPlacementId())[0];
				
				List<Placement> tags;
				if(sites.containsKey(site)) {
					tags = sites.get(site);
				} else {
					tags = new ArrayList<Placement>();
				}
				
				if(tagTiers.containsKey(t.getPlacementId())) {
					t.setTier(tagTiers.get(t.getPlacementId())[1]);
					tags.add(t);			
				}
				sites.put(site, tags);
			}
			
			Set<String> siteNames = sites.keySet();
			
			for(Iterator<String> i = siteNames.iterator(); i.hasNext(); ) {
				String sName = i.next();
				if(sName.indexOf("&&") > 0) {
					Site site = new Site();
					site.setSiteName(sName.substring(0, sName.indexOf("&&")));

					Position pos = new Position();
					pos.setPositionName(sName.substring(sName.indexOf("&&")+2, sName.length()));

					pos.setTagsList(sites.get(sName));
					site.setPosition(pos);
					
					masterSitesList.add(site);
				}
			}
			
			masterSitesList = getTierErrors(masterSitesList);
			masterSitesList = combineMasterList(masterSitesList);
			
		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return masterSitesList;
	}
	
	private List<Site> combineMasterList(List<Site> sites) {
		List<Site> sitesToReturn = new ArrayList<Site>();
		Map<String, List<Position>> sitesMap = new HashMap<String, List<Position>>();
		for(Iterator<Site> i = sites.iterator(); i.hasNext(); ) {
			Site currSite = i.next();
			String siteName = currSite.getSiteName();
			if(!sitesMap.containsKey(siteName)) {
				List<Position> posList = new ArrayList<Position>();
				posList.add(currSite.getPosition());
				sitesMap.put(siteName, posList);
			} else {
				List<Position> posList = sitesMap.get(siteName);
				posList.add(currSite.getPosition());
				sitesMap.put(siteName, posList);
			}
		}
		
		Set<String> siteKeys = sitesMap.keySet();
		for(Iterator<String> i = siteKeys.iterator(); i.hasNext(); ) {
			String siteName = i.next();
			Site s = new Site();
			s.setSiteName(siteName);
			s.setPositionsList(combinePositions(sitesMap.get(siteName)));
			sitesToReturn.add(s);
		}
		
		return sitesToReturn;
	}
	
	private List<Position> combinePositions(List<Position> positions) {
		List<Position> positionsToReturn = new ArrayList<Position>();
		Map<String, List<Placement>> positionsMap = new HashMap<String, List<Placement>>();
		for(Iterator<Position> i = positions.iterator(); i.hasNext(); ) {
			Position currPos = i.next();
			String posName = currPos.getPositionName();
			if(!positionsMap.containsKey(posName)) {
				List<Placement> tagsList = new ArrayList<Placement>();
				tagsList.addAll(currPos.getTagsList());
				positionsMap.put(posName, tagsList);
			} else {
				List<Placement> tagsList = new ArrayList<Placement>();
				tagsList.addAll(currPos.getTagsList());
				positionsMap.put(posName, tagsList);
			}
		}
		
		Set<String> posKeys = positionsMap.keySet();
		for(Iterator<String> i = posKeys.iterator(); i.hasNext(); ) {
			String posName = i.next();
			Position p = new Position();
			p.setPositionName(posName);
			p.setTagsList(positionsMap.get(posName));
			positionsToReturn.add(p);
		}
		
		return positionsToReturn;
	}
	
	private List<Site> getTierErrors(List<Site> tags) {
		List<Site> sitesToReturn = new ArrayList<Site>();
		for(Iterator<Site> i = tags.iterator(); i.hasNext(); ) {
			Site s = i.next();
			Position p = s.getPosition();
			List<Placement> tagsList = p.getTagsList(); 
			Map<Integer, Float[]> tierMap = new TreeMap<Integer, Float[]>();
			for(Iterator<Placement> j = tagsList.iterator(); j.hasNext(); ) {
				Placement t = j.next(); 
				Float[] ecpms;
				if(!tierMap.containsKey(Integer.parseInt(t.getTier()))) {
					ecpms = new Float[2];
					ecpms[0] = t.getAvgEcpm();
					ecpms[1] = t.getAvgEcpm();
					tierMap.put(Integer.parseInt(t.getTier()), ecpms);
				} else {
					ecpms = tierMap.get(Integer.parseInt(t.getTier()));
					if(t.getAvgEcpm() < ecpms[0])
						ecpms[0] = t.getAvgEcpm();
					if(t.getAvgEcpm() > ecpms[1])
						ecpms[1] = t.getAvgEcpm();
					tierMap.put(Integer.parseInt(t.getTier()), ecpms);
				}
				
				Integer tier = 1;
				Integer nextTier = tier + 1;
				while(tierMap.containsKey(tier) && tierMap.containsKey(nextTier)) {		
					Float[] tierEcpms = tierMap.get(tier);
					Float[] nextTierEcpms = tierMap.get(nextTier);
					if(nextTierEcpms[1] > tierEcpms[0]) {
						s.setTierErrorFlag(true);
						sitesToReturn.add(s);
					}
					
					tier = tier + 1;
					nextTier = tier + 1;
				}
						
			}
			
			/*Integer tier = 1;
			Integer nextTier = tier + 1;
			while(tierMap.containsKey(tier) && tierMap.containsKey(nextTier)) {		
				Float[] tierEcpms = tierMap.get(tier);
				Float[] nextTierEcpms = tierMap.get(nextTier);
				if(nextTierEcpms[1] > tierEcpms[0] && !s.isTierErrorFlag()) {
					s.setTierErrorFlag(true);
					sitesToReturn.add(s);
				}
				
				tier = tier + 1;
				nextTier = tier + 1;
			}*/
			
		}
		
		return sitesToReturn;
		
	}

	public List<Site> getSitesWithMultipleAuctions(String dateRange) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Site> sites = new ArrayList<Site>();
		String query = "select s.id, s.name, requests.reqs, auctions.aucs, round(auctions.aucs/requests.reqs,1) ratio, round(requests.reqs*0.003/1000,2) cost,  "
				+ "case " + "when s.name like 'MM-%' then round(rev.grossRevenue,2)  " + "else round(rev.netRevenue,2) "
				+ "end rev, rev.imps, round(rev.grossRevenue/rev.imps*1000, 2) ecpm " + "from "
				+ "(select a.site_id,  sum(a.ads_requested) reqs " + "from datawarehouse.fact_traffic_site a "
				+ "where a.start > date_sub(curdate(), interval " + dateRange + " day) and a.start < curdate() "
				+ "group by 1) requests, " + "(select a.site_id, sum(a.auctions) aucs "
				+ "from datawarehouse.fact_exchange_auctions a " + "where a.start > date_sub(curdate(), interval "
				+ dateRange + " day) and a.start < curdate() " + "group by 1) auctions,  "
				+ "(select a.site_id, sum(a.revenue_net) netRevenue, sum(a.revenue) grossRevenue, sum(a.ads_delivered) imps "
				+ "from datawarehouse.fact_exchange_wins a " + "where a.start > date_sub(curdate(), interval "
				+ dateRange + " day) and a.start < curdate() " + "group by 1) rev, datawarehouse.dim_site s "
				+ "where requests.site_id = auctions.site_id " + "and requests.site_id = rev.site_id "
				+ "and s.id = requests.site_id " + "group by 1 having ratio > 1 and cost > 0";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Site s = new Site();
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				s.setRatio(rs.getFloat(5));
				s.setCost(rs.getFloat(6));
				s.setRev(rs.getFloat(7));
				s.setTotalImpressions(rs.getInt(8));
				s.setAvgEcpm(rs.getFloat(9));
				s.setMargin(s.getRev() - s.getCost());
				sites.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sites;

	}
	
	public String getNexPubName(String id) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String name = new String();
		String query = "select name from datawarehouse.dim_company where id = ?";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.prepareStatement(query);
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				name = rs.getString(1);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return name;

	}
	
	public String getGreenPubName(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String name = new String();
		String query = "select name from mmedia.publishers where id = ?";

		try {

			conn = getConnection("mmedia");
			stmt = conn.prepareStatement(query);
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				name = rs.getString(1);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return name;

	}
	
	public List<Site> getOOSitesMTD() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Site> sitesList = new ArrayList<Site>();
		String query = "select s.id, s.name, t.name, sum(f.ads_requested_adnet), sum(f.ads_served), sum(f.ads_delivered),  "
						+"sum(f.ads_clicked), sum(f.revenue) rev "
						+"from datawarehouse.dim_site s, datawarehouse.fact_revenue_adnet f, datawarehouse.dim_ad_source_type t "
						+"where s.name like '%aol%' "
						+"and f.site_id = s.id "
						+"and date(f.start) >= date_format(now(), '%Y-%m-01') "
						+"and t.id = f.source_type "
						+"group by 1,3 having rev > 0 "
						+"union "
						+"select s.id, s.name, t.name, sum(f.ads_requested_adnet), sum(f.ads_served), sum(f.ads_delivered),  "
						+"sum(f.ads_clicked), sum(f.revenue) rev "
						+"from datawarehouse.dim_site s, datawarehouse.fact_revenue_adnet f, datawarehouse.dim_ad_source_type t "
						+"where s.name like '%verizon%' "
						+"and f.site_id = s.id "
						+"and date(f.start) >= date_format(now(), '%Y-%m-01') "
						+"and t.id = f.source_type "
						+"group by 1,3 having rev > 0 ";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Site s = new Site();
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				s.setAdSourceType(rs.getString(3));
				s.setTotalRequests(rs.getInt(4));
				s.setTotalCleared(rs.getInt(5));
				s.setTotalImpressions(rs.getInt(6));
				s.setTotalClicked(rs.getInt(7));
				s.setRev(rs.getFloat(8));
				sitesList.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sitesList;

	}
	
	public List<Site> getTagsMTD(String siteId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Site> tagsList = new ArrayList<Site>();
		String query = "select s.id, s.name, t.name, sum(f.ads_requested_adnet), sum(f.ads_served), sum(f.ads_delivered),  "
						+"sum(f.ads_clicked), sum(f.revenue) rev "
						+"from datawarehouse.dim_tag s, datawarehouse.fact_revenue_adnet f, datawarehouse.dim_ad_source_type t "
						+"where s.site_id = " + siteId + " "
						+"and f.tag_id = s.id "
						+"and date(f.start) >= date_format(now(), '%Y-%m-01') "
						+"and t.id = f.source_type "
						+"group by 1,3 having rev > 0 "
						+"order by rev desc";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Site s = new Site();
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				s.setAdSourceType(rs.getString(3));
				s.setTotalRequests(rs.getInt(4));
				s.setTotalCleared(rs.getInt(5));
				s.setTotalImpressions(rs.getInt(6));
				s.setTotalClicked(rs.getInt(7));
				s.setRev(rs.getFloat(8));
				tagsList.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return tagsList;

	}
	
	public List<Site> getOOSiteDaily(String siteId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Site> sitesList = new ArrayList<Site>();
		String query = "select s.id, s.name, date(f.start), t.name, sum(f.ads_requested_adnet), sum(f.ads_served), sum(f.ads_delivered), sum(f.ads_clicked), sum(f.revenue) rev "
						+"from datawarehouse.dim_site s, datawarehouse.fact_revenue_adnet f, datawarehouse.dim_ad_source_type t "
						+"where s.id = "+siteId+" "
						+"and f.site_id = s.id "
						+"and date(f.start) >= date_format(now(), '%Y-%m-01') "
						+"and t.id = f.source_type "
						+"group by 1,3,4 having rev > 0 "
						+"order by 3";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Site s = new Site();
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				s.setDate(rs.getString(3));
				s.setAdSourceType(rs.getString(4));
				s.setTotalRequests(rs.getInt(5));
				s.setTotalCleared(rs.getInt(6));
				s.setTotalImpressions(rs.getInt(7));
				s.setTotalClicked(rs.getInt(8));
				s.setRev(rs.getFloat(9));
				sitesList.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sitesList;

	}
	
	public List<Site> getOOTagDaily(String tagId) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Site> sitesList = new ArrayList<Site>();
		String query = "select s.id, s.name, date(f.start), t.name, sum(f.ads_requested_adnet), sum(f.ads_served), sum(f.ads_delivered), sum(f.ads_clicked), sum(f.revenue) rev "
						+"from datawarehouse.dim_tag s, datawarehouse.fact_revenue_adnet f, datawarehouse.dim_ad_source_type t "
						+"where s.id = "+tagId+" "
						+"and f.tag_id = s.id "
						+"and date(f.start) >= date_format(now(), '%Y-%m-01') "
						+"and t.id = f.source_type "
						+"group by 1,3,4 having rev > 0 "
						+"order by 3";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Site s = new Site();
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				s.setDate(rs.getString(3));
				s.setAdSourceType(rs.getString(4));
				s.setTotalRequests(rs.getInt(5));
				s.setTotalCleared(rs.getInt(6));
				s.setTotalImpressions(rs.getInt(7));
				s.setTotalClicked(rs.getInt(8));
				s.setRev(rs.getFloat(9));
				sitesList.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sitesList;

	}
	
	public List<Site> getNewTags() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Site> sitesList = new ArrayList<Site>();
		String query = "select temp.tagName, temp.siteName, temp.firstDate "
						+"from "
						+"(select t.name tagName, s.name siteName, date(min(t.updated_on)) firstDate "
						+"from core.tag_aud t, core.site s "
						+"where t.site_pid = s.pid "
						+"and t.name not like 'Z-TEST%' "
						+"group by 1) temp "
						+"where temp.firstDate > date_sub(curdate(), interval 7 day) "
						+"order by temp.firstdate";

		try {

			
			conn = getConnection("nex_core");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Site s = new Site();
				s.setSiteId(rs.getString(1));
				s.setSiteName(rs.getString(2));
				s.setDate(rs.getString(3));
				sitesList.add(s);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sitesList;

	}
	
	public List<AdQuality> getAdQualityData() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<AdQuality> biddersList = new ArrayList<AdQuality>();
		String query = "select v.name, v.count, u.count "
						+"from "
						+"(select c.name, count(cv.status) count "
						+"from core.company c, core.creative_verification cv "
						+"where c.type = 'BUYER' "
						+"and cv.buyer_pid = c.pid "
						+"and cv.status = 1 "
						+"group by 1) v, "
						+"(select c.name, count(cv.status) count "
						+"from core.company c, core.creative_verification cv "
						+"where c.type = 'BUYER' "
						+"and cv.buyer_pid = c.pid "
						+"and cv.status = 2 "
						+"group by 1) u "
						+"where v.name = u.name";

		try {

			
			conn = getConnection("nex_core");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				AdQuality aq = new AdQuality();
				aq.setBidder(rs.getString(1));
				aq.setVerified(rs.getInt(2));
				aq.setUnverified(rs.getInt(3));
				biddersList.add(aq);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return biddersList;

	}
	
	public List<AdQuality> getBidderDailyData(String bidder) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<AdQuality> biddersList = new ArrayList<AdQuality>();
		String query = "select v.date, v.count, u.count "
						+"from "
						+"(select date(cv.last_update) date, count(cv.status) count "
						+"from core.company c, core.creative_verification cv "
						+"where c.name = '"+bidder+"' "
						+"and cv.buyer_pid = c.pid "
						+"and cv.status = 1 "
						+"group by 1) v left outer join "
						+"(select date(cv.last_update) date, count(cv.status) count "
						+"from core.company c, core.creative_verification cv "
						+"where c.name = '"+bidder+"' "
						+"and cv.buyer_pid = c.pid "
						+"and cv.status = 2 "
						+"group by 1) u on v.date = u.date "
						+"order by v.date desc limit 30";

		try {

			
			conn = getConnection("nex_core");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				AdQuality aq = new AdQuality();
				aq.setDate(rs.getString(1));
				aq.setVerified(rs.getInt(2));
				aq.setUnverified(rs.getInt(3));
				biddersList.add(aq);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}
		
		Collections.sort(biddersList, AdQuality.COMPARE_BY_DATE_ASC);
		return biddersList;

	}
	
	public List<Daily> getAdQualityDailyData() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Daily> dailyData = new ArrayList<Daily>();
		String query = "select v.date, v.count, u.count "
						+"from "
						+"(select date(cv.last_update) date, count(cv.status) count "
						+"from core.creative_verification cv "
						+"where date(cv.last_update) >= date_sub(curdate(), interval 30 day) "
						+"and cv.status = 1 "
						+"group by 1) v, "
						+"(select date(cv.last_update) date, count(cv.status) count "
						+"from core.creative_verification cv "
						+"where date(cv.last_update) >= date_sub(curdate(), interval 30 day) "
						+"and cv.status = 2 "
						+"group by 1) u "
						+"where v.date = u.date";

		try {

			
			conn = getConnection("nex_core");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Daily d = new Daily();
				d.setDate(rs.getString(1));
				d.setVerified(rs.getInt(2));
				d.setUnverified(rs.getInt(3));
				dailyData.add(d);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return dailyData;

	}
	
	public List<Daily> getNativeData() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Daily> dailyData = new ArrayList<Daily>();
		String query = "select DATE(a11.start) NX_FACTREVENUEADNETDATE, "
				+ "sum(a11.ads_requested_site) NXINBOUNDREQUESTS, sum(a11.revenue) NXREVENUE "
+"from datawarehouse.fact_revenue_adnet a11 join datawarehouse.dim_position a12 "
+ "on (a11.site_id = a12.site_id and a11.zone = a12.name) "
+"where a12.video_support in (3) "
+"and DATE(a11.start) >= date_sub(curdate(), interval 30 day) "
+"and a11.tag_monetization in (-1, 1) "
+"group by 1 "
+"order by 1";

		try {

			
			conn = getConnection("nex_dw");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Daily d = new Daily();
				d.setDate(rs.getString(1));
				String reqs = rs.getString(2);
				d.setRequests(Integer.parseInt(reqs));
				String rev = rs.getString(3);
				d.setSpend(Float.parseFloat(rev));
				dailyData.add(d);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return dailyData;

	}
	
	public List<Viewability> getViewabilityData(String path) throws IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Viewability> sitesList = new ArrayList<Viewability>();
		String query = "select * from Jarvis.Exchange_Viewability";
		ExcelHelper eh = new ExcelHelper(path);
		Map<String, String> ams = eh.readPubAMs();

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(JARVIS_CONNECTION_STRING);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Viewability v = new Viewability();
				v.setNexPubName(rs.getString(1));
				v.setAcctMgr(ams.get(v.getNexPubName()));
				v.setNexSiteName(rs.getString(2));
				v.setNexAlias(rs.getString(3));
				v.setDspSiteId(rs.getString(4));
				v.setCreativeType(rs.getString(5));
				v.setCreativeSize(rs.getString(6));
				v.setOs(rs.getString(7));
				v.setInvType(rs.getString(8));
				v.setImpsAnalyzed(rs.getInt(9));
				v.setImpsDelivered(rs.getInt(10));
				v.setImpsDiscrepancy(rs.getString(11));
				v.setMeasuredRate(rs.getString(12));
				v.setViewScore(rs.getString(13));
				v.setDailyAvails(rs.getInt(14));
				v.setDateLastTested(rs.getString(15));
				v.setPartner(rs.getString(16));
				v.setPlatform("Exchange");
				sitesList.add(v);
			}
			
			getNetworkViewabilityData(path, sitesList);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sitesList;

	}
	
	public List<Viewability> getNetworkViewabilityData(String path, List<Viewability> sitesList) throws IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String query = "select * from Jarvis.Network_Viewability";
		ExcelHelper eh = new ExcelHelper(path);
		Map<String, String> ams = eh.readPubAMs();
		
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(JARVIS_CONNECTION_STRING);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Viewability v = new Viewability();
				v.setNexPubName(rs.getString(1));
				v.setAcctMgr(ams.get(v.getNexPubName()));
				v.setPubId(rs.getString(2));
				v.setNexSiteName(rs.getString(3));
				v.setDspSiteId(rs.getString(4));
				v.setPlacementName(rs.getString(5));
				v.setApid(rs.getString(6));
				v.setCreativeType(rs.getString(7));
				v.setCreativeSize(rs.getString(8));
				v.setOs(rs.getString(9));
				v.setInvType(rs.getString(10));
				v.setImpsAnalyzed(rs.getInt(11));
				v.setImpsDelivered(rs.getInt(12));
				v.setImpsDiscrepancy(rs.getString(13));
				v.setMeasuredRate(rs.getString(14));
				v.setViewScore(rs.getString(15));
				v.setDailyAvails(rs.getInt(16));
				v.setDateLastTested(rs.getString(17));
				v.setPartner(rs.getString(18));
				v.setPlatform("Network");
				sitesList.add(v);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				}

				stmt = null;
			}

		}

		return sitesList;

	}

}
