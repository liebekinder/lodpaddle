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
<!-- <link rel="stylesheet" -->
<!-- 	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" /> -->

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
		$(".accordion").accordion({ heightStyle: "fill" });
		doFlip();
	});
</script>

</head>
<body onload="pageLoaded('${domain}');">
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


			<div id="contentContainer" style="visibility: hidden">

			
				<div class="flipLoisir flipItem" style="visibility: hidden;">
					<div class="title">Loisirs</div>
					<div class="contenuFlip accordion" id="loisirContenu"><%@ include
							file="WEB-INF/afficherContenuLoisir.jspf"%></div>
				</div>
				<div class="flipCulture flipItem" style="visibility: hidden;">
					<div class="title">Culture</div>
					<div class="contenuFlip accordion" id="cultureContenu"><%@ include
							file="WEB-INF/afficherContenuCulture.jspf"%></div>
				</div>
				<div class="flipVille flipItem" style="visibility: hidden;">
					<div class="title">Ville</div>
					<div class="contenuFlip accordion" id="villeContenu"><%@ include
							file="WEB-INF/afficherContenuVille.jspf"%></div>
				</div>
				<div class="flipService flipItem" style="visibility: hidden;">
					<div class="title">Service</div>
					<div class="contenuFlip accordion" id="serviceContenu"><%@ include
							file="WEB-INF/afficherContenuService.jspf"%></div>
				</div>
				<div class="flipVisite flipItem" style="visibility: hidden;">
					<div class="title">Visite</div>
					<div class="contenuFlip accordion" id="visiteContenu"><%@ include
							file="WEB-INF/afficherContenuVisite.jspf"%></div>
				</div>
				<div class="flipTransport flipItem" style="visibility: hidden;">
					<div class="title">Transport</div>
					<div class="contenuFlip accordion" id="transportContenu"><%@ include
							file="WEB-INF/afficherContenuTransport.jspf"%></div>
				</div>
			</div>
			
			<div id="cadreInfo">
	
			</div>
			
			<div id="dialog">Blablablabl
			b<p>nihi'tiohoihgigg</p></div>
			<script type="text/javascript">
				$( "#dialog" ).dialog({
					autoOpen: false,
					width: 800,
					height: 480,
					modal: true
					});
			</script>
			
			
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
		
		<c:if test="${not empty position}">
			ajoutPoint(villeSearch, ${position.longitude}, ${position.latitude},'${domain}media/marqueur.png');
		</c:if>
		
		<c:if test="${not empty themeCulture}"> 
			<c:forEach var="item" items="${themeCulture.listeSousTheme}">
				<c:forEach var="link" items="${item.entrees}">
					ajoutPoint(cultureLayer,${link.position.longitude}, ${link.position.latitude}, '${item.marker}', "${link.entreeUrl}");
				</c:forEach>
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty themeLoisir}"> 
			<c:forEach var="item" items="${themeLoisir.listeSousTheme}">
				<c:forEach var="link" items="${item.entrees}">
					ajoutPoint(loisirLayer,${link.position.longitude}, ${link.position.latitude}, '${item.marker}', "${link.entreeUrl}");
				</c:forEach>
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty themeVille}"> 
			<c:forEach var="item" items="${themeVille.listeSousTheme}">
				<c:forEach var="link" items="${item.entrees}">
					ajoutPoint(villeLayer,${link.position.longitude}, ${link.position.latitude}, '${item.marker}', "${link.entreeUrl}");
				</c:forEach>
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty themeVisite}"> 
			<c:forEach var="item" items="${themeVisite.listeSousTheme}">
				<c:forEach var="link" items="${item.entrees}">
					ajoutPoint(visiteLayer,${link.position.longitude}, ${link.position.latitude}, '${item.marker}', "${link.entreeUrl}");
				</c:forEach>
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty themeTransport}"> 
			<c:forEach var="item" items="${themeTransport.listeSousTheme}">
				<c:forEach var="link" items="${item.entrees}">
					ajoutPoint(transportLayer,${link.position.longitude}, ${link.position.latitude}, '${item.marker}', "${link.entreeUrl}");
				</c:forEach>
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty themeService}"> 
			<c:forEach var="item" items="${themeService.listeSousTheme}">
				<c:forEach var="link" items="${item.entrees}">
					<c:if test="${link.position.longitude > -100}"> 
						ajoutPoint(serviceLayer,${link.position.longitude}, ${link.position.latitude}, '${item.marker}', "${link.entreeUrl}");
					</c:if>
				</c:forEach>
			</c:forEach>
		</c:if>
		
	</script>

</body>
</html>