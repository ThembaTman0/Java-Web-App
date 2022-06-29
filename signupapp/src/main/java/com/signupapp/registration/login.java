package com.signupapp.registration;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Check if user can signin
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		HttpSession session=request.getSession();
		RequestDispatcher dispatcher=null;
		try {
//			Access the database
			Class.forName("com.mysql.cj.jdbc.Driver");
//			login into the database and insert the fecthed data
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false","root","1234");
			PreparedStatement pst=con.prepareStatement("select * from users where uemail = ? and upwd = ?");
			
			pst.setString(1, uemail);
			pst.setString(2, upwd);

			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
			{
				session.setAttribute("name",rs.getString("uname"));
				dispatcher =request.getRequestDispatcher("index.jsp");
			}
			else {
				request.setAttribute("status", "failed");
				dispatcher =request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
