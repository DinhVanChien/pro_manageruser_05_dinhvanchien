/**
 * Copyright(C) 2017 Luvina
 * AddUserInputController.java, 1/11/2017 DinhVanChien
 */
package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Common;
import common.Constant;
import entity.MstGroup;
import entity.MstJapan;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;
import logic.impl.TblUserLogicImpl;
import validate.ValidateUser;
/**
 * lớp AddUserInputController để xử lý cho các chức năng ADD và EDIT
 * userInfor, Edit 
 * @author DinhVanChien
 */
@WebServlet(
		name = Constant.ADD_USER_INPUT_CONTROLLER,
		urlPatterns = { 
				Constant.ADD_USER_VALIDATE_ANNOTATION, 
				Constant.ADD_USER_INPUT_ANNOTATION,
				Constant.EDIT_USER_ANNOTATION
				})
public class AddUserInputController extends HttpServlet {
	/**
	 * Để đảm bảo  servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = -627032279005990387L;

	
	
	/**
	 * doPost() bắt validate cho các hạng mục ADM003 của chức năng Add, edit nếu có lỗi thông báo lỗi màn hình ADM003 nếu
	 * không có lỗi validate lưu userInfor lên session và sendRedirect ADM004
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// khai báo class tblUserLogicImpl để gọi method tblUserLogicImpl()
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			// khai báo userId để lấy userId từ request
			int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
			HttpSession session = request.getSession();
			List<String> listError = new ArrayList<>();
			UserInfor userInfor = new UserInfor();
			ValidateUser validateUser = new ValidateUser();
			// nếu userId > 0 và nếu không tồn tại thông báo Ko tồn tại bản ghi
			if (userId > 0 && !tblUserLogicImpl.checkExitedUser(userId)) {
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION + "?"
						+ Constant.TYPE + "=" + Constant.DOES_NOT_EXIT_USER);
			}
			userInfor = setDefaultValue(request, response);
			listError = validateUser.validateUserInfor(userInfor);
			// nếu có lỗi Validate
			if (listError.size() > 0) {
				request.setAttribute(Constant.LIST_ERROR, listError);
				// setDataLogicADM003 để set giá trị selecbox
				setDataLogicADM003(request, response);
				// setAttribute userInfor để giữ lại các giá trị nhập vào cho ADM003
				request.setAttribute("userInfor", userInfor);
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM003);
				rd.forward(request, response);
				// nếu không có lỗi Valiadate
			} else {
				String type = request.getParameter(Constant.TYPE);
				String key = Common.keySession();
				session.setAttribute(key, userInfor);
				// Trường hợp thêm mới user
				if (Constant.ADD_USER.equals(type)) {
					response.sendRedirect(request.getContextPath() + Constant.ADD_USER_CONFIM_ANNOTATION + "?"
							+ Constant.KEY + "=" + key);
				}
				// Trường hợp edit user
				else if (Constant.EDIT_USER.equals(type)) {
					response.sendRedirect(request.getContextPath() + Constant.EDIT_USER_CONFIM_ANNOTATION + "?"
							+ Constant.KEY + "=" + key);
				}
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION + "?" + Constant.TYPE
					+ "=" + Constant.ERROR);
		}
	}

	/**
	 * Xử lý khi khi vào màn hình ADM003 lần đầu, và chức năng edit khi click button
	 * ADM005 -> ADM003
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// khai báo type1 đề lấy về type từ request
			String type1 = request.getParameter(Constant.TYPE);
			// khai báo type2 để gán mã lỗi có thể sảy ra
			String type2 = Constant.EMPTY_STRING;
			// biến check kiểm tra đúng sai
			boolean check = false;
			UserInfor userInfor = new UserInfor();
			// nếu type = edit
				if (Constant.EDIT_USER.equals(type1)) {
					// khai báo userId để lấy về userId từ request
					int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
					// khai báo class tblUserLogicImpl để gọi method checkExitedUser()
					TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
					// kiểm tra với Id từ request gửi sang có tồn tại User trong DB ứng vs Id đó ko
					if (tblUserLogicImpl.checkExitedUser(userId)) {
						// setDataLogicADM003 để set giá trị selecbox
						setDataLogicADM003(request, response);
						// setDefaultValue để sét giá trị các hạng mục của ADM003 với dữ liệu là thông
						// tin userInfror
						userInfor = setDefaultValue(request, response);
					} else {
						// gán type2 với mã lỗi không tồn tại
						type2 = Constant.DOES_NOT_EXIT_USER;
						check = true;
					}
					// nếu type là lần đầu vào ADM003 hoặc là trường hợp back từ ADMN004 về ADM003
				} else if (Constant.FIRST_START.equals(type1) || Constant.BACK.equals(type1)) {
					setDataLogicADM003(request, response);
					userInfor = setDefaultValue(request, response);
					// trường hợp type = null
				} else if(type1 == null) {
					// gán type2 với là lỗi
					type2 = Constant.ERROR;
					check = true;
				}
				// nếu check là không đúng
				if(!check) {
					// setAttribute userInfor
					request.setAttribute("userInfor", userInfor);
					// dispatcher.forward ADM003
					RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.ADM003);
					dispatcher.forward(request, response);
				//nếu check đúng
				} else {
					// sendRedirect đến url:/error.do
					response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION + "?"
							+ Constant.TYPE + "=" + type2);
				}
		// nếu có lỗi ngoại lệ xảy ra
		} catch (Exception e) {
			// sendRedirect đến url:/error.do
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION + "?" + Constant.TYPE
					+ "=" + Constant.ERROR);
		}
	}

	/**
	 * Thực hiện set giá trị cho các hạng mục selectbox ở màn hình ADM003 Sau khi
	 * lấy được dữ liệu thì thực hiện set các dữ liệu đó lên request
	 * @see setDataLogicADM003(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private void setDataLogicADM003(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MstJapanLogicImpl mstJapanLogicImpl = new MstJapanLogicImpl();
		MstGroupLogicImpl mstGroupLogicImpl = new MstGroupLogicImpl();
		MstJapan mstJapanDefault = new MstJapan();
		List<MstJapan> listMstJapans = mstJapanLogicImpl.getAllMstJapan();
		MstGroup groupDefault = new MstGroup();
		List<MstGroup> listGroup = mstGroupLogicImpl.getListGroup();
		// sét DEFAUTL groupId của group là 0
		groupDefault.setGroupId(0);
		// set DEFAUTL GroupName của group "選択してください"
		groupDefault.setGroupName(Constant.DEFAUTL_SELECTION);
		// add groupDefault vào mảng listGroup vào đầu mảng
		listGroup.add(0, groupDefault);
		// set DEFAUTL CodeLevel của mstJapan "選択してください"
		mstJapanDefault.setCodeLevel(Constant.DEFAUTL_SELECTION);
		// set DEFAUTL NameLevel của mstJapan "選択してください"
		mstJapanDefault.setNameLevel(Constant.DEFAUTL_SELECTION);
		// add mstJapanDefault vào mảng listMstJapans vào đầu mảng
		listMstJapans.add(0, mstJapanDefault);
		// khai báo list năm vs năm bắt đầu = 1980, năm kết thúc là hiện tại
		List<Integer> startlistYears = Common.getListYear(Constant.FROM_YEAR, Common.getYearNow());
		List<Integer> endListYears = Common.getListYear(Constant.FROM_YEAR, Common.getYearNow() + 1);
		List<Integer> listMonths = Common.getListMonth();
		List<Integer> listDays = Common.getListDay();
		request.setAttribute("listGroup", listGroup);
		request.setAttribute("listMstJapans", listMstJapans);
		request.setAttribute("listYears", startlistYears);
		request.setAttribute("endListYears", endListYears);
		request.setAttribute("listMonths", listMonths);
		request.setAttribute("listDays", listDays);
	}

	/**
	 * Set giá trị default cho màn hình ADM003
	 * 
	 * @see setDefaultValue(HttpServletRequest request, HttpServletResponse
	 *      response)
	  * @return userInfor đối tượng chứa thông tin của màn hình ADM003
	 */
	private UserInfor setDefaultValue(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserInfor userInfor = new UserInfor();
		// lấy về ngày tháng năm hiện tại để làm giá trị mặc định cho selecbox
		List<Integer> defaultDate = Common.getCurrentYearMonthDay();
		String type = request.getParameter(Constant.TYPE);
		// trường hợp lần đầu
		if (Constant.FIRST_START.equals(type)) {
			userInfor.setLoginName(Constant.EMPTY_STRING);
			userInfor.setGroupId(0);
			userInfor.setFullName(Constant.EMPTY_STRING);
			userInfor.setFullNameKana(Constant.EMPTY_STRING);
			userInfor.setBirthDateYear(defaultDate.get(0));
			userInfor.setBirthDateMonth(defaultDate.get(1));
			userInfor.setBirthDateDay(defaultDate.get(2));
			userInfor.setEmail(Constant.EMPTY_STRING);
			userInfor.setTel(Constant.EMPTY_STRING);
			userInfor.setPassword(Constant.EMPTY_STRING);
			userInfor.setEncodePassword(Constant.EMPTY_STRING);
			// gọi hàm setDefaultMstJapan để set các giá trị mặc định cho trình độ tiếngnhật
			setDefaultMstJapan(request, response, userInfor);
			// trường hợp Add userInfor
		} else if (Constant.ADD_USER.equals(type)) {
			// khai báo các giá trị userInfor lấy các giá trị từ request
			String loginName = request.getParameter("loginName");
			int groupId = Common.converStringToInt(request.getParameter("groupId"));
			String fullName = request.getParameter("fullName");
			String fullNameKana = request.getParameter("fullNameKana");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String password = request.getParameter("password");
			String endcodepassword = request.getParameter("endcodepassword");
			String codeLevel = request.getParameter("codeLevel");
			String total = request.getParameter("total");
			//khai báo userId để lấy về id từ request
			int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
			// kiểm tra userId > 0 không
			if (userId > 0) {
				userInfor.setUserId(userId);
			}
			userInfor.setLoginName(loginName);
			userInfor.setGroupId(groupId);
			// kiểm tra groupId > 0 hay không để thực hiện set GroupName cho userInfor
			if (groupId > 0) {
				MstGroupLogicImpl groupLogic = new MstGroupLogicImpl();
				MstGroup mstGroup = groupLogic.getGroup(userInfor.getGroupId());
				userInfor.setGroupName(mstGroup.getGroupName());
			}
			userInfor.setFullName(fullName);
			userInfor.setFullNameKana(fullNameKana);
			userInfor.setEmail(email);
			userInfor.setTel(tel);
			userInfor.setPassword(password);
			userInfor.setEncodePassword(endcodepassword);
			userInfor.setTotal(total);
			// gọi getAndSetDate để lấy về ngày, tháng, năm ngày sinh, ngày bắt đầu, ngày
			// kết thúc trình độ tiếng nhật
			// và set các giá trị ngày, tháng, năm ngày sinh, ngày bắt đầu, ngày kết thúc
			// cho userInfor
			getAndSetDate(request, response, userInfor, codeLevel);

		}
		// trường hợp back
		else if (Constant.BACK.equals(type)) {
			HttpSession session = request.getSession();
			// lay key lay giu lieu tren session ve
			String key = request.getParameter(Constant.KEY);
			// gan vao doi tuong userInfor
			userInfor = (UserInfor) session.getAttribute(key);
		}
		// trường hợp edit
		else if (Constant.EDIT_USER.equals(type)) {
			// khai báo userId để lấy về userId từ request
			int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
			// khai báo class tblUserLogicImpl để gọi method getUserInforById()
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			// gọi method getUserInforById tham số userId để lấy thông tin User
			userInfor = tblUserLogicImpl.getUserInforById(userId);
			// khai báo dateBirthDay để lấy ngày sinh từ userInfor
			String dateBirthDay = userInfor.getBirthDay();
			// chuyển đổi dạng data format yyyy/mm/dd tách thành ngày, tháng, năm.
			userInfor.setBirthDateYear(Common.toDateArrayInt(dateBirthDay).get(0));
			userInfor.setBirthDateMonth(Common.toDateArrayInt(dateBirthDay).get(1));
			userInfor.setBirthDateDay(Common.toDateArrayInt(dateBirthDay).get(2));
			// nếu UserInfor có trình độ tiếng nhật
			if (userInfor.getCodeLevel() != null) {
				// khai báo dateStart để lấy ngày bắt đầu từ userInfor
				String dateStart = userInfor.getStartDate();
				// chuyển đổi dạng data format yyyy/mm/dd tách thành ngày, tháng, năm.
				userInfor.setStartDateYear(Common.toDateArrayInt(dateStart).get(0));
				userInfor.setStartDateMonth(Common.toDateArrayInt(dateStart).get(1));
				userInfor.setStartDateDay(Common.toDateArrayInt(dateStart).get(2));
				// khai báo dateEnd để lấy ngày kết thúc từ userInfor
				String dateEnd = userInfor.getEndDate();
				// chuyển đổi dạng data format yyyy/mm/dd tách thành ngày, tháng, năm.
				userInfor.setEndDateYear(Common.toDateArrayInt(dateEnd).get(0));
				userInfor.setEndDateMonth(Common.toDateArrayInt(dateEnd).get(1));
				userInfor.setEndDateDay(Common.toDateArrayInt(dateEnd).get(2));
				// User cần edit không có trình độ tiếng Nhật
			} else {
				setDefaultMstJapan(request, response, userInfor);
			}
		}
		return userInfor;
	}
	/**
	 * Method sét giá trị mặc định cho trình độ tiếng nhật
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param userInfor
	 *            đối tượng UserInfor có chứa thông tin trình độ tiếng nhật
	 */
	private void setDefaultMstJapan(HttpServletRequest request, HttpServletResponse response, UserInfor userInfor) {
		// lấy về (năm, tháng, ngày) hiện tại để làm giá trị mặc định cho selecbox
		List<Integer> defaultDate = Common.getCurrentYearMonthDay();
		userInfor.setCodeLevel(Constant.DEFAUTL_SELECTION);
		userInfor.setStartDateYear(defaultDate.get(0));
		userInfor.setStartDateMonth(defaultDate.get(1));
		userInfor.setStartDateDay(defaultDate.get(2));
		// default hiển thi năm hết hạn trình độ tiếng nhật = năm hiện tại + 1
		userInfor.setEndDateYear(defaultDate.get(0)+1);
		userInfor.setEndDateMonth(defaultDate.get(1));
		userInfor.setEndDateDay(defaultDate.get(2));
		userInfor.setTotal(null);
	}

