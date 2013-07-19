<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lodpaddle - balades en terres sémantiques</title>

<link rel="stylesheet" type="text/css" href="${domain}cssStyle/principal.css" />
<link type="text/css" rel="stylesheet" href="${domain}cssStyle/Header.css" />
<link type="text/css" rel="stylesheet" href="${domain}cssStyle/searchbar.css" />
<link type="text/css" rel="stylesheet" href="${domain}cssStyle/Widget.css" />
<link rel="stylesheet" href="${domain}cssStyle/ui-lightness/jquery-ui-1.10.3.custom.css">

<link rel="shortcut icon" type="image/ico" href="${domain}media/favicon.ico" />

<script type="text/javascript" src="${domain}javascript/principal.js"></script>
<script src="${domain}OpenLayers/OpenLayers.debug.js"></script>
<script type="text/javascript" src="${domain}javascript/jquery.min.js"></script>
<script type="text/javascript" src="${domain}javascript/blocksit.min.js"></script>
<script type="text/javascript" src="${domain}javascript/jquery-ui-1.10.3.custom.js"></script>
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
<body onload="pageLoaded('${domain}');">
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