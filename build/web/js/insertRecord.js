/* 
 * David Struewing, 2015.
 */

function initialize() {

	var initialLocation;
	var zoom;

	if (document.recordForm.latitude.value === "") {
		initialLocation = new google.maps.LatLng(39.25431756494671, -95.30418115234373);
		zoom = 4;
	} else {
		initialLocation = new google.maps.LatLng(document.recordForm.latitude.value, document.recordForm.longitude.value);
		zoom = 14;
	}

	var mapOptions = {
		center: initialLocation,
		zoom: zoom
	};

	var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

	var marker = new google.maps.Marker({
		position: initialLocation,
		map: map
	});

	document.recordForm.latitude.value = marker.getPosition().lat();
	document.recordForm.longitude.value = marker.getPosition().lng();

	google.maps.event.addListener(map, 'dragend', function () {
		marker.setPosition(map.getCenter());
		document.recordForm.latitude.value = marker.getPosition().lat();
		document.recordForm.longitude.value = marker.getPosition().lng();
	});

}

google.maps.event.addDomListener(window, 'load', initialize);