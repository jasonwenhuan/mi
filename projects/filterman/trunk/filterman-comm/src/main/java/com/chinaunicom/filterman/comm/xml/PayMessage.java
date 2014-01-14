package com.chinaunicom.filterman.comm.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: larry
 */

@XmlRootElement(name="paymessages")
public class PayMessage {

    private String checkOrderIdRsp;
    private String appname;
    private String feename;
    private String payfee;
    private String appdeveloper;
    private String gameaccount;
    private String macaddress;
    private String appid;
    private String ipaddress;
    private String serviceid;
    private String channelid;
    private String cpid;
    private String ordertime;
    private String imei;
    private String appversion;
    private String usercode;
    private String imsi;
    private String orderid;
    private boolean isTest = false;
    private String paytype;

    @Override
    public String toString() {
        return "PayMessages{" +
                "checkOrderIdRsp='" + checkOrderIdRsp + '\'' +
                ", appname='" + appname + '\'' +
                ", feename='" + feename + '\'' +
                ", payfee='" + payfee + '\'' +
                ", appdeveloper='" + appdeveloper + '\'' +
                ", gameaccount='" + gameaccount + '\'' +
                ", macaddress='" + macaddress + '\'' +
                ", appid='" + appid + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", serviceid='" + serviceid + '\'' +
                ", channelid='" + channelid + '\'' +
                ", cpid='" + cpid + '\'' +
                ", ordertime='" + ordertime + '\'' +
                ", imei='" + imei + '\'' +
                ", appversion='" + appversion + '\'' +
                ", usercode='" + usercode + '\'' +
                ", imsi='" + imsi + '\'' +
                ", orderid='" + orderid + '\'' +
                ", paytype='" + paytype + '\'' +
                '}';
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCheckOrderIdRsp() {
        return checkOrderIdRsp;
    }
    public void setCheckOrderIdRsp(String checkOrderIdRsp) {
        this.checkOrderIdRsp = checkOrderIdRsp;
    }
    public String getAppname() {
        return appname;
    }
    public void setAppname(String appname) {
        this.appname = appname;
    }
    public String getFeename() {
        return feename;
    }
    public void setFeename(String feename) {
        this.feename = feename;
    }
    public String getPayfee() {
        return payfee;
    }
    public void setPayfee(String payfee) {
        this.payfee = payfee;
    }
    public String getAppdeveloper() {
        return appdeveloper;
    }
    public void setAppdeveloper(String appdeveloper) {
        this.appdeveloper = appdeveloper;
    }
    public String getGameaccount() {
        return gameaccount;
    }
    public void setGameaccount(String gameaccount) {
        this.gameaccount = gameaccount;
    }
    public String getMacaddress() {
        return macaddress;
    }
    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getIpaddress() {
        return ipaddress;
    }
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
    public String getServiceid() {
        return serviceid;
    }
    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }
    public String getChannelid() {
        return channelid;
    }
    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }
    public String getCpid() {
        return cpid;
    }
    public void setCpid(String cpid) {
        this.cpid = cpid;
    }
    public String getOrdertime() {
        return ordertime;
    }
    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getImei() {
        return imei;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }
    public String getAppversion() {
        return appversion;
    }
    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }
    public String getUsercode() {
        return usercode;
    }
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }
    public String getImsi() {
        return imsi;
    }
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
}
