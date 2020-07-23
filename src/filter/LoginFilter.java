/**
 * Copyright(C) 2017 Luvina
 * LoginFilter.java, 22/10/2017 DinhVanChien
 */
package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import common.Common;
import common.Constant;

/**
 * Kiểm tra xem người dùng đã login hay chưa bằng cách  kiểm tra session hoặc (logicName lưu lên session) có null hay không nếu null 
 * quay lại ADM001 nếu ko null cho phép đi tiếp
 * @author DinhVanChien
 *
 */
@WebFilter(
		filterName = Constant.LOGIN_FILTER,
		urlPatterns = {Constant.DO_FILTER})
public class LoginFilter implements Filter{
	/**
	 * init(FilterConfig arg0) method Khởi tạo Filter
	 */
		@Override
		public void init(FilterConfig arg0) throws ServletException {
			// TODO Auto-generated method stub
		}
	/**
	 * doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) 
	 * Kiểm tra xem người dùng đã login hay chưa bằng cách  kiểm tra session hoặc (logicName lưu lên session) có null hay không nếu null 
	 * quay lại ADM001 nếu ko null cho phép đi tiếp
	 */
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse respone = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		// khai báo path để lấy về đường dẫn tuyệt đối
		String path = request.getRequestURI().substring(request.getContextPath().length());
		// kiểm tra nếu đường dẫn là login.do hoặc "/"
			if (Constant.LOGIN_ANNOTATION.equals(path) || "/".equals(path)) {
				// goi method checkLogin(session) kiểm tray xem session hoặc loginName có tồn tại hay không
				if(Common.checkLogin(session)) {
					// sendRedirect đến /listAllUser.do
					respone.sendRedirect(request.getContextPath() + Constant.LIST_USER_ANNOTATION);
				// ngược lại nếu không tồn tại session hoặc (loginName trên sisson)
				}else {
					// cho phép đi qua đến trang login
					arg2.doFilter(arg0, arg1);
				}
		
			} else if(path.contains(".jsp")) {
				respone.sendRedirect(request.getContextPath() + Constant.LOGIN_ANNOTATION);
			}
			// ngược lại nếu đường dẫn không phải login.do hoặc "/", hay đường dẫn file jsp
			else {
				// goi method checkLogin(session) kiểm tra xem session hoặc loginName có tồn tại hay không
				if(Common.checkLogin(session)) {
					// cho phép đi qua
					arg2.doFilter(arg0, arg1);
				// ngược lại sendRedirect đến login.do
				}else {
					respone.sendRedirect(request.getContextPath() + Constant.LOGIN_ANNOTATION);
				}
		}
	}

/**
 * Hủy Filter
 */
	@Override
	public void destroy() {
	}

}
