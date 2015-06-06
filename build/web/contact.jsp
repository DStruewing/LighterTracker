<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <jsp:include page="global/head.jsp" />
        
        <link href="css/contact.css" rel="stylesheet" type="text/css" />
        
    </head>
    
    <body>
        
        <jsp:include page="global/pre-body.jsp" />
        
        <script>
            document.getElementById("contact").className = "tab selected";
        </script> 
        
                <div class="content">    
    
                    <p>Use the form below to contact us with any questions, 
                        comments, or concerns about this project.</p>
                    
                    <form name="contactForm" method="post" 
                          action="http://www.temple.edu/cgi-bin/mail?tud04734@temple.edu">
                        
                        <table cellspacing="10">
                            <tr>
                                <td>First Name</td>
                                <td><input type="text" name="firstName"/></td>
                            </tr>
                            <tr>
                                <td>Last Name</td>
                                <td><input type="text" name="lastName"/></td>
                            </tr>
                            <tr>
                                <td>Email Address</td>
                                <td><input type="text" name="emailAddress"/></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <label for="radio-comment">
                                        <input type="radio" name="reason" value="Comment" id="radio-comment"/>
                                        Comment
                                    </label>
                                    <br>
                                    <label for="radio-complaint">
                                        <input type="radio" name="reason" value="Complaint" id="radio-complaint"/>
                                        Complaint
                                    </label>
                                    <br>
                                    <label for="radio-other">
                                        <input type="radio" name="reason" value="Other" id="radio-other"/>
                                        Other
                                    </label>
                                    <br>
                                </td>
                            </tr>
                            <tr>
                                <td>State</td>
                                <td>
                                    <select name="state">
                                        <option value="AL">Alabama</option>
                                        <option value="AK">Alaska</option>
                                        <option value="AZ">Arizona</option>
                                        <option value="AR">Arkansas</option>
                                        <option value="CA">California</option>
                                        <option value="CO">Colorado</option>
                                        <option value="CT">Connecticut</option>
                                        <option value="DE">Delaware</option>
                                        <option value="DC">District Of Columbia</option>
                                        <option value="FL">Florida</option>
                                        <option value="GA">Georgia</option>
                                        <option value="HI">Hawaii</option>
                                        <option value="ID">Idaho</option>
                                        <option value="IL">Illinois</option>
                                        <option value="IN">Indiana</option>
                                        <option value="IA">Iowa</option>
                                        <option value="KS">Kansas</option>
                                        <option value="KY">Kentucky</option>
                                        <option value="LA">Louisiana</option>
                                        <option value="ME">Maine</option>
                                        <option value="MD">Maryland</option>
                                        <option value="MA">Massachusetts</option>
                                        <option value="MI">Michigan</option>
                                        <option value="MN">Minnesota</option>
                                        <option value="MS">Mississippi</option>
                                        <option value="MO">Missouri</option>
                                        <option value="MT">Montana</option>
                                        <option value="NE">Nebraska</option>
                                        <option value="NV">Nevada</option>
                                        <option value="NH">New Hampshire</option>
                                        <option value="NJ">New Jersey</option>
                                        <option value="NM">New Mexico</option>
                                        <option value="NY">New York</option>
                                        <option value="NC">North Carolina</option>
                                        <option value="ND">North Dakota</option>
                                        <option value="OH">Ohio</option>
                                        <option value="OK">Oklahoma</option>
                                        <option value="OR">Oregon</option>
                                        <option value="PA">Pennsylvania</option>
                                        <option value="RI">Rhode Island</option>
                                        <option value="SC">South Carolina</option>
                                        <option value="SD">South Dakota</option>
                                        <option value="TN">Tennessee</option>
                                        <option value="TX">Texas</option>
                                        <option value="UT">Utah</option>
                                        <option value="VT">Vermont</option>
                                        <option value="VA">Virginia</option>
                                        <option value="WA">Washington</option>
                                        <option value="WV">West Virginia</option>
                                        <option value="WI">Wisconsin</option>
                                        <option value="WY">Wyoming</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Message</td>
                                <td><textarea name="message" cols="60" rows="7"></textarea></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <label for="checkbox-stickers">
                                        <input type="checkbox" name="stickers" id="checkbox-stickers"/>
                                        Please send me Lighter Tracker stickers!
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="submit" value="Send"</td>
                            </tr>
                        </table>
                    </form>

                </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />
        
    </body>
    
</html>
