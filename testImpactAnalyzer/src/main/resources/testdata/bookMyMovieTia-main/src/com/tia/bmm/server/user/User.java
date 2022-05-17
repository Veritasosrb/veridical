package com.tia.bmm.server.user;

public class User {
	int userId;
    String name;
    private String mobNo;
    private String emailId;
    String sex;

    public User() {}

    public User(int uid, String n, String mno, String eid, String gen) {
        userId = uid;
        name = n;
        setMobNo(mno);
        setEmailId(eid);
        sex = gen;
    }

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	
	
}
