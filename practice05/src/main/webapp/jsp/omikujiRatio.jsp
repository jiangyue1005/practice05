<%@page import="form.RatioForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
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
<title>割合結果</title>
</head>
<body>
	<font>🌸過去半年の割合です。🌸</font>
	<br>
	<br>

	<table>
		<tr>
			<th>運勢名</th>
			<th>割合</th>
		</tr>

		<logic:iterate id="map" name="RatioForm" property="halfYearRatio">
			<tr>

				<td><bean:write name="map" property="key" /></td>
				<td><bean:write name="map" property="value" />%</td>

			</tr>
		</logic:iterate>
	</table>
	<br>
	<br>
	<font>🌸本日の割合です。🌸</font>
	<table>

		<tr>
			<th>運勢名</th>
			<th>割合</th>

		</tr>

		<logic:iterate id="map" name="RatioForm" property="todayRatio">
			<tr>

				<td><bean:write name="map" property="key" /></td>
				<td><bean:write name="map" property="value" />%</td>

			</tr>
		</logic:iterate>
	</table>
	<br>

	<!-- 画面遷移 -->
	<html:link action="/ReturnAction">誕生日入力に戻る</html:link>
	<br>
	<html:link action="/HalfYearResultAction">誕生日の過去半年の結果を見る</html:link>
</body>
</html>