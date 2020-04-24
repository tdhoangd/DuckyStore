package com.duckyshop.model;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_01_Navbar extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();

        try
        {
        	out.println("<div class='topnav'>");
        	out.println("<a style='font-size:20px; padding-right:25px; '  href='./Index'><b>DUCKY STORE</b></a>");
        	out.println("<a class='search-container'>");
        	out.println("<form action='./Items' method='get'>");
        	out.println("<input type='text' placeholder='search...' name='search'>");
        	out.println("<button type='submit'>search</button>");  	
        	out.println("</form>");
        	out.println("</a>");
        	out.println("<div class='navright'>");
        	
        	if (session != null && session.getAttribute("user") != null) {
    			User user = (User) session.getAttribute("user");
    			String fullname = user.getFullname();
    			out.println("<a color='red' >Hello, <b>" + fullname + "</b></a>");
    			out.println("<a href='ViewCart'>Cart</a>");
    			out.println("<a href='LogoutServlet'>Logout</a>");  

    		} else {
    			out.println("<a href='./Login.jsp'>Login</a>");
    		}

        	out.println("</div>");
        	out.println("</div>");
        	        	
        }
        catch (IOException e)
        {
            throw new JspTagException("Tag_01_NavbarLogButton: " + e.getMessage());
        } // end try catch
        return SKIP_BODY;
	}

	
}
