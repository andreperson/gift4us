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
	value="jquery/bootstrap-toggle.min.js,jquery/select2.min.js,jquery/select2-pt-BR.js,ObjetoIdValor.js,OrcamentoFormulario.js" />
<my:template fluido="false"
	title="${mensagens.get('OrcamentoFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty orcamento.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_ORCAMENTO%>" />
	</c:if>
	<c:if test="${not empty orcamento.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_ORCAMENTO%>" />
	</c:if>


	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('OrcamentoFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form class="msbc-validator-form" id="formulario-form"
							name="formulario" action="${url}" method="POST">
							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty orcamento.id}">
								<input type="hidden" name="id" id="id" value="${orcamento.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="quantidade">${mensagens.get('OrcamentoFormularioQuantidade').valor}:</label>
								<input type="text" id="quantidade" name="quantidade"
									class="form-control msbc-validator"
									value="${orcamento.quantidade}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="2|O campo deve ter no máximo {value} caracteres"
									data-msbc-type-value="somente-numero" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataIncl">${mensagens.get('OrcamentoFormularioDataIncl').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${orcamento.dataIncl.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataIncl" id="dataIncl" value="${data}"
									autocomplete="off" data-msbc-required="Campo obrigatório" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataAlt">${mensagens.get('OrcamentoFormularioDataAlt').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${orcamento.dataAlt.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataAlt" id="dataAlt" value="${data}" autocomplete="off"
									data-msbc-required="Campo obrigatório" />
							</div>

							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="produto">${mensagens.get('OrcamentoFormularioProduto').valor}:</label>
								<select class="form-select msbc-validator" name="produto.id"
									id="produto" data-selecionado='${orcamento.produto.id}'
									data-msbc-required="Campo obrigatório"></select>
							</div>


							<div class="div-msbc-validator mb-3">
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<label class="col-form-label text-right"
											for="anunciante-origem-filtro">${mensagens.get("OrcamentoFormularioAnunciante").valor}:</label>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="anunciante-origem-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="anunciante-origem-filtro" /> <select
											class="form-select" name="origem" id="anunciante-origem"
											multiple="multiple" size="5"></select>
									</div>
									<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2 text-center">
										<i id="anunciante-envia-todos-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-double-left"
											style="margin-top: 5px"></i><br /> <i
											id="anunciante-envia-selecionados-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-left"
											style="margin-top: 10px"></i><br /> <i
											id="anunciante-envia-selecionados-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-right"
											style="margin-top: 10px"></i><br /> <i
											id="anunciante-envia-todos-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-double-right"
											style="margin-top: 10px"></i>
									</div>
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="anunciante-destino-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="anunciante-destino-filtro" />
										<c:set var="selecionado" value="" />
										<c:forEach items="${orcamento.listaDeAnunciante}"
											var="anunciante">
											<c:if test="${not empty selecionado}">
												<c:set var="selecionado"
													value="${selecionado},${anunciante.id}" />
											</c:if>
											<c:if test="${empty selecionado}">
												<c:set var="selecionado" value="${anunciante.id}" />
											</c:if>
										</c:forEach>
										<select class="form-select" name="anunciante"
											id="anunciante-destino" multiple="multiple" size="5"
											data-selecionado="${selecionado}"
											data-name="listaDeAnunciante[].id"></select> <span
											id="anunciante-inputs-objetos-selecionados"
											style="display: none;"></span>
									</div>
								</div>
							</div>

							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${empty orcamento.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('OrcamentoFormularioBotaoInsere').valor}" />
									</c:if>
									<c:if test="${not empty orcamento.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('OrcamentoFormularioBotaoAltera').valor}" />
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