/**
 * Copyright(C) 2017 Luvina
 * FileProperties.java, 7/12/2017 DinhVanChien
 */
package entity;

import java.util.HashMap;
import java.util.Map;
/**
 * class khởi tạo đối tượng file properties thuộc tính là các mảng Map và các hàm set, get tương ứng
 * @author DinhVanChien
 *
 */
public class FileProperties {
	//Các màng Map String cần set tương ứng với các file properties cần đọc
		static private Map<String, String> admin = new HashMap<String, String>();
		static private Map<String, String> config = new HashMap<String, String>();
		static private Map<String, String> database = new HashMap<String, String>();
		static private Map<String, String> messageError = new HashMap<String, String>();
		static private Map<String, String> message = new HashMap<String, String>();
		public static Map<String, String> getAdmin() {
			return admin;
		}
		public static void setAdmin(Map<String, String> admin) {
			FileProperties.admin = admin;
		}
		public static Map<String, String> getConfig() {
			return config;
		}
		public static void setConfig(Map<String, String> config) {
			FileProperties.config = config;
		}
		public static Map<String, String> getDatabase() {
			return database;
		}
		public static void setDatabase(Map<String, String> database) {
			FileProperties.database = database;
		}
		public static Map<String, String> getMessageError() {
			return messageError;
		}
		public static void setMessageError(Map<String, String> messageError) {
			FileProperties.messageError = messageError;
		}
		public static Map<String, String> getMessage() {
			return message;
		}
		public static void setMessage(Map<String, String> message) {
			FileProperties.message = message;
		}
		
}
