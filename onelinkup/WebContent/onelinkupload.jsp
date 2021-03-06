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
    C:\temp\
   </param-value> 
</context-param>
-->

<% 
  	 String title =  "Olympus FIS OneLine File Upload"; 	 
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
ArrayList<String> dataArr = new ArrayList<String>();
File file ;
int maxFileSize = 15000 * 1024;
int maxMemSize = 15000 * 1024;
ServletContext context = pageContext.getServletContext();
String filePath = context.getInitParameter("file-upload");
String xDataItem = null;
String rowEven = "#D7DBDD";
String rowOdd = "AEB6BF";
String rowColor = "";
Boolean chkRtn = false;
// Verify the content type
String contentType = request.getContentType();

String baseFileName = "";
 


if ((contentType.indexOf("multipart/form-data") >= 0)) {
    DiskFileItemFactory factory = new DiskFileItemFactory();
    // maximum size that will be stored in memory
    factory.setSizeThreshold(maxMemSize);
    
    // Location to save data that is larger than maxMemSize.
    factory.setRepository(new File("c:\\temp"));

    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);
    
    // maximum file size to be uploaded.
    upload.setSizeMax( maxFileSize );
    
    try { 
       // Parse the request to get file items.
       List fileItems = upload.parseRequest(request);

       // Process the uploaded file items
       Iterator i = fileItems.iterator();
/*
       out.println("<html>");
       out.println("<head>");
       out.println("<title>JSP File upload</title>");  
       out.println("</head>");
       out.println("<body>");
      */
       while ( i.hasNext () ) {
          FileItem fi = (FileItem)i.next();
          if ( !fi.isFormField () ) {
             // Get the uploaded file parameters
             String fieldName = fi.getFieldName();
             String fileName = fi.getName();
             boolean isInMemory = fi.isInMemory();
             long sizeInBytes = fi.getSize();
          
             // Write the file
             if( fileName.lastIndexOf("\\") >= 0 ) {
                file = new File( filePath + 
                fileName.substring( fileName.lastIndexOf("\\"))) ;
             } else {
                file = new File( filePath + 
                fileName.substring(fileName.lastIndexOf("\\")+1)) ;
             }
             fi.write( file ) ;
            // out.println("Uploaded Filename: " + filePath +  fileName + "<br>");
             
             //out.println("<h5>Uploaded Filename: " + fileName + "</h5>");
             //out.println("<h5>Uploaded to Directory: " + filePath + "</h5><br>");
             String srcFile = filePath + "\\" + basename(fileName);
             //out.println("<h5>srcFile: " + srcFile + "</h5><br>");
             dataArr = readFileData(srcFile);
          	//out.println("******* dataArr size=" + dataArr.size() + "--");
             baseFileName = basename(fileName);
       //out.println("<h5>******* filename=" +baseFileName  + "--</h5>");
       
     
       
             %>
    <!--    ************************************************************************************************************************ -->
         
              
  <!--    ************************************************************************************************************************ -->
	<h5>Create OneLink Upload CSV Template </h5>
	
	<form action="/onelinkup/getcsv" method="get">
		<table border="1">
			<tr bgcolor="#5DADE2" style="font-family: sans-serif; color: white;">
			<th class="a">File Name</th>
				<th class="a">Contract ID</th>
				<th class="a">Create Template</th>
			</tr>
			<tr>			
			<td>  <%= baseFileName %> </td>
				<td><input type="text" id="id" name="id"
					maxlength="20" size="20"></td>
				<td bgcolor="#AEB6BF"><input type="submit"
					value="Build Template" /></td>
			</tr>
		</table>
		<input type = "hidden" name="baseFileName" value = <%= baseFileName %> />
	</form>


	<!--    ************************************************************************************************************************ -->
    
             	   
            <%  
             
          }
       }
       //out.println("</body>");
      // out.println("</html>");
    } catch(Exception ex) {
       System.out.println(ex);
    }
 } else {
	   /*
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Servlet upload</title>");  
    out.println("</head>");
    out.println("<body>");
    */
    out.println("<p>No file uploaded</p>"); 
   // out.println("</body>");
    //out.println("</html>");
 }



%>


 

</body>
</html>