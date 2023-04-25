function addMoreDetails() {


	allDivDetails = $("[id^='divDetail']");
	divDetailsCount = allDivDetails.length;
	 
	htmlDetailSection = `
	<div class="form-inline" id="divDetail${divDetailsCount}">
				<label class="m-3">Name: </label>
				<input type="text" class="form-control" name="detailNames" maxlength="255"/>
				
				<label class="m-3">Value: </label>
				<input type="text" class="form-control" name="detailValues" maxlength="255"/>
			</div>
	`;
	
	
	$("#productDetails").append(htmlDetailSection);
	
	
	previousDivDetailSection = allDivDetails.last();
	previousDivDetailId = previousDivDetailSection.attr("id"); 

	htmlRemoveLink = `
		<a 
			href="javascript:removeDetailSectionById('${previousDivDetailId}')"			 
			class="fas fa-times-circle fa-2x icon-dark btn"
			title="remove this detail"></a>`;
			
	previousDivDetailSection.append(htmlRemoveLink); 
}

function removeDetailSectionById(id) {
	$("#"+id).remove();	
}