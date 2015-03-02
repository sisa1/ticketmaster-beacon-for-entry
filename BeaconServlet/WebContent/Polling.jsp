<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Polling</h1>
        <form method="POST" action="asynchPolling">
            <table>
                <tr>
                    <td>Your name:</td>
                    <td><input type="text" id="name" name="name"/></td>
                </tr>
                <tr>
                    <td>Your shout:</td>
                    <td><input type="text" id="message" name="message" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="POLL" /></td>
                </tr>
            </table>
        </form>
        <h2> Display Roster: </h2>
        <div id="content">
            <% if (application.getAttribute("entries") != null) {%>
            <%= application.getAttribute("entries")%>
            <% }%>
        </div>
    </body>
</html>