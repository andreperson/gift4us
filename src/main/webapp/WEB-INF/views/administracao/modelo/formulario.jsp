<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs" %>
<c:set var="cssFiles" value="bootstrap/bootstrap-toggle.min.css" />
<c:set var="jsFiles" value="jquery/bootstrap-toggle.min.js" />
<my:template fluido="false" title="${mensagens.get('ModeloFormularioTituloDaPagina').valor}" cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty modelo.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_MODELO %>" />
	</c:if>
	<c:if test="${not empty modelo.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_MODELO %>" />
	</c:if>
	<div class="col">
		<div class="card w-100">
			<div class="card-header bg-info">
				<h4 class="text-white mb-0">${mensagens.get('ModeloFormularioTituloDaPagina').valor}</h4>
			</div>
			<div class="card-body">
				<form class="msbc-validator-form" id="formulario-form" name="formulario" action="${url}?${_csrf.parameterName}=${_csrf.token}" method="POST" enctype='multipart/form-data'>
				<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<c:if test="${not empty modelo.id}">
					<input type="hidden" name="id" id="id" value="${modelo.id}" />
				</c:if>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="descricao">${mensagens.get('ModeloFormularioDescricao').valor}:</label>
					<input type="text" id="descricao" name="descricao" class="form-control msbc-validator" value="${modelo.descricao}"  />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="titulo">${mensagens.get('ModeloFormularioTitulo').valor}:</label>
					<input type="text" id="titulo" name="titulo" class="form-control msbc-validator" value="${modelo.titulo}"  />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="corTitulo">${mensagens.get('ModeloFormularioCorTitulo').valor}:</label>
					<input type="text" id="corTitulo" name="corTitulo" class="form-control msbc-validator" value="${modelo.corTitulo}"  />
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="imagemUnica">${mensagens.get('ModeloFormularioImagemUnica').valor}:</label>
					<input class="form-control msbc-validator" name="imagemUnicaFile" id="imagemUnica" value="${modelo.imagemUnica}" type="file"  />
					<input name="imagemUnica" value="${modelo.imagemUnica}" type="hidden" />
					<c:if test="${not empty modelo.imagemUnica}">
						<span>${modelo.imagemUnica}</span>
					</c:if>
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="imagemCabecalho">${mensagens.get('ModeloFormularioImagemCabecalho').valor}:</label>
					<input class="form-control msbc-validator" name="imagemCabecalhoFile" id="imagemCabecalho" value="${modelo.imagemCabecalho}" type="file"  />
					<input name="imagemCabecalho" value="${modelo.imagemCabecalho}" type="hidden" />
					<c:if test="${not empty modelo.imagemCabecalho}">
						<span>${modelo.imagemCabecalho}</span>
					</c:if>
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label" for="imagemRodape">${mensagens.get('ModeloFormularioImagemRodape').valor}:</label>
					<input class="form-control msbc-validator" name="imagemRodapeFile" id="imagemRodape" value="${modelo.imagemRodape}" type="file"  />
					<input name="imagemRodape" value="${modelo.imagemRodape}" type="hidden" />
					<c:if test="${not empty modelo.imagemRodape}">
						<span>${modelo.imagemRodape}</span>
					</c:if>
				</div>
				<div class="div-msbc-validator mb-3">
					<label class="col-form-label text-right" for="ativo">${mensagens.get('ModeloFormularioAtivo').valor}:</label>
					<c:set var="checked" value="" />
					<c:if test="${modelo.ativo}">
						<c:set var="checked" value="checked='checked'" />
					</c:if>
					<input name='ativo' id='ativo' type='checkbox' data-toggle='toggle' data-on="${mensagens.get('ModeloFormularioAtivoLigado').valor}" data-off="${mensagens.get('ModeloFormularioAtivoDesligado').valor}" data-onstyle='primary' data-offstyle='danger' ${checked} >
				</div>
				<div class="mb-3">
					<div class="col-md-4 offset-md-4">
						<c:if test="${empty modelo.id}">
							<input class="btn btn-success w-100 msbc-validator-button-submit" type="submit" value="${mensagens.get('ModeloFormularioBotaoInsere').valor}" />
						</c:if>
						<c:if test="${not empty modelo.id}">
							<input class="btn btn-success w-100 msbc-validator-button-submit" type="submit" value="${mensagens.get('ModeloFormularioBotaoAltera').valor}" />
						</c:if>
					</div>
				</div>

				</form>
			</div>
		</div>
	</div>
</my:template>