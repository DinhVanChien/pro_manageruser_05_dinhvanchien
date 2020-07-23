/**
 * Copyright(C) 2017 Luvina
 * TblUserLogicImpl.java, 24/10/2017 DinhVanChien
 */
package logic.impl;

import java.sql.SQLException;
import java.util.List;

import common.Common;
import common.Constant;
import dao.TblDetailUserJapanDao;
import dao.TblUserDao;
import dao.impl.TblDetailUserJapanDaoImpl;
import dao.impl.TblUserDaoImpl;
import entity.FileProperties;
import entity.TblDetailUserJapan;
import entity.TblUser;
import entity.UserInfor;
import logic.TblUserLogic;
import properties.ReadProperties;

/**
 * thực thực thi Interface TblUserLogic
 * 
 * @author DinhVanChien
 *
 */
public class TblUserLogicImpl implements TblUserLogic {
	/**
	 * Lấy tất cả bản ghi có id và tên cho trước
	 * 
	 * @return tổng số record
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) {
		// khai báo numberUser để lấy tổng số user
		int numberUser = 0;
		fullName = Common.escapeWildCardSQL(fullName);
		TblUserDao tblUserDao = new TblUserDaoImpl();
			numberUser = tblUserDao.getTotalUsers(groupId, fullName);
		return numberUser;
	}

	/**
	 * method lấy về listUser
	 * 
	 * @return mảng users
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
		fullName = Common.escapeWildCardSQL(fullName);
		List<UserInfor> listUser = null;
		TblUserDao tblUserDao = new TblUserDaoImpl();
			listUser = tblUserDao.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
					sortByCodeLevel, sortByEndDate);
		return listUser;

	}

	/**
	 * hàm update Password
	 * 
	 * @param userId
	 *            id của user
	 * @param pass
	 *            password mới
	 * @return true nếu thành công, false nếu thất bại
	*/
	@Override
	public boolean changePass(int userId, String pass) {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		// mã hóa pass
		pass = (Common.encodeSHA1(pass, Common.getSalt()));
		if (tblUserDao.changePass(userId, pass)) {
			return true;
		}
		return false;
	} 

	/**
	 * phương thức kiểm tra xem một user có tồn tại hay không
	 * 
	 * @param user_id
	 *            của user cần kiểm tra
	 * @return true nếu tồn tại , false không tồn tại
	 */
	@Override
	public boolean checkExitedUser(int userId) {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		if (tblUserDao.getUserdById(userId)) {
			return true;
		}
		return false;
	}

	/**
	 * Phương thức kiểm tra một Tên đăng nhập đã tồn tại trong DB chưa.
	 * 
	 * @param loginName
	 *            Tên đăng nhập cần kiểm tra
	 * 
	 * @return true nếu tên đăng nhập đã tồn tại false nếu chưa tồn tai.
	 */
	@Override
	public boolean checkExistLoginName(Integer id, String loginName) {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		if (loginName.equals(ReadProperties.getValue(Constant.PROPERTIES_ADMIN, FileProperties.getAdmin()))
				|| tblUserDao.checkExistedLoginName(id, loginName)) {
			return true;
		}
		return false;
	}

	/**
	 * Phương thức kiểm tra một email đã tồn tại trong DB chưa.
	 * 
	 * @param loginName
	 *            Tên đăng nhập cần kiểm tra
	 * 
	 * @return true nếu tên đăng nhập đã tồn tại false nếu chưa tồn tai.
	 */
	@Override
	public boolean checkExistEmail(int userId, String email) {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		return tblUserDao.checkExistEmail(userId, email);
	}

	/**
	 * phương thức lấy về một UserInfor
	 * 
	 * @param user_id
	 *            id của UserInfor cần lấy
	 * @return UserInfor
	 */
	@Override
	public UserInfor getUserInforById(int userId) {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		UserInfor userInfor = tblUserDao.getUserInforById(userId);
		return userInfor;
	}

