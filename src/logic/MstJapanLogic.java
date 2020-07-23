/**
 * Copyright(C) 2017 Luvina
 * MstJapanLogic.java, 1/11/2017 DinhVanChien
 */
package logic;

import java.util.List;

import entity.MstJapan;
/**
 * Interface chứa method tác với DB để lấy danh sách trình độ tiếng nhật
 * @author DinhVanChien
 *
 */
public interface MstJapanLogic {
	/**
	 * Method thao tác với DB để lấy danh sách trình độ tiếng nhật
	 * @return list trình độ tiếng nhật
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
	MstJapan getMstJapan(String codeLevel);
}
