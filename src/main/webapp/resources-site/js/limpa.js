var cookiename = "gift4us-cart";

$(document).ready(function() {
	console.log('load limpa');
	eraseCookie(cookiename);

});




function eraseCookie(cname) {

	console.log("Entrou ERASE: " + cname);

	document.cookie = cname + '=; Max-Age=-99999999;';
	
	//document.cookie ='nome=; expires=' + data + '; path=/';
	
}