package com.signupapp.registration;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		get the details entered by the user
//		Do this by getting the name="***" from <lable name="***"></lable>

		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String umobile=request.getParameter("contact");
		RequestDispatcher dispatcher =null;
//		DELETE ME
//		PrintWriter out= response.getWriter();
//		out.print(uname);
//		out.print(uemail);
//		out.print(upwd);
//		out.print(umobile);
		Connection con=null;
		try {
//			Access the database
			Class.forName("com.mysql.cj.jdbc.Driver");
//			login into the database and insert the fecthed data
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false","root","1234");
			PreparedStatement pst=con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?) ");
			
//			Since all rows are 'varchar' from mysql use setString
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
//			Check if user insersts anything into the database
//			check if user already has account if yess redirect to login
			int rowCount = pst.executeUpdate();
			dispatcher= request.getRequestDispatcher("registration.jsp");
			if(rowCount>0)
			{
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
				}catch(Exception e){
					e.printStackTrace();
			}finally {
				try {
					con.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			
		}
		
	

}
