<%-- 
    Document   : index
    Created on : Apr 14, 2015, 8:56:27 PM
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' href='css/layout.css' />
        <link rel='stylesheet' type='text/css' href='css/style.css' />
		<link rel='stylesheet' type='text/css' href='css/slides.css' />
        <title>Lighter Tracker</title>
    </head>
    <body>

        <div class='background-page'>

            <div class='content-container'>

				<div class="slides">

					<div id='progressSlide'></div>

					<div id='slide1'>
						<header id='title1'>
							<span class='text'>Enter your lighter's five digit tracking code in the box below.</span>
						</header>
						<aside id='errorMsg1'>
							<span class='text'>That lighter doesn't exist. Try again.</span>
						</aside>
						<form action="javascript:next()">
							<input id='lighterCodeInput' type='text' maxlength='6' autofocus />
							<div class='next'>
								<input type="button" onclick="next()" value="Next"/>
							</div>
							<input type="submit" style="display: none" />
						</form>
					</div>

					<div id='slide2'>
						<header id='title2'>
							<span class='text'>Please log in to continue.</span>
						</header>
						<aside id='errorMsg2'>
							<span class='text'>Incorrect email or password. Try again.</span>
						</aside>
						<form action="javascript:next()">
							<input id='emailInput' type='text' placeholder="email" />
							<input id='passwordInput' type='password' placeholder="password" />
							<div class='next'>
								<input type="button" onclick="next()" value="Next"/>
							</div>
							<input type="submit" style="display: none" />
						</form>
					</div>

					<div id='slide3'>
						<header id='title3'>
							<span class='text'></span>
						</header>
						<form action="javascript:next()">
							<div class='next'>
								<input type="button" onclick="next()" value="Next"/>
							</div>
						</form>
						<div id='map-canvas'></div>
					</div>

					<div id='resultSlide'>
						<div id="resultText"></div>
						<div id="successImage"></div>
						<div id="failureImage"></div>
						<div id='map-canvas'></div>
					</div>

				</div>

            </div>

        </div>

        <div class='foreground-page'>

            <div class='scroll-container'>

                <div class='header-container' style="text-align: center; color: lightgray">
					Header and login link will go here.
				</div>

                <div class='content-container' style="text-align: center; color: lightgray">
					Table containing all lighters in the system will go here.
				</div>

            </div>

        </div>	

		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <script type='text/javascript' src="js/index.js"></script>

    </body>
</html>
