<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.OutputStream"%>   
<%@ page import="java.io.File"%>
<%@ page import="java.util.ArrayList"%>
 <% 
  	String title =  "Olympus FIS OneLink Update"; 	 
%>

<!--    add to web.xml -- change directory as needed
  <context-param> 
   <description>Location to store uploaded file</description> 
   <param-name>file-upload</param-name> 
   <param-value>
      c:\Java_Dev\props\onelink\upload\
   </param-value> 
</context-param>
  
-->


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
<!-- ********************************************************************************************************************************************************* -->
<body style="font-family: sans-serif; color: black; font-size: 1;">
	<%@include file="includes/header.html"%>
	<div style="padding-left: 20px">
		<h3><%=title%></h3>
	</div>

	<form action="onelinkload.jsp" method="post"
		enctype="multipart/form-data">
		<table border="1" class="tablesorter">
			<tr bgcolor="#5DADE2" style="font-family: sans-serif; color: white;">
				<th class="a">Select File</th>
				<th class="a">Action</th>
			</tr>
			<tr>
				<td bgcolor="#AEB6BF"><input type="file" name="file" size="50" /></td>
				<td bgcolor="#AEB6BF"><input type="submit" value="Deliver File" /></td>

			</tr>
		</table>
	</form>
	<BR>

	<!-- ********************************************************************************************************************************************************* -->

	<!-- ********************************************************************************************************************************************************* -->
</body>
</html>