function changeStyle(choice)  {
    if (choice.value === "clear") {
        document.getElementById('theme').href = "css/stylesheet-default.css";
        eraseCookie("theme");
    } else {
        document.getElementById('theme').href = choice.value;
        createCookie("theme", choice.value);
    }
}

function createCookie(name,value) {
    document.cookie = name+"="+value+"; path=/";
}

function eraseCookie(name) {
    createCookie(name,"");
}

function readCookieSetStyle() { 
        var value = readCookie('theme');
        if(value !== "" && value !== null) {
            document.getElementById('theme').href = value;
        } else {
            document.getElementById('theme').href = "css/stylesheet-default.css";
        }
    
    $(document).ready( function() {
        $("#selectTheme option").each(function() {
            var $this = $(this);
            if($this.val() === value) {
                $this.prop('selected', true);
            }
        });
    });
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)===' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) === 0) {
            return c.substring(nameEQ.length,c.length);
        }
    }
    return null;
}