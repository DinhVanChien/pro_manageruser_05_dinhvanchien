/**
 * Copyright(C) 2017 Luvina
 * ValidateUser.java, 5/11/2017 DinhVanChien
 */

package validate;

import java.util.ArrayList;
import java.util.List;

import common.Common;
import common.Constant;
import entity.FileProperties;
import entity.UserInfor;
import logic.TblUserLogic;
import logic.impl.TblUserLogicImpl;
import properties.ReadProperties;

/**
 * class Thực hiện check dữ liệu nhập vào thông tin User
 * 
 * @author DinhVanChien
 *
 */
public class ValidateUser {
	/**
	 * Thực hiện validate thông tin user nhập từ màn hình ADM003 UserInfor userInfor
	 * Đối tượng user cần check Thực hiện các logic check mà requirement yêu cầu.
	 * Nếu có lỗi thì set lỗi vào mảng lỗi listError.
	 * 
	 * @return List<String> lstError Danh sách lỗi
	 */
	
	public List<String> validateUserInfor(UserInfor userInfor) {
		List<String> listError = new ArrayList<String>();
		String error = null;
		int userId = userInfor.getUserId();
		String loginName = userInfor.getLoginName();
		int groupId = userInfor.getGroupId();
		String fullName = userInfor.getFullName();
		String fullNameKana = userInfor.getFullNameKana();
		String birthDay = userInfor.getBirthDay();
		String email = userInfor.getEmail();
		String tel = userInfor.getTel();
		String pass = userInfor.getPassword();
		String passConfirm = userInfor.getEncodePassword();
		String codeLevel = userInfor.getCodeLevel();
		String startDate = userInfor.getStartDate();
		String endDate = userInfor.getEndDate();
		String total = userInfor.getTotal();
		if (userId == 0) {
			error = checkLoginName(userId, loginName);
			checkErrorValidate(listError, error);
		}
		error = checkGroup(groupId);
		checkErrorValidate(listError, error);
		error = checkFullName(fullName);
		checkErrorValidate(listError, error);
		error = checkFullNameKana(fullNameKana);
		checkErrorValidate(listError, error);
		error = checkBirthDay(birthDay);
		checkErrorValidate(listError, error);
		error = checkEmail(userId, email);
		checkErrorValidate(listError, error);
		error = checkTel(tel);
		checkErrorValidate(listError, error);
		if (userId == 0) {
			error = checkPassword(pass);
			checkErrorValidate(listError, error);
			error = checkPassConfirm(pass, passConfirm);
			checkErrorValidate(listError, error);
		}
		if (checkSelectBoxCodeLevel(codeLevel)) {
			error = checkExistJapanCodeLevel(codeLevel);
			checkErrorValidate(listError, error);
			error = checkStartDate(startDate);
			checkErrorValidate(listError, error);
			error = checkEndDate(startDate, endDate);
			checkErrorValidate(listError, error);
			error = checkTotal(total);
			checkErrorValidate(listError, error);
		}
		return listError;
	}
/**
 * Method kiểm tra xem có lỗi error không nếu có add vào listError
 * @param listError mảng lưu error
 * @param error lỗi gặp phải
 * @return mảng lỗi listError
 */
	public static List<String> checkErrorValidate(List<String> listError, String error) {
		if (error != null) {
			listError.add(error);
		}
		return listError;
	}

