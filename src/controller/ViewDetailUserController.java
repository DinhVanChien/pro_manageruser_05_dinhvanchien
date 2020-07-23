/**
 * Copyright(C) 2017 Luvina
 * ViewDetailUserController.java, 9/11/2017 DinhVanChien
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Common;
import common.Constant;
import entity.UserInfor;
import logic.impl.TblUserLogicImpl;


/**
 * Servlet implementation class ViewDetailUserController 
 * Controller xử lý hiển thị UserInfor theo ID sang màn hình ADM005
 * @author DinhVanChien
 */
@WebServlet(
		name = Constant.VIEW_DETAIL_USER_CONTROLLER,
		urlPatterns = {Constant.DETAIL_USER})
public class ViewDetailUserController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = 2440559372210035664L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDetailUserController() {
        super();
    }
	/**
	 * Method doGet() nhận request khi người dùng click ID màn hình ADM002
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// khởi tạo userInfor
			UserInfor userInfor = new UserInfor();
			// khai báo lớp tblUserLogicImpl để gọi method getUserInforById()
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			// khai báo userId để lấy từ request gửi lên 
			int userId = Common.converStringToInt(request.getParameter(Constant.USER_ID));
			// kiểm tra userId request gửi lên có tồn tại trong DB không
			if(tblUserLogicImpl.checkExitedUser(userId)) {
				// gọi method getUserInforById() để lấy về thông tin userInfor theo id
				userInfor = tblUserLogicImpl.getUserInforById(userId);
				// setAttribute userInfor
				request.setAttribute("userInfor", userInfor);
				//RequestDispatcher forward đến màn hình ADM005
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM005);
				rd.forward(request, response);
		// nếu userIdkhông tồn tại trong DB 
			} else {
				// sendRedirect đến url://error.do với type là không có bản ghi
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION 
						+"?" + Constant.TYPE + "="+ Constant.DOES_NOT_EXIT_USER);	
			}
		} catch (Exception e) {
			// sendRedirect đến url://error.do với type là error
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"=" +Constant.ERROR);
		}
		
	}

}
