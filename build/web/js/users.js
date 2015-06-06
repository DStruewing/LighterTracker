/* 
 * David Struewing, 2015.
 */

// The input area starts out being invisible (display: none).  
// When the user clicks the edit button, javascript function requestWebUser makes this area visible
// (display: block). When the user clicks submit (after edit complete), this JSP page posts to 
// itself and the input area needs to stay visible (to show validation error messages or sucess message).
// That's why this function is called at body onload time.
function setInputArea() {
	if (document.updateForm.webUserId.value !== null &&
			document.updateForm.webUserId.value.length > 0) {
		$("inputArea").style.display = "block";
	}
}

// It is easier to call this $() function than to write document.getElementById().
function $(id) {
	return document.getElementById(id);
}

// Note: These next 9 lines of javascript are global (not in any funtion).
// This is needed for asynchronous calls.
var httpReq;
if (window.XMLHttpRequest) {
	httpReq = new XMLHttpRequest(); //For Firefox, Safari, Opera
}
else if (window.ActiveXObject) {
	httpReq = new ActiveXObject("Microsoft.XMLHTTP"); //For IE 5+.
} else {
	alert('ajax not supported');
}

// Ajax call to the server requesting the specified web user.
function requestWebUser(primaryKey) {
	httpReq.open("GET", "get_webUser_JSON.jsp?primaryKey=" + primaryKey);
	httpReq.onreadystatechange = handleResponse;
	httpReq.send(null);
}
// Ajax callback function.
function handleResponse() {

	// Make the input form visible.
	document.getElementById("inputArea").style.display = "block";

	if (httpReq.readyState == 4 && httpReq.status == 200) { // Request was successful...
		// Extract ajax response and generate object from JSON.
		var response = httpReq.responseText;
		var webUserObj = eval(response);

		// Persist form values.
		document.updateForm.webUserId.value = webUserObj.webUserId;
		document.updateForm.userEmail.value = webUserObj.userEmail;
		document.updateForm.userPw.value = webUserObj.userPw;
		document.updateForm.userPw2.value = webUserObj.userPw2;
		document.updateForm.membershipFee.value = webUserObj.membershipFee;
		document.updateForm.userRoleId.value = webUserObj.userRoleId;
		document.updateForm.birthday.value = webUserObj.birthday;

		// Clear out previous messages.
		$("updateMsg").innerHTML = "";
		$("deleteMsg").innerHTML = "";

	}
}

function cancelDone() {
	document.getElementById("inputArea").style.display = "none";
}

// This function causes the JSP page to post to itself, passing in
// the primary key of the record that should be deleted.
function deleteRow(primaryKey) {
	if (confirm("Do you really want to delete web user " + primaryKey + "?")) {
		document.deleteForm.deleteWebUserId.value = primaryKey;
		document.deleteForm.submit();
	}
}
