// Function used to get parameters from URL
function getUrlParameter(sParam)
{
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for(var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam) {
			return sParameterName[1];
		}
	}
}

$(document).ready(function(){
	// Get RESTful service's URL with an id parameter
	var camId = getUrlParameter('id');
	var restUrl = ("/BeaconServlet/api/rest/EventBeacon/"+camId);
	
	// Make Ajax call to RESTful service to get camera's current info
	$.ajax({
		url: restUrl
	}).then(function(data) {
		var template = $('#mustacheTemplate').html();
		var out = Mustache.render(template, data);
		$('#tableDiv').html(out);
	});
	
	// Call when form is submitted
	$("form#editForm").submit(function(event) {
		event.preventDefault();
		
		// Set data for Ajax call
		var restUrl = ("/BeaconServlet/api/rest/EventBeacon");
		var myData = $("form#editForm").serialize();
		
		// Make Ajax call to update camera via RESTful POST method
		$.ajax({
			type: 'POST',
			url: restUrl,
			data:myData,
			dataType: "json",
			complete:function() {
				alert("Update request sent...");
			}
		});
	});// end form.submit()
	
});// end document.ready()

