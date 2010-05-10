package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseDAO {

	protected Log log = LogFactory.getLog(getClass());
	
	public Connection getConnection()throws ClassNotFoundException, NamingException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost/opencampus", "root", "");
	}
	
	/**
	 * 
	 */
	protected void closeConnection(Connection cons) throws RuntimeException {
		try {
			if (cons != null && !cons.isClosed()) {
				cons.close();
			}
		} catch (SQLException se) {
			log.error("Error: closeConnection: " + se);
		}
	}

	/**
	 * 
	 */
	protected void closeResultSet(ResultSet result)
			throws RuntimeException {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException se) {
			log.error("Error: closeResultSet: " + se);
		}
	}

	/**
	 * 
	 */
	protected void closeStatement(PreparedStatement stmt)
			throws RuntimeException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			log.error("Error: closeStatement: " + se);
		}
	}

	/**
	 * 
	 */
	protected void rollback(Connection cons) throws RuntimeException {
		try {
			if (cons != null && !cons.isClosed()) {
				cons.rollback();
			}
		} catch (SQLException se) {
			log.error("Error: rollBack: " + se);
		}
	}
}
