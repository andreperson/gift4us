var listaDeCampanha = new ListaObjetoIdValor('listaDeCampanha');
function carregaDadosDoCampanha() {

	$('#campanha').find('option').remove();
	$('#campanha').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/campanha',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.nome);
				$('#campanha').append($('<option>', {
					value : item.id,
					text : item.nome
				}));
				listaDeCampanha.adiciona(objeto);
			});
			var id = $('#campanha').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeCampanha.buscaPorId(id);
				$('#campanha').val(id);
				$('#select2-campanha-container').prop('title', objeto.getValor()).text(objeto.getValor());
			}
		}
	});
};

$(document).ready(function() {

	console.log('campanha-load');
	$('#campanha').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});

	console.log('antes de carregar os dados campanha');
	carregaDadosDoCampanha();
});

