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

<script type="text/javascript" src="${domain}javascript/principal.js"></script>
<script src="${domain}OpenLayers/OpenLayers.debug.js"></script>
<script type="text/javascript" src="${domain}javascript/jquery.min.js"></script>
<script type="text/javascript" src="${domain}javascript/blocksit.min.js"></script>
<script type="text/javascript"
	src="${domain}javascript/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript"
	src="${domain}javascript/jquery-css-transform.js"></script>
<script type="text/javascript" src="${domain}javascript/rotate3Di.js"></script>


</head>
<body onload="pageLoaded('${domain}','${idsForChange}');">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<div id="AllScreenDiv">

		<header>
			<div id="headerContainer">
				<%@ include file="WEB-INF/searchbar.jspf"%>
			</div>
		</header>
		<div id="content">
			<div id="scaleline-id"></div>
			<div id="olControlAttribution"></div>
			<div id="mousePosition"></div>
			<div id="contentContainer">
				<div id="oneContent">
					<p>Ceci est le contenu</p>
				</div>
				<div id="anotherContent">
					<p>Ceci est le contenu</p>
				</div>
				<div id="lastContent">
					<p>Ceci est le contenu</p>
				</div>
			</div>
		</div>

		<footer>
			<div id="footerContainer">
				<div id="staticTheme"><%@ include
						file="WEB-INF/themeZone.jspf"%></div>
				<div id="widgetZone">
					<%@ include file="WEB-INF/afficherWidget.jspf"%>
					<!-- ici je peux ajouter le if pour faire afficher widget ou autre chose -->
				</div>
			</div>
		</footer>

	</div>

	<script type="text/javascript">
		//init();
	</script>

</body>
</html>