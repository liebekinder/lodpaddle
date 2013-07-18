/**
 * main javascript file
 */

function pageLoaded() {
	var a = $('#query').autocomplete({
		source:'/lodpaddleTest/AjaxListName',
		minLength:3,
		autoFocus:true
		});
	
	
}

function init() {
	
	map = new OpenLayers.Map("basicMap",{
        controls:[
                  new OpenLayers.Control.Navigation(),
                  new OpenLayers.Control.PanZoomBar(),
                  new OpenLayers.Control.MousePosition(),
                  new OpenLayers.Control.ScaleLine({geodesic: true}),
                  new OpenLayers.Control.LayerSwitcher(),
                  new OpenLayers.Control.Attribution()
                  ],
                  maxExtent: new OpenLayers.Bounds(-20037508.34,-20037508.34,20037508.34,20037508.34),
                  maxResolution:'auto',
                  numZoomLevels: 19,
                  units: 'm',
                  projection: new OpenLayers.Projection("EPSG:900913"),
                  displayProjection: new OpenLayers.Projection("EPSG:4326")
              });
    

	var osm = new OpenLayers.Layer.OSM("OpenStreetMap");
	map.addLayer(osm);
	
	var osmNoLayer =new OpenLayers.Layer.OSM('osm-no-labels',
			["http://a.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png", 
	         "http://b.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png", 
	         "http://c.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
	         "http://d.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
	         "http://e.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png",
	         "http://f.www.toolserver.org/tiles/osm-no-labels/${z}/${x}/${y}.png"],
	         {tileOptions: {crossOriginKeyword: null}});
	map.addLayer(osmNoLayer);
	
	var center = new OpenLayers.LonLat(-0.353394,47.546711);
	var projFrom = new OpenLayers.Projection("EPSG:4326");
	var projTo = new OpenLayers.Projection("EPSG:900913");
	
    
	if( ! map.getCenter() ){
		map.setCenter(center.transform(projFrom, projTo), 7);
    }

    var point = new OpenLayers.Geometry.Point(-0.353394,47.546711);
    point.transform(projFrom, projTo);
    
	var vectorLayer = new OpenLayers.Layer.Vector("Point d'intérêt");
	var features = new OpenLayers.Feature.Vector(
            point,
            {type:0}
        );

	vectorLayer.addFeatures(features);

	map.addLayer(vectorLayer);
	
	
}