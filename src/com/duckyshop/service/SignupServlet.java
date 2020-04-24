package com.duckyshop.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duckyshop.model.User;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirm 	= request.getParameter("confirm-password");
		String fullname = request.getParameter("fullname");
		String email 	= request.getParameter("email");
		
		String url = "./Signup.jsp";
		
		System.out.println(username);
		System.out.println(password);
		System.out.println(confirm);
		System.out.println(fullname);
		System.out.println(email);
		
		
		if (username == null || password == null || username.length() == 0 || password.length() == 0 ||
			confirm == null || confirm.length() == 0 ||
			fullname == null || fullname.length() == 0 ||
			email == null || email.length() == 0
		   ) {
			request.setAttribute("errorMsg", "One or more fields are empty!");
			request.getRequestDispatcher(url).forward(request, response);
		} else if (password.equals(confirm) == false) {
			request.setAttribute("errorMsg", "Password and confirm not match");
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			// add new user/password to database 
			Service services = new Service();	
			User user = services.addNewUser(username, password, fullname, email);
						
			if (user == null) {
				// something wrong when add to database
				request.setAttribute("errorMsg", "Unable to create new account!!!");
				request.getRequestDispatcher(url).forward(request, response);
			} else {
				// redirect back to login page
				url = "./Login.jsp";
				response.sendRedirect(url);
			}						
		}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
