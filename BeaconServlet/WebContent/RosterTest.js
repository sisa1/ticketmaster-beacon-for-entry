$(document).ready(function(){
	$.ajax({
		url: "0/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		$.each(data, function(i, item) {
			$("#results").append(
				"<p>Visitor ID: " + item.visitor.id + "</p>" +
				"<p>First name: " + item.visitor.firstName + "</p>" +
				"<p>Last name: " + item.visitor.lastName + "</p>" +
				"<p>Event: " + item.event.id + "</p>"
			); //end of .append
		}) //end of .each
	});
});