package com.hyron.presentation.utils;

public class Sort {
	private String key;
    private int dir;
    private boolean local;

    public final static int SORT_ASCENDING = 0;
    public final static int SORT_DESCENDING = 1;
    
    public Sort(){
    	
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String toJSONString() {
        StringBuffer sb = new StringBuffer("");
        sb.append("sort:{\"key\" :\"");
        sb.append(getKey());
        sb.append("\", \"local\":");
        sb.append(isLocal());
        sb.append(", \"dir\":");
        sb.append(getDir());
        sb.append("}");
        return sb.toString();
    }
}
