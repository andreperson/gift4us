var listaDeCategoria = new ListaObjetoIdValor('listaDeCategoria');
var listaDeSubCategoria = new ListaObjetoIdValor('listaDeSubCategoria');
var listaDeAnunciante = new ListaObjetoIdValor('listaDeAnunciante');
var listaDeFaixaDePreco = new ListaObjetoIdValor('listaDeFaixaDePreco');
var listaDeStatus = new ListaObjetoIdValor('listaDeStatus');
var listaDeLinha = new ListaObjetoIdValor('listaDeLinha');

function subcategoria_click(){
	var subid = $("#subcategoria").val();
	$("#subcategoriaid").val(subid);
}


function faixadepreco_click(){
	var faixaid = $("#faixadepreco").val();
	$("#faixadeprecoid").val(faixaid);
}

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
				console.log('anunciante:' + item.razaosocial);
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

function carregaDadosDaFaixaDePreco() {

	console.log("Faixa de Preço: Load");

	$('#faixadepreco').find('option').remove();
	$('#faixadepreco').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/faixadepreco',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.nome);
				$('#faixadepreco').append($('<option>', {
					value : item.id,
					text : item.nome
				}));
				listaDeFaixaDePreco.adiciona(objeto);
			});
			var id = $('#faixadepreco').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeFaixaDePreco.buscaPorId(id);
				$('#faixadepreco').val(id);
				$('#select2-faixadepreco-container').prop('title', objeto.getValor()).text(objeto.getValor());
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



function carregaDadosDaLinha() {

	console.log("carrega dados da linha");

	$('#linha').find('option').remove();
	$('#linha').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/linha',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.nome);
				$('#linha').append($('<option>', {
					value : item.id,
					text : item.nome
				}));
				listaDeLinha.adiciona(objeto);
			});
			var id = $('#linha').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeLinha.buscaPorId(id);
				$('#linha').val(id);
				$('#select2-linha-container').prop('title', objeto.getValor()).text(objeto.getValor());
			}
		}
	});
};




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
				
				console.log('categoria:' + item.nome);
				
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


function carregaDadosDoSubCategoria() {

	$('#subcategoria').find('option').remove();
	$('#subcategoria').append($('<option>', {
		value : '',
		text : '-----------'
	}));
	$.ajax({
		url : urlPadrao + 'administracao/service/subcategoria',
		type : 'get',
		async : true,
		success : function(retorno) {
			retorno.data.forEach(function(item, index) {
				var objeto = new ObjetoIdValor();
				objeto.setId(item.id);
				objeto.setValor(item.nome);
				$('#subcategoria').append($('<option>', {
					value : item.id,
					text : item.nome
				}));
				listaDeSubCategoria.adiciona(objeto);
				
				console.log('subcateg:' + item.nome);
				
			});
			var id = $('#subcategoria').attr('data-selecionado');
			if(id != ''){
				var objeto = listaDeSubCategoria.buscaPorId(id);
				$('#subcategoria').val(id);
				$('#select2-subcategoria-container').prop('title', objeto.getValor()).text(objeto.getValor());
			}
		}
	});
};

$(document).ready(function() {

	$('#faixadepreco').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});


	$('#categoria').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});
	
	$('#subcategoria').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});
	
	$('#status').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});

	$('#linha').select2({
		theme : 'bootstrap4',
		language : 'pt-BR',
		width : '100%'
	});


	console.log('load form produtos');
	carregaDadosDoCategoria();
	carregaDadosDoSubCategoria();
	carregaDadosDoAnunciante();
	carregaDadosDaFaixaDePreco();
	carregaDadosDaLinha();
	carregaDadosDoStatus();
});


