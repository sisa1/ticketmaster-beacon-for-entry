//********************* settings ***********************//
var $errorMsg1 = "Invalid Ticket. Your ticket was not found.";
var $errorMsg2 = "Invalid Ticket. Your ticket has already been scanned.";
var $errorMsg3 = "Invalid Ticket. Event has already passed.";
var $errorMsg4 = "User does not exist - ";

var $uniqueId = 0;		//for styling the error msg readout with red/green

var $eventNum = 1;		// which event do you want to display?
var $timePassed;	// in seconds, remember scans from this long ago




//******************************************************//
//				start function call						//
//******************************************************//
//$.ajaxSetup({async: false});	// WHYYYYYYYYY
$(document).ready(main());




//******************************************************//
//						main							//
//******************************************************//
function main() {
	
	calculateTimePassed("/BeaconServlet/api/rest/EventTime/" + $eventNum);
	
	//alert("clearing #json-results");
	$("#json-results").empty();
	
	//print results
	printRoster("/BeaconServlet/api/rest/ScanEntry?eventId=" + $eventNum + "&timePassed=" + $timePassed);
	
};





//******************************************************//
//				calculate time passed					//
//******************************************************//
//set time passed to the time between now and the start time of the event
function calculateTimePassed(url) {
	var timeLength = 3000000;
	
//	$.ajax({
//		url: url
//	}).then(function(data) {
//		timeLength = data.startTime;
//	});	//end of .then
	

	$timePassed = timeLength;
};





//******************************************************//
//					choose event btn					//
//******************************************************//
function chooseEventClick() { 
	var $event = document.getElementById("choose-event");
	$eventNum = $event.options[$event.selectedIndex].value;
	
	//alert($event.options[$event.selectedIndex].value); 
	
	//reload page
	main();
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
			var $isSuccess = item.errorMessage			
			
			//print results
			$("#json-results").prepend(
				"<tr>" +
					"<td>" + item.username + "</td><td>" + item.userID + "</td><td id=" + $uniqueId + ">" + item.errorMessage + "</td><td>" + item.timeOfScanFormatted + "</td>" + 
				"</tr>"
			);	//end of .prepend
		
			//choose color of results
			if(($isSuccess == $errorMsg1) || ($isSuccess == $errorMsg2) || ($isSuccess == $errorMsg3) || ($isSuccess == $errorMsg4)) {
				document.getElementById($uniqueId).style.backgroundColor = '#b92e26';	/*red*/
				document.getElementById($uniqueId).style.color = 'white';				/*white text*/
			}
			else
				document.getElementById($uniqueId).style.backgroundColor = '#6b9f40';	/*green*/
				
			//increment ID variable
			$uniqueId++;
				
		}) //end of .each item
	
		
		//print header row
		$("#json-results").prepend(
			"<tr class=\"header-row\">" +
				"<td>Username</td>	<td>User Id</td>	<td>Scan Result</td>	<td>Time of Scan</td>" +
			"</tr>"
		);	//end of .prepend
		
		
	});	//end of .then
};	//end of printRoster