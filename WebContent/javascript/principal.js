/**
 * main javascript file
 */

function init() {
	map = new OpenLayers.Map("basicMap");
	var mapnik = new OpenLayers.Layer.OSM();

	var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;

	renderer = (renderer) ? [ renderer ]
			: OpenLayers.Layer.Vector.prototype.renderers;

	vectors = new OpenLayers.Layer.Vector("Vector Layer", {
		renderers : renderer
	});

	map.addLayers([ mapnik, vectors ]);

	var fromProjection = new OpenLayers.Projection("EPSG:4326"); // transform
																	// from WGS
																	// 1984
	var toProjection = new OpenLayers.Projection("EPSG:900913"); // to
																	// Spherical
																	// Mercator
																	// Projection
	var position = new OpenLayers.LonLat(-0.8419521, 47.6061394).transform(
			fromProjection, toProjection);
	var zoom = 8;

	map.setCenter(position, zoom);

	var drag = new OpenLayers.Control.DragFeature(vectors);

	drag.activate();

}