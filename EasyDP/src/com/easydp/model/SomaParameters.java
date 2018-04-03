package com.easydp.model;

public class SomaParameters {
	String ipAdd;
	String username;
	String password;
	String domains[];
	int numberOfDomains;
	static int first = 0;
	static int last = 4;
	
	public static int getFirst() {
		return first;
	}
	public static void setFirst(int first) {
		SomaParameters.first = first;
	}
	public static int getLast() {
		return last;
	}
	public static void setLast(int last) {
		SomaParameters.last = last;
	}
	public int getNumberOfDomains() {
		return domains.length;
	}
	static SomaParameters spObject;
	
	public String getIpAdd() {
		return ipAdd;
	}
	public void setIpAdd(String ipAdd) {
		this.ipAdd = ipAdd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String[] getDomains() {
		return domains;
	}
	public void setDomains(String[] domains) {
		this.domains = domains;
	}
	public static SomaParameters getSpObject() {
		return spObject;
	}
	public static void setSpObject(SomaParameters spObject) {
		SomaParameters.spObject = spObject;
	}
	
}
