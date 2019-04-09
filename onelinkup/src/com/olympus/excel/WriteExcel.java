package com.olympus.excel;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.NodeList;

import com.olympus.olyutil.Olyutil;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/excel")
public class WriteExcel extends HttpServlet {
	/****************************************************************************************************************************************************/

	public static String[] splitStr(String string, String delimiter) {
		String[] result = string.split(delimiter);
		int array_length = result.length;

		for (int i = 0; i < array_length; i++) {
			result[i] = result[i].trim();
		}
		return result;
	}
	
 
	/****************************************************************************************************************************************************/
	public static void loadWorkSheet(XSSFWorkbook workbook, XSSFSheet sheet, ArrayList<String> strArr, int rowNum) {
		String[] strSplitArr = null;

		//System.out.println("************* strArr=" + strArr.toString());
		for (String str : strArr) { // iterating ArrayList
			Row row = sheet.createRow(rowNum++);
			strSplitArr = splitStr(str, ",");
			int colNum = 0;
			for (String token : strSplitArr) {
				Cell cell = row.createCell(colNum++);
				if (token instanceof String) {
					cell.setCellValue((String) token);
				}
			}
		}
	}
	
 		
	/****************************************************************************************************************************************************/

	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		
		
		//System.out.println("*** IN doGet:" );
		
		ArrayList<String> csvArr = (ArrayList<String>) request.getSession().getValue("csvArr");
		//Olyutil.printStrArray(strArr);
		

		//System.out.println("*** IN doGet:" );
		
		/*
		for (String str : csvArr) { // iterating ArrayList
			System.out.println(str);
		}
	 
	  */
		Date date = Olyutil.getCurrentDate();
		String dateStamp = date.toString();
		//System.out.println("Date=" + dateStamp);
		 String FILE_NAME = "OneLink_Template_" + dateStamp  + ".xlsx";
		

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("OneLink Template");
		loadWorkSheet(workbook, sheet, csvArr, 1);

		response.setContentType("application/vnd.ms-excel"); // Set up mime type
		response.addHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);
		OutputStream out2 = response.getOutputStream();
		workbook.write(out2);
		out2.flush();
		
		
	}

}
