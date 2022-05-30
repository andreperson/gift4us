<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="${urlpadrao}_modelos" var="urlmodelo" />


<c:set var="cssFiles"
	value="../css/bootstrap/bootstrap-toggle.min.css,../css/bootstrap/select2.min.css,../css/bootstrap/select2-bootstrap4.min.css" />
<c:set var="jsFiles"
	value="/js/ProdutoFormulario.js,/js/jquery/bootstrap-toggle.min.js,js/jquery/select2.min.js,/js/jquery/select2-pt-BR.js" />


<my:template fluido="false"
	title="${mensagens.get('ProdutoFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">

	<c:if test="${not empty produto.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_PRODUTO%>" />
		<c:url var="urlimg"
			value="<%=ListaDeURLs.FORMULARIO_INSERCAO_DE_IMAGEM%>" />
	</c:if>

	<c:if test="${empty produto.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_PRODUTO%>" />

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
						<form class="msbc-validator-form" id="formproduto"
							name="formproduto"
							action="${url}?${_csrf.parameterName}=${_csrf.token}"
							enctype="multipart/form-data" method="post">

							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty produto.id}">
								<input type="hidden" name="id" id="id" value="${produto.id}" />
							</c:if>

							<div class="row">
								<div class="div-msbc-validator col-md-3 mb-3">
									<label class="col-form-label" for="codigo">${mensagens.get('ProdutoFormularioCodigo').valor}:</label>
									<input type="text" id="codigo" name="codigo"
										class="form-control" value="${produto.codigo}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="50|O campo deve ter no máximo {value} caracteres" />
								</div>
								<div class="div-msbc-validator col-md-9 mb-3">
									<label class="col-form-label" for="titulo">${mensagens.get('ProdutoFormularioTitulo').valor}:</label>
									<input type="text" id="titulo" name="titulo"
										class="form-control" value="${produto.titulo}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
								</div>
							</div>
							<div class="row">
								<div class="div-msbc-validator col-md-12 mb-3">
									<label class="col-form-label" for="brevedescricao">${mensagens.get('ProdutoFormularioBrevedescricao').valor}:</label>
									<input type="text" id="brevedescricao" name="brevedescricao"
										class="form-control" value="${produto.brevedescricao}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
								</div>

							</div>
							<div class="row">
								<div class="div-msbc-validator col-md-12 mb-3">
									<label class="col-form-label" for="descricaocompleta">${mensagens.get('ProdutoFormularioDescricaocompleta').valor}:</label>
									<textarea class="form-control texto-html"
										name="descricaocompleta" id="descricaocompleta" rows="5"
										cols="80" data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="3999|O campo deve ter no máximo {value} caracteres">${produto.descricaocompleta}</textarea>
								</div>
							</div>
							<div class="row">
								<div class="div-msbc-validator col-md-4 mb-3">
									<label class="col-form-label" for="tag">${mensagens.get('ProdutoFormularioTag').valor}:</label>
									<input type="text" id="tag" name="tag" class="form-control"
										value="${produto.tag}" data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
								</div>
								<div class="div-msbc-validator col-md-4 mb-3">
									<label class="col-form-label" for="urlanunciante">${mensagens.get('ProdutoFormularioUrlanunciante').valor}:</label>
									<input class="form-control texto-html" name="urlanunciante"
										id="urlanunciante" data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="100|O campo deve ter no máximo {value} caracteres"
										value="${produto.urlanunciante}" />
								</div>
								<div class="div-msbc-validator col-md-4 mb-3">
									<c:set var="urlmodeloproduto" scope="application"
										value="${urlmodelo}/modelo-220x330.jpg" />
									<label class="col-form-label" for="arquivo">${mensagens.get('ProdutoFormularioImagem').valor}
										<a href="${urlmodeloproduto}" title="Pegar modelo"
										target="_blank">(220x330)</a>: ${produto.imagem}
									</label> <input class="form-control texto-html" name="arquivo"
										id="arquivo" type="file"
										data-msbc-required="Campo obrigatório">


								</div>
							</div>


							<div class="row">
								<div class="div-msbc-validator col-md-2 mb-3">
									<label class="col-form-label" for="estoque">${mensagens.get('ProdutoFormularioEstoque').valor}:</label>
									<input type="text" id="estoque" name="estoque"
										class="form-control" value="${produto.qtdademin}" />
								</div>
								<div class="div-msbc-validator col-md-2 mb-3">
									<label class="col-form-label" for="qtde">${mensagens.get('ProdutoFormularioQtdademin').valor}:</label>
									<input type="text" id="qtdademin" name="qtdademin"
										class="form-control" value="${produto.qtdademin}" />
								</div>
								<div class="div-msbc-validator col-md-2 mb-3">
									<label class="col-form-label" for="preco">${mensagens.get('ProdutoFormularioPreco').valor}:</label>
									<input type="text" id="preco" name="preco" class="form-control"
										value="${produto.preco}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="11|O campo deve ter no máximo {value} caracteres" />
								</div>
								<div class="div-msbc-validator col-md-2 mb-3">
									<label class="col-form-label" for="desconto">${mensagens.get('ProdutoFormularioDesconto').valor}:</label>
									<input type="text" id="desconto" name="desconto"
										class="form-control" value="${produto.desconto}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="2|O campo deve ter no máximo {value} caracteres" />
								</div>
								<div class="div-msbc-validator col-md-3 mb-3">
									<label class="col-form-label" for="faixadepreco">${mensagens.get('ProdutoFormularioFaixadepreco').valor}:</label>
									<select class="form-control" name="faixadepreco.id"
										id="faixadepreco"
										data-selecionado='${produto.getFaixaDePreco().getId()}'
										data-msbc-required="Campo obrigatório"
										onchange="faixadepreco_click();"></select> <input
										type="hidden" id="faixadeprecoid" name="faixadeprecoid"
										value="${produto.getFaixaDePreco().getId()}">
								</div>
							</div>

							<div class="row">
								<div class="div-msbc-validator col-md-4 mb-3">
									<label class="col-form-label" for="categoria">${mensagens.get('ProdutoFormularioCategoria').valor}:</label>
									<select class="form-control form-select" name="categoria.id"
										id="categoria" data-selecionado='${produto.categoria.id}'
										data-msbc-required="Campo obrigatório"></select>
								</div>
								<div class="div-msbc-validator col-md-4 mb-3">
									<label class="col-form-label" for="subcategoria">${mensagens.get('ProdutoFormularioSubCategoria').valor}:</label>
									<select class="form-select" name="subcategoria.id"
										id="subcategoria"
										data-selecionado='${produto.getSubCategoria().getId()}'
										data-msbc-required="Campo obrigatório"
										onchange="subcategoria_click();"></select> <input
										type="hidden" id="subcategoriaid" name="subcategoriaid"
										value="${produto.getSubCategoria().getId()}">
								</div>
								<div class="div-msbc-validator col-md-4 mb-3">
									<label class="col-form-label" for="status">${mensagens.get('ProdutoFormularioLinha').valor}:</label>
									<select class="form-select" name="linha.id" id="linha"
										data-selecionado='${produto.linha.id}'
										data-msbc-required="Campo obrigatório"></select>
								</div>
							</div>

							<div class="row">
								<div class="div-msbc-validator col-md-4 mb-3"></div>
								<div class="div-msbc-validator col-md-4 mb-3"></div>
								<div class="div-msbc-validator col-md-4 mb-3">
									<c:if test="${not empty produto.id}">
										<div>
											<a href="${urlimg}/${produto.id}"><h4>
													<i class="fa fa-images"></i> [+] Imagens
												</h4></a>
										</div>
									</c:if>
								</div>
							</div>
							<p>&nbsp;</p>
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