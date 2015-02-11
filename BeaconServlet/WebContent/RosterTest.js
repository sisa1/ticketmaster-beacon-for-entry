$(document).ready(function(){
	$.ajax({
		url: "0/BeaconServlet/api/rest/Roster/Event/1"
	}).then(function(data) {
		
		//**************************//
		//		for each item		//
		//**************************//
		$.each(data, function(i, item) {
			
			//******************************//
			//	for each visitor, append...	//
			//******************************//
			$.each(item.visitor, function(x, y) {
				$("#results").append(
					"<p>Visitor ID: " + y.id + "</p>" +
					"<p>Visitor Username: " + y.username + "</p>" +
					"<p>Visitor Password: " + y.password + "</p>" +
					"<p>First name: " + y.firstName + "</p>" +
					"<p>Last name: " + y.lastName + "</p>"
				);  //end of .append
			}) //end of .each visitor
			
			//**************************//
			//		append attended?	//
			//**************************//
			$("#results").append(
					"<p>Attended? " + item.didAttend + "</p>"
			);
			
			//******************************//
			//	for each event, append...	//
			//******************************//
			$.each(item.event, function(x, y) {
				$("#results").append(
					"<p>Event ID: " + y.id + "</p>" +
					"<p>Event Name: " + y.name + "</p>"
				);
			}) //end of .each event
		}) //end of .each item
	});
});