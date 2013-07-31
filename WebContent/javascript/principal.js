/**
 * main javascript file
 */

function pageLoaded(domainPath, idChange) {
	 $('#searchInput').autocomplete({
	 source:domainPath+'AjaxListName',
	 minLength:2,
	 autoFocus:true
	 });
	doFlip(idChange);
	// geoloc("pos");
}

function doFlip(idChange) {
	$('.flipIt').click(function() {
		$(this).rotate3Di('toggle', 1000, {
			sideChange : mySideChange
		});
	});

	$('.fliploisir').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb1,
			easing : 'swing'
		});
	});

	$('.flipculture').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb2
		});
	});

	$('.flipville').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb3
		});
	});
	
	$('.flipservice').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb4
		});
	});

	$('.flipvisite').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb5
		});
	});

	$('.fliptransport').click(function() {
		$('#contentContainer').rotate3Di('toggle', 1000, {
			sideChange : mySideChangeb6
		});
	});


}

function mySideChange(front, idChange) {
	if (front) {
		$('#presentationVille').css('visibility', 'hidden');
		$('.presentationVille').css('visibility', 'visible');

	} else {

		$('#presentationVille').css('visibility', 'visible');
		$('.presentationVille').css('visibility', 'hidden');
	}
}

function mySideChangeb1(front) {
	$('.cultureWidget').css('visibility', 'hidden');
	$('.loisirWidget').css('visibility', 'visible');
	$('.transportWidget').css('visibility', 'hidden');
	$('.villeWidget').css('visibility', 'hidden');
	$('.serviceWidget').css('visibility', 'hidden');
	$('.visiteWidget').css('visibility', 'hidden');
}

function mySideChangeb2(front) {

	$('.cultureWidget').css('visibility', 'visible');
	$('.loisirWidget').css('visibility', 'hidden');
	$('.transportWidget').css('visibility', 'hidden');
	$('.villeWidget').css('visibility', 'hidden');
	$('.serviceWidget').css('visibility', 'hidden');
	$('.visiteWidget').css('visibility', 'hidden');

}

function mySideChangeb3(front) {
	
	$('.cultureWidget').css('visibility', 'hidden');
	$('.loisirWidget').css('visibility', 'hidden');
	$('.transportWidget').css('visibility', 'hidden');
	$('.villeWidget').css('visibility', 'visible');
	$('.serviceWidget').css('visibility', 'hidden');
	$('.visiteWidget').css('visibility', 'hidden');

}

function mySideChangeb4(front) {
	
	$('.cultureWidget').css('visibility', 'hidden');
	$('.loisirWidget').css('visibility', 'hidden');
	$('.transportWidget').css('visibility', 'hidden');
	$('.villeWidget').css('visibility', 'hidden');
	$('.serviceWidget').css('visibility', 'visible');
	$('.visiteWidget').css('visibility', 'hidden');
}

function mySideChangeb5(front) {
	
	$('.cultureWidget').css('visibility', 'hidden');
	$('.loisirWidget').css('visibility', 'hidden');
	$('.transportWidget').css('visibility', 'hidden');
	$('.villeWidget').css('visibility', 'hidden');
	$('.serviceWidget').css('visibility', 'hidden');
	$('.visiteWidget').css('visibility', 'visible');
}

function mySideChangeb6(front) {
	
	$('.cultureWidget').css('visibility', 'hidden');
	$('.loisirWidget').css('visibility', 'hidden');
	$('.transportWidget').css('visibility', 'visible');
	$('.villeWidget').css('visibility', 'hidden');
	$('.serviceWidget').css('visibility', 'hidden');
	$('.visiteWidget').css('visibility', 'hidden');
}

function afficheContenu() {
	document.getElementById("presentationVille").style.visibility = "visible";
}

function geoloc(monDiv) {
	if (navigator.geolocation) {

		// Fonction de callback en cas de succès
		function affichePosition(position) {

			var infopos = "Position déterminée :\n";
			infopos += "Latitude : " + position.coords.latitude + "\n";
			infopos += "Longitude: " + position.coords.longitude + "\n";
			infopos += "Altitude : " + position.coords.altitude + "\n";
			
			if(monDiv == 'alert') alert("succès!\n"+infopos);
			else document.getElementById(monDiv).innerHTML = infopos;

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
			if(monDiv == 'alert') alert("échec!\n"+infopos);
			else document.getElementById(monDiv).innerHTML = infopos;
		}

		navigator.geolocation.getCurrentPosition(affichePosition,
				erreurPosition);

	} else {

		alert("Ce navigateur ne supporte pas la géolocalisation");

	}
}

function init() {

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
		displayProjection : new OpenLayers.Projection("EPSG:4326")
	});

	var osm = new OpenLayers.Layer.OSM("OpenStreetMap");
	map.addLayer(osm);

	var mapquest = new OpenLayers.Layer.OSM(
			'mapquest',
			[
					"http://otile1.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg",
					"http://otile2.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg",
					"http://otile3.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg",
					"http://otile4.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.jpg",],
			{
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
	}

	var point = new OpenLayers.Geometry.Point(-0.353394, 47.546711);
	point.transform(projFrom, projTo);

	var vectorLayer = new OpenLayers.Layer.Vector("Point d'intérêt");
	var features = new OpenLayers.Feature.Vector(point, {
		type : 0
	});

	vectorLayer.addFeatures(features);

	map.addLayer(vectorLayer);

}