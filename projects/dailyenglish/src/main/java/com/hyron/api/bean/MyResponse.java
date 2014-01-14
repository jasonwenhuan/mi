package com.hyron.api.bean;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class MyResponse{
    private State state;
	private Object body ;
	public MyResponse(State ms,Object obj){
		state = ms;
		body = obj;
	}
	
	public Object getObject() {
		return body;
	}
	public void setObject(Object object) {
		this.body = object;
	}

	public State getMystate() {
		return state;
	}

	public void setMystate(State mystate) {
		this.state = mystate;
	}
	
}
