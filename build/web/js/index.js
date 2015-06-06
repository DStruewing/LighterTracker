// ID of the currently logged in user.
var webUserId = document.getElementById("webUserId").innerHTML;

// Global ajax variable.
var httpReq;
if (window.XMLHttpRequest) {
	httpReq = new XMLHttpRequest();
}
else if (window.ActiveXObject) {
	httpReq = new ActiveXObject("Microsoft.XMLHTTP");
} else {
	alert('ajax not supported');
}

function submitLighterCode() {
	document.getElementById("lighterCodeMsg").style.display = "none";
	if (webUserId !== "") { // User is logged in.
		httpReq.open("GET", "get_lighter_JSON.jsp?lighterCode=" + document.startForm.lighterCode.value);
		httpReq.onreadystatechange = handleResponse;
		httpReq.send(null);
	} else { // User is not logged in.
		alert("Please login to your account before creating a new record.");
		return;
	}
}

function handleResponse() {
	if (httpReq.readyState === 4 && httpReq.status === 200) { // Request was successful...
		// Extract ajax response and generate object from JSON.
		var response = httpReq.responseText;
		var lighterObj = eval(response);

		if (lighterObj.lighterId === "") { // Lighter code is not valid.
			document.getElementById("lighterCodeMsg").style.display = "block";

		} else { // Lighter code is valid.

			var latitude = "";
			var longitude = "";

			// Try to get geolocation.
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function (position) { // Location was found.
					var location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
					latitude = location.lat();
					longitude = location.lng();
					nextPage();
				}, function () {
					nextPage();
				}); // Location was not found, do nothing.
			} else {
				nextPage();
			} // Browser doesn't support Geolocation, do nothing.

			// Redirect to the insert record page, passing in the lighter ID,
			// webUserId, date, latitude, longitude, and the first rendering flag.
			function nextPage() {
				window.location.href = "insertRecord.jsp?lighterId=" + lighterObj.lighterId
						+ "&webUserId=" + webUserId
						+ "&date=" + new Date().toJSON().slice(0, 10)
						+ "&latitude=" + latitude
						+ "&longitude=" + longitude
						+ "&firstRendering=true";
			}
		}
	}
}
