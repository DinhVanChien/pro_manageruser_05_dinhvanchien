/**
 * Copyright(C) 2017 Luvina
 * BaseDaoImpl.java, 24/10/2017 DinhVanChien
 */
package dao;
import java.util.List;

import entity.TblUser;
import entity.UserInfor;


/**
 * Interface chứa các phương thức thao tác với bảng tbl_user
 * 
 * @author DinhVanChien
 */
public interface TblUserDao extends BaseDao{

	/**
	 * Thực hiện viết câu lệnh SQL join 4 bảng trong DB để lấy danh sách các user có trong hệ thồng
	 * @param offset vị trí lấy bản ghi
	 * @param limit giới hạn số bản ghi trên 1 trang
	 * @param groupId ID của nhóm
	 * @param fullName họ tên đầy đủ
	 * @param sortType kiểu sort(full_name or end_date or code_level)
	 * @param sortByFullName Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @param sortByCodeLevel Giá trị sắp xếp của cột Trình độ tiếng nhật(ASC or DESC)
	 * @param sortByEndDate : Giá trị sắp xếp của cột Ngày(ASC or DESC)
	 * @return trả về mảng danh sách User
	 */
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate);

	/**
	 * Lấy tổng số  user
	 * 
	 * @param groupId ID của nhóm
	 *            
	 * @param fullName họ tên user
	 *            
	 * @return tổng số user
	 */
	public int getTotalUsers(int groupId, String fullName);

	/**
	 * Kiểm tra loginName đã tồn tại trong bảng tbl_user chưa?
	 * Thực hiện viết câu lệnh SQL truy suất bảng tbl_user, 
	 * để lấythông tin của 1 user với điều kiện where là loginName
	 * 
	 * @param loginName Tên đăng nhập cần kiểm tra
	 * 
	 * @return tblUser đối tượng TblUsser
	 */
	public boolean checkExistedLoginName(final Integer userId,final String loginName);
	/**
	 * Ham check email co ton tai khong
	 * @param email : du lieu can ktra
	 * @return true neu co, false nau khong
	 */
	public boolean checkExistEmail(final Integer userId, final String email);
	/**
	 * Ham thực hiện insert thông tin đối tượng User vào DB
	 * @param TblUser tblUser Đối tượng chứa thông tin của user
	 * @return tblUser
	 */
	public int insertUser(TblUser tblUser) ;
	/**
	 * Ham check user co ton tai khong
	 * @param id : id của user cần kiểm tra
	 * @return true neu co tồn tại, false nau khong tồn tại
	 */
	public boolean getUserdById(int id);
	/**
	 * Hàm lấy thông tin của user theo id
	 * @param id: id của user cần lấy thông tin
	 * @return userInfor thông tin của 1 user
	 */
	public UserInfor getUserInforById(int id);
	/**
	 * hàm xóa đi user
	 * @param user cần xóa
	 * @param con kết nối csdl
	 * @return true nếu xóa thành công, false nếu ko thành công
	 */
	public boolean deleteUser(int userId) ;
	/**
	 * hàm update user
	 * @param user cần update
	 * @param con kết nối csdl
	 * @return true nếu update thành công, false nếu ko thành công
	 */
	public boolean editUser(TblUser tblUser);
	/**
	 * hàm update Password
	 * @param userId id của user
	 * @param pass password mới
	 * @return true nếu thành công, false nếu thất bại
	 */
	public boolean changePass(int userId, String pass);
}
