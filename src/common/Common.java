/**
 * Copyright(C) 2017 Luvina
 * Constant.java, 22/10/2017 DinhVanChien
 */
package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import entity.FileProperties;
import logic.MstGroupLogic;
import logic.MstJapanLogic;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;
import properties.ReadProperties;

/**
 * lớp chứa các method chung cho project
 * 
 * @author DinhVanChien
 *
 */
public class Common {
	/**
	 * method làm tròn số lên
	 * 
	 * @param numberA
	 *            là số được chia
	 * @param numberB
	 *            là số bị chia
	 * @return kq được làm tròn lên
	 */
	public static int roundUp(int numberA, int numberB) {
		double round = 0.0;
		round = (double) numberA / numberB;
		return (int) Math.ceil(round);
	}

	/**
	 * hàm Tính tổng số trang
	 * 
	 * @param totalUser
	 *            số lượng user
	 * @param limit
	 *            giới hạn bản ghi trong 1 trang
	 * @return số trang
	 */
	public static int getTotalPage(int totalUser, int limit) {
		if (totalUser <= 0) {
			return 0;
		}
		return roundUp(totalUser, limit);
	}

	/**
	 * 
	 * @param currentPage
	 *            trang hiện tại
	 * @param limit
	 *            giới hạn số trang
	 * @return vị trí cần lấy
	 */
	public static int getOffset(int currentPage, int limit) {
		return (currentPage - 1) * limit;
	}

	/**
	 * method Lấy phân vùng hiện tại đang hiển thị
	 * 
	 * @param currentPage
	 *            trang hiện tại
	 * 
	 * @param pageLimit
	 *            giới hạn số trang được hiển thị
	 * 
	 * @return phân vùng hiện tại
	 */
	public static int getCurrentPaging(int currentPage, int pageLimit) {
		return roundUp(currentPage, pageLimit);
	}

	/**
	 * lấy về trang đầu tiên của phân vùng tương ứng với trang đó
	 * 
	 * @param currentPage
	 *            trang hiện tại
	 * 
	 * @param pageLimit
	 *            giới hạn số trang được hiển thị
	 * @return trang đầu của phân vùng đó vd 1,4,7,11
	 */
	public static int getStartPage(int currentPage, int pageLimit) {
		return ((getCurrentPaging(currentPage, pageLimit) - 1) * pageLimit) + 1;
	}

	/**
	 * lấy về trang cuối cùng của phân vùng tương ứng với trang đó
	 * 
	 * @param currentPage
	 *            trang hiện tại
	 * @param pageLimit
	 *            giới hạn số trang được hiển thị
	 * @param totalPage
	 *            tổng số trang
	 * @return getEndPage là trang cuối của phân vùng đó vd 3,6,9..
	 */
	public static int getEndPage(int currentPage, int pageLimit, int totalPage) {
		// khai báo endPage = trang cuối
		int endPage = getCurrentPaging(currentPage, pageLimit) * pageLimit;
		// kiểm tra nếu trang cuối > tổng số trang
		// gán trang cuối = tổng số trang
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		return endPage;
	}

	/**
	 * Danh sách các trang cần hiển thị ở chuỗi paging theo trang hiện tại tạo ra
	 * các trang cần hiển thị ở chuỗi paging theo trang hiện tại
	 * 
	 * @param totalUser
	 *            tổng sô user
	 * @param limit
	 *            số lượng cần hiển thị trên 1 trang
	 * @param currentPage
	 *            trang hiện tại
	 * @return listPaging là các trang cần hiển thị ở chuỗi paging theo trang hiện
	 *         tại
	 */
	public static List<Integer> getListPaging(int totalUser, int limit, int currentPage) {
		List<Integer> listPaging = new ArrayList<Integer>();
		int totalPage = getTotalPage(totalUser, limit);
		if (totalPage == 0 || totalPage == 1) {
			return listPaging;
		}
		int pageLimit = converStringToInt(ReadProperties.getValue(Constant.PAGE_LIMIT, FileProperties.getConfig()));
		int pageStart = getStartPage(currentPage, pageLimit);
		int pageEnd = getEndPage(currentPage, pageLimit, totalPage);
		for (int i = pageStart; i <= pageEnd; i++) {
			listPaging.add(i);
		}
		return listPaging;
	}

	/**
	 * hàm getLimit lấy số lượng recors cần lấy
	 * 
	 * @return số lượng recors
	 */
	public static int getLimit() {
		return converStringToInt(ReadProperties.getValue(Constant.LIMIT, FileProperties.getConfig()));
	}

