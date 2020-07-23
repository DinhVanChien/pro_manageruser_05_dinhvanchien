<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src=".view/js/user.js"></script>
<title>Change Password</title>
</head>
<body align="center" >
	<!-- Begin vung header -->
	<jsp:include page="header.jsp" />
	<!-- End vung header -->
	<!-- Begin vung input-->
	<form action="changePass.do" method="post" name="inputform">
		<center>
		<table>
			<tr>
				<th width="120px">&nbsp;</th>
				<th></th>
				</tr>
			<tr>
				<c:forEach var="errorMessage" items="${listError}">
					<td class="errMsg" colspan="2" style="color: red;"><c:out value="${errorMessage}"/></td>
				</c:forEach>
				</tr>
		</table>
			<table class="tbl_input" cellpadding="4" cellspacing="0"
				width="400px">
				<tr>
					<td class="lbl_left"></td>
					<td align="left"><input type="hidden" name="userId"
						value="${userId}" /></td>
				</tr>
				<tr align="left">
					<td class="lbl_left">New pass:</td>
					<td align="left"><input class="txBox" type="password"
						name = "newPass"  size="22"
						value = "<c:out value = '${newPass}' escapeXml = 'true'/>"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';"/></td>
				</tr>
				<tr>
					<td class ="lbl_left">confirmPass:</td>
					<td align = "left"><input class="txBox" type="password"
						name = "confirmPass" size="22"
						value = "<c:out value = '${confirmPass}' escapeXml = 'true'/>"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';"/></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><input class="btn btn_wider" type="submit"
						value="Change" /></td>
				</tr>
			</table>
		</center>
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" />
	<!-- End vung footer -->
</body>
</html>