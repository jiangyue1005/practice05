<%@page import="form.HalfYearResultForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
//リクエストスコープから値を取得
String birthday = (String) request.getAttribute("birthday");
%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
body {
	background-color: #E6E6F2;
	margin-bottom: 1px;
}

font {
	font-size: 1.2em;
	text-align: center;
}

.back {
	text-align: center;
}
</style>
<meta charset="UTF-8">
<title>過去半年の占い結果</title>
</head>
<body>
	🌸誕生日
	<font><%=birthday%></font> 過去半年の占い結果です。🌸
	<br>
	<br>
	<table>
		<tr>
			<th>占い日</th>
			<th>運勢名</th>
			<th>願い事</th>
			<th>商い</th>
			<th>学問</th>
		</tr>

		<logic:iterate id="data" name="HalfYearResultForm"
			property="halfYearResult">
			<tr>
				<td><bean:write name="data" property="uranaiDate" /></td>
				<td><bean:write name="data" property="unsei" /></td>
				<td><bean:write name="data" property="negaigoto" /></td>
				<td><bean:write name="data" property="akinai" /></td>
				<td><bean:write name="data" property="gakumon" /></td>
			</tr>
		</logic:iterate>
	</table>

	<br>
	<html:link action="/ReturnAction">誕生日入力に戻る</html:link>
	<br>
	<html:link action="/OmikujiRatioAction">全体の過去半年と本日の占い結果の割合</html:link>
</body>
</html>