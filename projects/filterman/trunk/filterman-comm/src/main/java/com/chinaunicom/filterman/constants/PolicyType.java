package com.chinaunicom.filterman.constants;


public enum PolicyType {
	Three1Defense("1-1-1策略", 10), 
	Three2Defense("2-2-2策略", 20),
    ZonePay("区域比重策略", 30),
	BillInterval("支付间隔策略", 40),
	RelatedPhone("连号策略", 60),
	SuspectedUser("疑似用户", 70),
	BlackList("黑名单", 80), 
	WhiteList("白名单", 90);

	private String name;
	private int id;

	private PolicyType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getPolicyName() {
		return this.name;
	}

	public int getPolicyId() {
		return this.id;
	}

	public static PolicyType getPolicyByValue(String id) {
        int value = 0;
        try {
            value = Integer.parseInt(id);
        } catch (Exception e) {
            value = 0;
        }

		switch (value) {
		case 10:
			return Three1Defense;
		case 20:
			return Three2Defense;
        case 30:
            return ZonePay;
		case 40:
			return BillInterval;
		case 60:
			return RelatedPhone;
		case 70:
			return SuspectedUser;
		case 80:
			return BlackList;
        case 90:
            return WhiteList;
		default:
			return null;
		}
	}
    
	public static PolicyType[] getAllPolicies(){
		return PolicyType.values();
	}
}
