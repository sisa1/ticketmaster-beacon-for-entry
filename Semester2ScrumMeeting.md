# Scrum Meet 1 #

> ## 1/23 ##

> Attendees: Matt, Shelby, Tan

> ## What did we do ##
    * Overview of Capstone

> ## What are we going to do ##
    * Meet with Sponsor
    * Jenkins tool update
    * Data structure meeting

---

# Sponsor Meet #

> ## 1/26 ##

> Attendees: Danon (Sponsor), Matt, Donny, Tan, Zhe, Shelby (All)

> ## What did we do ##
    * Discuss data structure
    * Review:
      * HTTPS works
      * Reorganized Asana
    * Beacons available next week
    * Keep scope small

> ## What are we going to do ##
    * Android needs a repository (Git? Google code?)
    * Data transfer between servlet and phone app
    * Have our data structure reviewed once we make it
    * Create more user stories (See google doc)
      * implement them

> ## Details of what we discussed ##
> > https://docs.google.com/a/asu.edu/document/d/14mBzLN8mtDPHOwEfo7OQBcHut-2cY0ftjcVwIWUNGCE/edit

---


# Scrum Meet 2 #


> ## 2/6 ##

> Attendees: All (Matt, Tan, Zhe, Donny, Shelby)

> ## What did we do ##
> Phone app:
    * get() returning 404 - Blocker
> Database:
    * finished writing methods for User DAO
> Servlet:
    * made shell for roster service
> Web:
    * successful integration with DB (getUsers)

> ## What are we going to do ##
    * Have integration ready for meeting with sponsor (next Monday)
> Phone app:
    * android app makes a post() request to login service, display response
    * eclipse doesn't run on Zak's computer - blocker
    * make git repository? (optional)
> Database:
    * event DAO methods
    * roster table
> Servlet:
    * make query to fill roster service response - blocker (database not ready)
    * create list of available services
