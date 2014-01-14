package com.chinaunicom.filterman.ws.util;

public class PathBean {
    private String csvFilePath;
    
    private String csvBadBillPath;
    
    private String zonemapphonePath;

    private String defenseTestPath;

    public String getDefenseTestPath() {
        return defenseTestPath;
    }

    public void setDefenseTestPath(String defenseTestPath) {
        this.defenseTestPath = defenseTestPath;
    }

    public String getCsvFilePath() {
		return csvFilePath;
	}

	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public String getCsvBadBillPath() {
		return csvBadBillPath;
	}

	public void setCsvBadBillPath(String csvBadBillPath) {
		this.csvBadBillPath = csvBadBillPath;
	}

	public String getZonemapphonePath() {
		return zonemapphonePath;
	}

	public void setZonemapphonePath(String zonemapphonePath) {
		this.zonemapphonePath = zonemapphonePath;
	}
}
