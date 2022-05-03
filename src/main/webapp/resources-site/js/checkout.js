
$(document).ready(function() {
	console.log('load checkout');

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