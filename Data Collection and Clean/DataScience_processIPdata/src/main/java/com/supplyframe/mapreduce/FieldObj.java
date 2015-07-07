package com.supplyframe.mapreduce;

public class FieldObj {
	public String ipStart;
	/*public String ipEnd;
	public String country;
	public String city;
	public String latitude;
	public String logitude;
	public String ipStartLong;
	public String ipEndLong;
	public String dc_company;
	public String asn;
	public String asn_name;
	*/
	public String url;
	
	public FieldObj () {
		ipStart = "-";
		/*ipEnd = "-";
		country = "unknown";
		city = "unknown";
		latitude = "unknown";
		logitude = "unknown";
		dc_company = "unknown";
		asn = "unknown";
		asn_name = "unknown";
		ipStartLong = "-";
		ipEndLong = "-";*/
		url = "-";
	}

	public void setFields(String line) {
		String[] strs = line.split("\t");
		url = strs[7];
		ipStart = strs[6];
		/*ipStart = strs[2];
		ipEnd = strs[3];
		country = strs[4];
		city = strs[6];
		latitude = strs[12];
		logitude = strs[13];
		asn = strs[28];
		asn_name = strs[29];
		dc_company = strs[33];
		ipStartLong = String.valueOf(ipStr2Dec(ipStart));
		ipEndLong = String.valueOf(ipStr2Dec(ipEnd));
		*/
	}
	
	/*private long ipStr2Dec(String ipStr) {
		String[] strs = ipStr.split("\\.");
		long num = 0;
		for (int i = 0; i < 4; i++) num += (int)Integer.parseInt(strs[i])*Math.pow(256, 3-i);
		return num;
	}*/
}
