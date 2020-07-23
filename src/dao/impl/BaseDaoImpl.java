/**
 * Copyright(C) 2017 Luvina
 * BaseDaoImpl.java, 24/10/2017 DinhVanChien
 */
package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import common.Constant;
import dao.BaseDao;
import entity.FileProperties;
import properties.ReadProperties;

/**
 * class thực thi các method interface BaseDao
 * @author DinhVanChien
 *
 */
public class BaseDaoImpl implements BaseDao {
	protected Connection conn;
	/**
	 * method tạo kết nối CSDL
	 */
	@Override
	public Connection getConnection() {
		final String url = ReadProperties.getValue(Constant.URL, FileProperties.getDatabase());
		final String user = ReadProperties.getValue(Constant.USER, FileProperties.getDatabase());
		final String password = ReadProperties.getValue(Constant.PASSWORD, FileProperties.getDatabase());
		String driver = ReadProperties.getValue(Constant.DRIVER, FileProperties.getDatabase());
		try {
			Class.forName(driver);
			conn =  DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}
		return conn;
		}
	/**
	 * method đóng kết nối
	 */
	@Override
	public void closeConnection(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Method thực hiện commit
	 */
	@Override
	public void isCommit() {
		if(conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Method thực hiện setAutoCommit là false
	 */
	@Override
	public void setAutoCommitFalse() {
		if(conn != null) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Method thực hiện setAutoCommit là true
	 */
	@Override
	public void setAutoCommitTrue() {
		if(conn != null) {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Method thực hiện rollBack
	 */
	@Override
	public void rollBack() {
		if(conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
