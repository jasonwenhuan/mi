package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

public class RequestEntity implements Serializable {
    private String orderid;
	private String phone;
	private String mac;
	private String billid;
	private String appid;
	private String ip;
	private String account;
	private String billLocation;
	private String billWay;
	private String cp;
	private Date timestamp;
	private String checkOutIdRsp;
	private String appName;
	private String bill;
	private String appDeveloper;
	private String imei;
	private String appVersion;
	private String imsi;
    private String enabledPolicy;
    private Boolean checkFlg = true;
    private String explanation;
    private String payType;
    private String payfee;

    public RequestEntity() {}

    public RequestEntity(String phone, String mac, String appid, String account) {
        this.phone = phone;
        this.mac = mac;
        this.appid = appid;
        this.account = account;
    }
    
    public RequestEntity(String orderid, String phone, String mac, String account, String appName, Date timestamp, Boolean checkFlg, String explanation, String enabledPolicy) {
        this.orderid = orderid;
        this.phone = phone;
        this.mac = mac;
        this.account = account;
        this.appName = appName;
        this.timestamp = timestamp;
        this.checkFlg = checkFlg;
        this.explanation = explanation;
        this.enabledPolicy = enabledPolicy;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBillLocation() {
		return billLocation;
	}

	public void setBillLocation(String billLocation) {
		this.billLocation = billLocation;
	}

	public String getBillWay() {
		return billWay;
	}

	public void setBillWay(String billWay) {
		this.billWay = billWay;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getCheckOutIdRsp() {
		return checkOutIdRsp;
	}


	public void setCheckOutIdRsp(String checkOutIdRsp) {
		this.checkOutIdRsp = checkOutIdRsp;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getAppDeveloper() {
		return appDeveloper;
	}

	public void setAppDeveloper(String appDeveloper) {
		this.appDeveloper = appDeveloper;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

    public String getEnabledPolicy() {
        return enabledPolicy;
    }

    public void setEnabledPolicy(String enabledPolicy) {
        this.enabledPolicy = enabledPolicy;
    }

    public Boolean getCheckFlg() {
        return checkFlg;
    }

    public void setCheckFlg(Boolean checkFlg) {
        this.checkFlg = checkFlg;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayfee() {
        return payfee;
    }

    public void setPayfee(String payfee) {
        this.payfee = payfee;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "orderid='" + orderid + '\'' +
                ", phone='" + phone + '\'' +
                ", mac='" + mac + '\'' +
                ", billid='" + billid + '\'' +
                ", appid='" + appid + '\'' +
                ", ip='" + ip + '\'' +
                ", account='" + account + '\'' +
                ", billLocation='" + billLocation + '\'' +
                ", billWay='" + billWay + '\'' +
                ", cp='" + cp + '\'' +
                ", timestamp=" + timestamp +
                ", checkOutIdRsp='" + checkOutIdRsp + '\'' +
                ", appName='" + appName + '\'' +
                ", bill='" + bill + '\'' +
                ", appDeveloper='" + appDeveloper + '\'' +
                ", imei='" + imei + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", imsi='" + imsi + '\'' +
                ", enabledPolicy='" + enabledPolicy + '\'' +
                ", checkFlg=" + checkFlg +
                ", explanation='" + explanation + '\'' +
                ", payType='" + payType + '\'' +
                ", payfee=" + payfee +
                '}';
    }
}
