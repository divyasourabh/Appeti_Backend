package com.appeti.database.utils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appeti.database.table.utils.AdminString;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.Property;

public class DBUtils {
	
	private static Properties properties = null;
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String PROPERTY_FILE_PATH = "/properties/server.properties";
	private static final Logger LOG = LoggerFactory.getLogger(DBUtils.class);
	
	private static String URL;
	private static String DEFAULT_DB;
	private static String USERNAME;
	private static String PASSWORD;
	
	private static final String BACKUP_URL = "jdbc:mysql://54.255.225.162:3306/";
	private static final String BACKUP_DB = "wordpress";
	private static final String BACKUP_USERNAME = "nikhil";
	private static final String BACKUP_PASSWORD = "backup";
	
	static{
		//setCredentials();
	}
	public static Connection getConnection() throws SQLException{
		loadProperties();
		
		if(properties != null){
			try{
				Class.forName(DRIVER);
				return DriverManager.getConnection(properties.getProperty(Property.DB_URL) + properties.getProperty(Property.DB_DEFAULT_DB),
						properties.getProperty(Property.DB_USERNAME), properties.getProperty(Property.DB_PASSWORD,""));
				//return DriverManager.getConnection(URL + DEFAULT_DB,
					//	USERNAME, PASSWORD);
			}catch (ClassNotFoundException e) {
				ExceptionUtils.logException(e);
				throw new SQLException(e);
			}
		}else{
			throw new SQLException("Unable to load properties");
		}
	}
	
	public static Connection getConnection(String dataBase) throws SQLException{
		loadProperties();
		if(properties != null){
			try{
				Class.forName(DRIVER);
				return DriverManager.getConnection(properties.getProperty(Property.DB_URL) + dataBase,
						properties.getProperty(Property.DB_USERNAME), properties.getProperty(Property.DB_PASSWORD));
			}catch (ClassNotFoundException e) {
				ExceptionUtils.logException(e);
				throw new SQLException(e);
			}
		}else{
			throw new SQLException("Unable to load properties");
		}
	}
	
	public static Connection getBackupConnection() throws SQLException{
			try{
				Class.forName(DRIVER);
				return DriverManager.getConnection(BACKUP_URL + BACKUP_DB,
						BACKUP_USERNAME, BACKUP_PASSWORD);
			}catch (ClassNotFoundException e) {
				ExceptionUtils.logException(e);
				throw new SQLException(e);
			}
	}
	
	public static void closeConnection(Connection con){
		try{
			if(con != null && !con.isClosed()){
				con.close();
			}
		}catch(Exception e){
			ExceptionUtils.logException(e, "Failed to close connection.");
		}
	}
	
	public static void closeStatement(Statement stmt){
		try{
			if(stmt != null && !stmt.isClosed()){
				stmt.close();
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
	
	public static void closeResultSet(ResultSet rs){
		try{
			if(rs != null && !rs.isClosed()){
				rs.close();
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
	
	public static void closeAll(ResultSet rs, PreparedStatement stmt, Connection con){
		DBUtils.closeResultSet(rs);
		DBUtils.closeStatement(stmt);
		DBUtils.closeConnection(con);
	}
	
	public static int insertQuery(String query) throws SQLException{
		if(query != null && StringUtils.isNotBlank(query)){
			Connection con = null;
			Statement stmt = null;
			try{
				con = getConnection();
				stmt = con.createStatement();
				LOG.info("Executing query : " + query);
				return stmt.executeUpdate(query);
			}finally{
				closeConnection(con);
				closeStatement(stmt);
			}
		}
		return -1;
	}
	
	public static int insertQuery(Connection con, String query) throws SQLException{
		if(query != null && StringUtils.isNotBlank(query)){
			Statement stmt = null;
			try{
				stmt = con.createStatement();
				LOG.info("Executing query : " + query);
				return stmt.executeUpdate(query);
			}finally{
				closeStatement(stmt);
			}
		}
		return -1;
	}
	
	public static int updateQuery(String query) throws SQLException{
		if(query != null && StringUtils.isNotBlank(query)){
			Connection con = null;
			Statement stmt = null;
			try{
				con = getConnection();
				stmt = con.createStatement();
				LOG.info("Executing query : " + query);
				return stmt.executeUpdate(query);
			}finally{
				closeConnection(con);
				closeStatement(stmt);
			}
		}
		return -1;
	}
	
	public static String prepareSelectQuery(String columns,String tableName, String whereClause){
		if(StringUtils.isBlank(columns))
			columns = "*";
		if(StringUtils.isBlank(whereClause))
			whereClause = "";
		else
			whereClause = " WHERE " + whereClause;
		return "SELECT " + columns + " FROM " + tableName + whereClause;
	}
	
	public static String prepareSelectQuery(String columns,String tableName, String whereClause, String otherClause){
		if(StringUtils.isBlank(columns))
			columns = "*";
		if(StringUtils.isBlank(whereClause))
			whereClause = "";
		else
			whereClause = " WHERE " + whereClause;
		return "SELECT " + columns + " FROM " + tableName + whereClause + " " + otherClause;
	}
	
	public static String prepareInsertQuery(String tableName, String columns, String values){
		if(StringUtils.isBlank(columns))
			columns = " ";
		else 
			columns = " (" + columns + ") ";
		if(StringUtils.isBlank(values))
			values = "";
		else
			values = " VALUES (" + values + ")";
		return "INSERT INTO " + tableName + columns + values;
	}
	
	public static String prepareUpdateQuery(String tableName, String setCondition, String whereClause){
		if(StringUtils.isBlank(setCondition))
			setCondition = "";
		if(StringUtils.isBlank(whereClause))
			whereClause = "";
		else
			whereClause = " WHERE " + whereClause;
		return "UPDATE " + tableName + " SET " + setCondition + whereClause;
	}
	
	private static void loadProperties(){
		if(properties == null){
			try {
				properties = new Properties();
				properties.load(new FileInputStream(PROPERTY_FILE_PATH));
			} catch (FileNotFoundException e) {
				ExceptionUtils.logException(e);
			} catch (IOException e) {
				ExceptionUtils.logException(e);
			}
		}
	}
	
	private static void setCredentials(){
		URL = AdminString.get(Property.DB_URL);
		DEFAULT_DB = AdminString.get(Property.DB_DEFAULT_DB);
		USERNAME = AdminString.get(Property.DB_USERNAME);
		PASSWORD = AdminString.get(Property.DB_PASSWORD);
	}
}
