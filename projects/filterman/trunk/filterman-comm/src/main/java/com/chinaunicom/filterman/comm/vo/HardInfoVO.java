package com.chinaunicom.filterman.comm.vo;

/**
 * User: larry
 */

public class HardInfoVO {

    private String uuid;
    private String mac = "";
    private String imei = "";
    private String imsi = "";
    private String orderid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getOrderId() {
        return orderid;
    }

    public void setOrderId(String orderId) {
        this.orderid = orderId;
    }
}
