/**
 * main javascript file
 */

function pageLoaded(domainPath) {
	domain = domainPath;
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

	$('#accesJeu').hover(function() {
		$(this).stop();
		$(this).animate({
			right : "-5px"
		}, {
			duration : 300,
			easing : "linear",
		});
	}, function() {
		$(this).stop();
		$(this).animate({
			right : "-162px"
		}, {
			duration : 300,
			easing : "linear",
		});
	});

	$("#dialogMentionLegale").dialog({
		autoOpen : false,
		title : "Mentions légales",
		width : 800,
		height : 480,
		modal : true
	});

	$("#dialogContact").dialog({
		autoOpen : false,
		title : "Le projet Lodpaddle",
		width : 800,
		height : 480,
		modal : true
	});
}

function cacheAccesJeu() {
	$('#accesJeu').hide();
}

function doFlip() {
	$('.fliploisir').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb1,
			complete : flipOver(1)
		});
	});

	$('.flipculture').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb2,
			complete : flipOver(2)
		});
	});

	$('.flipville').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb3,
			complete : flipOver(3)
		});
	});

	$('.flipservice').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb4,
			complete : flipOver(4)
		});
	});

	$('.flipvisite').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb5,
			complete : flipOver(5)
		});
	});

	$('.fliptransport').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb6,
			complete : flipOver(6)
		});
	});

}

function flipOver(cat) {
	// cette fonction est appelée à la fin de l'animation
	// on s'en servira notemment pour afficher les infos sur la carte
	// cat = 1 => loisir
	// cat = 2 => culture
	// cat = 3 => ville
	// cat = 4 => service
	// cat = 5 => visite
	// cat = 6 => transport

}

function mySideChangeb1(front) {
	ajoutLayer(loisirLayer);
	$('#contentContainer').css('visibility', 'visible');
	$('.flipCulture').css('visibility', 'hidden');
	$('.flipLoisir').css('visibility', 'visible');
	$('.flipTransport').css('visibility', 'hidden');
	$('.flipVille').css('visibility', 'hidden');
	$('.flipService').css('visibility', 'hidden');
	$('.flipVisite').css('visibility', 'hidden');
}

function mySideChangeb2(front) {
	ajoutLayer(cultureLayer);
	$('#contentContainer').css('visibility', 'visible');
	$('.flipCulture').css('visibility', 'visible');
	$('.flipLoisir').css('visibility', 'hidden');
	$('.flipTransport').css('visibility', 'hidden');
	$('.flipVille').css('visibility', 'hidden');
	$('.flipService').css('visibility', 'hidden');
	$('.flipVisite').css('visibility', 'hidden');

}

function mySideChangeb3(front) {
	ajoutLayer(villeLayer);
	$('#contentContainer').css('visibility', 'visible');
	$('.flipCulture').css('visibility', 'hidden');
	$('.flipLoisir').css('visibility', 'hidden');
	$('.flipTransport').css('visibility', 'hidden');
	$('.flipVille').css('visibility', 'visible');
	$('.flipService').css('visibility', 'hidden');
	$('.flipVisite').css('visibility', 'hidden');

}

function mySideChangeb4(front) {
	ajoutLayer(serviceLayer);
	$('#contentContainer').css('visibility', 'visible');
	$('.flipCulture').css('visibility', 'hidden');
	$('.flipLoisir').css('visibility', 'hidden');
	$('.flipTransport').css('visibility', 'hidden');
	$('.flipVille').css('visibility', 'hidden');
	$('.flipService').css('visibility', 'visible');
	$('.flipVisite').css('visibility', 'hidden');
}

function mySideChangeb5(front) {
	ajoutLayer(visiteLayer);
	$('#contentContainer').css('visibility', 'visible');
	$('.flipCulture').css('visibility', 'hidden');
	$('.flipLoisir').css('visibility', 'hidden');
	$('.flipTransport').css('visibility', 'hidden');
	$('.flipVille').css('visibility', 'hidden');
	$('.flipService').css('visibility', 'hidden');
	$('.flipVisite').css('visibility', 'visible');
}

