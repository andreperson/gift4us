<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs" %>

<c:set var="cssFiles"
	value="../css/bootstrap/bootstrap-toggle.min.css,../css/bootstrap/select2.min.css,../css/bootstrap/select2-bootstrap4.min.css" />
<c:set var="jsFiles"
	value="/js/SubCategoriaFormulario.js,/js/jquery/bootstrap-toggle.min.js,js/jquery/select2.min.js,/js/jquery/select2-pt-BR.js" />

<my:template fluido="false" title="${mensagens.get('SubCategoriaFormularioTituloDaPagina').valor}" cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty subCategoria.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_SUBCATEGORIA %>" />
	</c:if>
	<c:if test="${not empty subCategoria.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_SUBCATEGORIA %>" />
	</c:if>
	
	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('SubCategoriaFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
				<form class="msbc-validator-form" id="formulario-form" name="formulario" action="${url}" method="POST" >
				<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<c:if test="${not empty subCategoria.id}">
					<input type="hidden" name="id" id="id" value="${subCategoria.id}" />
				</c:if>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="nome">${mensagens.get('SubCategoriaFormularioNome').valor}:</label>
					<input type="text" id="nome" name="nome" class="form-control msbc-validator" value="${subCategoria.nome}" 
						data-msbc-required="Campo obrigatório"
						data-msbc-maxlength="255|O campo deve ter no máximo {value} caracteres" />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="categoria">${mensagens.get('SubCategoriaFormularioCategoria').valor}:</label>
					<select class="form-select msbc-validator" name="categoria.id" id="categoria" data-selecionado='${subCategoria.categoria.id}' 
						data-msbc-required="Campo obrigatório" ></select>
				</div>
				
				<div class="mb-3">
					<div class="col-md-4 offset-md-4">
						<c:if test="${empty subCategoria.id}">
							<input class="btn btn-primary w-100 msbc-validator-button-submit" type="submit" value="${mensagens.get('SubCategoriaFormularioBotaoInsere').valor}" />
						</c:if>
						<c:if test="${not empty subCategoria.id}">
							<input class="btn btn-primary w-100 msbc-validator-button-submit" type="submit" value="${mensagens.get('SubCategoriaFormularioBotaoAltera').valor}" />
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