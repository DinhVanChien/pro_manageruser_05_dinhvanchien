<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="common.Constant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="WebContent/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src='<c:url value="/js/user.js"/>'></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- End vung header -->
	<jsp:include page="header.jsp" />


	<!-- Begin vung dieu kien tim kiem -->
	<form action="listAllUser.do?type=search" method="post" name="mainform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
					
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text" size="20"
								name="fullName" value="<c:out value="${fullName}" escapeXml="true"></c:out>"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" /></td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px"><select name="groupId">
									<option value="0">全て</option>
									<c:forEach items = "${listGroup}" var="group">
										<option value="${group.groupId}" ${group.groupId==groupId?'selected':'' }>
											<c:out value="${group.groupName}"  escapeXml="true" />
										</option>
									</c:forEach>
							</select></td>
							<td align="left"><input class="btn" type="submit" value="検索" /></td>
								<td><input class="btn" type="button" value="新規追加"
								onclick="window.location.href='addUserInput.do?type=firstStart'" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
		width="80%">
			<c:if test="${checkErrorDate == 0}">
				<font color="red"><c:out value="${messageErrorDate}"></c:out></font>
			</c:if>
		<tr class="tr2">
			<th align="center" width="20px">ID</th>
			<th align="left">氏名 
			<a href="./listAllUser.do?type=sort&typeSort=fullName&sortByFullName=${sortByFullName}">
			<c:choose>
					<c:when test="${sortByFullName == Constant.SORT_ASC}">▲▽
					</c:when>
					<c:otherwise>
						△▼
					</c:otherwise>
			</c:choose></a>
			
			</th>
			<th align="left">生年月日</th>
			<th align="left">グループ</th>
			<th align="left">メールアドレス</th>
			<th align="left" width="70px">電話番号</th>
			<th align="left">日本語能力
			
			<a href="./listAllUser.do?type=sort&typeSort=codeLevel&sortByCodeLevel=${sortByCodeLevel}">
				<c:choose>
						<c:when test="${sortByCodeLevel == Constant.SORT_ASC}">▲▽
						</c:when>
							<c:otherwise>
							△▼
							</c:otherwise>
				</c:choose></a>
			</th>
			<th align="left">失効日 
			
			<a href="./listAllUser.do?type=sort&typeSort=endDate&sortByEndDate=${sortByEndDate}">
				<c:choose>
					<c:when test="${sortByEndDate == Constant.SORT_ASC}">▲▽
					</c:when>
						<c:otherwise>
							△▼
						</c:otherwise>
				</c:choose></a>
			
			</th>
			<th align="left">点数</th>
		</tr>
		<c:forEach var="user" items="${listUser}">
			<tr>
				<td align="right"><a href="./detailUser.do?userId=${user.userId}"><c:out
							value="${user.userId}"></c:out></a></td>
				<td>
					<c:out value="${user.fullName}" escapeXml="true">
					</c:out>
				</td>
				<td align="center">
					<c:out value="${user.birthDay}">
					</c:out>
				</td>
				<td>
					<c:out value="${user.groupName}" escapeXml="true">
					</c:out>
				</td>
				<td>
					<c:out value="${user.email}" escapeXml="true">
					</c:out>
				</td>
				<td>
					<c:out value="${user.tel}" escapeXml="true">
					</c:out>
				</td>
				<td>
					<c:out value="${user.nameLevel}" escapeXml="true">
					</c:out>
				</td>
				<td align="center">
					<c:out value="${user.endDate}">
					</c:out>
				</td>
				<td align="right">
					<c:out value="${user.total}" escapeXml="true"/>
				</td>
			</tr>
		</c:forEach>

	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
	<!--lengthPaging độ dài của phân vùng  value="${fn:length(listPaging)} độ dài của 1 listPaging -->
		<c:set var="lengthPaging" value="${fn:length(listPaging)}" />
		<c:if test="${lengthPaging >= 1}">
			<tr class="lbl_paging">
				<c:if test="${listPaging[0] != 1}">
				<!--
					khi ta  back về gán giá trị lại cho trang đầu của vùng trước nó ví dụ đang ở vùng 2 trang (<<4,5,6)
					listPaging[0] = 4,pageLimit = 3 back về hiển thị dữ liệu trang 1-->
					<td>
						<a href="./listAllUser.do?type=paging&page=${listPaging[0] - pageLimit}">&lt;&lt;</a>&nbsp;</td>
				</c:if>
				<!-- vòng for duyệt các trang trong 1 phân vùng -->
				<c:forEach items="${listPaging}" var="page">
				<!-- kiểm tra trang đó phải trang hiện tại k0 -->
					<c:if test="${page == currentPage}">
					<!-- hiển thị trang đó vs them dau cách -->
						<td>${page}&nbsp;</td>
					</c:if>
					<c:if test="${page != currentPage}">
					<!-- link trang hiện tại đó để click vào -->
						<td>
							<a href="./listAllUser.do?type=paging&page=${page}">${page}</a>&nbsp;</td>
					</c:if>
				</c:forEach>
				<!-- Vùng này hiển thị dấu link >> và next hiển thị phân vùng tiếp theo -->
				<c:if test="${listPaging[lengthPaging - 1] < totalPage}">
					<td><a
						href="./listAllUser.do?type=paging&page=${listPaging[lengthPaging - 1] + 1}">&gt;&gt;</a>&nbsp;</td>
				</c:if>
			</tr>
		</c:if>

	</table>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp"></jsp:include>
	<!-- End vung footer -->

</body>
</html>