function mySideChangeb6(front) {
	ajoutLayer(transportLayer);
	$('#contentContainer').css('visibility', 'visible');
	$('.flipCulture').css('visibility', 'hidden');
	$('.flipLoisir').css('visibility', 'hidden');
	$('.flipTransport').css('visibility', 'visible');
	$('.flipVille').css('visibility', 'hidden');
	$('.flipService').css('visibility', 'hidden');
	$('.flipVisite').css('visibility', 'hidden');
}

function geoloc(domain) {
	if (navigator.geolocation) {

		// Fonction de callback en cas de succès
		function affichePosition(position) {

			var lat = position.coords.latitude;
			var lon = position.coords.longitude;
			lanceRecherche(domain, lon, lat);

		}

		// Fonction de callback en cas d’erreur
		function erreurPosition(error) {
			var info = "Erreur lors de la géolocalisation : ";
			switch (error.code) {
			case error.TIMEOUT:
				info += "Timeout !";
				break;
			case error.PERMISSION_DENIED:
				info += "Vous n’avez pas donné la permission";
				break;
			case error.POSITION_UNAVAILABLE:
				info += "La position n’a pu être déterminée";
				break;
			case error.UNKNOWN_ERROR:
				info += "Erreur inconnue";
				break;
			}
			alert("échec de la géolocalisation! Erreur: " + info);
		}

		navigator.geolocation.getCurrentPosition(affichePosition,
				erreurPosition);

	} else {

		alert("Ce navigateur ne supporte pas la géolocalisation");

	}
}

function lanceRecherche(domain, lon, lat) {
	document.location.href = domain + "?lon=" + lon + "&lat=" + lat;
}

function del(vectorLayer) {
	vectorLayer.removeAllFeatures();
}

function addPoint(vectorLayer, lat, lon) {

	var point = new OpenLayers.Geometry.Point(lon, lat);
	point.transform(new OpenLayers.Projection("EPSG:4326"),
			new OpenLayers.Projection("EPSG:900913"));

	var features = new OpenLayers.Feature.Vector(point, {
		type : 0
	});

	vectorLayer.addFeatures(features);
	alert(point.transform(new OpenLayers.Projection("EPSG:4326"),
			new OpenLayers.Projection("EPSG:900913")).x
			+ "  "
			+ point.transform(new OpenLayers.Projection("EPSG:4326"),
					new OpenLayers.Projection("EPSG:900913")).y);
}

function vectorLayerCreation() {

	return new OpenLayers.Layer.Vector("POI");
}

/**
 * Cette fonction initialise la carte
 * 
 * @returns la carte de type Openlayers.Map
 */
