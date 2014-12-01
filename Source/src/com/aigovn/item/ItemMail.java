package com.aigovn.item;

import com.aigovn.utility.Utils;

public class ItemMail {
	//private variables
    private int _id;
    private long mailID;
    private long userID;
    private String fromAddress;
    private String toAddress;
    private String ccAddress;
    private String desType;
    private String subject;
    private String body;
    private String receiveDate;
    private String processDate;
    private int receiveTimeStamp;
    private int processTimeStamp;
    private int status;
    private int type;
    
    // Empty constructor
    public ItemMail(){
         
    }

    public int get_id() {
		return _id;
	}

    public void set_id(int _id) {
		this._id = _id;
	}

    public long getMailID() {
		return mailID;
	}

    public void setMailID(long mailID) {
		this.mailID = mailID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	public String getDesType() {
		return desType;
	}

	public void setDesType(String desType) {
		this.desType = desType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	public int getReceiveTimeStamp() {
		return Utils.convertStringDateToTimeStamp(receiveDate, "yyyy-mm-dd HH:mm:ss");
	}

	public void setReceiveTimeStamp(String receiveDate) {
		this.receiveTimeStamp = Utils.convertStringDateToTimeStamp(receiveDate, "yyyy-mm-dd HH:mm:ss");
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	
	public int getProcessTimeStamp() {
		return Utils.convertStringDateToTimeStamp(processDate, "yyyy-mm-dd HH:mm:ss");
	}

	public void setProcessTimeStamp(String receiveDate) {
		this.receiveTimeStamp = Utils.convertStringDateToTimeStamp(processDate, "yyyy-mm-dd HH:mm:ss");
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    
    
    
    
}
