$(".modal-excluir-link").click(function() {
	$("#modal-form-campo-excluir").val($(this).attr("data-id"));
	$("#modal-item-excluir").html($(this).attr("data-descricao"));
	var m = new bootstrap.Modal(document.getElementById("modal-excluir"));
	m.show();
});
$("#btn-confirmacao-exclusao").click(function() {
	$("#modal-form-excluir").submit();
});

$(".modal-redefinir-senha-link").click(function() {
	$("#modal-form-campo-redefinir-senha").val($(this).attr("data-id"));
	$("#modal-item-redefinir-senha").html($(this).attr("data-descricao"));
	var m = new bootstrap.Modal(document.getElementById("modal-redefinir-senha"));
	m.show();
});
$("#btn-confirmacao-redefinir-senha").click(function() {
	$("#modal-form-redefinir-senha").submit();
});

jQuery.datetimepicker.setLocale('pt-BR');

$('.datatime').datetimepicker({
	timepicker : true,
	format : "d/m/Y H:i:s",
	step : 15,
	useCurrent : false
});

$('.data').datetimepicker({
	timepicker : false,
	format : "d/m/Y",
	useCurrent : false
});

function remove(objeto) {
	var id = $(objeto).attr('data-id');
	var idObjeto = $(objeto).attr('data-idObjeto');
	var listaSelecionado = eval($(objeto).attr('data-listaSelecionado'));
	var idObjetoSelecionado = $(objeto).attr('data-idObjetoSelecionado');

	listaSelecionado.remove(id);
	atualizaMuitosParaMuitos(idObjeto, listaSelecionado, idObjetoSelecionado);
}

function atualizaMuitosParaMuitos(idObjeto, listaSelecionado,
		idObjetoSelecionado) {
	var div = $(('#' + idObjeto));
	div.html("");
	var contador = 0;
	var listaSelecionadoArray = listaSelecionado.listaTodos();
	var html = "";
	listaSelecionadoArray
			.forEach(function(item, index) {
				html += '<tr>';
				html += '<td>'
						+ item.getValor()
						+ '</td><td class="text-right"><a href="#" onclick="remove(this)" class="btn btn-primary" data-id="'
						+ item.getId() + '" data-idObjeto="' + idObjeto
						+ '" data-listaSelecionado="' + listaSelecionado.nome()
						+ '" data-idObjetoSelecionado="' + idObjetoSelecionado
						+ '">Remover</a>';

				var name = idObjetoSelecionado.replace("[]", "[" + contador
						+ "]");
				html += '<input type="hidden" name="' + name + '" value="'
						+ item.getId() + '" /></td>';
				contador++;
				html += '</tr>';
			});
	if (html != '') {
		div.append('<table class="table">' + html + '</table>');
	}
}

function filtraDeSelectParaSelect(filtroSelectOrigem, filtroSelectDestino,
		conteudo) {

	filtroSelectOrigem.find("option").each(
			function() {
				console.log($(this).text());
				if ($(this).text().toUpperCase().indexOf(
						conteudo.val().toUpperCase()) < 0) {
					filtroSelectDestino.append($(this));
				}
			});

	filtroSelectDestino.find("option").each(
			function() {
				if ($(this).text().toUpperCase().indexOf(
						conteudo.val().toUpperCase()) >= 0) {
					filtroSelectOrigem.append($(this));
				}
			});

	ordenaSelect(filtroSelectOrigem, 'text', 'asc');
	filtroSelectOrigem.find("option:selected").prop("selected", false);

}

function copiaDeSelectParaSelect(origem, destino, todos) {
	todos = todos == true ? " option" : " option:selected";
	$(origem).find(todos).each(function() {
		var option = $(this);
		$(destino).append($('<option>', {
			value : option.val(),
			text : option.text()
		}));
		option.remove();
	});
	ordenaSelect(destino, 'text', 'asc');
	ordenaSelect(origem, 'text', 'asc');
	$((origem + " option:selected")).prop("selected", false);
	$((destino + " option:selected")).prop("selected", false);

}

var ordenaSelect = function(select, attr, order) {
	if (attr === 'text') {
		if (order === 'asc') {
			$(select).html(
					$(select).children('option').sort(
							function(x, y) {
								return $(x).text().toUpperCase() < $(y).text()
										.toUpperCase() ? -1 : 1;
							}));
		}
		if (order === 'desc') {
			$(select).html(
					$(select).children('option').sort(
							function(y, x) {
								return $(x).text().toUpperCase() < $(y).text()
										.toUpperCase() ? -1 : 1;
							}));
		}
	} else if (attr === 'value') {
		if (order === 'asc') {
			$(select).html(
					$(select).children('option').sort(
							function(x, y) {
								return $(x).val().toUpperCase() < $(y).val()
										.toUpperCase() ? -1 : 1;
							}));
		}
		if (order === 'desc') {
			$(select).html(
					$(select).children('option').sort(
							function(y, x) {
								return $(x).val().toUpperCase() < $(y).val()
										.toUpperCase() ? -1 : 1;
							}));
		}
	}

};

function enviaOptionsDetinoParaOrigem(selectOrigem, selectDestino, filtro, selectDestinoFiltrado, todosElementos, inputs){
	var valor = $(filtro).val();
	copiaDeSelectParaSelect(selectDestino, selectOrigem, todosElementos);
	$(filtro).val("").keyup();
	criaInputsParaEnvioDeMuitosParaMuitos(selectDestino, inputs);
	$(filtro).val(valor).keyup();
}

function enviaOptionsOrigemParaDetino(selectOrigem, selectDestino, filtro, selectDestinoFiltrado, todosElementos, inputs){
	var valor = $(filtro).val();
	$(filtro).val("").keyup();
	copiaDeSelectParaSelect(selectOrigem, selectDestino, todosElementos);
	criaInputsParaEnvioDeMuitosParaMuitos(selectDestino, inputs);
	$(filtro).val(valor).keyup();
}

function criaInputsParaEnvioDeMuitosParaMuitos(selectDestino, inputsDestino){
	var contador = 0;
	var name = $(selectDestino).attr("data-name");
	$(inputsDestino).html("");
	$(selectDestino).find("option").each(function() {
		var option = $(this);
		$(inputsDestino).append($('<input>',{
			type: "text",
			value: option.val(),
			name: name.replace("[]", "[" + contador + "]")
		}));
		contador++;
	});
}