function mapCreation() {

	map = new OpenLayers.Map("content", {
		controls : [ new OpenLayers.Control.Navigation(),
		// new OpenLayers.Control.PanZoomBar(),
		// new OpenLayers.Control.MousePosition({
		// div: document.getElementById("mousePosition")
		// }),
		// new OpenLayers.Control.ScaleLine({
		// div: document.getElementById("scaleline-id"),
		// geodesic : true
		// }),
//		new OpenLayers.Control.LayerSwitcher(),
//				new OpenLayers.Control.Attribution({
//					div : document.getElementById("olControlAttribution")
//				}) 
	],
		maxExtent : new OpenLayers.Bounds(-20037508.34, -20037508.34,
				20037508.34, 20037508.34),
		maxResolution : 'auto',
		numZoomLevels : 19,
		units : 'm',
		projection : new OpenLayers.Projection("EPSG:900913"),
		// projection : new OpenLayers.Projection("EPSG:4326"),
		displayProjection : new OpenLayers.Projection("EPSG:4326")
	});

	var osm = new OpenLayers.Layer.OSM("OpenStreetMap");
	map.addLayer(osm);

	var mapquest = new OpenLayers.Layer.OSM('mapquest', [
			"http://otile1.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg",
			"http://otile2.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg",
			"http://otile3.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg",
			"http://otile4.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg", ], {
		tileOptions : {
			crossOriginKeyword : null
		}
	});
	map.addLayer(mapquest);

	var osmNoLayer = new OpenLayers.Layer.OSM(
			'osm-no-labels',
			[
					"http://a.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://b.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://c.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://d.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://e.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://f.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png" ],
			{
				tileOptions : {
					crossOriginKeyword : null
				}
			});
	map.addLayer(osmNoLayer);

	var center = new OpenLayers.LonLat(-0.353394, 47.546711);
	var projFrom = new OpenLayers.Projection("EPSG:4326");
	var projTo = new OpenLayers.Projection("EPSG:900913");

	if (!map.getCenter()) {
		map.setCenter(center.transform(projFrom, projTo), 7);
		// map.setCenter(center.transform(projFrom, projTo), 2);
	}

	return map;

}

function ajoutPoint(layer, lon, lat, iconPath, id) {

	var projFrom = new OpenLayers.Projection("EPSG:4326");
	var projTo = new OpenLayers.Projection("EPSG:900913");
	var point = new OpenLayers.Geometry.Point(lon, lat).transform(projFrom,
			projTo);

	var markerStyle = {
		externalGraphic : iconPath,
		graphicWidth : 28,
		graphicHeight : 42,
		graphicXOffset : -14,
		graphicYOffset : -42
	};
	var feature = new OpenLayers.Feature.Vector(point, {
		title : id,
		clickable : 'off'
	}, markerStyle);
	layer.addFeatures([ feature ]);
}

function ajoutLayer(layer) {
	cadreInfoHide();
	enleveToutLayer();
	map.addLayer(layer);
	selector = new OpenLayers.Control.SelectFeature(layer, {
		click : true,
		autoActivate : true
	});
	var newZoom = layer.getDataExtent();
	if (newZoom != null) {
		map.zoomToExtent(newZoom);
		// besoin de corriger le zoom, des points peuvent apparaitre derriere
		// les cadres
		map.zoomOut();
	}
	map.addControl(selector);
}

function enleveToutLayer() {

	// alert(map.getLayersByName("serviceLayer").length);
	// if (map.getLayersByName("villeSearch").length != 0)
	// map.removeLayer(map.getLayersByName("villeSearch")[0]);
	if (map.getLayersByName("loisirLayer").length != 0)
		map.removeLayer(map.getLayersByName("loisirLayer")[0]);
	if (map.getLayersByName("cultureLayer").length != 0)
		map.removeLayer(map.getLayersByName("cultureLayer")[0]);
	if (map.getLayersByName("villeLayer").length != 0)
		map.removeLayer(map.getLayersByName("villeLayer")[0]);
	if (map.getLayersByName("serviceLayer").length != 0)
		map.removeLayer(map.getLayersByName("serviceLayer")[0]);
	if (map.getLayersByName("transportLayer").length != 0)
		map.removeLayer(map.getLayersByName("transportLayer")[0]);
	if (map.getLayersByName("visiteLayer").length != 0)
		map.removeLayer(map.getLayersByName("visiteLayer")[0]);
}

