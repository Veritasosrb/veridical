package com.tia.bmm.server.address;

import java.util.Scanner;

public class Address {
	private String city;
    private String pinCode;
    private String state;
    private String streetNo;
    private String landmark;

    public Address() {}
    Address(String c,String p,String st,String sn,String lm) {
        setCity(c);
        setPinCode(p);
        setState(st);
        setStreetNo(sn);
        setLandmark(lm);
    }

    public Address addAddress() {
        String c;String p;String st;String sn;String lm;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter city: ");
        c = sc.nextLine();
        System.out.print("Enter Pincode: ");
        p = sc.nextLine();
        System.out.print("Enter State: ");
        st = sc.nextLine();
        System.out.print("Enter Street Name: ");
        sn = sc.nextLine();
        System.out.print("Enter Landmark: ");
        lm = sc.nextLine();

        Address a = new Address(c,p,st,sn,lm);
        return a;
    }
    public void displayAddress(Address a) {
        System.out.println("Address: " + a.getStreetNo() + " , Near " + a.getLandmark() + " , " + a.getCity());
        System.out.println("Pincode: " + a.getPinCode());
        System.out.println("State: " + a.getState());
    }
	public String getStreetNo() {
		return streetNo;
	}
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
