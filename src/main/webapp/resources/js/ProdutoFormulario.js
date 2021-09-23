var listaDeCategoria = new ListaObjetoIdValor('listaDeCategoria');
var listaDeCategoriaSelecionada = new ListaObjetoIdValor('listaDeCategoriaSelecionada');
var listaDeSubCategoria = new ListaObjetoIdValor('listaDeSubCategoria');
var listaDeSubCategoriaSelecionada = new ListaObjetoIdValor('listaDeSubCategoriaSelecionada');
var listaDeAnunciante = new ListaObjetoIdValor('listaDeAnunciante');
var listaDeStatus = new ListaObjetoIdValor('listaDeStatus');
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

function carregaDadosDoStatus() {

	$('#status').find('option').remove();
	$('#status').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/status',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.nome);
				$('#status').append($('<option>', {
					value : item.id,
					text : item.nome
				}));
				listaDeStatus.adiciona(objeto);
			});
			var id = $('#status').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeStatus.buscaPorId(id);
				$('#status').val(id);
				$('#select2-status-container').prop('title', objeto.getValor()).text(objeto.getValor());
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

	$('#status').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});

	carregaDadosDoCategoria();
	carregaDadosDoSubCategoria();
	carregaDadosDoAnunciante();
	carregaDadosDoStatus();
});

function carregaDadosDoCategoria() {
	$('#categoria-origem').find('option').remove();
		
	$.ajax({
		url : urlPadrao + 'administracao/service/categoria',
		type : 'get',
		async : true,
		success : function(retorno) {
			var id = $('#categoria-destino').attr('data-selecionado');
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
					$('#categoria-destino').append($('<option>', {
						value : item.id,
						text : item.nome
					}));
				}else{
					$('#categoria-origem').append($('<option>', {
						value : item.id,
						text : item.nome
					}));
				}
			});
			ordenaSelect("#categoria-origem", 'text', 'asc');
			ordenaSelect("#categoria-destino", 'text', 'asc');
			criaInputsParaEnvioDeMuitosParaMuitos("#categoria-destino", "#categoria-inputs-objetos-selecionados");
		}
	});
};

$("#categoria-envia-todos-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#categoria-origem", "#categoria-destino", "#categoria-destino-filtro","categoria-destino-filtro-select", true, "#categoria-inputs-objetos-selecionados");
});

$("#categoria-envia-selecionados-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#categoria-origem", "#categoria-destino", "#categoria-destino-filtro","categoria-destino-filtro-select", false, "#categoria-inputs-objetos-selecionados");
});

$("#categoria-envia-todos-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#categoria-origem", "#categoria-destino","#categoria-destino-filtro","categoria-destino-filtro-select", true, "#categoria-inputs-objetos-selecionados");
});

$("#categoria-envia-selecionados-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#categoria-origem", "#categoria-destino","#categoria-destino-filtro","categoria-destino-filtro-select", false, "#categoria-inputs-objetos-selecionados");
});

$("#categoria-origem-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#categoria-origem"), $(("#categoria-origem-filtro-select")), $(this));
});

$("#categoria-destino-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#categoria-destino"), $(("#categoria-destino-filtro-select")), $(this));
});

function carregaDadosDoSubCategoria() {
	$('#subcategoria-origem').find('option').remove();
		
	$.ajax({
		url : urlPadrao + 'administracao/service/subcategoria',
		type : 'get',
		async : true,
		success : function(retorno) {
			var id = $('#subcategoria-destino').attr('data-selecionado');
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
					$('#subcategoria-destino').append($('<option>', {
						value : item.id,
						text : item.nome
					}));
				}else{
					$('#subcategoria-origem').append($('<option>', {
						value : item.id,
						text : item.nome
					}));
				}
			});
			ordenaSelect("#subcategoria-origem", 'text', 'asc');
			ordenaSelect("#subcategoria-destino", 'text', 'asc');
			criaInputsParaEnvioDeMuitosParaMuitos("#subcategoria-destino", "#subcategoria-inputs-objetos-selecionados");
		}
	});
};

$("#subcategoria-envia-todos-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#subcategoria-origem", "#subcategoria-destino", "#subcategoria-destino-filtro","subcategoria-destino-filtro-select", true, "#subcategoria-inputs-objetos-selecionados");
});

$("#subcategoria-envia-selecionados-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#subcategoria-origem", "#subcategoria-destino", "#subcategoria-destino-filtro","subcategoria-destino-filtro-select", false, "#subcategoria-inputs-objetos-selecionados");
});

$("#subcategoria-envia-todos-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#subcategoria-origem", "#subcategoria-destino","#subcategoria-destino-filtro","subcategoria-destino-filtro-select", true, "#subcategoria-inputs-objetos-selecionados");
});

$("#subcategoria-envia-selecionados-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#subcategoria-origem", "#subcategoria-destino","#subcategoria-destino-filtro","subcategoria-destino-filtro-select", false, "#subcategoria-inputs-objetos-selecionados");
});

$("#subcategoria-origem-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#subcategoria-origem"), $(("#subcategoria-origem-filtro-select")), $(this));
});

$("#subcategoria-destino-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#subcategoria-destino"), $(("#subcategoria-destino-filtro-select")), $(this));
});

$(document).ready(function() {
	$('.texto-html').each(function() {
		$(this).jqte();
	});
});

