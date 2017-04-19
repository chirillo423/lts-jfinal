package com.zhb.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/", viewPath = "/WEB-INF/pages/")
public class HelloController extends Controller{
	
	public void index() {
		renderText("Hello Jfinal world!!!!!");
	}
	
	public void hello() {
		renderJsp("hello.jsp");
	}
	
}
