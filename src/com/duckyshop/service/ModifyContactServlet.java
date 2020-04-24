package com.duckyshop.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.duckyshop.model.Contact;
import com.duckyshop.model.User;

/**
 * Servlet implementation class ModifyContactServlet
 */
@WebServlet("/ModifyContactServlet")
public class ModifyContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyContactServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname = request.getParameter("fname");
		String mname = request.getParameter("mname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String city	= request.getParameter("city");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
				
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			Service service = new Service();
			Contact contact = service.getContact(user.getId());
			String msg = null; 
			
			if (fname == null || fname.length() == 0 || 
				lname == null || lname.length() == 0 || 
				email == null || email.length() == 0 || 
				address == null || address.length() == 0 || 
				city == null || city.length() == 0 || 
				state == null || state.length() == 0 || 
				zipcode == null || zipcode.length() == 0 || 
				country == null || country.length() == 0 
				) {
				msg = "One or more fields empty!";
			} else {
				
				if (request.getParameter("update-contact-btn") != null) {
					if (contact == null) {
						msg = "Invalide action";
					} else {
						// UPDATE INFOR SERVICE
						contact = service.updateContact(contact.getId(), fname, mname, lname, email, address,
								 city, state, zipcode, country);
						msg = "Contact updated.";
					}
				} else if (request.getParameter("add-contact-btn") != null) {
					contact = service.addContact(user.getId(), fname, mname, lname, email, address,
							 city, state, zipcode, country);
					msg = "Contact added.";
				}
			}
			
			request.setAttribute("resMsg", msg);
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("contact.jsp").forward(request, response);
		} else {
			response.sendRedirect("Login.jsp");		
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
