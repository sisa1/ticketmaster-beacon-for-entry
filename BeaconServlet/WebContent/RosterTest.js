$(document).ready(function(){
	$.ajax({
		url: "0/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		$.each(data, function(i, item) {
			$("#results").append(
				"<p>Visitor ID: " + item.visitor.id + "</p>" +
				"<p>Visitor Username: " + item.visitor.username + "</p>" +
				"<p>Visitor Password: " + item.visitor.password + "</p>" +
				"<p>First name: " + item.visitor.firstName + "</p>" +
				"<p>Last name: " + item.visitor.lastName + "</p>" +
				"<p>Attended? " + item.didAttend + "</p>" +
				"<p>Event ID: " + item.event.id + "</p>" +
				"<p>Event Name: " + item.event.name + "</p>"
			); //end of .append
		}) //end of .each
	});
});