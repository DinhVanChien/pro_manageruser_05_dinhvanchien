/**
 * Copyright(C) 2017 Luvina
 * ErrorController.java, 22/10/2017 DinhVanChien
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;
import entity.FileProperties;
import properties.ReadProperties;

/**
 * Controller điều hướng các câu thông báo lỗi hiển thị lên màn hình System_Error
 * @author DinhVanChien
 *
 */
@WebServlet(name = Constant.ERROR_CONTROLLER,
		urlPatterns = {Constant.SYSTEM_ERROR_ANNOTATION})
public class ErrorController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = 5727080521362982099L;

	

	/*
	 * doGet() xử lý điều hướng các câu thông báo lỗi đến trang System_Error.jsp
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String type = req.getParameter(Constant.TYPE);
			if(type.equals(Constant.ERROR)) {
				// Lỗi thao tác với hệ thống
				req.setAttribute("message", ReadProperties.getValue(Constant.ER015, FileProperties.getMessageError()));
			} else if(type.equals(Constant.DOES_NOT_EXIT_USER)) {
				// Lỗi user khong ton tai
				req.setAttribute("message", ReadProperties.getValue(Constant.ER013, FileProperties.getMessageError()));
			}
			RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.SYSTEM_ERROR);
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			resp.sendRedirect(req.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
		
	}
}
