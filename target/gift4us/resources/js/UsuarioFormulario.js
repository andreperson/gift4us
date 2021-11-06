var listaDeGrupo = new ListaObjetoIdValor('listaDeGrupo');
var listaDeGrupoSelecionada = new ListaObjetoIdValor('listaDeGrupoSelecionada');
var listaDeAnunciante = new ListaObjetoIdValor('listaDeAnunciante');
function carregaDadosDoAnunciante() {

	$('#anunciante').find('option').remove();
	$('#anunciante').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/anunciante',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.razaosocial);
				$('#anunciante').append($('<option>', {
					value : item.id,
					text : item.razaosocial
				}));
				listaDeAnunciante.adiciona(objeto);
			});
			var id = $('#anunciante').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeAnunciante.buscaPorId(id);
				$('#anunciante').val(id);
				$('#select2-anunciante-container').prop('title', objeto.getValor()).text(objeto.getValor());
			}
		}
	});
};

$(document).ready(function() {
	$('#anunciante').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});

	carregaDadosDoGrupo();
	carregaDadosDoAnunciante();
});

function carregaDadosDoGrupo() {
	$('#grupo-origem').find('option').remove();
		
	$.ajax({
		url : urlPadrao + 'administracao/service/grupo',
		type : 'get',
		async : true,
		success : function(retorno) {
			var id = $('#grupo-destino').attr('data-selecionado');
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
					$('#grupo-destino').append($('<option>', {
						value : item.id,
						text : item.nome
					}));
				}else{
					$('#grupo-origem').append($('<option>', {
						value : item.id,
						text : item.nome
					}));
				}
			});
			ordenaSelect("#grupo-origem", 'text', 'asc');
			ordenaSelect("#grupo-destino", 'text', 'asc');
			criaInputsParaEnvioDeMuitosParaMuitos("#grupo-destino", "#grupo-inputs-objetos-selecionados");
		}
	});
};

$("#grupo-envia-todos-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#grupo-origem", "#grupo-destino", "#grupo-destino-filtro","grupo-destino-filtro-select", true, "#grupo-inputs-objetos-selecionados");
});

$("#grupo-envia-selecionados-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#grupo-origem", "#grupo-destino", "#grupo-destino-filtro","grupo-destino-filtro-select", false, "#grupo-inputs-objetos-selecionados");
});

$("#grupo-envia-todos-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#grupo-origem", "#grupo-destino","#grupo-destino-filtro","grupo-destino-filtro-select", true, "#grupo-inputs-objetos-selecionados");
});

$("#grupo-envia-selecionados-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#grupo-origem", "#grupo-destino","#grupo-destino-filtro","grupo-destino-filtro-select", false, "#grupo-inputs-objetos-selecionados");
});

$("#grupo-origem-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#grupo-origem"), $(("#grupo-origem-filtro-select")), $(this));
});

$("#grupo-destino-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#grupo-destino"), $(("#grupo-destino-filtro-select")), $(this));
});

