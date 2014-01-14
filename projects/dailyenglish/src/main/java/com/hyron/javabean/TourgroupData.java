package com.hyron.javabean;

import java.util.List;

import com.hyron.presentation.utils.State;

public class TourgroupData {
    private List<TourgroupBean> data;
    private State state;
    public TourgroupData(){
    	
    }
	
	public List<TourgroupBean> getData() {
		return data;
	}

	public void setData(List<TourgroupBean> data) {
		this.data = data;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
    
    
}
