$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/Roster/Event/1"
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
					"<p>Visitor ID: " + y[0] + "\n" +
					"Visitor last name: " + y[1] + "\n" +
					"Visitor first name: " + y[2] + "\n" +
					"Visitor username: " + y[3] + "\n" +
					"Visitor password: " + y[4] + "</p>"
				);
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
			$.each(item.event, function(a, b) {
				$("#results").append(
					"<p>Event ID: " + b.id + "\n" +
					"Event Name: " + b.name + "</p>" +
					"<p></p>"
				);
			}) //end of .each event
			
		}) //end of .each item
	});
});