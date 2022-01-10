var listaDeProduto = new ListaObjetoIdValor('listaDeProduto');
var listaDeAnunciante = new ListaObjetoIdValor('listaDeAnunciante');
var listaDeAnuncianteSelecionada = new ListaObjetoIdValor('listaDeAnuncianteSelecionada');
function carregaDadosDoProduto() {

	$('#produto').find('option').remove();
	$('#produto').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/produto',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.codigo);
				$('#produto').append($('<option>', {
					value : item.id,
					text : item.codigo
				}));
				listaDeProduto.adiciona(objeto);
			});
			var id = $('#produto').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeProduto.buscaPorId(id);
				$('#produto').val(id);
				$('#select2-produto-container').prop('title', objeto.getValor()).text(objeto.getValor());
			}
		}
	});
};

$(document).ready(function() {
	$('#produto').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});

	carregaDadosDoProduto();
	carregaDadosDoAnunciante();
});

function carregaDadosDoAnunciante() {
	$('#anunciante-origem').find('option').remove();
		
	$.ajax({
		url : urlPadrao + 'administracao/service/anunciante',
		type : 'get',
		async : true,
		success : function(retorno) {
			var id = $('#anunciante-destino').attr('data-selecionado');
			if(id != ''){
				if(id.indexOf(',') >=0){
					id = id.split(',');
				}else{
					var temp = id;
					id = new Array();
					id.push(temp);
				}
			}
			retorno.data.forEach(function(item, index) {
				
				var selecionado = false;
				if(id != ''){					
					id.forEach(function(item2, index2){
						if(item.id == item2){
							selecionado = true;
							return;
						}
					});
				}
				if(selecionado){
					$('#anunciante-destino').append($('<option>', {
						value : item.id,
						text : item.razaosocial
					}));
				}else{
					$('#anunciante-origem').append($('<option>', {
						value : item.id,
						text : item.razaosocial
					}));
				}
			});
			ordenaSelect("#anunciante-origem", 'text', 'asc');
			ordenaSelect("#anunciante-destino", 'text', 'asc');
			criaInputsParaEnvioDeMuitosParaMuitos("#anunciante-destino", "#anunciante-inputs-objetos-selecionados");
		}
	});
};

$("#anunciante-envia-todos-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#anunciante-origem", "#anunciante-destino", "#anunciante-destino-filtro","anunciante-destino-filtro-select", true, "#anunciante-inputs-objetos-selecionados");
});

$("#anunciante-envia-selecionados-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#anunciante-origem", "#anunciante-destino", "#anunciante-destino-filtro","anunciante-destino-filtro-select", false, "#anunciante-inputs-objetos-selecionados");
});

$("#anunciante-envia-todos-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#anunciante-origem", "#anunciante-destino","#anunciante-destino-filtro","anunciante-destino-filtro-select", true, "#anunciante-inputs-objetos-selecionados");
});

$("#anunciante-envia-selecionados-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#anunciante-origem", "#anunciante-destino","#anunciante-destino-filtro","anunciante-destino-filtro-select", false, "#anunciante-inputs-objetos-selecionados");
});

$("#anunciante-origem-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#anunciante-origem"), $(("#anunciante-origem-filtro-select")), $(this));
});

$("#anunciante-destino-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#anunciante-destino"), $(("#anunciante-destino-filtro-select")), $(this));
});

