package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UsersAPI
 */
@WebServlet("/UsersAPI")
public class UsersAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	Users userObj = new Users();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//No implementation
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = userObj.insertUsers(request.getParameter("name"), request.getParameter("email"), request.getParameter("userType"), request.getParameter("pw")); 
		
		response.getWriter().write(output);		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request); 
		
		String output = userObj.updateUsers(paras.get("hidItemIDSave").toString(), paras.get("name").toString(), paras.get("email").toString(), paras.get("userType").toString(), paras.get("pw").toString()); 
		
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request); 
		
		String output = userObj.deleteUsers(paras.get("userID").toString()); 
		
		response.getWriter().write(output); 
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) { 
		Map<String, String> map = new HashMap<String, String>(); 
		
		try	{ 
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : ""; 
			scanner.close(); 
			String[] params = queryString.split("&"); 
			
			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]); 
			} 
		} catch (Exception e) { 
			
		} 
		
		return map; 
	 }	
}
