


$(document).ready(function() {
	//carregaProdutoByLinha(4);
	console.log('load no index');

	var qtde = pegaQtdeCookie()
	$("#somacarrinho").html(qtde);
});

function addCarrinho(produtoid_click) {

	console.log("prod-id: " + produtoid_click);

	var novocarrinho = atualizaCarrinhoCookie(produtoid_click);

	setCookie("gift4us-cart",novocarrinho,30);

	qtde = pegaQtdeCookie();

	$("#somacarrinho").html(qtde);

}


function atualizaCarrinhoCookie(produtoid) {
	var carrinhoRetorno = "";
	var cretorno = checkCookie("gift4us-cart");

	console.log("carrinho atual:" + cretorno);

	var produtojaexistia = false;
	var qtde = parseInt(0);
	if (cretorno != '') {
		console.log("cretorno nao é vazio ");

		const myArray = cretorno.split("-");

		if (myArray.length > 0) {
			for (let i = 0; i < myArray.length; i = i + 1) {
				console.log(i + 'º [for]', myArray[i]);
				const myArrayProd = myArray[i].split(":");
				if (myArrayProd[0] == produtoid) {
					console.log('[qtde do produto]', myArrayProd[1]);
					qtde = myArrayProd[1];
					qtde = parseInt(qtde) + parseInt(1);
					carrinhoRetorno += produtoid + ":" + qtde + "-";
					produtojaexistia = true;
				}
				else {
					if (myArrayProd[0] != 30) {
						carrinhoRetorno += myArray[i] + "-";
					}
				}
			}
			
			if(!produtojaexistia){
				carrinhoRetorno += produtoid + ":" + 1 + "-";
			}
		}

	}
	else {
		console.log('primeiro item do carrinho');
		carrinhoRetorno = produtoid + ":" + 1 + "-";
	}

	

	console.log("carrinho retorno:" + carrinhoRetorno);
	carrinhoRetorno = carrinhoRetorno.substring(0, carrinhoRetorno.length-1)
	console.log("carrinho retorno substr:" + carrinhoRetorno);	

	return carrinhoRetorno;
}


function pegaQtdeCookie() {

	var cretorno = checkCookie("gift4us-cart");
	var qtde = parseInt(0);
	if (cretorno != '') {
		const myArray = cretorno.split("-");
		if (myArray.length > 0) {
			for (let i = 0; i < myArray.length; i = i + 1) {
				console.log(i + 'º for', myArray[i]);
				const myArrayQtde = myArray[i].split(":");
				console.log('[for 2]', myArrayQtde[i]);
				if (myArrayQtde[i] != null) {
					qtde = parseInt(qtde) + parseInt(myArrayQtde[1]);
					console.log('qtde:', qtde);
				}
			}
		}
	}
	else {
		console.log("cookie vazio!");
	}

	return qtde;
}

/*
function addCarrinho(produtoid) {
	console.log('chamou o produto id:' + produtoid);
	
	$.ajax({
		url : urlPadrao + 'site/service/addprodutonocarrinho/' + produtoid,
		type : 'get',
		async : true,
		success : function(retorno) {
			
			console.log("qtde carrinho:" + retorno.data);
			
			$("#somacarrinho").html(retorno.data);
			
			
		}
	});
};
*/


function carregaProdutoByLinha(linhaid) {
	console.log('chamou a linha id:' + linhaid);

	$.ajax({
		url: urlPadrao + 'site/service/produtobylinha/4',
		type: 'get',
		async: true,
		success: function(retorno) {
			retorno.data.forEach(function(item, index) {


				console.log("item:" + item.titulo);

			});

		}
	});
};



function setCookie(cname, cvalue, exdays) {

	console.log("nome: " + cname + " valor: " + cvalue + " expira: " + exdays);

	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toGMTString();

	//console.log("antes de criar;");

	document.cookie = cname + "=" + cvalue + ";" + expires + "; SameSite=None; Secure;";

}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') c = c.substring(1);
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}

function checkCookie(cname) {
	var cvalue = getCookie(cname);

	if (cvalue != "") {
		//console.log("existe valor:" + cvalue);
	}

	//console.log("check cookie :" + cvalue);

	return cvalue;

}

