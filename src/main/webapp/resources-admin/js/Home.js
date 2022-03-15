


$(document).ready(function() {
	console.log('load admin dashboard!');
	carregaTotaldeProdutos();
});


function carregaTotaldeProdutos() {
	console.log('load total de produtos');
	
	$.ajax({
		url : urlPadrao + 'administracao/service/produtototal/0',
		type : 'get',
		async : true,
		success : function(retorno) {
		
		
			console.log("retorno total de produtos");
			
			console.log("retorno: " + retorno.data);
			
			var totalprod = retorno.data[0];
			var mesprod = retorno.data[1];
			
			console.log("total: " + totalprod);
			console.log("mes: " + mesprod);
			
			
			$("#totaldeprodutos").html(totalprod);
			
			$("#produtosnomes").html(mesprod);
			
		}
	});
};

