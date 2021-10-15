$(document).ready(function() {

	console.log('load perfil do usuário');

});


function confirmaSenha(){
	
	
	var senha = $("#senha").val();
	
	var confirmarsenha = $("#confirmarsenha").val();
	
	console.log("senha: " + senha + " conf senha: " + confirmarsenha);
	
	var msg="";
	$("#msgTopo").hide();
	$("#msgTexto").html(msg)
	
	
	if (senha != "" && confirmarsenha != ""){
		if (senha != confirmarsenha){
			console.log("As senhas são diferentes!");
			msg = "Os campos 'Senha' e 'Confirmar Senha' não podem ser diferentes!"
			$("#msgTopo").show();
			$("#msgTexto").html(msg)
		}
		
		
		if (senha == "123456"){
			console.log("Por gentileza!");
				msg = "Por gentileza, use uma senha diferente!"
			$("#msgTopo").show();
			$("#msgTexto").html(msg)
		}
	}
	
}