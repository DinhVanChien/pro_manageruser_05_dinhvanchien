/**
 * Copyright(C) 2017 Luvina
 * MstJapan.java, 22/10/2017 DinhVanChien
 */
package entity;
/**
 * Lớp tạo đối tượng MstJapan làm việc vs bảng MstJapan
 * @author DinhVanChien
 *
 */
public class MstJapan {
	private String codeLevel;
	private String nameLevel;
	public MstJapan(String codeLevel, String nameLevel) {
		super();
		this.codeLevel = codeLevel;
		this.nameLevel = nameLevel;
	}
	public MstJapan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCodeLevel() {
		return codeLevel;
	}
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	public String getNameLevel() {
		return nameLevel;
	}
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}
	
}
