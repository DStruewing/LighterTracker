<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <jsp:include page="global/head.jsp" />
        
        <link href="css/labs.css" rel="stylesheet" type="text/css">
        
    </head>
    
    <body>
        
        <jsp:include page="global/pre-body.jsp" />
        
        <script>
            document.getElementById("labs").className = "tab selected";
        </script> 
        
                <div class="content">
                    <h2>Lab 1 Project Proposal</h2>
                    <p><a href="images/StruewingProposal.pdf">Project Proposal</a></p>
                    <h2>Lab 2 Data Model</h2>
                    <p><a href="images/DataModel.png">Data Model</a></p>
                    <h2>Lab 3 Home Page (HTML/CSS)</h2>
                    <p>Designed my home page and created my default CSS stylesheet.</p>
                    <h2>Lab 4 Forms, Javascript, Cookies</h2>
                    <p>Created a contact form, multiple styling options, and 
                        paragraphs that show/hide on my home page. <br>
                        Fixed errors from initial submission.</p>
                    <h2>Lab 5 Display Data</h2>
                    <p>Displaying data from database on Users, Lighters, and Records pages.</p>
                    <h2>Lab 6 Search</h2>
                    <p>Search page is done. Users can now search for tracking records using various parameters.
                        <br>Also added a "Reset" button that clears the search criteria and shows all records.
                        <br>If a search has no results, the user is shown a message rather than an empty table.</p>
                    <h2>Lab 7 Insert</h2>
                    <p>Added pages for inserting users and lighters.
                        <br>Completed extra credit pick list for user role.
                    </p>
                    <h2>Lab 8 Login</h2>
                    <p>Completed implementation of login functionality.</p>
                    <h2>Lab 9 Update</h2>
                    <p>Used ajax  to implement update functionality on <a href="users.jsp">users</a> and <a href="other.jsp">lighters</a> pages.</p>
                    <h2>Lab 10 Delete</h2>
                    <p>Added delete functionality to <a href="users.jsp">users</a>, <a href="other.jsp">lighters</a>, and <a href="assoc.jsp">records</a> pages.</p>
					<h2>Insert Assoc Challenge</h2>
					<p>
						The challenge that I completed for my final project submission is the insert associative challenge.
						The user begins the process of inserting a tracking record by expanding the "Get Started"
						section on the index page and entering a tracking code. I added a tracking code field 
						to my lighter table in the database because it wasn't part of my original data model.
						In a real use case, the tracking code would be printed on the lighter along with the website URL.
					</p>
					<p>
						When the user clicks Next, the page first checks to make sure that she is logged in. If not, an alert is 
						shown. Then, it verifies the tracking code, gets the lighterId webUserId and date, and attempts to get 
						the user's location using geolocation. After all of this work is done, it redirects the user to the 
						insertRecord page, passing in the values that it has already determined (webUserId, lighterId, date, latitude,
						and longitude).
					</p>
					<p>
						On the insertRecord page, the user is shown a map and a text box. The map allows the user to adjust the 
						location, which is especially necessary if her browser didn't support geolocation on the previous page. 
						The text box is where the user enters the lighter's condition. At the bottom of the page is a submit button,
						which posts back to the page passing in all of the tracking record values. At this point, the page calls 
						the insertRecord() function in RecordMods. After the insert is complete, the user is redirected to the 
						records page and shown a confirmation message.
					</p>
					
					<h2><a href="/SP15_2308_tud04734/release/index.jsp">Release Site</a></h2>
					<p>
						After completing the challenge, I turned my attention to working on a completely new version of my site 
						that I'm calling the <a href="/SP15_2308_tud04734/release/index.jsp">release site</a>. This is how I would want the site to 
						work if I actually released it to the public. Most of the functionality that I've implemented 
						for the labs would not exist in the release site.
					</p>
					<p>
						My plan for the release site consists of two main pages, the index page and the lighter detail page. The index page 
						contains a form for inserting a new tracking record, as well as a table showing all of the lighters in 
						the system. When the user clicks on a lighter in the table, she is brought to the lighter detail page where 
						all of the tracking records for that lighter are shown, as well as a map showing its path. From the user's perspective, 
						the release version is significantly simpler than this version.
					</p>
					<p>
						All of the jsp, css, and javascript files for the release site are in the "release" folder, and 
						the java classes are in the Release package. There is a lot of redundancy 
						between the code in the original classes and the release classes, but I wanted to rewrite all of the 
						classes so that the release site could exist independently and with no unused code.
					</p>
					<p>
						I'm not nearly finished implementing the release site, but I've begun working on the index page. 
						I've completed the insert record form, which is the main focus of the index page. It relies heavily on javascript 
						and ajax to collect and verify input without any redirects or postbacks. However, I haven't yet implemented 
						the rest of the index page or the lighter detail page. The look and feel are also tentative. Perhaps I will finish 
						work on the release site after the semester is over. In any case, I though I'd include it in my final project submission 
						so that you can see what I'm working on.
					</p>
					<p>
						Thanks for a great semester! This class was extremely interesting, fun, and useful. My only regret is not coming to 
						lecture more often (and missing those few lab deadlines). Have a great summer!
					</p>
                    
                </div> <!-- content -->
                
        <jsp:include page="global/post-body.jsp" />
        
    </body>
    
</html>
