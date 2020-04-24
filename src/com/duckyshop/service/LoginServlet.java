package com.duckyshop.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.duckyshop.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String url = "./Index";
			
		if (username == null || password == null || username.length() == 0 || password.length() == 0) {
			url = "/Login.jsp";
			request.setAttribute("loginResMsg", "Missing user name and/or password");
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			
			Service service = new Service();
			User user = service.login(username, password);
			if (user == null) {
				url = "/Login.jsp";
				request.setAttribute("loginResMsg", "Invalid username or/and password");
				request.getRequestDispatcher(url).forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(30*60);
				response.sendRedirect(url);
			}
					
		} // end if 
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
