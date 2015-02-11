$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		
		//**************************//
		//		for each item		//
		//**************************//
		$.each(data, function(i, item) {
			$("#results").append(
				"<p>Visitor ID: " + item.visitor[id] + "</p>" +
				"<p>Visitor last name: " + item.visitor[lastName] + "</p>" +
				"<p>Visitor first name: " + item.visitor[firstName] + "</p>" +
				"<p>Visitor Username: " + item.visitor[username] + "</p>" +
				"<p>Visitor Password: " + item.visitor[password] + "</p>" +
				
				"<p>Attended? " + item.didAttend + "</p>" +
				
				"<p>Event ID: " + item.event[id] + "</p>" +
				"<p>Event name: " + item.event[name] + "</p>" +
				"<p></p>"
			);			
		}) //end of .each item
		
		
	});
});