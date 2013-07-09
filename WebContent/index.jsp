<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lodpaddle - balades en terres sémantiques</title>
<link rel="stylesheet" type="text/css" href="cssStyle/principal.css" />
<link type="text/css" rel="stylesheet" href="cssStyle/Header.css" />
<link rel="shortcut icon" type="image/ico" href="media/favicon.ico" />
<script type="text/javascript" src="javascript/principal.js"></script>
<script src="http://openlayers.org/api/OpenLayers.js"></script>
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div id="principal">
		<div id="header"><%@include file="Header.jsp" %> </div>
		<div id="body">
			<%@ include file="map.jspf" %>
			<div id="searchBar"></div>
			<c:out value="blabla" />
			<c:forEach var="i" begin="1" end="10">
				<p>
					src Ligne
					<c:out value="${i}" />
				</p>
			</c:forEach>

		</div>
		<div id="footer"><%@include file="Footer.jsp" %></div>
	</div>
</body>
</html>