function getListURl(ressource) {
	// pos = ressource.lastIndexOf("/");
	// nom = ressource.substring(pos+1);
	nom = ressource;
	listNom = new Array();
	listUrl = new Array();
	// le nom ici correspond au nom d'une page puisque issue de wikipedia
	api = "http://fr.wikipedia.org/w/api.php";

	$.getJSON(api + "?callback=?", {
		action : "query",
		titles : nom,
		prop : "images",
		format : "json"
	}, function(json, etat, arg3) {
		$.each(json.query.pages, function(index, element) {
			$.each(element.images, function(index, element) {
				listNom.push(element.title);
			});
		});
	});

	$.each(listNom, function(index, element) {
		$.getJSON(api + "?callback=?", {
			action : "query",
			titles : element,
			prop : "imageinfo",
			iiprop : "url",
			format : "json"
		}, function(json, etat, arg3) {
			$.each(json.query.pages, function(index, element) {
				listUrl.push(element.imageinfo[0].url);
			});
		});
	});

	alert(listUrl.length);
}

function afficheCadreInfo(ressource) {
	if (ressource
			.indexOf("http://lodpaddle.univ-nantes.fr/Communes_geolocalises/") != -1
			|| ressource
					.indexOf("http://lodpaddle.univ-nantes.fr/Petites_cites_de_caractere/") != -1) {
		// Cas particulier des villes, on ne veut pas du cadre info qui serait
		// vide:
		// seulement un lien vers la recherche
		$.ajax({
			type : "POST",
			url : domain + "AjaxInsee",
			dataType : "html",
			data : "ressource=" + encodeURI(ressource),
			success : function(msg) {
				$('#cadreInfo').html(msg);
				cadreInfoShow();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$('#cadreInfo').html(
						"<p>Impossible de récupérer les données!</p>");
			}
		});
	} else {
		$.ajax({
			type : "POST",
			url : domain + "AjaxInformation",
			dataType : "html",
			data : "ressource=" + encodeURI(ressource),
			success : function(msg) {
				$('#cadreInfo').html(msg);
				cadreInfoShow();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$('#cadreInfo').html(
						"<p>Impossible de récupérer les données!</p>");
			}
		});
	}
}

function afficheCadreInfoPlus(ressource) {
	$.ajax({
		type : "POST",
		url : domain + "PlusInformation",
		dataType : "html",
		data : "ressource=" + encodeURI(ressource),
		success : function(msg) {
			$('#dialog').html(msg);
			$("#dialog").dialog("option", "title", ressource);
			$('#dialog').dialog('open');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('#dialog').html("<p>Impossible de récupérer les données!</p>");
			$("#dialog").dialog("option", "title", ressource);
			$('#dialog').dialog('open');
		}
	});
}

function cadreInfoShow() {
	$("#cadreInfo").show("slide", {
		direction : "right"
	}, 1000, function() {
	});
}

function cadreInfoHide() {
	$("#cadreInfo").hide("slide", {
		direction : "right"
	}, 1000, function() {
	});
}

function retourJeuShow() {
	$("#retourJeu").show("slide", {
		direction : "right"
	}, 1000, function() {
	});
}

function retourJeuHide() {
	$("#retourJeu").hide("slide", {
		direction : "right"
	}, 1000, function() {
	});
}

function selectionFeature(layer, feature) {
	deselectionne(layer);
	feature.style.graphicWidth = 42;
	feature.style.graphicHeight = 63;
	feature.style.graphicXOffset = -21;
	feature.style.graphicYOffset = -63;
	layer.drawFeature(feature);
	afficheCadreInfo(feature.attributes.title);
	// selectionLigne(layer.name,feature.attributes.title);
}

function selectionneLigne(titreFeature) {

	afficheCadreInfo(titreFeature);
	// alert(titreFeature);
	var layer = map.layers[5];
	var longueur = layer.features.length;
	var i = 0;
	var trouve = false;
	while (i < longueur && !trouve) {
		if (layer.features[i].attributes.title == titreFeature) {
			trouve = true;
			// layer.eventListeners.select(layer.features[i]);
			// SelectFeature.select(layer.features[i]);
			selector.select(layer.features[i]);
		} else {
			i++;
		}
	}
}

function selectionLigne(nomLayer, titreFeature) {

	document.getElementById(titreFeature).style.color = "red";
	document.getElementById(titreFeature).style.fontWeight = "bold";

}

