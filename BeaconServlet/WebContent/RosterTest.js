$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		
		//**************************//
		//		for each item		//
		//**************************//
		$.each(data, function(i, item) {
			$("#results").append(
				"<p>Visitor ID: " + item.visitor[i].id + "</p>" +
				"<p>Visitor Username: " + item.visitor[i].username + "</p>" +
				"<p>Visitor Password: " + item.visitor[i].password + "</p>" +
				"<p>First name: " + item.visitor[i].firstName + "</p>" +
				"<p>Last name: " + item.visitor[i].lastName + "</p>" +
				
				"<p>Attended? " + item.didAttend + "</p>" +
				
				"<p>Event ID: " + item.event[i].id + "</p>" +
				"<p>Event Name: " + item.event[i].name + "</p>" +
				"<p></p>"
			);			
		}) //end of .each item
		
		
	});
});