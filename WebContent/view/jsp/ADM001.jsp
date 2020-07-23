<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<title>ユーザ管理</title>
</head>
<body>
<form action="login.do" method="post">
<center>
	<table class="tbl_input" cellpadding="4" cellspacing="0" width="400px">
		<tr>
			<th width="120px">&nbsp;</th><th></th>
		</tr>
		<tr>
			<th colspan="2" align="left">アカウント名およびパスワードを入力してください</th>
		</tr>
		
		<tr>
			<c:forEach var="errorMessage" items="${listError}">
			<tr>
				<td class="errMsg" colspan="2" style="color: red;">
				<c:out value="${errorMessage}"/><br></td>
				</tr>
			</c:forEach>
		</tr>
			
		<tr align="left">
			<td class="lbl_left">アカウント名:</td>
			<td align="left">
				<input class="txBox" type="text" name="loginName" value="<c:out value='${loginName}' escapeXml='true'/>" size="20" onfocus="this.style.borderColor='#0066ff';"
				onblur="this.style.borderColor='#aaaaaa';" />
			</td>
		</tr>
		<tr>
			<td class="lbl_left">パスワード:</td>
			<td align="left">
				<input class="txBox" type="password" name="password" value="<c:out value='${password}' 
				escapeXml='true'/>" size="22" onfocus="this.style.borderColor='#0066ff';"
				onblur="this.style.borderColor='#aaaaaa';" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left">
				<input class="btn btn_wider" type="submit" value="ログイン" />				
			</td>
		</tr>
	</table>
	</center>
</form>
</body>

</html>