<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script type="text/javascript"
	src="${domain}javascript/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript"
	src="${domain}javascript/jquery.mCustomScrollbar.js"></script>

</head>
<body onload="pageLoaded('${domain}');">

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


	<c:if test="${not empty typeJeu && typeJeu == 3}">
	<div id="dialogLance">Cliquez sur le bouton ci-dessous dès que vous êtes prêt!</div>
		<script type="text/javascript">
			$("#dialogLance").dialog({
				autoOpen:false,
				width : 320,
				height : 240,
				modal : true,
				position: { my: "left center", at: "left center", of: window },
				close: function( event, ui ) {
					jeuEnCours.cycleSuivant();
				},
				buttons : {
					"Je suis prêt!" : function() {
						$(this).dialog("close");
					}
				}
			});
		</script>
	<div id="dialogScore"></div>
		<script type="text/javascript">
			$("#dialogScore").dialog({
				autoOpen:false,
				width : 320,
				height : 240,
				modal : true,
				position: { my: "left center", at: "left center", of: window },
				close: function( event, ui ) {
					jeuEnCours.cycleSuivant();
				},
				buttons : {
					"Ville suivante!" : function() {
						$(this).dialog("close");
					}
				}
			});
		</script>
		<div id="dialogDernierScore"></div>
		<script type="text/javascript">
			$("#dialogDernierScore").dialog({
				autoOpen:false,
				width : 320,
				height : 240,
				modal : true,
				position: { my: "left center", at: "left center", of: window },
				close: function( event, ui ) {
					jeuEnCours.cycleSuivant();
				},
				buttons : {
					"Résultats!" : function() {
						$(this).dialog("close");
					}
				}
			});
		</script>
		<div id="dialogFinal"></div>
		<script type="text/javascript">
			$("#dialogFinal").dialog({
				autoOpen:false,
				width : 320,
				height : 240,
				modal : true,
				close: function( event, ui ) {
					
				},
				buttons : {
					"Quitter" : function() {
						$(this).dialog("close");
					}
				}
			});
		</script>
	</c:if>

		</div>

		<footer>
			<div id="footerContainer">
				<c:if test="${empty jeuEnCours}">
					<div id="widgetZone" class="specificScroll">
						<%@ include file="WEB-INF/afficherWidget.jspf"%>
					</div>
				</c:if>
				<c:if test="${not empty jeuEnCours}">
					<%@ include file="WEB-INF/barreJeu.jspf"%>
				</c:if>
			</div>
		</footer>

	</div>

	<script type="text/javascript">
	
		//partie dédié à la création de la cart
		<c:if test="${not empty typeJeu && typeJeu == 3}">
		gestionCarteJeu(-1, 47.7, 7);
		$('#dialogLance').dialog('open');
		var jeuEnCours = new creeJeu('${domain}');
		</c:if>
		<c:if test="${not empty typeJeu && typeJeu == 2}">
		gestionCarteJeu(-1.5, 47.2, 8);
		$('#dialogLance').dialog('open');
		var jeuEnCours = new creeJeu('${domain}');
		</c:if>
		<c:if test="${not empty typeJeu && typeJeu == 1}">
		gestionCarteJeu(-1.55, 47.2, 10);
		$('#dialogLance').dialog('open');
		var jeuEnCours = new creeJeu('${domain}');
		</c:if>
		<c:if test="${empty typeJeu}">
		gestionCarteJeu(-0.6, 46.9, 7);
		</c:if>

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
				//on attends que la page soit complètement chargée pour lancer le jeu
				//différent de la navigation!!
				
			});
		})(jQuery);
	</script>
</body>