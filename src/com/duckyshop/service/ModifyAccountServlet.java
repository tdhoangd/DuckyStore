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
 * Servlet implementation class ModifyAccountServlet
 */
@WebServlet("/ModifyAccountServlet")
public class ModifyAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyAccountServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String fullname = request.getParameter("fullname");
		String email 	= request.getParameter("email");
		
		User user = (User) session.getAttribute("user");
		
		String url = "./Login.jsp";
		if (user == null) {
			response.sendRedirect(url);
		} else {
			url = "./ViewAccount";
			if ((fullname == null || fullname.length() == 0) && (email == null || email.length() == 0)){
					request.setAttribute("editMsg", "Missing input.");
					request.getRequestDispatcher(url).forward(request, response);
					return;
			} else {
				if (fullname == null || fullname.length() == 0) {
					fullname = user.getFullname();
				} else if (email == null || email.length() == 0) {
					email = user.getEmail();
				}
				
				Service service = new Service();
				user = service.modifyUserInfo(user, fullname, email);
				
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(30*60);
				
				response.sendRedirect(url);
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
