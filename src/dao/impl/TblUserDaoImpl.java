/**
 * Copyright(C) 2017 Luvina
 * TblUserDaoImpl.java, 22/10/2017 DinhVanChien
 */
package dao.impl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Common;
import common.Constant;
import dao.TblUserDao;
import entity.TblUser;
import entity.UserInfor;

/**
 * class BaseDaoImpl implements TblUserDao Thao tác với database
 * 
 * @author DinhVanChien
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/**
	 * Thực hiện viết câu lệnh SQL join 4 bảng trong DB để lấy danh sách các user có
	 * trong DB
	 * 
	 * @param offset
	 *            vị trí lấy bản ghi
	 * @param limit
	 *            giới hạn số bản ghi trên 1 trang
	 * @param groupId
	 *            ID của nhóm
	 * @param fullName
	 *            họ tên đầy đủ
	 * @param sortType
	 *            kiểu sort(full_name or end_date or code_level)
	 * @param sortByFullName
	 *            Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @param sortByCodeLevel
	 *            Giá trị sắp xếp của cột Trình độ tiếng nhật(ASC or DESC)
	 * @param sortByEndDate
	 *            : Giá trị sắp xếp của cột Ngày(ASC or DESC)
	 * @return trả về mảng danh sách User
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String typeSort,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
		List<UserInfor> listUser = new ArrayList<UserInfor>();
		int i = 0;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT u.user_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday, g.group_name, j.name_level, de.start_date, de.end_date, de.total ");
		queryBuilder.append("FROM tbl_user u LEFT JOIN( ");
		queryBuilder.append(
				"mst_japan j INNER JOIN tbl_detail_user_japan de ON de.code_level = j.code_level) ON u.user_id = de.user_id ");
		queryBuilder.append("INNER JOIN mst_group g ON u.group_id = g.group_id ");
		queryBuilder.append("WHERE ");
		if (groupId != 0) {
			queryBuilder.append("u.group_id = ? AND ");
		}
		if (fullName != null || fullName.length() != 0) {
			queryBuilder.append("u.full_name LIKE ? ");
		}

		if (Constant.FULL_NAME.equals(typeSort)) {
			queryBuilder.append("ORDER BY ").append("u.full_name ").append(sortByFullName)
					.append(", j.name_level ASC, de.end_date DESC ");
		} else if (Constant.CODE_LEVEL.equals(typeSort)) {
			queryBuilder.append("ORDER BY ").append("j.name_level ").append(sortByCodeLevel)
					.append(", u.full_name ASC, de.end_date DESC ");
		} else if (Constant.END_DATE.equals(typeSort)) {
			queryBuilder.append("ORDER BY ").append("de.end_date ").append(sortByEndDate)
					.append(", u.full_name ASC, j.name_level ASC ");
		} else if (Constant.EMPTY_STRING.equals(typeSort)) {
			queryBuilder.append("ORDER BY ").append("u.full_name ASC, j.name_level ASC, de.end_date DESC ");
		}
		queryBuilder.append("LIMIT ? ");
		queryBuilder.append("OFFSET ? ");
		String sql = queryBuilder.toString();
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				if (groupId != 0) {
					ptmt.setInt(++i, groupId);
				}
				ptmt.setString(++i, "%" + fullName + "%");
				ptmt.setInt(++i, limit);
				ptmt.setInt(++i, offset);
				ResultSet rs = ptmt.executeQuery();
				while (rs.next()) {
					UserInfor tblUserInfo = new UserInfor();
					tblUserInfo.setUserId(rs.getInt("user_id"));
					tblUserInfo.setFullName(rs.getString("full_name"));
					tblUserInfo.setEmail(rs.getString("email"));
					tblUserInfo.setTel(rs.getString("tel"));
					tblUserInfo.setBirthDay(rs.getString("birthday"));
					tblUserInfo.setGroupName(rs.getString("group_name"));
					tblUserInfo.setNameLevel(rs.getString("name_level"));
					tblUserInfo.setEndDate(rs.getString("end_date"));
					tblUserInfo.setTotal(rs.getString("total"));
					listUser.add(tblUserInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				closeConnection(conn);
			}
		}
		return listUser;
	}

	/**
	 * Kiểm tra loginName đã tồn tại trong bảng tbl_user chưa? Thực hiện viết câu
	 * lệnh SQL truy suất bảng tbl_user,
	 * 
	 * @param loginName
	 *            Tên đăng nhập cần kiểm tra
	 * 
	 * @return true nếu đã tồn tại, false nếu không
	 * 
	 */
	@Override
	public boolean checkExistedLoginName(final Integer userId, final String loginName) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT u.login_name FROM tbl_user u WHERE u.login_name = ? AND u.user_id != ?");
		String sql = sqlBuilder.toString();
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, loginName);
				pstm.setInt(2, userId);
				ResultSet rs = pstm.executeQuery();
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				return false;
			} finally {
				closeConnection(conn);
			}
		}
		return false;
	}

	/**
	 * Ham check email co ton tai khong
	 * 
	 * @param email
	 *            : du lieu can ktra
	 * @return true neu co, false nau khong
	 */
	@Override
	public boolean checkExistEmail(final Integer userId, final String email) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT u.email FROM tbl_user u WHERE u.email = ? AND u.user_id != ?");
		String sql = sqlBuilder.toString();
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, email);
				pstm.setInt(2, userId);
				ResultSet rs = pstm.executeQuery();
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				return false;
			} finally {
				closeConnection(conn);
			}
		}
		return false;
	}

	/**
	 * Lấy tổng số user
	 * 
	 * @param groupId
	 *            ID của nhóm
	 * 
	 * @param fullName
	 *            họ tên user
	 * 
	 * @return tổng số user
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) {
		int total = 0, i = 0;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT COUNT(user_id) FROM tbl_user u INNER JOIN mst_group g ON u.group_id = g.group_id ");
		queryBuilder.append("WHERE ");
		if (groupId != 0) {
			queryBuilder.append("u.group_id = ? AND ");
		}
		if (fullName != null || fullName.length() != 0) {
			queryBuilder.append("u.full_name LIKE ? ");
		}
		String sql = queryBuilder.toString();
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				if (groupId != 0) {
					ptmt.setInt(++i, groupId);
				}
				ptmt.setString(++i, "%" + fullName + "%");
				ResultSet rs = ptmt.executeQuery();
				while (rs.next()) {
					total = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			} finally {
				closeConnection(conn);
			}
		}
		return total;
	}

	/**
	 * Ham thực hiện insert thông tin đối tượng User vào DB
	 * 
	 * @param tblUser
	 *            : đối tượng User
	 * @return tblUser
	 */
	@Override
	public int insertUser(TblUser tblUser) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("INSERT INTO tbl_user ");
		queryBuilder
				.append("( group_id, login_name, password, full_name, full_name_kana, email, tel, birthday, salt) ");
		queryBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		String sql = queryBuilder.toString();
		int i = 0;
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ptmt.setInt(++i, tblUser.getGroupId());
				ptmt.setString(++i, tblUser.getLoginName());
				ptmt.setString(++i, tblUser.getPassword());
				ptmt.setString(++i, tblUser.getFullName());
				ptmt.setString(++i, tblUser.getFullNameKana());
				ptmt.setString(++i, tblUser.getEmail());
				ptmt.setString(++i, tblUser.getTel());
				ptmt.setString(++i, tblUser.getBirthDay());
				ptmt.setString(++i, tblUser.getSalt());
				ptmt.executeUpdate();
				ResultSet rs = ptmt.getGeneratedKeys();
				int generatedKey = 0;
				if (rs.next()) {
					generatedKey = rs.getInt(1);
				}
				return generatedKey;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**
	 * Ham check user co ton tai khong
	 * 
	 * @param id
	 *            : id của user cần kiểm tra
	 * @return true neu co tồn tại, false nau khong tồn tại
	 */
	@Override
	public boolean getUserdById(int id) {
		String sql = "SELECT u.user_id FROM tbl_user u WHERE u.user_id = ?";
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setInt(1, id);
				ResultSet rs = pstm.executeQuery();
				if (rs.next()) {
					return true;
				}

			} catch (SQLException e) {
				return false;
			} finally {
				closeConnection(conn);
			}
		}
		return false;
	}

	/**
	 * Hàm lấy thông tin của user theo id
	 * 
	 * @param id:
	 *            id của user cần lấy thông tin
	 * @return userInfor thông tin của 1 user
	 */
	@Override
	public UserInfor getUserInforById(int user_id) {
		UserInfor userInfo = new UserInfor();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT u.user_id, u.login_name,u.password, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday, u.salt,u.rule, u.password , g.group_id, g.group_name,j.code_level, j.name_level,de.start_date, de.end_date,de.total ");
		queryBuilder.append("FROM tbl_user u LEFT JOIN(");
		queryBuilder.append(
				" mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ON u.user_id = de.user_id ");
		queryBuilder.append("INNER JOIN mst_group g ON u.group_id = g.group_id ");
		queryBuilder.append(" WHERE u.user_id = ?");
		String sql = queryBuilder.toString();
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				if (user_id != 0) {
					ptmt.setInt(1, user_id);
				}
				ResultSet rs = ptmt.executeQuery();
				if (rs.next()) {
					userInfo.setUserId(rs.getInt("user_id"));
					userInfo.setLoginName(rs.getString("login_name"));
					userInfo.setFullName(rs.getString("full_name"));
					userInfo.setPassword(rs.getString("password"));
					userInfo.setEncodePassword(rs.getString("password"));
					userInfo.setFullNameKana(rs.getString("full_name_kana"));
					userInfo.setEmail(rs.getString("email"));
					userInfo.setTel(rs.getString("tel"));
					userInfo.setBirthDay(rs.getString("birthday"));
					userInfo.setGroupName(rs.getString("group_name"));
					userInfo.setGroupId(rs.getInt("group_id"));
					userInfo.setCodeLevel(rs.getString("code_level"));
					userInfo.setNameLevel(rs.getString("name_level"));
					userInfo.setStartDate(rs.getString("start_date"));
					userInfo.setEndDate(rs.getString("end_date"));
					userInfo.setRule(rs.getInt("rule"));
					userInfo.setTotal(rs.getString("total"));
				} else {
					userInfo = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				closeConnection(conn);
			}
		}
		return userInfo;
	}

	/**
	 * hàm xóa đi user
	 * 
	 * @param user
	 *            cần xóa
	 * @param con
	 *            kết nối csdl
	 * @return true nếu xóa thành công, false nếu ko thành công
	 */
	@Override
	public boolean deleteUser(int userid) {
		String sql = "DELETE FROM tbl_user WHERE user_id = ?";
		int temp = 0;
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				ptmt.setInt(1, userid);
				temp = ptmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		if (temp > 0) {
			return true;
		}
		return false;
	}

	/**
	 * hàm update user
	 * 
	 * @param user
	 *            cần update
	 * @param con
	 *            kết nối csdl
	 * @return true nếu update thành công, false nếu ko thành công
	 */
	@Override
	public boolean editUser(TblUser tblUser) {
		String sql = "UPDATE tbl_user SET group_id=?, full_name=?, full_name_kana=?, email=?, tel=?,birthday=? WHERE user_id=?";
		int i = 0, temp = 0;
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				ptmt.setInt(++i, tblUser.getGroupId());
				ptmt.setString(++i, tblUser.getFullName());
				ptmt.setString(++i, tblUser.getFullNameKana());
				ptmt.setString(++i, tblUser.getEmail());
				ptmt.setString(++i, tblUser.getTel());
				ptmt.setString(++i, tblUser.getBirthDay());
				ptmt.setInt(++i, tblUser.getUserId());
				temp = ptmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			if (temp > 0) {
				return true;
			}
		return false;
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
		String sql = "UPDATE tbl_user SET password = ?, salt = ? WHERE user_id = ?";
		int temp = 0;
		int i = 0;
		conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(++i, pass);
				pstm.setString(++i, Common.getSalt());
				pstm.setInt(++i, userId);
				temp = pstm.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				closeConnection(conn);
			}
			if (temp > 0) {
				return true;
			}
		}
		return false;
	}
}