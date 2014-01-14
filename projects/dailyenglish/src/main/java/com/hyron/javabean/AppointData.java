package com.hyron.javabean;

import java.util.List;

import com.hyron.presentation.utils.State;

public class AppointData {
    private List<AppointBean> data;
    private State state;
    
    public AppointData(){
    	
    }

	public List<AppointBean> getData() {
		return data;
	}

	public void setData(List<AppointBean> data) {
		this.data = data;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
    
    
}
