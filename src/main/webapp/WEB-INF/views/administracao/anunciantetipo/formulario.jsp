<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:set var="cssFiles" value="bootstrap/bootstrap-toggle.min.css" />
<c:set var="jsFiles" value="jquery/bootstrap-toggle.min.js" />
<my:template fluido="false"
	title="${mensagens.get('AnuncianteTipoFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty anuncianteTipo.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_ANUNCIANTETIPO%>" />
	</c:if>
	<c:if test="${not empty anuncianteTipo.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_ANUNCIANTETIPO%>" />
	</c:if>


	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('AnuncianteTipoFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form class="msbc-validator-form" id="formulario-form"
							name="formulario" action="${url}" method="POST">
							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty anuncianteTipo.id}">
								<input type="hidden" name="id" id="id"
									value="${anuncianteTipo.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="nome">${mensagens.get('AnuncianteTipoFormularioNome').valor}:</label>
								<input type="text" id="nome" name="nome"
									class="form-control msbc-validator"
									value="${anuncianteTipo.nome}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataIncl">${mensagens.get('AnuncianteTipoFormularioDataIncl').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${anuncianteTipo.dataIncl.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataIncl" id="dataIncl" value="${data}"
									autocomplete="off" data-msbc-required="Campo obrigatório" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataAlt">${mensagens.get('AnuncianteTipoFormularioDataAlt').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${anuncianteTipo.dataAlt.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataAlt" id="dataAlt" value="${data}" autocomplete="off"
									data-msbc-required="Campo obrigatório" />
							</div>
							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${empty anuncianteTipo.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('AnuncianteTipoFormularioBotaoInsere').valor}" />
									</c:if>
									<c:if test="${not empty anuncianteTipo.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('AnuncianteTipoFormularioBotaoAltera').valor}" />
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