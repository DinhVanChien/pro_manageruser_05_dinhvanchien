/**
 * Copyright(C) 2017 Luvina
 * ReadProperties.java, 22/10/2017 DinhVanChien
 */
package entity;
/**
 * Lớp TblDetailUserJapan tạo đối tượng TblDetailUserJapan
 * @author DinhVanChien
 *
 */


public class TblDetailUserJapan {
	private int detailUserJapanId;
	private int userId;
	private String codeLevel;
	private String startDate;
	private String endDate;
	private String total;
	
	public TblDetailUserJapan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getDetailUserJapanId() {
		return detailUserJapanId;
	}
	public void setDetailUserJapanId(int detailUserJapanId) {
		this.detailUserJapanId = detailUserJapanId;
	}
	public int getUsedId() {
		return userId;
	}
	public void setUsedId(int usedId) {
		this.userId = usedId;
	}
	public String getCodeLevel() {
		return codeLevel;
	}
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}
