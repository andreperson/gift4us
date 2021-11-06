$(".filtra").keyup(function (){
	$("#corpo-da-tabela").find("tr").removeClass("d-none");
	$(".filtra").each(function(){		
		var texto = $(this).val().toUpperCase();
		var thDaVez = parseInt($(this).closest("th").index());
		
		$("#corpo-da-tabela").find("tr").each(function(){
			var linha = $(this);
			$(this).find("td").each(function(){
				var tdDaVez = parseInt($(this).index());
				if(tdDaVez == thDaVez){					
					var conteudoColuna = $(this).text().replace(new RegExp('\r?\n?\t','g'), "").trim().toUpperCase();
					if(conteudoColuna.indexOf(texto) < 0){
						linha.addClass("d-none");
					}
				}
			});
			
		});
	});
});

$("#limpar-filtros").click(function(){
	$(".filtra").val("").trigger("keyup");
});