


$(document).ready(function() {
	//carregaProdutoByLinha(4);
	console.log('load no index');
	addProdutoNoCarrinho(1,1,1);
});



function addProdutoNoCarrinho(produtoid, anuncianteid, qtde) {
	console.log('chamou o produto id:' + produtoid);
	
	$.ajax({
		url : urlPadrao + 'site/service/addprodutonocarrinho/1',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				
				
				console.log("item:" + item.titulo);
				
			});
			
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

