
//verifica se deve motrar a imagem, conforme ultima escolha
$(document).ready(function () {
    var cookatual = getCookie("cooktopo");
    var myCook = "little";
    var little = "skin-blue sidebar-mini sidebar-collapse";
    var big = "skin-blue sidebar-mini";

    if (cookatual) {
        if (cookatual == "big") {
            $("#showtopo").attr("class", big)
        }
        else {
            $("#showtopo").attr("class", little);
        }
    }

    setCookie("cooktopo", cookatual, 15)

    
});


//$("#alterashowtopo").click(function () {
//    var myClass = $("#showtopo").attr("class");
//    var myCook = "";
//    if (myClass == "skin-blue sidebar-mini") {
//        myCook = "little";
//    }
//    else {
//        myCook = "big";
//    }

//    setCookie("cooktopo", myCook, 15)
//});



function setCookie(cookie_name, cookie_value, expire_in_days) {
    var cookie_expire = "";

    if (expire_in_days != null) {
        var expire = new Date();
        expire.setTime(expire.getTime() + 1000 * 60 * 60 * 24 * parseInt(expire_in_days));
        cookie_expire = "; expires=" + expire.toGMTString();
    }

    document.cookie = escape(cookie_name) + "=" + escape(cookie_value) + cookie_expire;
}

function getCookie(cookie_name) {
    if (!document.cookie.match(eval("/" + escape(cookie_name) + "=/"))) {
        return false;
    }

    return unescape(document.cookie.replace(eval("/^.*?" + escape(cookie_name) + "=([^\\s;]*).*$/"), "$1"));
}



// Acessando
function LeCookie() {
    cookie = getCookie("cookimg");

    //if (!cookie)
    //    alert("Cookie não gerado");
    //else
    //    alert("O valor do cookie gravado é: " + cookie);
}

