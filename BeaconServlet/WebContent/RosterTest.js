
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
$.ajaxSetup({async: false});	// SET AJAX TO BE SYNCHRONOUS
$(document).ready(poll());




//******************************************************//
//				polling, do calls 5 times				//
//******************************************************//
function poll() {
	setTimeout (function() {
		$timesPolled++;
		if($timesPolled <= 1) {
			
			
			
			// TEST: print $timesPolled
			//alert($timesPolled + " doing work, calling poll()");
			
			
			
			// The first time:	print first entries
			//					save first entries in the array
			if($timesPolled == 1) {
				// Print all Roster entries
				printRoster("/BeaconServlet/api/rest/Roster/Event/" + $eventNum);
				
				// Populate array
				populateArray("/BeaconServlet/api/rest/Roster/Event/" + $eventNum);
			}
			
			
			
			else {
			
				alert("Starting else stmt");
				
				// Check for differences between 
				compareInputToArray("/BeaconServlet/api/rest/Roster/Event/" + $eventNum);
	
				
				// TEST: print populated array
				$("#results").append("Printing the saved array:<br>");
				for(var i=0; i<$pastEntries.length; i++) {
					//alert("Printing array: " + "Polled: " + $timesPolled);
					if($pastEntries[i] != null)
						$("#results").append("Poll number " + $timesPolled + ", first name: " + $pastEntries[i].visitor.firstName + "<br>");
				}
			}
			

			// Recursively call poll()
			poll();
			
			
		}
		//else
			//alert($timesPolled + " stopping...");
	}, $timeout);
};




//******************************************************//
//		compare the JSON to the $pastEntries array		//
//******************************************************//
function compareInputToArray(url) {
	$.ajax({
		url: url
	}).then(function(data) {
		$.each(data, function(i, item) {
		
			// Is this item a new item?
				// yes: print it
				//		record it in array
			if($pastEntries[item.visitor.id] == null) {
				//printNewEntry(item);
				//addEntryToArray(item);
			}
				// no: does this item need to be updated?
					// yes: change the value of that item directly in the html
					//		change the value in the array to make note of it
					// no: nothing
			else {
				var $differences = detectDifferences($pastEntries[item.visitor.id], item);
				// 0 in an index = no difference, 1 in an index = there is a difference
				
				
				// 0 here = no differences exist for this item
				if($differences[0] == 0)
					{}
				
				else {
					// different first name
					if($differences[1] == 1) {
						
					}
					
					// different last name
					if($differences[2] == 1) {
						
					}
					
					// different visitor id
					// shouldn't happen. This would mess up indexing, etc...
					if($differences[3] == 1) {
						
					}
					
					// different username
					if($differences[4] == 1) {
						
					}
					
					// different password
					if($differences[5] == 1) {
						
					}
					// different didAttend
					// most common change
					if($differences[6] == 1) {
						
					}
					
					// different event id
					if($differences[7] == 1) {
						
					}
					
					// different event name
					if($differences[8] == 1) {
						
					}
				}
			}
			
			$pastEntries[Number(item.visitor.id)] = item;
		
		}) //end of .each item
	});	//end of .then	
};




//******************************************************//
//			check differences between two inputs		//
//******************************************************//
function detectDifferences(pastItem, newItem) {
	var returnArray = [0, 0, 0, 0, 0, 0, 0, 0, 0];
	
	if(pastItem.Visitor.firstName == newItem.Visitor.firstName)
		returnArray[1] = 1;
	if(pastItem.Visitor.lastName == newItem.Visitor.lastname)
		returnArray[2] = 1;
	if(pastItem.Visitor.id == newItem.Visitor.id)
		returnArray[3] = 1;
	if(pastItem.Visitor.username == newItem.Visitor.username)
		returnArray[4] = 1;
	if(pastItem.Visitor.password == newItem.Visitor.password)
		returnArray[5] = 1;
	if(pastItem.didAttend == newItem.didAttend)
		returnArray[6] = 1;
	if(pastItem.Event.id == newItem.Event.id)
		returnArray[7] = 1;
	if(pastItem.Event.name == newItem.Event.name)
		returnArray[8] = 1;
	
	return returnArray;
};




//******************************************************//
//				populate the pastEntries array			//
//******************************************************//
function populateArray(url) {
	$.ajax({
		url: url
	}).then(function(data) {
		$.each(data, function(i, item) {
			
			// TEST: print the polling# and which user is being stored in the array
			//alert("Polled: " + $timesPolled + " entering user " + item.visitor.firstName + " into array");
			
			$pastEntries[Number(item.visitor.id)] = item;

		}) //end of .each item
	});	//end of .then	
};




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



