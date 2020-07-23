/**
 * Copyright(C) 2017 Luvina
 * MstJapanDaoImpl.java, 1/11/2017 DinhVanChien
 */
package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MstJapanDao;
import entity.MstJapan;

/**
 * class thực thi Interface MstJapanDao để lấy về danh sách trình độ tiếng nhât
 * 
 * @author DinhVanChien
 *
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {
	/**
	 * method thực hiện lấy về danh sách trình độ tiếng nhật,sắp xếp tăng theo trình
	 * độ return listJapan trình độ tiếng nhật
	 */
	@Override
	public List<MstJapan> getAllMstJapan() {
		List<MstJapan> liMstJapans = new ArrayList<MstJapan>();
		String sql = "SELECT * FROM mst_japan ORDER BY code_level ASC";
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement ptst = conn.prepareStatement(sql);
				ResultSet rs = ptst.executeQuery();
				while (rs.next()) {
					MstJapan mstJapan = new MstJapan();
					mstJapan.setCodeLevel(rs.getString("code_level"));
					mstJapan.setNameLevel(rs.getString("name_level"));
					liMstJapans.add(mstJapan);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				closeConnection(conn);
			}
		}
		return liMstJapans;
	}

	/**
	 * Phương thức lấy ra nhóm MstJapan trong bảng MstJapan có codeLevel cho trước
	 * 
	 * @param codeLevel
	 *            mã trình độ tiếng nhật
	 * 
	 * @return nhóm tiếng nhật null nếu không tìm thấy
	 * 
	 */
	@Override
	public MstJapan getMstJapanByCode(String codeLevel) {
		conn = getConnection();
		MstJapan mstJapan = new MstJapan();
		String sql = "SELECT code_level,name_level FROM mst_japan where code_level = ?";
		if (conn != null) {
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, codeLevel);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					mstJapan.setCodeLevel(rs.getString("code_level"));
					mstJapan.setNameLevel(rs.getString("name_level"));
				} else {
					mstJapan = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				closeConnection(conn);
			}
		}
		return mstJapan;
	}
}
