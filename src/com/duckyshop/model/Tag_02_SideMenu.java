package com.duckyshop.model;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_02_SideMenu extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1589682607709358121L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();
		
        try
        {
        	if (session != null && session.getAttribute("user") != null) {
    			User user = (User) session.getAttribute("user");
    			Role role = user.getRole();
    			out.println(generateHTML(role));    			
    		} 
        }
        catch (IOException e)
        {
            throw new JspTagException("Tag_01_NavbarLogButton: " + e.getMessage());
        } // end try catch
        return SKIP_BODY;
	}

	public String generateHTML(Role role) {
		StringBuffer sb = new StringBuffer();
		int id = role.getRole();

		sb.append("<div class=\"sidemenu\">");
		switch (id) {
			case 4:
				sb.append("<a href='./ViewAccount'>Account</a>");
				
				sb.append("\n");
				sb.append("<a href='./ViewContact'>Contact</a>");
				sb.append("\n");
				sb.append("<a href='./ViewOrders'>Order</a>");
				sb.append("\n");
				break;
			case 3:
				sb.append("<a href='./ViewAccount'>Account</a>");
				sb.append("\n");
				sb.append("<a href='./ViewContact'>Contact</a>");
				sb.append("\n");
				sb.append("<hr>");
				sb.append("\n");
				sb.append("<a href='#ProcessOrdersServlet'>Process Orders</a>");
				sb.append("\n");
				sb.append("<a href='#ManageOrdersServlet'>Manage Orders</a>");
				sb.append("\n");
				break;
			case 2:
				sb.append("<a href='./ViewAccount'>Account</a>");
				sb.append("\n");
				sb.append("<a href='./ViewContact'>Contact</a>");
				sb.append("\n");
				sb.append("<hr>");
				sb.append("\n");
				sb.append("<a href='#ProcessOrdersServlet'>Process Orders</a>");
				sb.append("\n");
				sb.append("<a href='#ManageOrdersServlet'>Manage Orders</a>");
				sb.append("\n");
				sb.append("<a href='#ManageItemsServlet'>Manage Items</a>");
				sb.append("\n");
				break;
			case 1:
				sb.append("<a href='./ViewAccount'>Account</a>");
				sb.append("\n");
				sb.append("<a href='./ViewContact'>Contact</a>");
				sb.append("\n");
				sb.append("<hr>");
				sb.append("\n");
				sb.append("<a href='#ManageUsersServlet'>Manage Users</a>");
				sb.append("\n");
				break;
		}
		sb.append("</div>");
		return sb.toString();
	}
	
}
