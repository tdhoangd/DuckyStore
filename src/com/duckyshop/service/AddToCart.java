package com.duckyshop.service;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.duckyshop.model.CartItem;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String url = "./Index";
		HttpSession session = request.getSession();
		
		if (session.getAttribute("user") == null) {
			response.sendRedirect("./Login.jsp");
			return;
		}
		
		HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>) session.getAttribute("cart");
				
		if (cart == null) {
			cart = new HashMap<Integer, CartItem>();
		}  
		
		String strId = request.getParameter("iid");
		String strQty = request.getParameter("qty");
		
		if (strId == null || strId.length() == 0 || strQty == null || strQty.length() == 0) {
			response.sendRedirect(url);
		} else {
			int id 	= Integer.parseInt(strId);
			int qty = Integer.parseInt(strQty);
			
			if (cart.get(id) == null) {
				CartItem ci = new CartItem(id, qty, 
										 	request.getParameter("name"), 
										 	Double.parseDouble(request.getParameter("price")));	
				cart.put(id, ci);
			} else {
				if (qty > 0) {
					CartItem ci = cart.get(id);
					ci.setQuantity(qty + ci.getQuantity());
					cart.put(id, ci);				
				}
			}		
			
			session.setAttribute("cart", cart);	
			request.getRequestDispatcher(url).forward(request, response);
		}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
