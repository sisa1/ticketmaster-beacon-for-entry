var didStart;

$.ajaxSetup({async: false});

$(document).ready(function(){
	
	poll();
	didStart = false;
	
});

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function poll() {
	setTimeout (function() {
		
		if(!didStart) {
			$('#loadgif').hide();
		}
		
		$("#rosterTable").empty();
		
		var eventId = getParameterByName('eventId');
		
		$.ajax({
			url: ("/BeaconServlet/api/rest/Roster/Event/" + eventId)
		}).then(function(data) {
			var content = "<tr>";
			var backgroundColorAttendance = "#FFF";
			$("#title").empty();
			
			$.each(data, function(i, item) {
				if(i % 4 == 0) {
					content += "<tr>";
				}
				
				if(i == 0) {
					$("#title").append(item.event.name);
				}
				
				if(item.didAttend) {
					backgroundColorAttendance = "#AFA";
				} else {
					backgroundColorAttendance = "#FAA";
				}
				
				content +=
					"<td class ='centered'>" +
						"<div id='title-wrapper' style='background-color:" + backgroundColorAttendance + "; opacity:0.75; width:175px; height:110px;'>" +
							"<div style='font-size:20px; padding-top:5px;'>" +
								item.visitor.username +
							"</div><br />" +
							"<div>" +
								item.visitor.firstName +
							"</div>" +
							"<div>" +
								item.visitor.lastName +
							"</div>" +
						"</div>" +
					"</td>";
				if(i % 4 == 3) {
					content += "</tr>";
				}
			});
			
			$("#rosterTable").append(content);
		});
		
		//recursively call poll()
		poll();

	}, 5000);
};