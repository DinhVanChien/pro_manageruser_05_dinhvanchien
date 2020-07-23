/**
 * Copyright(C) 2017 Luvina
 * ValidateLogin.java, 22/10/2017 DinhVanChien
 */
package validate;

import java.util.List;

import common.Constant;

import java.util.ArrayList;

import entity.FileProperties;
import properties.ReadProperties;
/**
 * Lớp ValidateLogin trả về danh sách lỗi
 * @author DinhVanChien
 *
 */
public class ValidateLogin {
	/**
	 * method lấy về lỗi khi đăng nhập
	 * @param username tên đăng nhập
	 * @param password password khi đăng nhập
	 * @return listErroMessage danh sách lỗi khi đăng nhập
	 */
	
	public static List<String> validateLogin(String username, String password) {
		List<String> listErroMessage = new ArrayList<String>();
		if(username.isEmpty() || password.isEmpty()) {
			if(username.isEmpty()) {
				listErroMessage.add(ReadProperties.getValue(Constant.ER001_LOGIN_NAME, FileProperties.getMessageError()));
			} 
			if(password.isEmpty()) {
				listErroMessage.add(ReadProperties.getValue(Constant.ER001_PASS, FileProperties.getMessageError()));
			}
		}else if(!username.equals(ReadProperties.getValue(Constant.USER, FileProperties.getAdmin())) 
		|| !password.equals(ReadProperties.getValue(Constant.PASSWORD, FileProperties.getAdmin()))) {
			listErroMessage.add(ReadProperties.getValue(Constant.ER016, FileProperties.getMessageError()));
		}
		return listErroMessage;
	}
}
