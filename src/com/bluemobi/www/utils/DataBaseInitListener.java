package com.bluemobi.www.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.jdbc.ScriptRunner;

public class DataBaseInitListener {

	private final static Log log = LogFactory.getLog(DataBaseInitListener.class);

	private String isInitDatabase;
	
	public String getIsInitDatabase() {
		return isInitDatabase;
	}

	public void setIsInitDatabase(String isInitDatabase) {
		this.isInitDatabase = isInitDatabase;
	}

	public void initDatabase() {

		if (null != isInitDatabase && "false".equals(isInitDatabase.trim()))
			return;
		Properties dbcongif = new Properties();
		InputStream jdbcInput = null;
		BufferedReader reader = null;
		
		Connection conn = null;
		try {
			jdbcInput = DataBaseInitListener.class.getResourceAsStream("/resources/jdbc.properties");

			dbcongif.load(jdbcInput);

			String jdbcDriver = dbcongif.getProperty("jdbc.driver");
			String jdbcUrl = dbcongif.getProperty("jdbc.url");
			String user = dbcongif.getProperty("jdbc.username");
			String password = dbcongif.getProperty("jdbc.password");
			
			URL sqlScriptPath = DataBaseInitListener.class.getResource("/resources/together.sql");
			String sql_path = null;
			
			if (null != sqlScriptPath && null != sqlScriptPath.getPath() && !"".equals(sqlScriptPath.getPath())) {
				
				if (sqlScriptPath.getPath().indexOf(":") != -1 && sqlScriptPath.getPath().startsWith("/")) {
					sql_path = sqlScriptPath.getPath().substring(1);
				} else {
					sql_path = sqlScriptPath.getPath();
				}
			}
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(sql_path) , "utf-8"));
			Class.forName(jdbcDriver).newInstance();
			conn = (Connection) DriverManager.getConnection(jdbcUrl, user, password);
			
			URL errorLogPath = DataBaseInitListener.class.getResource("/resources/");
			String error_path = null;
			if (null != errorLogPath && null != errorLogPath.getPath() && !"".equals(errorLogPath.getPath())) {
				if (errorLogPath.getPath().indexOf(":") != -1 && errorLogPath.getPath().startsWith("/")) {
					error_path = errorLogPath.getPath().substring(1);
				}
			}
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(error_path+"error.log")));
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setErrorLogWriter(pw);
			runner.setLogWriter(pw);
			
			runner.runScript(reader);
		} catch (InstantiationException e) {
			log.error(e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getLocalizedMessage());
		} catch (ClassNotFoundException e) {
			log.error(e.getLocalizedMessage());
		}
		 catch (SQLException e) {
			 log.error(e.getLocalizedMessage());
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error(e.getLocalizedMessage());
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e.getLocalizedMessage());
				}
			}
		}
	}
}