	/**
	 * Kiểm tra điều kiện login
	 * 
	 * @param session
	 *            phiên làm việc
	 * 
	 * @return true nếu session không bị null hoặc loginName không bị null , trả về
	 *         flase nếu session hoặc loginName == null
	 */
	public static boolean checkLogin(HttpSession session) {
		if (session == null || session.getAttribute(Constant.LOGIN_NAME) == null) {
			return false;
		}
		return true;
	}

	/**
	 * Xử lí chuỗi ký tự nhập vào khi tìm kiếm để tránh lỗi SQL CỦA mệnh đề like
	 * 
	 * @param input
	 *            chuỗi có ký tự đặc biệt cần xử lý
	 * 
	 * @return input chuỗi được xử lý
	 */
	public static String escapeWildCardSQL(String input) {
		if (input != null) {
			input = input.replace("%", "\\%");
			input = input.replace("_", "\\_");
		}
		return input;
	}

	/**
	 * Method thay đổi trạng thái sắp xếp
	 * 
	 * @param sortType
	 *            là trạng thái hiện tại sắp xếp là ASC hay DESC
	 * @return sortType là trạng thái thay đổi
	 */
	public static String setSortType(String sortType) {
		return sortType = sortType.equals(Constant.SORT_ASC) ? Constant.SORT_DESC : Constant.SORT_ASC;
	}

	/**
	 * Method ép kiểu từ String sang kiểu int
	 * 
	 * @param input
	 *            là dữ liệu đầu vào cần ép kiểu
	 * @return number trả về số đc ép sang
	 */
	public static int converStringToInt(String input) {
		int number = 0;
		try {
			number = Integer.parseInt(input);
		} catch (Exception e) {
		}
		return number;
	}

	/**
	 * Phương thức lấy ra năm hiện tại.
	 * 
	 * @return Năm hiện tại.
	 */
	public static int getYearNow() {
		LocalDate now = LocalDate.now();
		return now.getYear();
	}

	/**
	 * Lấy danh sách các năm trong một khoảng từ fromYear đến toYear
	 * 
	 * @param fromYear
	 *            Lấy từ năm nào
	 * @param toYear
	 *            Lấy đến năm nào
	 * @return List<Integer> list các năm
	 */
	public static List<Integer> getListYear(int fromYear, int toYear) {
		List<Integer> listYear = new ArrayList<Integer>();
		for (int i = fromYear; i <= toYear; i++) {
			listYear.add(i);
		}
		return listYear;
	}

