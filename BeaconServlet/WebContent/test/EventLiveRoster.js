function getUrlParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}  

function doAJAX(url){
	$.ajax({
		url: url
	}).then(function(data) {
		$("#results").replaceWith("<div id='results'></div>");
		$.each(data, function(i, item) {
			var $a = item.didAttend;
			$("#results").append(
				"<br/>-----------------<span style='font-weight:bold;'>"+ item.visitor.username + "</span>-----------------" +
				"<table>" +
				"<tr><td>Last Name:</td> <td>" + item.visitor.lastName + "</td></tr>" +
				"<tr><td>First name: </td> <td>" + item.visitor.firstName + "</td></tr>" +
				"<tr id=attended" + i + "><td>Scanned:</td><td>" + item.didAttend + "</td></tr></table>"
			);	//end of .append
			
			//change the css color of the attended <p>
			if($a)
				$("#attended" + i).css("background-color", "#0F0");
			else
				$("#attended" + i).css("background-color", "#F00");
		}) //end of .each item
	});	//end of .then
};	//end of doAJAX


$(document).ready(function() {
	var ajaxData;
	var eventID = getUrlParameter('event');
	doAJAX("/BeaconServlet/api/rest/Roster/Event/" + eventID);
	
	$.ajax({
		url: ("/BeaconServlet/api/rest/Event/" + eventID)
	}).then(function(data) {
		$("#title").append(data.name);
	});	//end of .then
	
	setInterval(function() {
		doAJAX("/BeaconServlet/api/rest/Roster/Event/" + eventID);
	}, 2500);
});



