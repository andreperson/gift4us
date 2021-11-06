<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:set var="cssFiles"
	value="bootstrap/bootstrap-toggle.min.css,bootstrap/select2.min.css,bootstrap/select2-bootstrap4.min.css" />
<c:set var="jsFiles"
	value="jquery/bootstrap-toggle.min.js,jquery/select2.min.js,jquery/select2-pt-BR.js,ObjetoIdValor.js,AtividadeFormulario.js" />
<my:template fluido="false"
	title="${mensagens.get('AtividadeFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty atividade.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_ATIVIDADE%>" />
	</c:if>
	<c:if test="${not empty atividade.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_ATIVIDADE%>" />
	</c:if>

	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('AtividadeFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form class="msbc-validator-form" id="formulario-form"
							name="formulario" action="${url}" method="POST">
							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty atividade.id}">
								<input type="hidden" name="id" id="id" value="${atividade.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="nome">${mensagens.get('AtividadeFormularioNome').valor}:</label>
								<input type="text" id="nome" name="nome"
									class="form-control msbc-validator" value="${atividade.nome}" />
							</div>

							<div class="div-msbc-validator mb-3">
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<label class="col-form-label text-right"
											for="produto-origem-filtro">${mensagens.get("AtividadeFormularioProduto").valor}:</label>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="produto-origem-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="produto-origem-filtro" /> <select
											class="form-select" name="origem" id="produto-origem"
											multiple="multiple" size="5"></select>
									</div>
									<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2 text-center">
										<i id="produto-envia-todos-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-double-left"
											style="margin-top: 5px"></i><br /> <i
											id="produto-envia-selecionados-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-left"
											style="margin-top: 10px"></i><br /> <i
											id="produto-envia-selecionados-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-right"
											style="margin-top: 10px"></i><br /> <i
											id="produto-envia-todos-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-double-right"
											style="margin-top: 10px"></i>
									</div>
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="produto-destino-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="produto-destino-filtro" />
										<c:set var="selecionado" value="" />
										<c:forEach items="${atividade.listaDeProduto}" var="produto">
											<c:if test="${not empty selecionado}">
												<c:set var="selecionado"
													value="${selecionado},${produto.id}" />
											</c:if>
											<c:if test="${empty selecionado}">
												<c:set var="selecionado" value="${produto.id}" />
											</c:if>
										</c:forEach>
										<select class="form-select" name="produto"
											id="produto-destino" multiple="multiple" size="5"
											data-selecionado="${selecionado}"
											data-name="listaDeProduto[].id"></select> <span
											id="produto-inputs-objetos-selecionados"
											style="display: none;"></span>
									</div>
								</div>
							</div>

							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataIncl">${mensagens.get('AtividadeFormularioDataIncl').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${atividade.dataIncl.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataIncl" id="dataIncl" value="${data}"
									autocomplete="off" data-msbc-required="Campo obrigatório" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataAlt">${mensagens.get('AtividadeFormularioDataAlt').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${atividade.dataAlt.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataAlt" id="dataAlt" value="${data}" autocomplete="off"
									data-msbc-required="Campo obrigatório" />
							</div>
							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${empty atividade.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('AtividadeFormularioBotaoInsere').valor}" />
									</c:if>
									<c:if test="${not empty atividade.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('AtividadeFormularioBotaoAltera').valor}" />
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