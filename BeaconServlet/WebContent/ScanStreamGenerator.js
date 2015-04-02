
//********************* settings ***********************//
var $errorMsg1 = "Invalid Ticket. Your ticket was not found.";
var $errorMsg2 = "Invalid Ticket. Your ticket has already been scanned.";
var $errorMsg3 = "Invalid Ticket. Event has already passed.";

var $timesPolled = 0;
var $uniqueId = 0;			// used to id divs created from json obj

var $eventNum;		// which event do you want to display?
var $timeout;		// in milliseconds (5000 = 5 sec)
var $timePassed;	// in seconds, remember scans from this long ago
	//===> check every $timeout msec for scans that happened in the past $timePassed sec


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
//						polling							//
//******************************************************//
function poll() {
	setTimeout (function() {
		$timesPolled++;
		
		//startup activities
		if($timesPolled <= 1) {
			setSettings();
			//printSettings();
		}

		//alert("clearing #results");
		$("#results").empty();
		
		//print results
		printRoster("/BeaconServlet/api/rest/ScanEntry?eventId=" + $eventNum + "&timePassed=" + $timePassed);
		
		//recursively call poll()
		poll();

	}, $timeout);
};




//******************************************************//
//					choose event btn					//
//******************************************************//
function chooseEventClick() { 
	var $event = document.getElementById("choose-event");
	$eventNum = $event.options[$event.selectedIndex].value;
	
	//alert($event.options[$event.selectedIndex].value); 
};




//******************************************************//
//					set settings						//
//******************************************************//
function setSettings() {
	$eventNum = 1;			// which event do you want to display?
	$timeout = 2000;		// in milliseconds (5000 = 5 sec)
	$timePassed = 300000;	// in seconds, remember scans from this long ago
		//===> check every $timeout msec for scans that happened in the past $timePassed sec
};



//******************************************************//
//					print settings						//
//******************************************************//
/*function printSettings() {
	$("#settings-content").append(
		"Event: " + $eventNum + "<br>" +
		"Timeout: " + $timeout + " milliseconds<br>" + 
		"Time passed: " + $timePassed + " seconds<br><br>" +
		"<button onclick=\"editSettingsBtn()\">Edit Settings</button>"
	);
	
	//style setup
	var settings = document.getElementById("settings-content");
	
	settings.style.textAlign = 'left';
	
	settings.style.paddingLeft = '40px';
	settings.style.paddingTop = '10px';
};*/



//******************************************************//
//					edit settings btn					//
//******************************************************//
/*function editSettingsBtn() {
	var $newEvent = prompt("Enter an event id (integer)", $eventNum);
	var $newTimeout = prompt("Enter a timeout (miliseconds)", $timeout);
	var $newTimePassed = prompt("Enter a new time passed (seconds)", $timePassed);
	
	if(($newEvent != null) && 
	   ($newTimeout != null) && 
	   ($newTimePassed != null) && 
	   ($newEvent != "") && 
	   ($newTimeout != "") && 
	   ($newTimePassed != "") &&
	   ($newEvent >= 1) &&
	   ($newTimeout >= 50) &&
	   ($newTimePassed >= 1)) {
		
			//set the new values
			$eventNum = $newEvent;
			$timeout = $newTimeout;
			$timePassed = $newTimePassed;
			
			//re-print the settings
			$("#settings-content").empty();
			printSettings();
	}
	
	else {
		alert("Invalid Input. Please try again.");
	}
};*/


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
			
			var $isSuccess = item.errorMessage;
	
			//print results
			$("#results").append(
					
				"<div id=" + $uniqueId + "-wrapper>" +
				
					"<div id=" + $uniqueId + "-content>" +
					
						"<div id=" + $uniqueId + "-words>" +
							"<h1>Hello, "+ item.username +"!</h1>" +
							"<p>Visitor ID: " + item.userID + "</p>" +
							
							"<p>Scan Result: " + item.errorMessage + "</p>" +
						"</div>" +
						
						"<div id=" + $uniqueId + "-status>" +
						"</div>" +
						
					"</div>" +
					
				"</div>"
			);	//end of .append
			
			
			//style wrapper
			var wrapper = document.getElementById($uniqueId + "-wrapper");
			
			wrapper.style.display = 'inline-block';
			
			wrapper.style.borderStyle = 'solid';
			wrapper.style.borderColor = 'white';
			wrapper.style.borderWidth = '3px';
			
			wrapper.style.marginBottom = '30px';
			
			wrapper.style.width = '100%';

			
				//style content
				var content = document.getElementById($uniqueId + "-content");
			
				content.style.display = 'inline-block';
				content.style.position = 'relative';
				
				content.style.backgroundColor = 'white';
				content.style.opacity = '0.75';
				
				content.style.width = '100%';
			
				
					//style words
					var words = document.getElementById($uniqueId + "-words");
				
					words.style.cssFloat = 'left';
					words.style.display = 'block';
					
					words.style.paddingTop = '10px';		//compensate for header "next visitor"
					words.style.paddingRight = '30px';
					words.style.paddingBottom = '15px';
					words.style.paddingLeft = '30px';
					
					//style status
					var status = document.getElementById($uniqueId + "-status");
					
					status.style.position = 'absolute';		/*don't change this. makes the size of this div correct*/
					status.style.display = 'block';
					
					status.style.right = '0%';		/*the right side of the div should be all the way to the right*/
						
					status.style.width = '200px';
					status.style.height = '100%';
					
					if(($isSuccess == $errorMsg1) || ($isSuccess == $errorMsg2) || ($isSuccess == $errorMsg3)) {
						status.style.backgroundColor = '#b92e26';	/*red*/
					}
					else {
						status.style.backgroundColor = '#6b9f40';	/*green*/
					}
			
			//increment id variable
			$uniqueId++;

		}) //end of .each item
	});	//end of .then
};	//end of printRoster