	/**
	 * Method thực hiện validete pass và passConfirm của chức năng change pass
	 * 
	 * @param pass
	 *            password cần thay đồi
	 * @param passConfirm
	 *            nhập lại pass
	 * @return listError
	 */
	public List<String> validatePass(String pass, String passConfirm) {
		List<String> listError = new ArrayList<String>();
		String error = null;
		error = checkPassword(pass);
		if (error != null) {
			listError.add(error);
		}
		if (!pass.equals(passConfirm)) {
			error = ReadProperties.getValue(Constant.ER017_PASS_CONFIRM,FileProperties.getMessageError());
			listError.add(error);
		}
		return listError;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với tên tài khoản.
	 * 
	 * @param loginName
	 *            Tên tài khoản cần kiểm tra.
	 * @return Một list các lỗi. (Rỗng nếu không có lỗi)
	 */
	private String checkLoginName(Integer id, String loginName) {
		String error = null;
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		if (Common.isNullOrEmpty(loginName)) {
			error = ReadProperties.getValue(Constant.ER001_LOGIN_NAME, FileProperties.getMessageError());
		} else if (!Common.checkFormat("loginName", loginName)) {
			error = ReadProperties.getValue(Constant.ER019_USERNAME,FileProperties.getMessageError());
		} else if (!Common.checkLength(Constant.MIN_LENGTH_LOGIN_NAME, Constant.MAX_LENGTH_LOGIN_NAME, loginName)) {
			error = ReadProperties.getValue(Constant.ER007_USERNAME,FileProperties.getMessageError());
		} else if (tblUserLogic.checkExistLoginName(id, loginName)) {
			error = ReadProperties.getValue(Constant.ER003_USERNAME,FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với group.
	 * 
	 * @param groupId
	 *            Group ID cần kiểm tra.
	 * 
	 * @return một trong số các lỗi có thể gặp phải. Null nếu không có lỗi.
	 */
	private String checkGroup(int groupId) {
		String error = null;
		if (groupId == 0) {
			error = ReadProperties.getValue(Constant.ER002_GROUP, FileProperties.getMessageError());
		} else if (!Common.checkExistGroup(groupId)) {
			error = ReadProperties.getValue(Constant.ER004_GROUP, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với fullName
	 * 
	 * @param fullName
	 *            FullName cần kiểm tra
	 * @return một trong số các lỗi có thể gặp phải. Null nếu không có lỗi.
	 */
	private String checkFullName(String fullName) {
		String error = null;
		if (Common.isNullOrEmpty(fullName)) {
			error = ReadProperties.getValue(Constant.ER001_FULLNAME,FileProperties.getMessageError());
		} else if (!Common.checkLength(Constant.MIN_LENGTH, Constant.MAX_LENGTH, fullName)) {
			error = ReadProperties.getValue(Constant.ER006_FULLNAME, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với fullNameKana
	 * 
	 * @param fullNameKana
	 *            tên kana
	 * @return lỗi có thể gặp phải Null nếu ko có lỗi
	 */
	private String checkFullNameKana(String fullNameKana) {
		String error = null;
		if (!Common.isNullOrEmpty(fullNameKana)) {
			if (!Common.checkLength(Constant.MIN_LENGTH, Constant.MAX_LENGTH, fullNameKana)) {
				error = ReadProperties.getValue(Constant.ER006_FULLNAME_KANA, FileProperties.getMessageError());
			} else if (!Common.isKatakana(fullNameKana)) {
				error = ReadProperties.getValue(Constant.ER008_FULLNAME_KANA, FileProperties.getMessageError());
			}
		}

		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với ngày sinh.
	 * 
	 * @param birthday
	 *            Ngày sinh cần kiểm tra.
	 * 
	 * @return error nếu có lỗi Null nếu không có lỗi.
	 */
	private String checkBirthDay(String birthDay) {
		String error = null;
		int year = Common.toDateArrayInt(birthDay).get(0);
		int month = Common.toDateArrayInt(birthDay).get(1);
		int day = Common.toDateArrayInt(birthDay).get(2);
		if (!Common.checkYearMonthDay(year, month, day)) {
			error = ReadProperties.getValue(Constant.ER011_BRITHDAY, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với email.
	 * 
	 * @param email
	 *            email cần kiểm tra.
	 * 
	 * @return error nếu có lỗi Null nếu không có lỗi.
	 */
	private String checkEmail(int userId, String email) {
		String error = null;
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		if (Common.isNullOrEmpty(email)) {
			error = ReadProperties.getValue(Constant.ER001_EMAIL, FileProperties.getMessageError());
		} else if (tblUserLogic.checkExistEmail(userId, email)) {
			error = ReadProperties.getValue(Constant.ER003_EMAIL, FileProperties.getMessageError());
		} else if (!Common.checkFormat("email", email)) {
			error = ReadProperties.getValue(Constant.ER005_EMAIL, FileProperties.getMessageError());
		} else if (!Common.checkLength(Constant.MIN_LENGTH, Constant.MAX_LENGTH, email)) {
			error = ReadProperties.getValue(Constant.ER006_EMAIL, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với SDT.
	 * 
	 * @param tle
	 *            email cần kiểm tra.
	 * 
	 * @return error nếu có lỗi Null nếu không có lỗi.
	 */
	private String checkTel(String tel) {
		String error = null;
		if (Common.isNullOrEmpty(tel)) {
			error = ReadProperties.getValue(Constant.ER001_TEL, FileProperties.getMessageError());
		} else if (!Common.checkFormat("tel", tel)) {
			error = ReadProperties.getValue(Constant.ER005_TEL, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với SDT.
	 * 
	 * @param tle
	 *            email cần kiểm tra.
	 * 
	 * @return error nếu có lỗi Null nếu không có lỗi.
	 */
	private String checkPassword(String pass) {
		String error = null;
		if (Common.isNullOrEmpty(pass)) {
			error = ReadProperties.getValue(Constant.ER001_PASS, FileProperties.getMessageError());
		} else if (!Common.checkOneByte(pass)) {
			error = ReadProperties.getValue(Constant.ER008_PASS, FileProperties.getMessageError());
		} else if (!Common.checkLength(5, 15, pass)) {
			error = ReadProperties.getValue(Constant.ER007_PASS, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với mật khẩu xác nhận
	 * 
	 * @param password
	 *            password đã nhập.
	 * 
	 * @param passwordConfirm
	 *            Password Confirm cần kiểm tra .
	 * @return lỗi nếu cố Null nếu không có lỗi.
	 */
	private String checkPassConfirm(String pass, String passConfirm) {
		String error = null;
		if (passConfirm != null) {
			if (!pass.equals(passConfirm)) {
				error = ReadProperties.getValue(Constant.ER017_PASS_CONFIRM, FileProperties.getMessageError());
			}
		}

		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với trình độ tiếng nhật
	 * 
	 * @param codeLevel
	 *            codeLevel kiểm tra. .
	 * @return lỗi nếu cố Null nếu không có lỗi.
	 */
	private String checkExistJapanCodeLevel(String codeLevel) {
		String error = null;
		if (!Common.checkExistsCodeLevel(codeLevel)) {
			error = ReadProperties.getValue(Constant.ER004_NAME_LEVEL, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Kiểm tra xem người dùng có chọn vào codelevel không
	 * 
	 * @param codeLevel
	 * @return return false là không chọn, true có chọn
	 */
	private boolean checkSelectBoxCodeLevel(String codeLevel) {
		if (Constant.DEFAUTL_SELECTION.equals(codeLevel)) {
			return false;
		}
		return true;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với trình ngày cấp trình độ japan
	 * 
	 * @param date
	 *            date kiểm tra. .
	 * @return lỗi nếu cố Null nếu không có lỗi.
	 */
	private String checkStartDate(String date) {
		String error = null;
		int year = Common.toDateArrayInt(date).get(0);
		int month = Common.toDateArrayInt(date).get(1);
		int day = Common.toDateArrayInt(date).get(2);
		if (!Common.checkYearMonthDay(year, month, day)) {
			error = ReadProperties.getValue(Constant.ER011_START_DATE, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với ngày hết hạn japan
	 * 
	 * @param date
	 *            date kiểm tra. .
	 * @return lỗi nếu cố Null nếu không có lỗi.
	 */
	private String checkEndDate(String startDate, String endDate) {
		String error = null;
		int year = Common.toDateArrayInt(endDate).get(0);
		int month = Common.toDateArrayInt(endDate).get(1);
		int day = Common.toDateArrayInt(endDate).get(2);
		if (!Common.checkYearMonthDay(year, month, day)) {
			error = ReadProperties.getValue(Constant.ER011_END_DATE, FileProperties.getMessageError());
		} else if (!Common.compareDate(startDate, endDate)) {
			error = ReadProperties.getValue(Constant.ER012_END_DATA, FileProperties.getMessageError());
		}
		return error;
	}

	/**
	 * Phương thức kiểm tra các lỗi có thể xảy ra với tổng điểm.
	 * 
	 * @param total
	 *            Tổng điểm cần check
	 * @return Một lỗi đầu tiên gặp phải.<br />
	 *         Null nếu không có lỗi.
	 */
	private String checkTotal(String total) {
		String error = null;
		if (Common.isNullOrEmpty(total)) {
			error = ReadProperties.getValue(Constant.ER001_TOTAL, FileProperties.getMessageError());

		} else if (!Common.checkHalfSize(total)) {
			error = ReadProperties.getValue(Constant.ER018_TOTAL, FileProperties.getMessageError());
		}
		return error;
	}
	
}
