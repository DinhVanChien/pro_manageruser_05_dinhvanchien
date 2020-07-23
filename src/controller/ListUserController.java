/**
 * Copyright(C) 2017 Luvina
 * ListUserController.java, 22/10/2017 DinhVanChien
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
import logic.impl.MstGroupLogicImpl;
import logic.impl.TblUserLogicImpl;
import properties.ReadProperties;
import entity.FileProperties;
import entity.MstGroup;
import entity.UserInfor;;

/**
 * lớp thực hiện các chức năng màn hình ADM002, get listUser, search, sort, pading.
 * 
 * @author DinhVanChien
 *
 */
@WebServlet(
		name = Constant.LIST_USER_CONTROLLER,
		urlPatterns = { Constant.LIST_USER_ANNOTATION })
public class ListUserController extends HttpServlet {
	/**
	 * Để đảm bảo servlet cùng 1 vesion
	 */
	private static final long serialVersionUID = 4717901258796315422L;

	
	
/**
 * Method doGet() nhận req khi người dùng click link sắp xếp, link pading, button back ADM003 về ADM002, ADM005 về ADM002
 * gọi method doPost() xử lý vì
 * ở màn hình ADM002 có nhiều chức năng mà có xử lý gần giống nhau (4 trường hợp màn hình) và đều ra màn hình ADM002 
 * nhưng có những chức năng dưới dạng post như :tìm kiếm,
 *  dưới dạng get nên doGet và doPost xử lý gần giống nhau. Để tối ưu code ta viết gộp vào 1 hàm doPost rồi hàm doGet gọi đến
 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doPost(req, resp);
		} catch (Exception e) {
			// Nếu lỗi chuyển sang màn hình system_error
			resp.sendRedirect(req.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"="+Constant.ERROR);
		}
	}

	/**
	 * Method doPost() xử lý khi người dùng click nút search ADM002 và các nghiệm vụ sắp xếp, link pading, back ADM002
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			MstGroupLogicImpl mstGroupLogicImpl = new MstGroupLogicImpl();
			TblUserLogicImpl tbLogicImpl = new TblUserLogicImpl();
			int groupId = 0;
			int currentPage = 1;
			String fullName = Constant.EMPTY_STRING;
			String typeSort = Constant.EMPTY_STRING;
			String sortByFullName = Constant.SORT_ASC;
			String sortByCodeLevel = Constant.SORT_ASC;
			String sortByEndDate = Constant.SORT_DESC;
			int limit = Common.getLimit();
			int pageLimit = Common.converStringToInt(ReadProperties.getValue(Constant.PAGE_LIMIT, FileProperties.getConfig()));
			String type = req.getParameter(Constant.TYPE);
			// nếu type = firstStart (lần đầu vào trang)
			if (Constant.FIRST_START.equals(type)) {
				// gán kiểu sắp xếp là fullName, còn các giá trị khác mặc định như khai báo
				typeSort = Constant.FULL_NAME;
			// nếu type = chức năng search
			} else if (Constant.SEARCH.equals(type)) {
				fullName = (req.getParameter(Constant.FULL_NAME) != null) ?req.getParameter(Constant.FULL_NAME) 
						: Constant.EMPTY_STRING;
				groupId = (req.getParameter(Constant.GROUP_ID) != null) ? Common.converStringToInt(req.getParameter(Constant.GROUP_ID))
						: 0;
				typeSort = Constant.EMPTY_STRING;
				session.setAttribute(Constant.FULL_NAME, fullName);
				session.setAttribute(Constant.GROUP_ID, groupId);
				//set currentPage lên session để khi người dùng click search sau đó click thêm mới,
				//khi click back ADM003 về đúng trang hiển thị các bản ghi đã tìm kiếm ADM002
				session.setAttribute(Constant.CURRENT_PAGE, currentPage);
			} else {
				fullName = session.getAttribute(Constant.FULL_NAME) != null ? (String) session.getAttribute(Constant.FULL_NAME)
						: Constant.EMPTY_STRING;
				groupId = session.getAttribute(Constant.GROUP_ID) != null ? (Integer) session.getAttribute(Constant.GROUP_ID) : 0;
				// nếu type = chức năng sắp xếp
				if (Constant.SORT.equals(type)) {
					// kiểu sắp xếp lấy từ req
					typeSort = req.getParameter(Constant.TYPE_SORT);
					// nếu sắp xếp theo fullName
					if (typeSort.equals(Constant.FULL_NAME)) {
						sortByFullName = req.getParameter(Constant.SORT_BY_FULL_NAME);
						sortByFullName = Common.setSortType(sortByFullName);
						sortByCodeLevel = Constant.SORT_ASC;
						sortByEndDate = Constant.SORT_DESC;
						// nếu sắp xếp theo CodeLevel
					} else if (typeSort.equals(Constant.CODE_LEVEL)) {
						sortByCodeLevel = req.getParameter(Constant.SORT_BY_CODE_LEVEL);
						sortByCodeLevel = Common.setSortType(sortByCodeLevel);
						sortByFullName = Constant.SORT_ASC;
						sortByEndDate = Constant.SORT_DESC;
						// nếu sắp xếp theo EndDate
					} else if (typeSort.equals(Constant.END_DATE)) {
						sortByEndDate = req.getParameter(Constant.SORT_BY_END_DATE);
						sortByEndDate = Common.setSortType(sortByEndDate);
						sortByFullName = Constant.SORT_ASC;
						sortByCodeLevel = Constant.SORT_ASC;
					}
					session.setAttribute(Constant.TYPE_SORT, typeSort);
				}

				// nếu type = chức năng phân trang
				else if (Constant.PAGING.equals(type)) {
					typeSort = session.getAttribute(Constant.TYPE_SORT) != null
							? (String) session.getAttribute(Constant.TYPE_SORT)
							: Constant.EMPTY_STRING;
					sortByFullName = session.getAttribute(Constant.SORT_BY_FULL_NAME) != null
							? (String) session.getAttribute(Constant.SORT_BY_FULL_NAME)
							: Constant.SORT_ASC;
					sortByCodeLevel = session.getAttribute(Constant.SORT_BY_CODE_LEVEL) != null
							? (String) session.getAttribute(Constant.SORT_BY_CODE_LEVEL)
							: Constant.SORT_ASC;
					sortByEndDate = session.getAttribute(Constant.SORT_BY_END_DATE) != null
							? (String) session.getAttribute(Constant.SORT_BY_END_DATE)
							: Constant.SORT_ASC;
					currentPage = req.getParameter("page") == null ? 1
							: Common.converStringToInt(req.getParameter("page"));
					// set de khi nta back 03, 05 ve 02 van giu dung trang hien tai va user
					session.setAttribute(Constant.CURRENT_PAGE, currentPage);

				}
				// nếu type = chức năng back từ ADM003 -> ADM002 hoặc ADM005 -> ADM002
				else if (Constant.BACK.equals(type)) {
					typeSort = session.getAttribute(Constant.TYPE_SORT) != null ? (String) session.getAttribute(Constant.TYPE_SORT)
							: Constant.EMPTY_STRING;
					sortByFullName = session.getAttribute("sortName") != null ? (String) session.getAttribute(Constant.SORT_BY_FULL_NAME)
							: Constant.SORT_ASC;
					sortByCodeLevel = session.getAttribute("sortCode") != null ? (String) session.getAttribute(Constant.SORT_BY_CODE_LEVEL)
							: Constant.SORT_ASC;
					sortByEndDate = session.getAttribute("sortDate") != null ? (String) session.getAttribute(Constant.SORT_BY_END_DATE)
							: Constant.SORT_DESC;
					currentPage = session.getAttribute("currentPage") != null
							? (Integer) session.getAttribute("currentPage"): 1;
				}	
			}

			// lấy về tổng số user từ csdl
			int totalUser = tbLogicImpl.getTotalUsers(groupId, fullName);
			// kiểm tra xem tổng số user có khác 0 hay không
			if (totalUser != 0) {
				// khai báo offset
				int offset = Common.getOffset(currentPage, limit);
				List<UserInfor> listUser = new ArrayList<UserInfor>();
				// lấy về listUser
				listUser = tbLogicImpl.getListUsers(offset, limit, groupId, fullName, typeSort, sortByFullName,
						sortByCodeLevel, sortByEndDate);
				// lấy về listGroup
				List<MstGroup> listGroup = mstGroupLogicImpl.getListGroup();
				// tổng số trang
				int totalPage = Common.getTotalPage(totalUser, limit);
				// danh sach trang trong 1 phân vùng
				List<Integer> listPaging = Common.getListPaging(totalUser, limit, currentPage);
				req.setAttribute(Constant.LIST_USER, listUser);
				req.setAttribute(Constant.LIST_PAGING, listPaging);
				req.setAttribute(Constant.TOTAL_PAGE, totalPage);
				req.setAttribute(Constant.CURRENT_PAGE, currentPage);
				req.setAttribute(Constant.PAGE_LIMIT, pageLimit);
				req.setAttribute(Constant.LIST_GROUP, listGroup);
				req.setAttribute(Constant.SORT_BY_FULL_NAME, sortByFullName);
				req.setAttribute(Constant.SORT_BY_CODE_LEVEL, sortByCodeLevel);
				req.setAttribute(Constant.SORT_BY_END_DATE, sortByEndDate);

			} else {
				// nếu ko có user nào thông báo lỗi hiển thị màn hình ADM002
				req.setAttribute("checkErrorDate", 0);
				req.setAttribute("messageErrorDate", ReadProperties.getValue(Constant.MSG005, FileProperties.getMessage()));
			}
			// tạo đối tượng RequestDispatcher để forward đến trang ADM002.jsp
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Constant.ADM002);
			dispatcher.forward(req, resp);
			// else tổng số user = 0 thông báo lỗi MSG005 không tìm thấy bản ghi
		} catch (Exception e) {
			// nếu có lỗi ngoại lệ
			// sendRedirect đến trang System_Erro
			resp.sendRedirect(req.getContextPath() + Constant.SYSTEM_ERROR_ANNOTATION+"?"+Constant.TYPE+"="+Constant.ERROR);
		}
	}

}
