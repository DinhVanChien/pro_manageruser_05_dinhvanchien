/**
 * MstGroupLogicImpl.java, 24/10/2017 DinhVanChien
 */
package logic.impl;

import java.util.ArrayList;
import java.util.List;

import dao.MstGroupDao;
import dao.impl.MstGroupDaoImpl;
import entity.MstGroup;
import logic.MstGroupLogic;




/**
 * lớp MstGroupLogicImpl implements MstGroupLogic
 * 
 * @author DinhVanChien
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic{
	/**
	 * Lấy tất cả các nhóm
	 * 
	 * @return mảng các nhóm
	 */
	@Override
	public List<MstGroup> getListGroup() {
		MstGroupDao mstGroup = new MstGroupDaoImpl();
		List<MstGroup> listGroup = new ArrayList<MstGroup>();
		listGroup = mstGroup.getListAllMstGroup();
		return listGroup;
	}
	/**
	 * Lấy ra nhóm có id cho trước
	 * 
	 * @param id
	 *            mã id của nhóm cần tìm
	 * @return nhóm trong bảng mst_group
	 *         null nếu không tìm thấy
	 */
	@Override
	public MstGroup getGroup(int id) {
		MstGroupDao mstGroup = new MstGroupDaoImpl();
		MstGroup group = mstGroup.getGroup(id);
		return group;
	}

}
