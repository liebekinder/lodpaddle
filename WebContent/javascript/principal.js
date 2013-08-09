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

function getListURl(ressource){
	pos = ressource.lastIndexOf("/");
	nomPage = ressource;
//	var apiCall1 = "http://fr.wikipedia.org/w/api.php?action=query&titles=___LE_TITRE___&prop=images";	
//	var apiCall2 = "http://commons.wikimedia.org/w/api.php?action=query&titles=File:Anne%20of%20Brittany%20statue%20Ch%C3%A2teau%20des%20ducs%20de%20Bretagne.jpg&prop=imageinfo&iiprop=url";
//	var path ="Arrivée Maxi Banque Populaire V Nantes.JPG";
//	$.ajax({
//		type : "POST",
//		url : domain + "AjaxInsee",
//		dataType : "xml",
//		data : "ressource=" + encodeURI(ressource),
//		success : function(msg) {
//			$('#cadreInfo').html(msg);
//			cadreInfoShow();
//		},
//		error : function(jqXHR, textStatus, errorThrown) {
//			$('#cadreInfo').html(
//					"<p>Impossible de récupérer les données!</p>");
//		}
//	});
	
}

function afficheCadreInfo(ressource) {
	if (ressource.indexOf("http://lodpaddle.univ-nantes.fr/Communes_geolocalises/") != -1) {
		//Cas particulier des villes, on ne veut pas du cadre info qui serait vide:
		//seulement un lien vers la recherche
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

/**
*
*  MD5 (Message-Digest Algorithm)
*  http://www.webtoolkit.info/
*
**/
 
var MD5 = function (string) {
 
	function RotateLeft(lValue, iShiftBits) {
		return (lValue<<iShiftBits) | (lValue>>>(32-iShiftBits));
	}
 
	function AddUnsigned(lX,lY) {
		var lX4,lY4,lX8,lY8,lResult;
		lX8 = (lX & 0x80000000);
		lY8 = (lY & 0x80000000);
		lX4 = (lX & 0x40000000);
		lY4 = (lY & 0x40000000);
		lResult = (lX & 0x3FFFFFFF)+(lY & 0x3FFFFFFF);
		if (lX4 & lY4) {
			return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
		}
		if (lX4 | lY4) {
			if (lResult & 0x40000000) {
				return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
			} else {
				return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
			}
		} else {
			return (lResult ^ lX8 ^ lY8);
		}
 	}
 
 	function F(x,y,z) { return (x & y) | ((~x) & z); }
 	function G(x,y,z) { return (x & z) | (y & (~z)); }
 	function H(x,y,z) { return (x ^ y ^ z); }
	function I(x,y,z) { return (y ^ (x | (~z))); }
 
	function FF(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};
 
	function GG(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};
 
	function HH(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};
 
	function II(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};
 
	function ConvertToWordArray(string) {
		var lWordCount;
		var lMessageLength = string.length;
		var lNumberOfWords_temp1=lMessageLength + 8;
		var lNumberOfWords_temp2=(lNumberOfWords_temp1-(lNumberOfWords_temp1 % 64))/64;
		var lNumberOfWords = (lNumberOfWords_temp2+1)*16;
		var lWordArray=Array(lNumberOfWords-1);
		var lBytePosition = 0;
		var lByteCount = 0;
		while ( lByteCount < lMessageLength ) {
			lWordCount = (lByteCount-(lByteCount % 4))/4;
			lBytePosition = (lByteCount % 4)*8;
			lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount)<<lBytePosition));
			lByteCount++;
		}
		lWordCount = (lByteCount-(lByteCount % 4))/4;
		lBytePosition = (lByteCount % 4)*8;
		lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80<<lBytePosition);
		lWordArray[lNumberOfWords-2] = lMessageLength<<3;
		lWordArray[lNumberOfWords-1] = lMessageLength>>>29;
		return lWordArray;
	};
 
	function WordToHex(lValue) {
		var WordToHexValue="",WordToHexValue_temp="",lByte,lCount;
		for (lCount = 0;lCount<=3;lCount++) {
			lByte = (lValue>>>(lCount*8)) & 255;
			WordToHexValue_temp = "0" + lByte.toString(16);
			WordToHexValue = WordToHexValue + WordToHexValue_temp.substr(WordToHexValue_temp.length-2,2);
		}
		return WordToHexValue;
	};
 
	function Utf8Encode(string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";
 
		for (var n = 0; n < string.length; n++) {
 
			var c = string.charCodeAt(n);
 
			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
 
		}
 
		return utftext;
	};
 
	var x=Array();
	var k,AA,BB,CC,DD,a,b,c,d;
	var S11=7, S12=12, S13=17, S14=22;
	var S21=5, S22=9 , S23=14, S24=20;
	var S31=4, S32=11, S33=16, S34=23;
	var S41=6, S42=10, S43=15, S44=21;
 
	string = Utf8Encode(string);
 
	x = ConvertToWordArray(string);
 
	a = 0x67452301; b = 0xEFCDAB89; c = 0x98BADCFE; d = 0x10325476;
 
	for (k=0;k<x.length;k+=16) {
		AA=a; BB=b; CC=c; DD=d;
		a=FF(a,b,c,d,x[k+0], S11,0xD76AA478);
		d=FF(d,a,b,c,x[k+1], S12,0xE8C7B756);
		c=FF(c,d,a,b,x[k+2], S13,0x242070DB);
		b=FF(b,c,d,a,x[k+3], S14,0xC1BDCEEE);
		a=FF(a,b,c,d,x[k+4], S11,0xF57C0FAF);
		d=FF(d,a,b,c,x[k+5], S12,0x4787C62A);
		c=FF(c,d,a,b,x[k+6], S13,0xA8304613);
		b=FF(b,c,d,a,x[k+7], S14,0xFD469501);
		a=FF(a,b,c,d,x[k+8], S11,0x698098D8);
		d=FF(d,a,b,c,x[k+9], S12,0x8B44F7AF);
		c=FF(c,d,a,b,x[k+10],S13,0xFFFF5BB1);
		b=FF(b,c,d,a,x[k+11],S14,0x895CD7BE);
		a=FF(a,b,c,d,x[k+12],S11,0x6B901122);
		d=FF(d,a,b,c,x[k+13],S12,0xFD987193);
		c=FF(c,d,a,b,x[k+14],S13,0xA679438E);
		b=FF(b,c,d,a,x[k+15],S14,0x49B40821);
		a=GG(a,b,c,d,x[k+1], S21,0xF61E2562);
		d=GG(d,a,b,c,x[k+6], S22,0xC040B340);
		c=GG(c,d,a,b,x[k+11],S23,0x265E5A51);
		b=GG(b,c,d,a,x[k+0], S24,0xE9B6C7AA);
		a=GG(a,b,c,d,x[k+5], S21,0xD62F105D);
		d=GG(d,a,b,c,x[k+10],S22,0x2441453);
		c=GG(c,d,a,b,x[k+15],S23,0xD8A1E681);
		b=GG(b,c,d,a,x[k+4], S24,0xE7D3FBC8);
		a=GG(a,b,c,d,x[k+9], S21,0x21E1CDE6);
		d=GG(d,a,b,c,x[k+14],S22,0xC33707D6);
		c=GG(c,d,a,b,x[k+3], S23,0xF4D50D87);
		b=GG(b,c,d,a,x[k+8], S24,0x455A14ED);
		a=GG(a,b,c,d,x[k+13],S21,0xA9E3E905);
		d=GG(d,a,b,c,x[k+2], S22,0xFCEFA3F8);
		c=GG(c,d,a,b,x[k+7], S23,0x676F02D9);
		b=GG(b,c,d,a,x[k+12],S24,0x8D2A4C8A);
		a=HH(a,b,c,d,x[k+5], S31,0xFFFA3942);
		d=HH(d,a,b,c,x[k+8], S32,0x8771F681);
		c=HH(c,d,a,b,x[k+11],S33,0x6D9D6122);
		b=HH(b,c,d,a,x[k+14],S34,0xFDE5380C);
		a=HH(a,b,c,d,x[k+1], S31,0xA4BEEA44);
		d=HH(d,a,b,c,x[k+4], S32,0x4BDECFA9);
		c=HH(c,d,a,b,x[k+7], S33,0xF6BB4B60);
		b=HH(b,c,d,a,x[k+10],S34,0xBEBFBC70);
		a=HH(a,b,c,d,x[k+13],S31,0x289B7EC6);
		d=HH(d,a,b,c,x[k+0], S32,0xEAA127FA);
		c=HH(c,d,a,b,x[k+3], S33,0xD4EF3085);
		b=HH(b,c,d,a,x[k+6], S34,0x4881D05);
		a=HH(a,b,c,d,x[k+9], S31,0xD9D4D039);
		d=HH(d,a,b,c,x[k+12],S32,0xE6DB99E5);
		c=HH(c,d,a,b,x[k+15],S33,0x1FA27CF8);
		b=HH(b,c,d,a,x[k+2], S34,0xC4AC5665);
		a=II(a,b,c,d,x[k+0], S41,0xF4292244);
		d=II(d,a,b,c,x[k+7], S42,0x432AFF97);
		c=II(c,d,a,b,x[k+14],S43,0xAB9423A7);
		b=II(b,c,d,a,x[k+5], S44,0xFC93A039);
		a=II(a,b,c,d,x[k+12],S41,0x655B59C3);
		d=II(d,a,b,c,x[k+3], S42,0x8F0CCC92);
		c=II(c,d,a,b,x[k+10],S43,0xFFEFF47D);
		b=II(b,c,d,a,x[k+1], S44,0x85845DD1);
		a=II(a,b,c,d,x[k+8], S41,0x6FA87E4F);
		d=II(d,a,b,c,x[k+15],S42,0xFE2CE6E0);
		c=II(c,d,a,b,x[k+6], S43,0xA3014314);
		b=II(b,c,d,a,x[k+13],S44,0x4E0811A1);
		a=II(a,b,c,d,x[k+4], S41,0xF7537E82);
		d=II(d,a,b,c,x[k+11],S42,0xBD3AF235);
		c=II(c,d,a,b,x[k+2], S43,0x2AD7D2BB);
		b=II(b,c,d,a,x[k+9], S44,0xEB86D391);
		a=AddUnsigned(a,AA);
		b=AddUnsigned(b,BB);
		c=AddUnsigned(c,CC);
		d=AddUnsigned(d,DD);
	}
 
	var temp = WordToHex(a)+WordToHex(b)+WordToHex(c)+WordToHex(d);
 
	return temp.toLowerCase();
}