/* 
 * David Struewing, 2015.
 */

// This function causes the JSP page to post to itself, passing in
// the primary key of the record that shouhld be deleted.
function deleteRow(primaryKey) {
	if (confirm("Do you really want to delete tracking record " + primaryKey + "?")) {
		document.deleteForm.deleteTrackingRecordId.value = primaryKey;
		document.deleteForm.submit();
	}
}