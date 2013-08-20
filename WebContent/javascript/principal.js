/**
 * main javascript file
 */

function pageLoaded(domainPath) {
	domain = domainPath;
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
		new OpenLayers.Control.LayerSwitcher(),
				new OpenLayers.Control.Attribution({
					div : document.getElementById("olControlAttribution")
				}) ],
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
	if (map.getLayersByName("villeSearch").length != 0)
		map.removeLayer(map.getLayersByName("villeSearch")[0]);
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
	// le nom ici correspond au nom d'une page puisque issue de wikipedia
	var api = "http://fr.wikipedia.org/w/api.php";
	// var apiCall2 =
	// "http://commons.wikimedia.org/w/api.php?action=query&titles=File:Anne%20of%20Brittany%20statue%20Ch%C3%A2teau%20des%20ducs%20de%20Bretagne.jpg&prop=imageinfo&iiprop=url";
	// var path ="Arrivée Maxi Banque Populaire V Nantes.JPG";
	// $.ajax({
	// type : "GET",
	// url : api,
	// dataType : "json",
	// beforeSend: function(request) {
	// request.setRequestHeader("User-Agent","sebastien.chenais@etu.univ-nantes.fr");
	// },
	// data:action="action=query&titles="+ nom + "&prop=images&format=json",
	// success : function(msg){
	// getListURlSuite(msg);
	// },
	// error : function(jqXHR, textStatus, errorThrown) {
	// alert("La récupération des images sur wikipédia a échoué");
	// }
	// });
	$.getJSON(api + "?action=query&format=json&callback=?", {
		page : ressource,
		prop : "images"
	}, getListURlSuite);

}

function getListURlSuite(json, etat, arg3) {
	alert(json + " " + etat);
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
	var layer = map.layers[3];
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
			  'fillOpacity': 0,
			  'strokeColor': '#ff0000'
			}),
		strategies : [ new OpenLayers.Strategy.Fixed() ],
        projection: new OpenLayers.Projection('EPSG:4326'),
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

	if(type==3 || type ==0)	place = "data/region.geojson";
	else if(type == 2) place = "data/LA.geojson";
	else if(type == 1) place = "data/NM.geojson";
	
	geojson_layer = new OpenLayers.Layer.Vector("GeoJSON", {
		styleMap : new OpenLayers.Style(),
		strategies : [ new OpenLayers.Strategy.Fixed() ],
        projection: new OpenLayers.Projection('EPSG:4326'),
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

function creeJeu(domain) {

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

	var cycle = 0;

	this.cycleSuivant = function() {
		if (cycle < nbCycle) {
			cycle++;
			villePos.removeAllFeatures();
			$('#barreProgression').width("0%");
			RAZ();
			nouvelleVille();
			lanceDecompte();
		} else {
			afficheScore();
		}
	}

	function RAZ() {
		lonlat = new OpenLayers.LonLat(0, 0);
		temps = 0;
		firstClick = true;
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
		$('#barreProgression').animate(
				{
					width : "100%"
				},
				{
					duration : 10000,
					step : function(now, fx) {
						tempo = (now / 10).toFixed(2);
						$('#barreTexteGauche').html(
								"<div><b>" + villeCourante
										+ "</b></div><div><b>Temps: " + tempo
										+ "s</b></div>");
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
				+ "media/marker/marqueur.png", "pionJoueur");
		$.ajax({
			type : "POST",
			url : domain + "Game",
			dataType : "json",
			data : "ajax=true&resultatLon=" + lonlat.lon + "&resultatLat="
					+ lonlat.lat + "&temps=" + temps,
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
		html = boiteResultat(msg);
		ajoutPoint(villePos, msg.trueLon, msg.trueLat, domain
				+ "media/marker/marqueur.png", "pionReel");
		if (cycle == nbCycle) {
			$('#dialogDernierScore').html(html);
			$("#dialogDernierScore").dialog("open");
		} else {
			$('#dialogScore').html(html);
			$("#dialogScore").dialog("open");
		}
		$('#barreTexteDroit').html(
				"<div><b>" + msg.total + " points</b></div><div><b>Question "
						+ cycle + " sur 10</b></div>");

	}

	function nouvelleVille() {
		$.ajax({
			type : "POST",
			url : domain + "Game",
			dataType : "json",
			data : "ajax=true&ville=true",
			success : changeVilleCourante,
			error : function(jqXHR, textStatus, errorThrown) {
				$('#dialogScore').html(
						"<p>La connexion avec le serveur a échouée.</p>");
				$("#dialogScore").dialog("open");
			}
		});
	}

	function changeVilleCourante(msg) {
		villeCourante = msg.ville;
		$('#barreTexteGauche').html(
				"<div><b>" + villeCourante
						+ "</b></div><div><b>Temps: 0s</b></div>");

	}

	function afficheScore() {
		$.ajax({
			type : "POST",
			url : domain + "Game",
			dataType : "json",
			data : "ajax=true&score=true",
			success : finalScore,
			error : function(jqXHR, textStatus, errorThrown) {
				$('#dialogFinal').html(
						"<p>La connexion avec le serveur a échouée.</p>");
				$("#dialogFinal").dialog("open");
			}
		});
	}

	function finalScore(msg) {
		$('#barreTexteDroit').html(
				"<div><b>" + msg.total + " points</b></div><div><b>Question "
						+ cycle + " sur 10</b></div>");
		$('#dialogFinal').html(
				"<div>Vous avez obtenu un score de " + msg.total
						+ " points! Félicitations!</div>");
		$("#dialogFinal").dialog("open");
	}
}

function boiteResultat(data) {
	return "<div> Distance de " + data.ville + ": " + data.distance.toFixed(1)
			+ "km</div>" + "<div> Points pour cette manche: " + data.points
			+ "pts</div>";
}

function centrerAccueil(totalDiv) {
	var ecran = $(document).width();
	if (ecran >= totalDiv) {
		var padding = (ecran - totalDiv) / 2;
		$("#tableAccueil").css("padding-left", padding + "px");
	}
}