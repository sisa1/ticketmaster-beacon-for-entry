
//********************* settings ***********************//
//var $eventNum;
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
	setSettings("asdf", "123", "123");
	printSettings();
	generate("http://54.200.138.139:8080/BeaconServlet/api/rest/QRGen?UUID=" + $uuid + "&Major=" + $major + "&Minor=" + $minor);
};




//******************************************************//
//					set settings						//
//******************************************************//
function setSettings(uuid, major, minor) {
	//$eventNum = 1;
	$uuid = uuid;
	$major = major;
	$minor = minor;
};



//******************************************************//
//					print settings						//
//******************************************************//
function printSettings() {
	$("#settings").append(
		//"Event: " + $eventNum + "<br>" +
		"UUID: " + $uuid + "<br>" +
		"Major: " + $ajor + "<br>" + 
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
};




//******************************************************//
//					edit settings btn					//
//******************************************************//
function editSettingsBtn() {
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
};





//******************************************************//
//					generate QR code					//
//******************************************************//
function generate(url) {
	var $QRdiv = getElementById("QR");
	$QRdiv.innerHTMl = '<iframe style="width:100%;height:100%;" frameborder="0" src="' + url + '" />';
};