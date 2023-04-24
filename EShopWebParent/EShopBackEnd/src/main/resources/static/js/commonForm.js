$(document).ready(function() {
	$("#fileImage").change(function() {
		fileSize = this.files[0].size;
		if (fileSize > MAX_FILE_SIZE) {
			this.setCustomValidity("");
			this.reportValidity("");
		
		} else {
			this.setCustomValidity("");
			showImageThumbnail(this);	
		}
	});
});


function showMessageDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showErrorModal(message) {
	showMessageDialog('Error', message);	
}

function showWarningModal(message) {
	showMessageDialog('Warning', message);	
}

function showImageThumbnail(fileInput) {

	let file = fileInput.files[0];
	let reader = new FileReader();
	
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	}
	reader.readAsDataURL(file);	
}