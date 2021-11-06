<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs" %>
<my:template fluido="false" title="${mensagens.get('AlterarSenhaTitulo').valor}">
	<c:url var="url" value="<%=ListaDeURLs.ALTERAR_SENHA %>" />
	<div class="col">
		<div class="card w-100">
			<div class="card-header bg-info">
				<h4 class="text-white">${mensagens.get('AlterarSenhaTitulo').valor}</h4>
			</div>
			<div class="card-body">
				<form id="formulario-form" class="msbc-validator-form" name="formulario" action="${url}" method="POST" >
					<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div class="div-msbc-validator mb-3">
						<label class="col-form-label" for="senhaAtual">${mensagens.get('AlterarSenhaSenhaAtual').valor}:</label>
						<input class="form-control msbc-validator" name="senhaAtual" id="senhaAtual" type="password"
							data-msbc-required="Campo obrigatório"
							data-msbc-maxlength="30|O campo deve ter no máximo {value} caracteres" />
					</div>
					<div class="div-msbc-validator mb-3">
						<label class="col-form-label" for="novaSenha">${mensagens.get('AlterarSenhaNovaSenha').valor}:</label>
						<input class="form-control msbc-validator" name="novaSenha" id="novaSenha" type="password"
							data-msbc-required="Campo obrigatório"
							data-msbc-maxlength="30|O campo deve ter no máximo {value} caracteres" />
					</div>
					<div class="div-msbc-validator mb-3">
						<label class="col-form-label" for="repeteSenha">${mensagens.get('AlterarSenhaRepetirNovaSenha').valor}:</label>
						<input class="form-control msbc-validator" name="repeteSenha" id="repeteSenha" type="password"
							data-msbc-required="Campo obrigatório"
							data-msbc-maxlength="30|O campo deve ter no máximo {value} caracteres"
							data-msbc-dependency-equals-value-id="novaSenha|A repetição de senha não confere com a nova senha" />
					</div>
					<div class="mb-3">
						<div class="col-md-4 offset-md-4">
							<button class="btn btn-success w-100 msbc-validator-button-submit" type="submit">${mensagens.get('AlterarSenhaBotao').valor}</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</my:template>