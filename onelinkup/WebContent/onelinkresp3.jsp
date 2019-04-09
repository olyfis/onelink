<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.OutputStream"%>   
    
<%@ page import="java.io.File"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import = "javax.servlet.http.*" %>
<%@ page import = "org.apache.commons.fileupload.*" %>
<%@ page import = "org.apache.commons.fileupload.disk.*" %>
<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
<%@ page import = "org.apache.commons.io.output.*" %>
<%@ page import="java.sql.*"%>
<!--    add to web.xml -- change directory as needed
 <context-param> 
   <description>Location to store uploaded file</description> 
   <param-name>file-upload</param-name> 
   <param-value>
      D:\Pentaho\Kettle\RollOver\Upload\
   </param-value> 
</context-param>
-->

<% 
  	 String title =  "Olympus FIS OneLink Template File Generator"; 	 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 
<title><%=title%></title>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.9.1/jquery.tablesorter.min.js"></script>
<!--  <script type="text/javascript" src="includes/js/tablesort.js"></script> -->
<style><%@include file="includes/css/reports.css"%></style>
<style><%@include file="includes/css/table.css"%></style>
<style><%@include file="includes/css/header.css"%></style>
<script type="text/javascript" src="includes/js/tableFilter.js"></script>
<link rel="stylesheet" href="includes/css/calendar.css" />
</head>
<%!/*******************************************************************************************************************************************************************/
public ArrayList<String> readFileData(String filePath){
	 ArrayList<String> arr = new ArrayList<String>();
	 
     try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
     {
         String sCurrentLine;
         while ((sCurrentLine = br.readLine()) != null) {
             arr.add(sCurrentLine);
         }
     } catch (IOException e) {
         e.printStackTrace();
     } 
	return arr;
}
/*******************************************************************************************************************************************************************/
public static String basename(String path) {
		String filename = "";
		//System.out.println("PATH=" + path);
		String[] pathparts = path.split("\\\\");
		filename = pathparts[pathparts.length - 1];
		//System.out.println("FN=" + filename);
		return filename;
	}
/*******************************************************************************************************************************************************************/
%>
<body>
 <%@include  file="includes/header.html" %>
<%
//retrieve your list from the request, with casting 
ArrayList<String> dataArr = new ArrayList<String>();
String rowColor = null;
//out.println("<h5>Response received</h5>");
String formUrl =  (String) session.getAttribute("formUrl");
dataArr = (ArrayList<String>) session.getAttribute("csvArr");
String rowEven = "#D7DBDD";
String rowOdd = "AEB6BF";
String xDataItem = null;
formUrl = "/onelinkup/excel";
//String[] csvarr = dataArr.toArray(new String[dataArr.size()]);
 
session.setAttribute("csvArr", dataArr);
%>
<table class="noBorder">
<tr  class="noBorder" ><td  class="noBorder">
<!--    ************************************************************************************************************************ -->
<form name="excelForm" enctype="multipart/form-data" method="get" action=" <%=formUrl%> " \>
<% 
//out.println("<input type=\"hidden\"  name=\"startDate\" value=\"" + startDate  +  "\" >" );
//out.println("<input type=\"hidden\"  name=\"endDate\" value=\"" + endDate  +  "\" >" );
// out.println("<input type=\"hidden\"  name=\"csvArr\" value=\"" + csvarr +  "\" >" );
out.println("<input type=\"submit\" value=\"Save Excel File\" class=\"btn\" /> ");
out.println("</form>");
 


out.println("</td></tr></table><br>");
 
 /*****************************************************************************************************************************************************************************/

 //out.println("DS=" + dataArr.size());
 
 if (dataArr.size() > 0 ) {
	out.println("<table> ");
	for (int i = 0; i < dataArr.size(); i++) {
		rowColor = (i % 2 == 0) ? rowEven : rowOdd;
		out.println("<tr bgcolor=" + rowColor + ">");

		xDataItem = dataArr.get(i);
		String token_list[] = xDataItem.split(",");
		
		for (int x = 0; x < token_list.length; x++) {
			if ( x == 2) {		

			out.println("<td class=\"odd\">" + token_list[x] + "</td>");
			} else {			
					out.println("<td class=\"odd\">" + token_list[x] + "</td>");
				}
		} // end inner for
			out.println("</tr>");
	} // end outer for
	
	out.println("</td></tr></table><br>");
}
 
 
 
  /*****************************************************************************************************************************************************************************/



%>

</body>
</html>