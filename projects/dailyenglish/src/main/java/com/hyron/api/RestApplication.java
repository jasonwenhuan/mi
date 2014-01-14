package com.hyron.api;


import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.hyron.api.service.LoginService;


public class RestApplication extends Application {
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(LoginService.class);
		return s;
	}
}
