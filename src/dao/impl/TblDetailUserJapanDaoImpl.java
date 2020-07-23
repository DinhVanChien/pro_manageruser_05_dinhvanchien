/**
 * Copyright(C) 2017 Luvina
 * TblDetailUserJapanDaoImpl.java, 7/11/2017 DinhVanChien
 */
package dao.impl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.TblDetailUserJapanDao;
import entity.TblDetailUserJapan;

/**
 * class thực thi Interface TblDetailUserJapanDao
 * 
 * @author DinhVanChien
 *
 */
public class TblDetailUserJapanDaoImpl extends TblUserDaoImpl implements TblDetailUserJapanDao {

	/**
	 * methoad thực thi phương thức insertDetailUserJapan của interface
	 * TblDetailUserJapanDao
	 */
	@Override
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) {
		String sql = "INSERT INTO tbl_detail_user_japan(user_id, code_level, start_date, end_date, total) VALUES (?,?,?,?,?);";
		int i = 0;
		int temp = 0;
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				ptmt.setInt(++i, tblDetailUserJapan.getUsedId());
				ptmt.setString(++i, tblDetailUserJapan.getCodeLevel());
				ptmt.setString(++i, tblDetailUserJapan.getStartDate());
				ptmt.setString(++i, tblDetailUserJapan.getEndDate());
				ptmt.setString(++i, tblDetailUserJapan.getTotal());
				temp = ptmt.executeUpdate();
			} catch (SQLException e) {
				return false;
			}
			if (temp > 0) {
				return true;
			}
		return false;
	}

	/**
	 * Phương thức kiểm tra xem user có trình độ tiếng nhật không thông qua id của
	 * user
	 * 
	 * @param userId
	 *            là id của user
	 * @param conn
	 *            Connection kết nối csdl
	 * @return true nếu có trình độ tiếng nhật, false nếu không có
	 */
	@Override
	public boolean getDetailUserJapan(int userId) {
		conn = getConnection();
		String sql = "SELECT de.user_id, de.code_level, de.start_date, de.end_date, de.total FROM tbl_detail_user_japan de WHERE de.user_id = ?";
		if (conn != null) {
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				ptmt.setInt(1, userId);
				ResultSet rs = ptmt.executeQuery();
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Phương thức kiểm tra xem TblDetailUserJapan có update vào DB không
	 * 
	 * @param tblDetailUserJapan
	 *            đối tượng chứa thông tin bảng TblDetailUserJapan
	 * @param conn
	 *            Connection kết nối csdl
	 * @return true nếu update thành công, false nếu không
	 */
	@Override
	public boolean editDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) {
		String sql = "UPDATE tbl_detail_user_japan  SET code_level = ?, start_date = ?, end_date = ?, total= ? WHERE user_id = ?;";
		int i = 0;
		int temp = 0;
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				ptmt.setString(++i, tblDetailUserJapan.getCodeLevel());
				ptmt.setString(++i, tblDetailUserJapan.getStartDate());
				ptmt.setString(++i, tblDetailUserJapan.getEndDate());
				ptmt.setString(++i, tblDetailUserJapan.getTotal());
				ptmt.setInt(++i, tblDetailUserJapan.getUsedId());
				temp = ptmt.executeUpdate();
			} catch (SQLException e) {
				return false;
			}
		if (temp > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Phương thức kiểm tra xem TblDetailUserJapan có xóa khỏi DB không
	 * 
	 * @param tblDetailUserJapan
	 * @param conn
	 *            Connection kết nối csdl
	 * @return true nếu xóa thành công, false nếu không
	 */
	@Override
	public boolean deleteDetailUserJapan(int userId) {
		String sql = "DELETE FROM tbl_detail_user_japan WHERE user_id = ?";
		int temp = 0;
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				ptmt.setInt(1, userId);
				temp = ptmt.executeUpdate();
			} catch (SQLException e) {
				return false;
			}
		if (temp > 0) {
			return true;
		}
		return false;
	}

}
