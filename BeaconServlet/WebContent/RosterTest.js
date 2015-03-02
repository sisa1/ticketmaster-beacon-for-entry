function doAJAX(url){
	$.ajax({
		url: url
	}).then(function(data) {
		$.each(data, function(i, item) {
			var $a = item.didAttend;
			
			
			$("#results").append(
				"============================================" +
				"<h2>NEXT VISITOR:</h2>" +
				"<p>Visitor ID: " + item.visitor.id + "</p>" +
				"<p>Visitor last name: " + item.visitor.lastName + "</p>" +
				"<p>Visitor first name: " + item.visitor.firstName + "</p>" +
				"<p>Visitor Username: " + item.visitor.username + "</p>" +
				//"<p>Visitor Password: " + item.visitor.password + "</p>" +
				
				"<p id=attended" + i + ">Attended? " + item.didAttend + "</p>" +
				
				"<p>Event ID: " + item.event.id + "</p>" +
				"<p>Event name: " + item.event.name + "</p>"
			);	//end of .append
			
			//change the css color of the attended <p>
			if($a)
				$("#attended" + i).css("background-color", "green");
			else
				$("#attended" + i).css("background-color", "red");
			

		}) //end of .each item
	});	//end of .then
};	//end of doAJAX


$(document).ready(doAJAX("/BeaconServlet/api/rest/Roster/Event/1"));	//only event 1 has tuples for now
//$(document).ready(doAJAX("/BeaconServlet/api/rest/Roster/Event/2"));


