package utils;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;

import data.DataAccess;
import model.Bidder;
import model.Exchange;
import model.PubChurn;
import model.PublisherAccount;
import model.SDK;
import model.Viewability;

public class ExcelHelper {

	private String path = "";

	public ExcelHelper(String path) {
		if (path.equals("local"))
			this.path = "/users/kbasu/Desktop/";
		else if (path.equals("mac"))
			this.path = "/users/kbasu15/Desktop/Jarvis_Data/";
		else
			this.path = "/home/kbasu/report/";
	}

	public List<Exchange> readExchangeHighLevel() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "exchangeOverview.csv"));
		List<Exchange> exchanges = new ArrayList<Exchange>();
		String[] nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Exchange e = new Exchange();
			e.setExchangeId(nextLine[0]);
			e.setExchangeName(nextLine[5]);
			e.setCosts(Float.parseFloat(nextLine[1]));
			e.setImpressions(Long.parseLong(nextLine[2]));
			e.setClicks(Long.parseLong(nextLine[3]));
			e.setConversions(Long.parseLong(nextLine[4]));
			e.setEcpm(e.getCosts() / e.getImpressions() * 1000);
			e.setCtr((float) e.getClicks() / (float) e.getImpressions());
			e.setCvr((float) e.getConversions() / (float) e.getClicks());
			exchanges.add(e);
		}

		reader.close();
		

		for (int i = 0; i < exchanges.size(); i++) {
			Exchange currExchange = exchanges.get(i);
			for (int j = 0; j < exchanges.size(); j++) {
				Exchange nextExchange = exchanges.get(j);
				if (nextExchange.getCosts() > currExchange.getCosts()) {
					currExchange.setCostsRank(currExchange.getCostsRank() + 1);
				}
				if (nextExchange.getImpressions() > currExchange.getImpressions()) {
					currExchange.setImpsRank(currExchange.getImpsRank() + 1);
				}
				if (nextExchange.getClicks() > currExchange.getClicks()) {
					currExchange.setClicksRank(currExchange.getClicksRank() + 1);
				}
				if (nextExchange.getConversions() > currExchange.getConversions()) {
					currExchange.setConvsRank(currExchange.getConvsRank() + 1);
				}
				if (nextExchange.getEcpm() > currExchange.getEcpm()) {
					currExchange.setEcpmRank(currExchange.getEcpmRank() + 1);
				}
				if (nextExchange.getCtr() > currExchange.getCtr()) {
					currExchange.setCtrRank(currExchange.getCtrRank() + 1);
				}
				if (nextExchange.getCvr() > currExchange.getCvr()) {
					currExchange.setCvrRank(currExchange.getCvrRank() + 1);
				}
			}
		}

		return exchanges;
	}
	
	public List<Bidder> readNativeBidders() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "Native_Bidders.csv"));
		List<Bidder> bidders = new ArrayList<Bidder>();
		String[] nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Bidder b = new Bidder();
			b.setName(nextLine[0]);
			b.setServed(Integer.parseInt(nextLine[1]));
			b.setViewed(Integer.parseInt(nextLine[2]));
			b.setRevenue(Float.parseFloat(nextLine[3]));
			bidders.add(b);
		}

		reader.close();

		return bidders;
	}
	
	public Map<String, String> readPubAMs() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "pub_ams.csv"));
		Map<String, String> ams = new HashMap<String, String>();
		String[] nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			ams.put(nextLine[0], nextLine[1]);
		}
		reader.close();
		return ams;
	}
	
	public List<SDK> readSdkData() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "sdk.csv"));
		List<SDK> data = new ArrayList<SDK>();
		String[] nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			SDK sdk = new SDK();
			sdk.setMonth(nextLine[0]);
			sdk.setIos6(Float.parseFloat(nextLine[1]));
			sdk.setAndroid6(Float.parseFloat(nextLine[2]));
			sdk.setIos5(Float.parseFloat(nextLine[3]));
			sdk.setAndroid5(Float.parseFloat(nextLine[4]));
			sdk.setBelow4_6(Float.parseFloat(nextLine[5]));
			sdk.setJs(Float.parseFloat(nextLine[6]));
			sdk.setWin_s2s(Float.parseFloat(nextLine[7]));
			sdk.setUnknown(Float.parseFloat(nextLine[8]));
			data.add(sdk);
		}
		reader.close();
		return data;
	}
	
	public List<Viewability> readViewabilityData() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "MOAT_Exchange.csv"));
		List<Viewability> sitesList = new ArrayList<Viewability>();
		String[] nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Viewability v = new Viewability();
			v.setNexPubName(nextLine[1]);
			v.setAcctMgr(nextLine[0]);
			v.setNexSiteName(nextLine[2]);
			v.setNexAlias(nextLine[3]);
			v.setDspSiteId(nextLine[4]);
			v.setCreativeType(nextLine[5]);
			v.setCreativeSize(nextLine[6]);
			v.setOs(nextLine[7]);
			v.setInvType(nextLine[8]);
			v.setImpsAnalyzed(Integer.parseInt(nextLine[9]));
			v.setImpsDelivered(Integer.parseInt(nextLine[10]));
			v.setImpsDiscrepancy(nextLine[11]);
			v.setMeasuredRate(nextLine[12]);
			v.setViewScore(nextLine[13]);
			v.setDailyAvails(Integer.parseInt(nextLine[14]));
			v.setDateLastTested(nextLine[15]);
			v.setPartner("MOAT");
			v.setPlatform("Exchange");
			sitesList.add(v);
		}
		reader.close();
		
		reader = new CSVReader(new FileReader(path + "IAS_Exchange.csv"));
		nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Viewability v = new Viewability();
			v.setNexPubName(nextLine[1]);
			v.setAcctMgr(nextLine[0]);
			v.setNexSiteName(nextLine[2]);
			v.setNexAlias(nextLine[4]);
			v.setDspSiteId(nextLine[3]);
			v.setCreativeType(nextLine[5]);
			v.setCreativeSize(nextLine[6]);
			v.setOs(nextLine[7]);
			v.setInvType(nextLine[8]);
			v.setImpsAnalyzed(Integer.parseInt(nextLine[9]));
			v.setImpsDelivered(Integer.parseInt(nextLine[10]));
			v.setImpsDiscrepancy(nextLine[11]);
			v.setMeasuredRate(nextLine[12]);
			v.setViewScore(nextLine[13]);
			v.setDailyAvails(Integer.parseInt(nextLine[14]));
			v.setDateLastTested(nextLine[15]);
			v.setPartner("IAS");
			v.setPlatform("Exchange");
			sitesList.add(v);
		}
		reader.close();
		
		reader = new CSVReader(new FileReader(path + "MOAT_Network.csv"));
		nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Viewability v = new Viewability();
			v.setNexPubName(nextLine[1]);
			v.setAcctMgr(nextLine[0]);
			v.setPubId(nextLine[2]);
			v.setNexSiteName(nextLine[3]);
			v.setDspSiteId(nextLine[4]);
			v.setPlacementName(nextLine[5]);
			v.setApid(nextLine[6]);
			v.setCreativeType(nextLine[7]);
			v.setCreativeSize(nextLine[8]);
			v.setOs(nextLine[9]);
			v.setInvType(nextLine[10]);
			v.setImpsAnalyzed(Integer.parseInt(nextLine[11]));
			v.setImpsDelivered(Integer.parseInt(nextLine[12]));
			v.setImpsDiscrepancy(nextLine[13]);
			v.setMeasuredRate(nextLine[14]);
			v.setViewScore(nextLine[15]);
			v.setDailyAvails(Integer.parseInt(nextLine[16]));
			v.setDateLastTested(nextLine[16]);
			v.setPartner("MOAT");
			v.setPlatform("Network");
			sitesList.add(v);
		}
		reader.close();
		
		
		return sitesList;
	}
	
	
	public List<Viewability> readNewViewabilityData() throws IOException, ParseException {
		CSVReader reader = new CSVReader(new FileReader(path + "IAS_Exchange_New.csv"));
		List<Viewability> sitesList = new ArrayList<Viewability>();
		String[] nextLine = reader.readNext();
		
		DataAccess d = new DataAccess();
		Map<String, String[]> nexSites = d.getNexSites();
		Map<String, String[]> nexSitesData = d.getNexSitesData();
		Map<String, String> nexAvails = d.getNexDailyAvails();
		Map<String, Map<String, String>> siteValues = d.getNexDelivered();
		
		while ((nextLine = reader.readNext()) != null) {
			Viewability v = new Viewability();
			//v.setAcctMgr(nextLine[0]);
			v.setDspSiteId(nextLine[3]);
			v.setNexAlias(nexSites.get(v.getDspSiteId())[0]);
			v.setCreativeSize(nexSites.get(v.getDspSiteId())[1]);
			if(v.getCreativeSize().equals("320x480") || v.getCreativeSize().equals("480x320"))
				v.setCreativeType("FSI");
			else
				v.setCreativeType("Standard Banner");
			String nexSiteId = v.getNexAlias().substring(4, v.getNexAlias().length());
			if(nexSitesData.keySet().contains(nexSiteId)) {
				v.setNexPubName(nexSitesData.get(nexSiteId)[0]);
				v.setNexSiteName(nexSitesData.get(nexSiteId)[1]);
				v.setOs(nexSitesData.get(nexSiteId)[2]);
			} else {
				v.setNexPubName("N/A");
				v.setNexSiteName("N/A");
				v.setOs("N/A");
			}
			if(nexAvails.keySet().contains(nexSiteId))
				v.setDailyAvails(Integer.parseInt(nexAvails.get(nexSiteId)));

			v.setInvType("");
			v.setImpsAnalyzed(Integer.parseInt(nextLine[17]));
			v.setMeasuredRate(nextLine[11]);
			v.setViewScore(nextLine[12]);
			v.setDateLastTested(nextLine[0]);
			
			SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
			Date date = formatter.parse(v.getDateLastTested());
			SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy-mm-dd");
			String formatttedDate = newFormatter.format(date);
			
			if(siteValues.containsKey(nexSiteId)) {
				if(siteValues.get(nexSiteId).containsKey(formatttedDate)) {
					v.setImpsDelivered(Integer.parseInt(siteValues.get(nexSiteId).get(formatttedDate)));
				}
			} else {
				v.setImpsDelivered(0);
			}
			
			if(v.getImpsDelivered() > 0)
				v.setImpsDiscrepancy(""+v.getImpsAnalyzed()/v.getImpsDelivered()*100+"%");
			else
				v.setImpsDiscrepancy("0%");
			v.setPartner("IAS");
			v.setPlatform("Exchange");
			sitesList.add(v);
		}
		reader.close();
		
		/*reader = new CSVReader(new FileReader(path + "IAS_Exchange.csv"));
		nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Viewability v = new Viewability();
			v.setNexPubName(nextLine[1]);
			v.setAcctMgr(nextLine[0]);
			v.setNexSiteName(nextLine[2]);
			v.setNexAlias(nextLine[3]);
			v.setDspSiteId(nextLine[4]);
			v.setCreativeType(nextLine[5]);
			v.setCreativeSize(nextLine[6]);
			v.setOs(nextLine[7]);
			v.setInvType(nextLine[8]);
			v.setImpsAnalyzed(Integer.parseInt(nextLine[9]));
			v.setImpsDelivered(Integer.parseInt(nextLine[10]));
			v.setImpsDiscrepancy(nextLine[11]);
			v.setMeasuredRate(nextLine[12]);
			v.setViewScore(nextLine[13]);
			v.setDailyAvails(Integer.parseInt(nextLine[14]));
			v.setDateLastTested(nextLine[15]);
			v.setPartner("IAS");
			v.setPlatform("Exchange");
			sitesList.add(v);
		}
		reader.close();
		
		reader = new CSVReader(new FileReader(path + "MOAT_Network.csv"));
		nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Viewability v = new Viewability();
			v.setNexPubName(nextLine[1]);
			v.setAcctMgr(nextLine[0]);
			v.setPubId(nextLine[2]);
			v.setNexSiteName(nextLine[3]);
			v.setDspSiteId(nextLine[4]);
			v.setPlacementName(nextLine[5]);
			v.setApid(nextLine[6]);
			v.setCreativeType(nextLine[7]);
			v.setCreativeSize(nextLine[8]);
			v.setOs(nextLine[9]);
			v.setInvType(nextLine[10]);
			v.setImpsAnalyzed(Integer.parseInt(nextLine[11]));
			v.setImpsDelivered(Integer.parseInt(nextLine[12]));
			v.setImpsDiscrepancy(nextLine[13]);
			v.setMeasuredRate(nextLine[14]);
			v.setViewScore(nextLine[15]);
			v.setDailyAvails(Integer.parseInt(nextLine[16]));
			v.setDateLastTested(nextLine[16]);
			v.setPartner("MOAT");
			v.setPlatform("Network");
			sitesList.add(v);
		}
		reader.close();*/
		
		
		return sitesList;
	}

	public List<Exchange> readBusinessHighLevel() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "businessOverview.csv"));
		List<Exchange> exchanges = new ArrayList<Exchange>();
		String[] nextLine = reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			Exchange e = new Exchange();
			e.setExchangeId(nextLine[0]);
			e.setExchangeName(nextLine[1]);
			e.setLineOfBusiness(nextLine[2]);
			e.setCosts(Float.parseFloat(nextLine[3]));
			e.setImpressions(Long.parseLong(nextLine[4]));
			e.setClicks(Long.parseLong(nextLine[5]));
			e.setConversions(Long.parseLong(nextLine[6]));
			e.setEcpm(e.getCosts() / e.getImpressions() * 1000);
			e.setCtr((float) e.getClicks() / (float) e.getImpressions());
			e.setCvr((float) e.getConversions() / (float) e.getClicks());
			exchanges.add(e);
		}

		reader.close();

		for (int i = 0; i < exchanges.size(); i++) {
			Exchange currExchange = exchanges.get(i);
			if (currExchange.getLineOfBusiness().equals("Brand")) {
				for (int j = 0; j < exchanges.size(); j++) {
					Exchange nextExchange = exchanges.get(j);
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getCosts() > currExchange.getCosts()) {
						currExchange.setCostsRank(currExchange.getCostsRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getImpressions() > currExchange.getImpressions()) {
						currExchange.setImpsRank(currExchange.getImpsRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getClicks() > currExchange.getClicks()) {
						currExchange.setClicksRank(currExchange.getClicksRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getConversions() > currExchange.getConversions()) {
						currExchange.setConvsRank(currExchange.getConvsRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getEcpm() > currExchange.getEcpm()) {
						currExchange.setEcpmRank(currExchange.getEcpmRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getCtr() > currExchange.getCtr()) {
						currExchange.setCtrRank(currExchange.getCtrRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getCvr() > currExchange.getCvr()) {
						currExchange.setCvrRank(currExchange.getCvrRank() + 1);
					}
				}
			} else {

				for (int j = 0; j < exchanges.size(); j++) {
					Exchange nextExchange = exchanges.get(j);
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getCosts() > currExchange.getCosts()) {
						currExchange.setCostsRank(currExchange.getCostsRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getImpressions() > currExchange.getImpressions()) {
						currExchange.setImpsRank(currExchange.getImpsRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getClicks() > currExchange.getClicks()) {
						currExchange.setClicksRank(currExchange.getClicksRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getConversions() > currExchange.getConversions()) {
						currExchange.setConvsRank(currExchange.getConvsRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getEcpm() > currExchange.getEcpm()) {
						currExchange.setEcpmRank(currExchange.getEcpmRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getCtr() > currExchange.getCtr()) {
						currExchange.setCtrRank(currExchange.getCtrRank() + 1);
					}
					if (currExchange.getLineOfBusiness().equals(nextExchange.getLineOfBusiness())
							&& nextExchange.getCvr() > currExchange.getCvr()) {
						currExchange.setCvrRank(currExchange.getCvrRank() + 1);
					}
				}

			}
		}

		return exchanges;
	}

	public List<PubChurn> readNexPubChurn() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "nexPubChurn.csv"));
		Map<String, PubChurn> pubsMap = new HashMap<String, PubChurn>();
		DataAccess d = new DataAccess();
		Map<String, PublisherAccount> pubFirstMap = d.getNexPubFirstRequest();
		List<PubChurn> pubChurnList = new ArrayList<PubChurn>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			long reqs = Long.parseLong(nextLine[3]);
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			PubChurn pc;
			if (!pubsMap.containsKey(key)) {
				pc = new PubChurn();
				pc.setYear(year);
				pc.setMonth(month);
			} else {
				pc = pubsMap.get(key);
			}
			if (pa.getFirstYear() < currentYear - 1)
				pc.setOldRequests(pc.getOldRequests() + reqs);
			if (pa.getFirstYear() == currentYear - 1 && pa.getFirstYear() <= pc.getYear()) {
				if (pa.getFirstMonth() == 1
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m1requests(pc.getY1m1requests() + reqs);
				if (pa.getFirstMonth() == 2
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m2requests(pc.getY1m2requests() + reqs);
				if (pa.getFirstMonth() == 3
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m3requests(pc.getY1m3requests() + reqs);
				if (pa.getFirstMonth() == 4
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m4requests(pc.getY1m4requests() + reqs);
				if (pa.getFirstMonth() == 5
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m5requests(pc.getY1m5requests() + reqs);
				if (pa.getFirstMonth() == 6
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m6requests(pc.getY1m6requests() + reqs);
				if (pa.getFirstMonth() == 7
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m7requests(pc.getY1m7requests() + reqs);
				if (pa.getFirstMonth() == 8
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m8requests(pc.getY1m8requests() + reqs);
				if (pa.getFirstMonth() == 9
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m9requests(pc.getY1m9requests() + reqs);
				if (pa.getFirstMonth() == 10
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m10requests(pc.getY1m10requests() + reqs);
				if (pa.getFirstMonth() == 11
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m11requests(pc.getY1m11requests() + reqs);
				if (pa.getFirstMonth() == 12
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m12requests(pc.getY1m12requests() + reqs);
			}
			if (pa.getFirstYear() == currentYear && pa.getFirstYear() == pc.getYear()) {
				if (pa.getFirstMonth() == 1 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m1requests(pc.getY2m1requests() + reqs);
				if (pa.getFirstMonth() == 2 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m2requests(pc.getY2m2requests() + reqs);
				if (pa.getFirstMonth() == 3 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m3requests(pc.getY2m3requests() + reqs);
				if (pa.getFirstMonth() == 4 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m4requests(pc.getY2m4requests() + reqs);
				if (pa.getFirstMonth() == 5 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m5requests(pc.getY2m5requests() + reqs);
				if (pa.getFirstMonth() == 6 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m6requests(pc.getY2m6requests() + reqs);
				if (pa.getFirstMonth() == 7 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m7requests(pc.getY2m7requests() + reqs);
				if (pa.getFirstMonth() == 8 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m8requests(pc.getY2m8requests() + reqs);
				if (pa.getFirstMonth() == 9 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m9requests(pc.getY2m9requests() + reqs);
				if (pa.getFirstMonth() == 10 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m10requests(pc.getY2m10requests() + reqs);
				if (pa.getFirstMonth() == 11 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m11requests(pc.getY2m11requests() + reqs);
				if (pa.getFirstMonth() == 12 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m12requests(pc.getY2m12requests() + reqs);
			}

			pubsMap.put(key, pc);
		}

		reader.close();

		Set<String> keySet = pubsMap.keySet();

		for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
			pubChurnList.add(pubsMap.get(i.next()));
		}

		Collections.sort(pubChurnList, PubChurn.COMPARE_BY_FIRST_MONTH);

		return pubChurnList;
	}

	public List<PubChurn> readGreenPubChurn() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "greenPubChurn.csv"));
		Map<String, PubChurn> pubsMap = new HashMap<String, PubChurn>();
		DataAccess d = new DataAccess();
		Map<String, PublisherAccount> pubFirstMap = d.getGreenPubFirstRequest();
		List<PubChurn> pubChurnList = new ArrayList<PubChurn>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			long reqs = Long.parseLong(nextLine[3]);
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			PubChurn pc;
			if (!pubsMap.containsKey(key)) {
				pc = new PubChurn();
				pc.setYear(year);
				pc.setMonth(month);
			} else {
				pc = pubsMap.get(key);
			}
			if (pa.getFirstYear() < currentYear - 1)
				pc.setOldRequests(pc.getOldRequests() + reqs);
			if (pa.getFirstYear() == currentYear - 1 && pa.getFirstYear() <= pc.getYear()) {
				if (pa.getFirstMonth() == 1
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m1requests(pc.getY1m1requests() + reqs);
				if (pa.getFirstMonth() == 2
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m2requests(pc.getY1m2requests() + reqs);
				if (pa.getFirstMonth() == 3
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m3requests(pc.getY1m3requests() + reqs);
				if (pa.getFirstMonth() == 4
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m4requests(pc.getY1m4requests() + reqs);
				if (pa.getFirstMonth() == 5
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m5requests(pc.getY1m5requests() + reqs);
				if (pa.getFirstMonth() == 6
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m6requests(pc.getY1m6requests() + reqs);
				if (pa.getFirstMonth() == 7
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m7requests(pc.getY1m7requests() + reqs);
				if (pa.getFirstMonth() == 8
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m8requests(pc.getY1m8requests() + reqs);
				if (pa.getFirstMonth() == 9
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m9requests(pc.getY1m9requests() + reqs);
				if (pa.getFirstMonth() == 10
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m10requests(pc.getY1m10requests() + reqs);
				if (pa.getFirstMonth() == 11
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m11requests(pc.getY1m11requests() + reqs);
				if (pa.getFirstMonth() == 12
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m12requests(pc.getY1m12requests() + reqs);
			}
			if (pa.getFirstYear() == currentYear && pa.getFirstYear() == pc.getYear()) {
				if (pa.getFirstMonth() == 1 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m1requests(pc.getY2m1requests() + reqs);
				if (pa.getFirstMonth() == 2 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m2requests(pc.getY2m2requests() + reqs);
				if (pa.getFirstMonth() == 3 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m3requests(pc.getY2m3requests() + reqs);
				if (pa.getFirstMonth() == 4 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m4requests(pc.getY2m4requests() + reqs);
				if (pa.getFirstMonth() == 5 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m5requests(pc.getY2m5requests() + reqs);
				if (pa.getFirstMonth() == 6 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m6requests(pc.getY2m6requests() + reqs);
				if (pa.getFirstMonth() == 7 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m7requests(pc.getY2m7requests() + reqs);
				if (pa.getFirstMonth() == 8 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m8requests(pc.getY2m8requests() + reqs);
				if (pa.getFirstMonth() == 9 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m9requests(pc.getY2m9requests() + reqs);
				if (pa.getFirstMonth() == 10 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m10requests(pc.getY2m10requests() + reqs);
				if (pa.getFirstMonth() == 11 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m11requests(pc.getY2m11requests() + reqs);
				if (pa.getFirstMonth() == 12 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m12requests(pc.getY2m12requests() + reqs);
			}

			pubsMap.put(key, pc);
		}

		reader.close();

		Set<String> keySet = pubsMap.keySet();

		for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
			pubChurnList.add(pubsMap.get(i.next()));
		}

		Collections.sort(pubChurnList, PubChurn.COMPARE_BY_FIRST_MONTH);

		return pubChurnList;
	}

	public List<PubChurn> readGreenPubChurnRev() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "greenPubChurnRev.csv"));
		Map<String, PubChurn> pubsMap = new HashMap<String, PubChurn>();
		DataAccess d = new DataAccess();
		Map<String, PublisherAccount> pubFirstMap = d.getGreenPubFirstRequest();
		List<PubChurn> pubChurnList = new ArrayList<PubChurn>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			float rev = Float.parseFloat(nextLine[3]);
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			PubChurn pc;
			if (!pubsMap.containsKey(key)) {
				pc = new PubChurn();
				pc.setYear(year);
				pc.setMonth(month);
			} else {
				pc = pubsMap.get(key);
			}
			if (pa.getFirstYear() < currentYear - 1)
				pc.setOldrevenue(pc.getOldrevenue() + rev);
			if (pa.getFirstYear() == currentYear - 1 && pa.getFirstYear() <= pc.getYear()) {
				if (pa.getFirstMonth() == 1
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m1revenue(pc.getY1m1revenue() + rev);
				if (pa.getFirstMonth() == 2
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m2revenue(pc.getY1m2revenue() + rev);
				if (pa.getFirstMonth() == 3
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m3revenue(pc.getY1m3revenue() + rev);
				if (pa.getFirstMonth() == 4
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m4revenue(pc.getY1m4revenue() + rev);
				if (pa.getFirstMonth() == 5
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m5revenue(pc.getY1m5revenue() + rev);
				if (pa.getFirstMonth() == 6
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m6revenue(pc.getY1m6revenue() + rev);
				if (pa.getFirstMonth() == 7
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m7revenue(pc.getY1m7revenue() + rev);
				if (pa.getFirstMonth() == 8
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m8revenue(pc.getY1m8revenue() + rev);
				if (pa.getFirstMonth() == 9
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m9revenue(pc.getY1m9revenue() + rev);
				if (pa.getFirstMonth() == 10
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m10revenue(pc.getY1m10revenue() + rev);
				if (pa.getFirstMonth() == 11
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m11revenue(pc.getY1m11revenue() + rev);
				if (pa.getFirstMonth() == 12
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m12revenue(pc.getY1m12revenue() + rev);
			}
			if (pa.getFirstYear() == currentYear && pa.getFirstYear() == pc.getYear()) {
				if (pa.getFirstMonth() == 1 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m1revenue(pc.getY2m1revenue() + rev);
				if (pa.getFirstMonth() == 2 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m2revenue(pc.getY2m2revenue() + rev);
				if (pa.getFirstMonth() == 3 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m3revenue(pc.getY2m3revenue() + rev);
				if (pa.getFirstMonth() == 4 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m4revenue(pc.getY2m4revenue() + rev);
				if (pa.getFirstMonth() == 5 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m5revenue(pc.getY2m5revenue() + rev);
				if (pa.getFirstMonth() == 6 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m6revenue(pc.getY2m6revenue() + rev);
				if (pa.getFirstMonth() == 7 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m7revenue(pc.getY2m7revenue() + rev);
				if (pa.getFirstMonth() == 8 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m8revenue(pc.getY2m8revenue() + rev);
				if (pa.getFirstMonth() == 9 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m9revenue(pc.getY2m9revenue() + rev);
				if (pa.getFirstMonth() == 10 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m10revenue(pc.getY2m10revenue() + rev);
				if (pa.getFirstMonth() == 11 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m11revenue(pc.getY2m11revenue() + rev);
				if (pa.getFirstMonth() == 12 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m12revenue(pc.getY2m12revenue() + rev);
			}

			pubsMap.put(key, pc);
		}

		reader.close();

		Set<String> keySet = pubsMap.keySet();

		for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
			pubChurnList.add(pubsMap.get(i.next()));
		}

		Collections.sort(pubChurnList, PubChurn.COMPARE_BY_FIRST_MONTH);
		
		PubChurn pc = new PubChurn();

		for (Iterator<PubChurn> i = pubChurnList.iterator(); i.hasNext();) {
			pc = i.next();
		}

		double totalRev = pc.getOldrevenue() + pc.getY1m10revenue() + pc.getY1m11revenue() + pc.getY1m12revenue()
				+ pc.getY1m1revenue() + pc.getY1m2revenue() + pc.getY1m3revenue() + pc.getY1m4revenue()
				+ pc.getY1m5revenue() + pc.getY1m6revenue() + pc.getY1m7revenue() + pc.getY1m8revenue()
				+ pc.getY1m9revenue() + pc.getY2m10revenue() + pc.getY2m11revenue() + pc.getY2m12revenue()
				+ pc.getY2m1revenue() + pc.getY2m2revenue() + pc.getY2m3revenue() + pc.getY2m3revenue()
				+ pc.getY2m4revenue() + pc.getY2m5revenue() + pc.getY2m6revenue() + pc.getY2m7revenue()
				+ pc.getY2m8revenue() + pc.getY2m9revenue();

		double weightedRev = 1 * pc.getOldrevenue() + 0.96 * pc.getY1m1revenue() + 0.92 * pc.getY1m2revenue()
				+ 0.88 * pc.getY1m3revenue() + 0.84 * pc.getY1m4revenue() + 0.8 * pc.getY1m5revenue()
				+ 0.76 * pc.getY1m6revenue() + 0.72 * pc.getY1m7revenue() + 0.68 * pc.getY1m8revenue()
				+ 0.64 * pc.getY1m9revenue() + 0.6 * pc.getY1m10revenue() + 0.56 * pc.getY1m11revenue()
				+ 0.52 * pc.getY1m12revenue() + 0.48 * pc.getY2m1revenue() + 0.44 * pc.getY2m2revenue()
				+ 0.4 * pc.getY2m3revenue() + 0.36 * pc.getY2m4revenue() + 0.32 * pc.getY2m5revenue()
				+ 0.28 * pc.getY2m6revenue() + 0.24 * pc.getY2m7revenue() + 0.2 * pc.getY2m8revenue()
				+ 0.16 * pc.getY2m9revenue() + 0.12 * pc.getY2m10revenue() + 0.08 * pc.getY2m11revenue()
				+ 0.04 * pc.getY2m12revenue();
		
		PubChurn risk = new PubChurn();
		risk.setRisk(weightedRev/totalRev*100);

		pubChurnList.add(risk);

		return pubChurnList;
	}

	public List<PubChurn> readNexPubChurnRev() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "nexPubChurnRev.csv"));
		Map<String, PubChurn> pubsMap = new HashMap<String, PubChurn>();
		DataAccess d = new DataAccess();
		Map<String, PublisherAccount> pubFirstMap = d.getNexPubFirstRequest();
		List<PubChurn> pubChurnList = new ArrayList<PubChurn>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			float rev = Float.parseFloat(nextLine[3]);
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			PubChurn pc;
			if (!pubsMap.containsKey(key)) {
				pc = new PubChurn();
				pc.setYear(year);
				pc.setMonth(month);
			} else {
				pc = pubsMap.get(key);
			}
			if (pa.getFirstYear() < currentYear - 1)
				pc.setOldrevenue(pc.getOldrevenue() + rev);
			if (pa.getFirstYear() == currentYear - 1 && pa.getFirstYear() <= pc.getYear()) {
				if (pa.getFirstMonth() == 1
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m1revenue(pc.getY1m1revenue() + rev);
				if (pa.getFirstMonth() == 2
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m2revenue(pc.getY1m2revenue() + rev);
				if (pa.getFirstMonth() == 3
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m3revenue(pc.getY1m3revenue() + rev);
				if (pa.getFirstMonth() == 4
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m4revenue(pc.getY1m4revenue() + rev);
				if (pa.getFirstMonth() == 5
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m5revenue(pc.getY1m5revenue() + rev);
				if (pa.getFirstMonth() == 6
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m6revenue(pc.getY1m6revenue() + rev);
				if (pa.getFirstMonth() == 7
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m7revenue(pc.getY1m7revenue() + rev);
				if (pa.getFirstMonth() == 8
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m8revenue(pc.getY1m8revenue() + rev);
				if (pa.getFirstMonth() == 9
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m9revenue(pc.getY1m9revenue() + rev);
				if (pa.getFirstMonth() == 10
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m10revenue(pc.getY1m10revenue() + rev);
				if (pa.getFirstMonth() == 11
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m11revenue(pc.getY1m11revenue() + rev);
				if (pa.getFirstMonth() == 12
						&& ((pa.getFirstMonth() <= pc.getMonth() && pa.getFirstYear() == pc.getYear())
								|| pa.getFirstYear() < pc.getYear()))
					pc.setY1m12revenue(pc.getY1m12revenue() + rev);
			}
			if (pa.getFirstYear() == currentYear && pa.getFirstYear() == pc.getYear()) {
				if (pa.getFirstMonth() == 1 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m1revenue(pc.getY2m1revenue() + rev);
				if (pa.getFirstMonth() == 2 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m2revenue(pc.getY2m2revenue() + rev);
				if (pa.getFirstMonth() == 3 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m3revenue(pc.getY2m3revenue() + rev);
				if (pa.getFirstMonth() == 4 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m4revenue(pc.getY2m4revenue() + rev);
				if (pa.getFirstMonth() == 5 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m5revenue(pc.getY2m5revenue() + rev);
				if (pa.getFirstMonth() == 6 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m6revenue(pc.getY2m6revenue() + rev);
				if (pa.getFirstMonth() == 7 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m7revenue(pc.getY2m7revenue() + rev);
				if (pa.getFirstMonth() == 8 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m8revenue(pc.getY2m8revenue() + rev);
				if (pa.getFirstMonth() == 9 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m9revenue(pc.getY2m9revenue() + rev);
				if (pa.getFirstMonth() == 10 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m10revenue(pc.getY2m10revenue() + rev);
				if (pa.getFirstMonth() == 11 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m11revenue(pc.getY2m11revenue() + rev);
				if (pa.getFirstMonth() == 12 && pa.getFirstMonth() <= pc.getMonth())
					pc.setY2m12revenue(pc.getY2m12revenue() + rev);
			}

			pubsMap.put(key, pc);
		}

		reader.close();

		Set<String> keySet = pubsMap.keySet();

		for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
			pubChurnList.add(pubsMap.get(i.next()));
		}

		Collections.sort(pubChurnList, PubChurn.COMPARE_BY_FIRST_MONTH);

		PubChurn pc = new PubChurn();

		for (Iterator<PubChurn> i = pubChurnList.iterator(); i.hasNext();) {
			pc = i.next();
		}

		double totalRev = pc.getOldrevenue() + pc.getY1m10revenue() + pc.getY1m11revenue() + pc.getY1m12revenue()
				+ pc.getY1m1revenue() + pc.getY1m2revenue() + pc.getY1m3revenue() + pc.getY1m4revenue()
				+ pc.getY1m5revenue() + pc.getY1m6revenue() + pc.getY1m7revenue() + pc.getY1m8revenue()
				+ pc.getY1m9revenue() + pc.getY2m10revenue() + pc.getY2m11revenue() + pc.getY2m12revenue()
				+ pc.getY2m1revenue() + pc.getY2m2revenue() + pc.getY2m3revenue() + pc.getY2m3revenue()
				+ pc.getY2m4revenue() + pc.getY2m5revenue() + pc.getY2m6revenue() + pc.getY2m7revenue()
				+ pc.getY2m8revenue() + pc.getY2m9revenue();

		double weightedRev = 1 * pc.getOldrevenue() + 0.96 * pc.getY1m1revenue() + 0.92 * pc.getY1m2revenue()
				+ 0.88 * pc.getY1m3revenue() + 0.84 * pc.getY1m4revenue() + 0.8 * pc.getY1m5revenue()
				+ 0.76 * pc.getY1m6revenue() + 0.72 * pc.getY1m7revenue() + 0.68 * pc.getY1m8revenue()
				+ 0.64 * pc.getY1m9revenue() + 0.6 * pc.getY1m10revenue() + 0.56 * pc.getY1m11revenue()
				+ 0.52 * pc.getY1m12revenue() + 0.48 * pc.getY2m1revenue() + 0.44 * pc.getY2m2revenue()
				+ 0.4 * pc.getY2m3revenue() + 0.36 * pc.getY2m4revenue() + 0.32 * pc.getY2m5revenue()
				+ 0.28 * pc.getY2m6revenue() + 0.24 * pc.getY2m7revenue() + 0.2 * pc.getY2m8revenue()
				+ 0.16 * pc.getY2m9revenue() + 0.12 * pc.getY2m10revenue() + 0.08 * pc.getY2m11revenue()
				+ 0.04 * pc.getY2m12revenue();
		
		PubChurn risk = new PubChurn();
		risk.setRisk(weightedRev/totalRev*100);

		pubChurnList.add(risk);

		return pubChurnList;
	}

	public List<PublisherAccount> readNexDetailChurnRev(String category, String targetMonth) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "nexPubChurnRev.csv"));
		DataAccess d = new DataAccess();
		List<PublisherAccount> pubs = new ArrayList<PublisherAccount>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, PublisherAccount> pubFirstMap = d.getNexPubFirstRequest();
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			float rev = Float.parseFloat(String.format("%.2f", Float.parseFloat(nextLine[3])));
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			String targetFirstYear = category.substring(0, 4);
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			if (key.equals(targetMonth)) {
				if (Integer.parseInt(targetFirstYear) == currentYear - 2 && pa.getFirstYear() <= currentYear - 2) {
					pa.setPublisherId(pubId);
					pa.setRevenue(rev);
					pubs.add(pa);
				} else if (new String(pa.getFirstYear() + "-" + pa.getFirstMonth()).equals(category)) {
					pa.setPublisherId(pubId);
					pa.setRevenue(rev);
					pubs.add(pa);
				}
			}
		}
		reader.close();
		Collections.sort(pubs, PublisherAccount.COMPARE_BY_REV_DESC);
		if (pubs.size() > 20) {
			List<PublisherAccount> topPubs = new ArrayList<PublisherAccount>(pubs.subList(0, 20));
			for (Iterator<PublisherAccount> i = topPubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getNexPubName(a.getPublisherId()));
			}
			return topPubs;
		} else {
			for (Iterator<PublisherAccount> i = pubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getNexPubName(a.getPublisherId()));
			}
			return pubs;
		}
	}

	public List<PublisherAccount> readNexDetailChurnReqs(String category, String targetMonth) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "nexPubChurn.csv"));
		DataAccess d = new DataAccess();
		List<PublisherAccount> pubs = new ArrayList<PublisherAccount>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, PublisherAccount> pubFirstMap = d.getNexPubFirstRequest();
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			long reqs = Long.parseLong(nextLine[3]);
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			String targetFirstYear = category.substring(0, 4);
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			if (key.equals(targetMonth)) {
				if (Integer.parseInt(targetFirstYear) == currentYear - 2 && pa.getFirstYear() <= currentYear - 2) {
					pa.setPublisherId(pubId);
					pa.setRequests(reqs);
					pubs.add(pa);
				} else if (new String(pa.getFirstYear() + "-" + pa.getFirstMonth()).equals(category)) {
					pa.setPublisherId(pubId);
					pa.setRequests(reqs);
					pubs.add(pa);
				}
			}
		}
		reader.close();
		Collections.sort(pubs, PublisherAccount.COMPARE_BY_REQS_DESC);
		if (pubs.size() > 20) {
			List<PublisherAccount> topPubs = new ArrayList<PublisherAccount>(pubs.subList(0, 20));
			for (Iterator<PublisherAccount> i = topPubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getNexPubName(a.getPublisherId()));
			}
			return topPubs;
		} else {
			for (Iterator<PublisherAccount> i = pubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getNexPubName(a.getPublisherId()));
			}
			return pubs;
		}
	}

	public List<PublisherAccount> readMydasDetailChurnRev(String category, String targetMonth) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "greenPubChurnRev.csv"));
		DataAccess d = new DataAccess();
		List<PublisherAccount> pubs = new ArrayList<PublisherAccount>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, PublisherAccount> pubFirstMap = d.getGreenPubFirstRequest();
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			float rev = Float.parseFloat(String.format("%.2f", Float.parseFloat(nextLine[3])));
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			String targetFirstYear = category.substring(0, 4);
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			if (key.equals(targetMonth)) {
				if (Integer.parseInt(targetFirstYear) == currentYear - 2 && pa.getFirstYear() <= currentYear - 2) {
					pa.setPublisherId(pubId);
					pa.setRevenue(rev);
					pubs.add(pa);
				} else if (new String(pa.getFirstYear() + "-" + pa.getFirstMonth()).equals(category)) {
					pa.setPublisherId(pubId);
					pa.setRevenue(rev);
					pubs.add(pa);
				}
			}
		}
		reader.close();
		Collections.sort(pubs, PublisherAccount.COMPARE_BY_REV_DESC);
		if (pubs.size() > 20) {
			List<PublisherAccount> topPubs = new ArrayList<PublisherAccount>(pubs.subList(0, 20));
			for (Iterator<PublisherAccount> i = topPubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getGreenPubName(a.getPublisherId()));
			}
			return topPubs;
		} else {
			for (Iterator<PublisherAccount> i = pubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getGreenPubName(a.getPublisherId()));
			}
			return pubs;
		}
	}

	public List<PublisherAccount> readMydasDetailChurnReqs(String category, String targetMonth) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path + "greenPubChurn.csv"));
		DataAccess d = new DataAccess();
		List<PublisherAccount> pubs = new ArrayList<PublisherAccount>();
		String[] nextLine = reader.readNext();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, PublisherAccount> pubFirstMap = d.getGreenPubFirstRequest();
		while ((nextLine = reader.readNext()) != null) {
			int year = Integer.parseInt(nextLine[0]);
			int month = Integer.parseInt(nextLine[1]);
			String pubId = nextLine[2];
			long reqs = Long.parseLong(nextLine[3]);
			String key = Integer.toString(year) + "-" + Integer.toString(month);
			PublisherAccount pa = new PublisherAccount();
			String targetFirstYear = category.substring(0, 4);
			if (pubFirstMap.containsKey(pubId))
				pa = pubFirstMap.get(pubId);
			if (key.equals(targetMonth)) {
				if (Integer.parseInt(targetFirstYear) == currentYear - 2 && pa.getFirstYear() <= currentYear - 2) {
					pa.setPublisherId(pubId);
					pa.setRequests(reqs);
					pubs.add(pa);
				} else if (new String(pa.getFirstYear() + "-" + pa.getFirstMonth()).equals(category)) {
					pa.setPublisherId(pubId);
					pa.setRequests(reqs);
					pubs.add(pa);
				}
			}
		}
		reader.close();
		Collections.sort(pubs, PublisherAccount.COMPARE_BY_REQS_DESC);
		if (pubs.size() > 20) {
			List<PublisherAccount> topPubs = new ArrayList<PublisherAccount>(pubs.subList(0, 20));
			for (Iterator<PublisherAccount> i = topPubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getGreenPubName(a.getPublisherId()));
			}
			return topPubs;
		} else {
			for (Iterator<PublisherAccount> i = pubs.iterator(); i.hasNext();) {
				PublisherAccount a = i.next();
				if (a.getPublisherName().length() == 0)
					a.setPublisherName(d.getGreenPubName(a.getPublisherId()));
			}
			return pubs;
		}
	}

}
