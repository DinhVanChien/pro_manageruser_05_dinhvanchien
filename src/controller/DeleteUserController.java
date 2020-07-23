/**
 * Copyright(C) 2017 Luvina
 * DeleteUserController.java, 15/11/2017 DinhVanChien
 */
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Common;
import common.Constant;
import logic.impl.TblUserLogicImpl;

/**
 * Servlet implementation class DeleteUserController xử lý delete userinfor
 */
@WebServlet(
		name = Constant.DELETE_USER_CONTROLLER,
		urlPatterns = { Constant.DELETE_USER_ANNOTATION })
public class DeleteUserController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = 543939271166759724L;

	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method xử lý khi người dùng click ok delete bên dialog
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
			// khai báo type để gán kiểu type
			String type = "";
			// khai báo url để gán url cần sendRedirect đến
			String url = "";
			// kiểm tra nếu tồn tại user theo Id
			if (tblUserLogicImpl.checkExitedUser(userId)) {
					if (tblUserLogicImpl.deleteUserInfor(userId)) {
						type = Constant.DELETE_USER;
						url = Constant.SUCCESS_ANNOTATION;
					} else {
						type = Constant.ERROR;
						url = Constant.SYSTEM_ERROR_ANNOTATION;
					}
				// Không tồn tại user theo Id
			} else {
				type = Constant.DOES_NOT_EXIT_USER;
				url = Constant.SYSTEM_ERROR_ANNOTATION;
			}
			response.sendRedirect(request.getContextPath() + url + "?" + Constant.TYPE + "=" + type);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
		
	}
}
