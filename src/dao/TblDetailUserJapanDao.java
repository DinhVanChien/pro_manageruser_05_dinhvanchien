/**
 * Copyright(C) 2017 Luvina
 * TblDetailUserJapanDao.java, 7/11/2017 DinhVanChien
 */
package dao;


import entity.TblDetailUserJapan;
/**
 * class thực hiện chức năng với bẳng TblDetailUserJapan
 * @author DinhVanChien
 *
 */
public interface TblDetailUserJapanDao{
	/**
	 * Phương thức insertDetailUserJapan để insert dữ liệu vào bảng tblDetailUserJapan trong DB
	 * @param tblDetailUserJapan Đối tượng chứa thông tin của TblDetailUserJapan
	 * @param conn Connection kết nối csdl
	 * @return true thành công , flase không thành công
	 */
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan);
	/**
	 * Phương thức kiểm tra xem user có trình độ tiếng nhật không thông qua id của user
	 * @param userId  là id của user
	 * @param conn Connection kết nối csdl 
	 * @return true nếu có trình độ tiếng nhật, false nếu không có
	 */
	public boolean getDetailUserJapan(int userId);
	/**
	 * Phương thức kiểm tra xem TblDetailUserJapan có update vào DB không
	 * @param tblDetailUserJapan đối tượng chứa thông tin bảng TblDetailUserJapan
	 * @param conn Connection kết nối csdl
	 * @return true nếu update thành công, false nếu không
	 */
	public boolean editDetailUserJapan(TblDetailUserJapan tblDetailUserJapan);
	/**
	 * Phương thức kiểm tra xem TblDetailUserJapan có xóa khỏi DB không
	 * @param tblDetailUserJapan
	 * @param conn Connection kết nối csdl
	 * @return true nếu xóa thành công, false nếu không
	 */
	public boolean deleteDetailUserJapan(int userId);
}
