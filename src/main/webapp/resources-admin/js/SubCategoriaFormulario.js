var listaDeCategoria = new ListaObjetoIdValor('listaDeCategoria');
function carregaDadosDoCategoria() {

	$('#categoria').find('option').remove();
	$('#categoria').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/categoria',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.nome);
				$('#categoria').append($('<option>', {
					value : item.id,
					text : item.nome
				}));
				listaDeCategoria.adiciona(objeto);
			});
			var id = $('#categoria').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeCategoria.buscaPorId(id);
				$('#categoria').val(id);
				$('#select2-categoria-container').prop('title', objeto.getValor()).text(objeto.getValor());
			}
		}
	});
};

$(document).ready(function() {

	console.log('subcategoria-load');
	$('#categoria').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});

	console.log('antes de carregar os dados subcat');
	carregaDadosDoCategoria();
});

