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
				<%@ include file="WEB-INF/searchbarGame.jspf"%>
			</div>
		</header>
		<div id="content">
			<div id="scaleline-id"></div>
			<div id="olControlAttribution"></div>
			<div id="mousePosition"></div>


			<c:if test="${not empty typeJeu}">
				<div id="jeuDialogGeneral">
					<div id="dialogGeneralTexte" class="center">
						<table class="maxWidth">
							<tr>
								<td class="align" id="jeuDialogGeneralTableTexte">Cliquer
									sur le bouton ci-dessous pour lancer le jeu!</td>
							</tr>
						</table>
					</div>
					<div id="dialogGeneralBouton">
						<a href="#" class="boutonJeu"
							onclick="jeuEnCours.cycleSuivant();jeuDialogGeneralHide();"
							id="jeuDialogBoutonTexte">Lancer le jeu!</a>
					</div>
				</div>

				<div id="jeuDialogFinal">
					<table class="maxWidth maxHeight align">
						<tr>
							<td>Votre score</td>
						</tr>
						<tr>
							<td id="jeuDialogFinalScore"></td>
						</tr>
						<tr>
							<td>Top 10</td>
						</tr>
						<tr>
							<td><table class="tableResult" id="jeuDialogFinalTopTen"><%@ include file="WEB-INF/topTen.jspf"%></table></td>
						</tr>
						<tr>
							<td class="bleuFonce grosTexte" id="estHighScore"></td>
						</tr>
						<tr>
							<td><input type="text" name="" placeholder="Votre nom (8 caractères)"/></td>
						</tr>
						<tr>
							<td><a href="#" class="boutonJeu"
								onclick="$(location).attr('href','${domain}Game?fini=fini');">Sauvegarder</a></td>
						</tr>
					</table>
				</div>

				<div id="dialogJeu"></div>
				<script type="text/javascript">
					$("#dialogJeu").dialog({
						autoOpen : false,
						width : 320,
						height : 240,
						modal : true,
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
		domain = "${domain}";
		//partie dédié à la création de la carte
		<c:if test="${not empty typeJeu && typeJeu == 3}">
		gestionCarteJeu(-1, 47.7, 7, 3);
		jeuDialogGeneralShow();
		var jeuEnCours = new creeJeu('${domain}',3);
		</c:if>
		<c:if test="${not empty typeJeu && typeJeu == 2}">
		gestionCarteJeu(-1.5, 47.2, 8, 2);
		jeuDialogGeneralShow();
		var jeuEnCours = new creeJeu('${domain}',2);
		</c:if>
		<c:if test="${not empty typeJeu && typeJeu == 1}">
		gestionCarteJeu(-1.55, 47.2, 10, 1);
		jeuDialogGeneralShow();
		var jeuEnCours = new creeJeu('${domain}',1);
		</c:if>
		<c:if test="${empty typeJeu}">
		gestionCarteJeu(-0.6, 46.9, 7, 0);
		</c:if>
		
		var session = "${session}";

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