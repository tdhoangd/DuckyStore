package com.duckyshop.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.duckyshop.model.CartItem;
import com.duckyshop.model.User;


/**
 * Servlet implementation class ProcessCart
 */
@WebServlet("/ProcessCart")
public class ProcessCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessCart() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = "cart.jsp";
		HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>) session.getAttribute("cart");
		
		if (cart == null) {
			// nothing in cart
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		
		if (request.getParameter("checkout") != null) {
			// process check out
			User user = (User) session.getAttribute("user");
			
			if (user == null) {
				request.setAttribute("resMsg", "Please login first.");
			} else if (user.getContact() == null) {
				request.setAttribute("resMsg", "No contact found.");
			} else {
				Service service = new Service();
				boolean state = service.checkout(user.getId(), user.getContact().getId(), cart);
				
				if (state == true) {
					session.removeAttribute("cart");
					session.setAttribute("resMsg", "Your order have been placed.");
					response.sendRedirect(url);
					return;
				} else {
					response.sendRedirect(url);
				}	
			}				
			
		} else {
			// update cart
			for(Iterator<Map.Entry<Integer, CartItem>> it = cart.entrySet().iterator(); it.hasNext(); ) {
			    Map.Entry<Integer, CartItem> entry = it.next();
			    int iid = entry.getKey();
			    if (request.getParameter("update-btn-" + iid) != null) {
					// update button is clicked
					int update_qty = Integer.parseInt(request.getParameter(Integer.toString(iid)));
					if (update_qty > 0) {
						CartItem ci = entry.getValue();
						ci.setQuantity(update_qty);
						entry.setValue(ci);
					}
					else 
						it.remove();
					
				} else if (request.getParameter("delete-btn-" + iid) != null) {
					// delete button is clicked
					it.remove();
				}	
			}

			session.setAttribute("cart", cart);			
		}
		
		double total = 0.0;
		for(Iterator<Map.Entry<Integer, CartItem>> it = cart.entrySet().iterator(); it.hasNext(); ) {
		    Map.Entry<Integer, CartItem> entry = it.next();
		    total += entry.getValue().getPrice()*entry.getValue().getQuantity();
		}
		request.setAttribute("total", total);
		
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
