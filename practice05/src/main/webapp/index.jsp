<%@page language = "java" contentType = "text/html; charset = UTF-8"
	pageEncoding = "UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html>
	<head>
	<style type="text/css">
body {
	background-color: #E6E6F2;
	margin-bottom: 1px;
}

form {
	font-size: 1.2em;
	text-align: center;
}

</style>
	<title>⛩️占い⛩️</title>
	</head>
	<font color="red"><html:errors/></font>
		<body>
			<html:form action = "/BirthdayAction" method = "post">
			🌸本日の運勢を占いしましょう🌸
			<br>
			<br>
				誕生日を入力してください（例:20240101）：
				<br> <html:text styleClass = "birthday" property = "birthday" />
				<br> <html:submit styleClass = "success" property = "submit" value = "おみくじを引く" />
			</html:form>
	</form>
		</body>
</html>

