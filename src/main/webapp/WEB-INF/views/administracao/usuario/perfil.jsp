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
	value="/js/UsuarioPerfil.js,/js/jquery/bootstrap-toggle.min.js,js/jquery/select2.min.js,/js/jquery/select2-pt-BR.js" />
<my:template fluido="false"
	title="${mensagens.get('UsuarioFormularioTituloDaPagina').valor}"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty usuario.id}">
		<c:url var="url" value="<%=ListaDeURLs.INSERCAO_DE_USUARIO%>" />
	</c:if>
	<c:if test="${not empty usuario.id}">
		<c:url var="url" value="<%=ListaDeURLs.EDICAO_DE_USUARIO%>" />
	</c:if>

	<div class="main-content">
		<!-- Top navbar -->
		<!-- Header -->
		<sec:authentication property="principal.login" var="login" />
		<sec:authentication property="principal.apelido" var="apelido" />
		<div class="header pb-6 pt-5 pt-lg-1 d-flex align-items-center">
			<!-- Mask -->
			<span class="mask bg-gradient-default opacity-8"></span>
			<!-- Header container -->
			<div class="container-fluid d-flex align-items-center">
				<div class="row">
					<div class="col-lg-7 col-md-10">
						<h1 class="display-2 text-white">Olá ${apelido != null && apelido != null ? apelido : login}</h1>
						<p class="text-white mt-0 mb-5">Esta é a página do seu perfil
							de usuário. Verifique se todas as suas informações estão
							corretas. Em seu primeiro acesso, mude sua senha.</p>
					</div>
				</div>
			</div>

		</div>
		<!-- Page content -->
		<div class="container-fluid mt--7">
			<div class="row">
				<div class="col-xl-12 order-xl-1">
					<div class="card bg-secondary shadow">
						<div class="card-header bg-white border-0">
							<div class="row align-items-center">
								<div class="col-8">
									<h3 class="mb-0">Minha conta</h3>
								</div>
								<div class="col-4 text-right">
									<span class="btn btn-sm btn-primary" title="meu login">${login}</span>
								</div>
							</div>
						</div>


						<div class="alert alert-warning" role="alert" id="msgTopo"
							style="display: none">
							<span class="alert-icon"><i class="fas fa-key"></i></span> <span
								class="alert-text"><strong>Atenção! </strong><span
								id="msgTexto"></span> </span>
						</div>


						<div class="card-body">
							<c:url var="url" value="<%=ListaDeURLs.USUARIO_PERFIL_ALTERA%>" />
							<form class="msbc-validator-form" id="formulario-form"
								name="formulario" action="${url}" method="POST">
								<input type="hidden" id="${_csrf.parameterName}"
									name="${_csrf.parameterName}" value="${_csrf.token}" />
								<h6 class="heading-small text-muted mb-4">Informações do
									Usuário</h6>
								<div class="pl-lg-4">
									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label class="form-control-label" for="input-username">Nome</label>
												<input type="text" id="nome" name="nome"
													class="form-control msbc-validator" data-msbc-required="Campo obrigatório" 
													placeholder="" value="${usuario.nome}">
											</div>
										</div>
										<div class="col-lg-6">
											<div class="form-group">
												<label class="form-control-label" for="input-email">Apelido</label>
												<input type="text" id="apelido" name="apelido"
													class="form-control msbc-validator" data-msbc-required="Campo obrigatório" 
													placeholder="" value="${usuario.apelido}">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label class="form-control-label" for="input-first-name">Senha</label>
												<input type="password" id="senha" name="senha"
													class="form-control msbc-validator" data-msbc-required="Campo obrigatório" 
													placeholder="" value="" onblur="confirmaSenha();">
											</div>
										</div>
										<div class="col-lg-6">
											<div class="form-group">
												<label class="form-control-label" for="input-last-name">Confirmar
													Senha</label> <input type="password" id="confirmarsenha"
													name="confirmarsenha"
													class="form-control msbc-validator" data-msbc-required="Campo obrigatório" 
													placeholder="" value="" onblur="confirmaSenha();">
											</div>
										</div>
									</div>
								</div>
								<hr class="my-4" />
								<!-- Address -->
								<h6 class="heading-small text-muted mb-4">Dados de contato</h6>
								<div class="pl-lg-4">
									<div class="row">
										<div class="col-lg-12">
											<div class="form-group">
												<label class="form-control-label" for="input-first-name">Email</label>
												<input type="email" id="email" name="email"
													class="form-control msbc-validator" data-msbc-required="Campo obrigatório" 
													placeholder="" value="${usuario.email}">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-lg-1">
											<div class="form-group">
												<label class="form-control-label" for="input-last-name">Celular</label>
												<input type="text" id="dddcelular" name="dddcelular"
													class="form-control msbc-validator" data-msbc-required="Campo obrigatório" 
													placeholder="" value="${usuario.dddcelular}">
											</div>
										</div>
										<div class="col-lg-5">
											<div class="form-group">
												<label class="form-control-label" for="input-last-name">&nbsp;</label>
												<input type="text" id="celular" name="celular"
													class="form-control msbc-validator" data-msbc-required="Campo obrigatório" 
													placeholder="" value="${usuario.celular}">
											</div>
										</div>
										<div class="col-lg-1">
											<div class="form-group">
												<label class="form-control-label" for="input-last-name">Telefone</label>
												<input type="text" id="dddtelefone" name="dddtelefone"
													class="form-control form-control-alternative"
													placeholder="" value="${usuario.dddtelefone}">
											</div>
										</div>
										<div class="col-lg-5">
											<div class="form-group">
												<label class="form-control-label" for="input-last-name">&nbsp;</label>
												<input type="text" id="telefone" name="telefone"
													class="form-control form-control-alternative"
													placeholder="" value="${usuario.telefone}">
											</div>
										</div>
									</div>
								</div>
								<hr class="my-4" />
								<!-- Description -->
								<h6 class="heading-small text-muted mb-4">Escreva algo
									sobre você</h6>
								<div class="pl-lg-4">
									<div class="form-group">
										<label>Seja Criativo</label>
										<textarea rows="4"
											class="form-control form-control-alternative" name="about"
											id="about" placeholder="">${usuario.about}</textarea>
									</div>
								</div>
								<div class="mb-3">
									<div class="col-md-4 offset-md-4">
										<input
											class="btn btn-primary w-100 msbc-validator-button-submit"
											type="submit"
											value="${mensagens.get('UsuarioFormularioBotaoAltera').valor}" />
									</div>
								</div>
									<input type="hidden" id="login" name="login" value="${usuario.login}">
									<input type="hidden" name="id" id="id" value="${usuario.id}" />
									<input type="hidden" name="anuncianteid" id="anuncianteid" value="${usuario.anunciante.id}" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
</my:template>