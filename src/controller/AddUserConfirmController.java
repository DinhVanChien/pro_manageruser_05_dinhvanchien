/**
 * Copyright(C) 2017 Luvina
 * AddUserConfirmController.java, 5/11/2017 DinhVanChien
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import entity.UserInfor;
import logic.impl.TblUserLogicImpl;

/**
 * Servlet implementation class AddUserConfirmController Controller 
 * xử lý hiển thị màn hình ADM004 và khi người dùng click nút Ok ADM004
 *  @author DinhVanChien
 */
@WebServlet(
		name = Constant.ADD_USER_CONFIRM_CONTROLLER,
		urlPatterns = {
				Constant.ADD_USER_CONFIM_ANNOTATION, 
				Constant.EDIT_USER_CONFIM_ANNOTATION, 
				Constant.ADD_USER_OKE_ANNOTATION
				})
public class AddUserConfirmController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = -1171936340724348365L;

	

	public AddUserConfirmController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Method xử lý khi nhận sendRedirect từ controller AddUserInputController truyền sang  
	 * url:/addUserConfim.do và url:editUserConfirm.do
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String key = request.getParameter(Constant.KEY);
			UserInfor userInfor = (UserInfor) session.getAttribute(key);
			// kiểm tra userInfor có tồn tại không
			if (userInfor != null) {
				request.setAttribute(Constant.KEY, key);
				request.setAttribute("userInfor", userInfor);
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM004);
				rd.forward(request, response);
		// không tồn tại UserInfor
			} else {
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION + "?" + Constant.TYPE + "="
						+ Constant.DOES_NOT_EXIT_USER);
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Method doPost xử lý khi người dùng click OK màn hình ADM004
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String key = request.getParameter(Constant.KEY);
			// khai báo typeReq để lấy về type từ request
			String typeReq = request.getParameter(Constant.TYPE);
			// khai báo biến type để gán kiểm style
			String type = Constant.EMPTY_STRING;
			// khai báo biến url để gán các url mà sendRedirect đến success.do hoặc error.do
			String url = Constant.EMPTY_STRING;
			UserInfor userInfor = (UserInfor) session.getAttribute(key);
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			if (userInfor != null) {
				// Trường hợp thêm mới user
				if (Constant.ADD_USER.equals(typeReq)) {
					// kiểm tra createUser(UserInfor userInfor) add user đúng không
						if (tblUserLogicImpl.createUser(userInfor)) {
							type = Constant.INSERT_SUCCESS;
							url = Constant.SUCCESS_ANNOTATION;
						} else {
							type = Constant.ERROR;
							url = Constant.SYSTEM_ERROR_ANNOTATION;
						}
				}
				// Trường hợp update user
				if(Constant.EDIT_USER.equals(typeReq)) {
					// kiểm tra editUserInfor(UserInfor userInfor) edit user đúng không
					if(tblUserLogicImpl.editUserInfor(userInfor)) {
						type = Constant.EDIT_SUCCESS;
						url = Constant.SUCCESS_ANNOTATION;
					} else {
						type = Constant.ERROR;
						url = Constant.SYSTEM_ERROR_ANNOTATION;
					}
				}
				// xóa session
				session.removeAttribute(key);
			}else{
				type = Constant.ERROR;
				url = Constant.SYSTEM_ERROR_ANNOTATION;
			}
			// sendRedirect đến một trong các url: "/success.do", "/error.do"
			response.sendRedirect(request.getContextPath() + url + "?" + Constant.TYPE + "=" + type);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
	}
}
