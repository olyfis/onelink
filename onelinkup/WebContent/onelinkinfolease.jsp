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
  	 String title =  "Olympus FIS OneLink Get Infolease Data"; 	 
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
<script>


$(function() {

  // call the tablesorter plugin
  $("table").tablesorter({
    theme: 'blue',
    // initialize zebra striping of the table
    widgets: ["zebra"],
    // change the default striping class names
    // updated in v2.1 to use widgetOptions.zebra = ["even", "odd"]
    // widgetZebra: { css: [ "normal-row", "alt-row" ] } still works
    widgetOptions : {
      zebra : [ "normal-row", "alt-row" ]
    }
  });

});	
		
	
    </script>
</head>
<body>

<div style="padding-left:20px">
  <h3><%=title%></h3>
</div>

 
 <%@include  file="includes/header.html" %>
 <%!
 public String TableHeader(){
		String thead = null;
		String color1 = "#5DADE2";
		String style1 = "font-family: sans-serif; color: white;";	  
		//thead = "<tr bgcolor=" + color1 +  "style=" + style1 + ">";
		thead = "<tr class=\"b3\" "  +  "style=" + style1 + ">";
		//thead += "<th class=\"b\">App Number</th>";
		
		thead += "<th>ID</th>";
		thead += "<th>Contract ID</th>";
		thead += "<th>Customer</th>";
		thead += "<th>Date</th>";
		thead += "<th>Term</th>";
		thead += "<th>Active Date</th>";
		thead += "<th>Equip City</th>";
		thead += "<th>Equip State</th>";
		thead += "<th>Quantity</th>";
		thead += "<th>Model</th>";
		thead += "<th>Serial Number</th>";
		thead += "<th>Rental Amt</th>";
		thead += "<th>Equip Cost %</th>";
		thead += "<th>Misc Amt</th>";
	 
		
		
		
		return thead;
 }
 
 
 %>
 
 
 
 <% 

 String xDataItem = null;
 String color1 = "plum";
 String style1 = "font-family: sans-serif; color: white;";
 String rowEven = "#D7DBDD";
 String rowOdd = "AEB6BF";
 //retrieve your list from the request, with casting 
ArrayList<String> list = new ArrayList<String>();
String rowColor = null;
list = (ArrayList<String>) session.getAttribute("strArr");
//out.println("listSize=" + list.size());
	if (list.size() > 0 ) {
		out.println("<table class=\"tablesorter\" border=1> <thead> <tr>");
		out.println(TableHeader());
		out.println("</tr></thead>");
		out.println("<tbody id=\"report\">");
	
		String  appKeyValue = "";
		for (int i = 0; i < list.size(); i++) {
			rowColor = (i % 2 == 0) ? rowEven : rowOdd;
			out.println("<tr bgcolor=" + rowColor + ">");
	
			xDataItem = list.get(i);
			String token_list[] = xDataItem.split(":");
			
			for (int x = 0; x < token_list.length; x++) {
				if ( x == 2) {		
				 /*
				if ( x == 2) {
					appKeyValue =  token_list[x];
							out.println("<td class=\"odd\">" );
						out.println("</td>");
				 } else if (x == 3) {
					
				 out.println("<td class=\"odd\"> " );
									
					 out.println("</td>");
					 */
				out.println("<td class=\"odd\">" + token_list[x] + "</td>");
				} else {			
						out.println("<td class=\"odd\">" + token_list[x] + "</td>");
					}
			} // end inner for
				out.println("</tr>");
		} // end outer for

			out.println("</tbody></table> ");

		} else {
			out.println("No Asset data to display." + "<br>");
		}
	%>
 
</body>
</html>