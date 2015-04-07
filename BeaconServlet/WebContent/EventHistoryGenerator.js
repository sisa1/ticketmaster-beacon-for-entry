//********************* settings ***********************//
var $errorMsg1 = "Invalid Ticket. Your ticket was not found.";
var $errorMsg2 = "Invalid Ticket. Your ticket has already been scanned.";
var $errorMsg3 = "Invalid Ticket. Event has already passed.";
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
	
	calculateTimePassed();
	
	//alert("clearing #json-results");
	$("#json-results").empty();
	
	//print results
	printRoster("/BeaconServlet/api/rest/ScanEntry?eventId=" + $eventNum + "&timePassed=" + $timePassed);
	
};





//******************************************************//
//				calculate time passed					//
//******************************************************//
//set time passed to the time between now and the start time of the event
function calculateTimePassed() { 
	$timePassed = 3000000;
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
		
		
		//print header row
		$("#json-results").append(
			"<tr class=\"header-row\">" +
				"<td>Username</td>	<td>User Id</td>	<td>Scan Result</td>	<td>Time of Scan</td>" +
			"</tr>"
		);	//end of .append	
		
		
		$.each(data, function(i, item) {		
			var $isSuccess = item.errorMessage			
			
			//print results
			$("#json-results").append(
				"<tr>" +
					"<td>" + item.username + "</td><td>" + item.userID + "</td><td id=" + $uniqueId + ">" + item.errorMessage + "</td><td>" + item.timeOfScan + "</td>" + 
				"</tr>"
			);	//end of .append
		
			if(($isSuccess == $errorMsg1) || ($isSuccess == $errorMsg2) || ($isSuccess == $errorMsg3)) {
				document.getElementById($uniqueId).style.backgroundColor = '#b92e26';	/*red*/
				document.getElementById($uniqueId).style.color = 'white';
			}
			else
				document.getElementById($uniqueId).style.backgroundColor = '#6b9f40';	/*green*/
				
			//increment ID variable
			$uniqueId++;
				
		}) //end of .each item
		
		
	});	//end of .then
};	//end of printRoster