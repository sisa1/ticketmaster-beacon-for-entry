$(document).ready(function(){
	$.ajax({
		url: "/BeaconServlet/api/rest/User",
		dataType:"json"
	}).then(function(data) {
		var template = $('#mustacheTemplate').html();
		var out = Mustache.render(template, data);
		$('#tableDiv').html(out);
	});
});