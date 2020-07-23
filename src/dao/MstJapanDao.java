/**
 * Copyright(C) 2017 Luvina
 * MstJapanDao.java, 1/11/2017 DinhVanChien
 */
package dao;

import java.util.List;

import entity.MstJapan;
/**
 * interface thực hiện lấy danh sách trình độ tiếng nhật 
 * @author DinhVanChien
 *
 */
public interface MstJapanDao {
	/**
	 * Phương thức lấy danh sách trình độ tiếng nhật
	 * @return listJapan trình độ tiếng nhật
	 */
	public List<MstJapan> getAllMstJapan();
	/**
	 * Phương thức lấy ra nhóm MstJapan trong bảng MstJapan có codeLevel cho trước
	 *  
	 * @param codeLevel  mã trình độ tiếng nhật
	 *           
	 * @return nhóm tiếng nhật null nếu không tìm thấy
	 *         
	 */
	public MstJapan getMstJapanByCode(String codeLevel);
}
