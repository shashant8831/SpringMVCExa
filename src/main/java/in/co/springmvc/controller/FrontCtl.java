package in.co.springmvc.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Front controller will check authentication and authorization. If user is not
 * logged-in then forward control to login page.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 * 
 */
public class FrontCtl extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		System.out.println("Front Ctl Called");

		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			request.setAttribute("error",
					"Your session has been expired. Please re-Login.");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return false;
		}
		return true;
	}

}
