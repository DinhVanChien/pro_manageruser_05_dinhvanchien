/**
 * Copyright(C) 2017 Luvina
 * BaseDaoImpl.java, 7/12/2017 DinhVanChien
 */
package properties;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import common.Constant;
import entity.FileProperties;

/**
 * class đọc file properties
 * @author DinhVanChien
 *
 */
public class ReadProperties {
	// Đọc file 1 lần
	static {
		FileProperties fileProperties = new FileProperties();
		readFilePrperties(Constant.PROPERTIES_ADMIN, fileProperties.getAdmin());
		readFilePrperties(Constant.PROPERTIES_CONFIG, fileProperties.getConfig());
		readFilePrperties(Constant.PROPERTIES_DATABASE, fileProperties.getDatabase());
		readFilePrperties(Constant.PROPERTIES_MESSAGE, fileProperties.getMessage());
		readFilePrperties(Constant.PROPERTIES_MESSAGE_ERROR, fileProperties.getMessageError());
	}
	/**
	 * Hàm đọc file properties
	 * @param url đường dẫn cần đọc
	 * @param map mảng map String cần set
	 */
	public static void readFilePrperties(String url, Map<String, String> map) {
		try {
			//tạo 1 luồng khi tải lớp
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Properties properties = new Properties();
			//đọc file properties trong luồng được tải ở trên
			properties.load(classLoader.getResourceAsStream(url));
			Enumeration<String> en = (Enumeration<String>) properties.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String mes = properties.getProperty(key);
				map.put(key, mes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Đọc giá trị
	 * 
	 * @param key cần đọc
	 * @param map mảng Map lưu nội dung file properties cần đọc          
	 * @return giá trị cần đọc tương ứng với key
	 */
	public static String getValue(String key, Map<String, String> map) {
		String value = "";
		if (map.containsKey(key)) {
			value = map.get(key);
		}
		return value;
	}
}
