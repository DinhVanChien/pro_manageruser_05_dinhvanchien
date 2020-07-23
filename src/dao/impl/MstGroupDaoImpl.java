/**
 * Copyright(C) 2017 Luvina
 * Constant.java, 22/10/2017 DinhVanChien
 */
package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MstGroupDao;
import entity.MstGroup;

/**
 * lớp MstGroupDaoImpl implements MstGroupDao thực thi method MstGroupDao
 * 
 * @author DinhVanChien
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {
	/**
	 * method thực thi lấy 1 list các MstGroup return listMstGroup
	 */
	@Override
	public List<MstGroup> getListAllMstGroup() {
		conn = getConnection();
		List<MstGroup> listGroup = new ArrayList<MstGroup>();
		String sql = "SELECT group_id,group_name FROM mst_group";
		if (conn != null) {
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				ResultSet rs = ptmt.executeQuery();
				while (rs.next()) {
					MstGroup mstGroup = new MstGroup();
					mstGroup.setGroupId(rs.getInt("group_id"));
					mstGroup.setGroupName(rs.getString("group_name"));
					listGroup.add(mstGroup);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				closeConnection(conn);
			}
		}
		return listGroup;
	}

	/**
	 * Lấy ra nhóm có id cho trước
	 * 
	 * @param id
	 *            mã id của nhóm cần tìm
	 * @return nhóm trong bảng mst_group null nếu không tìm thấy
	 */
	@Override
	public MstGroup getGroup(int id) {
		conn = getConnection();
		String sql = "SELECT group_id,group_name FROM mst_group where group_id = ?";
		MstGroup mstGroup = new MstGroup();
		if(conn != null) {
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setInt(1, id);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					mstGroup.setGroupId(rs.getInt("group_id"));
					mstGroup.setGroupName(rs.getString("group_name"));
				} else {
					mstGroup = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				closeConnection(conn);
			}
		}
		return mstGroup;
	}
}
