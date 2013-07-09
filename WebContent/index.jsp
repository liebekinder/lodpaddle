<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lodpaddle - balades en terres sémantiques</title>
<link rel="stylesheet" type="text/css" href="cssStyle/principal.css" />
<link rel="shortcut icon" type="image/ico" href="media/favicon.ico" />
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div id="principal">
		<div id="header"></div>
		<div id="body">
			<div id="map"></div>
			<div id="searchBar"></div>
			<c:out value="blabla" />
			<c:forEach var="i" begin="1" end="50">
				<p>
					Item
					<c:out value="${i}" />
				</p>
			</c:forEach>

		</div>
		<div id="footer"></div>
	</div>
</body>
</html>