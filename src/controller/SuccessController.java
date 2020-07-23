/**
 * Copyright(C) 2017 Luvina
 * SuccessController.java, 1/11/2017 DinhVanChien
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
 * Servlet implementation class SuccessController
 * controller điều hướng hiển thị câu thông báo ADM006
 */
@WebServlet(
		name = Constant.SUCCESS_CONTROLLER,
		urlPatterns = {Constant.SUCCESS_ANNOTATION})
public class SuccessController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = 5762180556811305247L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public SuccessController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * method doGet() điều hướng các câu thông báo đến ADM006.jsp
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter(Constant.TYPE);
		try {
			// Trường hợp insert user thành công
			if (type.equals(Constant.INSERT_SUCCESS)) {
				request.setAttribute(Constant.MESSAGE, ReadProperties.getValue(Constant.MSG001, FileProperties.getMessage()));
			}else
				// Trường hợp edit user thành công hoạc edit password thành công
				if (type.equals(Constant.EDIT_SUCCESS) || type.equals(Constant.CHANGE_PASS)) {
					request.setAttribute(Constant.MESSAGE, ReadProperties.getValue(Constant.MSG002, FileProperties.getMessage()));
			} 
			else
				// trường hợp deleteUser
				if(type.equals(Constant.DELETE_USER)) {
					request.setAttribute(Constant.MESSAGE, ReadProperties.getValue(Constant.MSG003, FileProperties.getMessage()));
				}
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Constant.ADM006);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
	}
}
