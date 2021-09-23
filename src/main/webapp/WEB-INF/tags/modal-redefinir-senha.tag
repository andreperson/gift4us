<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="url" required="true"%>
<%@ attribute name="name" required="true"%>

<div class="modal fade" tabindex="-1" id="modal-redefinir-senha" aria-labelledby="modal-redefinir-senha-label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header bg-warning">
				<h5 class="modal-title" id="modal-redefinir-senha-label">
					<i class="fas fa-key" aria-hidden="true"></i> ${mensagens.get('ModalRedefinirSenhaTitulo').valor}
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<p>
					${mensagens.get('ModalRedefinirSenhaMensagemInicio').valor} <strong id="modal-item-redefinir-senha"></strong>${mensagens.get('ModalRedefinirSenhaMensagemFim').valor}
				</p>
				<form id="modal-form-redefinir-senha" action="${url}" method="post">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input id="modal-form-campo-redefinir-senha" type="hidden" name="${name}" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-warning" id="btn-confirmacao-redefinir-senha">${mensagens.get('ModalRedefinirSenhaBotaoSim').valor}</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${mensagens.get('ModalRedefinirSenhaBotaoNao').valor}</button>
			</div>
		</div>
	</div>
</div>