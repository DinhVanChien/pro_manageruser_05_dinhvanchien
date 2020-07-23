/**
 * Copyright(C) 2017 Luvina
 * MstJapanLogicImpl.java, 1/11/2017 DinhVanChien
 */
package logic.impl;

import java.util.List;

import dao.MstJapanDao;
import dao.impl.MstJapanDaoImpl;
import entity.MstJapan;
import logic.MstJapanLogic;
/**
 * class thực thi interface MstJapanLogic để lấy về list trình độ tiếng nhật
 * @author DinhVanChien
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic{
	/**
	 * Lấy tất cả MstJapan thỏa mãn điều kiện cho trước
	 * @return mảng MstJapans
	 */
	@Override
	public List<MstJapan> getAllMstJapan() {
		MstJapanDao mstJapanDao = new MstJapanDaoImpl();
		List<MstJapan> listMstJapans = mstJapanDao.getAllMstJapan();
		return listMstJapans;
	}

	/**
	 * Phương thức lấy ra nhóm MstJapan trong bảng MstJapan có codeLevel cho trước
	 *  
	 * @param codeLevel  mã trình độ tiếng nhật
	 *           
	 * @return nhóm tiếng nhật null nếu không tìm thấy
	 *         
	 */
	@Override
	public MstJapan getMstJapan(String codeLevel) {
		MstJapanDao mstJapanDao = new MstJapanDaoImpl();
		MstJapan mstJapan = mstJapanDao.getMstJapanByCode(codeLevel);
		return mstJapan;
	}
}
