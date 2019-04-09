package com.olympus.olyutil;

 

import java.io.*;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.*;
 
 

public class Olyutil {
	/*************************************************************************************************************************************************************/
	// Service method of servlet
	static Statement stmt = null;
	static Connection con = null;
	static ResultSet res = null;
	static NodeList node = null;
	static String s = null;
	static private PreparedStatement statement;
	
	

	/*************************************************************************************************************************************************************/
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
	/*************************************************************************************************************************************************************/
	public static void displayProps(Properties p) {
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
		    String key = (String)keys.nextElement();
		    String value = (String)p.get(key);
		    System.out.println(key + ": " + value);
		}
		
	}
	
	 
	/*************************************************************************************************************************************************************/
	public static void printHashMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }	    
	   // System.out.println("********^^^^************\n");
	}
	/*************************************************************************************************************************************************************/
	// Read property file into a HashMap
	public static Map<String,String> getProperties(String propFilePath)  {
        Properties prop = new Properties();
        Map<String,String>map = new HashMap<String,String>();
        try {
            FileInputStream inputStream = new FileInputStream(propFilePath);
            prop.load(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some issue finding or loading file....!!! " + e.getMessage());
        }
        for (final Entry<Object, Object> entry : prop.entrySet()) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
        return map;		
	}
	/*************************************************************************************************************************************************************/
	/*************************************************************************************************************************************************************/
	// method to get columns name s from DB
	public static ArrayList<String> getColNames(ResultSet resultSet) throws Exception {
		ArrayList<String> colArr = new ArrayList<String>();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			String name = rsmd.getColumnName(i);
			colArr.add(name);
			// System.out.println("**** ColCount: " + columnCount + " Name: " + name);
		}
		return colArr;
	}
	/*************************************************************************************************************************************************************/
	//method to print array
	public static void printName(ArrayList<String> line_arr) {
			// iterating ArrayList
			for (String str : line_arr)
				System.out.println("DATA:" + str + "---");
			// System.out.println(names[index]);
	}
	/*************************************************************************************************************************************************************/
	
	/*************************************************************************************************************************************************************/
	public static ResultSet getResultSet(Connection conn, String query) throws SQLException {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return (rs);
	}
	/*************************************************************************************************************************************************************/
	public static ResultSet getResultSetPS(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery();
		return (rs);
	}
	/*************************************************************************************************************************************************************/
	public static ArrayList<String> getFilesFromDir(String uploadDir) throws Exception { // Read files from a directory into an array
		ArrayList<String> fileListArr = new ArrayList<String>();
		
		File f = new File(uploadDir); // current directory
		File[] files = f.listFiles();
 
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.print("****^^^^**** directory: ");
			} else {
				//System.out.print("     file: ");	
			}
			 
			fileListArr.add(file.getCanonicalPath());
		}	
		return fileListArr;
	}
	/*************************************************************************************************************************************************************/
	public static String do_getQuery(String sqlFilePath ) throws Exception { // read a query from a file and return the query
		 String queryStr = "";
		 
			String s = new String();
	        StringBuffer sb = new StringBuffer();
			FileReader fr = new FileReader(new File(sqlFilePath));			
			// be sure to not have line starting with "--" or "/*" or any other non alphabetical character
			BufferedReader br = new BufferedReader(fr);
			while((s = br.readLine()) != null) {
			      sb.append(s);
			}
			br.close();	
			String query = new String();
			query = sb.toString();
			//System.out.println("**** Query: " + query);		 
		 return query;
	 }
	/*************************************************************************************************************************************************************/
	public static ArrayList<String> do_runQuery(Connection con, String query, String sep) throws Exception { // 
		ArrayList<String> strArr = new ArrayList<String>();	
		//System.out.println("**** Query: " + query);
		statement = con.prepareStatement(query);	
		res = getResultSetPS(statement);	 
		strArr = resultSetArray(res, sep);			
		//System.out.println("**** arrSize=" + strArr.size()  );
		//System.out.println("**** arr:" + strArr.toString());	
		//result = jutil.displayResults(res);
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} // end finally try			
		return strArr;
	}
	/*************************************************************************************************************************************************************/
	public static Boolean do_checkDbRec(Connection con, String recID, String query, String sep) throws Exception { // check for a particular recordId in a table
		Boolean status = false;
		ArrayList<String> strArr = new ArrayList<String>();	
		//System.out.println("**** Query: " + query);
		statement = con.prepareStatement(query);	
		statement.setString(1, recID);
		res = getResultSetPS(statement);	 
		strArr = resultSetArray(res, sep);			
		//System.out.println("**** arrSize=" + strArr.size()  );
		//System.out.println("**** arr:" + strArr.toString());	
		//result = jutil.displayResults(res);
		if (strArr.size() > 0) {
			status = true;
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} // end finally try	
		
		return status;
	}
	/*************************************************************************************************************************************************************/	
	public static String baseName(String path) {
		String filename = "";
		//System.out.println("PATH=" + path);
		String[] pathparts = path.split("\\\\");
		filename = pathparts[pathparts.length - 1];
		//System.out.println("FN=" + filename);
		return filename;
	}
	/*************************************************************************************************************************************************************/
	/*************************************************************************************************************************************************************/	
	public static void moveFile(String src, String dest) {
		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
		Path sourcePath = Paths.get(src);
		Path destinationPath = Paths.get(dest);
		//System.out.println("*** SRC: " + src + "SP: " + sourcePath);
		//System.out.println("*** DEST: " + dest + "DP: " + destinationPath);
		try {
			if (Files.copy(sourcePath, destinationPath, options) != null) {
				System.out.println("File moved successfully");
				if (Files.deleteIfExists(sourcePath)) {
					System.out.println("File deleted successfully");
				} else {
					System.out.println("File deletion failed.");
				}
			} else {
				System.out.println("File failed to move");
			}
		} catch (FileAlreadyExistsException e) {
			// file already exists and unable to copy
		} catch (IOException e) {
			// permission issue
			e.printStackTrace();
		}
	}
	/*************************************************************************************************************************************************************/	
	public static ArrayList<String> resultSetArray(ResultSet rs, String sep) throws SQLException {
		ArrayList<String> arrStr = new ArrayList<String>();
		ArrayList<String> xData = new ArrayList<String>();
		String outDataLine = "";
		ResultSetMetaData rsmd = rs.getMetaData();
		int numColumns = rsmd.getColumnCount();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) {
					// System.out.print(";");
					outDataLine += sep;
				}
				String columnValue = rs.getString(i);
				outDataLine += columnValue;
			}
			// System.out.println(outDataLine);
			arrStr.add(outDataLine);
			// System.out.println("");
			outDataLine = "";
		}
		return arrStr;
	}
	/*************************************************************************************************************************************************************/
	public static Connection getConnection(Properties connectionProps) throws SQLException {
		Connection conn = null;

		String myHost = (String) connectionProps.get("myHost");
		String myAccount = (String) connectionProps.get("myAccount");
		String uid = (String) connectionProps.get("userID");
		String pw = (String) connectionProps.get("passWord");
		String dbClass = (String) connectionProps.get("dbClass");
		String dbmsType = (String) connectionProps.get("dbmsType");
		String dbPort = (String) connectionProps.get("port");
		String url =  (String) connectionProps.get("url");
		String passWord = pw.trim();
		String userID = uid.trim();
		//String url = "";	
		// String url = "jdbc:rs-u2://" + myHost + "/" + myAccount + ";" + "dbmstype=" +
		// dbmsType; // generate URL
/*
		System.out.println("dbmsType: " + dbmsType + "--");
		System.out.println("dbClass: " + dbClass + "--");
		System.out.println("myHost: " + myHost + "--");
		System.out.println("userID: " + userID + "--");
		System.out.println("passWord: " + passWord + "--");
*/
		try {
			Class.forName(dbClass);
			conn = DriverManager.getConnection(url, userID, passWord);
			//System.out.println("URL: " + url);
			if (conn == null) {
				System.out.println(" %%% Returned null connection");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/*************************************************************************************************************************************************************/
	public static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}
	/*************************************************************************************************************************************************************/
	public static JSONArray resultSetJSON(ResultSet rs, String tagKey, String tagVal) throws SQLException {
		JSONArray json = new JSONArray();
		JSONObject obj = new JSONObject();
		ResultSetMetaData rsmd = rs.getMetaData();
		obj.put(tagKey, tagVal);
		int numColumns = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i < numColumns + 1; i++) {
				String column_name = rsmd.getColumnName(i);
				switch (rsmd.getColumnType(i)) {
				case java.sql.Types.ARRAY:
					obj.put(column_name, rs.getArray(column_name));
					break;
				case java.sql.Types.BIGINT:
					obj.put(column_name, rs.getInt(column_name));
					break;
				case java.sql.Types.BOOLEAN:
					obj.put(column_name, rs.getBoolean(column_name));
					break;
				case java.sql.Types.BLOB:
					obj.put(column_name, rs.getBlob(column_name));
					break;
				case java.sql.Types.DOUBLE:
					obj.put(column_name, rs.getDouble(column_name));
					break;
				case java.sql.Types.FLOAT:
					obj.put(column_name, rs.getFloat(column_name));
					// obj.put(column_name, rs.getString(column_name));
					break;
				case java.sql.Types.INTEGER:
					obj.put(column_name, rs.getInt(column_name));
					break;
				case java.sql.Types.NVARCHAR:
					obj.put(column_name, rs.getNString(column_name));
					break;
				case java.sql.Types.VARCHAR:
					obj.put(column_name, rs.getString(column_name));
					// System.out.println("VARCHAR:" + rsmd.getColumnType(i) + "VAL=" +
					// obj.toString(column_name, rs.getString(column_name))+ "--" );
					break;
				case java.sql.Types.TINYINT:
					obj.put(column_name, rs.getInt(column_name));
					break;
				case java.sql.Types.SMALLINT:
					obj.put(column_name, rs.getInt(column_name));
					break;
				case java.sql.Types.DATE:
					// obj.put(column_name, rs.getDate(column_name) );
					obj.put(column_name, rs.getString(column_name));
					// System.out.println("DATETYPE:" + rsmd.getColumnType(i) + "VAL=" +
					// obj.toString(column_name, rs.getString(column_name))+ "--" );
					break;
				case java.sql.Types.TIMESTAMP:
					// obj.put(column_name, rs.getTimestamp(column_name));
					obj.put(column_name, rs.getString(column_name));
					break;
				default:
					obj.put(column_name, rs.getObject(column_name));
					break;
				}
			}
			// json.put(obj);
			System.out.println("Adding Object: " + obj.toString() + "---");
		json.add(obj);
		}
		// System.out.println("Returning Array: " + json.toString() + "---");
		return (json);
	}
	/*************************************************************************************************************************************************************/
	public static void displayJsonArray(JSONArray jsonArr) {
		Iterator<Object> iterator = jsonArr.iterator();
		// Set<String> keys = jsonObject.keySet();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj instanceof JSONObject) {
				Set<String> keys = ((JSONObject) obj).keySet();
				// System.out.println("%%%%%%%%% KEYS %%%%%%%%" + keys.toString() + "keyNum=" +
				// keys.size());
				for (String key : keys) {
					// System.out.println(key + ":" + jsonObject.get(key));
					System.out.println("*******Key: " + key + " -> " + ((JSONObject) obj).get(key));
				}
				// System.out.println("*******" + ((JSONObject) obj).get("CUST_ID"));
			}
		}
	}
	/*************************************************************************************************************************************************************/
	public static String displayResults(ResultSet rs) throws SQLException {
		String result = null;
		JSONObject root = new JSONObject();
		int i = 1;
		//System.out.println("Display results");
		root.put("recs", "recs");
		JSONArray jsonArr = new JSONArray();
		while (rs.next()) {
			JSONObject row = new JSONObject();
			row.put("ID", rs.getString("ID"));
			row.put("UATB_SC_BRANCH", rs.getString("UATB_SC_BRANCH"));
			row.put("UATB_SC_CUST_NAME", rs.getString("UATB_SC_CUST_NAME"));
			row.put("INSURANCE_CODE", rs.getString("INSURANCE_CODE"));
			row.put("UATB_SC_ACTIV_DATE", rs.getString("UATB_SC_ACTIV_DATE"));
			row.put("EQUIPMENT_COST", rs.getString("EQUIPMENT_COST"));
			row.put("INSUR_EFFECTIVE_DATE", rs.getString("INSUR_EFFECTIVE_DATE"));
			row.put("INSUR_EXPIRE_DATE", rs.getString("INSUR_EXPIRE_DATE"));
			jsonArr.add(row);
			System.out.println("" + i + ":" + rs.getString("ID") + ":" + rs.getString("UATB_SC_BRANCH") + ":" + ":"
					+ rs.getString("UATB_SC_CUST_NAME") + ":" + rs.getString("INSURANCE_CODE")
					+ rs.getString("UATB_SC_ACTIV_DATE") + ":" + rs.getString("EQUIPMENT_COST") + ":"
					+ rs.getString("INSUR_EFFECTIVE_DATE") + ":" + rs.getString("INSUR_EXPIRE_DATE"));

			// result += "" + rs.getString("ID") + ":" + rs.getString("UATB_SC_BRANCH");
		}
		root.put("jsonArr", jsonArr);
		// System.out.println(root.toJSONString());
		result = root.toJSONString();
		System.out.println(result);
		return result;
	}
	/*************************************************************************************************************************************************************/
	// method to print array
	public static void printStrArray(ArrayList<String> strArr) {
		for (String str : strArr) { // iterating ArrayList
			System.out.println("DATA:" + str + "---");
		}
		// System.out.println(names[index]);
	}
	/*************************************************************************************************************************************************************/
	public static NodeList getXMLNodeList(String fileName, String elemName) {
		ArrayList<String> strData = new ArrayList<String>();
		NodeList nList = null;
		try {
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());
			nList = doc.getElementsByTagName(elemName);
			// System.out.println("----------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nList;
	}
	/*************************************************************************************************************************************************************/
	public static void displayXMLNodeList(NodeList nList) {
		String tagName = null;
		String appID = null;
		String eTag = null;
		String eTagValue = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element : " + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				NodeList nodes = eElement.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node n = nodes.item(j);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						Element eName = (Element) n;
						eTag = eName.getTagName();
						eTagValue = eName.getTextContent();
						// System.out.println("\nCurrent Element TagName : " + tagName + ":" +
						// eName.getTagName() + ":" + eName.getTextContent());
						// System.out.println(eName.geName.getTagName()etTagName() + ":" +
						// eName.getTextContent());
						System.out.println(eTag + ":" + eTagValue);
					}
				}
				// appID = eElement.getElementsByTagName(tagName).item(0).getTextContent();
				// System.out.println("ID:" + appID + "---");
			}
		}
	}
	/*************************************************************************************************************************************************************/
	public static int strToInt( String str ){
	    int i = 0;
	    int num = 0;
	    boolean isNeg = false;
	    //Check for negative sign; if it's there, set the isNeg flag
	    if (str.charAt(0) == '-') {
	        isNeg = true;
	        i = 1;
	    }
	    //Process each character of the string;
	    while( i < str.length()) {
	        num *= 10;
	        num += str.charAt(i++) - '0'; //Minus the ASCII code of '0' to get the value of the charAt(i++).
	    }
	    if (isNeg)
	        num = -num;
	    return num;
	}
	/*************************************************************************************************************************************************************/
	public static void printStrArray(ArrayList<String> strArr, String tag) {
		for (String str : strArr) { // iterating ArrayList
			System.out.println(tag + str);
		}
		// System.out.println(names[index]);
	}
	/*************************************************************************************************************************************************************/
	public static String readInput(InputStream inputStream) {
		String result = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
				result += "\n";
			}
			in.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}
	/*************************************************************************************************************************************************************/
	public static ArrayList<String> readInputFile(String fileName) {
		ArrayList<String> data = new ArrayList<String>();
		File txt = new File(fileName);
		Scanner scan = null;
		try {
			scan = new Scanner(txt);
			while(scan.hasNextLine()){
			    data.add(scan.nextLine());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			scan.close();
		}
		//System.out.println("Data=" + data.toString());
		return data;
	}
	/*************************************************************************************************************************************************************/
	/*************************************************************************************************************************************************************/
	// displayHash -- display Hashtable
	/*************************************************************************************************************************************************************/
	public static void displayHash(Hashtable hashtable) {
		Enumeration names = null;
		String key = null;
		names = hashtable.keys();
		while (names.hasMoreElements()) {
			key = (String) names.nextElement();
			System.out.println("Key:" + key + " -> Value:" + hashtable.get(key));
		}
	}
	/*************************************************************************************************************************************************************/
	public static FileTime getCreationTime(File file) throws IOException {
		Path p = Paths.get(file.getAbsolutePath());
		BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
		FileTime fileTime = view.creationTime();
		// also available view.lastAccessTine and view.lastModifiedTime
		return fileTime;
	}
	/*************************************************************************************************************************************************************/
	public static FileTime getModifyTime(File file) throws IOException {
		Path p = Paths.get(file.getAbsolutePath());
		BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
		FileTime fileTime = view.lastModifiedTime();
		// also available view.lastAccessTine and view.lastModifiedTime
		return fileTime;
	}
	/*************************************************************************************************************************************************************/
	public static FileTime getAccessTime(File file) throws IOException {
		Path p = Paths.get(file.getAbsolutePath());
		BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
		FileTime fileTime = view.lastAccessTime();
		// also available view.lastAccessTine and view.lastModifiedTime
		return fileTime;
	}
	/*************************************************************************************************************************************************************/
	public static String returnLastModifyDate(String filePath) throws IOException {
		String date1 = null;
		try {
			File file = new File(filePath);
			date1 = getFileDate(file);
			return date1;

		} catch (IOException e) {

			return "Date not available.";
		}
	}
	/*************************************************************************************************************************************************************/
	public static String getFileDate(File file) throws IOException {
		Path p = Paths.get(file.getAbsolutePath());
		BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
		FileTime fileTime = view.lastModifiedTime();

		String fileDate = (String) new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(getModifyTime(file).toMillis());
		// also available view.lastAccessTine and view.lastModifiedTime
		return fileDate;
	}
	/*************************************************************************************************************************************************************/
	public static Date getDate() {
	    java.util.Date today = new java.util.Date();
	    return new  Date(today.getTime());
	}
}
