package com.journaldev.servlet.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.print.attribute.Attribute;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if(userID.equals(user) && password.equals(pwd)){
			HttpSession session = request.getSession();
			session.setAttribute("user", "testUser");
//			char[] s = new char[1024 * 1000];
//			String str = String.copyValueOf(s);
//			session.setAttribute("string", str);
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("user", user);
			userName.setMaxAge(30*60);
			response.addCookie(userName);
			System.out.println("User="+session.getAttribute("user"));
			Cookie[] cookies = request.getCookies();
//			System.out.println((String)session.getAttribute("string"));
			if (cookies!= null) {
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("JSESSIONID")){
						System.out.println("JSESSIONID="+cookie.getValue());
						break;
					}
				}
			} else {
				System.out.println("JSESSIONID="+session.getId());
			}

			response.sendRedirect("LoginSuccess.jsp");
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or pwd is wrong.</font>");
			rd.include(request, response);
		}

	}

}
