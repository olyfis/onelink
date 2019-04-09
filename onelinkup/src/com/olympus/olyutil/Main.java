package com.olympus.olyutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.w3c.dom.NodeList;
import java.sql.*;

import org.json.simple.*;
//import org.json.*;

public class Main {
	static String propFile = "C:\\Java_Dev\\props\\connectionRapport.prop";
	
	public Main() {
		// TODO Auto-generated constructor stub
	}

	static Statement stmt = null;
	static Connection con = null;
	static ResultSet res  = null;
	static NodeList  node  = null;
	static String s = null;
	static private PreparedStatement statement;
	
	/*
	static 	String query = "SELECT DISTINCT  LS_MASTER.ID, UATB_SC_BRANCH, UATB_SC_CUST_NAME, INSURANCE_CODE, LS_MASTER.UATB_SC_ACTIV_DATE, EQUIPMENT_COST, INSUR_EFFECTIVE_DATE, INSUR_EXPIRE_DATE" +
     		  " FROM LS_MASTER_RPTS_NF LS_MASTER,  LS_NEW_FIELDS_NF LS_NEW_FIE,  LS_CADDR_NF LS_CADDR_N,  IT_POINTERS_NF IT_POINTER,  IT_INSURANCE_NF IT_INSURAN " 
     		  + " WHERE LS_MASTER.ID = LS_NEW_FIE.ID AND LS_MASTER.ID = LS_CADDR_N.ID AND LS_MASTER.CUST_CREDIT_ACCT = IT_POINTER.ITPTR_CUSTOMER_KEY "
     		  + "AND IT_POINTER.ID = IT_INSURAN.ID AND ( UATB_SC_NUM_OF_ASSETS > 0 AND CONTRACT_STATUS NOT IN( '03', '04' ) AND UATB_SC_ACTIV_DATE IS NOT NULL) and UATB_SC_CUST_NAME = 'UPMC' "; 
	
	*/
	
	public static void main(String[] args) throws Exception { // Auto-generated method stub
		
		
		String result = null;
		//Olyutil jutil = new Olyutil();
		//FileInputStream fis = new FileInputStream("C:\\Java_Dev\\props\\connection.prop");
		FileInputStream fis = new FileInputStream(propFile);
		Properties connectionProps = new Properties();
		String s = new String();
        StringBuffer sb = new StringBuffer();
        JSONArray jsonArr = new JSONArray(); 
        JSONObject jsonObject = new JSONObject(); 
        ArrayList<String> strArr = new ArrayList<String>();
        
		connectionProps.load(fis);
	 
		
		//System.out.println("Test");
 
		//FileReader fr = new FileReader(new File("C:\\Java_Dev\\props\\sql\\il_booking.sql"));
		//FileReader fr = new FileReader(new File("C:\\Java_Dev\\props\\sql\\rapbooking.sql"));
		FileReader fr = new FileReader(new File("C:\\Java_Dev\\props\\sql\\onelink.sql"));
		
		// be sure to not have line starting with "--" or "/*" or any other non alphabetical character
		BufferedReader br = new BufferedReader(fr);
		while((s = br.readLine()) != null){
		      sb.append(s);
		}
		br.close();
		
		String query = new String();
		query = sb.toString();
		System.out.println("Query=" + query);
		
		 Map<String,String> propMap =  Olyutil.getProperties(propFile);
/*
		 System.out.println("******************** Begin Map ******************************");
		 jutil.printHashMap(propMap);
		 System.out.println("******************** End Map ******************************");
		 
		 System.out.println("*** Date:" + jutil.getDate());
		 
*/ 
		try {
			con = Olyutil.getConnection(connectionProps);
			if (con != null) {
				System.out.println("Connected to the database");
//************************************************************************************************************************************************
				statement = con.prepareStatement(query);				
				
				//System.out.println(query2);
				//String date = br2.getCurrentDate().toString();
				//System.out.println("DATE=" + date + "--" );	
				
				//statement.setString(1, "2018-10-16");
				res = Olyutil.getResultSetPS(statement);
				//res = jutil.getResultSet(con, stmt, query);				 
				strArr = Olyutil.resultSetArray(res, ":");			
				//System.out.println("**** arrSize=" + strArr.size() + " ---- Date:"  + date);
			
				res = Olyutil.getResultSetPS(statement);
				System.out.print(strArr.toString());
				
				//jsonArr = jutil.resultSetJSON(res);
				//System.out.print(jsonArr.toString());	
				//jutil.displayJsonArray(jsonArr);
//************************************************************************************************************************************************
				// result = jutil.displayResults(res);
				// JButils.getXMLNodeList("D:\\pentaho\\Kettle\\Dashboard\\XML\\bobj_report3.xml",
				// JButils.displayXMLNodeList(node);
			} else {
				System.out.println("**** NOT Connected to the database");
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
			} // end finally try
		}
		// System.out.println("Q: " + query);
		// convert to string object and print it
		// System.out.println("Query Read: " + sb.toString());
		// System.out.println("*** END");
		
	}
}

