$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		
		//**************************//
		//		for each item		//
		//**************************//
		$.each(data, function(i, item) {
			$("#results").append(
				"<p>Visitor ID: " + item.visitor[0] + "</p>" +
				"<p>Last name: " + item.visitor[1] + "</p>" +
				"<p>First name: " + item.visitor[2] + "</p>" +
				"<p>Visitor Username: " + item.visitor[3] + "</p>" +
				"<p>Visitor Password: " + item.visitor[4] + "</p>" +
				
				"<p>Attended? " + item.didAttend + "</p>" +
				
				"<p>Event ID: " + item.event[0] + "</p>" +
				"<p>Event Name: " + item.event[1] + "</p>" +
				"<p></p>"
			);			
		}) //end of .each item
		
		
	});
});