	/**
	 * Phương thức tạo một user để add vào DB bao gồm các thông tin trong cả
	 * tbl_user và tbl_detail_user_japan(nếu cần) Method này cần quản lý transaction
	 * vì là chức năng insert cần thao tác 2 bảng tbl_user và
	 * tbl_detail_user_japan(nếu cần) nên cần đồng nhất dữ liệu , nếu 1, trong hai
	 * bảng không đúng không thực hiện commit
	 * 
	 * @param userInfor
	 *            Đối tượng chứa các thuộc tính là các thông tin của một User
	 * 
	 * 
	 * @return true nếu tạo thành công, false không thành công.
	 * 
	 */
	@Override
	public boolean createUser(UserInfor userInfor) throws SQLException {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblDetailUserJapanDao tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
		TblUser tblUser = new TblUser();
		// gọi method setTblUser(tblUser, userInfor) để sét các giá trị cho TblUser
		setTblUser(tblUser, userInfor);
		tblUser.setSalt(Common.getSalt());
		tblUser.setPassword(Common.encodeSHA1(userInfor.getPassword(), userInfor.getSalt()));
		boolean check = false;
		int generatedKey = 0;
		tblUserDao.getConnection();
		if (tblUserDao.getConnection() != null) {
			try {
				tblUserDao.setAutoCommitFalse();
				generatedKey = tblUserDao.insertUser(tblUser);
				// kiểm tra xem id của bảng user có != 0 hay không
				if (generatedKey != 0) {
					// kiểm tra xem người dùng chọn code level không và codelevel có != null ko
					if (!Constant.DEFAUTL_SELECTION.equals(userInfor.getCodeLevel())
							&& userInfor.getCodeLevel() != null) {
						tblDetailUserJapan.setUsedId(generatedKey);
						// gọi method setTblDetailUserJapan(tblDetailUserJapan, userInfor) để sét các
						// giá trị cho tblDetailUserJapan
						setTblDetailUserJapan(tblDetailUserJapan, userInfor);
						// kiểm tra xem insertDetailUserJapan có đúng không
						if (!(tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan))) {
							check = false;
						} else {
							check = true;
						}
					}
					// nếu người dùng không chọn code level
					// gán check == true để commit update tbl_user
					else {
						check = true;
					}
					// nếu id của bảng user id= 0
				} else {
					check = false;
				}
				// kiểm tra nếu check == true thực hiện commit
				if (check) {
					tblUserDao.isCommit();
				} else {
					tblUserDao.rollBack();
				}
			} catch (Exception e) {
				tblUserDao.rollBack();
			} finally {
				tblUserDao.setAutoCommitTrue();
				tblUserDao.closeConnection(tblUserDao.getConnection());
			}
		}
		return check;
	}

	/**
	 * Method thực hiện chức năng update thông tin của UserInfor vào DB
	 * 
	 * @param userInfor
	 *            Đối tượng chứa các thuộc tính là các thông tin của một User Method
	 *            này cần quản lý transaction vì là chức năng Update cần thao tác 2
	 *            bảng tbl_user và tbl_detail_user_japan(nếu cần) nên cần đồng nhất
	 *            dữ liệu , nếu 1, trong hai bảng không đúng không thực hiện commit
	 * @return true nếu update thành công, false nếu không thành công
	 */
	@Override
	public boolean editUserInfor(UserInfor userInfor) throws SQLException {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		TblUser tblUser = new TblUser();
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblDetailUserJapanDao tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
		// gọi method setTblUser(tblUser, userInfor) để sét các giá trị cho TblUser
		setTblUser(tblUser, userInfor);
		tblDetailUserJapan.setUsedId(userInfor.getUserId());
		// gọi method setTblDetailUserJapan(tblDetailUserJapan, userInfor) để sét các
		// giá trị cho tblDetailUserJapan
		setTblDetailUserJapan(tblDetailUserJapan, userInfor);
		boolean check = false;
		if (tblUserDao.getConnection() != null) {
			try {
				tblUserDao.setAutoCommitFalse();
				if (tblUserDao.editUser(tblUser)) {
					// trường hợp user có trình độ tiếng nhật
					if (tblDetailUserJapanDao.getDetailUserJapan(userInfor.getUserId())) {
						// nếu CodeLevel() có khác giá trị mặc định
						if (!Constant.DEFAUTL_SELECTION.equals(userInfor.getCodeLevel())) {
							// có trình độ tiếng nhật thực hiện Update trình độ tiếng nhật
							if (tblDetailUserJapanDao.editDetailUserJapan(tblDetailUserJapan)) {
								check = true;
							}
							// có trình độ tiếng nhật và Xóa trình độ tiếng nhật
						} else {
							if (tblDetailUserJapanDao.deleteDetailUserJapan(userInfor.getUserId())) {
								check = true;
							}
						}
					}
					// trường hợp user không có có trình độ tiếng nhật
					else {
						// nếu CodeLevel() có khác giá trị mặc định
						if (!Constant.DEFAUTL_SELECTION.equals(userInfor.getCodeLevel())) {
							// Insert trình độ tiếng nhật
							if (tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan)) {
								check = true;
							} else {
								check = false;
							}
						} else {
							check = true;
						}
					}
				}
				// nếu check = true thực hiện commit
				if (check) {
					tblUserDao.isCommit();
					// nếu check = false thực hiện rollback
				} else {
					tblUserDao.rollBack();
				}
			} catch (Exception e) {
				tblUserDao.rollBack();
			} finally {
				tblUserDao.setAutoCommitTrue();
				tblUserDao.closeConnection(tblUserDao.getConnection());
			}
		}
		return check;
	}

	/**
	 * hàm xóa UserInfor
	 * 
	 * @param userId
	 *            id của user Method này cần quản lý transaction vì là chức năng
	 *            delete cần thao tác 2 bảng tbl_user và tbl_detail_user_japan(nếu
	 *            cần) nên cần đồng nhất dữ liệu , nếu 1 trong hai bảng không đúng
	 *            không thực hiện commit
	 * @return true nếu xóa thành công, false nếu không thành công
	 */
	@Override
	public boolean deleteUserInfor(int userId) throws SQLException {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		TblDetailUserJapanDao tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
		// khai báo biến check
		boolean check = false;
		tblUserDao.getConnection();
		if (tblUserDao.getConnection() != null) {
			try {
				tblUserDao.setAutoCommitFalse();
				// kiểm tra xem có trình độ tiếng nhật không
				if (tblDetailUserJapanDao.getDetailUserJapan(userId)) {
					// kiểm tra xem delete bảng tblDetailUserJapanDaoImpl có thành công không
					if (tblDetailUserJapanDao.deleteDetailUserJapan(userId)) {
						// kiểm tra xem delete bảng tblUser có thành công không
						if (tblUserDao.deleteUser(userId)) {
							// gán check = true;
							check = true;
						}
						// nếu delete bảng tblDetailUserJapanDaoImpl không thành công
					} else {
						// gán check = false;
						check = false;
					}
					// trường hợp không có trình độ tiếng nhật
				} else {
					// kiểm tra xem delete bảng tblUser có thành công không
					if (tblUserDao.deleteUser(userId)) {
						// gán check = true;
						check = true;
					}
				}
				// nếu check = true thực hiện commit
				if (check) {
					tblUserDao.isCommit();
					// nếu check = false thực hiện rollback
				} else {
					tblUserDao.rollBack();
				}
			} catch (Exception e) {
				tblUserDao.rollBack();
			} finally {
				tblUserDao.setAutoCommitTrue();
				tblUserDao.closeConnection(tblUserDao.getConnection());
			}
		}
		return check;
	}

	/**
	 * method sét các giá trị cho TblUser
	 * 
	 * @param tblUser
	 *            đối tượng TblUser cần set giá trị
	 * @param userInfor
	 *            đối tượng UserInfor chứa thông tin của để sét TblUser
	 */
	public void setTblUser(TblUser tblUser, UserInfor userInfor) {
		tblUser.setGroupId(userInfor.getGroupId());
		tblUser.setUserId(userInfor.getUserId());
		tblUser.setLoginName(userInfor.getLoginName());
		tblUser.setFullName(userInfor.getFullName());
		tblUser.setFullNameKana(userInfor.getFullNameKana());
		tblUser.setEmail(userInfor.getEmail());
		tblUser.setTel(userInfor.getTel());
		tblUser.setBirthDay(userInfor.getBirthDay());
	}

	/**
	 * method sét các giá trị cho TblDetailUserJapan
	 * 
	 * @param tblUser
	 *            đối tượng TblDetailUserJapan cần set giá trị
	 * @param userInfor
	 *            đối tượng UserInfor chứa thông tin của để sét TblDetailUserJapan
	 */
	public void setTblDetailUserJapan(TblDetailUserJapan tblDetailUserJapan, UserInfor userInfor) {
		tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
		tblDetailUserJapan.setStartDate(userInfor.getStartDate());
		tblDetailUserJapan.setEndDate(userInfor.getEndDate());
		tblDetailUserJapan.setTotal(userInfor.getTotal());
	}
}
