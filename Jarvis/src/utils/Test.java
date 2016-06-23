package utils;

import data.DataAccess;

public class Test {

	public static void main(String[] args) {
		DataAccess d = new DataAccess();
		for(int i = 0 ; i < 12; i++)
			System.out.println(d.getGreenPubChurnRevenue("119", 2015, i+1));
	}

}
