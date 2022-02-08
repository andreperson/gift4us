


$(document).ready(function() {
	//carregaProdutoByLinha(4);
});


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

