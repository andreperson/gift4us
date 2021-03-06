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
<my:template fluido="true"
	title="${mensagens.get('CategoriaFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty categoria.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_CATEGORIA%>" />
	</c:if>
	<c:if test="${not empty categoria.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_CATEGORIA%>" />
	</c:if>

	<!-- Page content -->
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('CategoriaFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
					
					<form id="categoria-cadastro"
							action="${url}?${_csrf.parameterName}=${_csrf.token}"
							enctype="multipart/form-data" method="post">
					
							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty categoria.id}">
								<input type="hidden" name="id" id="id" value="${categoria.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="nome">${mensagens.get('CategoriaFormularioNome').valor}:</label>
								<input type="text" id="nome" name="nome"
									class="form-control msbc-validator" value="${categoria.nome}"
									data-msbc-required="Campo obrigat??rio"
									data-msbc-maxlength="255|O campo deve ter no m??ximo {value} caracteres" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="arquivo">${mensagens.get('ProdutoFormularioImagem').valor}: ${categoria.imagem}</label>
								<input class="form-control texto-html"
									name="arquivo" id="arquivo" type="file">
							</div>
							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${empty categoria.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('CategoriaFormularioBotaoInsere').valor}" />
									</c:if>
									<c:if test="${not empty categoria.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('CategoriaFormularioBotaoAltera').valor}" />
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