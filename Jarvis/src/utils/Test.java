package utils;

import java.io.IOException;

import data.Register;
import data.ViewRecords;

public class Test {

	public static void main(String[] args) throws IOException {
		Register r = new Register();
		r.insert("kb", "kb@aol.com", "1112223333");
		ViewRecords v = new ViewRecords();
		v.read();
	}

}
