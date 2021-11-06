$(document).ready(function() {
	/* Caso a coluna tenha datas, setar o formato na linha abaixo conforme exemplo para que a ordenação funcione corretamente
	 * requerido importar: moment.js e datetime-moment.js 
	 */ 
	$.fn.dataTable.moment('DD/MM/YYYY - HH:mm:ss');
	
	$(".dt-table").each(function() {
		var tabela = $(this).DataTable({	
			/* requerido importar: 'responsive.bootstrap4.min.css' e 'responsive.bootstrap4.min.js' */
			responsive: true, 	
			/* requerido importar: 'buttons.bootstrap4.min.css' e 'buttons.bootstrap4.min.js' */
			buttons: [
	        	{
			        extend: 'excel',
			        text: '<i class="fas fa-file-excel"></i>',
			        className: 'btn btn-success btn-sm',
			        exportOptions: {
			            columns: 'th:not(:last-child)' /* Não exportar a última coluna que normalmente são botões de ação */
			        }
			    },
			    {
			    	extend: 'print',
			    	text: '<i class="fas fa-print"></i>',
			    	className: 'btn btn-secondary btn-sm',
			    	exportOptions: {
			    		columns: 'th:not(:last-child)' /* Não exportar a última coluna que normalmente são botões de ação */
			    	}
			    }
	    	],
			"order": [ 0, 'asc' ], /* default */
	    	"pagingType": "full_numbers",
			"pageLength": 10, /* default */
			/* Caso tenha tooltip, adicionar a linha abaixo para iniciar em todas as páginas */
			"createdRow": function( row, data, dataIndex ) {
			    $('[data-toggle="tooltip"]').tooltip();
			 },
	    	/* PT-BT */
			"language": {
	        	"sEmptyTable": "Nenhum registro encontrado",
			    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
			    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
			    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
			    "sInfoThousands": ".",
			    "sLengthMenu": "_MENU_ resultados por página",
			    "sLoadingRecords": "Carregando...",
			    "sProcessing": "Processando...",
			    "sZeroRecords": "Nenhum registro encontrado",
			    "sSearch": "Pesquisar",
			    "oPaginate": {
			        "sNext": "Próximo",
			        "sPrevious": "Anterior",
			        "sFirst": "Primeiro",
			        "sLast": "Último"
			    },
			    "oAria": {
			        "sSortAscending": ": Ordenar colunas de forma ascendente",
			        "sSortDescending": ": Ordenar colunas de forma descendente"
			    },
			    "select": {
			        "rows": {
			            "_": "Selecionado %d linhas",
			            "0": "Nenhuma linha selecionada",
			            "1": "Selecionado 1 linha"
			        }
			    },
			    "buttons": {
			        "copy": "Copiar para a área de transferência",
			        "copyTitle": "Cópia bem sucedida",
			        "copySuccess": {
			            "1": "Uma linha copiada com sucesso",
			            "_": "%d linhas copiadas com sucesso"
			        }
			    }
	        }
		});

		/* Linha que adiciona os botões de ação na div pré-definida, caso não tenha botões é opcional */
		tabela.buttons().container().appendTo($(this).closest(".card").find('.div-btn-export'));
	});
});
