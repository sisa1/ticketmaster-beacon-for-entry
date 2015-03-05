
//********************* settings ***********************//
var $eventNum = 1		//which event do you want to display?
var $timeout = 1000		//in milliseconds (5000 = 5 sec)
var $timesPolled = 0;


//***************** previous data array ****************//
var $pastEntries = new Array(10);
	// holds JSON objects
	// array is indexed by the user's ID



//******************************************************//
//							main						//
//******************************************************//
$(document).ready(poll());




//******************************************************//
//				polling, do calls 5 times				//
//******************************************************//
function poll() {
	setTimeout (function() {
		$timesPolled++;
		if($timesPolled <= 5) {
			
			
			// TEST: print $timesPolled
			alert($timesPolled + " doing work, calling poll()");
			
			
			// Populate array
			populateArray("/BeaconServlet/api/rest/Roster/Event/" + $eventNum, function(){
																					alert(this);
																					// TEST: print pastEntries
																					
																					$(document).ajaxComplete(function() {
																					for(var i=0; i<$pastEntries.length; i++) {
																						alert("Printing array: " + "Polled: " + $timesPolled);
																						if($pastEntries[i] != null)
																							$("#results").append($pastEntries[i].visitor.firstName + "<br>");
																					}
																					});
																				}
						 );
			

				
			
			// Print all Roster entries
			//printRoster("/BeaconServlet/api/rest/Roster/Event/" + $eventNum);
			
			
			// Recursively call poll()
			poll();
			
			
		}
		else
			alert($timesPolled + " stopping...");
	}, $timeout);
};




//******************************************************//
//				populate the pastEntries array			//
//******************************************************//
function populateArray(url, callback){
	$.ajax({
		url: url
	}).then(function(data) {
		$.each(data, function(i, item) {
			
			// TEST: print the polling# and which user is being stored in the array
			alert("Polled: " + $timesPolled + " looking at user " + item.visitor.firstName);
			
			$pastEntries[Number(item.visitor.id)] = item;

		}) //end of .each item
	});	//end of .then
	
	callback.call("populateArray is finished.");
	
};	//end of printRoster




//******************************************************//
//			print all JSON objects in Roster			//
//******************************************************//
	// creates divs with unique IDs based on the visitor's id
	// changes color of the attended <p> based on true/false value

function printRoster(url){
	$.ajax({
		url: url
	}).then(function(data) {
		$.each(data, function(i, item) {
			var $a = item.didAttend;
			
			
			$("#results").append(
				"============================================" +
				"<div id=" + item.visitor.id + ">" +
				"<h2>NEXT VISITOR:</h2>" +
				"<p>Visitor ID: " + item.visitor.id + "</p>" +
				"<p>Visitor last name: " + item.visitor.lastName + "</p>" +
				"<p>Visitor first name: " + item.visitor.firstName + "</p>" +
				"<p>Visitor Username: " + item.visitor.username + "</p>" +
				//"<p>Visitor Password: " + item.visitor.password + "</p>" +
				
				"<p id=attended" + item.visitor.id + ">Attended? " + item.didAttend + "</p>" +
				
				"<p>Event ID: " + item.event.id + "</p>" +
				"<p>Event name: " + item.event.name + "</p>" +
				"</div>"
			);	//end of .append
			
			
			//change the css color of the attended <p>
			if($a)
				$("#attended" + item.visitor.id).css("background-color", "green");
			else
				$("#attended" + item.visitor.id).css("background-color", "red");
			

		}) //end of .each item
	});	//end of .then
};	//end of printRoster



