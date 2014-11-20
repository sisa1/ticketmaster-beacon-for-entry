<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>

<HEAD>
	<title>Beacon Project Web UI</title>
	<link type="text/css" rel="stylesheet" href="Main.css"/>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</HEAD>


<BODY>

<!-------------------------------------------------------------------->
<div class="top-bar">
	<div id="top-title">
		<p>Web UI</p>
	</div>
	<div id="top-menu">
		<ul>
			<li>	<a href="#">User Login</a>	</li>
			<li>	<a href="#">Admin Login</a>	</li>
		</ul>
	</div>
</div>

<!-------------------------------------------------------------------->
<table>
	<tr>
		<td id="left-col">
			<div id="hor-gradient"></div> <!-- div to create a horiz gradient for the menu -->

			<ul>
				<li>	<a href="#">Home</a>	</li>
				<li>	<a href="#">Scan Stream</a>		</li>
			</ul>
		</td>

		<td id="center-col">
			<div id="center-header">
				<h2>Scan Stream:</h2>
				<p>Refresh the page to see the updated list of scans.</p>
			</div>

			<div id="single-user">
				<div id="important-user-info">
					<b>Name:</b> John Smith	<br> <br>
					<b>Username:</b> JSmith0123
				</div>
				<div id="entry-result-success">
					<p>SUCCESS!</p>
				</div>
			</div>

			<div id="single-user">
				<div id="important-user-info">
					<b>Name:</b> Jane Doe 	<br> <br>
					<b>Username:</b> JDoe2014
				</div>
				<div id="entry-result-failed">
					<p>FAILED!</p>
				</div>
			
			</div>
		</td>

		<td id="right-col">
			<p>javascript test:</p>
			<script>
			document.write(Date());
			</script>
			
			<form action="/WebUIServlet" method="POST">
			
		</td>
	</tr>
</table>

<!-------------------------------------------------------------------->
<div class="bottom-bar">
	<h3>bottom-bar</h3>
</div>

</BODY>

</HTML>