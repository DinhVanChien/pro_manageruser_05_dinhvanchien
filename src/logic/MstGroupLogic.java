/**
 * Copyright(C) 2017 Luvina
 * MstGroupLogic.java, 24/10/2017 DinhVanChien
 */
package logic;

import java.util.List;
import entity.MstGroup;
/**
 * Interface chứa các phương thức xử lý logic với bảng mst_group
 * 
 * @author DinhVanChien
 *
 */
public interface MstGroupLogic {
	/**
	 * Lấy tất cả các nhóm
	 * 
	 * @return mảng các nhóm
	 */
	List<MstGroup> getListGroup();
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