function deselectionne(layer) {
	// on regarde toutes les features et on applique le syle neutre
	for ( var i = 0; i < layer.features.length; i++) {
		deselectionneFeature(layer, layer.features[i]);
	}
}

function deselectionneFeature(layer, feature) {
	feature.style.graphicWidth = 28;
	feature.style.graphicHeight = 42;
	feature.style.graphicXOffset = -14;
	feature.style.graphicYOffset = -42;
	layer.drawFeature(feature);
}

function gestionCarte() {

	map = mapCreation();

	villeSearch = new OpenLayers.Layer.Vector("villeSearch");
	loisirLayer = new OpenLayers.Layer.Vector("loisirLayer", {
		eventListeners : {
			'featureselected' : function(evt) {
				selectionFeature(loisirLayer, evt.feature);
			}
		}
	});
	cultureLayer = new OpenLayers.Layer.Vector("cultureLayer", {
		eventListeners : {
			'featureselected' : function(evt) {
				selectionFeature(cultureLayer, evt.feature);
			}
		}
	});
	villeLayer = new OpenLayers.Layer.Vector("villeLayer", {
		eventListeners : {
			'featureselected' : function(evt) {
				selectionFeature(villeLayer, evt.feature);
			}
		}
	});
	serviceLayer = new OpenLayers.Layer.Vector("serviceLayer", {
		eventListeners : {
			'featureselected' : function(evt) {
				selectionFeature(serviceLayer, evt.feature);
			}
		}
	});
	transportLayer = new OpenLayers.Layer.Vector("transportLayer", {
		eventListeners : {
			'featureselected' : function(evt) {
				selectionFeature(transportLayer, evt.feature);
			}
		}
	});
	visiteLayer = new OpenLayers.Layer.Vector("visiteLayer", {
		eventListeners : {
			'featureselected' : function(evt) {
				selectionFeature(visiteLayer, evt.feature);
			}
		}
	});

	selector = new OpenLayers.Control.SelectFeature(villeSearch, {
		click : true,
		autoActivate : true
	});

	geojson_layer = new OpenLayers.Layer.Vector("GeoJSON", {
		styleMap : new OpenLayers.Style({
			'fillOpacity' : 0,
			'strokeColor' : '#ff0000'
		}),
		strategies : [ new OpenLayers.Strategy.Fixed() ],
		projection : new OpenLayers.Projection('EPSG:4326'),
		protocol : new OpenLayers.Protocol.HTTP({
			url : domain + "data/region.geojson",
			format : new OpenLayers.Format.GeoJSON()
		})
	});
	map.addLayer(geojson_layer);

	map.addControl(selector);
	map.addLayer(villeSearch);

}

function gestionCarteJeu(lon, lat, zoom, type) {

	map = new OpenLayers.Map("content", {
		controls : [
		// new OpenLayers.Control.Navigation(),
		// new OpenLayers.Control.MousePosition({})
		],
		maxExtent : new OpenLayers.Bounds(-20037508.34, -20037508.34,
				20037508.34, 20037508.34),
		maxResolution : 'auto',
		numZoomLevels : 19,
		units : 'm',
		projection : new OpenLayers.Projection("EPSG:900913"),
		displayProjection : new OpenLayers.Projection("EPSG:4326")
	});
	osmNoLayer = new OpenLayers.Layer.OSM(
			'osm-no-labels',
			[
					"http://a.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://b.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://c.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://d.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://e.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
					"http://f.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png" ],
			{
				tileOptions : {
					crossOriginKeyword : null
				}
			});
	map.addLayer(osmNoLayer);

	var center = new OpenLayers.LonLat(lon, lat);
	var projFrom = new OpenLayers.Projection("EPSG:4326");
	var projTo = new OpenLayers.Projection("EPSG:900913");

	if (!map.getCenter()) {
		map.setCenter(center.transform(projFrom, projTo), zoom);
	}

	if (type == 3 || type == 0)
		place = "data/region.geojson";
	else if (type == 2)
		place = "data/LA.geojson";
	else if (type == 1)
		place = "data/NM.geojson";

	geojson_layer = new OpenLayers.Layer.Vector("GeoJSON", {
		styleMap : new OpenLayers.Style(),
		strategies : [ new OpenLayers.Strategy.Fixed() ],
		projection : new OpenLayers.Projection('EPSG:4326'),
		protocol : new OpenLayers.Protocol.HTTP({
			url : domain + place,
			format : new OpenLayers.Format.GeoJSON()
		})
	});
	map.addLayer(geojson_layer);

	villePos = new OpenLayers.Layer.Vector("vector");
	map.addLayer(villePos);

}

