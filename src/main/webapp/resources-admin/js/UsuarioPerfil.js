$(document).ready(function() {

	console.log('load perfil do usuário');

});


function confirmaSenha() {

	console.log('confirma senha!');
	var senha = $("#senha").val();

	var confirmarsenha = $("#confirmarsenha").val();

	console.log(senha + " " + confirmarsenha);

	var msg = "";
	$("#msgTopo").hide();
	$("#msgTexto").html(msg)

	if (!validaSenha(senha, confirmarsenha)) {
		$("#senha").focus();
	}

}


function validaSenha(senha, confirmarsenha) {
	console.log('valida senha!');
	var validou = true;


	validou = validouDiferencas(senha, confirmarsenha)

	if (!validou) {
		console.log("As senhas são diferentes!");
		msg = "Os campos 'Senha' e 'Confirmar Senha' não podem ser diferentes!"
		$("#msgTopo").show();
		$("#msgTexto").html(msg)
	}

	if (validou) {
		validou = verificaForcaSenha(senha)
	}

	return validou;

}

function validouDiferencas(senha, confirmarsenha) {
	var validou = true;
	if (senha != "" && confirmarsenha != "") {
		if (senha != confirmarsenha) {
			validou = false;
		}
	}

	return validou;
}


function verificaForcaSenha(senha) {
	var validou = true;
	var numeros = /([0-9])/;
	var chEspeciais = /([~,!,@,#,$,%,^,&,*,-,_,+,=,?,>,<])/;

	if (senha.length < 6) {
		validou = false;
		msg = "Por gentileza, utilize o mínimo de 6 caracteres!";
		$("#msgTopo").show();
		$("#msgTexto").html(msg);
		}
	else if(!senha.match(numeros)){
		validou = false;
		msg = "Por gentileza, utilize ao menos 1 número!";
		$("#msgTopo").show();
		$("#msgTexto").html(msg);
		
	} 
	else if(!senha.match(chEspeciais)){
		validou = false;
		msg = "Por gentileza, utilize ao menos 1 caracter especial!";
		$("#msgTopo").show();
		$("#msgTexto").html(msg);
	}

	return validou;
}

