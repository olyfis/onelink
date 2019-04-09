package com.olympus.onelink;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.NodeList;

import com.olympus.olyutil.Olyutil;

@WebServlet("/getdata")
public class GetInfoleaseData extends HttpServlet { 
	
	// Service method of servlet
	static Statement stmt = null;
	static Connection con = null;
	static ResultSet res  = null;
	static NodeList  node  = null;
	static String s = null;
	static private PreparedStatement statement;
	static String propFile = "C:\\Java_Dev\\props\\unidata.prop";
	
	static String sqlFile = "C:\\Java_Dev\\props\\sql\\onelinkfull.sql";
	

	/****************************************************************************************************************************************************/
	public static ArrayList<String> getDbData(String contractID) throws IOException {
		FileInputStream fis = null;
		FileReader fr = null;
		String s = new String();
        StringBuffer sb = new StringBuffer();
        ArrayList<String> strArr = new ArrayList<String>();
		try {
			fis = new FileInputStream(propFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties connectionProps = new Properties();
		connectionProps.load(fis);
		 
		fr = new FileReader(new File(sqlFile));
		
		// be sure to not have line starting with "--" or "/*" or any other non alphabetical character
		BufferedReader br = new BufferedReader(fr);
		while((s = br.readLine()) != null){
		      sb.append(s);
		       
		}
		br.close();
		//displayProps(connectionProps);
		String query = new String();
		query = sb.toString();	
		//System.out.println("Query=" + query);	 
		try {
			con = Olyutil.getConnection(connectionProps);
			if (con != null) {
				//System.out.println("Connected to the database");
				statement = con.prepareStatement(query);
				
				//System.out.println("***^^^*** contractID=" + contractID);
				statement.setString(1, contractID);
				res = Olyutil.getResultSetPS(statement);		 	 
				strArr = Olyutil.resultSetArray(res, ":");			
			}		
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return strArr;
	}
	/****************************************************************************************************************************************************/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contractID = "";
		ArrayList<String> strArr = new ArrayList<String>();
		

		String paramName = "id";
		String paramValue = request.getParameter(paramName);
		//if ((paramValue != null && !paramValue.isEmpty()) && paramValue.equals("id")) {
		if ((paramValue != null && !paramValue.isEmpty())) {	
			 contractID = paramValue.trim();
			 //System.out.println("*** contractID:" + contractID);			 
		}
		String dispatchJSP = "/onelinkinfolease.jsp";
		strArr = getDbData(contractID);
		//Olyutil.printStrArray(strArr);
		request.getSession().setAttribute("strArr",strArr);
		 request.getRequestDispatcher(dispatchJSP).forward(request, response);
		 
	}
	/****************************************************************************************************************************************************/

}
