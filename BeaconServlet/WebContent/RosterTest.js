function doAJAX(url){
	$.ajax({
		url: url
	}).then(function(data) {
		
		$.each(data, function(i, item) {
			$("#results").append(
				"<h2>NEXT VISITOR:</h2>" +
				"<p>Visitor ID: " + item.visitor.id + "</p>" +
				"<p>Visitor last name: " + item.visitor.lastName + "</p>" +
				"<p>Visitor first name: " + item.visitor.firstName + "</p>" +
				"<p>Visitor Username: " + item.visitor.username + "</p>" +
				//"<p>Visitor Password: " + item.visitor.password + "</p>" +
				
				"<p>Attended? " + item.didAttend + "</p>" +
				
				"<p>Event ID: " + item.event.id + "</p>" +
				"<p>Event name: " + item.event.name + "</p>" +
				"<p></p>"
			);			
		}) //end of .each item
		
		
	});	//end of .then
};	//end of doAJAX


$(document).ready(doAJAX("/BeaconServlet/api/rest/Roster/Event/1"));
$(document).ready(doAJAX("/BeaconServlet/api/rest/Roster/Event/2"));
