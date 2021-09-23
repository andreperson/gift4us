<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>


<c:set var="cssFiles"
	value="../css/bootstrap/bootstrap-toggle.min.css,../css/bootstrap/select2.min.css,../css/bootstrap/select2-bootstrap4.min.css" />
<c:set var="jsFiles"
	value="/js/AnuncianteFormulario.js,/js/jquery/bootstrap-toggle.min.js,js/jquery/select2.min.js,/js/jquery/select2-pt-BR.js" />


<my:template fluido="false"
	title="${mensagens.get('AnuncianteTipoFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty anunciante.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_ANUNCIANTE%>" />
	</c:if>
	<c:if test="${not empty anunciante.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_ANUNCIANTE%>" />
	</c:if>
	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('AnuncianteFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form class="msbc-validator-form" id="formulario-form"
							name="formulario" action="${url}" method="POST">
							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />

							<c:if test="${not empty anunciante.id}">
								<input type="hidden" name="id" id="id" value="${anunciante.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="razaosocial">${mensagens.get('AnuncianteFormularioRazaosocial').valor}:</label>
								<input type="text" id="razaosocial" name="razaosocial"
									class="form-control msbc-validator"
									value="${anunciante.razaosocial}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="fantasia">${mensagens.get('AnuncianteFormularioFantasia').valor}:</label>
								<input type="text" id="fantasia" name="fantasia"
									class="form-control msbc-validator"
									value="${anunciante.fantasia}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="email">${mensagens.get('AnuncianteFormularioEmail').valor}:</label>
								<input type="text" id="email" name="email"
									class="form-control msbc-validator" value="${anunciante.email}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="ddd">${mensagens.get('AnuncianteFormularioDdd').valor}:</label>
								<input type="text" id="ddd" name="ddd"
									class="form-control msbc-validator" value="${anunciante.ddd}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="telefone">${mensagens.get('AnuncianteFormularioTelefone').valor}:</label>
								<input type="text" id="telefone" name="telefone"
									class="form-control msbc-validator"
									value="${anunciante.telefone}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="" for="anuncianteTipo">${mensagens.get('AnuncianteFormularioAnuncianteTipo').valor}:</label>
								<select class="form-control msbc-validator"
									name="anuncianteTipo.id" id="anuncianteTipo"
									data-selecionado='${anunciante.anuncianteTipo.id}'
									data-msbc-required="Campo obrigatório"></select>
							</div>

							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${empty anunciante.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('AnuncianteFormularioBotaoInsere').valor}" />
									</c:if>
									<c:if test="${not empty anunciante.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('AnuncianteFormularioBotaoAltera').valor}" />
									</c:if>
								</div>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
</my:template>