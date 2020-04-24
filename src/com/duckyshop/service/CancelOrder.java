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
 * Servlet implementation class CancelOrder
 */
@WebServlet("/CancelOrder")
public class CancelOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelOrder() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = "orders.jsp";
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("Login.jsp");
			return;
		} 
		
		String msg = null;
		String oidStr = request.getParameter("oid");
		if (oidStr == null || oidStr.length() == 0) {
			msg = "Invalid order number.";
		}
		
		Service service = new Service();
		
		if (service.cancelOrder(user.getId(), Integer.parseInt(oidStr))) {
			// delete order
			msg = "Order no. " + oidStr + " deleted.";
		}
		
		// update order list
		request.setAttribute("orderList", service.getOrders(user.getId()));
		
		request.setAttribute("resMsg", msg);
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
