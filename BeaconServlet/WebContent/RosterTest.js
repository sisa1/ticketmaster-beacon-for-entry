$(document).ready(function(){
	$.ajax({
		url: "0/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		$.each(data, function(i, item) {
			$("#results").append(
				"<p>Visitor: " + item.visitor + "</p>" +
				"<p>Event: " + item.event + "</p>" +
				"<p>First name: " + item.firstName + "</p>" +
				"<p>Last name: " + item.lastName + "</p>"
			); //end of .append
		}) //end of .each
	});
});