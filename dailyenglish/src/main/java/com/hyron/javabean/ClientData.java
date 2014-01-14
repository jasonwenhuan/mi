package com.hyron.javabean;

import java.util.List;

import com.hyron.presentation.utils.State;

public class ClientData {
    private List<ClientBean> data;
    private State state;
    
    public ClientData(){
    	
    }

	public List<ClientBean> getData() {
		return data;
	}

	public void setData(List<ClientBean> data) {
		this.data = data;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
    
    
}
