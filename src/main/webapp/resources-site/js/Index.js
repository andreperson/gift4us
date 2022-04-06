var cookiename = "gift4us-cart";

$(document).ready(function() {
	console.log('load index');
	var biscoito = getCookie(cookiename);
	console.log("cookie: " + biscoito);
	var qtde = 0;

	checkCookie(cookiename);

	if (biscoito != null) {
		qtde = pegaQtde();
	}

	$("#somacarrinho").html(qtde);
});


function add(produtoid) {
	console.log('entrou ADD prodid: ' + produtoid);
	var biscoito = getCookie(cookiename);
	console.log("cookie: " + biscoito);
	novocarrinho = atualizaCarrinhoCookie(biscoito, produtoid, 1, "add");
	console.log("novo cart " + novocarrinho);
	setCookie(cookiename, novocarrinho);
	var qtde = pegaQtde();
	$("#somacarrinho").html(qtde);
}




function upd(produtoid) {
	console.log('entrou UPD');
	var biscoito = getCookie(cookiename);
	qtde_no_carrinho = $("#quantity_" + produtoid).val();
	console.log("qtde no carrinho: " + qtde_no_carrinho);
	console.log("prod-id: " + produtoid);
	var novocarrinho = atualizaCarrinhoCookie(biscoito, produtoid, qtde_no_carrinho, "upd");
	setCookie(cookiename, novocarrinho);
	var qtde = pegaQtde();
	$("#somacarrinho").html(qtde);
}



function del(produtoid) {
	console.log('entrou DEL prdid:' + produtoid);
	var biscoito = getCookie(cookiename);
	var novocarrinho = atualizaCarrinhoCookie(biscoito, produtoid, 0, "del");
	setCookie(cookiename, novocarrinho);
	qtde = pegaQtde();
	$("#somacarrinho").html(qtde);

	biscoito = checkCookie(cookiename);
	if (biscoito == "") {
		console.log("cookie vazio");
	}
	else {
		console.log("cookie não esta vazio");
	}

	location.reload();
}


function atualizaCarrinhoCookie(biscoito, produtoid, qtdeUser, tipo) {
	console.log('entrou Atualiza Carrinho: ' + biscoito + " " + tipo);
	var carrinhoRetorno = "";
	var produtojaexistia = false;
	var qtde = parseInt(0);
	if (biscoito != null) {
		if (biscoito != "") {
			const myArray = biscoito.split("-");


			console.log("biscoito: " + biscoito);

			console.log("array: " + myArray);

			if (myArray.length > 0) {
				for (let i = 0; i < myArray.length; i = i + 1) {
					console.log(i + 'º item ', myArray[i]);
					const myArrayProd = myArray[i].split(":");

					console.log("myArrayProd:" + myArrayProd);


					if (myArrayProd[0] == produtoid) {
						qtde = parseInt(myArrayProd[1]);
						if (tipo == "add") {
							console.log("entrou no add prodid: " + produtoid + " qtde do carrinho: " + qtde + " qtde p/ add: " + qtdeUser);
							qtde = parseInt(qtde) + parseInt(qtdeUser);
							carrinhoRetorno += produtoid + ":" + qtde + "-";
						}
						else if (tipo == "upd") {
							console.log("entrou no upd - prodid: " + produtoid + " qtde do carrinho: " + qtde + " qtde p/ add: " + qtdeUser);
							qtde = parseInt(qtdeUser);
							carrinhoRetorno += produtoid + ":" + qtde + "-";
						}
						else if (tipo == "del") {
							//nao monta o carrinho com o produto escolhido ele vai sumir 	
						}

						console.log("nova quantidade: " + qtde);
						produtojaexistia = true;
					}
					else {
						carrinhoRetorno += myArray[i] + "-";
					}
				}

				if (!produtojaexistia) {
					carrinhoRetorno += produtoid + ":" + 1 + "-";
				}
			}
		}
		else {
			console.log('primeiro item do carrinho');
			carrinhoRetorno = produtoid + ":" + 1 + "-";
		}

	}
	else {
		console.log('primeiro item do carrinho');
		carrinhoRetorno = produtoid + ":" + 1 + "-";
	}

	console.log("carrinho retorno:" + carrinhoRetorno);
	carrinhoRetorno = carrinhoRetorno.substring(0, carrinhoRetorno.length - 1)
	console.log("carrinho retorno substr:" + carrinhoRetorno);

	return carrinhoRetorno;
}


function pegaQtde() {
	var biscoito = getCookie(cookiename);
	console.log('entrou Pega Qtde');
	var qtde = parseInt(0);
	if (biscoito != '') {
		const myArray = biscoito.split("-");
		console.log('array length: ' + myArray.length);
		if (myArray.length > 0) {
			for (let i = 0; i < myArray.length; i = i + 1) {
				const myArrayQtde = myArray[i].split(":");
				console.log('i: ' + i + ' array quantidade: ' + myArrayQtde + ' length: ' + myArrayQtde.length);
				if (myArrayQtde != null) {
					qtde = parseInt(qtde) + parseInt(myArrayQtde[1]);
					console.log('qtde somada: ', qtde);
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


function getCookie(cname) {
	return Cookies.get(cname);
}

function checkCookie(cname) {
	var cvalue = getCookie(cname);
	if (cvalue != "") {
		console.log("existe valor:" + cvalue);
	}
	else {
		eraseCookie(cookiename);
	}


	return cvalue;
}

function setCookie(cname, cvalue) {

	// Cria uma nova data no futuro 01/01/2020
	var data = new Date(2020, 0, 01);
	// Converte a data para GMT
	// Wed, 01 Jan 2020 02:00:00 GMT
	data = data.toGMTString();
	// Cria o cookie

	console.log("Entrou Set: " + cname + " " + cvalue);

	document.cookie = cname + "=" + cvalue + "; " + data + '; path=/' + "; SameSite=None; Secure;";
}

function eraseCookie(cname) {

	console.log("Entrou ERASE: " + cname);

	document.cookie = cname + '=; Max-Age=-99999999;';
}