	/**
	 * Method lấy về ngày, tháng, năm ngày sinh, ngày bắt đầu, ngày kết thúc trình
	 * độ tiếng nhật và sét giá trị cho userInfor
	 * @param request gửi dữ liệu từ client lên server
	 * @param response trả dữ liệu từ server về client
	 * @param userInfor  đối tượng UserInfror
	 * @param codeLevel  trình độ tiếng nhật
	 *           
	 */
	private void getAndSetDate(HttpServletRequest request, HttpServletResponse response, UserInfor userInfor,
			String codeLevel){
		// khai báo các giá trị ngày, tháng, năm sinh, bắt đầu, kết thúc trình độ tiếng nhật lấy từ request
		int birthDayYear = Common.converStringToInt(request.getParameter("birthDayYear"));
		int birthDayMonth = Common.converStringToInt(request.getParameter("birthDayMonth"));
		int birthDayDay = Common.converStringToInt(request.getParameter("birthDayDay"));
		int startDateYear = Common.converStringToInt(request.getParameter("startDateYear"));
		int startDateMonth = Common.converStringToInt(request.getParameter("startDateMonth"));
		int startDateDay = Common.converStringToInt(request.getParameter("startDateDay"));
		int endDateYear = Common.converStringToInt(request.getParameter("endDateYear"));
		int endDateMonth = Common.converStringToInt(request.getParameter("endDateMonth"));
		int endDateDay = Common.converStringToInt(request.getParameter("endDateDay"));
		
		userInfor.setBirthDateYear(birthDayYear);
		userInfor.setBirthDateMonth(birthDayMonth);
		userInfor.setBirthDateDay(birthDayDay);
		// Convert các số năm, tháng, ngày thành 1 chuỗi ngày tháng có format yyyy/mm/dd
		userInfor.setBirthDay(Common.convertToString(birthDayYear, birthDayMonth, birthDayDay));
		// kiểm tra xem người dùng có chọn trình độ tiếng nhật không
		if (!codeLevel.equals(Constant.DEFAUTL_SELECTION)) {
			userInfor.setCodeLevel(codeLevel);
			MstJapanLogicImpl mstJapanLogicImpl = new MstJapanLogicImpl();
			MstJapan mstJapan = mstJapanLogicImpl.getMstJapan(userInfor.getCodeLevel());
			userInfor.setNameLevel(mstJapan.getNameLevel());
			userInfor.setStartDateDay(startDateDay);
			userInfor.setStartDateMonth(startDateMonth);
			userInfor.setStartDateYear(startDateYear);
			userInfor.setEndDateYear(endDateYear);
			userInfor.setEndDateMonth(endDateMonth);
			userInfor.setEndDateDay(endDateDay);
			// Convert các số năm, tháng, ngày thành 1 chuỗi ngày tháng có format yyyy/mm/dd
			userInfor.setStartDate(Common.convertToString(startDateYear, startDateMonth, startDateDay));
			// Convert các số năm, tháng, ngày thành 1 chuỗi ngày tháng có format yyyy/mm/dd
			userInfor.setEndDate(Common.convertToString(endDateYear, endDateMonth, endDateDay));
		} else {
			// gọi hàm setDefaultMstJapan sét các giá trị selecbox mặc định cho trình độ
			// tiêng nhật
			setDefaultMstJapan(request, response, userInfor);
		}
	}
}
