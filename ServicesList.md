# Available Services - Website #

> ### Get all Users ###
> - HTTP GET
> - http://54.200.138.139:8080/BeaconServlet/api/rest/User

> ### Get all Events ###
> - HTTP GET
> - http://54.200.138.139:8080/BeaconServlet/api/rest/Event

> ### Get users in a specific event ###
> - HTTP GET
> - http://54.200.138.139:8080/BeaconServlet/api/rest/Roster/Event/{id}
> - example=http://54.200.138.139:8080/BeaconServlet/api/rest/Roster/Event/1


# Available Services - Mobile #

> ### Login Request ###
> - HTTP POST (String username, String password)
> - http://54.200.138.139:8080/BeaconServlet/api/rest/Login

> ### Set a ticket for an event ###
> - HTTP POST (int eventId, String username)
> - http://54.200.138.139:8080/BeaconServlet/api/rest/Roster

---


# IN PROGRESS #

> ### Get a Users at ID ###
> - HTTP GET
> - http://54.200.138.139:8080/BeaconServlet/api/rest/User/{id}
> - example = http://54.200.138.139:8080/BeaconServlet/api/rest/User/1