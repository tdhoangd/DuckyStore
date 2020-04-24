package com.duckyshop.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.duckyshop.dao.PasswordUtil;
import com.duckyshop.model.User;

/**
 * Servlet implementation class ChangePasswordSerlvet
 */
@WebServlet("/ChangePasswordSerlvet")
public class ChangePasswordSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordSerlvet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String oldPassword 		= request.getParameter("old-password");
		String newPassword 		= request.getParameter("new-password");
		String confirmNewPass 	= request.getParameter("confirm-new-password");
		
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("Login.jsp");
			return;
		}
			
		String changeMsg = null;

		if (oldPassword == null || oldPassword.length() == 0 || 
			newPassword == null || newPassword.length() == 0 ||
			confirmNewPass == null || confirmNewPass.length() == 0) {
			changeMsg = "ERROR: One or more fields are empty!!";
		} else if (!newPassword.equals(confirmNewPass))  {
			changeMsg = "ERROR: new password and confirm password not match";
		} else if (!PasswordUtil.verifyPassword(oldPassword, user.getHashpass(), user.getSalt())) {
			changeMsg = "ERROR: invalid current password";
		} else {	
			Service service = new Service();
			boolean succ = service.changePassword(user, newPassword);
			
			if (succ) {
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(30*60);
				changeMsg = "Password updated.";
			} else {
				changeMsg = "Unable to update password.";
			}				
			
		}
		
		request.setAttribute("changeMsg", changeMsg);
		request.getRequestDispatcher("./ViewAccount").forward(request, response);
							
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
