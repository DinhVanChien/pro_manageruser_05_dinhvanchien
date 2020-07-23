/**
 * Copyright(C) 2017 Luvina
 * BaseDao.java, 24/10/2017 DinhVanChien
 */
package dao;


import java.sql.Connection;
/**
 * interface chứa các method thao tác Database
 * @author DinhVanChien
 *
 */
public interface BaseDao {
	/**
	 * Method tạo kết nối csdl
	 * @return kết nối
	 */
	public Connection getConnection();
	/**
	 * Method đóng kết nối csdl
	 */
	public void closeConnection(Connection con);
	/**
	 * Method thực hiện commit
	 */
	public void isCommit();
	/**
	 * Method thực hiện setAutoCommit là false
	 */
	public void setAutoCommitFalse();
	/**
	 * Method thực hiện setAutoCommit là true
	 */
	public void setAutoCommitTrue();
	/**
	 * Method thực hiện rollBack
	 */
	public void rollBack();
}
