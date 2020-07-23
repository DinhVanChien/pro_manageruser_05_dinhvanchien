/**
 * Copyright(C) 2017 Luvina
 * ReadProperties.java, 22/10/2017 DinhVanChien
 */
package dao;
/**
 * Interface chứa các method thao tác với bảng mst_group
 * @author DinhVanChien
 *
 */

import java.util.List;

import entity.MstGroup;

public interface MstGroupDao {
	/**
	 * lấy tất cả các nhóm
	 * @return mảng các nhóm
	 */
	List<MstGroup> getListAllMstGroup();
	/**
	 * Lấy ra nhóm có id cho trước
	 * 
	 * @param id
	 *            mã id của nhóm cần tìm
	 * @return nhóm trong bảng mst_group
	 *         null nếu không tìm thấy
	 */
	public MstGroup getGroup(int id);
}
