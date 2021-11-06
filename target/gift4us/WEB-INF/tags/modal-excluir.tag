<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="url" required="true"%>
<%@ attribute name="name" required="true"%>

<div class="modal fade" tabindex="-1" id="modal-excluir" aria-labelledby="modal-excluir-label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title" id="modal-excluir-label">
					<i class="fas fa-trash-alt" aria-hidden="true"></i> ${mensagens.get('ModalExclusaoTitulo').valor}
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<p>
					${mensagens.get('ModalExclusaoMensagemInicio').valor} <strong id="modal-item-excluir"></strong>${mensagens.get('ModalExclusaoMensagemFim').valor}
				</p>
				<form id="modal-form-excluir" action="${url}" method="post">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input id="modal-form-campo-excluir" type="hidden" name="${name}" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger text-white" id="btn-confirmacao-exclusao">${mensagens.get('ModalExclusaoBotaoSim').valor}</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${mensagens.get('ModalExclusaoBotaoNao').valor}</button>
			</div>
		</div>
	</div>
</div>