function recupereGeoJson() {
	$.ajax({
		type : "GET",
		url : domain + "data/region2.geojson",
		dataType : "json",
		success : function(msg) {
			return msg;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('connexion aux données geoJson impossible: ' + textStatus
					+ "  " + erroThrown);
			return "{}";
		}
	});
}

function afficheTitreHide() {
	$("#jeuVilleAffichage").hide("slide", {
		direction : "bottom"
	}, 100, function() {
	});
}

function afficheTitreShow() {
	$("#jeuVilleAffichage").show("slide", {
		direction : "bottom"
	}, 100,
	function() {
	});
}

function reponseTemporaireHide() {
	$("#jeuReponseTemporaire").hide("slide", {
		direction : "bottom"
	}, 200, function() {
	});
}

function reponseTemporaireShow() {
	$("#jeuReponseTemporaire").show("slide", {
		direction : "bottom"
	}, 200, function() {
	});
}

function jeuDialogGeneralHide() {
	$("#jeuDialogGeneral").hide("slide", {
		direction : "bottom"
	}, 200, function() {
	});
}

function jeuDialogFinalShow() {
	centrerDiv("jeuDialogFinal",false);
	$("#jeuDialogFinal").show("slide", {
		direction : "bottom"
	}, 200, function() {
	});
}

function jeuDialogFinalHide() {
	$("#jeuDialogFinal").hide("slide", {
		direction : "bottom"
	}, 200, function() {
	});
}

function jeuDialogGeneralShow() {
	centrerDiv("jeuDialogGeneral",false);
	$("#jeuDialogGeneral").show("slide", {
		direction : "bottom"
	}, 200, function() {
	});
}

