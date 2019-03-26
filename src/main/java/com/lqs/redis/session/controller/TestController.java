package com.lqs.redis.session.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/set-session")
	public String setSession(@RequestParam(name = "sessionVal",required = false) String sessionVal, HttpSession session, HttpServletRequest request) {
		System.out.println("Param 'sessionVal' = " + sessionVal);
		session.setAttribute("sessionVal", sessionVal);
		return "port " + request.getLocalPort() + " , the session val is " + sessionVal;
	}

	@GetMapping("/get-session")
	public Object readSession(HttpSession httpSession, HttpServletRequest request) {
		Object obj = httpSession.getAttribute("sessionVal");
		System.out.println("'sessionVal' in Session = " + obj);
		return "port " + request.getLocalPort() + " , the session val is " + obj;
	}

}
