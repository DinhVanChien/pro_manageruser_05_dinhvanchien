/**
 * Copyright(C) 2017 Luvina
 * TblUserLogic.java, 24/10/2017 DinhVanChien
 */
package logic;

import java.sql.SQLException;
import java.util.List;

import entity.UserInfor;


public interface TblUserLogic {
	/**
	 * Lấy tất cả bản ghi có id và tên cho trước
	 * @return tổng số record
	 */
	public int getTotalUsers(int groupId, String fullName);

	/**
	 * method lấy về listUser
	 * @return mảng users
	 */
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate);
	/**
	 * Phương thức tạo một user trong DB bao gồm các thông tin trong cả tbl_user và
	 * tbl_detail_user_japan(nếu cần)
	 * 
	 * @param userInfor  Đối tượng chứa các thuộc tính là các thông tin của một User
	 *           
	 * @return true nếu tạo thành công, false không thành công.
	 *        
	 */
	public boolean createUser(UserInfor userInfor) throws SQLException;
	/**
	 * phương thức kiểm tra xem một user có tồn tại hay không
	 * @param user_id của user cần kiểm tra
	 * @return true nếu tồn tại , false không tồn tại
	 */
	public boolean checkExitedUser(int userId);
	/**
	 * Phương thức kiểm tra một Tên đăng nhập đã tồn tại trong DB chưa.
	 * 
	 * @param loginName
	 *            Tên đăng nhập cần kiểm tra
	 * 
	 * @return true nếu tên đăng nhập đã tồn tại false nếu chưa tồn tai.
	 */
	public boolean checkExistLoginName(Integer id, String loginName);
		
	/**
	 * Phương thức kiểm tra một email đã tồn tại trong DB chưa.
	 * 
	 * @param loginName
	 *            Tên đăng nhập cần kiểm tra
	 * 
	 * @return true nếu tên đăng nhập đã tồn tại false nếu chưa tồn tai.
	 */
	public boolean checkExistEmail(int userId, String email);
	/**
	 * phương thức lấy về một UserInfor
	 * @param user_id id của UserInfor cần lấy
	 * @return UserInfor
	 */
	public UserInfor getUserInforById(int userId);
	/**
	 * Method thực hiện chức năng update thông tin của UserInfor vào DB
	 * @param userInfor Đối tượng chứa các thuộc tính là các thông tin của một User
	 * @return true nếu update thành công, false nếu không thành công
	 */
	public boolean editUserInfor(UserInfor userInfor) throws SQLException;
	/**
	 * hàm update Password
	 * @param userId id của user
	 * @param pass password mới
	 * @return true nếu thành công, false nếu thất bại
	 */
	public boolean changePass(int userId, String pass);
	/**
	 * hàm xóa UserInfor
	 * @param userId id của user
	 * @return true nếu xóa thành công, false nếu không thành công
	 * @throws SQLException
	 */
	public boolean deleteUserInfor(int userId) throws SQLException;
}
