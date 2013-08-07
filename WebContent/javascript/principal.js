/**
 * main javascript file
 */

function pageLoaded(domainPath) {
	$('#searchInput').autocomplete({
		source : domainPath + 'AjaxListName',
		minLength : 2,
		autoFocus : true,
		delay : 500,
		open : function(event, ui) {
			$("#ui-id-1").zIndex(5010);
		},
		select : function(event, ui) {
			$("#searchInput").val(ui.item.label);
			$("#searchBarForm").submit();
		}
	}).data("ui-autocomplete")._renderItem = function(ul, item) {
		return $("<li></li>").data("item.autocomplete", item).append(
				$("<a></a>").html(item.label.slice(0, -3))).appendTo(ul);
	};
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

function geoloc(monDiv) {
	if (navigator.geolocation) {

		// Fonction de callback en cas de succès
		function affichePosition(position) {

			var lat = position.coords.latitude;
			var lon = position.coords.longitude;
			addPoint(vectorLayer, lat, lon);

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

function ajoutVille(lon, lat) {
	villeSearch.clearMarkers();
	var size = new OpenLayers.Size(14, 21);
	var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
	var icon = new OpenLayers.Icon(
			'http://lodpaddle.univ-nantes.fr/lodpaddle/media/marqueur.png',
			size, offset);
	var position = new OpenLayers.LonLat(lon, lat);
	var projFrom = new OpenLayers.Projection("EPSG:4326");
	var projTo = new OpenLayers.Projection("EPSG:900913");
	villeSearch.addMarker(new OpenLayers.Marker(position.transform(projFrom,
			projTo), icon));
}

function ajoutPoint(layer, lon, lat, iconPath, id) {

	var projFrom = new OpenLayers.Projection("EPSG:4326");
	var projTo = new OpenLayers.Projection("EPSG:900913");
	var point = new OpenLayers.Geometry.Point(lon, lat).transform(projFrom,
			projTo);

	var markerStyle = {
		externalGraphic : iconPath,
		graphicWidth : 14,
		graphicHeight : 21,
		graphicXOffset : -7,
		graphicYOffset : -21
	};
	var feature = new OpenLayers.Feature.Vector(point, {
		title : id,
		clickable : 'off'
	}, markerStyle);
	layer.addFeatures([ feature ]);
}

function ajoutLayer(layer) {
	enleveToutLayer();
	map.addLayer(layer);
	var selector = new OpenLayers.Control.SelectFeature(layer, {
		click : true,
		autoActivate : true
	});

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

function selectionFeature(layer, feature) {
	feature.style.graphicWidth = 28;
	feature.style.graphicHeight = 42;
	feature.style.graphicXOffset = -14;
	feature.style.graphicYOffset = -42;
	layer.drawFeature(feature);
}

function gestionCarte() {

	map = mapCreation();

	villeSearch = new OpenLayers.Layer.Vector("villeSearch", {
		eventListeners : {
			'featureselected' : function(evt) {
				selectionFeature(villeSearch, evt.feature);
			}
		}
	});
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
	transportLayer = new OpenLayers.Layer.Vector("serviceLayer", {
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

	var selector = new OpenLayers.Control.SelectFeature(villeSearch, {
		click : true,
		autoActivate : true
	});

	map.addControl(selector);
	map.addLayer(villeSearch);

}

function centrerAccueil(totalDiv) {
	var ecran = $(document).width();
	if (ecran >= totalDiv) {
		var padding = (ecran - totalDiv) / 2;
		$("#tableAccueil").css("padding-left", padding + "px");
	}
}