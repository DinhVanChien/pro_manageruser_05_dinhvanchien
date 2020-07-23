/**
 * Copyright(C) 2017 Luvina
 * ChangePassController.java, 15/11/2017 DinhVanChien
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

import common.Common;
import common.Constant;
import logic.impl.TblUserLogicImpl;
import validate.ValidateUser;

/**
 * Sevlet thực hiện chức năng thay đổi password
 */
@WebServlet(
		name = Constant.CHANGE_PASS_CONTROLLER,
		urlPatterns = { Constant.CHANGE_PASS_ANNOTATION})
		
public class ChangePassController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = -6165470132667239512L;

	
	/**
	 * doGet() lấy userId gửi từ request, kiểm tra User có tồn tại không, điều hướng forward trang ADM007
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
			if(tblUserLogicImpl.checkExitedUser(userId)) {
				request.setAttribute(Constant.USER_ID, userId);
				// forward trang ADM007
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Constant.ADM007);
				dispatcher.forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION + "?" + Constant.TYPE + "="
						+ Constant.DOES_NOT_EXIT_USER);
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
	}

	/**
	 * doPost nhận userId, newPass, confirmPass từ request , kiểm tra updatePass để hiển thị thông báo 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
			String newPass = request.getParameter("newPass");
			String confirmPass = request.getParameter("confirmPass");
			ValidateUser validateUser = new ValidateUser();
			List<String> listError = new ArrayList<>();
			// khai báo type để gán kiểu type
			String type = "";
			// khai báo url để gán url cần sendRedirect đến
			String url = "";
			if(tblUserLogicImpl.checkExitedUser(userId)) {
				listError = validateUser.validatePass(newPass, confirmPass);
				if(listError.size() > 0) {
					request.setAttribute(Constant.LIST_ERROR, listError);
					request.setAttribute(Constant.USER_ID, userId);
					request.setAttribute("newPass", newPass);
					request.setAttribute("confirmPass", confirmPass);
					RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM007);
					rd.forward(request, response);
				}else {
					if(tblUserLogicImpl.changePass(userId, newPass)) {
						type = Constant.CHANGE_PASS;
						url = Constant.SUCCESS_ANNOTATION;
					} else {
						type = Constant.ERROR;
						url = Constant.SYSTEM_ERROR_ANNOTATION;
					}
				}
			}else {
				type = Constant.DOES_NOT_EXIT_USER;
				url = Constant.SYSTEM_ERROR_ANNOTATION;
			}
			response.sendRedirect(request.getContextPath() + url + "?" + Constant.TYPE + "=" + type);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}	
	}
}
