/**
 * class thực hiện xử lý javascript của project
 */

/**
 * hàm thực hiện show or ẩn trình độ tiếng nhật
 * @param element
 * @returns
 */
function showOrHideJapaneseLevel(element) {
	if (document.getElementById(element).style.display == "none") {
		document.getElementById(element).style.display = "block";
	} else {
		document.getElementById(element).style.display = "none";
	}
}
function dialogDelete(userId) {
	var message = confirm("削除しますが、よろしいでしょうか。");
	if(message == true) {
		window.location.href="deleteUser.do?userId="+userId;
	} else {
		window.location.href="detailUser.do?userId="+userId;
		
	}
}