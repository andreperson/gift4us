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
	value="/js/UsuarioFormulario.js,/js/jquery/bootstrap-toggle.min.js,js/jquery/select2.min.js,/js/jquery/select2-pt-BR.js" />
<my:template fluido="false"
	title="${mensagens.get('UsuarioFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty usuario.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_USUARIO%>" />
	</c:if>
	<c:if test="${not empty usuario.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_USUARIO%>" />
	</c:if>

	<div class="container-fluid mt--1" style="padding-top: 50px;">
		<div class="row">
			<div class="col-xl-12 order-xl-1">
				<div class="card bg-secondary shadow">
					<div class="card-header bg-white border-0">
						<div class="row align-items-center">
							<div class="col-8">
								<h3 class="mb-0">${mensagens.get('UsuarioFormularioTituloDaPagina').valor}
								</h3>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form class="msbc-validator-form" id="formulario-form"
							name="formulario" action="${url}" method="POST">
							<input type="hidden" id="${_csrf.parameterName}"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<c:if test="${not empty usuario.id}">
								<input type="hidden" name="id" id="id" value="${usuario.id}" />
							</c:if>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="login">${mensagens.get('UsuarioFormularioLogin').valor}:</label>
								<input type="text" id="login" name="login"
									class="form-control msbc-validator" data-msbc-required="Campo obrigat??rio" value="${usuario.login}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="nome">${mensagens.get('UsuarioFormularioNome').valor}:</label>
								<input type="text" id="nome" name="nome"
									class="form-control msbc-validator" data-msbc-required="Campo obrigat??rio" value="${usuario.nome}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="apelido">${mensagens.get('UsuarioFormularioApelido').valor}:</label>
								<input type="text" id="apelido" name="apelido"
									class="form-control msbc-validator" value="${usuario.apelido}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="email">${mensagens.get('UsuarioFormularioEmail').valor}:</label>
								<input type="text" id="email" name="email"
									class="form-control msbc-validator" value="${usuario.email}" />
							</div>
							<div class="div-msbc-validator mb-3">
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<label class="col-form-label text-right"
											for="grupo-origem-filtro">${mensagens.get("UsuarioFormularioGrupo").valor}:</label>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="grupo-origem-filtro-select" style="display: none;"></select>
										<input class="form-control " type="text"
											id="grupo-origem-filtro" /> <select class="form-select"
											name="origem" id="grupo-origem" multiple="multiple" size="5"></select>
									</div>
									<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2 text-center">
										<i id="grupo-envia-todos-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-double-left"
											style="margin-top: 5px"></i><br /> <i
											id="grupo-envia-selecionados-para-origem"
											class="btn w-100 btn-outline-info fas fa-angle-left"
											style="margin-top: 10px"></i><br /> <i
											id="grupo-envia-selecionados-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-right"
											style="margin-top: 10px"></i><br /> <i
											id="grupo-envia-todos-para-destino"
											class="btn w-100 btn-outline-info fas fa-angle-double-right"
											style="margin-top: 10px"></i>
									</div>
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
										<select id="grupo-destino-filtro-select"
											style="display: none;"></select> <input class="form-control "
											type="text" id="grupo-destino-filtro" />
										<c:set var="selecionado" value="" />
										<c:forEach items="${usuario.listaDeGrupo}" var="grupo">
											<c:if test="${not empty selecionado}">
												<c:set var="selecionado" value="${selecionado},${grupo.id}" />
											</c:if>
											<c:if test="${empty selecionado}">
												<c:set var="selecionado" value="${grupo.id}" />
											</c:if>
										</c:forEach>
										<select class="form-select" name="grupo" id="grupo-destino"
											multiple="multiple" size="5"
											data-selecionado="${selecionado}"
											data-name="listaDeGrupo[].id"></select> <span
											id="grupo-inputs-objetos-selecionados" style="display: none;"></span>
									</div>
								</div>
							</div>
							<div class="div-msbc-validator mb-3">
								<label class="col-form-label" for="anunciante">${mensagens.get('UsuarioFormularioAnunciante').valor}:</label>
								<select class="form-select msbc-validator" name="anunciante.id"
									id="anunciante" data-selecionado='${usuario.anunciante.id}'
									data-msbc-required="Campo obrigat??rio"></select>
							</div>

							<div class="mb-3">
								<div class="col-md-4 offset-md-4">
									<c:if test="${empty usuario.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('UsuarioFormularioBotaoInsere').valor}" />
									</c:if>
									<c:if test="${not empty usuario.id}">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('UsuarioFormularioBotaoAltera').valor}" />
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