$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/User"
	}).then(function(data) {
		$.each(data, function(i, item) {
			$("#stream-content").append(
				"<p>ID number: " + item.id + "</p>" +
				"<p>Username: " + item.username + "</p>" +
				"<p>First name: " + item.firstName + "</p>" +
				"<p>Last name: " + item.lastName + "</p>"
			);
		})
	});
});