//******************************************************//
//					choose event btn					//
//******************************************************//
function chooseEventClick() { 
	var $event = document.getElementById("choose-event");
	//$eventNum = $event.options[$event.selectedIndex].value;
	
	alert($event.options[$event.selectedIndex].value);
};
