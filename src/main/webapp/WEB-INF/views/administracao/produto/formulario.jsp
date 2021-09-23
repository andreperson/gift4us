<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:set var="cssFiles"
	value="bootstrap/bootstrap-toggle.min.css,bootstrap/select2.min.css,bootstrap/select2-bootstrap4.min.css,jqte/jquery-te.css" />
<c:set var="jsFiles"
	value="jquery/bootstrap-toggle.min.js,jquery/select2.min.js,jquery/select2-pt-BR.js,ObjetoIdValor.js,jquery/jquery-te.min.js,ProdutoFormulario.js" />
<my:template fluido="false"
	title="${mensagens.get('ProdutoFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty produto.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_PRODUTO%>" />
	</c:if>
	<c:if test="${not empty produto.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_PRODUTO%>" />
	</c:if>


	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('ProdutoFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form class="msbc-validator-form" id="formulario-form"
							name="formulario" action="${url}" method="POST">
							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty produto.id}">
								<input type="hidden" name="id" id="id" value="${produto.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="codigo">${mensagens.get('ProdutoFormularioCodigo').valor}:</label>
								<input type="text" id="codigo" name="codigo"
									class="form-control msbc-validator" value="${produto.codigo}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="50|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="titulo">${mensagens.get('ProdutoFormularioTitulo').valor}:</label>
								<input type="text" id="titulo" name="titulo"
									class="form-control msbc-validator" value="${produto.titulo}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="brevedescricao">${mensagens.get('ProdutoFormularioBrevedescricao').valor}:</label>
								<input type="text" id="brevedescricao" name="brevedescricao"
									class="form-control msbc-validator"
									value="${produto.brevedescricao}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="descricaocompleta">${mensagens.get('ProdutoFormularioDescricaocompleta').valor}:</label>
								<textarea class="form-control texto-html msbc-validator"
									name="descricaocompleta" id="descricaocompleta" rows="5"
									cols="80" data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="3999|O campo deve ter no máximo {value} caracteres">${produto.descricaocompleta}</textarea>
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="tag">${mensagens.get('ProdutoFormularioTag').valor}:</label>
								<input type="text" id="tag" name="tag"
									class="form-control msbc-validator" value="${produto.tag}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="qtdademin">${mensagens.get('ProdutoFormularioQtdademin').valor}:</label>
								<input type="text" id="qtdademin" name="qtdademin"
									class="form-control msbc-validator"
									value="${produto.qtdademin}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="preco">${mensagens.get('ProdutoFormularioPreco').valor}:</label>
								<input type="text" id="preco" name="preco"
									class="form-control msbc-validator" value="${produto.preco}"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="11|O campo deve ter no máximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="faixadepreco">${mensagens.get('ProdutoFormularioFaixadepreco').valor}:</label>
								<textarea class="form-control texto-html msbc-validator"
									name="faixadepreco" id="faixadepreco" rows="5" cols="80"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="100|O campo deve ter no máximo {value} caracteres">${produto.faixadepreco}</textarea>
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="imagem">${mensagens.get('ProdutoFormularioImagem').valor}:</label>
								<textarea class="form-control texto-html msbc-validator"
									name="imagem" id="imagem" rows="5" cols="80"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres">${produto.imagem}</textarea>
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="urlanunciante">${mensagens.get('ProdutoFormularioUrlanunciante').valor}:</label>
								<textarea class="form-control texto-html msbc-validator"
									name="urlanunciante" id="urlanunciante" rows="5" cols="80"
									data-msbc-required="Campo obrigatório"
									data-msbc-maxlength="100|O campo deve ter no máximo {value} caracteres">${produto.urlanunciante}</textarea>
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataIncl">${mensagens.get('ProdutoFormularioDataIncl').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${produto.dataIncl.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataIncl" id="dataIncl" value="${data}"
									autocomplete="off" data-msbc-required="Campo obrigatório" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label text-right" for="dataAlt">${mensagens.get('ProdutoFormularioDataAlt').valor}:</label>
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${produto.dataAlt.time}" var="data" />
								<input class="form-control data msbc-validator" type="text"
									name="dataAlt" id="dataAlt" value="${data}" autocomplete="off"
									data-msbc-required="Campo obrigatório" />
							</div>

							<div class="div-msbc-validator mb-3">
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<label class="col-form-label text-right"
											for="categoria-origem-filtro">${mensagens.get("ProdutoFormularioCategoria").valor}:</label>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="categoria-origem-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="categoria-origem-filtro" /> <select
											class="form-select" name="origem" id="categoria-origem"
											multiple="multiple" size="5"></select>
									</div>
									<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2 text-center">
										<i id="categoria-envia-todos-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-double-left"
											style="margin-top: 5px"></i><br /> <i
											id="categoria-envia-selecionados-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-left"
											style="margin-top: 10px"></i><br /> <i
											id="categoria-envia-selecionados-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-right"
											style="margin-top: 10px"></i><br /> <i
											id="categoria-envia-todos-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-double-right"
											style="margin-top: 10px"></i>
									</div>
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="categoria-destino-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="categoria-destino-filtro" />
										<c:set var="selecionado" value="" />
										<c:forEach items="${produto.listaDeCategoria}" var="categoria">
											<c:if test="${not empty selecionado}">
												<c:set var="selecionado"
													value="${selecionado},${categoria.id}" />
											</c:if>
											<c:if test="${empty selecionado}">
												<c:set var="selecionado" value="${categoria.id}" />
											</c:if>
										</c:forEach>
										<select class="form-select" name="categoria"
											id="categoria-destino" multiple="multiple" size="5"
											data-selecionado="${selecionado}"
											data-name="listaDeCategoria[].id"></select> <span
											id="categoria-inputs-objetos-selecionados"
											style="display: none;"></span>
									</div>
								</div>
							</div>


							<div class="div-msbc-validator mb-3">
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<label class="col-form-label text-right"
											for="subcategoria-origem-filtro">${mensagens.get("ProdutoFormularioSubCategoria").valor}:</label>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="subcategoria-origem-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="subcategoria-origem-filtro" /> <select
											class="form-select" name="origem" id="subcategoria-origem"
											multiple="multiple" size="5"></select>
									</div>
									<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2 text-center">
										<i id="subcategoria-envia-todos-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-double-left"
											style="margin-top: 5px"></i><br /> <i
											id="subcategoria-envia-selecionados-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-left"
											style="margin-top: 10px"></i><br /> <i
											id="subcategoria-envia-selecionados-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-right"
											style="margin-top: 10px"></i><br /> <i
											id="subcategoria-envia-todos-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-double-right"
											style="margin-top: 10px"></i>
									</div>
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="subcategoria-destino-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="subcategoria-destino-filtro" />
										<c:set var="selecionado" value="" />
										<c:forEach items="${produto.listaDeSubCategoria}"
											var="subcategoria">
											<c:if test="${not empty selecionado}">
												<c:set var="selecionado"
													value="${selecionado},${subcategoria.id}" />
											</c:if>
											<c:if test="${empty selecionado}">
												<c:set var="selecionado" value="${subcategoria.id}" />
											</c:if>
										</c:forEach>
										<select class="form-select" name="subcategoria"
											id="subcategoria-destino" multiple="multiple" size="5"
											data-selecionado="${selecionado}"
											data-name="listaDeSubCategoria[].id"></select> <span
											id="subcategoria-inputs-objetos-selecionados"
											style="display: none;"></span>
									</div>
								</div>
							</div>


							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="anunciante">${mensagens.get('ProdutoFormularioAnunciante').valor}:</label>
								<select class="form-select msbc-validator" name="anunciante.id"
									id="anunciante" data-selecionado='${produto.anunciante.id}'
									data-msbc-required="Campo obrigatório"></select>
							</div>


							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="status">${mensagens.get('ProdutoFormularioStatus').valor}:</label>
								<select class="form-select msbc-validator" name="status.id"
									id="status" data-selecionado='${produto.status.id}'
									data-msbc-required="Campo obrigatório"></select>
							</div>

							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${empty produto.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('ProdutoFormularioBotaoInsere').valor}" />
									</c:if>
									<c:if test="${not empty produto.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('ProdutoFormularioBotaoAltera').valor}" />
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