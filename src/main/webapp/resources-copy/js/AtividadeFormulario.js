var listaDeProduto = new ListaObjetoIdValor('listaDeProduto');
var listaDeProdutoSelecionada = new ListaObjetoIdValor('listaDeProdutoSelecionada');
$(document).ready(function() {
	carregaDadosDoProduto();
});

function carregaDadosDoProduto() {
	$('#produto-origem').find('option').remove();
		
	$.ajax({
		url : urlPadrao + 'administracao/service/produto',
		type : 'get',
		async : true,
		success : function(retorno) {
			var id = $('#produto-destino').attr('data-selecionado');
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
					$('#produto-destino').append($('<option>', {
						value : item.id,
						text : item.codigo
					}));
				}else{
					$('#produto-origem').append($('<option>', {
						value : item.id,
						text : item.codigo
					}));
				}
			});
			ordenaSelect("#produto-origem", 'text', 'asc');
			ordenaSelect("#produto-destino", 'text', 'asc');
			criaInputsParaEnvioDeMuitosParaMuitos("#produto-destino", "#produto-inputs-objetos-selecionados");
		}
	});
};

$("#produto-envia-todos-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#produto-origem", "#produto-destino", "#produto-destino-filtro","produto-destino-filtro-select", true, "#produto-inputs-objetos-selecionados");
});

$("#produto-envia-selecionados-para-destino").click(function() {
	enviaOptionsOrigemParaDetino("#produto-origem", "#produto-destino", "#produto-destino-filtro","produto-destino-filtro-select", false, "#produto-inputs-objetos-selecionados");
});

$("#produto-envia-todos-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#produto-origem", "#produto-destino","#produto-destino-filtro","produto-destino-filtro-select", true, "#produto-inputs-objetos-selecionados");
});

$("#produto-envia-selecionados-para-origem").click(function() {
	enviaOptionsDetinoParaOrigem("#produto-origem", "#produto-destino","#produto-destino-filtro","produto-destino-filtro-select", false, "#produto-inputs-objetos-selecionados");
});

$("#produto-origem-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#produto-origem"), $(("#produto-origem-filtro-select")), $(this));
});

$("#produto-destino-filtro").keyup(function() {
	filtraDeSelectParaSelect($("#produto-destino"), $(("#produto-destino-filtro-select")), $(this));
});