function creeJeu(domain, t) {

	villePos.events.on({
		click : onClick
	});
	projTo = new OpenLayers.Projection("EPSG:4326");
	projFrom = new OpenLayers.Projection("EPSG:900913");

	var lonlat = new OpenLayers.LonLat(0, 0);
	var temps = 0;
	var firstClick = true;
	var running = false;
	var villeCourante = "";
	var nbCycle = 10;
	var type = t;
	var highScore = false;

	var cycle = 0;

	this.cycleSuivant = function() {
		reponseTemporaireHide();
		if (cycle < nbCycle) {
			cycle++;
			villePos.removeAllFeatures();
			$('#barreProgression').width("0%");
			RAZ();
			changeBarreVille();
			afficheTitreShow();
			//lanceDecompte();
		} else {
			if (highScore) {
				saveScore();
			} else {

				afficheTitreHide();
				afficheScore();
			}
		}
	};

	function RAZ() {
		lonlat = new OpenLayers.LonLat(0, 0);
		temps = 0;
		firstClick = true;
		highScore = false;
	}

	function onClick(event) {
		if (firstClick && running) {
			var position = this.events.getMousePosition(event);
			lonlat = map.getLonLatFromPixel(position).transform(projFrom,
					projTo);
			stopDecompte(lonlat, true);
		}
	}

	function lanceDecompte() {
		running = true;
		$('#barreProgression').animate({
			width : "100%"
		}, {
			duration : 10000,
			step : function(now, fx) {
				tempo = (now / 10).toFixed(2);
				$('#barreTexte').html("Temps: " + tempo + "s");
				temps = tempo;
			},
			easing : "linear",
			complete : function() {
				stopDecompte(lonlat, false);
			}
		});
	}

	function stopDecompte(lonlat, avantFin) {
		if (avantFin)
			$('#barreProgression').stop();
		first = false;
		running = false;
		ajoutPoint(villePos, lonlat.lon, lonlat.lat, domain
				+ "media/marker/marqueurPose.png", "pionJoueur");
		$.ajax({
			type : "POST",
			url : domain + "Game",
			dataType : "json",
			xhrFields : {
				withCredentials : true
			},
			crossDomain : true,
			data : "ajax=true&resultatLon=" + lonlat.lon + "&resultatLat="
					+ lonlat.lat + "&temps=" + temps + "&type=" + type,
			success : stopSuccess,
			error : function(jqXHR, textStatus, errorThrown) {
				$('#dialogScore').html(
						"<p>La connexion avec le serveur a échouée.</p>");
				$("#dialogScore").dialog("open");
			}
		});
		// alert(tempo+" - "+lonlat.lon + " - " + lonlat.lat);
	}

	function stopSuccess(msg) {
		ajoutPoint(villePos, msg.trueLon, msg.trueLat, domain
				+ "media/marker/marqueur.png", "pionReel");
		if (cycle == nbCycle) {
			showResultatIntermediaire(msg, true);
		} else {
			showResultatIntermediaire(msg, false);
		}

	}

	function showResultatIntermediaire(msg, dernier) {
		$('#jeuScore').html(msg.points + " pts");
		$('#jeuScoreTotal').html(
				"Score total : <span class='bleuFonce'> " + msg.total
						+ " pts</span>");
		$('#jeuDistance').html((msg.distance).toFixed(1) + " km");
		$('#jeuTemps').html("Vous avez répondu en " + temps + "s");
		if (dernier) {
			// on dois changer le bouton
			$('#jeuZoneBouton')
					.html(
							"<a href='#' class='boutonJeu' onClick='jeuEnCours.cycleSuivant();'>Voir le résultat</a>");

		}
		reponseTemporaireShow();
	}

	function changeBarreVille() {
		$.ajax({
			type : "POST",
			url : domain + "Game",
			dataType : "json",
			// xhrFields: {
			// withCredentials: true
			// },
			// crossDomain:true,
			data : "ajax=true&ville=true" + "&type=" + type,
			success : changeVilleCourante,
			// beforeSend: checkbefore,
			error : function(jqXHR, textStatus, errorThrown) {
				$('#dialogJeu').html(
						"<p>La connexion avec le serveur a échouée.</p>"
								+ textStatus + "   " + errorThrown);
				$("#dialogJeu").dialog("open");
			}
		});
	}

	function changeVilleCourante(msg) {
		villeCourante = msg.ville;
		$('#jeuNomVille').html(villeCourante);
		$('#jeuAvancement').html(cycle + " / 10");
		lanceDecompte();

	}

	function afficheScore() {
		$.ajax({
			type : "POST",
			url : domain + "Game",
			dataType : "json",
			// xhrFields: {
			// withCredentials: true
			// },
			// crossDomain:true,
			data : "ajax=true&score=true" + "&type=" + type,
			success : finalScore,
			error : function(jqXHR, textStatus, errorThrown) {
				$('#dialogJeu').html(
						"<p>La connexion avec le serveur a échouée."
								+ textStatus + "</p>");
				$("#dialogJeu").dialog("open");
			}
		});
	}

	function checkbefore(jqXHR, settings) {
		console.info(jqXHR);
		console.info(settings);
	}

	function finalScore(msg) {
		$('#jeuDialogFinalScore').html(msg.total + "pts");
		if (msg.isHS > 0) {
			$('#estHighScore').html("Nouveau record : " + msg.isHS + "e !");
			$('#highScoreButtonCheck')
					.html(
							"<a href=\"#\" class=\"boutonJeu2\"	onclick=\"jeuEnCours.cycleSuivant();\">Sauvegarder</a>");
			highScore = true;
		} else {
			$('#highScoreButtonCheck').html(
					"<a href=\"#\" class=\"boutonJeu2\"	onclick=\"$(location).attr('href','"
							+ domain + "Game?fini=fini');\">Continuer</a>");
		}
		$('#jeuDialogFinalTopTen').html(msg.topten);
		jeuDialogFinalShow();
	}

	function saveScore() {
		nom = $('#winnerName').val();
		var regex = /[0-9a-zA-Z]/;
		if(regex.test(nom)) {
			if(nom)
			// sauvegarde le score et affiche le nouveau résultat
			$.ajax({
				type : "POST",
				url : domain + "Game",
				dataType : "json",
				// xhrFields: {
				// withCredentials: true
				// },
				// crossDomain:true,
				data : "ajax=true&save=true" + "&nom=" + nom + "&type=" + type,
				success : saveScoreFin,
				error : function(jqXHR, textStatus, errorThrown) {
					$('#dialogJeu').html(
							"<p>La connexion avec le serveur a échouée."
									+ textStatus + "</p>");
					$("#dialogJeu").dialog("open");
				}
			});
		}
		else{
			alert("Vous ne pouvez utiliser que des chiffres et des lettres!");
		}
	}

	function saveScoreFin(msg) {
		$('#jeuDialogFinalTopTen').html(msg.topten);
		$('#highScoreButtonCheck').html(
				"<a href=\"#\" class=\"boutonJeu2\"	onclick=\"$(location).attr('href','"
						+ domain + "Game?fini=fini');\">Continuer</a>");

	}
}