> Web:
    * adjust look and feel - blocker (local copy doesn't run js, so will make many commits)
    * make template html/css
    * display services as they are made - blocker (availability)

---


# Sponsor Meet 2 #

> ## 2/11 ##

> Attendees: Danon (sponsor), Matt, Shelby, Zak, Donny

> ## What did we do ##
    * Overview of integration status
    * Created service list page

> ## What are we going to do ##
    * Use phone to trigger website output
      * blocker: getting coordinated, communication
    * Progress toward the goal in scope. Don't think too much ahead.
    * Create tests
    * Test integration early - Let's meet on Friday (2/13)
    * Be ready to meet with Danon next week
    * Phone: create apk?
    * Use fiddler for testing? (http://www.telerik.com/fiddler)
    * Real time updating: Ajax makes a long request to check if something has changed on the servlet

---


# Scrum Meet 3 #

> ## 2/13 ##

> Attendees: All (Matt, Tan, Zhe, Donny, Shelby)

> ## What did we do ##
    * Website:
      * Get all users for an event done and deployed to the server
      * Create sequence diagram (https://www.lucidchart.com/invitations/accept/ea408368-6c21-4e0e-a314-97ae4b20341d)
    * App: blocker - beacon (running test program)
    * Servlet:
      * DAOs for beacon entry
      * blocker - DAOs not implemented yet
    * DB:
      * Finished event DAO
      * blocker - time

> ## What are we going to do ##
    * Finish integration: App submits ticket (set ticket with service, get 200 response, show welcome message), handled by server/DB  (provide ticket service and access service), displayed by website (use access service)

  * Website:
    * Change website to display users for event 1 AND 2 (url here: http://54.200.138.139:8080/BeaconServlet/RosterTest.html)
    * Help out: Email availability to tan and matt to meet before Wednesday
  * App:
    * Be able to set ticket
    * Implement Matt's code
    * Try to get beacon to work
  * Servlet:
    * Call & test DAOs, put into service
  * DB:
    * Roster DAO
    * Help calling DAO from services


---



# Sponsor Meet 3 #

> ## 2/18 ##

> Attendees: Danon (Sponsor), Matt, Donny, Zhe, Shelby

> ## What did we do ##
    * Demo system

> ## What are we going to do ##
    * Think about
      * Error handling/testing (make msg for deny only, for now)
      * App handles service response (200? etc)
      * Stress testing (10 people, for now)
    * Polling, NOT manual refresh
    * Testing ideas:
      * Have one phone only for event 1 and another for only event 2
      * Try to enter venue for event starting at wrong time -> Deny
      * Record & Tracks denials
      * Don't worry about adding new people in roster for now, just change color


---


# Scrum Meet 4 #

> ## 2/27 ##

> Attendees: Matt, Shelby, Zak, Donny, Tan (All)

> ## What did we do ##
    * Trying to schedule meeting with Nicole
    * App (beacon)
      * Trying to get the beacon to work with the app (make activity appear when phone enter/leave the beacon range)
    * Servlet
      * Asana changes
      * Servlet logs scan requests in DB - for testing purposes (removes double scans)
      * Events 1, 2, 3, 4 url requests work now (Website tests using 1)
    * Website
      * Designed a possible new mockup for the website
    * DB
      * Installed MySQL Workbench
      * Updated table names

> ## What are we going to do ##
    * App (beacon)
      * Statically connect the app to the blue beacon on start (assume only 1 beacon for now)
      * Send 1 request when the app connects to the beacon
      * Blocker: very little information about how to made estimote code work
    * App (Other)
      * Work on sql database to log calls to the service
      * Incorporate beacon result into app
      * Update info presented in toast into a more prominent notification on the screen?
    * Servlet
      * Report to app reason for failed scans
    * Website
      * Implement polling
    * DB
      * Handle case where user tries to enter event happening at the wrong time, i.e. event not happening at the time of scan ( => deny)


---


# Sponsor Meet 4 #

> ## 3/2 ##

> Attendees: Danon (Sponsor), Daniel, Rich, Matt, Donny, Tan, Zhe, Shelby (All)

> ## What did we do ##
    * App
      * More beacon functionality (range sensor)
      * Don't need to manually select the beacon
    * Servlet
      * added scan log
    * DB
      * Extra tables for scan log
    * Website
      * Polling with servlet

> ## What are we going to do ##
    * App
      * How fast does the phone recognize the beacon (test latency)
      * More testing
      * Always listen for beacons
      * Integrate the two apps?
      * **Create APK**
        * Blocker: confusion about export to apk
        * Doesn't need to be signed
      * Don't connect to beacons. Instead, the trigger is seeing the beacon list
    * Servlet
      * **Servlet deals with beacons.** Servlet has a list of beacons it recognizes. Phone sends list of recognized beacons to Servlet and it chooses which one to use.
    * DB
      * Logging more errors
    * Website
      * Auto refresh (timed or event)
      * Add reason for denial to JSON obj?
      * **UI issues**
        * Old entry events fade out after time amt
        * show % entered
    * Multiple Deny Reasons:
      * Ticket scanned?
      * Event time passed? (can do start/end or assume start begins after end of last event)
      * Already entered?
    * Error messages:
      * Store messages as files, have processes analyze those files (DB not bloated)
      * Also store contents of the request message?
      * Record all data that would make it easy to debug
      * app-beacon latency record
    * Change displayed data:
      * Event name, fName, lName, time of event, didAttend?, error
    * To finish this scope:
      * Recognize beacon? Automatically allowed into event
    * scope ^: 2 beacons, enter/exit (Not yet)
      * Assign beacons to events (UI through website)

---


# Scrum Meet 5 #

> ## 3/3 ##

> Attendees: Matt, Shelby, Tan, Zak, Donny, Nicole (TA)

> ## What did we do ##
    * App (non-beacon)
      * DB works, keeps track of certain metrics (redundancy for login svc, offline mode)
      * UI
    * App (beacon)
      * Autoconnect with beacon
    * Website
      * Successful polling
    * DB
      * Event time DAO
    * Servlet
      * Late tickets
      * Logging requests/responses

> ## What are we going to do ##
    * App (non-beacon)
      * Put two apps together?
      * apk?
        * Blocker: how to make an apk
      * add to DB: log of events the user is valid for
      * Use android phone for testing
    * App (beacon)
      * apk
        * Blocker: Integrating both apps
    * DB
      * Event logging
        * Blocker: need to work with matt
    * Website
      * Lots of UI
        * Blocker: using servlet and jsp
    * Servlet
      * Experiment with beacons to help beacon side
      * Tan's DAOs to detect multiple start/end for each event
> ## Code Review/Addressing Each Other's Blockers ##
    * Website:
      * Fixed local connection to DB in order to execute .js
      * jsp: send objects to my servlet instead of just text
      * set initial webpage with doGet
      * edit webpage with doPost


---


# Scrum Meet 6 #

> ## 3/16 ##

> Attendees: Matt, Shelby, Tan, Zak, Donny (all)

> ## What did we do ##
    * App (non-beacon)
      * 
    * App (beacon)
      * 
    * Website
      * updated UI
      * UI shows live stream
    * DB
      * CRUD
      * method to compare scan times
      * **fixed scan authentication**
    * Servlet
      * Refactor CRUD operations, roster entries
      * Stubs
      * QR code generator

> ## What are we going to do ##
    * App (non-beacon)
      * blocker: can't test beacon, no android phone (need v4.3)
      * work on phone UI
    * App (beacon)
      * 
    * Website
      * polish UI
      * popout window for polling settings
      * blocker: decide an indicator for success/fail of each scan
      * make page for matching events with beacons
      * make a template html/css
    * DB
      * create stubs for event times
      * help incorporate events with beacons
      * blocker: how to make a stub
    * Servlet
      * Implement connect beacons with events