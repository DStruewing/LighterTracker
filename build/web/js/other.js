/* 
 * David Struewing, 2015.
 */

// The input area starts out being invisible (display: none).  
// When the user clicks the edit button javascript function requestLighter makes this area visible
// (display: block). When the user clicks submit (after edit complete), this JSP page posts to 
// itself and the input area needs to stay visible (to show validation error messages or sucess message).
// That's why this function is called at body onload time.
function setInputArea() {
	if (document.updateForm.lighterId.value != null &&
			document.updateForm.lighterId.value.length > 0) {
		$("inputArea").style.display = "block";
	}
}

// It is easier to call this $() function than having to write document.getElementById().
function $(id) {
	return document.getElementById(id);
}

// Note: These next 9 lines of javascript are global (not in any funtion).
// This is needed for asynchronous calls.
var httpReq;
if (window.XMLHttpRequest) {
	httpReq = new XMLHttpRequest();  //For Firefox, Safari, Opera
}
else if (window.ActiveXObject) {
	httpReq = new ActiveXObject("Microsoft.XMLHTTP");         //For IE 5+
} else {
	alert('ajax not supported');
}

// Ajax call to the server requesting the specified lighter.
function requestLighter(primaryKey) {
	httpReq.open("GET", "get_lighter_JSON.jsp?primaryKey=" + primaryKey);
	httpReq.onreadystatechange = handleResponse;
	httpReq.send(null);
}
// Ajax callback function.
function handleResponse() {

	// Make the update input area visible.
	document.getElementById("inputArea").style.display = "block";

	if (httpReq.readyState == 4 && httpReq.status == 200) { // Request was successful...
		// Extract ajax response and generate object from JSON.
		var response = httpReq.responseText;
		var lighterObj = eval(response);

		// Persist form values.
		document.updateForm.lighterId.value = lighterObj.lighterId;
		document.updateForm.lighterName.value = lighterObj.lighterName;
		document.updateForm.purchaseDate.value = lighterObj.purchaseDate;
		document.updateForm.lighterColor.value = lighterObj.lighterColor;
		document.updateForm.trackingCode.value = lighterObj.trackingCode;

		// Clear out previous messages.
		$("message").innerHTML = "";
		$("deleteMsg").innerHTML = "";

	}
}

function cancelDone() {
	document.getElementById("inputArea").style.display = "none";
}

// This function causes the JSP page to post to itself, passing in
// the primary key of the record that shouhld be deleted.
function deleteRow(primaryKey) {
	if (confirm("Do you really want to delete lighter " + primaryKey + "?")) {
		document.deleteForm.deleteLighterId.value = primaryKey;
		document.deleteForm.submit();
	}
}
