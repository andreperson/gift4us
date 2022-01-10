var listaDeAnuncianteTipo = new ListaObjetoIdValor('listaDeAnuncianteTipo');
function carregaDadosDoAnuncianteTipo() {

	$('#anuncianteTipo').find('option').remove();
	$('#anuncianteTipo').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/anunciantetipo',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.nome);
				$('#anuncianteTipo').append($('<option>', {
					value : item.id,
					text : item.nome
				}));
				listaDeAnuncianteTipo.adiciona(objeto);
			});
			var id = $('#anuncianteTipo').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeAnuncianteTipo.buscaPorId(id);
				$('#anuncianteTipo').val(id);
				$('#select2-anuncianteTipo-container').prop('title', objeto.getValor()).text(objeto.getValor());
			}
		}
	});
};

$(document).ready(function() {
	$('#anuncianteTipo').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});

	carregaDadosDoAnuncianteTipo();
});

