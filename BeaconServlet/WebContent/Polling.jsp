<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Poll Roster</h1>
        <form>
            <table>
                <!-- <tr>
                    <td>Your name:</td>
                    <td><input type="text" id="name" name="name"/></td>
                </tr>
                <tr>
                    <td>Your shout:</td>
                    <td><input type="text" id="message" name="message" /></td>
                </tr> -->
                <tr>
                    <td><input type="button" onclick="poll();" value="POLL" /></td>
                </tr>
            </table>
        </form>
        <h2> Roster: </h2>
        <div id="content">
            <% if (application.getAttribute("username") != null) {%>
            <%= application.getAttribute("username")%>
            <% }%>
        </div>
        <script>
            function poll() {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("POST", "asynchPolling?t="+new Date(), true);
                xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xmlhttp.send();
                //var nameText = escape(document.getElementById("name").value);
                //var messageText = escape(document.getElementById("message").value);
                //document.getElementById("message").value = "";
                //xmlhttp.send("name="+nameText+"&message="+messageText);
            }
            //setInterval(poll, 1000);
            /* var messagesWaiting = false;
            function getMessages(){
                if(!messagesWaiting){
                    messagesWaiting = true;
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function(){
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                            messagesWaiting = false;
                            var contentElement = document.getElementById("content");
                            contentElement.innerHTML = xmlhttp.responseText + contentElement.innerHTML;
                        }
                    }
                    xmlhttp.open("GET", "asynchPolling?t="+new Date(), true);
                    xmlhttp.send();
                }
            }
            setInterval(getMessages, 1000); */
        </script>
    </body>
</html>