	/**
	 * Xử lý để lấy danh sách các tháng: từ 1->12
	 * 
	 * @return List< Integer > list các tháng
	 */
	public static List<Integer> getListMonth() {
		List<Integer> listMonth = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			listMonth.add(i);
		}
		return listMonth;
	}

	/**
	 * Xử lý để lấy danh sách các ngày: từ 1->12
	 * 
	 * @return List< Integer > list các ngày
	 */
	public static List<Integer> getListDay() {
		List<Integer> listDay = new ArrayList<Integer>();
		for (int i = 1; i <= 31; i++) {
			listDay.add(i);
		}
		return listDay;
	}

	/**
	 * Convert các số năm, tháng, ngày thành 1 chuỗi ngày tháng có format yyyy/mm/dd
	 * Xử lý để tạo ra 1 chuỗi ngày tháng từ year, month, day
	 * 
	 * @param year
	 *            năm cần chuyển
	 * @param month
	 *            tháng cần chuyển
	 * @param day
	 *            ngày cần chuyển
	 * @return String chuỗi ngày tháng
	 */
	public static String convertToString(int year, int month, int day) {
		String date = "";
		return date.concat(String.valueOf(year)).concat("/")
				.concat(String.valueOf(month).concat("/").concat(String.valueOf(day)));
	}

	/**
	 * Phương thức lấy ra năm, tháng, ngày hiện tại.
	 * 
	 * @return List<Integer> Năm, Tháng, Ngày hiện tại
	 */
	public static List<Integer> getCurrentYearMonthDay() {
		LocalDate now = LocalDate.now();
		int currentDay = now.getDayOfMonth();
		int currentMonth = now.getMonthValue();
		int currentYear = now.getYear();
		List<Integer> currentDate = new ArrayList<>();
		currentDate.add(currentYear);
		currentDate.add(currentMonth);
		currentDate.add(currentDay);
		return currentDate;
	}

	/**
	 * Phương thức chuyển đổi một ngày dưới dạng mảng String thành một list chứa
	 * thông tin Ngày-Tháng-Năm.
	 * 
	 * @param date
	 *            Một ngày dưới dạng Date.
	 * 
	 * @return Một List<Integer> chứa thông tin Ngày-Tháng-Năm.
	 */
	public static List<Integer> toDateArrayInt(String date) {
		String[] dateArray = date.split("/");
		List<Integer> yearMonthDay = new ArrayList<>();
		yearMonthDay.add(converStringToInt(dateArray[0]));
		yearMonthDay.add(converStringToInt(dateArray[1]));
		yearMonthDay.add(converStringToInt(dateArray[2]));
		return yearMonthDay;
	}

	/**
	 * Phương thức kiểm tra một chuỗi đầu vào có null hoặc rỗng không.
	 * 
	 * @param input
	 *            Chuỗi cần kiểm tra.
	 * 
	 * @return true nếu chuỗi đầu vào null hoặc rỗng false nếu ngược lại lại.
	 */
	public static boolean isNullOrEmpty(String input) {
		return (input == null || input.isEmpty());
	}
	/**
	 * Phương thức kiểm tra sự tồn tại của group id
	 * 
	 * @param groupId
	 *            Group Id cần kiểm tra.
	 * 
	 * @return true nếu Group Id tồn tại false nếu chưa tồn tại
	 */
	public static boolean checkExistGroup(int groupId) {
		MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
		if (mstGroupLogic.getGroup(groupId) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Phương thức kiểm tra sự tồn tại của code level.
	 * 
	 * @param codeLevel
	 *            Code level cần kiểm tra.
	 * @return true nếu code level tồn tại. <br />
	 *         false trong các trường hợp còn lại.
	 */
	public static boolean checkExistsCodeLevel(String codeLevel) {
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		if (mstJapanLogic.getMstJapan(codeLevel) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Phương thức kiểm tra độ dài một chuỗi có độ dài nằm trong khoản min và max
	 * length hay không.
	 * 
	 * @param minLength
	 *            Độ dài chuỗi nhỏ nhất.
	 * @param maxLength
	 *            Độ dài chuỗi lớn nhất.
	 * @param input
	 *            Chuỗi cần kiểm tra.
	 * @return true nếu chuỗi có độ dài nằm trong đoạn minLength và maxLength.<br />
	 *         false nếu không đúng trong khoang.
	 */
	public static boolean checkLength(int minLength, int maxLength, String input) {
		int inputLength = input.length();
		if (inputLength < minLength || inputLength > maxLength) {
			return false;
		}
		return true;
	}

	/**
	 * Phương thức kiểm tra tính đúng định dạng của chuỗi đầu vào theo một trong các
	 * type: Tên đăng nhập: type = loginName Email: type = email Số điện thoại: type
	 * = tel Tổng điểm: type = total Ngày tháng : type = date
	 * 
	 * @param type
	 *            kiểu format kiểm tra.
	 * 
	 * @param input
	 *            dữ liệu cần kiểm tra.
	 * 
	 * @return true nếu type truyền vào tồn tại và chuỗi đầu vào đúng định dạng của
	 *         type đã truyền vào false nếu là không đúng định dạng
	 */
	public static boolean checkFormat(String type, String input) {
		String regex = "";
		switch (type) {
		case "loginName":
			regex = "[a-zA-Z_]+[a-zA-Z0-9_]*";
			break;
		case "email":
			regex = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,5}$";
			break;
		case "tel":
			regex = "[0-9]{1,4}-[0-9]{1,4}-[0-9]{1,4}";
			break;
		case "total":
			regex = "[0-9]{3}";
			break;
		case "date":
			regex = "[0-9]{4}/[0-9]{2}/[0-9]{2}";
			break;
		default:
			return false;
		}
		return input.matches(regex);
	}

	/**
	 * Phương thức kiểm tra tính tồn tại của ngày nhập vào.
	 * 
	 * @param year
	 *            Năm cần kiểm tra.
	 * 
	 * @param month
	 *            Tháng cần kiểm tra.
	 * 
	 * @param day
	 *            Ngày cần kiểm tra
	 * 
	 * 
	 * @return true nếu ngày nhập vào đúng false nếu ko đúng
	 */
	public static boolean checkYearMonthDay(int year, int month, int day) {
		int yearNow = Common.getYearNow();
		if (year < Constant.FROM_YEAR || year > yearNow + 1) {
			return false;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day < 1 || day > 31) {
				return false;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day < 1 || day > 30) {
				return false;
			}
			break;
		case 2:
			if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
				if (day < 1 || day > 29) {
					return false;
				}
			} else {
				if (day < 1 || day > 28) {
					return false;
				}
			}
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * 数値型のデータをチェックする。
	 * 
	 * @param c
	 *            チェックされるオブジェクト
	 * @return チェックが正常終了した場合、Trueを返す。
	 */
	public static boolean isDigit(char c) {
		int x = (int) c;

		if ((x >= 48) && (x <= 57)) {
			return true;
		}

		return false;
	}

	/**
	 * カタカナをチェックする。
	 * 
	 * @param s
	 *            チェックされるオブジェクト
	 * @return チェックが正常終了した場合、Trueを返す。
	 */
	public static boolean isKatakana(String text) {
		char[] c = text.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if ((Character.UnicodeBlock.of(c[i]) != Character.UnicodeBlock.KATAKANA) && (!isDigit(c[i]))
					&& (!Character.isWhitespace(c[i]))) {

				return false;
			}
		}
		return true;
	}

	/**
	 * Kiểm tra chuỗi đầu vào có chứa ký tự 2 byte trở lên hay không.
	 * 
	 * @param input
	 *            Chuỗi cần kiểm tra.
	 * @return true nếu chuỗi đầu vào có ký tự nhiều hơn 1 byte. false nếu <= 1
	 *         byte.
	 * @throws RuntimeException
	 */
	public static boolean checkOneByte(String input) {
		if (input.getBytes().length > input.length()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Phương thức kiểm tra ngày hết hạn có nhỏ hơn ngày cấp hay không.
	 * 
	 * @param startDate
	 *            Ngày cấp.
	 * @param endDate
	 *            Ngày hết hạn.
	 * @return true nếu ngày cấp nhỏ hơn ngày hết hạn. false nếu ngược lại.
	 */
	public static boolean compareDate(String startDate, String endDate) {
		int yearStart = Common.toDateArrayInt(startDate).get(0);
		int monthStart = Common.toDateArrayInt(startDate).get(1);
		int dayStart = Common.toDateArrayInt(startDate).get(2);
		int yearEnd = Common.toDateArrayInt(endDate).get(0);
		int monthEnd = Common.toDateArrayInt(endDate).get(1);
		int dayEnd = Common.toDateArrayInt(endDate).get(2);
		if (yearEnd > yearStart) {
			return true;
		} else if(monthEnd > monthStart){
			return true;
		} else if(dayEnd > dayStart) {
			return true;
		}
		return false;
	}

	/**
	 * Hàm check halfSize
	 * 
	 * @param total Tổng điểm cần check
	 *            
	 * @return true nếu là số halfSize false nếu không phải
	 */
	public static boolean checkHalfSize(String total) {
		String regex = "^\\d+$";
		Pattern pattern = Pattern.compile(regex);
		//phương thức matches yêu cầu toàn bộ dãy total để được so khớp
		Matcher matcher = pattern.matcher(total);
		return matcher.matches();

	}
	/**
	 * ham ma hoa SHA1 cho pass
	 * @param password : pass can ma hoa
	 * @param salt: String them vao de ma hoa
	 * @return pass da ma hoa
	 */
	public static String encodeSHA1(String password, String salt) {
		String sha1 = "";
		try {
			String pass = password + salt;
			//để tạo đối tượng MessageDigest gọi thuật toán mã hóa là SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			//xóa tất cả các cài đặt trước đó.
			md.reset();
			//update lại digest bởi Chuỗi Mã hóa thành một dãy byte 
			md.update(pass.getBytes("UTF-8"));
			sha1 = byteToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	/**
	 * Hàm mã hóa
	 * @param hash can ma hoa
	 * @return kết quả mã hóa
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			// format dạng Hex string.
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	/**
	 * method tạo key session
	 * @return key được tạo 
	 */
	public static String keySession() {
		String key = System.currentTimeMillis() + Constant.EMPTY_STRING;
		return key;
	}
	/**
	 * method tạo giá trị salt
	 * @return salt được tạo 
	 */
	public static String getSalt() {
		String salt = System.currentTimeMillis() + Constant.EMPTY_STRING;
		return salt;
	}
}
