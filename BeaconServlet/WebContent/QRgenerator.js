
//********************* settings ***********************//
var $eventNum = 1;
var $uuid;
var $major;
var $minor;

//******************************************************//
//				start function call						//
//******************************************************//
$.ajaxSetup({async: false});	// SET AJAX TO BE SYNCHRONOUS
$(document).ready(main());




//******************************************************//
//							main						//
//******************************************************//
function main() {
	
	//get beacon's ID numbers based on event num
	getAndSetBeaconInfo("http://54.200.138.139:8080/BeaconServlet/api/rest/Beacon/" + $eventNum);
	
//	if($eventNum == 1)
//		setSettings("asdf", "123", "123");
//	else
//		setSettings("lkjh", "321", "321");

	generate("http://54.200.138.139:8080/BeaconServlet/api/rest/QRGen?UUID=" + $uuid + "&Major=" + $major + "&Minor=" + $minor);
};



//******************************************************//
//				getAndSetBeaconInfo						//
//******************************************************//
function getAndSetBeaconInfo(url){
	$.ajax({
		url: url,
	}).then(function(data) {
		
		var $newUuid = data.item.uuid;
		var $newMajor = data.item.major;
		var $newMinor = data.item.minor;
		
		setSettings($newUuid, $newMajor, $newMinor);
		
		
	});	//end of .then	
};



//******************************************************//
//					set settings						//
//******************************************************//
function setSettings(uuid, major, minor) {
	$uuid = uuid;
	$major = major;
	$minor = minor;
};



//******************************************************//
//					print settings						//
//******************************************************//
/*function printSettings() {
	$("#settings").append(
		//"Event: " + $eventNum + "<br>" +
		"UUID: " + $uuid + "<br>" +
		"Major: " + $major + "<br>" + 
		"Minor: " + $minor + "<br><br>" +
		"<button onclick=\"editSettingsBtn()\">Edit Settings</button>"
	);

//	//style setup
//	var settings = document.getElementById("settings-content");
//	
//	settings.style.textAlign = 'left';
//	
//	settings.style.paddingLeft = '40px';
//	settings.style.paddingTop = '10px';
};*/




//******************************************************//
//					edit settings btn					//
//******************************************************//
/*function editSettingsBtn() {
	var $newUuid = prompt("Enter a UUID (string)", $uuid);
	var $newMajor = prompt("Enter a timeout (integer)", $major);
	var $newMinor = prompt("Enter a new time passed (integer)", $minor);
	
	if(($uuid != null) && 
		($major != null) && 
		($minor != null) && 
		($uuid != "") && 
		($major != "") && 
		($minor != "")) {
	
			//set the new values
			setSettings($newUuid, $newMajor, $newMinor);
			
			//re-print the settings
			$("#settings").empty();
				printSettings();
		}
	
	else {
		alert("Invalid Input. Please try again.");
	}
};*/



//******************************************************//
//					choose event btn					//
//******************************************************//
function chooseEventClick() { 
	//find selected event
	var $event = document.getElementById("choose-event");
	$eventNum = $event.options[$event.selectedIndex].value;
	
	//alert($event.options[$event.selectedIndex].value);
	
	//clear div, reload page
	$("#results").empty();
	main();
};




//******************************************************//
//					generate QR code					//
//******************************************************//
function generate(url) {
    clearTimeout(window.ht);
    window.ht = setTimeout(function(){
		var $QRdiv = document.getElementById("results");
		$QRdiv.innerHTML = '<iframe style="width:100%;height:100%;" frameborder="0" src="' + url + '" />';
    },20);
};