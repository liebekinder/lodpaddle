<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lodpaddle - balades en terres sémantiques</title>
<link rel="stylesheet" type="text/css" href="cssStyle/principal.css" />
<link type="text/css" rel="stylesheet" href="cssStyle/Header.css" />
<link type="text/css" rel="stylesheet" href="cssStyle/searchbar.css" />
<link type="text/css" rel="stylesheet" href="cssStyle/Widget.css" />
<link rel="shortcut icon" type="image/ico" href="media/favicon.ico" />
<script type="text/javascript" src="javascript/principal.js"></script>
<script src="http://openlayers.org/api/OpenLayers.js"></script>
<script type="text/javascript" src="javascript/jquery.min.js"></script>
<script type="text/javascript" src="javascript/blocksit.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#widgets').BlocksIt({
			numOfCol : 6,
			offsetX : 4,
			offsetY : 4,
			blockElement : '.grid'
		});
	});
</script>
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div id="principal">
		<%@include file="header.jspf"%>

		<div id="body">
			<%@ include file="map.jspf"%>
			<%@include file="searchbar.jspf"%>
			<%@include file="WEB-INF/afficherWidget.jspf"%>

		</div>

		<%@include file="footer.jspf"%>
	</div>

</body>
</html>