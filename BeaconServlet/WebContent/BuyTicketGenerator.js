//
////********************* settings ***********************//
//var $errorMsg1 = "Invalid Ticket. Your ticket was not found.";
//var $errorMsg2 = "Invalid Ticket. Your ticket has already been scanned.";
//var $errorMsg3 = "Invalid Ticket. Event has already passed.";
//
//var $timesPolled = 0;
//var $uniqueId = 0;			// used to id divs created from json obj
//
//var $eventNum;		// which event do you want to display?
//var $timeout;		// in milliseconds (5000 = 5 sec)
//var $timePassed;	// in seconds, remember scans from this long ago
//	//===> check every $timeout msec for scans that happened in the past $timePassed sec



//******************************************************//
//					start function						//
//******************************************************//
$.ajaxSetup({async: false});	// SET AJAX TO BE SYNCHRONOUS
$(document).ready(main());



//******************************************************//
//							main						//
//******************************************************//
function main() {
	var form = $('#inputform');
	
	$(form).submit(function(event) {
		
		//get POST call inputs
		var restUrl = ("/BeaconServlet/api/rest/User");
		var myData = $(form).serialize();
		
		// POST call to create new user rest service at restUrl
		$.ajax({
			type:	'POST',
			url:	restUrl,
			data:	myData,
			complete:function() {
				alert("Create request sent...");
			}
		});
		
	});// end form.submit()
	
};