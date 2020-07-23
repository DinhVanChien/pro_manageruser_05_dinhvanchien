/**
 * Copyright(C) 2017 Luvina
 * TblUser.java, 22/10/2017 DinhVanChien
 */
package entity;
/**
 * class tạo đối tượng User
 * @author DinhVanChien
 *
 */
public class TblUser {
	private int userId;
	private int groupId;
	private String loginName;
	private String password;
	private String fullName;
	private String fullNameKana;
	private String email;
	private String tel;
	private String birthDay;
	private String salt;
	public TblUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TblUser(int userId, int groupId, String loginName, String password, String fullName, String fullNameKana,
			String email, String tel, String birthDay, String salt) {
		super();
		this.userId = userId;
		this.groupId = groupId;
		this.loginName = loginName;
		this.password = password;
		this.fullName = fullName;
		this.fullNameKana = fullNameKana;
		this.email = email;
		this.tel = tel;
		this.birthDay = birthDay;
		this.salt = salt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFullNameKana() {
		return fullNameKana;
	}
	public void setFullNameKana(String fullNameKana) {
		this.fullNameKana = fullNameKana;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
