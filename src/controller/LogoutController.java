/**
 * Copyright(C) 2017 Luvina
 * LogoutController.java, 22/10/2017 DinhVanChien
 */
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Common;
import common.Constant;

/**
 * LogoutController thực hiện logout
 * @author DinhVanChien
 *
 */
@WebServlet(
		name = Constant.LOGOUT_CONTROLLER,
		urlPatterns = Constant.LOGOUT_ANNOTATION)
public class LogoutController extends HttpServlet {

	/**
	 * Để đảm bảo  servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = -8103028548640776989L;

	/**
	 * method doGet xử lý chức năng logOut
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();	
			// gọi checkLogin(session) kiểm tra loginName lưu lên sisson có tồn tại không
			if(Common.checkLogin(session)) {
				// xóa loginName lưu lên sisson 
				session.removeAttribute(Constant.LOGIN_NAME);
			}	
			resp.sendRedirect(req.getContextPath() + Constant.LOGIN_ANNOTATION);
		} catch (Exception e) {
			resp.sendRedirect(req.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
	}
}