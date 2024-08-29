<%@ page language="java" import="kadai4.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="omikuji.Omikuji"%>
<%@page import="dao.OmikujiDAO"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
//リクエストスコープから値を取得
Omikuji omikujiDao = (Omikuji) request.getAttribute("omikuji");
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
<title>⛩️占い結果⛩️</title>
</head>
<body>


	<!-- 結果表示 -->
	<!--運勢-->
	🌸今日の運勢は
	<font><%=omikujiDao.getUnsei()%></font> です。🌸
	<br>
	<br>
	<table>
		<tr>
			<td>願い事</td>
			<td><%=omikujiDao.getNegaigoto()%></td>
		</tr>
		<tr>
			<td>商い</td>
			<td><%=omikujiDao.getAkinai()%></td>
		</tr>
		<tr>
			<td>学問</td>
			<td><%=omikujiDao.getGakumon()%></td>
		</tr>
	</table>

	<!-- 画面遷移 -->
	<br>
	<html:link action="/ReturnAction">誕生日入力に戻る</html:link>
	<br>
	<html:link action="/OmikujiRatioAction">全体の過去半年と本日の占い結果の割合</html:link>
	<br>
	<html:link action="/HalfYearResultAction">誕生日の過去半年の結果を見る</html:link>


</body>
</html>