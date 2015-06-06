/* 
 * David Struewing, 2015.
 */

var currentSlide = 1; // Keeps track of which slide is showing on the insert record form.
var lighterCode, lighterId, email, password, webUserId, date, latlng; // Global variables for inserting a record.

date = mysqlDate(); // Get the current date in mysql format.

// Global AJAX HTTP request object.
var httpReq;
if (window.XMLHttpRequest) {
	httpReq = new XMLHttpRequest(); //For Firefox, Safari, Opera
}
else if (window.ActiveXObject) {
	httpReq = new ActiveXObject("Microsoft.XMLHTTP"); //For IE 5+.
} else {
	alert('ajax not supported');
}

// Called each time the user clicks "Next" on the insert record form.
function next() {
	$("progressSlide").style.display = "block"; // Show progress indicator.
	makeAjaxCall();
}

// Takes care of making the appropriate ajax call when the user clicks "Next".
function makeAjaxCall() {
	switch (currentSlide) {
		case 1:
			// Check if lighter exists.
			lighterCode = $("lighterCodeInput").value;
			httpReq.open("GET", "check_lighter_code.jsp?lighterCode=" + lighterCode);
			break;
		case 2:
			// Attempt login.
			email = $("emailInput").value;
			password = $("passwordInput").value;
			httpReq.open("GET", "attempt_login.jsp?email=" + email + "&password=" + password);
			break;
		case 3:
			// Insert the record.
			httpReq.open("GET", "insert_record.jsp?"
					+ "lighterId=" + lighterId
					+ "&webUserId=" + webUserId
					+ "&date=" + date
					+ "&latitude=" + latlng.lat()
					+ "&longitude=" + latlng.lng());
			break;
	}
	httpReq.onreadystatechange = ajaxCallback;
	httpReq.send(null);
}

// Takes care of extracting data from ajax responses and showing error messages.
function ajaxCallback() {
	if (httpReq.readyState === 4 && httpReq.status === 200) { // Ajax call was successful.
		var responseObj = eval("(" + httpReq.responseText + ")");
		if (responseObj.errorMsg) { // If there was an error...
			if (responseObj.errorMsg === "Not found") { // If the lighter or webUser was simply not found...
				$("errorMsg" + currentSlide).style.visibility = "visible";
				$("progressSlide").style.display = "none";
			} else { // Else if there was a real error...
				showResultSlide(false);
			}
		} else { // Else the ajax call was successful.
			switch (currentSlide) {
				case 1: // Extract lighterId
					lighterId = responseObj.lighterId;
					showNextSlide();
					break;
				case 2: // Extract webUserId
					webUserId = responseObj.webUserId;
					// We need to prepare the map *before* showing slide 3.
					prepareMap(showNextSlide);
					break;
				case 3:
					var errorMsg = responseObj.errorMsg;
					if (errorMsg) {
						showResultSlide(false);
					} else {
						showResultSlide(true);
					}
			}
		}
	}
}

function showNextSlide() {
	$("slide" + currentSlide).style.display = "none"; // Hide current slide.
	currentSlide++;
	$("progressSlide").style.display = "none"; // Hide progress slide.
	$("slide" + currentSlide).style.display = "block"; // Show next slide.
}

// Shows the final result slide with either success or failure.
function showResultSlide(success) {
	if (success) {
		$("resultText").innerHTML = "Thanks!";
		$("successImage").style.display = "block";
	} else {
		$("resultText").innerHTML = "Something went wrong.<br />Please try again.";
		$("failureImage").style.display = "block";
	}
	$("slide" + currentSlide).style.display = "none";
	$("progressSlide").style.display = "none";
	$("resultSlide").style.display = "block";
}

// Default map options.
latlng = new google.maps.LatLng(39.2543, -95.3041);
var zoom = 4;

// Uses geolocation to get the user's location.
function prepareMap(callback) {
	if (navigator.geolocation) { // If geolocation is supported...
		navigator.geolocation.getCurrentPosition(function (position) { // Location was found...
			// Set the latlng to the found location.
			latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			zoom = 14; // Closer zoom.
			showMap(callback);
		}, function () { // Location was not found...
			showMap(callback);
		});
	} else { // Geolocation is not supported...
		showMap(callback);
	}
}

// Renders the map and places it on the slide.
// We need this to happen after the user's location has been found,
// so it needs to be in a separate function.
function showMap(callback) {
	var mapOptions = {
		center: latlng,
		zoom: zoom
	};
	var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	var marker = new google.maps.Marker({// Marker in the center of the map.
		position: latlng,
		map: map
	});
	// This event listener will move the marker each time the user moves the map.
	google.maps.event.addListener(map, 'dragend', function () {
		marker.setPosition(map.getCenter());
		latlng = map.getCenter();
	});
	callback(); // Map rendering is done.
}

// Creates a date string in mysql format (YYYY-MM-DD) from a given
// date if one is provided, or the current date if one is not.
function mysqlDate(date) {
	date = date || new Date();
	return date.toISOString().split('T')[0];
}

// Utility function.
function $(id) {
	return document.getElementById(id);
}

function showNewAccountForm() {
	$("loginForm").style.display = "none";
	$("newAccountForm").style.display = "block";
}

function login() {
	var email = $("loginForm").email.value;
	var password = $("loginForm").password.value;
	httpReq.open("GET", "attempt_login.jsp?email=" + email + "&password=" + password);
	httpReq.onreadystatechange = loginCallback;
	httpReq.send(null);
}

function loginCallback() {
	if (httpReq.readyState === 4 && httpReq.status === 200) { // Ajax call was successful.
		var responseObj = eval("(" + httpReq.responseText + ")");
		if (responseObj.error) {
			$("newAccountLink").style.display = "none";
			$("loginError").style.display = "block";
		} else {
			$("login-link").style.display = "none";
			$("welcome-message").innerHTML = "Hello, " + responseObj.name + ".";
			$("welcome-message").style.display = "block";
		}
	}
}

function newAccount() {
	var name = $("newAccountForm").name.value;
	var email = $("newAccountForm").email.value;
	var password1 = $("newAccountForm").password1.value;
	var password2 = $("newAccountForm").password2.value;
	httpReq.open("GET", "insert_web_user.jsp?name=" + name
			+ "&email=" + email
			+ "&password1=" + password1
			+ "&password2=" + password2);
	httpReq.onreadystatechange = newAccountCallback;
	httpReq.send(null);
}

function newAccountCallback() {
	if (httpReq.readyState === 4 && httpReq.status === 200) { // Ajax call was successful.
		var responseObj = eval("(" + httpReq.responseText + ")");
		if (responseObj.success) {
			$("login-link").style.display = "none";
			$("welcome-message").innerHTML = "Welcome, " + responseObj.name + ".";
			$("welcome-message").style.display = "block";
		} else {
			if (responseObj.emailError || responseObj.duplicateError) {
				$("newAccountForm").email.style.border = "1px solid red";
			}
			if (responseObj.passwordError) {
				$("newAccountForm").password1.style.border = "1px solid red";
				$("newAccountForm").password2.style.border = "1px solid red";
			}
		}
	}
}