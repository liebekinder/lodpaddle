/**
 * main javascript file
 */


function pageLoaded(domainPath,idChange) {
	 $('#query').autocomplete({
		source:domainPath+'AjaxListName',
		minLength:3,
		autoFocus:true
		});
	 doFlip(idChange);
//		geoloc("pos");
}


function doFlip(idChange){	 
	 $('.flipIt').click(function () {$(this).rotate3Di('toggle', 1000,        {
         sideChange: mySideChange
     }
			 );});
	 
}


function mySideChange(front,idChange) {
    if (front) {
    	$('#presentationVille').css('visibility', 'hidden');
        $('.presentationVille').css('visibility', 'visible');
        
    } else {

        $('#presentationVille').css('visibility', 'visible');
        $('.presentationVille').css('visibility', 'hidden');
    }
}


function afficheContenu(){ 
	document.getElementById("presentationVille").style.visibility="visible";
}

function geoloc(monDiv) {
	if (navigator.geolocation) {

		// Fonction de callback en cas de succès
		function affichePosition(position) {

			var infopos = "Position déterminée : <br>";
			infopos += "Latitude : " + position.coords.latitude + "<br>";
			infopos += "Longitude: " + position.coords.longitude + "<br>";
			infopos += "Altitude : " + position.coords.altitude + "<br>";
			document.getElementById(monDiv).innerHTML = infopos;

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
			document.getElementById(monDiv).innerHTML = info;
		}

		navigator.geolocation.getCurrentPosition(affichePosition,
				erreurPosition);

	} else {

		alert("Ce navigateur ne supporte pas la géolocalisation");

	}
}

function init() {

	map = new OpenLayers.Map("basicMap", {
		controls : [ new OpenLayers.Control.Navigation(),
				new OpenLayers.Control.PanZoomBar(),
				new OpenLayers.Control.MousePosition(),
				new OpenLayers.Control.ScaleLine({
					geodesic : true
				}), new OpenLayers.Control.LayerSwitcher(),
				new OpenLayers.Control.Attribution() ],
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