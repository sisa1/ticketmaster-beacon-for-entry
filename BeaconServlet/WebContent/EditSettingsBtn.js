//******************************************************//
//					choose event btn					//
//******************************************************//
function chooseEventClick() { 
	   var event = document.getElementById("choose-event");

	   alert(event.options[event.selectedIndex].value); 
};
