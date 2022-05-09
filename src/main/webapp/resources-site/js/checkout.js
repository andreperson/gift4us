
$(document).ready(function() {
	console.log('load checkout');
	verificaLimparCarrinho();
});



$("form").on("submit", function(event) {
	var validado =true;
	

	// remove as mensagens de erro
	$("#msgnome").html('');
	$("#msgemail").html('');

	// verificar se os campos foram preenchidos
	var strnome = $("#nome").val();
	var stremail = $("#email").val();

	if (!strnome || strnome.length < 5) {
		$("#msgnome").html("Por gentileza preencha o nome corretamente!");
		validado=false;
	}

	const substring = "@";

	console.log("stremail:" + stremail); // true

	if (strnome.length < 5) {
		if (!stremail.includes(substring)){
			$("#msgemail").html("Por gentileza preencha o email corretamente!");
			validado=false;
		} 		
	}
	if(!validado){
		event.preventDefault();
	}

});


function verificaLimparCarrinho(){

	var podelimpar = $("#podelimpar").val();

	console.log("podelimpar:" + podelimpar);
	if (podelimpar == 'ok'){
	

		var cname = "gift4us-cart";
		
		console.log("Entrou ERASE: " + cname);
	
		//document.cookie = cname + '=; Max-Age=-99999999;';
		
		//document.cookie = nome=''; expires='' + data + ''; path='';
		
		
		document.cookie = cname+'="";-1; path=/';
		
		//$.cookie(cname, null);
			
		
	}


function deleteCookie(name) {
    document.cookie = name+'="";-1; path=/';
}

var login = document.getElementById("loginlink");
login.onclick = function() {
  deleteCookie("name");
};


}



function getCookie(cname) {
	return Cookies.get(cname);
}
