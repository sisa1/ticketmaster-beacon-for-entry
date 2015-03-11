$(document).ready(function(){
	
	// Form to add a camera is submitted 
	$("form#addForm").submit(function() {
		// Setup data for Ajax call (Pass in all camera parameters)
		var restUrl = ("/BeaconServlet/api/rest/Event");
		var myData = $("form#addForm").serialize();
		
		// Make Ajax call to create a new camera (remove id from POST parameter)
		$.ajax({
			type: 'POST',
			url: restUrl,
			data:myData,
			complete:function() {
				alert("Create request sent...");
			}
		});
		
	});// end form.submit()
	
});// end document.ready()