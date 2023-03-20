function showMessageDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showErrorModal(message) {
	showMessageDialog('Error', message)	
}

function showWarningModal(message) {
	showMessageDialog('Warning', message)	
}