function centrerAccueil(totalDiv) {
	var ecran = $(document).width();
	//fix
	totalDiv = totalDiv + 150;
	if (ecran >= totalDiv) {
		var padding = (ecran - totalDiv) / 2;
		$("#tableAccueil").css("padding-left", padding + "px");
	}
}

function centrerDiv(div, onlyMap) {
	var bla = "#" + div;
	var ecranW = $(document).width();
	var ecranH=0;
	if(onlyMap){
		ecranH = $(document).height()-35-135;
	}
	else{
		ecranH = $(document).height()-15-35;
	}
	var divW = $(bla).width();
	var divH = $(bla).height();
	if (ecranW >= divW && ecranH >= divH) {
		var left = (ecranW - divW) / 2;
		var top = (ecranH - divH) / 2;
		$(bla).css("left", left + "px");
		$(bla).css("top", top + "px");
	}
}

function openScoresGlobaux(msg){
	centrerDiv("scoresGlobaux",true);
	
	$("#scoresGlobauxNM").html(msg.topten1);
	$("#scoresGlobauxLA").html(msg.topten2);
	$("#scoresGlobauxPDLL").html(msg.topten3);
	
	$("#scoresGlobaux").show("slide", {
		direction : "bottom"
	}, 200, function() {
	});
	
}

function closeScoresGlobaux(){
	$("#scoresGlobaux").hide("slide", {
		direction : "bottom"
	}, 200, function() {
	});
}

function getScoresGlobaux(){
	$.ajax({
		type : "POST",
		url : domain + "Scores",
		dataType : "json",
		data : "ajax=true",
		success : openScoresGlobaux,
		error : function(jqXHR, textStatus, errorThrown) {
			$('#scoresGlobauxLigne').html(
					"<p>La connexion avec le serveur a échouée.</p>"
							+ textStatus + "   " + errorThrown);
			$("#scoresGlobaux").show("slide", {
				direction : "bottom"
			}, 200, function() {
			});
		}
	});
}