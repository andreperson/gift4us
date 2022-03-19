


$(document).ready(function() {
	//carregaProdutoByLinha(4);
	console.log('load no index');
	addCarrinho(0);
});



function addCarrinho(produtoid) {
	console.log('chamou o produto id:' + produtoid);
	
	$.ajax({
		url : urlPadrao + 'site/service/addprodutonocarrinho/' + produtoid,
		type : 'get',
		async : true,
		success : function(retorno) {
			
			console.log("qtde carrinho:" + retorno.data);
			
			$("#somacarrinho").html(retorno.data).fadeIn(1000);
			
			
		}
	});
};



function carregaProdutoByLinha(linhaid) {
	console.log('chamou a linha id:' + linhaid);
	
	$.ajax({
		url : urlPadrao + 'site/service/produtobylinha/4',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				
				
				console.log("item:" + item.titulo);
				
			});
			
		}
	});
};

