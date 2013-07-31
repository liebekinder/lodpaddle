<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lodpaddle - balades en terres sémantiques</title>


<link rel="stylesheet"
	href="${domain}cssStyle/custom-theme/jquery-ui-1.10.3.custom.css">
<link rel="shortcut icon" type="image/ico"
	href="${domain}media/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${domain}cssStyle/principal.css" />
<link rel="stylesheet" type="text/css"
	href="${domain}cssStyle/jquery.mCustomScrollbar.css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script type="text/javascript" src="${domain}javascript/principal.js"></script>
<script src="${domain}OpenLayers/OpenLayers.debug.js"></script>
<script type="text/javascript" src="${domain}javascript/jquery.min.js"></script>
<script type="text/javascript" src="${domain}javascript/blocksit.min.js"></script>
<script type="text/javascript"
	src="${domain}javascript/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript"
	src="${domain}javascript/jquery-css-transform.js"></script>
<script type="text/javascript" src="${domain}javascript/rotate3Di.js"></script>
<script type="text/javascript"
	src="${domain}javascript/jquery.mCustomScrollbar.js"></script>

<script>
	$(function() {
		$("#testflipbla").accordion();
	});
</script>

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
				<div class="loisirWidget flipItem" style="visibility: hidden;">
					<div class="title">Loisir</div>
					<div class="contenuFlip" id="testflipbla"><%@ include
							file="WEB-INF/afficherContenu.jspf"%></div>
				</div>
				<div class="cultureWidget flipItem" style="visibility: hidden;">
					<div class="title">Culture</div>
					<div class="contenuFlip"><%@ include
							file="WEB-INF/afficherContenuCulture.jspf"%></div>
				</div>
				<div class="villeWidget flipItem" style="visibility: hidden;">
					<div class="title">Ville</div>
					<div class="contenuFlip"><%@ include
							file="WEB-INF/afficherContenuVille.jspf"%></div>
				</div>
				<div class="serviceWidget flipItem" style="visibility: hidden;">
					<div class="title">Service</div>
					<div class="contenuFlip"><%@ include
							file="WEB-INF/afficherContenuService.jspf"%></div>
				</div>
				<div class="visiteWidget flipItem" style="visibility: hidden;">
					<div class="title">Visite</div>
					<div class="contenuFlip"><%@ include
							file="WEB-INF/afficherContenuVisite.jspf"%></div>
				</div>
				<div class="transportWidget flipItem" style="visibility: hidden;">
					<div class="title">Transport</div>
					<div class="contenuFlip"><%@ include
							file="WEB-INF/afficherContenuTransport.jspf"%></div>
				</div>
			</div>
		</div>

		<footer>
			<div id="footerContainer">
				<div id="staticTheme"><%@ include
						file="WEB-INF/themeZone.jspf"%></div>
				<div id="widgetZone" class="specificScroll">
					<%@ include file="WEB-INF/afficherWidget.jspf"%>
					<!-- ici je peux ajouter le if pour faire afficher widget ou autre chose -->
				</div>
			</div>
		</footer>

	</div>

	<script type="text/javascript">
		//partie dédié à la création de la carte

		gestionCarte();		

		(function($) {
			$(window).load(function() {
				$("#widgetZone").mCustomScrollbar({
					scrollButtons : {
						enable : true
					},
					horizontalScroll : true,
					advanced : {
						autoExpandHorizontalScroll : true,
						updateOnContentResize : false
					}
				});
			});
		})(jQuery);
	</script>

</body>
</html>