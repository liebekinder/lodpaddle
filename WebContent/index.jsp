<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lodpaddle - balades en terres sémantiques</title>


<link rel="stylesheet"
	href="${domain}cssStyle/ui-lightness/jquery-ui-1.10.3.custom.css">
<link rel="shortcut icon" type="image/ico"
	href="${domain}media/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${domain}cssStyle/principal.css" />


</head>
<body onload="doFlip('${idsForChange}')">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<div id="AllScreenDiv">

		<header>
			<div id="headerContainer">
				<p>ceci est le header</p>
			</div>
		</header>
		<div id="content">
			<div id="scaleline-id"></div>
			<div id="olControlAttribution"></div>
			<div id="contentContainer">
				<p>Ceci est le contenu</p>
			</div>
		</div>

		<footer>
			<div id="footerContainer">
				<div id="staticTheme"></div>
				<div id="widgetZone"></div>
			</div>
		</footer>

	</div>

	<!-- Javascript placed at the end of the document so the pages load faster -->

	<script type="text/javascript" src="${domain}javascript/principal.js"></script>
	<script src="${domain}OpenLayers/OpenLayers.debug.js"></script>
	<script type="text/javascript" src="${domain}javascript/jquery.min.js"></script>
	<script type="text/javascript"
		src="${domain}javascript/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript"
		src="${domain}javascript/jquery-css-transform.js"></script>
	<script type="text/javascript" src="${domain}javascript/rotate3Di.js"></script>
	<script type="text/javascript">
		init();
	</script>

</body>
</html>