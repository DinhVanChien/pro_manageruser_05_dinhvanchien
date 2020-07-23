<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="WebContent/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src='<c:url value="./view/js/user.js"/>'></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->	
			<jsp:include page="header.jsp" />
<!-- End vung header -->	

<!-- Begin vung input-->	
<form action="addUserValidate.do?type=add" method="post" name="inputform" id="inputform">
	<table  class="tbl_input"   border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					会員情報編集
				</div>
			</th>			
		</tr>		
	
			<c:forEach items="${listError}" var="error">
				<tr>
					<td class="errMsg" style="padding-left: 120px" colspan="2">
						<c:out value="${error}" />
					</td>
							
				</tr>
			</c:forEach>

		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="0" width="100%" class="tbl_input" cellpadding="4" cellspacing="0" >	
					<tr>
						<td></td>
						<td align="left">
							<input class="txBox" type="hidden" name="userId" 
							value="<c:out value="${userInfor.userId}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>				
					<tr>
						<td class="lbl_left"><font color = "red">*</font> アカウント名:</td>
						<td align="left">
						<c:if test="${userInfor.userId != 0 }">
							<input class="txBox" type="text" name="loginName" readonly="readonly" 
							value = "<c:out value = "${userInfor.loginName}" escapeXml = "true"/>"
							size="15" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';"/>
						</c:if>
						<c:if test ="${userInfor.userId == 0 }">
							<input class="txBox" type="text" name = "loginName" 
							value = "<c:out value = "${userInfor.loginName}" escapeXml = "true"/>"
							size="15" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';"
								/></c:if>
						</td>
						<td align="left">
						<input class="txBox" type="hidden" name = "userId" 
							value = "<c:out value = "${userInfor.userId}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> グループ:</td>
						<td align="left">						
					<select name = "groupId">
					<c:forEach items="${listGroup}" var="group">
							<option value = "${group.groupId}"
								${group.groupId==userInfor.groupId?'selected':'' }>
								
								<c:out value = "${group.groupName}" escapeXml = "true"></c:out>
								</option>
					</c:forEach>
					</select>							
							<span>&nbsp;&nbsp;&nbsp;</span>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name = "fullName"
							value = "<c:out value = "${userInfor.fullName}" escapeXml = "true"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name = "fullNameKana" 
						value = "<c:out value = "${userInfor.fullNameKana}" escapeXml = "true"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>	
					<tr>
					
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><select name="birthDayYear">
										<c:forEach items="${listYears}" var="year">
											<option value="${year}" ${year==userInfor.birthDateYear?'selected':'' }>${year}</option>
										</c:forEach>
								</select>年 <select name="birthDayMonth">
										<c:forEach items="${listMonths}" var="month">
											<option value="${month}"
												${month==userInfor.birthDateMonth?'selected':'' }>${month}</option>
										</c:forEach>
								</select>月 <select name="birthDayDay">
										<c:forEach items="${listDays}" var="day">
											<option value="${day}" ${day==userInfor.birthDateDay?'selected':'' }>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> メールアドレス:</td>
						<td align="left">
							<input class="txBox" type="text" name = "email" 
							value = "<c:out value = "${userInfor.email}" escapeXml = "true"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font>電話番号:</td>
						<td align="left">
						<input class="txBox" type="text" name = "tel" 
							value = "<c:out value = "${userInfor.tel}" escapeXml = "true"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />						
						</td>
					</tr>
					<tr>
							<c:choose>
									<c:when test="${userInfor.userId == 0}">
										<td class="lbl_left"><font color = "red">*</font> パスワード:</td>
											<td align="left">
											<input class="txBox" type="password" name = "password"
											value = "<c:out value = "${userInfor.password}" escapeXml = "true"/>"
											size="15" onfocus="this.style.borderColor='#0066ff';"
											onblur="this.style.borderColor='#aaaaaa';"/>
									</c:when>
									<c:otherwise>
											<input class="txBox" type="hidden" name = "password" 
											value = "<c:out value = "${userInfor.password}" escapeXml = "true"/>"
											size="30" onfocus="this.style.borderColor='#0066ff';"
											onblur="this.style.borderColor='#aaaaaa';" />	
									</c:otherwise>
							</c:choose>
							</tr>
							<tr>
							<c:choose>
								<c:when test="${userInfor.userId == 0}">
									<td class="lbl_left">パスワード（確認）:</td>
									<td align="left">
									<input class="txBox" type="password" name = "endcodepassword"
									value="<c:out value = "${userInfor.encodePassword}" escapeXml = "true"/>"
									size="15" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';"/>			
								</c:when>
							<c:otherwise>
									<input class="txBox" type="hidden" name = "endcodepassword" 
									value = "<c:out value = "${userInfor.encodePassword}" escapeXml = "true"/>"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" />					
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<th align="left" colspan="2"><a href="#"
								onclick="showOrHideJapaneseLevel('mstJapan')">日本語能力</a></th>
						</tr>
					
					
					</table>
					</div>
				</td>
			</tr>
			<!-- Xử lý trình độ tiếng nhật -->
				<tr>
					<td align="left">
					<div id="mstJapan" style="padding-left: 100px; display: none">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
						
						<tr>
						<td class="lbl_left">資格:</td>
						<td align="left">
							<select name="codeLevel">			
								<c:forEach items="${listMstJapans}" var = "mstJapan">
									<option value = "${mstJapan.codeLevel}" ${mstJapan.codeLevel==userInfor.codeLevel?'selected':''}>
									<c:out value = "${mstJapan.nameLevel}" escapeXml = "true"/></option>
								</c:forEach>
							</select>												
						</td>
					</tr>
					<tr>
						<td class="lbl_left">資格交付日:</td>
								<td align="left"><select name = "startDateYear">
										<c:forEach items="${listYears}" var="year">
											<option value="${year}" ${year==userInfor.startDateYear?'selected':'' }>${year}</option>
										</c:forEach>
								</select>年 <select name="startDateMonth">
										<c:forEach items="${listMonths}" var="month">
											<option value="${month}"
												${month==userInfor.startDateMonth?'selected':'' }>${month}</option>
										</c:forEach>
								</select>月 <select name="startDateDay">
										<c:forEach items="${listDays}" var="day">
											<option value="${day}" ${day==userInfor.startDateDay?'selected':'' }>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><select name="endDateYear">
										<c:forEach items="${endListYears}" var="year">
											<option value="${year}" ${year==userInfor.endDateYear?'selected':'' }>${year}</option>
										</c:forEach>
								</select>年 <select name="endDateMonth">
										<c:forEach items="${listMonths}" var="month">
											<option value="${month}"
												${month==userInfor.endDateMonth?'selected':'' }>${month}</option>
										</c:forEach>
								</select>月 <select name="endDateDay">
										<c:forEach items="${listDays}" var="day">
											<option value="${day}" ${day==userInfor.endDateDay?'selected':'' }>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
					<tr>
						<td class="lbl_left">点数: </td>
						<td align="left">
							<input class="txBox" type="text" name = "total" 
							value = "<c:out value = "${userInfor.total}" escapeXml = "true"/>"
							size="5" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<c:choose>
		<c:when test="${userInfor.userId > 0}">
			<c:set var="action"
				value="detailUser.do?userId=${userInfor.userId}" />
		</c:when>
		<c:otherwise>
			<c:set var="action"
				value="listAllUser.do?&type=back" />
		</c:otherwise>
	</c:choose>
	<div style="padding-left:45px;">
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
				<td>
					<input class="btn" type="submit" value="確認" />					
				</td>	
				<td>
					<input class="btn" type="button" value="戻る" 
					onclick="window.location.href = '${action}'" />						
				</td>
		</tr>		
	</table>
	</div>
	<!-- End vung button -->	
</form>
<!-- End vung input -->

<!-- Begin vung footer -->
<jsp:include page="footer.jsp"></jsp:include>
<!-- End vung footer -->
</body>

</html>