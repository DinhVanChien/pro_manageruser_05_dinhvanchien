/**


 * 
 * 
 * Copyright(C) 2017 Luvina
 * LoginController.java, 22/10/2017 DinhVanChien
 */
package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import common.Constant;
import validate.ValidateLogin;

/**
 * Lớp LoginController điều hướng xử lý chức năng login
 * @author DinhVanChien
 *
 */
@WebServlet(
		name = Constant.LOGIN_CONTROLLER,
		urlPatterns = {Constant.LOGIN_ANNOTATION})
public class LoginController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = -1591444818940832891L;
	
	/**
	 * method xử lý chức năng login khi người dùng click login ADM001
	 * @param req là thông tin gửi từ client lên server
	 * @param resp là server trả kết quả về cho client
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String loginName = req.getParameter(Constant.LOGIN_NAME);
			String password = req.getParameter(Constant.PASSWORD);
			List<String> listErro = ValidateLogin.validateLogin(loginName, password);
			// kiểm tra nếu có lỗi
			if(listErro.size() > 0) {
				req.setAttribute(Constant.LIST_ERROR, listErro);
				req.setAttribute(Constant.LOGIN_NAME, loginName);
				req.setAttribute(Constant.PASSWORD, password);
				//RequestDispatcher forward trang login ADM001
				RequestDispatcher rd = req.getRequestDispatcher(Constant.ADM001);
				rd.forward(req, resp);
			}
			// nếu không có lỗi khi đăng nhập sendRedirect đến url:/listAllUser.do", đặt type = firstStart			
			else {
				HttpSession session = req.getSession();
				session.setAttribute(Constant.LOGIN_NAME, loginName);
				resp.sendRedirect(req.getContextPath() + Constant.LIST_USER_ANNOTATION + "?" + Constant.TYPE + "="
						+ "firstStart");
			}
	// nếu có lỗi ngoại lệ khi đăng nhập sendRedirect đến url:/error.do, đặt type = error		
		} catch (Exception e) {
			resp.sendRedirect(req.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE +"="+Constant.ERROR);
		}
	}
	/**
	 * method xử lý khi sendRedirect từ LogoutController gửi sang, khi url không thể đi qua LoginFilter.
	 * @param req là thông tin gửi từ client lên server
	 * @param resp là server trả kết quả về cho client
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//RequestDispatcher forward trang login ADM001
				RequestDispatcher rd = req.getRequestDispatcher(Constant.ADM001);
				rd.forward(req, resp);
	// nếu có lỗi ngoại lệ khi đăng nhập sendRedirect đến url:/error.do, đặt type = error		
		} catch (Exception e) {
			resp.sendRedirect(req.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
	}	
	
}
