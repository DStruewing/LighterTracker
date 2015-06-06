<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />

        <link href="css/index.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/slidetoggle.js"></script>  

    </head>

    <body>

        <jsp:include page="global/pre-body.jsp?logonPage=yes" />

        <script>
			document.getElementById("home").className = "tab selected";
        </script>

        <div class="content">

            <h1>How It Works</h1>

            <div>
                <p>Have you ever wondered where your lighter came from, 
                    or where it will go when you lose it? Lighter Tracker 
                    is designed to shed light on the question of lighter 
                    movement. 
                <p>Contribute to the project by using this website to record 
                    the location and condition of any lighters with a Lighter 
                    Tracker ID number. The more people who track their lighters, 
                    the more we'll learn about their mysterious and transient 
                    lives.</p>
            </div>

            <h2>Get Started</h2>

            <div id="startForm">
                <p id="startFormMsg">To get started, enter your lighter's tracking code below:</p>
                <p id="lighterCodeMsg" style="color:red; display:none;">That lighter doesn't exist. Please try again.</p>
                <form name="startForm" class="bigForm" action="javascript:submitLighterCode()" method="get">
                    <input autofocus type="text" name="lighterCode"><br>
                    <input type="submit" value="GO">
                </form>
            </div>

        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

        <script type="text/javascript"
                src="https://maps.googleapis.com/maps/api/js?sensor=false">
        </script>
        <script type="text/javascript"
				src="js/index.js">
		</script>
    </body>

</html>
