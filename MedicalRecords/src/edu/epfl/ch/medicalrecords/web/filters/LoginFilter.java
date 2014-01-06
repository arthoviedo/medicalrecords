package edu.epfl.ch.medicalrecords.web.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;

public class LoginFilter implements Filter {

	private List<String> registeredUsers = Lists.newLinkedList("arthoviedo");
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = ((HttpServletRequest)request);
        HttpServletResponse httpResponse = ((HttpServletResponse)response);
        
        String relativePath = httpRequest.getServletPath();
        System.out.println("Requesting: " + relativePath);
        UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user == null && !relativePath.equals("/_ah/login")) {
			System.out.println( "You are not logged in!");
			String redirect = userService.createLoginURL( httpRequest.getRequestURI());
			httpResponse.sendRedirect(redirect);
			return;
		}
		if (!registeredUsers.contains(user) && !relativePath.equals("/_ah/login")) {
			System.out.println( "You don't have a registered account, please contact the administrators");
			String redirect = userService.createLoginURL( httpRequest.getRequestURI());
			httpResponse.sendRedirect(redirect);
			return;
		}
		
        chain.doFilter(request, response);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	// ...
}