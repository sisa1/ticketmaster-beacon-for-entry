$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		
		//**************************//
		//		for each item		//
		//**************************//
		$.each(data, function(i, item) {
			$("#results").append(
				"<p>Visitor ID: " + item[0].id + "</p>"
/*				"<p>Visitor last name: " + item[0].lastName + "</p>" +
				"<p>Visitor first name: " + item[0].firstName + "</p>" +
				"<p>Visitor Username: " + item[0].username + "</p>" +
				"<p>Visitor Password: " + item[0].password + "</p>" +
				
				"<p>Attended? " + item.didAttend + "</p>" +
				
				"<p>Event ID: " + item[2].id + "</p>" +
				"<p>Event name: " + item[2].name + "</p>" +
				"<p></p>"*/
			);			
		}) //end of .each item
		
		
	});
});