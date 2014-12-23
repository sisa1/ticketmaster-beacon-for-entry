$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/User"
	}).then(function(data) {
		$.each(data, function(i, item) {
			$("#userTable").append(
				"<tr><td>" + item.id + "</td>" +
				"<td>" + item.username + "</td>" +
				"<td>" + item.firstName + "</td>" +
				"<td>" + item.lastName + "</td></tr>"
			);
		})
	});
});